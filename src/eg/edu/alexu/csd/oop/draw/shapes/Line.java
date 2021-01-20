package eg.edu.alexu.csd.oop.draw.shapes;

import eg.edu.alexu.csd.oop.draw.MyShape;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Line extends MyShape {

    public Line() {

    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties=properties;
    }

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(color);
        Integer x2 = Math.toIntExact((long)Math.floor(properties.get("x")));
        Integer y2 = Math.toIntExact((long)Math.floor(properties.get("y")));
        ((Graphics2D)canvas).setStroke(new BasicStroke(2.0F));
        canvas.drawLine(point.x , point.y ,x2,y2);

    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Shape shape = new Line();
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