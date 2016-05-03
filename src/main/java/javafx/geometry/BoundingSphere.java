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

    /**
     * The origin x coordinate of this {@code BoundingSphere}.
     *
     * @return the origin x coordinate
     */
    public final double getOriginX() {
        return originX;
    }

    private double originX;

    /**
     * The origin y coordinate of this {@code BoundingSphere}.
     *
     * @return the origin y coordinate
     */
    public final double getOriginY() {
        return originY;
    }

    private double originY;

    /**
     * The origin z coordinate of this {@code BoundingSphere}.
     *
     * @return the origin z coordinate
     */
    public final double getOriginZ() {
        return originZ;
    }

    private double originZ;

    /**
     * The radius of this {@code BoundingSphere}.
     *
     * @return the radius
     */
    public final double getRadius() {
        return radius;
    }

    private double radius;

    /**
     * Creates a new instance of 3D {@code BoundingSphere}.
     *
     * @param originX the origin x coordinate of this {@code BoundingSphere}.
     * @param originY the origin y coordinate of this {@code BoundingSphere}.
     * @param originZ the origin z coordinate of this {@code BoundingSphere}.
     * @param radius  the radius of the {@code BoundingSphere}
     */
    public BoundingSphere(double originX, double originY, double originZ, double radius) {
        super(originX - radius, originY - radius, originZ - radius, radius, radius, radius);

        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;

        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getMaxX() < getMinX() || getMaxY() < getMinY() || getMaxZ() < getMinZ();
    }

    /**
     * {@inheritDoc}
     * The points on the boundary are considered to lie inside the {@code BoundingSphere}.
     */
    @Override
    public boolean contains(Point2D p) {
        if (p == null) return false;
        return contains(p.getX(), p.getY(), 0);
    }

    /**
     * {@inheritDoc}
     * The points on the boundary are considered to lie inside the {@code BoundingSphere}.
     */
    @Override
    public boolean contains(Point3D p) {
        if (p == null) return false;
        return contains(p.getX(), p.getY(), p.getZ());
    }

    /**
     * {@inheritDoc}
     * The points on the boundary are considered to lie inside the {@code BoundingSphere}.
     */
    @Override
    public boolean contains(double x, double y) {
        return contains(x, y, 0);
    }

    /**
     * {@inheritDoc}
     * The points on the boundary are considered to lie inside the {@code BoundingSphere}.
     */
    @Override
    public boolean contains(double x, double y, double z) {
        double a = originX - x;
        double b = originY - y;
        double c = originZ - z;
        return Math.sqrt((a * a) + (b * b) + (c * c)) <= radius;
    }

    /**
     * {@inheritDoc}
     * The points on the boundary are considered to lie inside the {@code BoundingSphere}.
     */
    @Override
    public boolean contains(Bounds b) {
        if ((b == null) || b.isEmpty()) return false;
        if (b instanceof BoundingSphere) {
            double x = originX - ((BoundingSphere) b).originX;
            double y = originY - ((BoundingSphere) b).originY;
            double z = originZ - ((BoundingSphere) b).originZ;
            return Math.sqrt((x * x) + (y * y) + (z * z)) + ((BoundingSphere) b).radius <= radius;
        }
        return intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    /**
     * {@inheritDoc}
     * The points on the boundary are considered to lie inside the {@code BoundingSphere}.
     */
    @Override
    public boolean contains(double x, double y, double w, double h) {
        return contains(x, y) && contains(x + w, y + h);
    }

    /**
     * {@inheritDoc}
     * The points on the boundary are considered to lie inside the {@code BoundingSphere}.
     */
    @Override
    public boolean contains(double x, double y, double z, double w, double h, double d) {
        return contains(x, y, z) && contains(x + w, y + h, z + d);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersects(Bounds b) {
        if ((b == null) || b.isEmpty()) return false;
        if (b instanceof BoundingSphere) {
            double x = originX - ((BoundingSphere) b).originX;
            double y = originY - ((BoundingSphere) b).originY;
            double z = originZ - ((BoundingSphere) b).originZ;
            return Math.sqrt((x * x) + (y * y) + (z * z)) <= (radius + ((BoundingSphere) b).radius);
        }
        return intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return intersects(x, y, 0, w, h, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersects(double x, double y, double z, double w, double h, double d) {
        double min = 0;

        if (originX < x) {
            min += Math.pow(originX - x, 2);
        } else {
            min += Math.pow(originX - (x + w), 2);
        }

        if (originY < y) {
            min += Math.pow(originY - y, 2);
        } else {
            min += Math.pow(originY - (y + h), 2);
        }

        if (originZ < z) {
            min += Math.pow(originZ - z, 2);
        } else {
            min += Math.pow(originZ - (z + d), 2);
        }

        return min <= Math.pow(radius, 2);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof BoundingBox) {
            BoundingBox other = (BoundingBox) obj;
            return getMinX() == other.getMinX()
                    && getMinY() == other.getMinY()
                    && getMinZ() == other.getMinZ()
                    && getWidth() == other.getWidth()
                    && getHeight() == other.getHeight()
                    && getDepth() == other.getDepth();
        } else return false;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for the object.
     */
    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(getMinX());
            bits = 31L * bits + Double.doubleToLongBits(getMinY());
            bits = 31L * bits + Double.doubleToLongBits(getMinZ());
            bits = 31L * bits + Double.doubleToLongBits(getWidth());
            bits = 31L * bits + Double.doubleToLongBits(getHeight());
            bits = 31L * bits + Double.doubleToLongBits(getDepth());
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    /**
     * Returns a string representation of this {@code BoundingSphere}.
     * This method is intended to be used only for informational purposes.
     * The content and format of the returned string might getMary between
     * implementations.
     * The returned string might be empty but cannot be {@code null}.
     */
    @Override
    public String toString() {
        return "BoundingSphere ["
                + "originX:" + getOriginX()
                + ", originY:" + getOriginY()
                + ", originZ:" + getOriginZ()
                + ", radius:" + getRadius()
                + "]";
    }
}
