package com.skidrunner.javafx.raytracer;

import com.sun.javafx.geom.Vec3d;

/**
 * Created by Mark on 5/3/2016.
 */
public class Ray {

    private final Vector3D origin;
    private final Vector3D direction;

    public double maximumLength;

    public Ray() {
        origin = new Vector3D();
        direction = new Vector3D(0, 0, 1);
        maximumLength = Double.POSITIVE_INFINITY;
    }

    public Ray(Vector3D origin, Vector3D direction) {
        this.origin = new Vector3D(origin);
        this.direction = new Vector3D(direction);
        maximumLength = Double.POSITIVE_INFINITY;
    }

    public Ray(Vector3D origin, Vector3D direction, double maximumLength) {
        this.origin = new Vector3D(origin);
        this.direction = new Vector3D(direction);
        this.maximumLength = maximumLength;
    }

    public Vector3D intersects(Vector3D minimum, Vector3D maximum, Vector3D store) {

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

        if(tmin < 0) {
            return null;
        }

        if(store == null) {
            store = new Vector3D();
        }

        return store.set(direction.normalize()).multiply(tmax);
    }
}
