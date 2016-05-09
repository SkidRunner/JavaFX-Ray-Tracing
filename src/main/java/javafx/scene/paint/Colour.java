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

package javafx.scene.paint;

/**
 * 
 * @author SkidRunner
 */
public class Colour implements Cloneable, java.io.Serializable {
    
    // This class was created because Color in JavaFX does not allow
    // modification of its components after creation.
    
    static final long serialVersionUID = 1;
    
    /**
     * The red component of the {@code Colour}.
     *
     * @defaultValue 0.0
     */
    public double red;
    
    /**
     * The green component of the {@code Colour}.
     *
     * @defaultValue 0.0
     */
    public double green;
    
    /**
     * The blue component of the {@code Colour}.
     *
     * @defaultValue 0.0
     */
    public double blue;
    
    /**
     * The opacity component of the {@code Colour}.
     *
     * @defaultValue 0.0
     */
    public double opacity;
    
    /**
     * Cache the hash code to make computing hashes faster.
     */
    private int hashCode = 0;
    
    /**
     * Creates a new instance of {@code Colour}.
     */
    public Colour() {
        this.red = 0.0;
        this.green = 0.0;
        this.blue = 0.0;
        this.opacity = 1.0;
    }
    
    /**
     * Creates a new instance of {@code Colour}.
     * 
     * @param   colour The color components
     */
    public Colour(Colour colour) {
        this.red = colour.red;
        this.green = colour.green;
        this.blue = colour.blue;
        this.opacity = colour.opacity;
    }
    
    /**
     * Creates a new instance of {@code Colour}.
     * 
     * @param   red The red component
     * @param   green The green component
     * @param   blue The blue component
     * @param   opacity The opacity component
     */
    public Colour(double red, double green, double blue, double opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }
    
    public Colour set(Colour colour) {
        this.red = colour.red;
        this.green = colour.green;
        this.blue = colour.blue;
        this.opacity = colour.opacity;
    }
    
    public Colour set(double red, double green, double blue, double opacity) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
    }
    
    /**
     * Creates and returns a copy of this object.
     *
     * @return  A clone of this instance
     * @see     java.lang.Object#clone()
     * @see     java.lang.Cloneable
     */
    @Override public Colour clone() {
        try {
            return (Colour) super.clone();
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
            bits = (31L * bits) + Double.doubleToLongBits(red);
            bits = (31L * bits) + Double.doubleToLongBits(green);
            bits = (31L * bits) + Double.doubleToLongBits(blue);
            bits = (31L * bits) + Double.doubleToLongBits(opacity);
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
        if(!(object instanceof Colour)) return false;
        if (object == this) return true;
        Colour colour = (Colour) object;
        if(Double.compare(red, colour.red) != 0) return false;
        if(Double.compare(green, colour.green) != 0) return false;
        if(Double.compare(blue, colour.blue) != 0) return false;
        if(Double.compare(opacity, colour.opacity) != 0) return false;
        return true;
    }

    /**
     * Returns a string representation of the object in JSON format.
     *
     * @return  A string representation of the object in JSON format
     * @see     java.lang.Object#toString()
     */
    @Override public String toString() {
        return "{'red':" + red + ", 'green':" + green + ", 'blue':" + blue + ", 'opacity':" + opacity + "}";
    }
    
}
