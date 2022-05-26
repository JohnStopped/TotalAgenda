
import java.io.*;
import java.util.*;

class User{

    private String email;
    private String passwd;
    private int idSesion;

    User(){
        //this.email = null;
        //this.idSesion = null;
        //this.passwd = null;
    }
    
    User(String email, int idSesion,String passwd) {
        this.email = email;
		this.idSesion = idSesion;
        this.passwd = passwd;
    }

    public void setEmail(String email){

        this.email = email;
    }

    public String getEmail(){

        return this.email;
    }

    public void setidSesion(int idSesion){

        this.idSesion = idSesion;
    }

    public int getidSesion(){

        return this.idSesion;
    }

    public void setPasswd(String passwd){

        this.passwd = passwd;
    }

    public String getPasswd(){

        return this.passwd;
    }

    public String toString(){
        return (String.valueOf(idSesion)+"-"+email);
    }
}
