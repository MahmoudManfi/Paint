package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.MyShape;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Rectangle extends MyShape {

    public Rectangle(){

    }

    @Override
    public void draw(Graphics canvas) {

        Integer x = Math.toIntExact((long) Math.floor(properties.get("x")));
        Integer y = Math.toIntExact((long) Math.floor(properties.get("y")));
        canvas.setColor(fillColor);
        canvas.fillRect(point.x, point.y, x-point.x, y-point.y);
        ((Graphics2D)canvas).setStroke(new BasicStroke(2.0F));
        canvas.setColor(color);
        canvas.drawRect(point.x, point.y, x-point.x,y-point.y);


    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Shape shape = new Rectangle();
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
