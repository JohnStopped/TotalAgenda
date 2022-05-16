
import java.io.*;
import java.util.*;

class Event{

    private String name;
    private String note;
    private Date date;

    Event(){}
    
    Event(String name, String note, Date date7) {
        this.name = name;
		this.note = note;
		this.date = date;
    }

    public void setName(String name){

        this.name = name;
    }

    public String getName(){

        return this.name;
    }

    public void setNote(String note){

        this.note = note;
    }

    public String getNote(){

        return this.note;
    }

    public String toString(){
        //TODO Hay que hacer que se devuelva AM o PM en función de lo que devuelca el método get de calendar (0 o 1)
        return (calendario.get(Calendar.HOUR) +":"+ calendario.get(Calendar.MINUTE) + calendario.get(Calendar.AM_PM)); 
    }

}
