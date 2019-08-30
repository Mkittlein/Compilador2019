package Kittlein_Gallo;

import javax.swing.*;
import java.io.*;


public class Main {
    public static void setInterface() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            // handle exception
        } catch (ClassNotFoundException e) {
            // handle exception
        } catch (InstantiationException e) {
            // handle exception
        } catch (IllegalAccessException e) {
            // handle exception
        }
    }


    public static void setOut() throws FileNotFoundException {
        // Creating a File object that represents the disk file.
        PrintStream o = new PrintStream(new File("LOG.txt"));

        // Store current System.out before assigning a new value
        PrintStream console = System.out;
        System.setOut(o);
        // Assign o to out
    }
    public static void main(String[] args) throws IOException{
        setInterface();
        setOut();
        JFileChooser file = new JFileChooser(".\\");
        file.setDialogTitle("Elegir Codigo a cargar");
        int r = file.showOpenDialog(null);
        File f = file.getSelectedFile();
        if (f!=null){
            Gui gui= new Gui(f);
            gui.setTitle("Trabajo Compiladores Kittlein-Gallo");
        }
    }
}
