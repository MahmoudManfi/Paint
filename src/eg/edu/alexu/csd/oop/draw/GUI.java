package eg.edu.alexu.csd.oop.draw;

import eg.edu.alexu.csd.oop.draw.shapes.*;
import eg.edu.alexu.csd.oop.draw.shapes.Rectangle;
import eg.edu.alexu.csd.oop.test.ReflectionHelper;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;


public class GUI {
    private JMenu fileMenu, editMenu, helpMenu, save;
    private JMenuItem load, fxml, json, about, userguide, undo, redo;
    private DrawArea drawArea;
    private JButton circleBtn, rectangeBtn, EllipseBtn, lineBtn, squareBtn, triangleBtn;
    private Color color = Color.BLACK, fillColor = Color.WHITE;
    private static Shape shape;
    private JButton bSelect, bRemove, bUndo, bRedo, bColor, bFillColor;
    private JPanel box;
    private JFrame f;
    private static boolean select = false;

    GUI() {
        f = new JFrame("Vector Paint");

        drawArea = new DrawArea();
        JMenuBar mb = makeMenuBar();

        Box box = makeLeftPanel();
        JPanel box1 = ToolBar();

        Container container = new Container();
        container.setLayout(new BorderLayout());
        container.add(box, BorderLayout.WEST);
        container.add(box1, BorderLayout.NORTH);
        container.add(drawArea, BorderLayout.CENTER);
        f.add(container);
        f.setJMenuBar(mb);
//        f.setLayout(null);
        f.setSize(1000, 700);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    JMenuBar makeMenuBar() {
        JMenuBar mb = new JMenuBar();

        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        helpMenu = new JMenu("Help");
        save = new JMenu("Save");
        load = new JMenuItem("Load");
        load.addActionListener(loadActionListener);
        fxml = new JMenuItem("Fxml");
        fxml.addActionListener(saveActionListener);
        json = new JMenuItem("Json");
        json.addActionListener(saveActionListener);

        about = new JMenuItem("About");
        userguide = new JMenuItem("UserGuide");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");

        save.add(fxml);
        save.add(json);
        fileMenu.add(save);
        fileMenu.add(load);
        editMenu.add(undo);
        editMenu.add(redo);
        helpMenu.add(userguide);
        helpMenu.add(about);

        mb.add(fileMenu);
        mb.add(editMenu);
        mb.add(helpMenu);
        return mb;
    }

    Box makeLeftPanel() {
        final int iconWidth = 30;
        final int iconHeight = 30;
        final Color backGroundIconColor = Color.WHITE;

        /*Box acts as a container to hold shapes buttons and to put it at the left of the screen */
        Box box = Box.createVerticalBox(); // Vertical box to put the btns vertically
        box.setOpaque(true); // A Box is by default a container to hold other components, so by default it is transparent. we need to make it opaque to change its background
        box.setBackground(Color.WHITE); // Change backGround of the Layout

        /*Here we make icons for all shapes that we have*/

        ImageIcon circleIcon = new ImageIcon("shapesIcons/Circle.png");
        circleIcon = new ImageIcon(circleIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));

        ImageIcon rectangleIcon = new ImageIcon("shapesIcons/Rectangle.png");
        rectangleIcon = new ImageIcon(rectangleIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));

        ImageIcon ellipseIcon = new ImageIcon("shapesIcons/Ellipse.png");
        ellipseIcon = new ImageIcon(ellipseIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));

        ImageIcon lineIcon = new ImageIcon("shapesIcons/Line.png");
        lineIcon = new ImageIcon(lineIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));


        ImageIcon squareIcon = new ImageIcon("shapesIcons/Square.png");
        squareIcon = new ImageIcon(squareIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));

        ImageIcon traingleIcon = new ImageIcon("shapesIcons/Triangle.png");
        traingleIcon = new ImageIcon(traingleIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));

        ImageIcon markIcon = new ImageIcon("shapesIcons/Mark.png");
        markIcon = new ImageIcon(markIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_DEFAULT));

        /*Make the buttons that we will put in the box layout and connect them with the icons we have just made above */

        JButton circleBtn = new JButton(circleIcon);
        circleBtn.setName("Circle");
        circleBtn.addActionListener(actionListener);
        circleBtn.setBackground(backGroundIconColor);
        circleBtn.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4));


        JButton rectangeBtn = new JButton(rectangleIcon);
        rectangeBtn.setName("Rectangle");
        rectangeBtn.addActionListener(actionListener);
        rectangeBtn.setBackground(backGroundIconColor);
        rectangeBtn.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4));


        JButton EllipseBtn = new JButton(ellipseIcon);
        EllipseBtn.setName("Ellipse");
        EllipseBtn.addActionListener(actionListener);
        EllipseBtn.setBackground(backGroundIconColor);
        EllipseBtn.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4));


        JButton lineBtn = new JButton(lineIcon);
        lineBtn.setName("Line");
        lineBtn.addActionListener(actionListener);
        lineBtn.setBackground(backGroundIconColor);
        lineBtn.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4));


        JButton squareBtn = new JButton(squareIcon);
        squareBtn.setName("Square");
        squareBtn.addActionListener(actionListener);
        squareBtn.setBackground(backGroundIconColor);
        squareBtn.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4));


        JButton triangleBtn = new JButton(traingleIcon);
        triangleBtn.setName("Triangle");
        triangleBtn.addActionListener(actionListener);
        triangleBtn.setBackground(backGroundIconColor);
        triangleBtn.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4));

        JButton markBtn = new JButton(markIcon);
        markBtn.setName("Mark");
        markBtn.addActionListener(actionListener);
        markBtn.setBackground(backGroundIconColor);
        markBtn.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 4));


        /** Add the buttons to the box  */
        box.add(Box.createHorizontalStrut(1));
        box.add(circleBtn);
        box.add(Box.createHorizontalStrut(1));
        box.add(rectangeBtn);
        box.add(Box.createHorizontalStrut(1));
        box.add(EllipseBtn);
        box.add(Box.createHorizontalStrut(1));
        box.add(lineBtn);
        box.add(Box.createHorizontalStrut(1));
        box.add(squareBtn);
        box.add(Box.createHorizontalStrut(1));
        box.add(triangleBtn);
        box.add(Box.createHorizontalStrut(1));
        box.add(markBtn);
        box.add(Box.createHorizontalStrut(1));

        return box;

    }

    JPanel ToolBar() {

        box = new JPanel(); // creat a container to put the button in it
        GridLayout layout = new GridLayout();
        box.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        box.setOpaque(true); // A Box is by default a container to hold other components, so by default it is transparent. we need to make it opaque to change its background

        ImageIcon undoIcon = new ImageIcon("shapesIcons/undo.png");
        undoIcon = new ImageIcon(undoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        ImageIcon redoIcon = new ImageIcon("shapesIcons/Redo.png");
        redoIcon = new ImageIcon(redoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        ImageIcon colorIcon = new ImageIcon("shapesIcons/color20.png");
        colorIcon = new ImageIcon(colorIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        ImageIcon fColorIcon = new ImageIcon("shapesIcons/editColor.png");
        fColorIcon = new ImageIcon(fColorIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        ImageIcon removeIcon = new ImageIcon("shapesIcons/remove.png");
        removeIcon = new ImageIcon(removeIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

        /* here make buttons and put it in the toolbar */

        bUndo = new JButton(undoIcon);
        bUndo.addActionListener(actionListener);
        bUndo.setBackground(Color.white);

        bRedo = new JButton(redoIcon);
        bRedo.addActionListener(actionListener);
        bRedo.setBackground(Color.WHITE);

        bColor = new JButton(colorIcon);
        bColor.addActionListener(actionListener);
        bColor.setBackground(color);

        bFillColor = new JButton(fColorIcon);
        bFillColor.addActionListener(actionListener);
        bFillColor.setBackground(fillColor);

        bRemove = new JButton(removeIcon);
        bRemove.setBackground(Color.white);
        bRemove.addActionListener(actionListener);

        bSelect = new JButton("Select");
        bSelect.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        bSelect.addActionListener(actionListener);
        bSelect.setBackground(Color.white);
        /* add the objects in the box*/

        box.add(bUndo);
        box.add(bRedo);
        box.add(bColor);
        box.add(bFillColor);
        box.add(bSelect);
        box.add(bRemove);
        bRemove.setVisible(false);
        bSelect.setVisible(false);
        return box;
    }

    public static void main(String[] args) {
        //List<Class<?>> SupportedShapes = ReflectionHelper.findClassesImpmenenting(Shape.class , Shape.class.getPackage());
        new GUI();
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String shapeName = ((JButton) e.getSource()).getName();

            if (e.getSource() == bColor) {

                color = JColorChooser.showDialog(null, "Pick Color", color);
                if (color == null) {
                    color = Color.BLACK;
                }
                bColor.setBackground(color);
                select=false; shape=null;

            } else if (e.getSource() == bFillColor) {

                fillColor = JColorChooser.showDialog(null, "Pick Fill Color", fillColor);
                if (fillColor == null) {
                    fillColor = Color.WHITE;
                }
                bFillColor.setBackground(fillColor);
                select=false; shape = null;

            } else if (e.getSource() == bUndo) {

                drawArea.undo();
                bSelect.setVisible(true);
                select = false;
                bRemove.setVisible(false); shape=null;

            } else if (e.getSource() == bRedo) {

                drawArea.redo();
                bSelect.setVisible(true);
                select = false;
                bRemove.setVisible(false); shape=null;

            } else if (e.getSource() == bRemove) {

                bSelect.setVisible(true);
                select = false;
                bRemove.setVisible(false);
                shape = null;

                drawArea.delete();

            } else if (e.getSource() == bSelect) {

                bSelect.setVisible(false);
                bRemove.setVisible(true);
                select = true;
                shape = null;

            } else if (shapeName.equals("Circle")) {

                shape = new Circle();
                work();

            } else if (shapeName.equals("Ellipse")) {

                shape = new Ellipse();
                work();

            } else if (shapeName.equals("Rectangle")) {

                shape = new Rectangle();
                work();

            } else if (shapeName.equals("Square")) {

                shape = new Square();
                work();

            } else if (shapeName.equals("Line")) {

                shape = new Line();
                work();

            } else if (shapeName.equals("Triangle")) {

                shape = new Triangle();
                work();

            } else if (shapeName.equals("Mark")) {

                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a jar file to Load");
                int userSelection = fileChooser.showOpenDialog(parentFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToLoad = fileChooser.getSelectedFile();
                    String fileName = fileToLoad.getName();
                    String fileExtention = fileName.substring(fileName.lastIndexOf('.'));
                    if(fileExtention.equalsIgnoreCase(".jar")) {
                        try {
                            Class<? extends Shape> c = (Class<? extends Shape>) Class.forName("eg.edu.alexu.csd.oop.draw." + fileName.substring(0,fileName.lastIndexOf('.')));
                                shape = (Shape) c.newInstance(); work();
                                drawArea.supported(shape.getClass());
                        } catch (Exception ex) {
                            System.out.println("Error");
                        }
                    }else
                    {
                        JOptionPane.showMessageDialog(new Panel(), "Error Type Format", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }

            }
        }
    };


    static Shape getShape() {
        return shape;
    }

    private void work() { // to save time when the user click on shape this should run

        shape.setColor(color);
        shape.setFillColor(fillColor);
        bSelect.setVisible(true);
        select = false;
        bRemove.setVisible(false);
        drawArea.Clear();

    }

    public static boolean getSelect() {
        return select;
    }

    ActionListener saveActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
//            drawArea.saveCurrent("C:\\Users\\hamzahassan\\Desktop\\sample.xml");
            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");

            int userSelection = fileChooser.showSaveDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToLoad = fileChooser.getSelectedFile();
                String fileName = fileToLoad.getName();
                String fileExtention = fileName.substring(fileName.lastIndexOf('.'));
                if(fileExtention.equalsIgnoreCase(".xml") && e.getSource() == fxml)
                {
                    //System.out.println("User choosed a fxml file ");
                    drawArea.saveCurrent(fileToLoad.getAbsolutePath());
                }else if(fileExtention.equalsIgnoreCase(".json") && e.getSource() == json)
                {
                   // System.out.println("The User chose a non json file ");
                    drawArea.saveCurrent(fileToLoad.getAbsolutePath());
                }else
                {
                    JOptionPane.showMessageDialog(new Panel(), "Error Type Format", "Error", JOptionPane.ERROR_MESSAGE);
                }



            }
        }
    };
    ActionListener loadActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame parentFrame = new JFrame();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to Load");
            int userSelection = fileChooser.showOpenDialog(parentFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String fileName = fileToSave.getName();
                String fileExtention = fileName.substring(fileName.lastIndexOf('.'));
                if((fileExtention.equalsIgnoreCase(".xml")||fileExtention.equalsIgnoreCase(".json")))
                {
                    try {
                        drawArea.loadCurrent(fileToSave.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }else
                {
                    JOptionPane.showMessageDialog(new Panel(), "Error Type Format", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }



        }
    };


}
