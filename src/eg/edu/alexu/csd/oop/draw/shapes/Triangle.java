package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.MyShape;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Triangle extends MyShape {

    public Triangle() {

    }

    @Override
    public void draw(Graphics canvas) {

        Integer x = Math.toIntExact((long) Math.floor(properties.get("x")));
        Integer y = Math.toIntExact((long) Math.floor(properties.get("y")));


        canvas.setColor(fillColor);
        canvas.fillPolygon(new int[] {point.x,x,point.x+(x-point.x)/2},new int[] {y,y,point.y},3);
        ((Graphics2D)canvas).setStroke(new BasicStroke(2.0F));
        canvas.setColor(color);
        canvas.drawPolygon(new int[] {point.x,x,point.x+(x-point.x)/2},new int[] {y,y,point.y},3);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Shape shape = new Triangle();
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
