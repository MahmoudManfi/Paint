package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.draw.shapes.Circle;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyShape implements Shape{

    protected Point point = new Point();
    protected Map<String,Double> properties;
    protected Color color;
    protected Color fillColor;

    public MyShape(){
        properties=new HashMap<>();
        properties.put("x",0.0);
        properties.put("y",0.0);
        color = Color.BLACK;
        fillColor = fillColor.WHITE;
        point = new Point(0,0);
    }
    @Override
    public void setPosition(Point position) { point = position; }

    @Override
    public Point getPosition() { return point; }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
        properties.put("x",point.x+Math.abs(point.x-properties.get("x")));
        properties.put("y",point.y+Math.abs(point.y-properties.get("y")));
    }

    @Override
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) { this.color = color; }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) { fillColor = color; }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void draw(Graphics canvas) {  }

    @Override
    public Object clone() throws CloneNotSupportedException {

        Shape shape = new MyShape();
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
