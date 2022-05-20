
import java.io.*;
import java.util.*;

class Event{

    private String email;
    private String passwd;

    Event(){}
    
    Event(String email, String passwd, Calendar date, String color) {
        this.email = email;
		this.passwd = passwd;
    }

    public void setEmail(String email){

        this.email = email;
    }

    public String getEmail(){

        return this.email;
    }

    public void setPasswd(String passwd){

        this.passwd = passwd;
    }

    public String getPasswd(){

        return this.passwd;
    }

    /*
    public String toString(){
        return ();
    }
    */
}
