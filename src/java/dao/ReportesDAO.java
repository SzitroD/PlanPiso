package dao;

import control.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Reportes;
public class ReportesDAO {

    Connection con= ConexionMySQL.conectarPP();
    
    public void insertarReporte(String nombreReporte, String ruta){
        if(con == null ){
           con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try {
            String SQL = "INSERT INTO reportes(nombreReporte,ruta)"
                    + " values (?,?);";
            Connection conPP = ConexionMySQL.conectarPP();
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ps.setString(1, nombreReporte);
            ps.setString(2, ruta);
            
            ps.executeUpdate();
            } catch (SQLException ex) {
                 System.out.println("Algo fallo: "+ex.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
    
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
    
    public static ArrayList<Reportes> buscarReporte(String busqueda){
         
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Reportes> lista = new ArrayList<Reportes>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT nombreReporte, ruta FROM reportes WHERE nombreReporte LIKE '%"+ busqueda +"%'";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Reportes r = new Reportes();
                    r.setNombreReporte(rs.getString("nombreReporte"));
                    r.setRuta(rs.getString("ruta"));
                    lista.add(r);
                }
                return lista;
            
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
