
import java.io.*;
import java.util.*;

class User{

    private String email;
    private int idSesion;

    User(){}
    
    User(String email, int idSesion) {
        this.email = email;
		this.idSesion = idSesion;
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

    public String toString(){
        return (String.valueOf(idSesion)+"-"+email);
    }
}
