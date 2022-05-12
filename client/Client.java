import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
 
public class Client extends JFrame implements ActionListener{

    private JMenuBar menu_bar;
    private JMenuItem menuItem_calendario, menuItem_cuenta, menuItem_fin;
    private JPanel panel_inicio,panel_calendario,panel_cuenta;

    public Client() {

        //Se crea el panel de inicio
        this.setLayout(null); //Layout absoluto
        panel_inicio = panelInicio();

        //Se crea el panel de cuenta
        //panel_cuenta = panelCuenta();

        //Se crea el panel de calendario
        //panel_calendario = panelCalendario();

        //Creamos la barra de menu de la ventana
        menu_bar=menuBar();

        //Asociamos los elementos al JFrame
        this.setJMenuBar(menu_bar);
        this.add(panel_inicio,BorderLayout.CENTER);

        //Configurar y mostrar JFrame
        initPantalla();
    }

    private void initPantalla() {

        this.setTitle("TotalAgenda"); //Título del JFrame
        this.setSize(800,550); //Dimensiones del JFrame (ancho,alto)
        this.setMinimumSize(new Dimension(800,550)); //(ancho,alto)
        this.setLocationRelativeTo(null); //Esto permite que la ventana aparezca al centro
        this.setResizable(false); //Redimensionable
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cerrar proceso al cerrar ventana
        this.setVisible(true); //Mostrar JFrame
    }

    /* Método que implementa las acciones de cada ítem de menú */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==menuItem_calendario) {
            setSize(640,480);
        }
        if (e.getSource()==menuItem_cuenta) {
            setSize(1024,768);
        }
        if (e.getSource()==menuItem_fin) {
            System.out.println("Se llama a finalizar la aplicación");
            System.exit(0);
        }
    }
    private JPanel panelInicio(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6,1,15,15)); // filas,columnas,pixeles entre filas,pixeles entre columnas

        JLabel label_correo = new JLabel("Correo");
        //label_correo.setMinimumSize(new Dimension(20, 5));
        //label_correo.setPreferredSize(new Dimension(20, 5));
        //label_correo.setMaximumSize(new Dimension(20, 5));
        //label_correo.setLocation(400,100);
        JTextField field_correo = new JTextField("correo",20);
        //label_correo.setMinimumSize(new Dimension(20, 5));
        //label_correo.setPreferredSize(new Dimension(20, 5));
        //label_correo.setMaximumSize(new Dimension(20, 5));
        //label_correo.setLocation(400,130);
        JLabel label_pass = new JLabel("Contraseña");
        //label_correo.setMinimumSize(new Dimension(20, 5));
        //label_correo.setPreferredSize(new Dimension(20, 5));
        //label_correo.setMaximumSize(new Dimension(20, 5));
        //label_correo.setLocation(400,160);        
        JTextField field_pass = new JTextField("contraseña",20);
        //label_correo.setMinimumSize(new Dimension(20, 5));
        //label_correo.setPreferredSize(new Dimension(20, 5));
        //label_correo.setMaximumSize(new Dimension(20, 5));
        //label_correo.setLocation(400,190);        
        JButton boton_login = new JButton("Inicar Sesión");
        //label_correo.setMinimumSize(new Dimension(20, 5));
        //label_correo.setPreferredSize(new Dimension(20, 5));
        //label_correo.setMaximumSize(new Dimension(20, 5));
        //label_correo.setLocation(400,220);        
        JButton boton_register = new JButton("Crear Cuenta");
        //label_correo.setMinimumSize(new Dimension(20, 5));
        //label_correo.setPreferredSize(new Dimension(20, 5));
        //label_correo.setMaximumSize(new Dimension(20, 5));
        //label_correo.setLocation(400,250);        

        panel.add(label_correo);
        panel.add(field_correo);
        panel.add(label_pass);
        panel.add(field_pass);
        panel.add(boton_login);
        panel.add(boton_register);

        return panel;
    }
    private JPanel panelCuenta(){
        JPanel panel = new JPanel();
        JLabel label_correo = new JLabel("Correo");
        JTextField field_correo = new JTextField("correo",20);
        JLabel label_pass = new JLabel("Contraseña");
        JTextField field_pass = new JTextField("contraseña",20);
        JButton boton_login = new JButton("Inicar Sesión");
        JButton boton_register = new JButton("Crear Cuenta");
        panel.add(label_correo);
        panel.add(field_correo);
        panel.add(label_pass);
        panel.add(field_pass);
        panel.add(boton_login);
        panel.add(boton_register);

        return panel;
    }
    private JPanel panelCalendario(){
        JPanel panel = new JPanel();
        JLabel label_correo = new JLabel("Correo");
        JTextField field_correo = new JTextField("correo",20);
        JLabel label_pass = new JLabel("Contraseña");
        JTextField field_pass = new JTextField("contraseña",20);
        JButton boton_login = new JButton("Inicar Sesión");
        JButton boton_register = new JButton("Crear Cuenta");
        panel.add(label_correo);
        panel.add(field_correo);
        panel.add(label_pass);
        panel.add(field_pass);
        panel.add(boton_login);
        panel.add(boton_register);

        return panel;
    }
    
    public JMenuBar menuBar(){
        //Creamos la barra de menu de la ventana
        JMenuBar menu = new JMenuBar();

        //Creamos los menuItem y se los asociamos a la barra de menu
        menuItem_calendario=new JMenuItem("Calendario");
        menuItem_calendario.addActionListener(this);
        menu.add(menuItem_calendario);

        menuItem_cuenta=new JMenuItem("Cuenta");
        menuItem_cuenta.addActionListener(this);
        menu.add(menuItem_cuenta);
        
        menuItem_fin=new JMenuItem("Finalizar");
        menuItem_fin.addActionListener(this);
        menu.add(menuItem_fin);

        return menu;
    }

    public static void main(String[] args) {
        System.out.println("La aplicación empieza");
        
        Client ventana = new Client();

        System.out.println("Se está solicitando el inicio de sesión");

        //Scanner input = new Scanner(System.in);
        //input.nextInt();
        
    }
}