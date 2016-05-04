package com.skidrunner.javafx.raytracer;

import com.sun.javafx.geom.Vec3d;
import javafx.geometry.Point3D;

/**
 * Created by Mark on 5/3/2016.
 */
public class Vector3D {

    public double x;
    public double y;
    public double z;

    public Vector3D() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3D(Vector3D vector) {
        x = vector.x;
        y = vector.y;
        z = vector.z;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D set(Vector3D vector) {
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vector3D set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vector3D multiply(double factor) {
        x *= factor;
        y *= factor;
        z *= factor;

        return this;
    }

    public double magnitude() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector3D normalize() {
        double magnitude = Math.sqrt((x * x) + (y * y) + (z * z));

        if(magnitude == 0) {
            return set(0, 0, 0);
        }

        double factor = 1.0 / magnitude;

        x *= factor;
        y *= factor;
        z *= factor;

        return this;
    }
}
