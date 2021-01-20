package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.MyShape;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Square extends Rectangle {

    public Square(){

    }

    @Override
    public void draw(Graphics canvas) {

        Integer x = Math.toIntExact((long) Math.floor(properties.get("x")));
        int dist = x-point.x;
        canvas.setColor(fillColor);
        canvas.fillRect(point.x, point.y, dist,dist);
        ((Graphics2D)canvas).setStroke(new BasicStroke(2.0F));
        canvas.setColor(color);
        canvas.drawRect(point.x, point.y, dist,dist);

    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Shape shape = new Square();
        shape.setColor(color);
        shape.setFillColor(fillColor);
        shape.setPosition(point);

        Map newprop = new HashMap();
        Iterator var4 = this.properties.entrySet().iterator();

        while(var4.hasNext()) {
            Map.Entry s = (Map.Entry)var4.next();
            newprop.put(s.getKey(), s.getValue());
        }

        shape.setProperties(properties);
        return shape;

    }

}
