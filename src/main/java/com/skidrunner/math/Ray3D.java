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

package com.skidrunner.math;

/**
 * @author SkidRunner
 */
public class Ray3D implements Cloneable, java.io.Serializable {

    private Vector3D origin;
    private Vector3D direction;

    private double magnitude;

    /**
     * Cache the hash code to make computing hashes faster.
     */
    private int hashCode = 0;

    public Ray3D() {
        origin = new Vector3D(0.0, 0.0, 0.0);
        direction = new Vector3D(0.0, 0.0, 1.0);
        magnitude = Double.POSITIVE_INFINITY;
    }

    public Ray3D(Ray3D ray) {
        this(ray.origin, ray.direction, ray.magnitude);
    }

    public Ray3D(Vector3D origin, Vector3D direction, double magnitude) {
        this.origin = new Vector3D(origin);
        this.direction = new Vector3D(direction);
        this.magnitude = magnitude;
    }

    public void set(Ray3D ray) {
        set(ray.origin, ray.direction, ray.magnitude);
    }

    public void set(Vector3D origin, Vector3D direction, double magnitude) {
        this.origin.set(origin);
        this.direction.set(direction);
        this.magnitude = magnitude;
    }

    public Vector3D getOrigin() {
        return origin;
    }

    public void setOrigin(Vector3D origin) {
        setOrigin(origin.x, origin.y, origin.z);
    }

    public void setOrigin(double x, double y, double z) {
        origin.set(x, y, z);
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        setDirection(direction.x, direction.y, direction.z);
    }

    public void setDirection(double x, double y, double z) {
        direction.set(x, y, z);
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public boolean intersect(BoundingBox boundingBox, Vector3D result) {
        Vector3D minimum = boundingBox.getMinimum();
        Vector3D maximum = boundingBox.getMaximum();

        double tx1 = (minimum.x - origin.x) * -direction.x;
        double tx2 = (maximum.x - origin.x) * -direction.x;

        double tmin = Math.min(tx1, tx2);
        double tmax = Math.max(tx1, tx2);

        double ty1 = (minimum.y - origin.y) * -direction.y;
        double ty2 = (maximum.y - origin.y) * -direction.y;

        tmin = Math.min(tmin, Math.min(ty1, ty2));
        tmax = Math.max(tmax, Math.max(ty1, ty2));

        double tz1 = (minimum.z - origin.z) * -direction.z;
        double tz2 = (maximum.z - origin.z) * -direction.z;

        tmin = Math.min(tmin, Math.min(tz1, tz2));
        tmax = Math.max(tmax, Math.max(tz1, tz2));

        result.set(origin).multiply(tmax);

        if (tmax < 0 || tmax <= tmin) {
            return false;
        }

        return true;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return  A clone of this instance
     * @see     java.lang.Object#clone()
     * @see     java.lang.Cloneable
     */
    @Override public Ray3D clone() {
        try {
            Ray3D ray = (Ray3D) super.clone();
            ray.origin = origin.clone();
            ray.direction = direction.clone();
            return ray;
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError(exception);
        }
    }

    /**
     * Returns a string representation of the object in JSON format.
     *
     * @return  A string representation of the object in JSON format
     * @see     java.lang.Object#toString()
     */
    @Override public String toString() {
        return "{'origin':" + origin.toString() + ", 'direction':" + direction.toString() + "}";
    }
}
