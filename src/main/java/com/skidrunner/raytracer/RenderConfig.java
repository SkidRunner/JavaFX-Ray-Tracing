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
public class RenderConfig {

    private int imageWidth;
    private int imageHeight;
    private int rays;
    private String[] lines;
    private int threads;
    private Vector3D rayOrigin;
    private Vector3D camDirection;
    private Vector3D oddColour;
    private Vector3D evenColour;
    private Vector3D skyColour;
    private float sphereReflectivity;
    private float brightness;

    public final int getImageWidth() {
        return imageWidth;
    }

    public final void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public final int getImageHeight() {
        return imageHeight;
    }

    public final void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public final int getRays() {
        return rays;
    }

    public final void setRays(int rays) {
        this.rays = rays;
    }

    public final String[] getLines() {
        return lines;
    }

    public final void setLines(String[] lines) {
        this.lines = lines;
    }

    public final int getThreads() {
        return threads;
    }

    public final void setThreads(int threads) {
        this.threads = threads;
    }

    public final Vector3D getRayOrigin() {
        return rayOrigin;
    }

    public final void setRayOrigin(Vector3D rayOrigin) {
        this.rayOrigin = rayOrigin;
    }

    public final Vector3D getCamDirection() {
        return camDirection;
    }

    public final void setCamDirection(Vector3D camDirection) {
        this.camDirection = camDirection;
    }

    public final Vector3D getOddColour() {
        return oddColour;
    }

    public final void setOddColour(Vector3D oddColour) {
        this.oddColour = oddColour;
    }

    public final Vector3D getEvenColour() {
        return evenColour;
    }

    public final void setEvenColour(Vector3D evenColour) {
        this.evenColour = evenColour;
    }

    public final Vector3D getSkyColour() {
        return skyColour;
    }

    public final void setSkyColour(Vector3D skyColour) {
        this.skyColour = skyColour;
    }

    public final float getSphereReflectivity() {
        return sphereReflectivity;
    }

    public final void setSphereReflectivity(float sphereReflectivity) {
        this.sphereReflectivity = sphereReflectivity;
    }

    public final float getBrightness() {
        return brightness;
    }

    public final void setBrightness(float brightness) {
        this.brightness = brightness;
    }
}
