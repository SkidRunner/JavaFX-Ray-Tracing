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
 * @author SkidRunner
 */
public class Ray3D implements Cloneable, java.io.Serializable {

    public final Vector3D origin;
    public final Vector3D direction;

    public double magnitude;

    /**
     * Cache the hash code to make computing hashes faster.
     */
    private int hashCode = 0;

    public Ray3D() {
        origin = new Vector3D(0.0, 0.0, 0.0);
        direction = new Vector3D(0.0, 0.0, 1.0);
        magnitude = Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return  A hash code value for this object
     * @see     java.lang.Object#hashCode()
     * @see     java.util.Hashtable
     */
    @Override public int hashCode() {
        if (hashCode == 0) {
            long bits = 7L;
            bits = (31L * bits) + origin.hashCode();
            bits = (31L * bits) + direction.hashCode();
            hashCode = (int) (bits ^ (bits >> 32));
        }
        return hashCode;
    }

    /**
     * Indicates whether some other {@code Object} is "equal to" this one.
     *
     * @param   object The reference {@code Object} with which to compare
     * @return  {@code true} if this {@code Object} is the same as the
     *          {@code object} argument; {@code false} otherwise
     * @see     java.lang.Object#equals(java.lang.Object)
     * @see     java.util.Hashtable
     */
    @Override public boolean equals(Object object) {
        if(!(object instanceof Ray3D)) {
            return false;
        }
        if(object == this) {
            return true;
        }
        Ray3D ray = (Ray3D) object;
        if(!origin.equals(ray.origin)) {
            return false;
        }
        if(!direction.equals(ray.origin)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of the object in JSON format.
     *
     * @return  A string representation of the object in JSON format
     * @see     java.lang.Object#toString()
     */
    @Override public String toString() {
        return "{'origin':" + origin.toString() + ", 'direction':" + direction.toString() + ", 'magnitude':" + magnitude + "}";
    }
}
