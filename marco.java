import javax.swing.*;
 
public class marco extends JFrame{
 
    public marco(){
    
        //Ventana -> tiene de contenido un frame
        //Pestaña -> tiene de contenido un panel

        //Parametros asociados a la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650,400); // Tamaño inicial
        setExtendedState(MAXIMIZED_BOTH); // Máximo tamaño
        setVisible(true);
        setTitle("Agenda Total"); // Nombre de la ventana
 
        //Creamos el conjunto de pestañas
        JTabbedPane pestañas=pestañas();
        //Añadimos el conjunto de pestañas al marco de la ventana
        getContentPane().add(pestañas); // Añadimos las pestañas creadas al marco
    }

    public JTabbedPane pestañas(){

        //Creamos el conjunto de pestañas
        JTabbedPane pestañas=new JTabbedPane();
 
        //Creamos el panel de la pestaña de eventos
        JPanel panel_pestaña_eventos=pestaña_eventos();
        //Añadimos el panel a la pestaña y le damos un nombre
        pestañas.addTab("Eventos", panel_pestaña_eventos);

        //Creamos el panel de la pestaña de calendario
        JPanel panel_pestaña_calendario=pestaña_calendario();
        //Añadimos el panel a la pestaña y le damos un nombre
        pestañas.addTab("Calendario", panel_pestaña_calendario);

        //Creamos el panel de la pestaña de cuenta
        JPanel panel_pestaña_cuenta=pestaña_cuenta();
        //Añadimos el panel a la pestaña y le damos un nombre
        pestañas.addTab("Cuenta", panel_pestaña_cuenta);

        //Devolvemos el conjunto de pestañas
        return pestañas;
    }

    public JPanel pestaña_eventos(){
        //Creamos el panel de la pestaña
        JPanel panel =new JPanel();

        JLabel et_p1=new JLabel("Estas en el panel 1");
        panel.add(et_p1);

        return panel;
    }
 
    public JPanel pestaña_calendario(){
        //Creamos el panel de la pestaña
        JPanel panel =new JPanel();

        JLabel et_p1=new JLabel("Estas en el panel 1");
        panel.add(et_p1);

        return panel;        
    }

    public JPanel pestaña_cuenta(){
        //Creamos el panel de la pestaña
        JPanel panel =new JPanel();

        JLabel et_p1=new JLabel("Estas en el panel 1");
        panel.add(et_p1);

        return panel;        
    }    

    public static void main(String[] args) {
 
        marco frame = new marco();
 
    }
 
}