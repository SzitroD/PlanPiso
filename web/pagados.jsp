<%-- 
    Document   : pagados
    Created on : 14/11/2019, 10:23:23 AM
    Author     : Toshiba
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="dao.BancosDAO"%>
<%@page import="dao.VistasDAO"%>
<%@page import="modelo.VistaCompras"%>
<%@page import="dao.VehiculosDAO"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="modelo.Bancos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%
    HttpSession sesion = request.getSession();
    String usuarioValidado = (String) sesion.getAttribute("usuario");
    
    if (usuarioValidado == null) {
        response.sendRedirect("login.jsp");
    }else{
%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
	
        <title>Plan Piso</title>
        
	<link rel="stylesheet" href="./css/pagos.css">
	<link rel="stylesheet" href="./css/header.css">
	<link rel="stylesheet" href="./css/footer.css">

</head>
<body>

    <%@include file="header.jsp" %>
    
     <div class="contenedor-titulo primer-titulo">
        <h2 class="titulo">Vehiculos terminados de pagar</h2>
    </div>
    
    <div class="contenedor-busqueda">
        <form action="ControlPagados" method="get" class="form-busqueda">
            <input type="search" name="busqueda" placeholder="Vin del Vehiculo" class="search">
            <input type="submit" name="accion" value="Buscar" class="btn-enviar">
        </form>
    </div>
    
     <div class="contenedor-btn-financiera">
        <form class="form-btn" action="ControlPagados" method="get">
            <% for(Bancos b : BancosDAO.listarBancos()){
                    if(b.getStatus()== 1){
            %>
            <input class="btn" type="submit" name="accion" value="<%= b.getNombreBanco() %>">
        <%          }
                }
        %>
        </form>
    </div>

    <div class="contenedor-titulo">
        <h2 class="titulo">Resultado</h2>
    </div>
         
    <div class="contendor-tabla ultimo-contenido" >
        <table class="table">
            <thead>
                <tr class="thead">
                    <th class="dato-auto">Fecha de Compra</th>
                    <th class="dato-auto">Marca</th>
                    <th class="dato-auto">VIN</th>
                    <th class="dato-auto">Serie</th>
                    <th class="dato-auto">Valor Factura</th>
                    <th class="dato-auto">Importe a pagar NETO</th>
                    <th class="dato-auto">Proveedor</th>
                    <th class="dato-auto">Status</th>
                    <th class="dato-auto">Ubicacion</th>
                    <th class="dato-auto">Financiado con</th>
                    <th class="dato-auto">Fecha Financiamiento</th>
                    <th class="dato-compra">Dias de Credito Fijos</th>
                    <th class="dato-compra">Dias de Credito Reales</th>
                    <th class="dato-compra">Dias Transcurrido</th>
                    <th class="dato-compra">Tasa de interes</th>
                    <th class="dato-compra">Interes</th>
                    <th class="dato-compra">Importe a pagar</th>
                    <th class="dato-compra">Reporte Inventario</th>
                    <th class="dato-cliente">Factura</th>
                    <th class="dato-cliente">Fecha Factura</th>
                    <th class="dato-cliente">Nombre Cliente</th>
                    <th class="dato-cliente">ID Tipo de Venta</th>
                    <th class="dato-cliente">Tipo de Venta</th>
                    <th class="dato-cliente">Valor de Venta</th>
                    <th class="dato-cliente">Pago del Cliente</th>
                    <th class="dato-cliente">Saldo actual</th>
                    <th class="dato-cliente">DB</th>
                    <th class="dato-cliente">Fecha Carga</th>
                    <th class="dato-cliente">Prioridad Pago</th>
                    <th class="dato-cliente">Reporte NVDR</th>
                    <th class="dato-cliente">Observacion</th>
                    <th class="dato-cliente">CF</th>
                    <th class="dato-cliente">Diferencia</th>
                </tr>
            </thead>
            <tbody class="tbody">
            <%  
                String accion = null;
                DecimalFormat formateador = new DecimalFormat("###,###,###.00");

                try{
                    accion = request.getParameter("accion");
                }catch(Exception e){
                    System.out.println("error al recibir accion "+e);
                }

                String financiera = "";
                String Busqueda = "";
                double impPago = 0;
                double diferencia = 0;
                if(accion != null){
                    if(accion.equals("Buscar")){
                        Busqueda = request.getParameter("busqueda");
                        for(VistaCompras v: VistasDAO.buscarPagadasVin(Busqueda)){
                            if(v.getStatusBanco()==1){
                            
                            impPago = v.getInteresVehiculo() + v.getImporteNeto();
                            diferencia = v.getImporteNeto() - v.getCf();
            %>
                <tr>            
                    <td> <%= v.getFechaCompra() %> </td>
                    <td> <%= v.getMarca() %> </td>
                    <td> <%= v.getVin() %> </td>
                    <td> <%= v.getSerie() %> </td>
                    <td> $<%= formateador.format(v.getValorFactura()) %> </td>
                    <td> $<%= formateador.format(v.getImporteNeto()) %> </td>
                    <td> <%= v.getCarteraFinanciera() %> </td>
                    <td> <%= v.getStatusFinanciamiento()%> </td>
                    <td> <%= v.getUbicacion() %> </td>
                    <td> <%= v.getNombreBanco() %> </td>
                    <td> <%= v.getFechaFinanciamiento() %> </td>
                    <td> <%= v.getDiasFinanciamiento() %> </td>   
                    <td> <%= v.getDiasRealesFinanciamiento() %> </td>
                    <td class="calcular"> <%= v.getDias() %> </td>
                    <td class="calcular"> <%= v.getTasa() %> </td>
                    <td class="calcular"> $<%= formateador.format(v.getInteresVehiculo()) %> </td>
                    <td class="calcular"> $<%= formateador.format(impPago) %> </td>
                    <td> </td>
                    <td> <%= v.getFactura()%> </td>
                    <td> <%= v.getFechaFactura() %> </td>
                    <td> <%= v.getCliente() %> </td>
                    <td> <%= v.getIdTipoVenta() %> </td>
                    <td> <%= v.getTipoVenta() %> </td>
                    <td> $<%= formateador.format(v.getValorVenta()) %> </td>
                    <td> $<%= formateador.format(v.getPagoCliente()) %> </td>
                    <td> $<%= formateador.format(v.getSaldo()) %> </td>
                    <td> <%= v.getDb() %> </td>
                    <td> <%= v.getFechaCarga() %> </td>
                    <td> <%= v.getPrioridadPago() %> </td>
                    <td> <%= v.getReportadoNVDR() %> </td>
                    <td> <%= v.getObservaciones() %> </td>
                    <td class="calcular"> $<%= formateador.format(v.getCf()) %> </td>
                    <td class="calcular"> $<%= formateador.format(diferencia) %> </td>
                </tr> 
                <%          }//System.out.println("Iteraciones : "+ i);
                        }     //System.out.println("Total Repeticiones: "+ i);
                    }else{ 
                        financiera = accion;
                        for(VistaCompras v: VistasDAO.buscarPagadas(financiera)){ 
                            if(v.getStatusBanco()==1){
                        
                            impPago = v.getInteresVehiculo() + v.getImporteNeto();
                            diferencia = v.getImporteNeto() - v.getCf();
                     
                %>
                
                <tr>            
                    <td> <%= v.getFechaCompra() %> </td>
                    <td> <%= v.getMarca() %> </td>
                    <td> <%= v.getVin() %> </td>
                    <td> <%= v.getSerie() %> </td>
                    <td> $<%= formateador.format(v.getValorFactura()) %> </td>
                    <td> $<%= formateador.format(v.getImporteNeto()) %> </td>
                    <td> <%= v.getCarteraFinanciera() %> </td>
                    <td> <%= v.getStatusFinanciamiento()%> </td>
                    <td> <%= v.getUbicacion() %> </td>
                    <td> <%= v.getNombreBanco() %> </td>
                    <td> <%= v.getFechaFinanciamiento() %> </td>
                    <td> <%= v.getDiasFinanciamiento() %> </td>   
                    <td> <%= v.getDiasRealesFinanciamiento() %> </td>
                    <td class="calcular"> <%= v.getDias() %> </td>
                    <td class="calcular"> <%= v.getTasa() %> </td>
                    <td class="calcular"> $<%= formateador.format(v.getInteresVehiculo()) %> </td>
                    <td class="calcular"> $<%= formateador.format(impPago) %> </td>
                    <td> </td>
                    <td> <%= v.getFactura()%> </td>
                    <td> <%= v.getFechaFactura() %> </td>
                    <td> <%= v.getCliente() %> </td>
                    <td> <%= v.getIdTipoVenta() %> </td>
                    <td> <%= v.getTipoVenta() %> </td>
                    <td> $<%= formateador.format(v.getValorVenta()) %> </td>
                    <td> $<%= formateador.format(v.getPagoCliente()) %> </td>
                    <td> $<%= formateador.format(v.getSaldo()) %> </td>
                    <td> <%= v.getDb() %> </td>
                    <td> <%= v.getFechaCarga() %> </td>
                    <td> <%= v.getPrioridadPago() %> </td>
                    <td> <%= v.getReportadoNVDR() %> </td>
                    <td> <%= v.getObservaciones() %> </td>
                    <td class="calcular"> $<%= formateador.format(v.getCf()) %> </td>
                    <td class="calcular"> $<%= formateador.format(diferencia) %> </td>
                </tr> 
            </tbody>  
        <%                  }
                        }
                    }
                }else{
                    //System.out.println("accion es null");
                }
        %>
        </table>
    </div>

    <%@include file="footer.jsp" %>
 
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="./js/jquery/jquery.table2excel.min.js"></script>
    <script type="text/javascript" src= "./js/main.js"></script>
 
</body>
</html>

<% } %>