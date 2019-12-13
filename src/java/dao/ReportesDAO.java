package dao;

import control.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import modelo.Reportes;
public class ReportesDAO {

    Connection con= ConexionMySQL.conectarPP();
    
    //Subir un archivo a la base de datos
    public void insertarReporte(String nombreReporte, String ruta,String fecha){
        if(con == null ){
           con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try {
            String SQL = "INSERT INTO reportes(nombreReporte,ruta,fecha_subida)"
                    + " values (?,?,?);";
            Connection conPP = ConexionMySQL.conectarPP();
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ps.setString(1, nombreReporte);
            ps.setString(2, ruta);
            ps.setString(3, fecha);
            
            ps.executeUpdate();
            } catch (SQLException ex) {
                 System.out.println("Error al insertar el archivo: "+ex.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
    
    //Listar todos los archivos
    public static ArrayList<Reportes> listarReporte(){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Reportes> lista = new ArrayList<Reportes>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT * FROM reportes";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();


                while(rs.next()){
                    Reportes rp = new Reportes();
                    rp.setIdReporte(rs.getInt("idReportes"));
                    rp.setNombreReporte(rs.getString("nombreReporte"));
                    rp.setRuta(rs.getString("ruta"));
                    rp.setTipo(rs.getString("tipo"));
                    rp.setFecha(rs.getString("fecha_subida"));
                    rp.setConciliacion(rs.getString("conciliacion"));
                    lista.add(rp);
                }
            }catch(SQLException e){
                System.out.println("Algo fallo: \n" + e.getLocalizedMessage());
                return null;
            }finally {
                        try {
                            conPP.close();
                        } catch (SQLException ee) {
                            System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                        }
                    }
        }
                return lista;
    }
    
    //Buscar un archivo por su nombre
    public static ArrayList<Reportes> buscarReporte(String busqueda){
         
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Reportes> lista = new ArrayList<Reportes>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT * FROM reportes WHERE nombreReporte LIKE '%"+ busqueda +"%'";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Reportes r = new Reportes();
                    r.setNombreReporte(rs.getString("nombreReporte"));
                    r.setRuta(rs.getString("ruta"));
                    r.setTipo(rs.getString("tipo"));
                    r.setFecha(rs.getString("fecha_subida"));
                    r.setConciliacion(rs.getString("conciliacion"));
                    lista.add(r);
                }
                return lista;
            
            }catch(SQLException e){
                System.out.println("Error al buscar el archivo: \n" + e.getLocalizedMessage());
                return null;
            }finally {
                try {
                    conPP.close();
                } catch (SQLException ee) {
                    System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                }
            }
        }
                return lista;
    }
    
    //Agregar un archivo de conciliacion 
    public void insertarConciliacion(String nombreReporte, String ruta,String tipo,String financiera,String fecha){
        if(con == null ){
           con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try {
            String SQL = "INSERT INTO reportes(nombreReporte,ruta,tipo,fecha_subida,conciliacion)"
                    + " values (?,?,?,?,?);";
            Connection conPP = ConexionMySQL.conectarPP();
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ps.setString(1, nombreReporte);
            ps.setString(2, ruta);
            ps.setString(3, tipo);
            ps.setString(4, fecha);
            ps.setString(5, financiera);
            
            ps.executeUpdate();
            } catch (SQLException ex) {
                 System.out.println("Error al subir el archivo de conciliacion: "+ex.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
 
    //Listar todos los archivos de concilacion
    public static ArrayList<Reportes> listarReporteConciliacion(){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Reportes> lista = new ArrayList<Reportes>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT * FROM reportes WHERE fecha_subida = (\n" +
                            "	SELECT max(fecha_subida)\n" +
                            "    FROM plan_piso.reportes\n" +
                            ") AND tipo = 'conciliacion'";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();


                while(rs.next()){
                    Reportes rp = new Reportes();
                    rp.setIdReporte(rs.getInt("idReportes"));
                    rp.setNombreReporte(rs.getString("nombreReporte"));
                    rp.setRuta(rs.getString("ruta"));
                    rp.setTipo(rs.getString("tipo"));
                    rp.setFecha(rs.getString("fecha_subida"));
                    rp.setConciliacion(rs.getString("conciliacion"));
                    lista.add(rp);
                }
            }catch(SQLException e){
                System.out.println("Algo fallo: \n" + e.getLocalizedMessage());
                return null;
            }finally {
                        try {
                            conPP.close();
                        } catch (SQLException ee) {
                            System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                        }
                    }
        }
                return lista;
    }
    
}
