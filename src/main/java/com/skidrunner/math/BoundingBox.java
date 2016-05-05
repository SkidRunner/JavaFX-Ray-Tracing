package com.skidrunner.math;

/**
 * Created by Mark on 5/4/2016.
 */
public class BoundingBox {

    private Vector3D minimum;
    private Vector3D maximum;

    public BoundingBox() {
        minimum = new Vector3D(0.0, 0.0, 0.0);
        maximum = new Vector3D(0.0, 0.0, 0.0);
    }

    public BoundingBox(Vector3D minimum, Vector3D maximum) {
        this.maximum = new Vector3D(minimum);
        this.maximum = new Vector3D(maximum);
    }

    public Vector3D getMinimum() {
        return minimum;
    }

    public void setMinimum(Vector3D minimum) {
        setMinimum(minimum.x, minimum.y, minimum.z);
    }

    public void setMinimum(double x, double y, double z) {
        minimum.set(x, y, z);
    }

    public Vector3D getMaximum() {
        return maximum;
    }

    public void setMaximum(Vector3D maximum) {
        setMaximum(maximum.x, maximum.y, maximum.z);
    }

    public void setMaximum(double x, double y, double z) {
        maximum.set(x, y, z);
    }

    public boolean intersect(Ray3D ray, Vector3D result) {
        return ray.intersect(this, result);
    }

}
