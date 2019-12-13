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
    
    //Listar todos los vehiculos sin ningun filtro
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
    
    //Listar vehiculos que no esten financiados
    public static ArrayList<Vehiculo> listarVehiculoFinanciar(){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<Vehiculo> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
            try{
                String SQL = "SELECT * FROM vehiculo WHERE vin NOT IN (SELECT vin_vehiculo FROM financiar)";
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
    
    //Listar vehiculos por vin y que estos no esten financiados
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
    
}
