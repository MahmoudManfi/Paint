package eg.edu.alexu.csd.oop.draw.datamodel;

import eg.edu.alexu.csd.oop.draw.Engine;
import eg.edu.alexu.csd.oop.draw.MyShape;
import eg.edu.alexu.csd.oop.draw.Shape;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XML {

    public void save(String path, Shape[] arr) throws ParserConfigurationException, TransformerException, FileNotFoundException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElement("shapes");

        for (Shape shape : arr) {
            System.out.println(shape.getClass().getSimpleName());
            Element currentShape = document.createElement(shape.getClass().getSimpleName());


            String PositionX = shape.getPosition() == null ? "0": String.valueOf(shape.getPosition().getX()) ;
            String PositionY = shape.getPosition() == null ? "0": String.valueOf(shape.getPosition().getY()) ;
            String Color = shape.getColor() == null ?  String.valueOf(java.awt.Color.BLACK.getRGB()) :String.valueOf(shape.getColor().getRGB());
            String FillColor = shape.getFillColor() == null ? String.valueOf(java.awt.Color.WHITE.getRGB()) :String.valueOf(shape.getFillColor().getRGB());

            currentShape.setAttribute("PositionX",PositionX);
            currentShape.setAttribute("PositionY", PositionY);
            currentShape.setAttribute("Color",Color);
            currentShape.setAttribute("FillColor", FillColor);
            Map<String, Double> properties = shape.getProperties();
            if (properties != null) {
                for (Map.Entry<String, Double> prop : shape.getProperties().entrySet()) {
                    Element property = document.createElement(prop.getKey());
                    property.appendChild(document.createTextNode(prop.getValue().toString()));
                    currentShape.appendChild(property);
                }
            }
            root.appendChild(currentShape);

//            Transformer tr = TransformerFactory.newInstance().newTransformer();
//            tr.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
//            tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(path)));


        }
        document.appendChild(root);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
//        transformer.transform(new DOMSource(document) , new StreamResult(new FileOutputStream(path)));
        DOMSource source = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(path));
        transformer.transform(source,streamResult);


    }

    public Shape[] load (String Path , List<Class<? extends Shape>> SupportedShapes) throws ParserConfigurationException, IOException, SAXException {
        Shape[] arr ; //  This will be the array we would return

        File xmlFile = new File(Path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        Element root = document.getDocumentElement();
        NodeList shapesNodes = root.getChildNodes(); //
        arr = new Shape[shapesNodes.getLength()];
        for (int i = 0; i <shapesNodes.getLength() ; i++) {
            Shape currentShape = null; //This
            Node currentShapeNode = shapesNodes.item(i);
//            System.out.println(currentShapeNode);
            String classTarget = currentShapeNode.getNodeName();

            /** We try to make an instance of the right shape */
            for( Class<? extends Shape> j : SupportedShapes)
            {
//                System.out.println(j.getName());
//                System.out.println(j.getSimpleName());
//                System.out.println(j.getClass().getSimpleName());

               if(j.getSimpleName().equals(classTarget)) {
                   try {
                       currentShape = j.newInstance();
                   } catch (InstantiationException e) {
                       e.printStackTrace();
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   }
                   break; // because we found the class
               }
            }
            /**Give properties to the shape */
            if(currentShape !=null)
            {
                Element e = ((Element)currentShapeNode);
                currentShape.setColor(new Color(Integer.parseInt(e.getAttribute("Color"))));
                System.out.println("LOL "+" " + currentShape.getClass().getSimpleName());
                currentShape.setFillColor(new Color(Integer.parseInt(e.getAttribute("FillColor"))));
                currentShape.setPosition(new Point(Double.valueOf(e.getAttribute("PositionX")).intValue() , Double.valueOf(e.getAttribute("PositionY")).intValue()));
                Map<String, Double> prop = new HashMap<>();
                NodeList propNodes = currentShapeNode.getChildNodes();
                for (int j = 0; j < propNodes.getLength(); j++) {
                    Node probNode = propNodes.item(j);
                    Element probElement = (Element) probNode;
                    prop.put(probElement.getNodeName(), Double.valueOf(probElement.getTextContent()));
                }
                currentShape.setProperties(prop);

            }
//            // test
//            System.out.println("Shape Name is : "+ currentShape.getClass().getSimpleName());
////            System.out.println("Shape color is  " +currentShape.getColor() );
////            System.out.println("Shape fill color is  " +currentShape.getFillColor() );
////            System.out.println("Shape place  is  " +currentShape.getPosition() );
////
////            for(Map.Entry<String, Double> testProp :currentShape.getProperties().entrySet())
////            {
////                System.out.print(testProp.getKey()+": ");
////                System.out.println(testProp.getValue());
////
////            }
////            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
////            //-------------------------------
            arr[i] = currentShape;
        }

        return arr;
    }
}
