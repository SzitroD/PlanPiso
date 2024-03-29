package dao;

import com.mysql.cj.protocol.Resultset;
import control.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Vehiculo;
import modelo.Financiar;

public class FinanciarDAO {
    
    Connection con = ConexionMySQL.conectarPP();
    
    //Ingresar un nuevo vehiculo a una financiera
    public void insertarFinanciamiento(String vin,int idBanco, String fechaFinanciamiento,double interesReal, int diasReal, String fechaNVDR,String observacion){
        if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "INSERT INTO financiar(vin_vehiculo,id_banco,fecha_financiamiento,interes_real,dias_reales,reportado_nvdr,observaciones) "
                        +   "VALUES (?,?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setString(1, vin);
                ps.setInt(2, idBanco);
                ps.setString(3, fechaFinanciamiento);
                ps.setDouble(4, interesReal);
                ps.setInt(5, diasReal);
                ps.setString(6, fechaFinanciamiento);
                ps.setString(7, observacion);

                ps.executeUpdate();
            }catch(SQLException e){
                System.out.println("Error al financiar: "+e.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
    
    //Actualizar las propiedades de un vehiculo financiado
    public void actualizar(int idBanco, String fechaFinanciamiento,String vin,int dias, String prioridad, String observaciones,double interesReal,String statusFinanciamiento, String fechaNVDR){
        if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "UPDATE financiar SET "
                        +   "id_banco = ?,"
                        +   "fecha_financiamiento = ?, "
                        +   "dias_reales = ?, "
                        +   "prioridad_pago = ?, "
                        +   "observaciones = ?, "
                        +   "interes_real = ?, "
                        +   "status = ?, "
                        +   "reportado_nvdr = ?"
                        +   "WHERE vin_vehiculo = ?";

                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setString(9, vin);
                ps.setInt(1, idBanco);
                ps.setString(2, fechaFinanciamiento);
                ps.setInt(3, dias);
                ps.setString(4,prioridad);
                ps.setString(5, observaciones);
                ps.setDouble(6, interesReal);
                ps.setString(7,statusFinanciamiento);
                ps.setString(8,fechaNVDR);

                ps.executeUpdate();
            }catch(SQLException e){
                System.out.println("Error al financiar: "+e.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
    
    
    //Cambiar el estado de un vehiculo a refinanciado
    public void refinanciemiento(int idBanco, String fechaFinanciamiento,String vin,int dias, String prioridad, String observaciones,double interesExtraReal,String statusFinanciamiento
                                ,String fechaNVDR){
        if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "UPDATE financiar SET "
                        +   "id_banco = ?,"
                        +   "fecha_financiamiento = ?, "
                        +   "dias_reales = ?, "
                        +   "prioridad_pago = ?, "
                        +   "observaciones = ?, "
                        +   "interes_extra_real = ?, "
                        +   "status = ? ,"
                        +   "reportado_nvdr = ? "
                        +   "WHERE vin_vehiculo = ?";

                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setString(9, vin);
                ps.setInt(1, idBanco);
                ps.setString(2, fechaFinanciamiento);
                ps.setInt(3, dias);
                ps.setString(4,prioridad);
                ps.setString(5, observaciones);
                ps.setDouble(6, interesExtraReal);
                ps.setString(7,statusFinanciamiento);
                ps.setString(8,fechaNVDR);

                ps.executeUpdate();
            }catch(SQLException e){
                System.out.println("Error al financiar: "+e.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
    
    //Actualizar el campo de CF de un vehiculo con un archivo de conciliacion
    public void cargarConciliacion(double valor,String vin){
        if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQL = "UPDATE financiar SET "
                        +   "cf = ? "
                        +   "WHERE  vin_vehiculo = ?";
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setString(2, vin);
                ps.setDouble(1, valor);

                ps.executeUpdate();
            }catch(SQLException e){
                System.out.println("Error al cargar la conciliacion: "+e.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
    
    //Actualizar todos los financiamientos
    public static ArrayList<Financiar> listarFinanciamiento(){
       
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Financiar> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT * FROM financiar";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    Financiar f = new Financiar();
                    f.setIdFinanciar(rs.getInt("id_financiar"));
                    f.setVinVehiculo(rs.getString("vin_vehiculo"));
                    f.setIdBanco(rs.getInt("id_banco"));
                    f.setFechaFinanciamineto(rs.getString("fecha_financiamiento"));
                    f.setCf(rs.getDouble("cf"));
                    f.setDiasReales(rs.getInt("dias_reales"));
                    f.setPrioridadPago(rs.getString("prioridad_pago"));
                    f.setReportadoNVDR(rs.getString("reportado_nvdr"));
                    f.setObservaciones(rs.getString("observaciones"));
                    f.setInteresReal(rs.getDouble("interes_real"));
                    f.setStatus(rs.getString("status"));
                    lista.add(f);
                }
            }catch(SQLException e){
                System.out.println("Error al listar financiamientos: "+e.getLocalizedMessage());
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
