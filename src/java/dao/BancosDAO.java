package dao;

import control.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Bancos;
import java.util.ArrayList;

public class BancosDAO {
    
    Connection con = ConexionMySQL.conectarPP();
    
    public static ArrayList<Bancos> listarBancos(){
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Bancos> lista = new ArrayList<>();
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT * FROM bancos";

                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                Bancos b;

                while(rs.next()){
                    b = new Bancos();
                    b.setIdBancos(rs.getInt("idBancos"));
                    b.setNombreBanco(rs.getString("nombreBanco"));
                    b.setInteres(rs.getDouble("interes"));
                    b.setDiasFinanciamiento(rs.getInt("diasFinanciamiento"));
                    b.setDiasExtra(rs.getInt("diasExtra"));
                    b.setInteresExtra(rs.getDouble("interesExtra"));
                    b.setDiasLibres(rs.getInt("diasLibres"));
                    b.setStatus(rs.getInt("status"));
                    lista.add(b);
                }

            }catch(SQLException e){
                System.out.println("Error al listar financieras: "+ e.getLocalizedMessage());
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
    
    public void modificarBanco(String nombre, double interes, int dias, int diasExtra, double interesExtra,int id,int diasLibres){
        if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "UPDATE bancos SET "
                        +   "nombreBanco = ?,"
                        +   "interes = ?,"
                        +   "diasFinanciamiento = ?,"
                        +   "diasExtra = ?,"
                        +   "interesExtra = ?,"
                        +   "diasLibres = ? "
                        +   "WHERE idBancos = ?";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setInt(7, id);
                ps.setString(1, nombre);
                ps.setDouble(2, interes);
                ps.setInt(3, dias);
                ps.setInt(4, diasExtra);
                ps.setDouble(5, interesExtra);
                ps.setInt(6, diasLibres);

                ps.executeUpdate();

            }catch(SQLException e){
                System.out.println("Error al actualizar: "+e.getLocalizedMessage());
            }finally {
                try {
                    con.close();
                } catch (SQLException ee) {
                    System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                }
            }
        }
    }
    
    public void insertarBanco(String nombreBanco,double interes, int diasInteres, int diasExtra, double interesExtra, int diasLibres){
         if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "INSERT INTO bancos (nombreBanco, interes, diasFinanciamiento, diasExtra, interesExtra, diasLibres) "
                        + "VALUES (?,?,?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(SQL);

                ps.setString(1, nombreBanco);
                ps.setDouble(2, interes);
                ps.setInt(3, diasInteres);
                ps.setInt(4, diasExtra);
                ps.setDouble(5, interesExtra);
                ps.setInt(6, diasLibres);

                ps.executeUpdate();

            }catch(SQLException e){
                System.out.println("Error al ingresar financiera: "+e.getLocalizedMessage());
            }finally {
                try {
                    con.close();
                } catch (SQLException ee) {
                    System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                }
            }
        }
    }
    
    public void activarBanco(int id){
         if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "UPDATE bancos SET "
                        +   "status = '1' "
                        +   "WHERE idBancos = ? ";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setInt(1, id);

                ps.executeUpdate();
            }catch(SQLException e){
                System.out.println("Error al activar: "+e.getLocalizedMessage());
            }finally {
                try {
                    con.close();
                } catch (SQLException ee) {
                    System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                }
            }
        }
    }
    
    public void desactivarBanco(int id){
        if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "UPDATE bancos SET "
                        +   "status = '0' "
                        +   "WHERE idBancos = ? ";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setInt(1, id);

                ps.executeUpdate();
            }catch(SQLException e){
                System.out.println("Error al desactivar: "+e.getLocalizedMessage());
            }finally {
                try {
                    con.close();
                } catch (SQLException ee) {
                    System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                }
            }
        }
    }
    
}
