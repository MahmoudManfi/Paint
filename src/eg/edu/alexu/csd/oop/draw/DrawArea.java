package eg.edu.alexu.csd.oop.draw;

import com.sun.javafx.collections.MappingChange;
import eg.edu.alexu.csd.oop.draw.shapes.*;
import eg.edu.alexu.csd.oop.draw.shapes.Rectangle;
import javafx.scene.layout.Background;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DrawArea extends JComponent implements MouseListener, MouseMotionListener{

    private Graphics pen;
    private Image image;
    private Shape rectToDraw; // the rectangle that will be aroud the shape;
    private Shape shapeToDraw; // the shape that i hold when i clicked on the button
    private Shape shape; // the shape that i  clicked on it on the canvas
    private Engine drawingEngine;
    private Map<String,Double> properties; // to hold the properties of the shape and all time become null
    private Point point; // to hold the position of the shape
    private Shape[] shapes;// array of shapes that i take from the engine
    private boolean isSelect=false,reSize=false,move=false; // to know if selected make draged and relesed
    private Shape temp;
    private int x1,y1,x2,y2;
    private int xline,yline,x2line,y2line;
    private boolean ignore=false;
    private boolean ignore1=false;


    /***
- pen : Graphics
- image :  Image
- reactToDraw : Shape
- shapeToDraw : Shape
- shape : Shape
- drawingEngine : DrawingEngine
- properties : Map
- Point : point
- shapes : List of Shapes
- isSelect : boolean
- resize  : boolean
- move : boolean
- ignore : boolean
- x1,x2,y1,y2 : int
- temp : Shape
     */

    public DrawArea(){

        this.addMouseListener((MouseListener)this);
        this.addMouseMotionListener((MouseMotionListener)this);

    }


    @Override
    protected void paintComponent(Graphics g) {

        if (image == null) {

            image = createImage(2000,2000);
            pen = image.getGraphics();
            ((Graphics2D)pen).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            pen.setColor(Color.WHITE);
            pen.fillRect(0,0,2000,2000);
            pen.setColor(Color.BLACK);
            drawingEngine = new Engine();
            shapes = drawingEngine.getShapes();

        }
        g.drawImage(image,0,0,null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (GUI.getSelect() == true) {

            shape=Search(e.getX(),e.getY());// i take the shape that i was selected when i clicked on the canvas
            if (ignore==true && shape != null) {

                rectToDraw = new DashedRectangle();
                Point p = new Point(shape.getPosition().x,shape.getPosition().y);
                rectToDraw.setPosition(p);
                Map <String,Double> prop = new HashMap<>();
                prop.put("x",(double)(xline));
                prop.put("y",(double)(yline));
                rectToDraw.setProperties(prop);
                Clear();
                rectToDraw.draw(pen);
                repaint(); isSelect=true; ignore1=true; ignore=false;

            } else if (shape != null) {

                if (shape.getClass().getName() == "eg.edu.alexu.csd.oop.draw.shapes.Line") {

                    if (CircleSquare(shape)) {
                        rectToDraw = new DashedSquare();

                    } else rectToDraw = new DashedRectangle();

                    Point p = new Point(xline-15,yline-15);
                    rectToDraw.setPosition(p);
                    Map <String,Double> prop = new HashMap<>();
                    prop.put("x",(double)(x2line+15));
                    prop.put("y",(double)(y2line+15));
                    rectToDraw.setProperties(prop);
                    Clear();
                    rectToDraw.draw(pen);
                    repaint(); isSelect=true;

                } else {

                    if (CircleSquare(shape)) {
                        rectToDraw = new DashedSquare();

                    } else rectToDraw = new DashedRectangle();

                    Point p = new Point(shape.getPosition().x,shape.getPosition().y);
                    rectToDraw.setPosition(p);
                    Map <String,Double> prop = new HashMap<>();
                    prop.put("x",shape.getProperties().get("x"));
                    prop.put("y",shape.getProperties().get("y"));
                    rectToDraw.setProperties(prop);
                    Clear();
                    rectToDraw.draw(pen);
                    repaint(); isSelect=true;
                }

            } else {

                Clear();

            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

        shapeToDraw = GUI.getShape();
        if (shapeToDraw != null) {

            point = new Point(e.getX(),e.getY());
            shapeToDraw.setPosition(point);

        } else if (isSelect == true) {

            if (ignore1) {
                ignore1=false;
                if (e.getX() >= xline && e.getY() >= yline && e.getX() <= xline +15&& e.getY() <= yline + 15) reSize=true;

                else if (e.getX()>shape.getPosition().x && e.getY()>shape.getPosition().y && e.getY() < yline && e.getX() < xline) {

                    move=true;
                    x1 = e.getX()-shape.getPosition().x;
                    y1 = e.getY()-shape.getPosition().y;
                    x2 = (int)(xline-e.getX());
                    y2 = (int)(yline-e.getY());

                    try{
                        temp = (Shape) shape.clone();
                    } catch (Exception e1) {
                        System.out.println("error e1");

                    }
                } else Clear();


            } else if (CircleSquare(shape) == true) {

                if ((e.getX() >= shape.getProperties().get("x") && (e.getY() )>= shape.getPosition().y+(shape.getProperties().get("x")-shape.getPosition().x)) && (shape.getProperties().get("x")+15 >= e.getX()) &&  (e.getY() <= shape.getPosition().y+(shape.getProperties().get("x")-shape.getPosition().x)+15)) {

                    reSize=true;

                } else if (e.getX()>shape.getPosition().x && e.getY()>shape.getPosition().y && e.getX()<shape.getProperties().get("x") && e.getY()<shape.getPosition().y+(shape.getProperties().get("x")-shape.getPosition().x)) {

                    move=true;
                    x1 = e.getX()-shape.getPosition().x;
                    y1 = e.getY()-shape.getPosition().y;
                    x2 = (int)((long)Math.floor(shape.getProperties().get("x"))-e.getX());
                    y2 = shape.getPosition().y+(int)((long)Math.floor(shape.getProperties().get("x"))-shape.getPosition().x)-e.getY();
                    try{
                        temp = (Shape) shape.clone();
                    } catch (Exception e1) {
                        System.out.println("error e1");

                    }

                } else Clear();

            } else {

                if (shape.getClass().getName() == "eg.edu.alexu.csd.oop.draw.shapes.Line") {

                    if (e.getX() >= x2line+15 && e.getY() >= y2line+15 && e.getX() <= x2line +30&& e.getY() <= y2line + 30) reSize=true;

                    else if (e.getX() >= xline - 15 && e.getY() >= yline - 15 && e.getX() <= x2line + 15 && e.getY() <= y2line + 15) {

                        move=true;

                        x1 = e.getX() - shape.getPosition().x;
                        y1 = e.getY() - shape.getPosition().y;
                        x2 = (int) (shape.getProperties().get("x") - e.getX());
                        y2 = (int) (shape.getProperties().get("y") - e.getY());

                        try{
                            temp = (Shape) shape.clone();
                        } catch (Exception e1) {
                            System.out.println("error e1");

                        }
                    } else Clear();

                } else {

                    if (e.getX() >= shape.getProperties().get("x") && e.getY() >= shape.getProperties().get("y") && e.getX() <= shape.getProperties().get("x") +15&& e.getY() <= shape.getProperties().get("y") + 15) reSize=true;

                    else if (e.getX()>shape.getPosition().x && e.getY()>shape.getPosition().y && e.getY() < shape.getProperties().get("y") && e.getX() < shape.getProperties().get("x")) {

                        move=true;
                        x1 = e.getX()-shape.getPosition().x;
                        y1 = e.getY()-shape.getPosition().y;
                        x2 = (int)(shape.getProperties().get("x")-e.getX());
                        y2 = (int)(shape.getProperties().get("y")-e.getY());

                        try{
                            temp = (Shape) shape.clone();
                        } catch (Exception e1) {
                            System.out.println("error e1");

                        }
                    } else Clear();

                }


            }

        } else Clear();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (shapeToDraw != null && (e.getY()!=point.y || e.getX() != point.x)) {
            try{
                drawingEngine.addShape((Shape)shapeToDraw.clone()); shapeToDraw=null;
            } catch (Exception e4){

                System.out.println("error e4");
            }



        } else if (reSize == true) {

            drawingEngine.updateShape(shape,temp); shape=null; temp=null;

        } else if (move == true && (shape.getPosition().x != temp.getPosition().x  || shape.getPosition().y != temp.getPosition().y)) {

            drawingEngine.updateShape(shape,temp); shape=null; temp=null;

        } else {

            Clear();

        }
        reSize=false;
        isSelect=false; move=false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (shapeToDraw != null) {
            String str = shapeToDraw.getClass().getName();
            if (str ==  "eg.edu.alexu.csd.oop.draw.shapes.Circle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Line" || str == "eg.edu.alexu.csd.oop.draw.shapes.Square" || str == "eg.edu.alexu.csd.oop.draw.shapes.Rectangle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Triangle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Ellipse") {

                Clear();
                properties = new HashMap<>();
                properties.put("x",(double)e.getX());
                properties.put("y",(double)e.getY());
                shapeToDraw.setProperties(properties);
                shapeToDraw.draw(pen); repaint();

            } else {
                Clear();
                properties = new HashMap<>();
                properties.put("Width", (double)(Math.abs(point.x-e.getX())));
                properties.put("Length", (double)(Math.abs(point.y-e.getY())));
                properties.put("ArcWidth",(double)(Math.abs(point.x-e.getX()))/3);
                properties.put("ArcLength", (double)(Math.abs(point.y-e.getY()))/3);
                shapeToDraw.setProperties(properties);
                shapeToDraw.draw(pen); repaint();
            }

        } else if (isSelect && reSize) {

            shapes= drawingEngine.getShapes();

            pen.setColor(Color.WHITE);
            pen.fillRect(0,0,2000,2000);

            for (int i = 0; i < shapes.length; i++) {

                if (shapes[i] == shape) {

                    try {
                        temp = (Shape) shape.clone();
                    } catch (Exception e1) {

                        System.out.println("Error e1");

                    }
                    String str = shape.getClass().getName();
                    if (str ==  "eg.edu.alexu.csd.oop.draw.shapes.Circle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Line" || str == "eg.edu.alexu.csd.oop.draw.shapes.Square" || str == "eg.edu.alexu.csd.oop.draw.shapes.Rectangle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Triangle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Ellipse") {
                        properties = new HashMap<>();
                        properties.put("x", (double) e.getX());
                        properties.put("y", (double) e.getY());
                        temp.setProperties(properties);
                        temp.draw(pen);
                        repaint();
                    } else {

                        properties = new HashMap<>();
                        properties.put("Width", (double)(Math.abs(temp.getPosition().x-e.getX())));
                        properties.put("Length", (double)(Math.abs(temp.getPosition().y-e.getY())));
                        properties.put("ArcWidth",(double)(Math.abs(temp.getPosition().x-e.getX()))/3);
                        properties.put("ArcLength", (double)(Math.abs(temp.getPosition().y-e.getY()))/3);
                        temp.setProperties(properties);
                        temp.draw(pen);
                        repaint();

                    }
                } else shapes[i].draw(pen);

            }

        } else if (isSelect && move) {

            shapes= drawingEngine.getShapes();

            pen.setColor(Color.WHITE);
            pen.fillRect(0,0,2000,2000);

            for (int i = 0; i < shapes.length; i++) {

                if (shapes[i] != shape) {

                    shapes[i].draw(pen);

                }

            }
            try {
                temp = (Shape) shape.clone();
            } catch (Exception e1) {

                System.out.println("Error e1");

            }
            String str = shape.getClass().getName();
            if (str ==  "eg.edu.alexu.csd.oop.draw.shapes.Circle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Line" || str == "eg.edu.alexu.csd.oop.draw.shapes.Square" || str == "eg.edu.alexu.csd.oop.draw.shapes.Rectangle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Triangle" || str == "eg.edu.alexu.csd.oop.draw.shapes.Ellipse") {
                Point p = new Point(e.getX() - x1, e.getY() - y1);
                temp.setPosition(p);
                properties = new HashMap<>();
                properties.put("x", (double) e.getX() + x2);
                properties.put("y", (double) e.getY() + y2);
                temp.setProperties(properties);
                temp.draw(pen);
                repaint();
            } else {

                Point p = new Point(e.getX() - x1, e.getY() - y1);
                temp.setPosition(p);
                temp.draw(pen);
                repaint();

            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {


    }

    public Shape Search(int x, int y) { // find the shape who i pressed on it or return null if i don't find the shape;

        shapes = drawingEngine.getShapes();

        for (int i = shapes.length-1; i >= 0; i--) {

            Map <String,Double> prop = shapes[i].getProperties();
            Point p =  shapes[i].getPosition(); String str = shapes[i].getClass().getName();
            if (str != "eg.edu.alexu.csd.oop.draw.shapes.Line" && str != "eg.edu.alexu.csd.oop.draw.shapes.Rectangle" && str != "eg.edu.alexu.csd.oop.draw.shapes.Triangle" && str != "eg.edu.alexu.csd.oop.draw.shapes.Ellipse" && str != "eg.edu.alexu.csd.oop.draw.shapes.Circle" && str != "eg.edu.alexu.csd.oop.draw.shapes.Square") {

                Integer x2 = Math.toIntExact((long)Math.floor(prop.get("Width")));
                Integer y2 = Math.toIntExact((long)Math.floor(prop.get("Length")));
                if (x>=p.x && y>=p.y && p.x+x2>=x && p.y+y2>=y) {

                    xline=(int)(p.x+x2); yline=(int)(p.y+y2); ignore=true;
                    return shapes[i];

                }
                continue;
            }
            Integer x2 = Math.toIntExact((long)Math.floor(prop.get("x")));
            Integer y2 = Math.toIntExact((long)Math.floor(prop.get("y")));

            if (str == "eg.edu.alexu.csd.oop.draw.shapes.Line") {

                xline=(int)p.getX(); yline=(int)p.getY();
                x2line=x2; y2line=y2;
                if (x2line < xline) {

                    int temp = x2line;
                    x2line=xline;
                    xline=temp;

                }
                if (y2line < yline) {

                    int temp = y2line;
                    y2line=yline;
                    yline=temp;

                }

                if (x>=xline-15 && y>=yline-15 && y<=y2line+15 && x<=x2line+15) {

                    return shapes[i];

                }

            }
            else if (CircleSquare(shapes[i]) == true)
            {

                if (x>=p.x && y>=p.y && x<=x2 && y<=p.y+(x2-p.x)) {

                    return shapes[i];

                }

            } else {

                if (x>=p.x && y>=p.y && y <= y2 && x <= x2) {

                    return shapes[i];

                }

            }

        }

        return null;
    }

    public void Clear(){

        pen.setColor(Color.WHITE);
        pen.fillRect(0,0,2000,2000);
        drawingEngine.refresh(pen); repaint();

    }

    public void delete() {

        if (isSelect) {

            drawingEngine.removeShape(shape);
            Clear();
            repaint(); isSelect=false;

        }

    }

    private boolean CircleSquare(Shape x) {

        return x.getClass().getName() == "eg.edu.alexu.csd.oop.draw.shapes.Circle" || x.getClass().getName() == "eg.edu.alexu.csd.oop.draw.shapes.Square";

    }

    public void saveCurrent(String path)
    {
        drawingEngine.save(path);
    }

    public void loadCurrent(String path) throws IOException {
        drawingEngine.load(path); Clear();
    }

    public void undo() {

        drawingEngine.undo(); Clear();

    }

    public void redo() {

        drawingEngine.redo(); Clear();

    }
    public void supported(Class<? extends Shape> c) {
        drawingEngine.putSupportedShape(c);
    }


    /*
clear() :void
delete() :void
undo() :void
redo() :void
search(int  , int ) :Shape
circleSquare(Shape X): boolean
     */

}



