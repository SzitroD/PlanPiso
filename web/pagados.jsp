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
        <link rel="stylesheet" href="./css/style.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
        <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>

</head>
<body>

    <%@include file="header.jsp" %>
    
     
    <div class="container-fluid bg-light" style="margin-top:75px; height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra" >Vehiculos pagados</h2>
        </div>
    </div>
    
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Busqueda por financiera</h3>
        </div>
    </div>
    
    <!-- FORMULARIO PARA FILTRADO DE VEHICULOS POR NOMBRE DE FINANCIERA -->
    
    <div class="container bg-light" style="padding: 15px 0px;">
        <form class="row w-100 p-0 m-0 justify-content-around align-items-center" action="ControlPagados" method="post">
        <%  for(Bancos b: BancosDAO.listarBancos()){ 
                if(b.getStatus() == 1){
        %>
        <input type="submit" class="btn text-light" name="nomFinanciera" value="<%= b.getNombreBanco() %>" style="margin: 5px; background-color: #023059">
        <%      } 
            }
        %>
        </form>
    </div>
    
          <!-- TABLA DE VEHICULOS FILTRADO POR NOMBRE DE FINANCIERA --> 
    <div class="container bg-light" style="padding-top:20px;">
        <table class="table table-boostrap-3 table-striped table-bordered tipo-letra text-center" style="width: 100%;">
            <thead>
                <tr>
                    <th>Fecha de Compra</th>
                    <th>Marca</th>
                    <th>VIN</th>
                    <th>Serie</th>
                    <th>Valor Factura</th>
                    <th>Importe a pagar NETO</th>
                    <th>Proveedor</th>
                    <th>Status Financiamiento</th>
                    <th>Ubicacion</th>
                    <th>Financiado con</th>
                    <th>Fecha Financiamiento</th>
                    <th>Dias de Credito Fijos</th>
                    <th>Dias de Credito Reales</th>
                    <th>Dias Transcurrido</th>
                    <th>Tasa de interes Fija</th>
                    <th>Tasa de interes Real</th>
                    <th>Interes</th>
                    <th>Importe a pagar</th>
                    <th>Reporte Inventario</th>
                    <th>Factura</th>
                    <th>Fecha Factura</th>
                    <th>Nombre Cliente</th>
                    <th>ID Tipo de Venta</th>
                    <th>Tipo de Venta</th>
                    <th>Valor de Venta</th>
                    <th>Pago del Cliente</th>
                    <th>Saldo actual</th>
                    <th>DB</th>
                    <th>Fecha Carga</th>
                    <th>Prioridad Pago</th>
                    <th>Reporte NVDR</th>
                    <th>Observacion</th>
                    <th>CF</th>
                    <th>Diferencia</th>
                </tr>
            </thead>
            <tbody>
            <%  
                //Parametro recibido para filtro en la tabla 
                String financiera = request.getParameter("nomFinanciera");
                
                    //Formato en que se presentan las cantidades 
                DecimalFormat formateador = new DecimalFormat("###,###,###.00");

                double impPago = 0;
                double diferencia = 0;
                
                for(VistaCompras v: VistasDAO.buscarPagadas(financiera)){
                    

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
                    <td> <%= v.getDias() %> </td>
                    <td> <%= v.getTasa() %> </td>
                    <td> <%= v.getInteresReal() %> </td>
                    <td> $<%= formateador.format(v.getInteresVehiculo()) %> </td>
                    <td> $<%= formateador.format(impPago) %> </td>
                    <td>  </td>
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
                <%          //System.out.println("Iteraciones : "+ i);
                        }     //System.out.println("Total Repeticiones: "+ i);
                %>
                
            </tbody>  
        </table>
    </div>
    
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Busqueda rapida</h3>
        </div>
    </div>
         
    <!-- TABLA DE VEHICULOS FILTRADO POR NOMBRE FINANCIERA -->      
                
    <div class="container bg-light" style="padding-top: 20px; padding-bottom: 10px; margin-bottom: 80px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
        <table class="table table-striped table-bordered tipo-letra text-center" style="width: 100%;" id="table-boostrap">
            <thead>
                <tr>
                    <th>Fecha de Compra</th>
                    <th>Marca</th>
                    <th>VIN</th>
                    <th>Serie</th>
                    <th>Valor Factura</th>
                    <th>Importe a pagar NETO</th>
                    <th>Proveedor</th>
                    <th>Status Financiamiento</th>
                    <th>Ubicacion</th>
                    <th>Financiado con</th>
                    <th>Fecha Financiamiento</th>
                    <th>Dias de Credito Fijos</th>
                    <th>Dias de Credito Reales</th>
                    <th>Dias Transcurrido</th>
                    <th>Tasa de interes Fija</th>
                    <th>Tasa de interes Real</th>
                    <th>Interes</th>
                    <th>Importe a pagar</th>
                    <th>Reporte Inventario</th>
                    <th>Factura</th>
                    <th>Fecha Factura</th>
                    <th>Nombre Cliente</th>
                    <th>ID Tipo de Venta</th>
                    <th>Tipo de Venta</th>
                    <th>Valor de Venta</th>
                    <th>Pago del Cliente</th>
                    <th>Saldo actual</th>
                    <th>DB</th>
                    <th>Fecha Carga</th>
                    <th>Prioridad Pago</th>
                    <th>Reporte NVDR</th>
                    <th>Observacion</th>
                    <th>CF</th>
                    <th>Diferencia</th>
                </tr>
            </thead>
            <tbody>
            <%  
                for(VistaCompras v: VistasDAO.listarVehiculosPagados()){
                    

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
                    <td> <%= v.getDias() %> </td>
                    <td> <%= v.getTasa() %> </td>
                    <td> <%= v.getInteresReal() %> </td>
                    <td> $<%= formateador.format(v.getInteresVehiculo()) %> </td>
                    <td> $<%= formateador.format(impPago) %> </td>
                    <td>  </td>
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
                <%          //System.out.println("Iteraciones : "+ i);
                        }     //System.out.println("Total Repeticiones: "+ i);
                %>
                
            </tbody>  
        </table>
    </div>

    <%@include file="footer.jsp" %>
 
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>
    
</body>
</html>

<% } %>