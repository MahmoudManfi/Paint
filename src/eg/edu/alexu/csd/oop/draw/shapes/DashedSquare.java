package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.MyShape;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DashedSquare extends Rectangle {

    @Override
    public void draw(Graphics canvas) {

        Integer x = Math.toIntExact((long) Math.floor(properties.get("x")));
        Integer y = Math.toIntExact((long) Math.floor(properties.get("y")));
        int dist = Math.abs(point.x-x);
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        ((Graphics2D)canvas).setStroke(dashed);
        canvas.setColor(Color.GRAY);
        canvas.drawRect(point.x, point.y, dist,dist);
        canvas.drawRect(x,point.y+(x-point.x),15,15);

    }

}
