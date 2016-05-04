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
 * A 3D value that usually represents a relative magnitude vector's x, y, z
 * magnitudes. It can also represent x, y, z coordinates.
 *
 * @author SkidRunner
 */
public class Vector3D implements Cloneable, java.io.Serializable {

    // This class was created because Point3D in JavaFX does not allow
    // modification of coordinates after creation. Also when doing any math
    // with Point3D ie. (add, subtract, multiply) a new instance of Point3D
    // is created per method call. This may not be an issue for most
    // applications but the cost is too great in terms of ray tracing.

    static final long serialVersionUID = 1;

    /**
     * A constant holding a Not-a-Number (NaN) value of type {@code Vector3D}.
     */
    public final static Vector3D NaN = new Vector3D(Double.NaN, Double.NaN, Double.NaN);

    /**
     * A constant holding the negative infinity of type {@code Vector3D}.
     */
    public final static Vector3D NEGATIVE_INFINITY = new Vector3D(
            Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY,
            Double.NEGATIVE_INFINITY);

    /**
     * A constant holding the positive infinity of type {@code Vector3D}.
     */
    public final static Vector3D POSITIVE_INFINITY = new Vector3D(
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            Double.POSITIVE_INFINITY);

    /**
     * The x coordinate.
     *
     * @defaultValue 0.0
     */
    public double x;

    /**
     * The y coordinate.
     *
     * @defaultValue 0.0
     */
    public double y;

    /**
     * The z coordinate.
     *
     * @defaultValue 0.0
     */
    public double z;

    /**
     * Cache the hash code to make computing hashes faster.
     */
    private int hashCode = 0;

    /**
     * Creates a new instance of {@code Vector3D}.
     */
    public Vector3D() {
        this(0, 0, 0);
    }

    /**
     * Creates a new instance of {@code Vector3D}.
     *
     * @param   vector The {@code Vector3D} coordinates
     */
    public Vector3D(Vector3D vector) {
        this(vector.x, vector.y, vector.z);
    }

    /**
     * Creates a new instance of {@code Vector3D}.
     *
     * @param   x The X coordinate
     * @param   y The Y coordinate
     * @param   z The Z coordinate
     */
    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Sets the coordinates of {@code this} to specified vector.
     *
     * @param   vector The {@code Vector3D} coordinates
     */
    public Vector3D set(Vector3D vector) {
        return set(vector.x, vector.y, vector.z);
    }

    /**
     * Sets the coordinates of {@code this} to specified vector.
     *
     * @param   x The X coordinate
     * @param   y The Y coordinate
     * @param   z The Z coordinate
     */
    public Vector3D set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Computes the distance between specified vector coordinates and
     * {@code this} vector.
     *
     * @param   vector The other vector
     * @return  The distance between the two vectors
     */
    public double distance(Vector3D vector) {
        return distance(vector.x, vector.y, vector.z);
    }

    /**
     * Computes the distance between specified vector coordinates and
     * {@code this} vector.
     *
     * @param   x The X coordinate of other vector
     * @param   y The Y coordinate of other vector
     * @param   z The Z coordinate of other vector
     * @return  The distance between the two vectors
     */
    public double distance(double x, double y, double z) {
        double x1 = this.x - x;
        double y1 = this.y - y;
        double z1 = this.z - z;
        return Math.sqrt((x1 * x1) + (y1 * y1) + (z1 * z1));
    }

    /**
     * Adds specified vector to the coordinates of {@code this} vector.
     *
     * @param   vector The vector whose coordinates are to be added
     * @return  {@code this} vector with added coordinates
     */
    public Vector3D add(Vector3D vector) {
        return add(vector.x, vector.y, vector.z);
    }

    /**
     * Adds specified vector to the coordinates of {@code this} vector.
     *
     * @param   x The X coordinate addition
     * @param   y The Y coordinate addition
     * @param   z The Z coordinate addition
     * @return  The point with added coordinates
     */
    public Vector3D add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Subtracts specified vector from the coordinates of {@code this} vector.
     *
     * @param   vector The vector whose coordinates are to be subtracted
     * @return  {@code this} vector with subtracted coordinates
     */
    public Vector3D subtract(Vector3D vector) {
        return subtract(vector.x, vector.y, vector.z);
    }

    /**
     * Subtracts specified vector from the coordinates of {@code this} vector.
     *
     * @param   x The X coordinate subtraction
     * @param   y The Y coordinate subtraction
     * @param   z The Z coordinate subtraction
     * @return  {@code this} vector with subtracted coordinates
     */
    public Vector3D subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Multiplies {@code this} vector by the specified factor.
     *
     * @param   factor The factor multiplying the coordinates
     * @return  {@code this} vector with multiplied coordinates
     */
    public Vector3D multiply(double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
        return this;
    }

    /**
     * Divides {@code this} vector by the specified factor.
     *
     * @param   factor The factor multiplying the coordinates
     * @return  {@code this} vector with divided coordinates
     */
    public Vector3D divide(double factor) {
        factor = 1.0 / factor;
        x *= factor;
        y *= factor;
        z *= factor;
        return this;
    }

    /**
     * Normalizes the relative magnitude vector represented by this instance.
     * Resulting in a vector with the same direction and magnitude equal to 1.
     * If this is a zero vector, a zero vector is returned.
     *
     * @return  {@code this} as normalized vector
     */
    public Vector3D normalize() {
        double magnitude = (x * x) + (y * y) + (z * z);
        if (magnitude != 1.0 && magnitude != 0.0) {
            magnitude = 1.0 / Math.sqrt(magnitude);
            x *= magnitude;
            y *= magnitude;
            z *= magnitude;
        }
        return this;
    }

    /**
     * Computes the interpolated vector which lies between this vector and the
     * specified coordinates.
     *
     * @param   vector The other end vector
     * @param   value The magnitude of change between the two vectors
     * @return  {@code this} as interpolated vector
     */
    public Vector3D interpolate(Vector3D vector, double value) {
        return interpolate(vector.x, vector.y, vector.z, value);
    }

    /**
     * Computes the interpolated vector which lies between this vector and the
     * specified coordinates.
     *
     * @param   x The X coordinate of the second end vector
     * @param   y The Y coordinate of the second end vector
     * @param   z The Z coordinate of the second end vector
     * @param   value The magnitude of change between the two vectors
     * @return  {@code this} as interpolated vector
     */
    public Vector3D interpolate(double x, double y, double z, double value) {
        double x1 = ((1 - value) * this.x) + (value * x);
        double y1 = ((1 - value) * this.y) + (value * y);
        double z1 = ((1 - value) * this.z) + (value * z);
        return set(x1, y1, z1);
    }

    /**
     * Computes the angle (in radians) between the vector represented
     * by this vector and the vector represented by the specified vector.
     *
     * @param   vector The other vector
     * @return  The angle between the two vectors measured in radians,
     *          {@code NaN} if any of the two vectors is a zero vector
     */
    public double angle(Vector3D vector) {
        return angle(vector.x, vector.y, vector.z);
    }

    /**
     * Computes the angle (in radians) between the vector represented
     * by this vector and the specified vector.
     *
     * @param   x The X magnitude of the other vector
     * @param   y The Y magnitude of the other vector
     * @param   z The Z magnitude of the other vector
     * @return  The angle between the two vectors measured in radians
     */
    public double angle(double x, double y, double z) {
        double dotProduct = (this.x * x) + (this.y * y) + (this.z * z);
        double magnitude = Math.sqrt(((this.x * this.x) + (this.y * this.y)
                + (this.z * this.z)) * ((x * x) + (y * y) + (z * z)));
        return Math.acos(dotProduct / Math.sqrt(magnitude));
    }

    /**
     * Computes the angle (in radians) between the three vectors with this vector
     * as a vertex.
     *
     * @param   vectorA one vector
     * @param   vectorB other vector
     * @return  The angle between the vectors (this, vectorA) and
     *          (this, vectorB) measured in radians, {@code NaN} if the three
     *          vectors are not different from one another
     */
    public double angle(Vector3D vectorA, Vector3D vectorB) {
        double x1 = vectorA.x - x;
        double y1 = vectorA.y - y;
        double z1 = vectorA.z - z;
        double x2 = vectorB.x - x;
        double y2 = vectorB.y - y;
        double z2 = vectorB.z - z;
        double dotProduct = (x1 * x2) + (y1 * y2) + (z1 * z2);
        double magnitude = Math.sqrt(((x1 * x1) + (y1 * y1) + (z1 * z1))
                * ((x2 * x2) + (y2 * y2) + (z2 * z2)));
        return Math.acos(dotProduct / Math.sqrt(magnitude));
    }

    /**
     * Computes magnitude (length) of the relative magnitude vector represented
     * by this instance.
     *
     * @return  The magnitude of the vector
     */
    public double magnitude() {
        return Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * Computes dot (scalar) product of the vector represented by this instance
     * and the specified vector.
     *
     * @param   vector The other vector
     * @return  {@code this} as the dot product of the two vectors
     */
    public double dotProduct(Vector3D vector) {
        return dotProduct(vector.x, vector.y, vector.z);
    }

    /**
     * Computes dot (scalar) product of the vector represented by this instance
     * and the specified vector.
     *
     * @param   x The X magnitude of the other vector
     * @param   y The Y magnitude of the other vector
     * @param   z The Z magnitude of the other vector
     * @return  {@code this} as the dot product of the two vectors
     */
    public double dotProduct(double x, double y, double z) {
        return (this.x * x) + (this.y * y) + (this.z * z);
    }

    /**
     * Computes cross product of the vector represented by this instance
     * and the specified vector.
     *
     * @param   vector The other vector
     * @return  {@code this} as the cross product of the two vectors
     */
    public Vector3D crossProduct(Vector3D vector) {
        return crossProduct(vector.x, vector.y, vector.z);
    }

    /**
     * Computes cross product of the vector represented by this instance
     * and the specified vector.
     *
     * @param   x The X magnitude of the other vector
     * @param   y The Y magnitude of the other vector
     * @param   z The Z magnitude of the other vector
     * @return  {@code this} as the cross product of the two vectors
     */
    public Vector3D crossProduct(double x, double y, double z) {
        double x1 = (this.y * z) - (this.z * y);
        double y1 = (this.z * x) - (this.x * z);
        double z1 = (this.x * y) - (this.y * x);
        return set(x1, y1, z1);
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return  A clone of this instance
     * @see     java.lang.Object#clone()
     * @see     java.lang.Cloneable
     */
    @Override public Vector3D clone() {
        try {
            return (Vector3D) super.clone();
        } catch (CloneNotSupportedException exception) {
            throw new AssertionError(exception);
        }
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
            bits = (31L * bits) + Double.doubleToLongBits(x);
            bits = (31L * bits) + Double.doubleToLongBits(y);
            bits = (31L * bits) + Double.doubleToLongBits(z);
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
        if(!(object instanceof Vector3D)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        Vector3D vector = (Vector3D) object;
        if(Double.compare(x, vector.x) != 0) {
            return false;
        }
        if(Double.compare(y, vector.y) != 0) {
            return false;
        }
        if(Double.compare(z, vector.z) != 0) {
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
        return "{'x':'" + x + "', 'y':'" + y + "', 'z':'" + z + "'}";
    }
}
