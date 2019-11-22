package dao;

import control.ConexionMySQL;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import modelo.Vehiculo;

public class VehiculosDAO {
    
    Connection con = ConexionMySQL.conectarPP();
    
    /*
    public void ingresarDatosCSV (String nombre){
        try{
            String SQL = "LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/"+nombre+"'\n" +
                        "INTO TABLE vehiculo\n" +
                        "FIELDS TERMINATED BY ','\n" +
                        "ENCLOSED BY '\"'\n" +
                        "LINES TERMINATED BY '\\n'\n" +
                        "IGNORE 1 ROWS;";
            Connection con = ConexionMySQL.conectarPP();
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
        }catch(SQLException e){
            System.out.println("Error al cargar el archivo: "+e.getLocalizedMessage());
        }
    }*/
    
    public static ArrayList<Vehiculo> listarVehiculo(){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Vehiculo> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT * FROM vehiculo";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
            
                while(rs.next()){
                    Vehiculo v = new Vehiculo();
                    v.setFechaCompra(rs.getString("fecha_compra"));
                    v.setMarca(rs.getString("marca"));
                    v.setVin(rs.getString("vin"));
                    v.setSerie(rs.getString("serie"));
                    v.setImporteNeto(rs.getDouble("valor_factura"));
                    v.setValorFactura(rs.getDouble("valor_factura"));
                    v.setSituacion(rs.getString("situacion"));
                    v.setUbicacion(rs.getString("ubicacion"));
                    v.setCarteraFinanciera(rs.getString("cartera_financiera"));
                    v.setFactura(rs.getString("factura"));
                    v.setCliente(rs.getString("cliente"));
                    v.setIdTipoVenta(rs.getString("id_tipo_venta"));
                    v.setTipoVenta(rs.getString("tipo_venta"));
                    v.setFechaFactura(rs.getString("fecha_factura"));
                    v.setValorVenta(rs.getDouble("valor_venta"));
                    v.setPagoCliente(rs.getDouble("pago_cliente"));
                    v.setSaldo(rs.getDouble("saldo"));
                    v.setDb(rs.getString("db"));
                    v.setFechaCarga(rs.getString("fecha_carga"));

                    lista.add(v);
                }
            }catch(SQLException e){
                System.out.println("Herror al listar los vehiculos: "+e.getLocalizedMessage());
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
    
    public static ArrayList<Vehiculo> buscarVehiculo(String busqueda){
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Vehiculo> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
        try{
            String SQL = "SELECT * FROM vehiculo WHERE vin LIKE '%"+busqueda+"%' AND vin NOT IN (SELECT vin_vehiculo FROM financiar)" ;
            Connection con = ConexionMySQL.conectarPP();
            PreparedStatement ps = con.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Vehiculo v = new Vehiculo();
                v.setFechaCompra(rs.getString("fecha_compra"));
                v.setMarca(rs.getString("marca"));
                v.setVin(rs.getString("vin"));
                v.setSerie(rs.getString("serie"));
                v.setValorFactura(rs.getDouble("valor_factura"));
                v.setImporteNeto(rs.getDouble("valor_factura"));
                v.setCarteraFinanciera(rs.getString("cartera_financiera"));
                v.setUbicacion(rs.getString("ubicacion"));
                v.setFactura(rs.getString("factura"));
                lista.add(v);
            }
        }catch(SQLException e){
            System.out.println("Error al buscar los vehiculos: "+e.getLocalizedMessage());
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
    
    public static long calcularDias(long fechaAct, long fechaConv){
        
        long diferenciaSegundos = fechaAct - fechaConv;
        long segundos = diferenciaSegundos/1000;
        long horas = segundos/3600;
        long dias = horas/24;
        
        return dias;
    }
    
    public static Date sumaFecha(Date fecha, int dias){
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); 
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
        
    }
    /*
    public void modificarVehiculo(String status, String vin , int diasReales){
         if(con == null ){
            con = ConexionMySQL.conectarPP();
        }
        if(con != null){
            try{
                String SQl = "UPDATE vehiculo SET "
                        +   "status = ? ,"
                        +   "dias_reales_financiamiento = ? "
                        +   "WHERE vin = ?";
                PreparedStatement ps = con.prepareStatement(SQl);

                ps.setString(3, vin);
                ps.setString(1, status);
                ps.setInt(2, diasReales);

                ps.executeUpdate();
            }catch(SQLException e){
                System.out.println("Error al modificar el vehiculo: "+e.getLocalizedMessage());
            }finally {
                    try {
                        con.close();
                    } catch (SQLException ee) {
                        System.out.println("SQL ERROR-2 " + ee.getSQLState() + ee.getMessage());
                    }
                }
        }
    }
    */
   
    
}
