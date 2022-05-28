import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

//import java.awt.*;
import java.awt.Color;
import java.awt.Component.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client extends JFrame implements ActionListener{

    private JFrame ventana;
    
    private final HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();

    //Información 
    private User usuario;
    private List<Event> eventos;

    // Aplicación
    private JTabbedPane panelDePestanas;
    private JPanel panelCalendario, panelEventos, panelCuenta;

    //Apartado de Calendario
    private JButton boton_mes_mayor,boton_mes_menor;
    private JLabel label_fecha_iterador;
    
    private int mes_actual,anio_actual,dia_actual;
    private int mes_iterador,anio_iterador;
    private JButton[][] label_day_month;

    // Apartado Evento
    private JButton boton_creaEvento;
    private JPanel areaListaEventos;
    //Apartado de Cuenta
    private JButton boton_modifica_credenciales, boton_salir, boton_cerrarSesion;

    // Constructor de la interfaz de la aplicación
    public Client() {
        // Esta variable nos permitirá acceder al JFrame desde las funciones de los botones y así poder realizar acciones sobre el.
        ventana = this;
        
        // Esta variable contendrá los eventos del usuario que existan
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
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Para que la ventana se cierre
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //Para que no se haga nada y siga el programa
        // Gestionamos el evento de cerrar sesión
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("Se ha pulsado el boton para cerrar la ventana");
                if(usuario!=null){
                    close();
                }
                System.exit(0);                    
            }
        });        
        
        // Se crean la interfaz de la sección de inicio de sesión
        this.add(PanelInicioSesion(),BorderLayout.CENTER);
        this.setVisible(true);   //Mostrar JFrame 
    }

    private HttpResponse<String> send(String funcion, String cadena) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(new StringBuilder(cadena).toString()))
                .uri(URI.create("https://2296n1t8g9.execute-api.eu-west-1.amazonaws.com/totalagenda/"+funcion))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("//-----");
        System.out.println("//La cadena Json enviada es:\n" + "//   " + cadena);         
        // print status code
        System.out.println("//El status de la petición es: " + response.statusCode());
        // print response body
        System.out.println("//La respuesta de la petición es: \n" + "//   " + response.body());
        System.out.println("//-----");
        return response;        
    }

    // Método que de manera genérica crea una ventana externa con un texto
    private void FrameError(String cadena){
        System.out.println("ERROR -- "+cadena);
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
        frameError.setTitle("Error");
        frameError.setVisible(true);  
    }

    // Método que implementa las acciones de items de la interfaz que no implementan la acción directamente
    // Todos los elementos cuya acción se encuentre en este método deben ser variables globales
    public void actionPerformed(ActionEvent e) {

        //Apartado de Calendario
        if (e.getSource()==boton_mes_menor) {
            decrementaMesIterador();
            System.out.println("#");
            System.out.println("#Se ha pulsado el boton del calendario grafico para ir al mes anterior");
            System.out.println("#Se va al mes anterior: " + mes_iterador);
            System.out.println("#");            
            cambiaCalendario();
        }        
        if (e.getSource()==boton_mes_mayor) {
            incrementaMesIterador();
            System.out.println("#");
            System.out.println("#Se ha pulsado el boton del calendario grafico para ir al mes siguiente");
            System.out.println("#Se va al mes siguiente: " + mes_iterador);
            System.out.println("#");                       
            cambiaCalendario();
        }

        //Apartado de creación de eventos
        if (e.getSource()==boton_creaEvento) {
            System.out.println("BOTON --> Ventana Crear Evento");
            FrameCreaEventos();
        }

        //Apartado de Cuenta
        if (e.getSource()==boton_modifica_credenciales) {
            System.out.println("BOTON --> Ventana Modificar Credenciales");
            FrameModificaCredenciales();
        }  
        if (e.getSource()==boton_salir) {
            System.out.println("BOTON --> Fin Aplicación");
            close();
            System.exit(0);            
        }   
        if (e.getSource()==boton_cerrarSesion) {
            System.out.println("BOTON --> Cierra Sesión --> Panel Inicio Sesión");
            close();
            usuario = new User();
            this.getContentPane().removeAll();
            this.getContentPane().invalidate();           
            this.add(PanelInicioSesion(),BorderLayout.CENTER);
            this.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
            this.getContentPane().revalidate();
            this.getContentPane().setVisible(true);
        }
    }

    private List<Event> solicitaEvents(){

        List<Event> events=null;
        try{
            String cadenaJson = "{\"session_id\":"+String.valueOf(usuario.getIdSesion())+"}";
            HttpResponse<String> response = send("listEvents", cadenaJson);
            
            if (response.statusCode() == 200){
                events = processListEvents(response.body());
            }
        }catch(Exception ex){

        }

        return events;
    }

    private List<Event> processListEvents(String body) {

        /*
        {
            "state": 1, 
            "desc": "List of events recovered successfully", 
            "list_events": 
                [
                    {
                      "event_id": 41618, 
                      "date": "2022-05-27 17:00:00", 
                      "advice_date": null, 
                      "name": "Hacer trabajo RSSA", 
                      "color": "red", 
                      "note": "si ome"
                    }, 
                    {
                      "event_id": 47229, 
                      "date": "2022-05-27 17:00:00", 
                      "advice_date": null, 
                      "name": "Hacer TFG", 
                      "color": "red", 
                      "note": ""
                    }, 

                ]
        }

        */
        List<Event> list = null;

        String in_body = body.substring(1,body.length()-1); //delete first keys

        String[] json_att = in_body.split(","); //divide in every attribute: value

        int state = new Integer(json_att[0].split(":")[1].trim()).intValue(); //Recover state

        if (state == 1) {
            list = new ArrayList();
            String list_events = in_body.substring(in_body.indexOf('[')+1, in_body.indexOf(']'));
            
            List<String> events = new ArrayList<String>();

            boolean flag = true;
            int keyUp;
            int keyDown;
            int indexOffset=0;

            while (flag) {

                keyUp = list_events.indexOf('{', indexOffset);
                keyDown = list_events.indexOf('}', indexOffset);

                if (keyUp != -1 && keyDown!=-1){
                    events.add(list_events.substring(keyUp+1,keyDown));
                    indexOffset = keyDown+1;
                }
                else {
                    flag = false;
                }
            }

            int event_id;
            String str_date;
            String str_advice_date;
            String name;
            String color;               
            String note;
            String[] date_pt;
            String[] advice_date_pt;
            Date date; 
            Date advice_date;

            for (String str_event : events) {
                String[] att_value = str_event.split(",");

                event_id = new Integer(att_value[0].split(":")[1].trim()).intValue();

                str_date = att_value[1].split(": ")[1].trim();

                str_date = str_date.substring(1,str_date.length()-1);
                date_pt = str_date.split(" ");


                str_advice_date = att_value[2].split(": ")[1].trim();
                

                if(str_advice_date.charAt(0)=='\"'){   
                    str_advice_date = str_advice_date.substring(1,str_advice_date.length()-1);
                    advice_date_pt = str_advice_date.split(" ");
                    advice_date = new Date(new Integer(advice_date_pt[0].split("-")[0]).intValue(),new Integer(advice_date_pt[0].split("-")[1]).intValue(),new Integer(advice_date_pt[0].split("-")[2]).intValue(),new Integer(advice_date_pt[1].split(":")[0]).intValue(),new Integer(advice_date_pt[1].split(":")[1]).intValue(),new Integer(advice_date_pt[1].split(":")[2]).intValue());
                }else {
                    advice_date = null;
                }

                name = att_value[3].split(":")[1].trim();
                name = name.substring(1,name.length()-1); 

                color = att_value[4].split(":")[1].trim();
                color = color.substring(1,color.length()-1);


                note = att_value[5].split(":")[1].trim();
                if (note.length() > 2)
                    note = note.substring(1,note.length()-1);
                else 
                    note = null;
                
                date = new Date(new Integer(date_pt[0].split("-")[0]).intValue(),new Integer(date_pt[0].split("-")[1]).intValue(),new Integer(date_pt[0].split("-")[2]).intValue(),new Integer(date_pt[1].split(":")[0]).intValue(),new Integer(date_pt[1].split(":")[1]).intValue(),new Integer(date_pt[1].split(":")[2]).intValue());
                

                Event obj_event = new Event(event_id,usuario.getEmail(),date,advice_date,name,color,note);
                System.out.println(obj_event.getId());
                System.out.println(obj_event.getEmail());
                System.out.println(obj_event.getDate());
                System.out.println(obj_event.getAdvice_date());
                System.out.println(obj_event.getTitulo());
                System.out.println(obj_event.getColor());
                System.out.println(obj_event.getNote());
                System.out.println("");
                list.add(obj_event);
            }       

        }
        return list;

    }

    private void close(){

        try{
            String cadenaJson = "{\"email\": \""+usuario.getEmail()+"\", \"passwd\": \""+usuario.getPasswd()+"\"}";
            HttpResponse<String> response = send("closeSession",cadenaJson);

            if (response.statusCode() == 200){
                Integer int_aux = new Integer(0);

                //{"state": 0, "desc": "Session already was initializated", "session-id": 0}
                // Se quitan las llaves al body (primer y ultimo caracter)
                String strAux = response.body().substring(1,response.body().length()-1);
               
                // Se hace un split por comas
                String [] atributos = strAux.split(",");
                
            }
        }catch(Exception ex){
            System.out.println("Excepción al cerrar sesión: \n" + ex);
        }
        //ventana.dispose();
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

        //Definimos lo que realiza el botón del formulario de inicio se sesión
        boton_formulario_login.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println("##");
                System.out.println("#");
                System.out.println("BOTON - Formulario del login");

                // En caso de que esten vacíos los campos se devuelve un error en una ventana flotante              
                if (field_correo_iniciosesion.getText().isEmpty() || field_pass_iniciosesion.getText().isEmpty() ){
                    FrameError("Hay campos obligatorios vacíos");
                }else{
                    // En caso en que los campos no estén vacíos, se procede a hacer la consulta
                    boolean correcto = false;

                    try{
                        String cadenaJson = "{\"email\": \""+field_correo_iniciosesion.getText()+"\", \"passwd\": \""+field_pass_iniciosesion.getText()+"\"}";
                        HttpResponse<String> response = send("initSession",cadenaJson);

                        if (response.statusCode() == 200){
                            Integer int_aux = new Integer(0);
                            usuario = new User();
                            //{"state": 0, "desc": "Session already was initializated", "session-id": 0}
                            // Se quitan las llaves al body (primer y ultimo caracter)
                            String strAux = response.body().substring(1,response.body().length()-1);
                            // Se hace un split por comas
                            String [] atributos = strAux.split(",");
                            // Extraemos la información de los elemetnos que nos interesan, el 3

                            if (int_aux.parseInt(atributos[0].split(":")[1].trim()) == 1){
                                usuario.setIdSesion(int_aux.parseInt(atributos[2].split(":")[1].trim()));
                                usuario.setEmail(field_correo_iniciosesion.getText());
                                usuario.setPasswd(field_pass_iniciosesion.getText());
                                correcto = true;
                            }
                        }

                        // Según si las credenciales son válidas o no se accede a la aplicación o se genera un error
                        if (correcto){
                            ventana.getContentPane().removeAll();
                            ventana.getContentPane().invalidate();
                            eventos = solicitaEvents();
                            Pestañas();
                            ventana.getContentPane().revalidate();
                            ventana.getContentPane().setVisible(true);                
                        }else{
                            FrameError("El email o la contraseña son incorrectos");
                        }

                    }catch(Exception ex){
                        FrameError("Ha ocurrido un fallo, vuelva a intentarlo");
                        System.out.println("Excepción:\n"+ex);
                    }

                }
                System.out.println("#");                
                System.out.println("##");
            }
        });              
        panelInicioSesion.add(boton_formulario_login);
        
        x=x+ancho+espacio_x;
        JButton boton_register = new JButton("Crear Cuenta");
        boton_register.setBounds(x,y,ancho,alto-5);

        //Definimos lo que realiza el botón de registro
        boton_register.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Se ha pulsado el botón para ir a crear cuenta");
                // Se mostrará en la ventana el formulario para crear un usuario
                ventana.getContentPane().removeAll();
                ventana.getContentPane().invalidate();           
                ventana.add(PanelCreaCuenta(),BorderLayout.CENTER);
                ventana.setTitle("TotalAgenda - Crear Cuenta"); //Título del JFrame
                ventana.getContentPane().revalidate();
                ventana.getContentPane().setVisible(true); 
            }
        });         
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
        JPasswordField field_pass1 = new JPasswordField();
        //JTextField field_pass = new JTextField("contraseña",20);
        field_pass1.setBounds(x,y,ancho,alto);   
        panelCreaCuenta.add(field_pass1);

        y = y + alto + espacio_y;
        JPasswordField field_pass2 = new JPasswordField();
        field_pass2.setBounds(x,y,ancho,alto);   
        panelCreaCuenta.add(field_pass2);

        y = y + alto + espacio_y;
        JButton boton_formulario_crear_cuenta = new JButton("Crear Cuenta");
        boton_formulario_crear_cuenta.setBounds(x,y,ancho,alto-5);

        //Definimos lo que realiza el botón del formulario para crear un nuevo usuario
        boton_formulario_crear_cuenta.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println("##");
                System.out.println("#");
                System.out.println("BOTON -- Formulario crear cuenta");
                // En caso de que esten vacíos los campos se devuelve un error en una ventana flotante              
                if (field_correo.getText().isEmpty() || field_pass1.getText().isEmpty() || field_pass2.getText().isEmpty() ){
                    FrameError("Hay campos obligatorios vacíos");
                }else if (field_pass1.getText().contentEquals(field_pass2.getText())) {
                    // En caso en que los campos no estén vacíos, y las contraseñas sean iguales se crea el usuario y se accede a la ventana de inicio de sesión

                    // Se añade al usuario
                    try{
                        HttpResponse<String> response = send("createUser","{\"email\": \""+field_correo.getText()+"\", \"passwd\": \""+field_pass1.getText()+"\"}");

                        if (response.statusCode() == 200){
                            Integer int_aux = new Integer(0);
                            usuario = new User();
                            //{"state": 0, "desc": "Session already was initializated", "session-id": 0}
                            // Se quitan las llaves al body (primer y ultimo caracter)
                            String strAux = response.body().substring(1,response.body().length()-1);

                            // Se hace un split por comas
                            String [] atributos = strAux.split(",");
                                                        
                            if(int_aux.parseInt(atributos[0].split(":")[1].trim())!=1){
                                FrameError("El usuario ya existe");
                            }
                        }else{
                            FrameError("Ha ocurrido algún problema");
                        }

                    }catch(Exception ex){
                        FrameError("Ha ocurrido algún problema, vuelva a intentarlo :");
                        System.out.println("Excepción:\n"+ex);
                    }

                    // Se muestra la interfaz de inicio de sesión
                    ventana.getContentPane().removeAll();
                    ventana.getContentPane().invalidate();           
                    ventana.add(PanelInicioSesion(),BorderLayout.CENTER);
                    ventana.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
                    ventana.getContentPane().revalidate();
                    ventana.getContentPane().setVisible(true);              
                }else{
                    FrameError("Las contraseñas no son iguales");
                }
            System.out.println("#");
            System.out.println("##");
            }
        });     
        panelCreaCuenta.add(boton_formulario_crear_cuenta);

        y = y + alto + espacio_y;
        JButton boton_inicio = new JButton("Iniciar Sesión");
        boton_inicio.setBounds(x,y,ancho,alto-5);

        //Definimos lo que realiza el botón de acceso a inicio
        boton_inicio.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                // Se muestra la itnerfaz para realizar el inicio de sesión
                System.out.println("BOTON --> Inicio Sesión");
                ventana.getContentPane().removeAll();
                ventana.getContentPane().invalidate();           
                ventana.add(PanelInicioSesion(),BorderLayout.CENTER);
                ventana.setTitle("TotalAgenda - Inicio Sesión"); //Título del JFrame
                ventana.getContentPane().revalidate();
                ventana.getContentPane().setVisible(true);      
            }
        });           
        panelCreaCuenta.add(boton_inicio);

        x = borde_x + ancho + borde_x;
        y = y + alto-5 + borde_y;
        panelCreaCuenta.setBounds(0,0,x,y);

        return panelCreaCuenta;
    }

    private Color color(String color){
        String[] colores = {"white","rojo","verde","azul","amarillo","naranja","gris"};
        Color res = null;
        if (color.contentEquals("white")){
            res = Color.WHITE;
        }else if (color.contentEquals("blanco")){
            res = Color.WHITE;
        }else if (color.contentEquals(colores[1])){
            res = Color.RED;
        }        else if (color.contentEquals(colores[2])){
            res = Color.GREEN;
        }else if (color.contentEquals(colores[3])){
            res = Color.BLUE;
        }else if (color.contentEquals(colores[4])){
            res = Color.YELLOW;
        }else if (color.contentEquals(colores[5])){
            res = Color.ORANGE;
        }else if (color.contentEquals(colores[6])){
            res = Color.GRAY;
        }
        return res;
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

        JPanel panelGrafico = new JPanel();
        panelGrafico.setLayout(null);
        panelGrafico.setVisible(true);
        

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
        panelGrafico.add(boton_mes_menor);

        x = x+ancho;
        boton_mes_mayor = new JButton("sig");
        boton_mes_mayor.setBounds(x,y,ancho,alto);
        boton_mes_mayor.addActionListener(this);     
        panelGrafico.add(boton_mes_mayor);

        x = x+ancho+espacio_x;
        label_fecha_iterador = new JLabel(String.valueOf(mes_iterador)+"/"+String.valueOf(anio_iterador));
        label_fecha_iterador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label_fecha_iterador.setBounds(x,y,ancho,alto);   
        panelGrafico.add(label_fecha_iterador);

        x = x+ancho+espacio_x;
        boton_creaEvento = new JButton("Crear evento");
        boton_creaEvento.addActionListener(this);
        boton_creaEvento.setBounds(x,y,3*ancho,alto);
        panelGrafico.add(boton_creaEvento);

        // Se crea la fila con los días del calendario grafico
        x = borde;
        y = y+alto+espacio_y;
        label_day_month = new JButton[6][7];
        String[] dias= {"L","M","X","J","V","S","D"};
        for (j=0; (j<7) ;j++){
            label_day_month[i][j] = new JButton("<html><div style='text-align: center;'>"+dias[j]+"</div></html>");
            label_day_month[i][j].setEnabled(false);
            label_day_month[i][j].setForeground(Color.BLACK);
            label_day_month[i][j].setBackground(Color.WHITE);
            label_day_month[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label_day_month[i][j].setBounds(x,y,lado,lado/2);
            panelGrafico.add(label_day_month[i][j]);
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
                label_day_month[i][j] = new JButton("<html><div style='text-align: center;'>"+String.valueOf(int_aux)+"</div></html>");
                label_day_month[i][j].setEnabled(true);
            }else{
                label_day_month[i][j] = new JButton("");
                label_day_month[i][j].setEnabled(false);
            }

            label_day_month[i][j].setForeground(Color.BLACK);
            label_day_month[i][j].setBackground(Color.WHITE);
            label_day_month[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            label_day_month[i][j].setBounds(x,y,lado,lado);
            panelGrafico.add(label_day_month[i][j]);
            x = x+lado;
          }
          y = y+lado;
        }

        x = x+borde;
        y = y+borde;
        panelGrafico.setBounds(0,0,x,y);
        panelCalendario.add(panelGrafico);
        JPanel paneleventos = PanelEventos();
        paneleventos.setBounds(x,0,300,y);
        panelCalendario.add(paneleventos);

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
                label_day_month[i][j].setText("<html><div style='text-align: center;'>"+String.valueOf(int_aux)+"<br>"+"</div></html>");
                label_day_month[i][j].setEnabled(true);
            }else{
                label_day_month[i][j].setText("");
                label_day_month[i][j].setEnabled(false);
            }
          }
          
        }
    }

    // Método que crea la interfaz de la sección de eventos dentro de la pestaña calendario  
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

        panelEventos.add(barraSuperior,BorderLayout.NORTH);

        JPanel areaListaEventos = new JPanel();
        areaListaEventos.setLayout(null);
        // Se definen las coordenadas para colocar los objetos
        int ancho = 200;
        int alto = 30;
        int borde = 20;
        int x = borde;
        int y = borde;
        int espacio_x = 10;
        int espacio_y = 20;
        int i = 0;
        int j = 1;

        Calendar fecha_actual = new GregorianCalendar();
        //Comparamos unicamente la fecha no la hora
        fecha_actual.set(Calendar.HOUR_OF_DAY,0);
        fecha_actual.set(Calendar.MINUTE,0);
        fecha_actual.set(Calendar.SECOND,0);
        Calendar fecha_iterador = fecha_actual;
        Calendar fechaAux = fecha_actual;
        JButton botonAux;

        if (eventos.size()!=0){
            for (Event e: eventos){

                fechaAux = new GregorianCalendar(e.getDate().getYear(),e.getDate().getMonth(),e.getDate().getDay(),e.getDate().getHours(),e.getDate().getMinutes());
                //Comparamos unicamente la fecha no la hora
                fechaAux.set(Calendar.HOUR_OF_DAY,0);
                fechaAux.set(Calendar.MINUTE,0);
                fechaAux.set(Calendar.SECOND,0);               
                
                // La fecha del evento es anterior a la fecha del iterador
                if( fecha_iterador.getInstance().compareTo(fechaAux.getInstance()) > 0 ){
                    //No se imprime
                }              
                // Las fechas son iguales
                else if( fecha_iterador.getInstance().compareTo(fechaAux.getInstance()) == 0 ){
                    // Se imprime el evento en cuestión, la fecha ya se ha impreso antes
                    botonAux = new JButton("");
                    botonAux.setEnabled(false);
                    if (e.getColor()!=null){
                        botonAux.setForeground(color(e.getColor()));
                        botonAux.setBackground(color(e.getColor()));
                    }                
                    botonAux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    botonAux.setBounds(x,y,alto,alto);
                    areaListaEventos.add(botonAux);
                    x = x+alto;
                    botonAux = new JButton(e.toString());
                    botonAux.setForeground(Color.BLACK);
                    botonAux.setBackground(Color.WHITE);
                    botonAux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    botonAux.setBounds(x,y,ancho,alto);
                    botonAux.addActionListener (new ActionListener () {
                        public void actionPerformed(ActionEvent ae) {
                            FrameEvento (e);
                        }
                    });

                    areaListaEventos.add(botonAux);
                    y=y+alto;
                    x=borde;
                }
                i++;
            }
        }
        
        areaListaEventos.setVisible(true);

        JScrollPane scrollBar = new JScrollPane(areaListaEventos, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelEventos.add(scrollBar,BorderLayout.CENTER);

        return panelEventos;
    }

    private void FrameEvento(Event evento){
        JDialog frameEvento = new JDialog(this);
        
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
        String event_id = String.valueOf(evento.getId());

        // Se crean las etiquetas y botones, y se añaden a la ventana
        //FILA1
        JButton botonAux = new JButton("");
        botonAux.setEnabled(false);
        botonAux.setForeground(color(evento.getColor()));
        botonAux.setBackground(color(evento.getColor()));                
        botonAux.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        botonAux.setBounds(x,y,alto,alto);
        panelCreaEvento.add(botonAux);
        x = x+alto;        
        
        JLabel label_titulo = new JLabel(evento.getTitulo());
        label_titulo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label_titulo.setBounds(x,y,2*ancho,alto);
        panelCreaEvento.add(label_titulo);

        //FILA2
        x = borde;
        y=y+alto+espacio_y;
        JLabel label_fecha1 = new JLabel("Fecha: ");
        label_fecha1.setBounds(x,y,ancho,alto);    
        panelCreaEvento.add(label_fecha1);

        x = x+ancho+espacio_x;
        JLabel label_fecha2 = new JLabel(evento.getDate().getYear()+"/"+evento.getDate().getMonth()+"/"+evento.getDate().getDay()+" "+evento.getDate().getHours() +":"+ evento.getDate().getMinutes());
        label_fecha2.setBounds(x,y,ancho,alto);    
        panelCreaEvento.add(label_fecha2);
        
        if(evento.getAdvice_date()!=null){
            //FILA2
            x = borde;
            y=y+alto+espacio_y;
            JLabel label_fechaAviso = new JLabel("Fecha de aviso: ");
            label_fechaAviso.setBounds(x,y,ancho,alto);    
            panelCreaEvento.add(label_fechaAviso);            
            x = x+ancho+espacio_x;
            JLabel label_fechaAviso2 = new JLabel(evento.getAdvice_date().getYear()+"/"+evento.getAdvice_date().getMonth()+"/"+evento.getAdvice_date().getDay()+" "+evento.getAdvice_date().getHours() +":"+ evento.getAdvice_date().getMinutes());
            label_fechaAviso2.setBounds(x,y,ancho,alto);    
            panelCreaEvento.add(label_fechaAviso2);
        }


        JButton boton_borrar = new JButton("Borrar");
        if (evento.getNote()!=null){
            //FILA4
            x = borde;
            y=y+alto+espacio_y;
            JLabel label_nota = new JLabel("Nota: ");
            label_nota.setBounds(x,y,ancho,alto);    
            panelCreaEvento.add(label_nota);
            
            //FILA5
            x = borde;
            y=y+alto+espacio_y;
            JLabel field_nota = new JLabel(evento.getNote());
            field_nota.setBounds(x,y,3*ancho,5*alto);    
            panelCreaEvento.add(field_nota);            
            //FILA6
            x = borde;
            y=y+5*alto+espacio_y;
            boton_borrar.setBounds(x,y,ancho,alto);
        }else{
            //FILA6
            x = borde;
            y=y+alto+espacio_y;            
            boton_borrar.setBounds(x,y,ancho,alto);            
        }


        // Se define lo que realiza el boton del formulario para crear un evento
        boton_borrar.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println("##");
                System.out.println("#");
                System.out.println("BOTON --> Formulario crear evento");
                String cadenaJson="{\"event_id\":"+event_id+"}";

                try{
                    HttpResponse<String> response = send("deleteEvent",cadenaJson);

                    if (response.statusCode() == 200){
                        Integer int_aux = new Integer(0);

                        //{"state": 0, "desc": "Session already was initializated", "session-id": 0}
                        // Se quitan las llaves al body (primer y ultimo caracter)
                        String strAux = response.body().substring(1,response.body().length()-1);

                        // Se hace un split por comas
                        String [] atributos = strAux.split(",");

                        // Extraemos la información de los elemetnos que nos interesan, el 3
                        System.out.println("El estado de la operación POST es: "+int_aux.parseInt(atributos[0].split(":")[1].trim()));
                        
                        if (int_aux.parseInt(atributos[0].split(":")[1].trim()) == 1){
                            //Borramos el evento de la lista local

                            eventos.remove(evento);
                            ventana.getContentPane().removeAll();
                            ventana.getContentPane().invalidate();           
                            Pestañas();
                            ventana.getContentPane().revalidate();
                            ventana.getContentPane().setVisible(true);                                 
                        }else{
                            FrameError("Ha ocurrido un problema");
                        }
                    }

                }catch(Exception ex){
                    FrameError("Ha ocurrido un error,vuelva a intentarlo :"+ex);
                    System.out.println("excepción:\n"+ex);
                }

                System.out.println("#");
                System.out.println("##");
                // Se cierra la ventana flotante
                frameEvento.dispose();
            
            }
        }); 

        panelCreaEvento.add(boton_borrar);

        x = x + 3*ancho + 3*borde;
        y = y + alto + borde;
        panelCreaEvento.setBounds(0,0,x,y); 
        
        frameEvento.add(panelCreaEvento);
        frameEvento.setSize(x,y+25);
        frameEvento.setTitle("Evento");
        frameEvento.setVisible(true);
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
        JTextField field_titulo = new JTextField("Titulo");
        field_titulo.setBounds(x,y,2*ancho,alto);
        panelCreaEvento.add(field_titulo);

        x = x+2*ancho+espacio_x*2;
        String[] colores = {"blanco","rojo","verde","azul","amarillo","naranja","gris"};
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
        JButton boton_formulario_crear_evento = new JButton("Crear");
        boton_formulario_crear_evento.setBounds(x,y,ancho,alto);

        // Se define lo que realiza el boton del formulario para crear un evento
        boton_formulario_crear_evento.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println("##");
                System.out.println("#");
                System.out.println("BOTON --> Formulario crear evento");
                if (anio_fecha.getText().isEmpty() || mes_fecha.getText().isEmpty() || dia_fecha.getText().isEmpty() || field_titulo.getText().isEmpty() || hora_fecha.getText().isEmpty() || min_fecha.getText().isEmpty()){
                    FrameError("Hay campos obligatorios vacíos");
                }else{
                    // Cuando los campos obligatorios no están vacíos se procede a crearse el evento
                    String cadenaJson;
                    String cadenaFecha; //2004-10-19 10:23:54
                    String cadenaFechaAviso;

                    // Creamos un objeto para tenerlo de manera local
                    Event evento = new Event();
                    evento.setTitulo(field_titulo.getText());
                    evento.setIdSesion(usuario.getIdSesion());
                    Integer auxinteger = new Integer(0);
                    evento.setDate(new Date(auxinteger.parseInt(anio_fecha.getText()),auxinteger.parseInt(mes_fecha.getText()),auxinteger.parseInt(dia_fecha.getText()),auxinteger.parseInt(hora_fecha.getText()),auxinteger.parseInt(min_fecha.getText())));


                    cadenaFecha = anio_fecha.getText()+"-"+mes_fecha.getText()+"-"+dia_fecha.getText()+" "+hora_fecha.getText()+":"+min_fecha.getText()+":00";
                    cadenaJson = "{\"session_id\":"+usuario.getIdSesion()+",\"date\":\""+cadenaFecha+"\",\"name\":\""+field_titulo.getText()+"\"";

                    if(!anio_recordatorio.getText().isEmpty() && !mes_recordatorio.getText().isEmpty() && !dia_recordatorio.getText().isEmpty() && !hora_recordatorio.getText().isEmpty() && !min_recordatorio.getText().isEmpty()){
                        evento.setAdvice_date(new Date(auxinteger.parseInt(anio_recordatorio.getText()),auxinteger.parseInt(mes_recordatorio.getText()),auxinteger.parseInt(dia_recordatorio.getText()),auxinteger.parseInt(hora_recordatorio.getText()),auxinteger.parseInt(min_recordatorio.getText())));
                        cadenaFechaAviso = ",\"advice_date\":\""+anio_recordatorio.getText()+"-"+mes_recordatorio.getText()+"-"+dia_recordatorio.getText()+" "+hora_recordatorio.getText()+":"+min_recordatorio.getText()+":00\"";
                        cadenaJson = cadenaJson + cadenaFechaAviso;
                    }

                    String color_vacio = "blanco";
                    if ( !color_vacio.contentEquals( (String) menuColores.getSelectedItem() ) ){
                        evento.setColor((String) menuColores.getSelectedItem());
                        cadenaJson = cadenaJson + ",\"color\":\""+(String)menuColores.getSelectedItem()+"\"";
                    }

                    if(!field_nota.getText().isEmpty()){
                        evento.setNote(field_nota.getText());
                        cadenaJson = cadenaJson + ",\"note\":\""+field_nota.getText()+"\"";
                    }

                    cadenaJson = cadenaJson + "}";                   

                    // Añadimos el evento al servidor
                    try{
                        HttpResponse<String> response = send("createEvent",cadenaJson);

                        if (response.statusCode() == 200){
                            Integer int_aux = new Integer(0);

                            //{"state": 0, "desc": "Session already was initializated", "session-id": 0}
                            // Se quitan las llaves al body (primer y ultimo caracter)
                            String strAux = response.body().substring(1,response.body().length()-1);

                            // Se hace un split por comas
                            String [] atributos = strAux.split(",");

                            // Extraemos la información de los elemetnos que nos interesan, el 3
                            System.out.println("El estado de la operación POST es: "+int_aux.parseInt(atributos[0].split(":")[1].trim()));
                            
                            if (int_aux.parseInt(atributos[0].split(":")[1].trim()) == 1){
                                evento.setId(int_aux.parseInt(atributos[2].split(":")[1].trim())); //TODO
                                eventos.add(new Integer (int_aux.parseInt(atributos[3].split(":")[1].trim())).intValue(),evento);
                                ventana.getContentPane().removeAll();
                                ventana.getContentPane().invalidate();           
                                Pestañas();
                                System.out.println("4");
                                ventana.getContentPane().revalidate();
                                ventana.getContentPane().setVisible(true);                                 
                            }else{
                                FrameError("Ha ocurrido un problema");
                            }
                        }

                    }catch(Exception ex){
                        FrameError("Ha ocurrido un error,vuelva a intentarlo :"+ex);
                        System.out.println("excepción:\n"+ex);
                    }

                    System.out.println("#");
                    System.out.println("##");
                    // Se cierra la ventana flotante
                    frameCreaEventos.dispose();
                }
            }
        }); 

        panelCreaEvento.add(boton_formulario_crear_evento);

        x = x + 3*ancho + 3*borde;
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
        JLabel label_correo2 = new JLabel(usuario.getEmail());
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
        JLabel label_correo2 = new JLabel(usuario.getEmail());
        label_correo2.setBounds(x,y,ancho,alto);
        panelModificaCredenciales.add(label_correo2);

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

        // Se define lo que realiza el boton del formulario para modificar credenciales
        boton_formulario_modifica_credenciales.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                System.out.println("##");
                System.out.println("#");
                System.out.println("BOTON Formulario para modificar credenciales");
                if (field_password1.getText().isEmpty() || field_password2.getText().isEmpty() || field_password3.getText().isEmpty() ){
                    FrameError("Hay campos obligatorios vacíos");
                }else if( !field_password1.getText().contentEquals(field_password2.getText()) ){
                    FrameError("Las constraseñas nuevas son diferentes");
                }else{
                    
                    //Se accede a la base de datos con el usuario y contraseña introducidas usuario.getEmail() 
                    try{
                        String cadenaJson = "{\"email\":\""+usuario.getEmail()+"\",\"passwd\":\""+field_password1.getText()+"\",\"old_passwd\":\""+field_password3.getText()+"\"}";
                        HttpResponse<String> response = send("modifyUser",cadenaJson);

                        if (response.statusCode() == 200){
                            Integer int_aux = new Integer(0);

                            //{"state": 0, "desc": "Session already was initializated", "session-id": 0}
                            // Se quitan las llaves al body (primer y ultimo caracter)
                            String strAux = response.body().substring(1,response.body().length()-1);

                            // Se hace un split por comas
                            String [] atributos = strAux.split(",");

                            // Extraemos la información de los elemetnos que nos interesan, el 3
                            System.out.println("El estado de la operación POST es: "+int_aux.parseInt(atributos[0].split(":")[1].trim()));
                            
                            if ( (int_aux.parseInt(atributos[0].split(":")[1].trim()) == 1) ){
                                //se hace el update de correo
                                usuario.setPasswd(field_password1.getText());
                                frameModificaCredenciales.dispose();
                            }
                        }else{
                            FrameError("La contraseña antigua no es correcta");
                        }
                    }catch(Exception ex){
                        System.out.print("Excepción: \n"+ex);
                    }
                }
                System.out.println("#");                
                System.out.println("##");
            }
        }); 

        panelModificaCredenciales.add(boton_formulario_modifica_credenciales);

        x = x + 2*ancho + 2*borde;
        y = y + alto + borde;
        panelModificaCredenciales.setBounds(0,0,x,y); 
        
        frameModificaCredenciales.add(panelModificaCredenciales);
        frameModificaCredenciales.setSize(x,y+25);
        frameModificaCredenciales.setTitle("Modificar credenciales");
        frameModificaCredenciales.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Main - La aplicación empieza");

        Client ventana = new Client();
        System.out.println("Main - Ya se ha instanciado la interfaz");        

    }
}
