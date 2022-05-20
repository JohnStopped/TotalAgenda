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

    // Apliación
    private JTabbedPane panelDePestanas;
    private JPanel panelCalendario, panelEventos, panelCuenta;

    //Apartado de Calendario
    private JButton boton_mes_mayor,boton_mes_menor;
    private JLabel label_anio_iterador,label_mes_iterador;
    
    private int mes_actual,anio_actual,dia_actual;
    private int mes_iterador,anio_iterador;
    private JLabel[][] label_day_month;

    // Apartado Evento
    private JButton boton_creaEvento,boton_formulario_crear_evento;

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
            pestañas();
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

        //Apartado de Calendario
        if (e.getSource()==boton_creaEvento) {
            System.out.println("Se va a crear un evento");
            frameCreaEventos();
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
        this.setSize(x,y+25); //Dimensiones del JFrame (ancho,alto) - se le añaden 25 de alto para los del marco superior de la ventana
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
        this.setSize(x,y+25); //Dimensiones del JFrame (ancho,alto) - se le añaden 25 de alto para los del marco superior de la ventana
        this.setResizable(false); //No redimensionable
        //this.setBackground(Color.decode("#ACBFC5"));
    }

    // Método para crear las pestañas de la aplicación
    private void pestañas(){

        // Se configura la ventana
        this.setTitle("TotalAgenda"); //Título del JFrame
        this.setSize(800,600); //Dimensiones del JFrame (ancho,alto) - se le añaden 25 de alto para los del marco superior de la ventana y 25 para los de la barra de menu
        this.setResizable(true); //Redimensionable

        JTabbedPane panelDePestanas = new JTabbedPane(JTabbedPane.TOP);
        panelCalendario = PanelCalendario();
        panelEventos = PanelEventos();
        panelCuenta = PanelCuenta();
        panelDePestanas.addTab("Calendario", null, panelCalendario, null);
        panelDePestanas.addTab("Eventos", null, panelEventos, null);
        panelDePestanas.addTab("Cuenta", null, panelCuenta, null);

        panelDePestanas.setBounds(0,0,800,600);
        this.add(panelDePestanas);
    }

    // Método que crea la interfaz de la sección del calendario gráfico
    private JPanel PanelCalendario(){

        JPanel panelCalendario = new JPanel();
        panelCalendario.setLayout(null);
        panelCalendario.setVisible(true);
        panelCalendario.setBackground(Color.GREEN); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int lado = 60;
        int ancho = 40;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;
        int i = 0;
        int j = 0;
        int int_aux;

        boton_mes_menor = new JButton("<--");
        boton_mes_menor.setBounds(x,y,ancho,alto);
        boton_mes_menor.addActionListener(this);     
        panelCalendario.add(boton_mes_menor);

        x = x+ancho;
        boton_mes_mayor = new JButton("-->");
        boton_mes_mayor.setBounds(x,y,ancho,alto);
        boton_mes_mayor.addActionListener(this);     
        panelCalendario.add(boton_mes_mayor);
        
        x = x+ancho+espacio_x;
        label_anio_iterador = new JLabel(String.valueOf(anio_iterador));
        label_anio_iterador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label_anio_iterador.setBounds(x,y,ancho,alto);   
        panelCalendario.add(label_anio_iterador);

        x = x+ancho;
        label_mes_iterador = new JLabel(String.valueOf(mes_iterador));
        label_mes_iterador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label_mes_iterador.setBounds(x,y,ancho,alto);    
        panelCalendario.add(label_mes_iterador);

        // Se crea la fila con los días del calendario grafico
        x = borde;
        y = y+alto+espacio_y;
        label_day_month = new JLabel[6][7];
        String[] dias= {"L","M","X","J","V","S","D"};
        for (j=0; (j<7) ;j++){
            label_day_month[i][j] = new JLabel("<html><div style='text-align: center;'>"+dias[j]+"</div></html>", SwingConstants.CENTER);

            label_day_month[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label_day_month[i][j].setBounds(x,y,lado,lado/2);
            panelCalendario.add(label_day_month[i][j]);
            x = x+lado;
        }

        // Se crea el calendario gráfico completo con el 
        y = y+lado/2;
        int[][] matrix = calendario.calendar(anio_actual,mes_actual);
        for (i = 0; (i<6) ;i++ ){
            x = borde;
          for (j=0; (j<7) ;j++){
            int_aux = matrix[i][j];

            if (int_aux!=0){
                label_day_month[i][j] = new JLabel("<html><div style='text-align: center;'>"+String.valueOf(int_aux)+"<br>"+"<br>"+"hola"+"</div></html>", SwingConstants.CENTER);
            }else{
                label_day_month[i][j] = new JLabel("");
            }

            label_day_month[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label_day_month[i][j].setBounds(x,y,lado,lado);
            panelCalendario.add(label_day_month[i][j]);
            x = x+lado;
          }
          y = y+lado;
        }

        x = x+borde;
        y = y+borde;
        panelCalendario.setBounds(0,0,x,y);
        
        return panelCalendario;       
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
    private JPanel PanelEventos(){

        JPanel panelEventos = new JPanel();
        panelEventos.setLayout(new BorderLayout());
        panelEventos.setVisible(true);
        //panelEventos.setBackground(Color.GREEN); // Color del fondo

        // Se crea la barra superiora del panel
        JPanel barraSuperior = new JPanel();
        barraSuperior.setLayout(new FlowLayout());
        barraSuperior.setVisible(true);
        
        JLabel label_fecha = new JLabel(String.valueOf(dia_actual+"/"+mes_actual+"/"+anio_actual));
        label_fecha.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        barraSuperior.add(label_fecha);

        boton_creaEvento = new JButton("Crear evento");
        boton_creaEvento.addActionListener(this);
        barraSuperior.add(boton_creaEvento);
        
        panelEventos.add(barraSuperior,BorderLayout.NORTH);

        JTextArea areaIntroduccion = new JTextArea();
        JScrollPane scrollBar = new JScrollPane(areaIntroduccion, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelEventos.add(scrollBar,BorderLayout.CENTER);

        return panelEventos;
    }

    private void frameCreaEventos(){
        JDialog frame_creaEvento = new JDialog(this);
        
        JPanel panel_creaEvento = new JPanel();
        panel_creaEvento.setLayout(null);
        panel_creaEvento.setVisible(true);

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 10;

        // Se crean las etiquetas y botones, y se añaden a la ventana
        //FILA1
        JTextField field_titulo = new JTextField("Título");
        field_titulo.setBounds(x,y,ancho,alto);
        panel_creaEvento.add(field_titulo);

        //FILA2
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_fecha = new JLabel("Fecha: ");
        label_fecha.setBounds(x,y,ancho,alto);    
        panel_creaEvento.add(label_fecha);

        //FILA3
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_fecha_advice = new JLabel("Fecha de aviso");
        label_fecha_advice.setBounds(x,y,ancho,alto);    
        panel_creaEvento.add(label_fecha_advice);

        //FILA4
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_nota = new JLabel("Nota: ");
        label_nota.setBounds(x,y,ancho,alto);    
        panel_creaEvento.add(label_nota);

        //FILA5
        x = borde;
        y=y+alto+espacio_y;
        JTextArea field_nota = new JTextArea();
        field_nota.setBounds(x,y,2*ancho,5*alto);    
        panel_creaEvento.add(field_nota);

        //FILA6
        x = borde;
        y=y+5*alto+espacio_y;
        boton_formulario_crear_evento = new JButton("Crear");
        boton_formulario_crear_evento.setBounds(x,y,ancho,alto);    
        panel_creaEvento.add(boton_formulario_crear_evento);

        x = x + 2*ancho + borde;
        y = y + alto + borde;
        panel_creaEvento.setBounds(0,0,x,y); 
        
        frame_creaEvento.add(panel_creaEvento);
        frame_creaEvento.setSize(x,y+25);
        frame_creaEvento.setTitle("Crear evento");
        frame_creaEvento.setVisible(true);
    }

    // Método que crea la interfaz de la sección de gestión de la cuenta del usuario
    private JPanel PanelCuenta(){

        JPanel panelCuenta = new JPanel();
        panelCuenta.setLayout(null);
        panelCuenta.setVisible(true);
        panelCuenta.setBackground(Color.BLUE); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 10;

        JLabel label_correo = new JLabel("<html><div style='text-align: center;'>" + "Correo:" + "</div></html>");
        label_correo.setBounds(x,y,ancho,alto);
        label_correo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelCuenta.add(label_correo);

        x=x+ancho+espacio_x;
        JLabel label_correo2 = new JLabel("Correo"); //usuario.getEmail();
        label_correo2.setBounds(x,y,ancho,alto);
        label_correo2.setBorder(BorderFactory.createLineBorder(Color.BLACK));        
        panelCuenta.add(label_correo2);

        x=borde+ancho/2;
        y=y+alto+espacio_y;
        boton_cambio_contraseña = new JButton("Cambiar Contraseña"); //usuario.getEmail();
        boton_cambio_contraseña.setBounds(x,y,ancho,alto);
        panelCuenta.add(boton_cambio_contraseña);
        
        y=y+alto+espacio_y;
        boton_cerrarSesion=new JButton("Cerrar Sesión");
        boton_cerrarSesion.setBounds(x,y,ancho,alto);
        boton_cerrarSesion.addActionListener(this);
        panelCuenta.add(boton_cerrarSesion);
        
        y=y+alto+espacio_y;
        boton_salir=new JButton("Salir");
        boton_salir.setBounds(x,y,ancho,alto);
        boton_salir.addActionListener(this);
        panelCuenta.add(boton_salir);

        x = 2*borde + espacio_x + 2*ancho;
        y = y+alto+borde;
        panelCuenta.setBounds(0,0,x,y);
        
        return panelCuenta;
    }

    public static void main(String[] args) {
        System.out.println("La aplicación empieza");
        
        Client ventana = new Client();

        System.out.println("Se está solicitando el inicio de sesión");

        //Scanner input = new Scanner(System.in);
        //input.nextInt(); 
    }
}