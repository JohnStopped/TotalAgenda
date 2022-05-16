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
    private JButton boton_formulario_login,boton_formulario_crear_cuenta,boton_register,boton_inicio ;

//JTextField:
    // .setBackground(); // Color del fondo
    // .setForeground(); // Color de la fuente
    // .setFont(new java.awt.Font("Swis721 Blk BT", 1, 12)); // Tipo de fuente
    // .setText("texto"); // Para vaciarlo basta con asignar ""
    // .getText().isEmpty() // Así podemos validar si está vacío o no

    public Client() {
        this.setLocationRelativeTo(null); //Esto permite que la ventana aparezca al centro
        this.setLayout(null); //Layout absoluto

        MarcoInicioSesion();

        //this.setMinimumSize(new Dimension(800,550)); //(ancho,alto)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cerrar proceso al cerrar ventana
        this.setVisible(true); //Mostrar JFrame 
        //Instancia un objeto BorderLayout con una separacion de 15px en horizonal y vertical
        //BorderLayout miBorderLayout = new BorderLayout(15,15);

        //Uso este BorderLayout para que sea el controlador de posicionamiento de mi objeto JFrame
        //this.setLayout(miBorderLayout);
    }

    /* Método que implementa las acciones de cada ítem de menú */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==menuItem_calendario) {
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();
            this.getContentPane().add(panelCalendario());
            this.getContentPane().revalidate();
        }
        if (e.getSource()==menuItem_cuenta) {
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();
            this.getContentPane().add(panelCuenta());
            this.getContentPane().revalidate();
        }
        if (e.getSource()==boton_inicio) {
           System.out.println("Se ha pulsado el botón para ir al inicio de sesión");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoInicioSesion();
            this.getContentPane().revalidate();           
        }
        if (e.getSource()==boton_register) {
           System.out.println("Se ha pulsado el botón para ir a crear cuenta");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoCreaCuenta();
            this.getContentPane().revalidate();   
        }                
        if (e.getSource()==boton_formulario_login) {
           System.out.println("Se ha pulsado el botón para enviar el formulario del login");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoApp();
            this.getContentPane().revalidate();            
        }
        if (e.getSource()==boton_formulario_crear_cuenta) {
           System.out.println("Se ha pulsado el botón para enviar el formulario para crear la cuenta");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoInicioSesion();
            this.getContentPane().revalidate();
        }
        if (e.getSource()==menuItem_fin) {
            System.out.println("Se llama a finalizar la aplicación");
            System.exit(0);
        }
    }

    private void MarcoInicioSesion(){
        
        JLabel label_correo_iniciosesion = new JLabel("Correo");
        label_correo_iniciosesion.setBounds(40,30,200,30);
        this.add(label_correo_iniciosesion);

        JTextField field_correo_iniciosesion = new JTextField("email",20);
        field_correo_iniciosesion.setBounds(40,60,200,30);
        this.add(field_correo_iniciosesion);

        JLabel label_pass_iniciosesion = new JLabel("Contraseña");
        label_pass_iniciosesion.setBounds(40,110,200,30);    
        this.add(label_pass_iniciosesion);

        JPasswordField field_pass_iniciosesion = new JPasswordField();
        //JTextField field_pass = new JTextField("contraseña",20);
        field_pass_iniciosesion.setBounds(40,150,200,30);   
        this.add(field_pass_iniciosesion);

        boton_formulario_login = new JButton("Inicar Sesión");
        boton_formulario_login.setBounds(40,190,200,25);
        boton_formulario_login.addActionListener(this);     
        this.add(boton_formulario_login);

        boton_register = new JButton("Crear Cuenta");
        boton_register.setBounds(40,230,200,25);
        boton_register.addActionListener(this);    
        this.add(boton_register);

        //this.setBackground(Color.decode("#ACBFC5"));

        // Configuramos la ventana
        this.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
        this.setSize(280,300); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(false); //No redimensionable

    }

    private void MarcoCreaCuenta(){

        JLabel label_correo = new JLabel("Correo");
        label_correo.setBounds(40,30,200,30);
        this.add(label_correo);

        JTextField field_correo = new JTextField("email",20);
        field_correo.setBounds(40,60,200,30);
        this.add(field_correo);

        JLabel label_pass = new JLabel("Contraseña");
        label_pass.setBounds(40,110,200,30);    
        this.add(label_pass);

        JPasswordField field_pass = new JPasswordField();
        //JTextField field_pass = new JTextField("contraseña",20);
        field_pass.setBounds(40,140,200,30);   
        this.add(field_pass);

        JPasswordField field_pass2 = new JPasswordField();
        field_pass2.setBounds(40,170,200,30);   
        this.add(field_pass2);

        boton_formulario_crear_cuenta = new JButton("Crear Cuenta");
        boton_formulario_crear_cuenta.setBounds(40,220,200,25);
        boton_formulario_crear_cuenta.addActionListener(this);     
        this.add(boton_formulario_crear_cuenta);

        boton_inicio = new JButton("Iniciar Sesión");
        boton_inicio.setBounds(40,250,200,25);
        boton_inicio.addActionListener(this);        
        this.add(boton_inicio);

        this.setBackground(Color.decode("#ACBFC5"));

        // Configuramos la ventana
        this.setTitle("TotalAgenda - Crear Cuenta"); //Título del JFrame
        this.setSize(290,300); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(false); //No redimensionable
    }

    private void MarcoApp(){

        this.setTitle("TotalAgenda"); //Título del JFrame
        this.setSize(600,500); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(true); //Redimensionable

        //Creamos la barra de menu de la ventana
        menu_bar=menuBar();
        this.setJMenuBar(menu_bar);

        JLabel label_correo = new JLabel("PRUEBA");
        label_correo.setBounds(40,30,200,30);
        this.add(label_correo);

    }
    private JPanel panelCuenta(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);

        JLabel label_correo = new JLabel("Correo");
        //JTextField field_correo = new JTextField("correo",20);
        //JLabel label_pass = new JLabel("Contraseña");
        //JTextField field_pass = new JTextField("contraseña",20);
        //JButton boton_login = new JButton("Inicar Sesión");
        //JButton boton_register = new JButton("Crear Cuenta");
        panel.add(label_correo);
        //panel.add(field_correo);
        //panel.add(label_pass);
        //panel.add(field_pass);
        //panel.add(boton_login);
        //panel.add(boton_register);

        return panel;
    }
    private JPanel panelCalendario(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLUE);

        JLabel label_correo = new JLabel("Correo");
        //JTextField field_correo = new JTextField("correo",20);
        //JLabel label_pass = new JLabel("Contraseña");
        //JTextField field_pass = new JTextField("contraseña",20);
        //JButton boton_login = new JButton("Inicar Sesión");
        //JButton boton_register = new JButton("Crear Cuenta");
        panel.add(label_correo);
        //panel.add(field_correo);
        //panel.add(label_pass);
        //panel.add(field_pass);
        //panel.add(boton_login);
        //panel.add(boton_register);

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