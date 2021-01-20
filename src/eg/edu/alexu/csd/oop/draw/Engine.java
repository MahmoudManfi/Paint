package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.draw.datamodel.JSON;
import eg.edu.alexu.csd.oop.draw.datamodel.XML;
import eg.edu.alexu.csd.oop.draw.shapes.*;
import eg.edu.alexu.csd.oop.draw.shapes.Rectangle;
import eg.edu.alexu.csd.oop.test.DummyShape;
import eg.edu.alexu.csd.oop.test.ReflectionHelper;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.DebugGraphics;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Engine implements DrawingEngine {

    private ArrayList<Shape> myShapes = new ArrayList<>();
    private LinkedList<Shape[] > boxUndo = new LinkedList<>(); // to store all the operation;
    private LinkedList<Shape[] > boxRedo = new LinkedList<>(); // to store all the operation;
    private List<Class<? extends Shape>> SupportedShapes = new ArrayList<>();

    Engine() {

        boxUndo.addLast(getShapes());
        SupportedShapes.add(Circle.class);
        SupportedShapes.add(Square.class);
        SupportedShapes.add(Rectangle.class);
        SupportedShapes.add(Triangle.class);
        SupportedShapes.add(Line.class);
        SupportedShapes.add(Ellipse.class);
        SupportedShapes.add(RoundRectangle.class);

    }

    @Override
    public void refresh(Graphics canvas) {

        Shape[] ArrayShapes = getShapes();

        for (int i = 0; i < ArrayShapes.length; i++) {

                ArrayShapes[i].draw(canvas);

        }

    }


    @Override
    public void addShape(Shape shape) {

        myShapes.add(shape);
        boxUndo.addLast(getShapes());
        if (boxUndo.size() == 22) boxUndo.removeFirst();

    }

    @Override
    public void removeShape(Shape shape) {

        int size = myShapes.size();
        myShapes.remove(shape);
        if (size == myShapes.size()) {

            for (int i = 0; i < myShapes.size(); i++) {

                if (myShapes.get(i).getColor() == shape.getColor() && myShapes.get(i).getFillColor() == shape.getFillColor())
                    if (myShapes.get(i).getPosition() == shape.getPosition())
                        if (myShapes.get(i).getProperties() == shape.getProperties())
                        {
                            myShapes.remove(myShapes.get(i)); break;
                        }

            }

        }
        boxUndo.addLast(getShapes());
        if (boxUndo.size() == 22) boxUndo.removeFirst();

    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {

        try{


            int size = myShapes.size();
            myShapes.remove(oldShape);
            if (size == myShapes.size()) {

                for (int i = 0; i < myShapes.size(); i++) {

                    if (myShapes.get(i).getColor() == oldShape.getColor() && myShapes.get(i).getFillColor() == oldShape.getFillColor())
                        if (myShapes.get(i).getPosition() == oldShape.getPosition())
                            if (myShapes.get(i).getProperties() == oldShape.getProperties()) {
                                myShapes.remove(myShapes.get(i)); break;
                            }

                }

            }

            myShapes.add((Shape) newShape.clone());

        } catch (Exception e) {
            System.out.println("Error");
        }

        boxUndo.addLast(getShapes());
        if (boxUndo.size() == 22) boxUndo.removeFirst();

    }

    @Override
    public Shape[] getShapes() {

        Shape[] ArrayShapes = new Shape[myShapes.size()];
        for (int i = 0; i < myShapes.size(); i++) {
            ArrayShapes[i]=myShapes.get(i);
        }
        return ArrayShapes;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {

        return SupportedShapes;
    }

    @Override
    public void undo() {

        if (boxUndo.size()>1) {
            boxRedo.addLast(boxUndo.getLast());
            boxUndo.removeLast();
            Shape[] ArrayShapes = boxUndo.getLast();
            myShapes=new ArrayList<>();
            for (int i = 0; i < ArrayShapes.length; i++) {

                myShapes.add(ArrayShapes[i]);

            }
        }

    }

    @Override
    public void redo() {

        if (boxRedo.size()!=0) {

            Shape[] ArrayShapes = boxRedo.getLast();
            boxUndo.addLast(ArrayShapes);
            myShapes=new ArrayList<>();
            for (int i = 0; i < ArrayShapes.length; i++) {

                myShapes.add(ArrayShapes[i]);

            }
            boxRedo.removeLast();

        }

    }

    @Override
    public void save(String path) {
        String extension = path.substring(path.lastIndexOf('.'));
       if(extension.equalsIgnoreCase(".xml")) {
           XML xml = new XML();
           try {

               xml.save(path, getShapes());
           } catch (TransformerException e) {
               e.printStackTrace();
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (ParserConfigurationException e) {
               e.printStackTrace();
           }
       }else
       {
           JSON json = new JSON();
           try {
               json.save(path, getShapes());
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

    @Override
    public void load(String path) throws IOException {
        Shape[]x = null;

        String extension = path.substring(path.lastIndexOf('.'));
        if (extension.equalsIgnoreCase(".xml")) {
            XML xml = new XML();
            try {
                 x = xml.load(path, getSupportedShapes());
//                for (Shape currentShape : x) {
//                    // test for el method bt el ws*a de
//                    System.out.println("Shape Name is : " + currentShape.getClass().getSimpleName());
//                    System.out.println("Shape color is  " + currentShape.getColor());
//                    System.out.println("Shape fill color is  " + currentShape.getFillColor());
//                    System.out.println("Shape place  is  " + currentShape.getPosition());
//
//                    for (Map.Entry<String, Double> testProp : currentShape.getProperties().entrySet()) {
//                        System.out.print(testProp.getKey() + ": ");
//                        System.out.println(testProp.getValue());
//
//                    }
//                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
//                    //-------------------------------
//                }
            } catch (FileNotFoundException e) {
                System.out.println("A7a2");
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                System.out.println("A7a3");
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
        else{
            JSON json = new JSON();
            try {
               x =  json.load(path , getSupportedShapes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        myShapes = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {

            myShapes.add(x[i]);

        }

        boxUndo.clear();
        boxUndo.add(getShapes());
        boxRedo.clear();

    }
    public  void putSupportedShape(Class<? extends Shape> c) {
        SupportedShapes.add(c);
    }
}
