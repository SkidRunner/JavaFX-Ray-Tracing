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
 * A line segment which has an origin and a direction.
 * @author SkidRunner
 */
public class Ray {

    /**
     * Cache the hash code to make computing hashes faster.
     */
    private int hash = 0;

    /**
     * The origin x coordinate of this {@code Ray}.
     * @return the origin x coordinate
     */
    public final double getOriginX() { return originX; }
    private final double originX;

    /**
     * The origin y coordinate of this {@code Ray}.
     * @return the origin y coordinate
     */
    public final double getOriginY() { return originY; }
    private final double originY;

    /**
     * The origin z coordinate of this {@code Ray}.
     * @return the origin z coordinate
     */
    public final double getOriginZ() { return originZ; }
    private final double originZ;

    /**
     * The direction x coordinate of this {@code Ray}.
     * @return the direction x coordinate
     */
    public final double getDirectionX() { return directionX; }
    private final double directionX;

    /**
     * The direction y coordinate of this {@code Ray}.
     * @return the direction y coordinate
     */
    public final double getDirectionY() { return directionY; }
    private final double directionY;

    /**
     * The direction z coordinate of this {@code Ray}.
     * @return the direction z coordinate
     */
    public final double getDirectionZ() { return directionZ; }
    private final double directionZ;

    /**
     * Tests if this {@code Ray} intersects the interior of a specified Bounds, {@code b}.
     *
     * @param b The specified Bounds
     * @return true if this {@code Ray} and the interior of the specified Bounds, {@code b}, intersect.
     */
    public boolean intersects(Bounds b) {
        if ((b == null) || b.isEmpty()) return false;
        return intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    /**
     * Tests if this {@code Ray} intersects the interior of a specified rectangular area.
     *
     * @param x the x coordinate of the upper-left corner of the specified rectangular area
     * @param y the y coordinate of the upper-left corner of the specified rectangular area
     * @param w the width of the specified rectangular area
     * @param h the height of the specified rectangular area
     * @return true if this {@code Ray} and the interior of the rectangular area intersect.
     */
    public boolean intersects(double x, double y, double w, double h) {
        return intersects(x, y, 0, w, h, 0);
    }

    /**
     * Tests if this {@code Ray} intersects the interior of a specified rectangular area.
     *
     * @param x the x coordinate of the upper-left corner of the specified rectangular volume
     * @param y the y coordinate of the upper-left corner of the specified rectangular volume
     * @param z the z coordinate of the upper-left corner of the specified rectangular volume
     * @param w the width of the specified rectangular volume
     * @param h the height of the specified rectangular volume
     * @param d the depth of the specified rectangular volume
     * @return trueif this {@code Ray} and the interior of the rectangular area intersect.
     */
    public boolean intersects(double x, double y, double z, double w, double h, double d) {
        double tx1 = (x - originX) * -directionX;
        double tx2 = ((x + w) - originX) * -directionX;

        double tmin = Math.min(tx1, tx2);
        double tmax = Math.max(tx1, tx2);

        double ty1 = (y - originY) * -directionY;
        double ty2 = ((y + h) - originY) * -directionY;

        tmin = Math.min(tmin, Math.min(ty1, ty2));
        tmax = Math.max(tmax, Math.max(ty1, ty2));

        double tz1 = (z - originZ) * -directionZ;
        double tz2 = ((z + d) - originZ) * -directionZ;

        tmin = Math.min(tmin, Math.min(tz1, tz2));
        tmax = Math.max(tmax, Math.max(tz1, tz2));

        if(tmax >= 0) {
            return tmax > tmin;
        }

        return false;
    }

    /**
     * Creates a new instance of 2D {@code Ray}.
     * @param originX the origin x coordinate of this {@code Ray}.
     * @param originY the origin y coordinate of this {@code Ray}.
     * @param directionX the direction x coordinate of this {@code Ray}.
     * @param directionY the direction y coordinate of this {@code Ray}.
     */
    public Ray(double originX, double originY, double directionX, double directionY) {
        this.originX = originX;
        this.originY = originY;
        this.originZ = 0;

        double l = Math.sqrt((directionX * directionX) + (directionY * directionY));

        if(l != 0) {
            this.directionX = directionX / l;
            this.directionY = directionY / l;
        } else {
            this.directionX = directionX;
            this.directionY = directionY;
        }
        this.directionZ = 0;
    }

    /**
     * Creates a new instance of 3D {@code Ray}.
     * @param originX the origin x coordinate of this {@code Ray}.
     * @param originY the origin y coordinate of this {@code Ray}.
     * @param originZ the origin z coordinate of this {@code Ray}.
     * @param directionX the direction x coordinate of this {@code Ray}.
     * @param directionY the direction y coordinate of this {@code Ray}.
     * @param directionZ the direction z coordinate of this {@code Ray}.
     */
    public Ray(double originX, double originY, double originZ, double directionX, double directionY, double directionZ) {
        this.originX = originX;
        this.originY = originY;
        this.originZ = originZ;

        double l = Math.sqrt((directionX * directionX) + (directionY * directionY) + (directionZ * directionZ));

        if(l != 0) {
            this.directionX = directionX / l;
            this.directionY = directionY / l;
            this.directionZ = directionZ / l;
        } else {
            this.directionX = directionX;
            this.directionY = directionY;
            this.directionZ = directionZ;
        }
    }

    /**
     * Returns a hash code value for the point.
     * @return a hash code value for the point.
     */
    @Override public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj instanceof Ray) {
            Ray other = (Ray) obj;
            return getOriginX() == other.getOriginX() && getOriginY() == other.getOriginY() && getOriginZ() == other.getOriginZ() && getDirectionX() == other.getDirectionX() && getDirectionY() == other.getDirectionY() && getDirectionZ() == other.getDirectionZ();
        } else return false;
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for the object.
     */
    @Override public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + Double.doubleToLongBits(getOriginX());
            bits = 31L * bits + Double.doubleToLongBits(getOriginY());
            bits = 31L * bits + Double.doubleToLongBits(getOriginZ());
            bits = 31L * bits + Double.doubleToLongBits(getDirectionX());
            bits = 31L * bits + Double.doubleToLongBits(getDirectionY());
            bits = 31L * bits + Double.doubleToLongBits(getDirectionZ());
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }

    /**
     * Returns a string representation of this {@code Ray}.
     * This method is intended to be used only for informational purposes.
     * The content and format of the returned string might vary between
     * implementations.
     * The returned string might be empty but cannot be {@code null}.
     */
    @Override public String toString() {
        return "Ray ["
                + "originX:" + getOriginX()
                + ", originY:" + getOriginY()
                + ", originZ:" + getOriginZ()
                + ", directionX:" + getDirectionX()
                + ", directionY:" + getDirectionY()
                + ", directionZ:" + getDirectionZ()
                + "]";
    }
}
