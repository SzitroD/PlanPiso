package dao;

import control.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import modelo.VistaCompras;

public class VistasDAO {
    
    //Buscar vehiculos por nombre de financiera
    public static ArrayList<VistaCompras> buscarCompraFinanciera(String financiera){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){

            try{
                String SQL = 
                    "SELECT * ,\n" +
                    "case\n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then vista_general.interes_real/100\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then 0/100\n" +
                    "	when statusFinanciamiento = 'REFINANCIADA' then vista_general.interes_extra_real/100\n" +
                    " end as tasa,\n" +
                    "case \n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                    "   when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "end as interes_vehiculo\n" +
                    "FROM plan_piso.vista_general WHERE nombreBanco = '"+financiera+"' AND statusBanco = 1";
                
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    VistaCompras v = new VistaCompras();

                    v.setFechaCompra(rs.getString("fecha_compra"));
                    v.setMarca(rs.getString("marca"));
                    v.setVin(rs.getString("vin"));
                    v.setSerie(rs.getString("serie"));
                    v.setValorFactura(rs.getDouble("valor_factura"));
                    v.setImporteNeto(rs.getDouble("valor_factura"));
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
                    v.setPrioridadPago(rs.getString("prioridad_pago"));
                    v.setReportadoNVDR(rs.getString("reportado_nvdr"));
                    v.setObservaciones(rs.getString("observaciones"));
                    v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                    v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                    v.setCf(rs.getDouble("cf"));
                    v.setInteresReal(rs.getDouble("interes_real"));
                    v.setInteresExtraReal(rs.getDouble("interes_extra_real"));
                    v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));

                    v.setIdBanco(rs.getInt("id_banco"));
                    v.setNombreBanco(rs.getString("nombreBanco"));
                    v.setInteres(rs.getDouble("interes"));
                    v.setDiasFinanciamiento(rs.getInt("diasFinanciamiento"));
                    v.setDiasExtra(rs.getInt("diasExtra"));
                    v.setInteresExtra(rs.getDouble("interesExtra"));
                    v.setDiasLibres(rs.getInt("diasLibres"));
                    v.setStatusBanco(rs.getInt("statusBanco"));
                    
                    v.setDias(rs.getInt("dias"));
                    v.setTasa(rs.getDouble("tasa"));
                    v.setInteresVehiculo(rs.getDouble("interes_vehiculo"));

                    v.setLinea(rs.getDouble("linea"));
                    lista.add(v);
                }
            }catch(SQLException e){
                System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
    
    //Buscar vehiculos por vin 
    public static ArrayList<VistaCompras> buscarCompraVin(String vin){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){

            try{
                String SQL = 
                    "SELECT * ,\n" +
                    "case\n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then vista_general.interes_real/100\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then 0/100\n" +
                    "	when statusFinanciamiento = 'REFINANCIADA' then vista_general.interes_extra_real/100\n" +
                    " end as tasa,\n" +
                    "case \n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                    "   when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "end as interes_vehiculo\n" +
                    "FROM plan_piso.vista_general WHERE vin LIKE '%"+vin+"%' AND statusBanco = 1";
                
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    VistaCompras v = new VistaCompras();

                    v.setFechaCompra(rs.getString("fecha_compra"));
                    v.setMarca(rs.getString("marca"));
                    v.setVin(rs.getString("vin"));
                    v.setSerie(rs.getString("serie"));
                    v.setValorFactura(rs.getDouble("valor_factura"));
                    v.setImporteNeto(rs.getDouble("valor_factura"));
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
                    v.setPrioridadPago(rs.getString("prioridad_pago"));
                    v.setReportadoNVDR(rs.getString("reportado_nvdr"));
                    v.setObservaciones(rs.getString("observaciones"));
                    v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                    v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                    v.setCf(rs.getDouble("cf"));
                    v.setInteresReal(rs.getDouble("interes_real"));
                    v.setInteresExtraReal(rs.getDouble("interes_extra_real"));
                    v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));

                    v.setIdBanco(rs.getInt("id_banco"));
                    v.setNombreBanco(rs.getString("nombreBanco"));
                    v.setInteres(rs.getDouble("interes"));
                    v.setDiasFinanciamiento(rs.getInt("diasFinanciamiento"));
                    v.setDiasExtra(rs.getInt("diasExtra"));
                    v.setInteresExtra(rs.getDouble("interesExtra"));
                    v.setDiasLibres(rs.getInt("diasLibres"));
                    v.setStatusBanco(rs.getInt("statusBanco"));
                    
                    v.setDias(rs.getInt("dias"));
                    v.setTasa(rs.getDouble("tasa"));
                    v.setInteresVehiculo(rs.getDouble("interes_vehiculo"));

                    v.setLinea(rs.getDouble("linea"));
                    lista.add(v);
                }
            }catch(SQLException e){
                System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
    
    //Buscar vehiculos por vin, calculando el interes total 
    public static ArrayList<VistaCompras> buscarVehiculo(String vin){
         Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
        try{
            String SQL = 
                "SELECT statusBanco,vin,marca,ubicacion,prioridad_pago,cartera_financiera,statusFinanciamiento,fecha_financiamiento,nombreBanco,dias_reales,dias,\n" +
                "case \n" +
                "   when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then ((((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "   when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then ((((0/100)  * vista_general.valor_factura)/360)* dias) + vista_general.valor_factura\n" +
                "   when statusFinanciamiento = 'REFINANCIADA' then ((((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "end as total_pago\n" +
                "FROM plan_piso.vista_general WHERE vin LIKE'%"+vin+"%' AND statusBanco = 1";
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                VistaCompras v = new VistaCompras();
                
                v.setStatusBanco(rs.getInt("statusBanco"));
                v.setVin(rs.getString("vin"));
                v.setMarca(rs.getString("marca"));
                v.setUbicacion(rs.getString("ubicacion"));
                v.setPrioridadPago(rs.getString("prioridad_pago"));
                v.setCarteraFinanciera(rs.getString("cartera_financiera"));
                v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));
                v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                v.setNombreBanco(rs.getString("nombreBanco"));
                v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                v.setDias(rs.getInt("dias"));
                
                v.setTotalPago(rs.getDouble("total_pago"));
                
                lista.add(v);
            }
            return lista;
        }catch(SQLException e){
            System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
    
    //Buscar vehiculos con calculos de interes, dias transcurridos y total a pagar para conciliacion.jsp
     public static ArrayList<VistaCompras> listas_conciliacion(){
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
        try{
            String SQL = 
                "SELECT id_banco,linea,nombreBanco,prioridad_pago, sum(cf) as total_cf, count(vin) as total_vehiculos,\n" +
                "case\n" +
                "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then vista_general.interes_real/100\n" +
                "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then 0/100\n" +
                "	when statusFinanciamiento = 'REFINANCIADA' then vista_general.interes_extra_real/100\n" +
                " end as tasa,\n" +
                "sum( \n" +
                "case \n" +
                "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                "       when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                "end) as total_interes,\n" +
                "sum(vista_general.valor_factura) as total_pago_neto,\n" +
                "sum(\n" +
                "case \n" +
                "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then ((((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then ((((0/100)  * vista_general.valor_factura)/360)* dias) + vista_general.valor_factura\n" +
                "       when statusFinanciamiento = 'REFINANCIADA' then ((((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "end) as total_pago\n" +
                "FROM plan_piso.vista_general\n" +
                "WHERE statusBanco = 1 \n" +    
                "GROUP BY id_banco, prioridad_pago;" ;
            
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                VistaCompras v = new VistaCompras();
                
                v.setIdBanco(rs.getInt("id_banco"));
                v.setNombreBanco(rs.getString("nombreBanco"));
                v.setPrioridadPago(rs.getString("prioridad_pago"));
                v.setTotalCF(rs.getDouble("total_cf"));
                v.setTotalVehiculos(rs.getInt("total_vehiculos"));
                v.setTasa(rs.getDouble("tasa"));
                v.setTotalInteres(rs.getDouble("total_interes"));
                v.setTotalImporteNeto(rs.getDouble("total_pago_neto"));
                v.setTotalPago(rs.getDouble("total_pago"));
                v.setLinea(rs.getDouble("linea"));
                lista.add(v);
            }
            return lista;
        }catch(SQLException e){
            System.out.println("Error al listar vista de resumen: "+e.getLocalizedMessage());
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
    
    //Buscar vehiculos por nombre de financiera, con status = PAGADA 
     public static ArrayList<VistaCompras> buscarPagadas(String financiera){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){

            try{
                String SQL = 
                    "SELECT * , "+ 
                    "case\n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then vista_general.interes_real/100\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then 0/100\n" +
                    "	when statusFinanciamiento = 'REFINANCIADA' then vista_general.interes_extra_real/100\n" +
                    " end as tasa,\n" +
                    "case \n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                    "   when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "end as interes_vehiculo\n" +
                    "FROM plan_piso.vista_general WHERE nombreBanco = '"+financiera+"' AND (statusFinanciamiento = 'PAGADA' AND statusBanco = 1)";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    VistaCompras v = new VistaCompras();

                    v.setFechaCompra(rs.getString("fecha_compra"));
                    v.setMarca(rs.getString("marca"));
                    v.setVin(rs.getString("vin"));
                    v.setSerie(rs.getString("serie"));
                    v.setValorFactura(rs.getDouble("valor_factura"));
                    v.setImporteNeto(rs.getDouble("valor_factura"));
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
                    v.setPrioridadPago(rs.getString("prioridad_pago"));
                    v.setReportadoNVDR(rs.getString("reportado_nvdr"));
                    v.setObservaciones(rs.getString("observaciones"));
                    v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                    v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                    v.setCf(rs.getDouble("cf"));
                    v.setInteresReal(rs.getDouble("interes_real"));
                    v.setInteresExtraReal(rs.getDouble("interes_extra_real"));
                    v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));

                    v.setIdBanco(rs.getInt("id_banco"));
                    v.setNombreBanco(rs.getString("nombreBanco"));
                    v.setInteres(rs.getDouble("interes"));
                    v.setDiasFinanciamiento(rs.getInt("diasFinanciamiento"));
                    v.setDiasExtra(rs.getInt("diasExtra"));
                    v.setInteresExtra(rs.getDouble("interesExtra"));
                    v.setDiasLibres(rs.getInt("diasLibres"));
                    v.setStatusBanco(rs.getInt("statusBanco"));

                    v.setDias(rs.getInt("dias"));
                    v.setTasa(rs.getDouble("tasa"));
                    v.setInteresVehiculo(rs.getDouble("interes_vehiculo"));

                    v.setLinea(rs.getDouble("linea"));
                    lista.add(v);
                }
            }catch(SQLException e){
                System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
     
     
     //Buscar vehiculos por vin con status = PAGADA
    public static ArrayList<VistaCompras> buscarPagadasVin(String vin){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){

            try{
                String SQL = 
                    "SELECT * , "+ 
                    "case\n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then vista_general.interes_real/100\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then 0/100\n" +
                    "	when statusFinanciamiento = 'REFINANCIADA' then vista_general.interes_extra_real/100\n" +
                    " end as tasa,\n" +
                    "case \n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                    "   when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "end as interes_vehiculo\n" +
                    "FROM plan_piso.vista_general WHERE vin LIKE '%"+vin+"%' AND (statusFinanciamiento = 'PAGADA' AND statusBanco = 1)";
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    VistaCompras v = new VistaCompras();

                    v.setFechaCompra(rs.getString("fecha_compra"));
                    v.setMarca(rs.getString("marca"));
                    v.setVin(rs.getString("vin"));
                    v.setSerie(rs.getString("serie"));
                    v.setValorFactura(rs.getDouble("valor_factura"));
                    v.setImporteNeto(rs.getDouble("valor_factura"));
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
                    v.setPrioridadPago(rs.getString("prioridad_pago"));
                    v.setReportadoNVDR(rs.getString("reportado_nvdr"));
                    v.setObservaciones(rs.getString("observaciones"));
                    v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                    v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                    v.setCf(rs.getDouble("cf"));
                    v.setInteresReal(rs.getDouble("interes_real"));
                    v.setInteresExtraReal(rs.getDouble("interes_extra_real"));
                    v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));

                    v.setIdBanco(rs.getInt("id_banco"));
                    v.setNombreBanco(rs.getString("nombreBanco"));
                    v.setInteres(rs.getDouble("interes"));
                    v.setDiasFinanciamiento(rs.getInt("diasFinanciamiento"));
                    v.setDiasExtra(rs.getInt("diasExtra"));
                    v.setInteresExtra(rs.getDouble("interesExtra"));
                    v.setDiasLibres(rs.getInt("diasLibres"));
                    v.setStatusBanco(rs.getInt("statusBanco"));

                    v.setDias(rs.getInt("dias"));
                    v.setTasa(rs.getDouble("tasa"));
                    v.setInteresVehiculo(rs.getDouble("interes_vehiculo"));
                    v.setLinea(rs.getDouble("linea"));
                    lista.add(v);
                }
            }catch(SQLException e){
                System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
     
    //Listar vehiculos con interes y total de pago para generar informacion necesaria para un archivo excel
     public static ArrayList<VistaCompras> listarVehiculosExcel(){
         Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
        try{
            String SQL = 
                "SELECT vin,prioridad_pago,tipo_venta,valor_factura,id_banco,nombreBanco,cf,\n" +
                "case \n" +
                "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                "       when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                "end as interes,\n" +
                "case \n" +
                "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then ((((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then ((((0/100)  * vista_general.valor_factura)/360)* dias) + vista_general.valor_factura\n" +
                "       when statusFinanciamiento = 'REFINANCIADA' then ((((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "end as total_pago\n" +
                "FROM plan_piso.vista_general "
                + "WHERE statusBanco = 1" ;
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                VistaCompras v = new VistaCompras();
                
                v.setVin(rs.getString("vin"));
                v.setImporteNeto(rs.getDouble("valor_factura"));
                v.setTipoVenta(rs.getString("tipo_venta"));
                v.setPrioridadPago(rs.getString("prioridad_pago"));
                v.setIdBanco(rs.getInt("id_banco"));
                v.setNombreBanco(rs.getString("nombreBanco"));
                v.setCf(rs.getDouble("cf"));
                
                v.setInteres(rs.getDouble("interes"));
                v.setTotalPago(rs.getDouble("total_pago"));
                
                lista.add(v);
            }
            return lista;
        }catch(SQLException e){
            System.out.println("Error al listar vista: "+e.getLocalizedMessage());
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
     
    public static ArrayList<VistaCompras> listarVehiculosFiltro(){
         
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
        try{
            String SQL = 
                "SELECT diasExtra,statusBanco,vin,marca,ubicacion,prioridad_pago,cartera_financiera,statusFinanciamiento,fecha_financiamiento,nombreBanco,dias_reales,dias,\n" +
                "case \n" +
                "   when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then ((((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "   when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then ((((0/100)  * vista_general.valor_factura)/360)* dias) + vista_general.valor_factura\n" +
                "   when statusFinanciamiento = 'REFINANCIADA' then ((((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "end as total_pago\n" +
                "FROM plan_piso.vista_general "
                    + "WHERE statusBanco = 1";
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                VistaCompras v = new VistaCompras();
                
                v.setStatusBanco(rs.getInt("statusBanco"));
                v.setVin(rs.getString("vin"));
                v.setMarca(rs.getString("marca"));
                v.setUbicacion(rs.getString("ubicacion"));
                v.setPrioridadPago(rs.getString("prioridad_pago"));
                v.setCarteraFinanciera(rs.getString("cartera_financiera"));
                v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));
                v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                v.setNombreBanco(rs.getString("nombreBanco"));
                v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                v.setDias(rs.getInt("dias"));
                v.setDiasExtra(rs.getInt("diasExtra"));
                
                v.setTotalPago(rs.getDouble("total_pago"));
                
                lista.add(v);
            }
            return lista;
        }catch(SQLException e){
            System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
    //Listar todos los vehiculos sin ningun filtro
    public static ArrayList<VistaCompras> listarVehiculos(){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){

            try{
                String SQL = 
                    "SELECT * ,\n" +
                    "case\n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then vista_general.interes_real/100\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then 0/100\n" +
                    "	when statusFinanciamiento = 'REFINANCIADA' then vista_general.interes_extra_real/100\n" +
                    " end as tasa,\n" +
                    "case \n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                    "   when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "end as interes_vehiculo\n" +
                    "FROM plan_piso.vista_general "
                        + "WHERE statusBanco = 1";
                
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    VistaCompras v = new VistaCompras();

                    v.setFechaCompra(rs.getString("fecha_compra"));
                    v.setMarca(rs.getString("marca"));
                    v.setVin(rs.getString("vin"));
                    v.setSerie(rs.getString("serie"));
                    v.setValorFactura(rs.getDouble("valor_factura"));
                    v.setImporteNeto(rs.getDouble("valor_factura"));
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
                    v.setPrioridadPago(rs.getString("prioridad_pago"));
                    v.setReportadoNVDR(rs.getString("reportado_nvdr"));
                    v.setObservaciones(rs.getString("observaciones"));
                    v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                    v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                    v.setCf(rs.getDouble("cf"));
                    v.setInteresReal(rs.getDouble("interes_real"));
                    v.setInteresExtraReal(rs.getDouble("interes_extra_real"));
                    v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));

                    v.setIdBanco(rs.getInt("id_banco"));
                    v.setNombreBanco(rs.getString("nombreBanco"));
                    v.setInteres(rs.getDouble("interes"));
                    v.setDiasFinanciamiento(rs.getInt("diasFinanciamiento"));
                    v.setDiasExtra(rs.getInt("diasExtra"));
                    v.setInteresExtra(rs.getDouble("interesExtra"));
                    v.setDiasLibres(rs.getInt("diasLibres"));
                    v.setStatusBanco(rs.getInt("statusBanco"));
                    
                    v.setDias(rs.getInt("dias"));
                    v.setTasa(rs.getDouble("tasa"));
                    v.setInteresVehiculo(rs.getDouble("interes_vehiculo"));

                    v.setLinea(rs.getDouble("linea"));
                    lista.add(v);
                }
            }catch(SQLException e){
                System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
    
    //Listar todos los vehiculos con un status = PAGADA
    public static ArrayList<VistaCompras> listarVehiculosPagados(){
        
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){

            try{
                String SQL = 
                    "SELECT * ,\n" +
                    "case\n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then vista_general.interes_real/100\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then 0/100\n" +
                    "	when statusFinanciamiento = 'REFINANCIADA' then vista_general.interes_extra_real/100\n" +
                    " end as tasa,\n" +
                    "case \n" +
                    "	when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then (((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "	when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then (((0/100)  * vista_general.valor_factura)/360)* dias\n" +
                    "   when statusFinanciamiento = 'REFINANCIADA' then (((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias\n" +
                    "end as interes_vehiculo\n" +
                    "FROM plan_piso.vista_general WHERE statusFinanciamiento = 'PAGADA' AND statusBanco = 1";
                
                PreparedStatement ps = conPP.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    VistaCompras v = new VistaCompras();

                    v.setFechaCompra(rs.getString("fecha_compra"));
                    v.setMarca(rs.getString("marca"));
                    v.setVin(rs.getString("vin"));
                    v.setSerie(rs.getString("serie"));
                    v.setValorFactura(rs.getDouble("valor_factura"));
                    v.setImporteNeto(rs.getDouble("valor_factura"));
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
                    v.setPrioridadPago(rs.getString("prioridad_pago"));
                    v.setReportadoNVDR(rs.getString("reportado_nvdr"));
                    v.setObservaciones(rs.getString("observaciones"));
                    v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                    v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                    v.setCf(rs.getDouble("cf"));
                    v.setInteresReal(rs.getDouble("interes_real"));
                    v.setInteresExtraReal(rs.getDouble("interes_extra_real"));
                    v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));

                    v.setIdBanco(rs.getInt("id_banco"));
                    v.setNombreBanco(rs.getString("nombreBanco"));
                    v.setInteres(rs.getDouble("interes"));
                    v.setDiasFinanciamiento(rs.getInt("diasFinanciamiento"));
                    v.setDiasExtra(rs.getInt("diasExtra"));
                    v.setInteresExtra(rs.getDouble("interesExtra"));
                    v.setDiasLibres(rs.getInt("diasLibres"));
                    v.setStatusBanco(rs.getInt("statusBanco"));
                    
                    v.setDias(rs.getInt("dias"));
                    v.setTasa(rs.getDouble("tasa"));
                    v.setInteresVehiculo(rs.getDouble("interes_vehiculo"));

                    v.setLinea(rs.getDouble("linea"));
                    lista.add(v);
                }
            }catch(SQLException e){
                System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
    
//Listar vehiculos sin seguro. Vehiculos que sobrepasan 365 dias desde su fecha de compra    
    public static ArrayList<VistaCompras> listarVehiculosSinSeguro(){
         
        Connection conPP = ConexionMySQL.conectarPP();
        ArrayList<VistaCompras> lista = new ArrayList<>();
        
        if(conPP == null){
            conPP = ConexionMySQL.conectarPP();
        }
        if(conPP != null){
        try{
            String SQL = 
                "SELECT fecha_compra,diasExtra,statusBanco,vin,marca,ubicacion,prioridad_pago,cartera_financiera,statusFinanciamiento,fecha_financiamiento,nombreBanco,dias_reales,dias,\n" +
                "case \n"+ 
                "   when `fecha_compra` > '2000-01-01' \n"+
                "   then (TO_DAYS(CURDATE()) - TO_DAYS(`fecha_compra`))\n" +
                "end as dias_seguro, \n" +
                "case \n" +
                "   when dias <= vista_general.dias_reales and statusFinanciamiento != 'REFINANCIADA' then ((((vista_general.interes_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "   when dias = 0 and statusFinanciamiento != 'REFINANCIADA' then ((((0/100)  * vista_general.valor_factura)/360)* dias) + vista_general.valor_factura\n" +
                "   when statusFinanciamiento = 'REFINANCIADA' then ((((vista_general.interes_extra_real/100) * (vista_general.valor_factura))/360) * dias) + vista_general.valor_factura\n" +
                "end as total_pago\n" +
                "FROM plan_piso.vista_general "
                    + "WHERE statusBanco = 1";
            PreparedStatement ps = conPP.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()){
                VistaCompras v = new VistaCompras();
                
                v.setFechaCompra(rs.getString("fecha_compra"));
                v.setStatusBanco(rs.getInt("statusBanco"));
                v.setVin(rs.getString("vin"));
                v.setMarca(rs.getString("marca"));
                v.setUbicacion(rs.getString("ubicacion"));
                v.setPrioridadPago(rs.getString("prioridad_pago"));
                v.setCarteraFinanciera(rs.getString("cartera_financiera"));
                v.setStatusFinanciamiento(rs.getString("statusFinanciamiento"));
                v.setFechaFinanciamiento(rs.getString("fecha_financiamiento"));
                v.setNombreBanco(rs.getString("nombreBanco"));
                v.setDiasRealesFinanciamiento(rs.getInt("dias_reales"));
                v.setDias(rs.getInt("dias"));
                v.setDiasExtra(rs.getInt("diasExtra"));
                v.setDiasSeguro(rs.getInt("dias_seguro"));
                
                v.setTotalPago(rs.getDouble("total_pago"));
                
                lista.add(v);
            }
            return lista;
        }catch(SQLException e){
            System.out.println("Error al buscar en la vista: "+e.getLocalizedMessage());
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
