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

/**
 * @author SkidRunner
 */
public class Vector3D {
    private final double x, y, z; // Vector has three double attributes.

    // Constructor
    public Vector3D(double a, double b, double c) {
        x = a;
        y = b;
        z = c;
    }

    // Vector add
    public Vector3D add(Vector3D r) {
        return new Vector3D(x + r.x, y + r.y, z + r.z);
    }

    // Vector scaling
    public Vector3D scale(double r) {
        return new Vector3D(x * r, y * r, z * r);
    }

    public Vector3D invertScale(double r) {
        return new Vector3D(x / r, y / r, z / r);
    }

    // Vector dot product
    public double dot(Vector3D r) {
        return x * r.x + y * r.y + z * r.z;
    }

    // Cross-product
    public Vector3D cross(Vector3D r) {
        return new Vector3D(y * r.z - z * r.y, z * r.x - x * r.z, x * r.y - y * r.x);
    }

    // Used later for normalizing the vector
    public Vector3D normalise() {
        double factor = (double) (1f / (double) Math.sqrt(dot(this)));

        return scale(factor);
    }

    public String toString() {
        return x + "  " + y + "  " + z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
