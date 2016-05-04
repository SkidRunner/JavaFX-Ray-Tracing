package com.skidrunner.raytracer;

/*
 *  Copyright (C) 2016 SkidRunner
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import com.sun.javafx.geom.PickRay;

/**
 * @author SkidRunner
 */
public class Ray {

    private byte[] imageData;

    private boolean[][] data;
    private int rows;
    private int cols;

    private Vector3D floorColourOdd;
    private Vector3D floorColourEven;
    private Vector3D skyColour;

    private float sphereReflectivity;

    private long renderStart = 0;
    private long renderTime = 0;

    private void init(String[] lines) {
        cols = lines[0].length();
        rows = lines.length;
        com.sun.javafx.geom.PickRay p = new PickRay();
        data = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = lines[r].charAt(c);

                data[rows - 1 - r][cols - 1 - c] = ch == '*';
            }
        }
    }

    // The intersection test for line [o,v].
    // Return 2 if a hit was found (and also return distance t and bouncing ray
    // n).
    // Return 0 if no hit was found but ray goes upward
    // Return 1 if no hit was found but ray goes downward

    // Returns object[] 0 = int (m), 1 = float (t), 2 = Vector3f n
    Object[] test(Vector3D o, Vector3D d, Vector3D n) {
        double t = 1e9f;

        int m = 0;

        double p2 = -o.getZ() / d.getZ();

        if (.01 < p2) {
            t = p2;
            n = new Vector3D(0, 0, 1);
            m = 1;
        }

        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows; row++) {
                // For this row and column is there a sphere?
                if (data[row][col]) {
                    // There is a sphere but does the ray hit it ?

                    Vector3D p = o.add(new Vector3D(-col, 0, -row - 4));

                    double b = p.dot(d);
                    double c = p.dot(p) - 1;
                    double q = b * b - c;

                    // Does the ray hit the sphere ?
                    if (q > 0) {
                        double s = -b - Math.sqrt(q);

                        if (s < t && s > .01) { // So far this is the minimum distance, save
                            // it. And // also // compute the bouncing ray
                            // vector into 'n'
                            t = s;
                            n = (p.add(d.scale(t))).normalise();
                            m = 2;
                        }
                    }
                }
            }
        }
        return new Object[]{m, t, n};
    }

    // sample the world and return the pixel color for
    // a ray passing by point o (Origin) and d (Direction)
    Vector3D sample(Vector3D origin, Vector3D direction) {
        Vector3D n = new Vector3D(0, 0, 0);

        // Search for an intersection ray Vs World.
        Object[] result = test(origin, direction, n);

        int m = (Integer) result[0];
        double t = (Double) result[1];
        n = (Vector3D) result[2];

        if (m == 0) {
            // No sphere found and the ray goes upward: Generate a sky color
            return skyColour.scale((float) Math.pow(1 - direction.getZ(), 4));
        }

        // A sphere was maybe hit.

        // h = intersection coordinate
        Vector3D h = origin.add(direction.scale(t));

        // 'l' = direction to light (with random delta for soft-shadows).
        Vector3D l = new Vector3D(9 + Math.random(), 9 + Math.random(), 16);

        l = l.add(h.scale(-1));

        l = l.normalise();

        // r = The half-vector
        Vector3D r = direction.add(n.scale(n.dot(direction.scale(-2f))));

        // Calculated the lambertian factor
        double b = l.dot(n);

        // Calculate illumination factor (lambertian coefficient > 0 or in
        // shadow)?
        if (b < 0) {
            b = 0;
        } else {
            result = test(h, l, n);

            int res = (Integer) result[0];
            t = (Double) result[1];
            n = (Vector3D) result[2];

            if (res > 0) {
                b = 0;
            }
        }

        // Calculate the color 'p' with diffuse and specular component
        Vector3D rdash = r.scale(b > 0 ? 1 : 0);

        float p = (float) Math.pow(l.dot(rdash), 64);

        if (m == 1) {
            // No sphere was hit and the ray was going downward:
            h = h.invertScale(4);

            // Generate a floor color
            int ceil = (int) (Math.ceil(h.getX()) + Math.ceil(h.getY()));

            if ((ceil & 1) == 1) {
                return floorColourOdd.scale(b / 4 + .1f);
            } else {
                return floorColourEven.scale(b / 4 + .1f);
            }
        }

        // m == 2 A sphere was hit.
        // Cast an ray bouncing from the sphere surface.

        // Attenuate color since it is bouncing
        return new Vector3D(p, p, p).add(sample(h, r).scale(sphereReflectivity));
    }

    public byte[] getImageData() {
        renderTime = System.currentTimeMillis() - renderStart;
        return imageData;
    }

    public Ray() {

    }

    public void render(final RenderConfig config) {
        renderStart = System.currentTimeMillis();

        this.floorColourOdd = config.getOddColour();
        this.floorColourEven = config.getEvenColour();
        this.skyColour = config.getSkyColour();
        this.sphereReflectivity = config.getSphereReflectivity();

        init(config.getLines());

        imageData = new byte[config.getImageWidth() * config.getImageHeight() * 3];

        // Camera direction
        final Vector3D g = config.getCamDirection().normalise();

        // Camera up vector...Seem Z is pointing up :/ WTF !
        final Vector3D a = new Vector3D(0, 0, 1).cross(g).normalise().scale(.003f);

        // The right vector, obtained via traditional cross-product
        final Vector3D b = g.cross(a).normalise().scale(.003f);

        // WTF ? See https://news.ycombinator.com/item?id=6425965 for more.
        final Vector3D c = a.add(b).scale(-256).add(g);

        final int linesPerThread = config.getImageHeight() / config.getThreads();

        // System.out.println("LinesPerThread: " + linesPerThread);

        Thread[] workers = new Thread[config.getThreads()];

        final Vector3D defaultPixelColour = new Vector3D(16, 16, 16);

        for (int i = 0; i < config.getThreads(); i++) {
            final int startingLine = config.getImageHeight() - 1 - (i * linesPerThread);
            final int pixelBufferOffset = i * linesPerThread;

            // System.out.println("Thread " + i + " plotting " + startingLine);

            Thread worker = new Thread(new Runnable() {

                public void run() {
                    int pixel = config.getImageWidth() * pixelBufferOffset * 3;

                    // For each line
                    for (int y = startingLine; y > startingLine - linesPerThread; y--) {
                        // For each pixel in a line
                        for (int x = config.getImageWidth() - 1; x >= 0; x--) {
                            // Reuse the vector class to store not XYZ but an
                            // RGB
                            // pixel color
                            // Default pixel color is almost pitch black
                            Vector3D p = defaultPixelColour;

                            // Cast rays per pixel (For blur (stochastic
                            // sampling) and
                            // soft-shadows.
                            for (int r = config.getRays() - 1; r >= 0; r--) {
                                // The delta to apply to the origin of the view
                                // (For Depth of View blur).

                                // A little bit of delta up/down and left/right
                                Vector3D t = a.scale(Math.random() - 0.5f);
                                t = t.scale(64);

                                Vector3D t2 = b.scale(Math.random() - 0.5f);
                                t2 = t2.scale(64);

                                t = t.add(t2);

                                // Set the camera focal point and
                                // Cast the ray
                                // Accumulate the color returned in the p
                                // variable
                                // Ray Direction with random deltas for
                                // stochastic sampling

                                Vector3D dirA = a.scale(Math.random() + x);
                                Vector3D dirB = b.scale(Math.random() + y);
                                Vector3D dirC = dirA.add(dirB).add(c);

                                Vector3D dir = t.scale(-1).add(dirC.scale(16)).normalise();

                                // Ray Origin +p for color accumulation
                                p = sample(config.getRayOrigin().add(t), dir).scale(config.getBrightness()).add(p);

                            }

                            imageData[pixel++] = (byte) p.getX();
                            imageData[pixel++] = (byte) p.getY();
                            imageData[pixel++] = (byte) p.getZ();
                        }
                    }
                }
            });

            worker.start();
            workers[i] = worker;
        }

        for (int i = 0; i < config.getThreads(); i++) {
            try {
                workers[i].join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        renderTime = System.currentTimeMillis() - renderStart;
    }

    public long getRenderTime() {
        return renderTime;
    }
}
