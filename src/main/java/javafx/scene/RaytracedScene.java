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

package javafx.scene;

import com.sun.javafx.sg.prism.NGGroup;
import com.sun.javafx.sg.prism.NGNode;
import com.sun.javafx.tk.TKScene;
import com.sun.prism.*;
import javafx.beans.DefaultProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.lang.reflect.Field;
import java.nio.Buffer;

/**
 * @author SkidRunner
 */
@DefaultProperty("root")
public class RaytracedScene extends Scene {

    public RaytracedScene(Parent root) {
        this(root, -1, -1, Color.WHITE);
    }

    public RaytracedScene(Parent root, double width, double height) {
        this(root, width, height, Color.WHITE);
    }

    public RaytracedScene(Parent root, Paint fill) {
        this(root, -1, -1, fill);
    }

    public RaytracedScene(Parent root, double width, double height, Paint fill) {
        super(root, width, height, fill);
        try {
            Field field = Node.class.getDeclaredField("peer");
            field.setAccessible(true);
            field.set(root, new RaytracedNGNode());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private class RaytracedNGNode extends NGNode {

        @Override protected void renderContent(Graphics graphics) {

        }

        @Override protected boolean hasOverlappingContents() {
            return false;
        }

    }
}
