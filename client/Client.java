import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
 
public class Client extends JFrame implements ActionListener{

    //Apartado de Inicio de sesión
    private JButton boton_formulario_login,boton_register;

    //Apartado de Crear Cuenta
    private JButton boton_formulario_crear_cuenta,boton_inicio;

    //Apartado de la barra
    private JMenuBar menu_bar;
    private JMenuItem menuItem_calendario,menuItem_evento,menuItem_cuenta;

    //Apartado de Calendario
    private JButton boton_mes_mayor,boton_mes_menor;
    private JLabel label_anio_iterador,label_mes_iterador;
    
    private int mes_actual,anio_actual,dia_actual;
    private int mes_iterador,anio_iterador;
    private JLabel[][] label_day_month;

    //Apartado de Cuenta
    private JButton boton_cambio_contraseña, boton_salir, boton_cerrarSesion;
    
    //Información 
    //private User usuario;
    //private List<Event> eventos;

//JTextField:
    // .setBackground(); // Color del fondo
    // .setForeground(); // Color de la fuente
    // .setFont(new java.awt.Font("Swis721 Blk BT", 1, 12)); // Tipo de fuente
    // .setText("texto"); // Para vaciarlo basta con asignar ""
    // .getText().isEmpty() // Así podemos validar si está vacío o no

    // Constructor de la interfaz de la aplicación
    public Client() {

        // Se crea el calendario para obtener el dia mes y año actual
        Calendar calendario = new GregorianCalendar();
        mes_actual = calendario.get(Calendar.MONTH)+1 ;
        anio_actual = calendario.get(Calendar.YEAR) ;
        dia_actual = calendario.get(Calendar.DATE) ;

        // En el calendario grafico se comenzará mostrando el mes del año en el que nos encontramos
        mes_iterador = mes_actual ;
        anio_iterador = anio_actual ;         

        // Se configuran propiedades de la ventana
        this.setLocationRelativeTo(null); //Esto permite que la ventana aparezca al centro
        this.setLayout(null); //Layout absoluto

        // Se crean la interfaz de la sección de inicio de sesión
        MarcoInicioSesion();

        // Se configuran propiedades de la ventana
        //this.setMinimumSize(new Dimension(600,500)); //(ancho,alto)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cerrar proceso al cerrar ventana
        this.setVisible(true); //Mostrar JFrame 
    }

    // Método que implementa las acciones de cada ítem de la interfaz
    public void actionPerformed(ActionEvent e) {

        //Apartado de Inicio de sesión      
        if (e.getSource()==boton_formulario_login) {
            System.out.println("Se ha pulsado el botón para enviar el formulario del login");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoCalendario();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);
        }        
        if (e.getSource()==boton_register) {
            System.out.println("Se ha pulsado el botón para ir a crear cuenta");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoCreaCuenta();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);   
        }

        //Apartado de Crear Cuenta            
        if (e.getSource()==boton_formulario_crear_cuenta) {
            System.out.println("Se ha pulsado el botón para enviar el formulario para crear la cuenta");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoInicioSesion();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);
        }
        if (e.getSource()==boton_inicio) {
            System.out.println("Se ha pulsado el botón para ir al inicio de sesión");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoInicioSesion();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);           
        }

        //Apartado de la barra
        if (e.getSource()==menuItem_calendario) {
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoCalendario();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true); 
        }
        if (e.getSource()==menuItem_evento) {
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoEventos();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);
        }
        if (e.getSource()==menuItem_cuenta) {
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoCuenta();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true); 
        }

        //Apartado de Calendario
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

        //Apartado de Cuenta
        if (e.getSource()==boton_cambio_contraseña) {
            System.out.println("Se llama a cambiar la contraseña");
        }  
        if (e.getSource()==boton_salir) {
            System.out.println("Se llama a finalizar la aplicación");
            System.exit(0);
        }   
        if (e.getSource()==boton_cerrarSesion) {
            System.out.println("Se ha pulsado el botón para ir al inicio de sesión");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            MarcoInicioSesion();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);
        }
    }

    // Método que crea la interfaz correspondiente al incio de sesión
    private void MarcoInicioSesion(){
        
        JPanel panelInicioSesion = new JPanel();
        panelInicioSesion.setLayout(null);
        panelInicioSesion.setVisible(true);
        panelInicioSesion.setBackground(Color.GREEN); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;

        // Se crean las etiquetas y botones, y se añaden a la ventana
        //FILA1
        JLabel label_correo_iniciosesion = new JLabel("Correo");
        label_correo_iniciosesion.setBounds(x,y,ancho,alto);
        panelInicioSesion.add(label_correo_iniciosesion);

        x=x+ancho+espacio_x;
        JTextField field_correo_iniciosesion = new JTextField("email",20);
        field_correo_iniciosesion.setBounds(x,y,ancho,alto);
        panelInicioSesion.add(field_correo_iniciosesion);
        
        //FILA2
        x=borde;
        y=y+alto+espacio_x;
        JLabel label_pass_iniciosesion = new JLabel("Contraseña");
        label_pass_iniciosesion.setBounds(x,y,ancho,alto);    
        panelInicioSesion.add(label_pass_iniciosesion);

        x=x+ancho+espacio_x;
        JPasswordField field_pass_iniciosesion = new JPasswordField();
        //JTextField field_pass = new JTextField("contraseña",20);
        field_pass_iniciosesion.setBounds(x,y,ancho,alto);   
        panelInicioSesion.add(field_pass_iniciosesion);

        //FILA3
        x=borde;
        y=y+alto+espacio_x;
        boton_formulario_login = new JButton("Inicar Sesión");
        boton_formulario_login.setBounds(x,y,ancho,alto-5);
        boton_formulario_login.addActionListener(this);     
        panelInicioSesion.add(boton_formulario_login);
        
        x=x+ancho+espacio_x;
        boton_register = new JButton("Crear Cuenta");
        boton_register.setBounds(x,y,ancho,alto-5);
        boton_register.addActionListener(this);    
        panelInicioSesion.add(boton_register);

        x = x + ancho + borde;
        y = y + alto-5 + borde;
        panelInicioSesion.setBounds(0,0,x,y);
        this.add(panelInicioSesion);

        // Se configura la ventana
        this.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
        this.setSize(x,y+25); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(false); //No redimensionable
        //this.setBackground(Color.decode("#ACBFC5"));

    }

    // Método que crea la interfaz correspondiente a la creación de una cuenta
    private void MarcoCreaCuenta(){

        JPanel panelCreaCuenta = new JPanel();
        panelCreaCuenta.setLayout(null);
        panelCreaCuenta.setVisible(true);
        panelCreaCuenta.setBackground(Color.GREEN); // Color del fondo


        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;


        // Se crean las etiquetas y botones, y se añaden a la ventana
        JLabel label_correo = new JLabel("Correo");
        label_correo.setBounds(x,y,ancho,alto);
        panelCreaCuenta.add(label_correo);

        y = y + alto + espacio_y;
        JTextField field_correo = new JTextField("email",20);
        field_correo.setBounds(x,y,ancho,alto);
        panelCreaCuenta.add(field_correo);

        y = y + alto + espacio_y;
        JLabel label_pass = new JLabel("Contraseña");
        label_pass.setBounds(x,y,ancho,alto);    
        panelCreaCuenta.add(label_pass);

        y = y + alto + espacio_y;
        JPasswordField field_pass = new JPasswordField();
        //JTextField field_pass = new JTextField("contraseña",20);
        field_pass.setBounds(x,y,ancho,alto);   
        panelCreaCuenta.add(field_pass);

        y = y + alto + espacio_y;
        JPasswordField field_pass2 = new JPasswordField();
        field_pass2.setBounds(x,y,ancho,alto);   
        panelCreaCuenta.add(field_pass2);

        y = y + alto + espacio_y;
        boton_formulario_crear_cuenta = new JButton("Crear Cuenta");
        boton_formulario_crear_cuenta.setBounds(x,y,ancho,alto-5);
        boton_formulario_crear_cuenta.addActionListener(this);     
        panelCreaCuenta.add(boton_formulario_crear_cuenta);

        y = y + alto + espacio_y;
        boton_inicio = new JButton("Iniciar Sesión");
        boton_inicio.setBounds(x,y,ancho,alto-5);
        boton_inicio.addActionListener(this);        
        panelCreaCuenta.add(boton_inicio);

        x = borde + ancho + borde;
        y = y + alto-5 + borde;
        panelCreaCuenta.setBounds(0,0,x,y);
        this.add(panelCreaCuenta);

       // Se configura la ventana
        this.setTitle("TotalAgenda - Crear Cuenta"); //Título del JFrame
        this.setSize(x,y+25); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(false); //No redimensionable
        //this.setBackground(Color.decode("#ACBFC5"));
    }

    // Método que crea la interfaz de la sección del calendario gráfico
    private void MarcoCalendario(){

        // Se configura la ventana
        this.setTitle("TotalAgenda"); //Título del JFrame
        this.setSize(600,500); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(true); //Redimensionable

        // Se crea la barra de menu de la ventana
        menu_bar=menuBar();
        this.setJMenuBar(menu_bar);
        
        // Se crean los elementos que forman la interfaz de esta sección y los añadimos
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

        // Se crea la fila con los días del calendario grafico
        label_day_month = new JLabel[6][7];
        String[] dias= {"L","M","X","J","V","S","D"};
        for (j=0; (j<7) ;j++){
            label_day_month[i][j] = new JLabel("<html><div style='text-align: center;'>"+dias[j]+"</div></html>", SwingConstants.CENTER);

            label_day_month[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label_day_month[i][j].setBounds(x+ancho*j,60,ancho,30);
            this.add(label_day_month[i][j]);
        }

        // Se crea el calendario gráfico completo con el 
        int[][] matrix = calendario.calendar(anio_actual,mes_actual);
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

    // Método que aumenta el mes del calendario que se está viendo
    private void incrementaMesIterador(){
        if (mes_iterador==12){
            mes_iterador=1;
            anio_iterador=anio_iterador+1;
        }else{
            mes_iterador = mes_iterador+1;
        }
    }

    // Método que decrementa el mes del calendario que se está viendo
    private void decrementaMesIterador(){
        if (mes_iterador==1){
            mes_iterador=12;
            anio_iterador=anio_iterador-1;
        }else{
            mes_iterador = mes_iterador-1;
        }
    }

    // Método que cambia el calendario de mes
    private void cambiaCalendario(){

        // Se cambia la información que se muestra en el calendario gráfico en función del mes y del año que se vayan a visualizar
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

    // Método que crea la interfaz de la sección de eventos    
    private void MarcoEventos(){

        // Se configura la ventana
        this.setTitle("TotalAgenda"); //Título del JFrame
        this.setSize(600,500); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(true); //Redimensionable

        // Se crea la barra de menu de la ventana
        menu_bar=menuBar();
        this.setJMenuBar(menu_bar);

        JPanel panelEventos = new JPanel();
        panelEventos.setLayout(null);
        panelEventos.setVisible(true);
        panelEventos.setBackground(Color.GREEN); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;

        JLabel label_correo = new JLabel("<html><div style='text-align: center;'>" + "Correo:" + "</div></html>");
        label_correo.setBounds(x,y,ancho,alto);
        label_correo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelEventos.add(label_correo);

        x=x+ancho+espacio_x;
        JLabel label_correo2 = new JLabel("Correo"); //usuario.getEmail();
        label_correo2.setBounds(x,y,ancho,alto);
        label_correo2.setBorder(BorderFactory.createLineBorder(Color.BLACK));        
        panelEventos.add(label_correo2);

        x=borde+ancho/2;
        y=y+alto+espacio_y;
        boton_cambio_contraseña = new JButton("Cambiar Contraseña"); //usuario.getEmail();
        boton_cambio_contraseña.setBounds(x,y,ancho,alto);
        panelEventos.add(boton_cambio_contraseña);
        
        y=y+alto+espacio_y;
        boton_cerrarSesion=new JButton("Cerrar Sesión");
        boton_cerrarSesion.setBounds(x,y,ancho,alto);
        boton_cerrarSesion.addActionListener(this);
        panelEventos.add(boton_cerrarSesion);
        
        y=y+alto+espacio_y;
        boton_salir=new JButton("Salir");
        boton_salir.setBounds(x,y,ancho,alto);
        boton_salir.addActionListener(this);
        panelEventos.add(boton_salir);

        x=2*borde + espacio_x + 2*ancho;
        y = y+alto+borde;
        panelEventos.setBounds(0,0,x,y);
        this.add(panelEventos);
    }

    // Método que crea la interfaz de la sección de gestión de la cuenta del usuario
    private void MarcoCuenta(){

        // Se configura la ventana
        this.setTitle("TotalAgenda"); //Título del JFrame
        this.setSize(600,500); //Dimensiones del JFrame (ancho,alto)
        this.setResizable(true); //Redimensionable

        // Se crea la barra de menu de la ventana
        menu_bar=menuBar();
        this.setJMenuBar(menu_bar);

        JPanel panelEventos = new JPanel();
        panelEventos.setLayout(null);
        panelEventos.setVisible(true);
        panelEventos.setBackground(Color.GREEN); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;

        JLabel label_correo = new JLabel("<html><div style='text-align: center;'>" + "Correo:" + "</div></html>");
        label_correo.setBounds(x,y,ancho,alto);
        label_correo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelEventos.add(label_correo);

        x=x+ancho+espacio_x;
        JLabel label_correo2 = new JLabel("Correo"); //usuario.getEmail();
        label_correo2.setBounds(x,y,ancho,alto);
        label_correo2.setBorder(BorderFactory.createLineBorder(Color.BLACK));        
        panelEventos.add(label_correo2);

        x=borde+ancho/2;
        y=y+alto+espacio_y;
        boton_cambio_contraseña = new JButton("Cambiar Contraseña"); //usuario.getEmail();
        boton_cambio_contraseña.setBounds(x,y,ancho,alto);
        panelEventos.add(boton_cambio_contraseña);
        
        y=y+alto+espacio_y;
        boton_cerrarSesion=new JButton("Cerrar Sesión");
        boton_cerrarSesion.setBounds(x,y,ancho,alto);
        boton_cerrarSesion.addActionListener(this);
        panelEventos.add(boton_cerrarSesion);
        
        y=y+alto+espacio_y;
        boton_salir=new JButton("Salir");
        boton_salir.setBounds(x,y,ancho,alto);
        boton_salir.addActionListener(this);
        panelEventos.add(boton_salir);

        x = 2*borde + espacio_x + 2*ancho;
        y = y+alto+borde;
        panelEventos.setBounds(0,0,x,y);
        this.add(panelEventos);
    }

    // Método que crea la barra de menú de la aplicación
    public JMenuBar menuBar(){
        // Se la barra de menu de la ventana
        JMenuBar menu = new JMenuBar();

        // Se crean los menuItem y se los asociamos a la barra de menu
        JLabel menuFecha=new JLabel(String.valueOf(dia_actual)+"/"+String.valueOf(mes_actual)+"/"+String.valueOf(anio_actual));
        menu.add(menuFecha);

        menuItem_calendario=new JMenuItem("Calendario");
        menuItem_calendario.addActionListener(this);
        menu.add(menuItem_calendario);

        menuItem_evento=new JMenuItem("Eventos");
        menuItem_evento.addActionListener(this);
        menu.add(menuItem_evento);
        
        menuItem_cuenta=new JMenuItem("Cuenta");
        menuItem_cuenta.addActionListener(this);
        menu.add(menuItem_cuenta);

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