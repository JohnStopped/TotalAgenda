import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
 
public class Client extends JFrame implements ActionListener{

    private JButton boton_formulario_login,boton_formulario_crear_cuenta,boton_register,boton_inicio;
    private JMenuBar menu_bar;
    private JMenuItem menuItem_calendario, menuItem_evento, menuItem_fin;
    private JButton boton_mes_mayor,boton_mes_menor;
    private JLabel label_anio_iterador,label_mes_iterador;

    //private List<Event> eventos;
    private int mes_actual,anio_actual,dia_actual;
    private int mes_iterador;
    private int anio_iterador;
    private JLabel[][] label_day_month;

//JTextField:
    // .setBackground(); // Color del fondo
    // .setForeground(); // Color de la fuente
    // .setFont(new java.awt.Font("Swis721 Blk BT", 1, 12)); // Tipo de fuente
    // .setText("texto"); // Para vaciarlo basta con asignar ""
    // .getText().isEmpty() // Así podemos validar si está vacío o no

    public Client() {

        Calendar calendario = new GregorianCalendar();
        mes_actual = calendario.get(Calendar.MONTH)+1 ;
        anio_actual = calendario.get(Calendar.YEAR) ;
        dia_actual = calendario.get(Calendar.DATE) ;

        mes_iterador = mes_actual ;
        anio_iterador = anio_actual ;         
        //System.out.println(calendario.get(Calendar.DATE) +"-"+ calendario.get(Calendar.MONTH) +"-"+ calendario.get(Calendar.YEAR) +" "+ calendario.get(Calendar.HOUR_OF_DAY) +":"+ calendario.get(Calendar.MINUTE));

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
            MarcoCalendario();
            this.getContentPane().revalidate(); 
        }
        if (e.getSource()==menuItem_evento) {
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoEventos();
            this.getContentPane().revalidate(); 
        }
        if (e.getSource()==menuItem_fin) {
            System.out.println("Se llama a finalizar la aplicación");
            System.exit(0);
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
            MarcoCalendario();
            this.getContentPane().revalidate();            
        }
        if (e.getSource()==boton_formulario_crear_cuenta) {
           System.out.println("Se ha pulsado el botón para enviar el formulario para crear la cuenta");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoInicioSesion();
            this.getContentPane().revalidate();
        }
        if (e.getSource()==boton_mes_menor) {
            decrementaMesIterador();
            System.out.println("Se va al mes anterior: " + mes_iterador);
            cambiaCalendario();
        }        
        if (e.getSource()==boton_mes_mayor) {
            incrementaMesIterador();
            System.out.println("Se va al mes siguiente: " + mes_iterador);
            cambiaCalendario();
        }
    }

    private void incrementaMesIterador(){
        if (mes_iterador==12){
            mes_iterador=1;
            anio_iterador=anio_iterador+1;
        }else{
            mes_iterador = mes_iterador+1;
        }
    }

    private void decrementaMesIterador(){
        if (mes_iterador==1){
            mes_iterador=12;
            anio_iterador=anio_iterador-1;
        }else{
            mes_iterador = mes_iterador-1;
        }
    }

    private void cambiaCalendario(){

        label_anio_iterador.setText(String.valueOf(anio_iterador));
        label_mes_iterador.setText(String.valueOf(mes_iterador));
        int i=0;
        int j=0;
        int int_aux;
        int[][] matrix = calendario.calendar(anio_iterador,mes_iterador);
        for (i = 0; (i<6) ;i++ ){
          for (j=0; (j<7) ;j++){
            int_aux = matrix[i][j];

            if (int_aux!=0){
                label_day_month[i][j].setText("<html><div style='text-align: center;'>"+String.valueOf(int_aux)+"<br>"+"<br>"+"hola"+"</div></html>");
            }else{
                label_day_month[i][j].setText("");
            }
          }
          
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

    private void MarcoEventos(){

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

    private void MarcoCalendario(){

        this.setTitle("TotalAgenda"); //Título del JFrame
        this.setSize(600,500); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(true); //Redimensionable

        //Creamos la barra de menu de la ventana
        menu_bar=menuBar();
        this.setJMenuBar(menu_bar);
        
        int ancho = 60;
        int alto = 60;
        int x = 40;
        int y = 90;
        int i = 0;
        int j = 0;
        int int_aux;

        boton_mes_menor = new JButton("<--");
        boton_mes_menor.setBounds(x,30,35,25);
        boton_mes_menor.addActionListener(this);     
        this.add(boton_mes_menor);

        boton_mes_mayor = new JButton("-->");
        boton_mes_mayor.setBounds(x+35,30,35,25);
        boton_mes_mayor.addActionListener(this);     
        this.add(boton_mes_mayor);

        label_anio_iterador = new JLabel(String.valueOf(anio_iterador));
        label_anio_iterador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label_anio_iterador.setBounds(x+35*2,30,35,25);   
        this.add(label_anio_iterador);

        label_mes_iterador = new JLabel(String.valueOf(mes_iterador));
        label_mes_iterador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label_mes_iterador.setBounds(x+35*3,30,35,25);    
        this.add(label_mes_iterador);

        label_day_month = new JLabel[6][7];
        int[][] matrix = calendario.calendar(anio_actual,mes_actual);
        String[] dias= {"L","M","X","J","V","S","D"};

          for (j=0; (j<7) ;j++){
            label_day_month[i][j] = new JLabel("<html><div style='text-align: center;'>"+dias[j]+"</div></html>", SwingConstants.CENTER);

            label_day_month[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label_day_month[i][j].setBounds(x+ancho*j,60,ancho,30);
            this.add(label_day_month[i][j]);
        }


        for (i = 0; (i<6) ;i++ ){
          for (j=0; (j<7) ;j++){
            int_aux = matrix[i][j];

            if (int_aux!=0){
                label_day_month[i][j] = new JLabel("<html><div style='text-align: center;'>"+String.valueOf(int_aux)+"<br>"+"<br>"+"hola"+"</div></html>", SwingConstants.CENTER);
            }else{
                label_day_month[i][j] = new JLabel("");
            }

            label_day_month[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label_day_month[i][j].setBounds(x+ancho*j,y+alto*i,ancho,alto);
            this.add(label_day_month[i][j]);
          }
          
        }

    }
    
    public JMenuBar menuBar(){
        //Creamos la barra de menu de la ventana
        JMenuBar menu = new JMenuBar();

        //Creamos los menuItem y se los asociamos a la barra de menu
        JLabel menuFecha=new JLabel(String.valueOf(dia_actual)+"/"+String.valueOf(mes_actual)+"/"+String.valueOf(anio_actual));
        menu.add(menuFecha);

        menuItem_calendario=new JMenuItem("Calendario");
        menuItem_calendario.addActionListener(this);
        menu.add(menuItem_calendario);

        menuItem_evento=new JMenuItem("Eventos");
        menuItem_evento.addActionListener(this);
        menu.add(menuItem_evento);
        
        menuItem_fin=new JMenuItem("Cerrar Sesión -->");
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