
import java.io.*;
import java.util.*;

class Event{

    private String name;
    private String note;
    private Calendar date;
    private String color;

    Event(){}
    
    Event(String name, String note, Calendar date, String color) {
        this.name = name;
		this.note = note;
		this.date = date;
        this.color = color;
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

    public void setDate(Calendar date){

        this.date = date;
    }

    public Calendar getDate(){

        return this.date;
    }

    public void setColor(String color){

        this.color = color;
    }

    public String getColor(){

        return this.color;
    }


    public String toString(){
        return (date.get(Calendar.HOUR_OF_DAY) +":"+ date.get(Calendar.MINUTE) +" "+ name);
    }

}
