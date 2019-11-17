package Kittlein_Gallo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Gui extends JFrame implements ActionListener {
    GeneradorDeCodigo GC;
    private JTextArea textTokens, textCode, textRepIntermedia,textWarnings;
    private int n=0;
    TablaSimbolos TS;
    private JButton botonGet;
    private JButton botonSave;
    private JButton botonTS;
    private File f;
    private JScrollPane scrollTokens, scrollCode, scrollRepIntermedia, scrollWarnings;
    private AnalizadorLexico AL;
    private Parser parser;
    private StringBuilder tokens, warnings;


    public void addMensaje(String M) {
        warnings.append("\n" + M);
        textWarnings.setText(warnings.toString());
    }

    public void addToken(String M) {
        tokens.append("\n" + M);
        textTokens.setText(tokens.toString());
    }

    public void setColorError(int nivelError){
        textWarnings.setOpaque(true);
       if (this.n<nivelError) {
           this.n=nivelError;
           if (n == 1){
               textWarnings.setBackground(Color.GREEN);

           }
           if (n == 2){
               textWarnings.setBackground(Color.YELLOW);

           }
           if (n == 3){
               textWarnings.setBackground(Color.RED);

           }
       }
    }

    Gui(File Cod) throws IOException {
        GC = new GeneradorDeCodigo();
        TS=new TablaSimbolos();
        f = Cod;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        AL= new AnalizadorLexico(Cod,this,TS);
        parser = new Parser(AL);
        byte[] encoded = Files.readAllBytes(f.toPath());
        String Codigo = new String(encoded, StandardCharsets.UTF_8);
        tokens = new StringBuilder("Tokens: ");
        warnings = new StringBuilder("Warnings: ");
        textRepIntermedia = new JTextArea();
        textTokens = new JTextArea();
        textWarnings = new JTextArea();
        scrollRepIntermedia = new JScrollPane(textRepIntermedia);
        scrollTokens = new JScrollPane(textTokens);
        scrollWarnings= new JScrollPane(textWarnings);
        textCode = new JTextArea();
        scrollCode = new JScrollPane(textCode);
        TextLineNumber tln = new TextLineNumber(textCode);
        tln.setBorderGap(5);
        scrollCode.setRowHeaderView(tln);
        textRepIntermedia.setText("Polaca Inversa:");
        textCode.setText(Codigo);
        textTokens.setEditable(false);
        scrollTokens.setAutoscrolls(false);
        scrollWarnings.setAutoscrolls(false);
        JButton botonGetAll = new JButton("COMPILAR");
        botonTS = new JButton("Mostrar Tabla de Simbolos");
        botonSave = new JButton("Save");
        JPanel outs = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty=0.75;
        c.weightx=1;
        c.fill=c.BOTH;
        c.gridy=0;
        c.gridx=0;
        outs.add(scrollTokens,c);
        c.weighty=0.25;
        c.fill=c.BOTH;
        c.gridy=1;
        c.gridx=0;
        outs.add(scrollWarnings,c);
        JPanel botones = new JPanel(new GridLayout(1, 2));
        JPanel texto = new JPanel(new GridLayout(1, 3));
        botones.setSize(new Dimension(-1, 30));
        botonSave.addActionListener(this);
        botonGetAll.addActionListener(this);
        botonTS.addActionListener(this);
        textTokens.setText(tokens.toString());
        textWarnings.setText(warnings.toString());
        botones.add(botonTS, BorderLayout.WEST);
        botones.add(botonSave, BorderLayout.CENTER);
        botones.add(botonGetAll, BorderLayout.EAST);
        add(texto, BorderLayout.CENTER);
        texto.add(outs);
        texto.add(scrollCode);
        texto.add(scrollRepIntermedia);
        add(botones, BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(800, 600));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);

    }



    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("COMPILAR")) {
            System.out.println("inicio compilación");
            parser.run();
            int aux=0;
            this.setColorError(1);
            if (n!=3) {
                if(n==1)
                    textWarnings.setText("Codigo compilado sin warnings ni errores");
                GC.generarCodigo(AL.getPolaca(),f.getName(),TS.getTablaDeSimbolos());
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
            textTokens.setText(TS.toString());
        }
        if (e.getActionCommand().equals("Ver Tokens")) {
            botonTS.setText("Mostrar Tabla de Simbolos");
            textTokens.setText(tokens.toString());
        }
    }

    public void updatePolaca() {
        textRepIntermedia.setText(AL.getPolacaToString());
    }
}
