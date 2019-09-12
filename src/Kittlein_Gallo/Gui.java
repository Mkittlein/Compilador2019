package Kittlein_Gallo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;

public class Gui extends JFrame implements ActionListener {
    JTextArea textTokens, textCode, textPolaca;
    boolean error;
    //TablaSimbolos TS;
    JButton botonGet, botonSave, botonGetAll, botonTS;
    File f;
    JScrollPane scrollTokens, scrollCode, scrollPolaca;
    //AnalizadorLexico AL;
    //Parser parser;
    StringBuilder tokens;
    //GeneradorCodigo generadorCodigo;

    public void addMensaje(String M) {
        tokens.append("\n" + M);
        textTokens.setText(tokens.toString());
    }



    Gui(File Cod) throws IOException {

        error=false;
        f = Cod;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        byte[] encoded = Files.readAllBytes(f.toPath());
        String Codigo = new String(encoded);
        tokens = new StringBuilder("Tokens: ");
        textPolaca = new JTextArea();
        textTokens = new JTextArea();
        scrollPolaca = new JScrollPane(textPolaca);
        scrollTokens = new JScrollPane(textTokens);
        textCode = new JTextArea();
        scrollCode = new JScrollPane(textCode);
        textPolaca.setText("Polaca Inversa:");
        textCode.setText(Codigo);
        textTokens.setEditable(false);
        scrollTokens.setAutoscrolls(true);
        botonGet = new JButton("Get Token");
        botonGetAll = new JButton("COMPILAR");
        botonTS = new JButton("Mostrar Tabla de Simbolos");
        botonSave = new JButton("Save");
        JPanel botones = new JPanel(new GridLayout(1, 2));
        JPanel texto = new JPanel(new GridLayout(1, 2));
        botones.setSize(new Dimension(-1, 30));
        botonGet.addActionListener(this);
        botonSave.addActionListener(this);
        botonGetAll.addActionListener(this);
        botonTS.addActionListener(this);
        textTokens.setText(tokens.toString());
        botones.add(botonGet, BorderLayout.WEST);
        botones.add(botonTS, BorderLayout.CENTER);
        botones.add(botonGetAll, BorderLayout.CENTER);
        botones.add(botonSave, BorderLayout.EAST);
        add(texto, BorderLayout.CENTER);
        texto.add(scrollTokens);
        texto.add(scrollCode);
        texto.add(scrollPolaca);
        add(botones, BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(800, 600));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);

    }




    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Get Token")) {
            try {
                //Token t = AL.getToken();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        if(e.getActionCommand().equals("COMPILAR")) {
           // parser.run();
            if (!error) {
                //generadorCodigo.generarCodigo(AL.getPolaca(), f.getName(),TS.getTablaDeSimbolos());
            }
        }
        if(e.getActionCommand().equals("Save")) {
            Writer writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f.toString()), "utf-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                writer.write(textCode.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (e.getActionCommand().equals("Mostrar Tabla de Simbolos")) {
            botonTS.setText("Ver Tokens");
            //textTokens.setText(TS.toString());
        }
        if (e.getActionCommand().equals("Ver Tokens")) {
            botonTS.setText("Mostrar Tabla de Simbolos");
            textTokens.setText(tokens.toString());
        }
    }
}
