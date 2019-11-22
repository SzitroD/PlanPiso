package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    
    public static Connection conectarPP(){
        String host="192.168.1.73:3306", usuario="dev";
        String contrasena="stmsc0nt";
        //String host = "localhost:3306", usuario ="root";
        //String contrasena ="sistemas";
        String sid="plan_piso";
       
        
        String cadenaconexion;
        cadenaconexion ="jdbc:mysql://"+host+"/"+sid+"?useSSL=false&useTimezone=true&serverTimezone=GMT-5";
        Connection con = null;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(cadenaconexion, usuario, contrasena);
            return con;
        }
        catch(ClassNotFoundException | SQLException e){
        	System.out.println("Error al Conectar a " + sid);
        	System.out.println("Error: " + e.toString());
            return null;
        }
    }
}
