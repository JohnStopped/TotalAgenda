
import java.io.*;
import java.util.*;

class Event{

    private int idSesion;    
    private int id;
    private String email;
    private Date date;
    private Date advice_date;
    private String titulo;
    private String color;
    private String note;

    Event(){

        //this.idSesion = null;
        //this.id = null;
        //this.email = null;
        //this.date = null;
        //this.advice_date = null;
        //this.titulo = null;
        //this.color = null;
        //this.note = null;

    }
    
    Event(int id, String email, Date date, Date advice_date, String titulo, String color, String note) {
        
        this.id = id;
        this.email = email;
        this.date = date;
        this.advice_date = advice_date;
        this.titulo = titulo;
        this.color = color;
        this.note = note;
    }

    public void setIdSesion(int idSesion){

        this.idSesion = idSesion;
    }

    public int getIdSesion(){

        return this.idSesion;
    }


    //id
    public void setId(int id){

        this.id = id;
    }

    public int getId(){

        return this.id;
    }

    //email
    public void setEmail(String email){

        this.email= email;
    }

    public String getEmail(){

        return this.email;
    }

    //date
    public void setDate(Date date){

        this.date = date;
    }

    public Date getDate(){

        return this.date;
    }

    //advice_date
    public void setAdvice_date(Date advice_date){

        this.advice_date = advice_date;
    }

    public Date getAdvice_date(){

        return this.advice_date;
    }

    //titulo
    public void setTitulo(String titulo){

        this.titulo = titulo;
    }

    public String getTitulo(){

        return this.titulo;
    }

    //color
    public void setColor(String color){

        this.color = color;
    }

    public String getColor(){

        return this.color;
    }

    //note
    public void setNote(String note){

        this.note = note;
    }

    public String getNote(){

        return this.note;
    }

    //toString
    public String toString(){

        String cadena = date.getYear()+"/"+date.getMonth()+"/"+date.getDate()+" "+date.getHours() +":"+ date.getMinutes() +" | "+ titulo; 
        return (cadena);
    }

}
