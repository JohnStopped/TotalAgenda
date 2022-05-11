import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
 
public class Client extends JFrame implements ActionListener{

    private JMenuBar menu_bar;
    private JMenuItem menuItem_calendario, menuItem_cuenta, menuItem_fin;
    private JPanel panel_inicio,panel_calendario,panel_cuenta;

    public Client() {

        //Se crea el panel de inicio
        //panel_inicio = panelInicio();

        //Se crea el panel de cuenta
        //panel_cuenta = panelCuenta();

        //Se crea el panel de calendario
        //panel_calendario = panelCalendario();

        //Creamos la barra de menu de la ventana
        menu_bar=menuBar();

        //Asociamos los elementos al JFrame
        setJMenuBar(menu_bar);
        //setContentPane(panel_inicio);

        JLabel label_correo = new JLabel("Correo");
        JTextField field_correo = new JTextField("correo",20);
        JLabel label_pass = new JLabel("Contraseña");
        JTextField field_pass = new JTextField("contraseña",20);
        JButton boton_login = new JButton("Inicar Sesión");
        JButton boton_register = new JButton("Crear Cuenta");
        add(label_correo);
        add(field_correo);
        add(label_pass);
        add(field_pass);
        add(boton_login);
        add(boton_register);

        //Configurar y mostrar JFrame
        initPantalla();
    }

    private void initPantalla() {

        setLayout(null); //Layout absoluto
        setTitle("TotalAgenda"); //Título del JFrame
        setSize(640,480); //Dimensiones del JFrame
        setResizable(true); //No redimensionable
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cerrar proceso al cerrar ventana
        setVisible(true); //Mostrar JFrame
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