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
    
    //Listar todas las financieras
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
                    b.setLinea(rs.getDouble("linea"));
                    b.setPxf(rs.getInt("pxf"));
                    b.setValorPxf(rs.getDouble("valor_pxf"));
                    b.setPagadas(rs.getInt("pagadas"));
                    b.setValorPagadas(rs.getDouble("valor_pagadas"));
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
    
    //Actualizar las propiedades de una financiera 
    public void modificarBanco(String nombre, double interes, int dias, int diasExtra, double interesExtra,int id,int diasLibres,double linea){
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
                        +   "diasLibres = ?, "
                        +   "linea = ? "
                        +   "WHERE idBancos = ?";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setInt(8, id);
                ps.setString(1, nombre);
                ps.setDouble(2, interes);
                ps.setInt(3, dias);
                ps.setInt(4, diasExtra);
                ps.setDouble(5, interesExtra);
                ps.setInt(6, diasLibres);
                ps.setDouble(7, linea);

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
    
    //Insertar una nueva financiera 
    public void insertarBanco(String nombreBanco,double interes, int diasInteres, int diasExtra, double interesExtra, int diasLibres,double linea){
         if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "INSERT INTO bancos (nombreBanco, interes, diasFinanciamiento, diasExtra, interesExtra, diasLibres, linea) "
                        + "VALUES (?,?,?,?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(SQL);

                ps.setString(1, nombreBanco);
                ps.setDouble(2, interes);
                ps.setInt(3, diasInteres);
                ps.setInt(4, diasExtra);
                ps.setDouble(5, interesExtra);
                ps.setInt(6, diasLibres);
                ps.setDouble(7,linea);

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
    
    //Activar una financiera
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
    
    //Desactivar una financiera 
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
    
    //Actualizar las propiedades de una conciliacion
    public void actializarConciliacion(int pxf, double valorPxf, int pagadas, double valorPagadas, int id){
        if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "UPDATE bancos SET "
                        +   "pxf = ?,"
                        +   "valor_pxf = ?,"
                        +   "pagadas = ?,"
                        +   "valor_pagadas = ? "
                        +   "WHERE idBancos = ?";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setInt(5, id);
                ps.setInt(1, pxf);
                ps.setDouble(2, valorPxf);
                ps.setInt(3, pagadas);
                ps.setDouble(4, valorPagadas);

                ps.executeUpdate();

            }catch(SQLException e){
                System.out.println("Error al actualizar la conciliacion: "+e.getLocalizedMessage());
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
