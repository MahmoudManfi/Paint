package eg.edu.alexu.csd.oop.draw.datamodel;

import eg.edu.alexu.csd.oop.draw.MyShape;
import eg.edu.alexu.csd.oop.draw.Shape;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSON {

    public void save(String path, Shape[] arr) throws IOException {
        FileWriter pw = new FileWriter(path);
        String test = "";
        pw.write('[');
        int l = arr.length;
        for (Shape i : arr) {
            String PositionX = i.getPosition() == null ? "0" : String.valueOf(i.getPosition().getX());
            String PositionY = i.getPosition() == null ? "0" : String.valueOf(i.getPosition().getY());
            String Color = i.getColor() == null ? String.valueOf(java.awt.Color.BLACK.getRGB()) : String.valueOf(i.getColor().getRGB());
            String FillColor = i.getFillColor() == null ? String.valueOf(java.awt.Color.BLACK.getRGB()) : String.valueOf(i.getFillColor().getRGB());
            pw.write('{');
            pw.write("\"Name\":" + "\"" + i.getClass().getSimpleName() + "\",");
            pw.write("\"PositionX\":" + "\"" + PositionX + "\",");
            pw.write("\"PositionY\":" + "\"" + PositionY + "\",");
            pw.write("\"Color\":" + "\"" + Color + "\",");
            pw.write("\"FillColor\":" + "\"" + FillColor + "\",");
            pw.write("\"Properties\":{");

            if(i.getProperties() ==null)
            {
                Map<String , Double> dummy = new HashMap<>();
                dummy.put("x",0.0);
                dummy.put("y",0.0);
                i.setProperties(dummy);
            }
            int k = i.getProperties().size();
            for (Map.Entry<String, Double> j : i.getProperties().entrySet()) {
                //(String.valueOf(j.getKey()), String.valueOf(j.getValue()));                // "":""
                pw.write("\"" + j.getKey() + "\":" + "\"" + String.valueOf(j.getValue()) + "\"");
                if (--k > 0) {
                    pw.write(",");
                }

            }
            pw.write("}");

            pw.write("}");
            if (--l > 0)
                pw.write(",\n");


        }
        pw.write(']');
        pw.flush();
        // Test for the method
//        BufferedReader br = new BufferedReader(new FileReader(path));
//        System.out.println(br.lines());
//        while (br.ready())
//        {
//            System.out.println(br.readLine());
//        }
    }

    public Shape[] load(String path, List<Class<? extends Shape>> SupportedShapes) throws IOException {

        Shape[] arr = new Shape[countLinesInFile(path)];
        int shapesArrayPtr = 0;
        BufferedReader br = new BufferedReader(new FileReader(path));
        Pattern patt = Pattern.compile("\"([^\"]*)\"");
        while (br.ready()) {
            String s = br.readLine();
            ArrayList<String> names = new ArrayList<>();
            Matcher m = patt.matcher(s);
            while (m.find()) {
                String ss = m.group(0);
                names.add(ss.replace("\"", ""));
            }
            Shape currentShape = new MyShape();

            for (Class<? extends Shape> j : SupportedShapes) {
                if (j.getSimpleName().equals(names.get(1))) {
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


            currentShape.setPosition(new Point(Double.valueOf(names.get(3)).intValue(), Double.valueOf(names.get(5)).intValue()));
            currentShape.setColor(new Color(Integer.parseInt(names.get(7))));
            currentShape.setFillColor(new Color(Integer.parseInt(names.get(9))));

            Map<String, Double> prop = new HashMap<>();

            for (int i = 10; i < names.size() - 1; i += 2) {
                prop.put(names.get(i + 1), Double.parseDouble(names.get(i + 2)));
            }
            currentShape.setProperties(prop);
            arr[shapesArrayPtr++] = currentShape;


//            // Test Code
//            System.out.println("Shape Name is : " + currentShape.getClass().getSimpleName());
//            System.out.println("Shape color is  " + currentShape.getColor());
//            System.out.println("Shape fill color is  " + currentShape.getFillColor());
//            System.out.println("Shape place  is  " + currentShape.getPosition());
//
//            for (Map.Entry<String, Double> testProp : currentShape.getProperties().entrySet()) {
//                System.out.print(testProp.getKey() + ": ");
//                System.out.println(testProp.getValue());
//
//            }
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");


        }

//        System.out.println("Here We Go");
//        for(String i : names)
//        {
//            System.out.println(i);
//        }
//
        return arr;
    }

    private int countLinesInFile(String path) throws IOException {
        int k = 0;
        BufferedReader br = new BufferedReader(new FileReader(path));
        while (br.ready()) {
            br.readLine();
            k++;
        }
        return k;
    }
}
