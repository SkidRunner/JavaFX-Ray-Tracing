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

package javafx.geometry;

/**
 * A sphere which is used to describe the bounds of a node
 * or other scene graph object.
 *
 * @author SkidRunner
 */
public class BoundingSphere extends Bounds {
    /**
     * Cache the hash code to make computing hashes faster.
     */
    private int hash = 0;

    private double originX;
    private double originY;
    private double originZ;

    private double radius;
    /**
     * Creates a new instance of 3D {@code BoundingSphere}.
     * @param originX    the origin x coordinate of this {@code BoundingSphere}.
     * @param originY    the origin y coordinate of this {@code BoundingSphere}.
     * @param originZ    the origin z coordinate of this {@code BoundingSphere}.
     * @param radius the radius of the {@code BoundingSphere}
     */
    public BoundingSphere(double originX, double originY, double originZ, double radius) {
        super(originX - radius, originY - radius, originZ - radius, radius, radius, radius);

        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;

        this.radius = radius;
    }

    @Override public boolean isEmpty() {
        return getMaxX() < getMinX() || getMaxY() < getMinY() || getMaxZ() < getMinZ();
    }

    @Override public boolean contains(Point2D p) {
        if (p == null) return false;
        return contains(p.getX(), p.getY(), 0);
    }

    @Override public boolean contains(Point3D p) {
        if (p == null) return false;
        return contains(p.getX(), p.getY(), p.getZ());
    }

    @Override public boolean contains(double x, double y) {
        return contains(x, y, 0);
    }

    @Override public boolean contains(double x, double y, double z) {
        double a = originX - x;
        double b = originY - y;
        double c = originZ - z;
        return Math.sqrt((a * a) + (b * b) + (c * c)) <= radius;
    }

    @Override public boolean contains(Bounds b) {
        if ((b == null) || b.isEmpty()) return false;
        if(b instanceof BoundingSphere) {
            double x = originX - ((BoundingSphere) b).originX;
            double y = originY - ((BoundingSphere) b).originY;
            double z = originZ - ((BoundingSphere) b).originZ;
            return Math.sqrt((x * x) + (y * y) + (z * z)) + ((BoundingSphere) b).radius <= radius;
        }
        return intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    @Override public boolean contains(double x, double y, double w, double h) {
        return contains(x, y) && contains(x + w, y + h);
    }

    @Override public boolean contains(double x, double y, double z, double w, double h, double d) {
        return contains(x, y, z) && contains(x + w, y + h, z + d);
    }

    @Override public boolean intersects(Bounds b) {
        if ((b == null) || b.isEmpty()) return false;
        if(b instanceof BoundingSphere) {
            double x = originX - ((BoundingSphere) b).originX;
            double y = originY - ((BoundingSphere) b).originY;
            double z = originZ - ((BoundingSphere) b).originZ;
            return Math.sqrt((x * x) + (y * y) + (z * z)) <= (radius + ((BoundingSphere) b).radius);
        }
        return intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    @Override public boolean intersects(double x, double y, double w, double h) {
        return intersects(x, y, 0, w, h, 0);
    }

    @Override public boolean intersects(double x, double y, double z, double w, double h, double d) {
        double min = 0;

        if(originX < x) {
            min += Math.pow(originX - x, 2);
        } else {
            min += Math.pow(originX - (x + w), 2);
        }

        if(originY < y) {
            min += Math.pow(originY - y, 2);
        } else {
            min += Math.pow(originY - (y + h), 2);
        }

        if(originZ < z) {
            min += Math.pow(originZ - z, 2);
        } else {
            min += Math.pow(originZ - (z + d), 2);
        }

        return min <= Math.pow(radius, 2);
    }
}
