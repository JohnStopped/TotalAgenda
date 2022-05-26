
import java.io.*;
import java.util.*;

class Event{

    private int idSesion;    
    private int id;
    private String email;
    private Calendar date;
    private Calendar advice_date;
    private String titulo;
    private String color;
    private String note;

    Event(){

        //this.idSesion = idSesion;
        //this.id = id;
        //this.email = email;
        //this.date = date;
        //this.advice_date = advice_date;
        //this.titulo = titulo;
        //this.color = color;
        //this.note = note;

    }
    
    Event(int id, String email, Calendar date, Calendar advice_date, String titulo, String color, String note) {
        
        this.idSesion = idSesion;
        this.id = id;
        this.email = email;
        this.date = date;
        this.advice_date = advice_date;
        this.titulo = titulo;
        this.color = color;
        this.note = note;
    }

    public void setidSesion(int idSesion){

        this.idSesion = idSesion;
    }

    public int getidSesion(){

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
    public void setDate(Calendar date){

        this.date = date;
    }

    public Calendar getDate(){

        return this.date;
    }

    //advice_date
    public void setAdvice_date(Calendar advice_date){

        this.advice_date = advice_date;
    }

    public Calendar getAdvice_date(){

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
        return (date.get(Calendar.HOUR_OF_DAY) +":"+ date.get(Calendar.MINUTE) +" "+ titulo);
    }

}
