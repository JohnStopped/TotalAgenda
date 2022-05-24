import java.io.*;
import java.util.*;
import javax.swing.*;
//import java.awt.*;
import java.awt.Color;
import java.awt.Component.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.net.http.*;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class Client extends JFrame implements ActionListener{

    //Apartado de Inicio de sesión
    private JButton boton_register;

    //Apartado de Crear Cuenta
    private JButton boton_formulario_crear_cuenta,boton_inicio;

    // Apliación
    private JTabbedPane panelDePestanas;
    private JPanel panelCalendario, panelEventos, panelCuenta;

    //Apartado de Calendario
    private JButton boton_mes_mayor,boton_mes_menor;
    private JLabel label_fecha_iterador;
    
    private int mes_actual,anio_actual,dia_actual;
    private int mes_iterador,anio_iterador;
    private JLabel[][] label_day_month;

    // Apartado Evento
    private JButton boton_creaEvento,boton_formulario_crear_evento;

    //Apartado de Cuenta
    private JButton boton_modifica_credenciales, boton_salir, boton_cerrarSesion;
    
    //Información 
    private User usuario;
    private List<Event> eventos;

    // Constructor de la interfaz de la aplicación
    public Client() {
        eventos = new ArrayList();
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
        this.setLayout(new BorderLayout()); //Layout absoluto
        this.setSize(800,600);
        this.setResizable(false); //No redimensionable
        // Se crean la interfaz de la sección de inicio de sesión
        this.add(PanelInicioSesion(),BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Cerrar proceso al cerrar ventana
        this.setVisible(true); //Mostrar JFrame 
    }

    // Método que implementa las acciones de cada ítem de la interfaz
    public void actionPerformed(ActionEvent e) {

        //Apartado de Inicio de sesión      
        if (e.getSource()==boton_formulario_login) {
            System.out.println("Se ha pulsado el botón para enviar el formulario del login");
            InicioSesion();
        }        
        if (e.getSource()==boton_register) {
            System.out.println("Se ha pulsado el botón para ir a crear cuenta");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            this.add(PanelCreaCuenta(),BorderLayout.CENTER);
            this.setTitle("TotalAgenda - Crear Cuenta"); //Título del JFrame
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);   
        }

        //Apartado de Crear Cuenta            
        if (e.getSource()==boton_formulario_crear_cuenta) {
            System.out.println("Se ha pulsado el botón para enviar el formulario para crear la cuenta");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            this.add(PanelInicioSesion(),BorderLayout.CENTER);
            this.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);
        }
        if (e.getSource()==boton_inicio) {
            System.out.println("Se ha pulsado el botón para ir al inicio de sesión");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            this.add(PanelInicioSesion(),BorderLayout.CENTER);
            this.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
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

        //Apartado de creación de eventos
        if (e.getSource()==boton_creaEvento) {
            System.out.println("Se abre la ventana para crear un evento");
            FrameCreaEventos();
        }
        if (e.getSource()==boton_formulario_crear_evento) {
            System.out.println("Se crea un evento");
        }        
        //Apartado de Cuenta
        if (e.getSource()==boton_modifica_credenciales) {
            System.out.println("Se llama a cambiar la contraseña");
            FrameModificaCredenciales();
        }  
        if (e.getSource()==boton_salir) {
            System.out.println("Se llama a finalizar la aplicación");
            System.exit(0);
        }   
        if (e.getSource()==boton_cerrarSesion) {
            System.out.println("Se ha pulsado el botón para ir al inicio de sesión");
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            this.add(PanelInicioSesion(),BorderLayout.CENTER);
            this.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);
        }
    }

    private void InicioSesion(){
        boolean correcto = true;

        /*
        HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://2296n1t8g9.execute-api.eu-west-1.amazonaws.com/totalagenda/initSession"))
                .setHeader("User-Agent", "application/json") // add request header
                .build();
 
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
 
        // print response headers
        HttpHeaders headers = response.headers();
        headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
 
        // print status code
        System.out.println(response.statusCode());
 
        // print response body
        System.out.println(response.body());
        */

        // Según si las credenciales son válidas o no se accede a la aplicación o se genera un error
        if (correcto){
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            Pestañas();
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);                
        }else{
            FrameError("El email o la contraseña son incorrectos");
        }
    }

    private void FrameError(String cadena){
        JDialog frameError = new JDialog(this);
        
        JPanel panelError = new JPanel();
        panelError.setLayout(null);
        panelError.setVisible(true);

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 10;

        JLabel label_error = new JLabel(cadena);
        label_error.setBounds(x,y,2*ancho,alto);
        panelError.add(label_error);

        x = x + 2*ancho + borde;
        y = y + alto + borde;
        panelError.setBounds(0,0,x,y); 
        
        frameError.add(panelError);
        frameError.setSize(x,y+25);
        frameError.setTitle("Crear evento");
        frameError.setVisible(true);  
    }

    // Método que crea la interfaz correspondiente al incio de sesión
    private JPanel PanelInicioSesion(){
        
        JPanel panelInicioSesion = new JPanel();
        panelInicioSesion.setLayout(null);
        panelInicioSesion.setVisible(true);
        //panelInicioSesion.setBackground(Color.GREEN); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde_x = 192;
        int borde_y = 200;
        int x = borde_x;
        int y = borde_y;
        int espacio_x = 10;
        int espacio_y = 20;

        // Se crean las etiquetas y botones, y se añaden a la ventana
        //FILA1
        JLabel label_correo_iniciosesion = new JLabel("Correo");
        label_correo_iniciosesion.setBounds(x,y,ancho,alto);
        panelInicioSesion.add(label_correo_iniciosesion);

        x=x+ancho+espacio_x;
        JTextField field_correo_iniciosesion = new JTextField();
        field_correo_iniciosesion.setBounds(x,y,ancho,alto);
        panelInicioSesion.add(field_correo_iniciosesion);
        
        //FILA2
        x=borde_x;
        y=y+alto+espacio_x;
        JLabel label_pass_iniciosesion = new JLabel("Contraseña");
        label_pass_iniciosesion.setBounds(x,y,ancho,alto);    
        panelInicioSesion.add(label_pass_iniciosesion);

        x=x+ancho+espacio_x;
        JPasswordField field_pass_iniciosesion = new JPasswordField();
        field_pass_iniciosesion.setBounds(x,y,ancho,alto);   
        panelInicioSesion.add(field_pass_iniciosesion);

        //FILA3
        x=borde_x;
        y=y+alto+espacio_x;
        JButton boton_formulario_login = new JButton("Inicar Sesión");
        boton_formulario_login.setBounds(x,y,ancho,alto-5);
        boton_formulario_login.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if (anio_fecha.getText().isEmpty() || dia_fecha.getText().isEmpty() || field_titulo.getText().isEmpty() || hora_fecha.getText().isEmpty() || min_fecha.getText().isEmpty()){
                    FrameError("Hay campos obligatorios vacíos");
                }else{
                    Event evento = new Event();
                    evento.setTitulo(field_titulo.getText());
                    evento.setColor((String) menuColores.getSelectedItem());
                    Integer auxinteger = new Integer(0);
                    evento.setDate(new GregorianCalendar(auxinteger.parseInt(anio_fecha.getText()),auxinteger.parseInt(mes_fecha.getText()),auxinteger.parseInt(dia_fecha.getText()),auxinteger.parseInt(hora_fecha.getText()),auxinteger.parseInt(min_fecha.getText())));
                    evento.setNote(field_nota.getText());
                    if(!anio_recordatorio.getText().isEmpty() && !mes_recordatorio.getText().isEmpty() && !dia_recordatorio.getText().isEmpty() && !hora_recordatorio.getText().isEmpty() && !min_recordatorio.getText().isEmpty()){
                        evento.setAdvice_date(new GregorianCalendar(auxinteger.parseInt(anio_recordatorio.getText()),auxinteger.parseInt(mes_recordatorio.getText()),auxinteger.parseInt(dia_recordatorio.getText()),auxinteger.parseInt(hora_recordatorio.getText()),auxinteger.parseInt(min_recordatorio.getText())));
                    }
                    System.out.println(evento.toString());
                    eventos.add(evento);
                    frameCreaEventos.dispose();
                }
            }
        });              
        panelInicioSesion.add(boton_formulario_login);
        
        x=x+ancho+espacio_x;
        boton_register = new JButton("Crear Cuenta");
        boton_register.setBounds(x,y,ancho,alto-5);
        boton_register.addActionListener(this);    
        panelInicioSesion.add(boton_register);

        x = x + ancho + borde_x;
        y = y + alto-5 + borde_y;
        panelInicioSesion.setBounds(0,0,x,y);

        return panelInicioSesion;
    }

    // Método que crea la interfaz correspondiente a la creación de una cuenta
    private JPanel PanelCreaCuenta(){

        JPanel panelCreaCuenta = new JPanel();
        panelCreaCuenta.setLayout(null);
        panelCreaCuenta.setVisible(true);
        //panelCreaCuenta.setBackground(Color.GREEN); // Color del fondo


        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde_x = 300;
        int borde_y = 100;
        int x = borde_x;
        int y = borde_y;
        int espacio_x = 10;
        int espacio_y = 20;

        // Se crean las etiquetas y botones, y se añaden a la ventana
        JLabel label_correo = new JLabel("Correo");
        label_correo.setBounds(x,y,ancho,alto);
        panelCreaCuenta.add(label_correo);

        y = y + alto + espacio_y;
        JTextField field_correo = new JTextField();
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

        x = borde_x + ancho + borde_x;
        y = y + alto-5 + borde_y;
        panelCreaCuenta.setBounds(0,0,x,y);

        return panelCreaCuenta;
    }

    // Método para crear las Pestañas de la aplicación
    private void Pestañas(){

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
        //panelCalendario.setBackground(Color.GREEN); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int lado = 60;
        int ancho = 60;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;
        int i = 0;
        int j = 0;
        int int_aux;

        boton_mes_menor = new JButton("ant");
        boton_mes_menor.setBounds(x,y,ancho,alto);
        boton_mes_menor.addActionListener(this);     
        panelCalendario.add(boton_mes_menor);

        x = x+ancho;
        boton_mes_mayor = new JButton("sig");
        boton_mes_mayor.setBounds(x,y,ancho,alto);
        boton_mes_mayor.addActionListener(this);     
        panelCalendario.add(boton_mes_mayor);

        x = x+ancho+espacio_x;
        label_fecha_iterador = new JLabel(String.valueOf(mes_iterador)+"/"+String.valueOf(anio_iterador));
        label_fecha_iterador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label_fecha_iterador.setBounds(x,y,ancho,alto);   
        panelCalendario.add(label_fecha_iterador);

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
        label_fecha_iterador.setText(String.valueOf(mes_iterador)+"/"+String.valueOf(anio_iterador));
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
        barraSuperior.add(label_fecha);

        boton_creaEvento = new JButton("Crear evento");
        boton_creaEvento.addActionListener(this);
        barraSuperior.add(boton_creaEvento);
        
        panelEventos.add(barraSuperior,BorderLayout.NORTH);

        JPanel areaListaEventos = new JPanel();
        areaListaEventos.setLayout(null);
        areaListaEventos.setVisible(true);
        // Se definen las coordenadas para colocar los objetos
        int lado = 60;
        int ancho = 60;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;
        int i = 0;
        int j = 0;
        int int_aux;
        /*
        for (i=0; i<;i++){
            for each 
        }
        */
        JScrollPane scrollBar = new JScrollPane(areaListaEventos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelEventos.add(scrollBar,BorderLayout.CENTER);

        return panelEventos;
    }

    private void FrameCreaEventos(){
        JDialog frameCreaEventos = new JDialog(this);
        
        JPanel panelCreaEvento = new JPanel();
        panelCreaEvento.setLayout(null);
        panelCreaEvento.setVisible(true);

        // Se definen las coordenadas para colocar los objetos
        int ancho = 120;
        int alto = 30;
        int borde = 10;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 10;

        // Se crean las etiquetas y botones, y se añaden a la ventana
        //FILA1
        JTextField field_titulo = new JTextField("Título");
        field_titulo.setBounds(x,y,2*ancho,alto);
        panelCreaEvento.add(field_titulo);

        x = x+2*ancho+espacio_x*2;
        String[] colores = {"blanco","rojo","verde","azul","amarillo","naranja","negro","gris"};
        JComboBox<String> menuColores = new JComboBox<String>(colores);
        menuColores.setBounds(x,y,ancho*2/3,alto);
        panelCreaEvento.add(menuColores);

        //FILA2
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_fecha = new JLabel("Fecha: ");
        label_fecha.setBounds(x,y,ancho,alto);    
        panelCreaEvento.add(label_fecha);

        x = x+ancho+espacio_x;
        JTextField anio_fecha = new JTextField(String.valueOf(anio_actual));
        anio_fecha.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(anio_fecha);
        
        x = x+ancho/3+espacio_x;
        JTextField mes_fecha = new JTextField(String.valueOf(mes_actual));
        mes_fecha.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(mes_fecha);

        x = x+ancho/3+espacio_x;
        JTextField dia_fecha = new JTextField(String.valueOf(dia_actual));
        dia_fecha.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(dia_fecha);

        x = x+ancho/3+2*espacio_x;
        JTextField hora_fecha = new JTextField();
        hora_fecha.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(hora_fecha); 

        x = x+ancho/3;
        JLabel label_sep = new JLabel(":");
        label_sep.setBounds(x,y,10,alto);    
        panelCreaEvento.add(label_sep);

        x = x+10;                
        JTextField min_fecha = new JTextField();
        min_fecha.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(min_fecha);

        //FILA3
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_recordatorio = new JLabel("Fecha de aviso: ");
        label_recordatorio.setBounds(x,y,ancho,alto);    
        panelCreaEvento.add(label_recordatorio);

        x = x+ancho+espacio_x;
        JTextField anio_recordatorio = new JTextField();
        anio_recordatorio.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(anio_recordatorio);
        
        x = x+ancho/3+espacio_x;
        JTextField mes_recordatorio = new JTextField();
        mes_recordatorio.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(mes_recordatorio);

        x = x+ancho/3+espacio_x;
        JTextField dia_recordatorio = new JTextField();
        dia_recordatorio.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(dia_recordatorio);        

        x = x+ancho/3+2*espacio_x;
        JTextField hora_recordatorio = new JTextField();
        hora_recordatorio.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(hora_recordatorio); 

        x = x+ancho/3;
        JLabel label_sep2 = new JLabel(":");
        label_sep2.setBounds(x,y,10,alto);    
        panelCreaEvento.add(label_sep2);

        x = x+10;                
        JTextField min_recordatorio = new JTextField();
        min_recordatorio.setBounds(x,y,ancho/3,alto);
        panelCreaEvento.add(min_recordatorio);

        //FILA4
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_nota = new JLabel("Nota: ");
        label_nota.setBounds(x,y,ancho,alto);    
        panelCreaEvento.add(label_nota);

        //FILA5
        x = borde;
        y=y+alto+espacio_y;
        JTextArea field_nota = new JTextArea();
        field_nota.setBounds(x,y,3*ancho,5*alto);    
        panelCreaEvento.add(field_nota);

        //FILA6
        x = borde;
        y=y+5*alto+espacio_y;
        boton_formulario_crear_evento = new JButton("Crear");
        boton_formulario_crear_evento.setBounds(x,y,ancho,alto);
        boton_formulario_crear_evento.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if (anio_fecha.getText().isEmpty() || dia_fecha.getText().isEmpty() || field_titulo.getText().isEmpty() || hora_fecha.getText().isEmpty() || min_fecha.getText().isEmpty()){
                    FrameError("Hay campos obligatorios vacíos");
                }else{
                    Event evento = new Event();
                    evento.setTitulo(field_titulo.getText());
                    evento.setColor((String) menuColores.getSelectedItem());
                    Integer auxinteger = new Integer(0);
                    evento.setDate(new GregorianCalendar(auxinteger.parseInt(anio_fecha.getText()),auxinteger.parseInt(mes_fecha.getText()),auxinteger.parseInt(dia_fecha.getText()),auxinteger.parseInt(hora_fecha.getText()),auxinteger.parseInt(min_fecha.getText())));
                    evento.setNote(field_nota.getText());
                    if(!anio_recordatorio.getText().isEmpty() && !mes_recordatorio.getText().isEmpty() && !dia_recordatorio.getText().isEmpty() && !hora_recordatorio.getText().isEmpty() && !min_recordatorio.getText().isEmpty()){
                        evento.setAdvice_date(new GregorianCalendar(auxinteger.parseInt(anio_recordatorio.getText()),auxinteger.parseInt(mes_recordatorio.getText()),auxinteger.parseInt(dia_recordatorio.getText()),auxinteger.parseInt(hora_recordatorio.getText()),auxinteger.parseInt(min_recordatorio.getText())));
                    }
                    System.out.println(evento.toString());
                    eventos.add(evento);
                    frameCreaEventos.dispose();
                }
            }
        }); 

        panelCreaEvento.add(boton_formulario_crear_evento);

        x = x + 3*ancho + borde;
        y = y + alto + borde;
        panelCreaEvento.setBounds(0,0,x,y); 
        
        frameCreaEventos.add(panelCreaEvento);
        frameCreaEventos.setSize(x,y+25);
        frameCreaEventos.setTitle("Crear evento");
        frameCreaEventos.setVisible(true);
    }

    // Método que crea la interfaz de la sección de gestión de la cuenta del usuario
    private JPanel PanelCuenta(){

        JPanel panelCuenta = new JPanel();
        panelCuenta.setLayout(null);
        panelCuenta.setVisible(true);
        //panelCuenta.setBackground(Color.RED); // Color del fondo

        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde_x = 300;
        int borde_y = 200;
        int x = borde_x;
        int y = borde_y;
        int espacio_x = 10;
        int espacio_y = 10;

        JLabel label_correo = new JLabel("Correo");
        label_correo.setBounds(x,y,ancho,alto);
        panelCuenta.add(label_correo);

        y=y+alto+espacio_y;
        JLabel label_correo2 = new JLabel("usuario.getEmail()");
        label_correo2.setBounds(x,y,ancho,alto);
        panelCuenta.add(label_correo2);

        y=y+alto+espacio_y;
        boton_modifica_credenciales = new JButton("Cambiar credenciales");
        boton_modifica_credenciales.setBounds(x,y,ancho,alto);
        boton_modifica_credenciales.addActionListener(this);
        panelCuenta.add(boton_modifica_credenciales);
        
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

        x = 2*borde_x + espacio_x + ancho;
        y = y+alto+borde_y;
        panelCuenta.setBounds(0,0,x,y);
        
        return panelCuenta;
    }

    private void FrameModificaCredenciales(){
        JDialog frameModificaCredenciales = new JDialog(this);
        
        JPanel panelModificaCredenciales = new JPanel();
        panelModificaCredenciales.setLayout(null);
        panelModificaCredenciales.setVisible(true);

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
        JLabel label_correo = new JLabel("Correo");
        label_correo.setBounds(x,y,ancho,alto);
        panelModificaCredenciales.add(label_correo);
        
        x = x+ancho+espacio_x;
        JTextField field_correo = new JTextField("usuario.getEmail()");
        field_correo.setBounds(x,y,ancho,alto);
        panelModificaCredenciales.add(field_correo);

        //FILA2
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_password1 = new JLabel("Nueva contraseña: ");
        label_password1.setBounds(x,y,ancho,alto);    
        panelModificaCredenciales.add(label_password1);

        x = x+ancho+espacio_x;
        JPasswordField field_password1 = new JPasswordField();
        field_password1.setBounds(x,y,ancho,alto);    
        panelModificaCredenciales.add(field_password1);

        //FILA3
        x = borde;
        y=y+alto+espacio_y;       
        JLabel label_password2 = new JLabel("Nueva contraseña: ");
        label_password2.setBounds(x,y,ancho,alto);    
        panelModificaCredenciales.add(label_password2);

        x = x+ancho+espacio_x;
        JPasswordField field_password2 = new JPasswordField();
        field_password2.setBounds(x,y,ancho,alto);    
        panelModificaCredenciales.add(field_password2);

        //FILA4
        x = borde;
        y=y+alto+espacio_y;       
        JLabel label_password3 = new JLabel("Antigua contraseña: ");
        label_password3.setBounds(x,y,ancho,alto);    
        panelModificaCredenciales.add(label_password3);

        x = x+ancho+espacio_x;
        JPasswordField field_password3 = new JPasswordField();
        field_password3.setBounds(x,y,ancho,alto);    
        panelModificaCredenciales.add(field_password3);

        //FILA5
        x = borde;
        y=y+alto+espacio_y;
        JButton boton_formulario_modifica_credenciales = new JButton("Cambiar credenciales");
        boton_formulario_modifica_credenciales.setBounds(x,y,ancho,alto);
        boton_formulario_modifica_credenciales.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if (field_correo.getText().isEmpty() || field_password3.getText().isEmpty() ){
                    FrameError("Hay campos obligatorios vacíos");
                }else if( (field_password1.getText().isEmpty()) != (field_password2.getText().isEmpty()) ){
                    FrameError("Hay campos obligatorios vacíos");
                }else{
                    boolean correcto = false;
                    
                    //Se accede a la base de datos con el usuario y contraseña introducidas usuario.getEmail() field_password3.getText()
                    

                    if (correcto == true){
                        if(field_password1.getText().isEmpty() == false){
                            //se hace el update de correo y contraseña
                            //usuario.setEmail() = field_correo.getText();
                        }else{
                            //Se hace el update del correo unicamente
                            //usuario.setEmail() = field_correo.getText();
                        }
                        frameModificaCredenciales.dispose();
                    }
                }
            }
        }); 

        panelModificaCredenciales.add(boton_formulario_modifica_credenciales);

        x = x + 2*ancho + borde;
        y = y + alto + borde;
        panelModificaCredenciales.setBounds(0,0,x,y); 
        
        frameModificaCredenciales.add(panelModificaCredenciales);
        frameModificaCredenciales.setSize(x,y+25);
        frameModificaCredenciales.setTitle("Modificar credenciales");
        frameModificaCredenciales.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("La aplicación empieza");

        Client ventana = new Client();

    }
}