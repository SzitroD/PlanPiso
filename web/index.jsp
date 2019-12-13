<%-- 
    Document   : index
    Created on : 7/10/2019, 08:53:34 AM
    Author     : Toshiba
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="dao.VistasDAO"%>
<%@page import="modelo.VistaCompras"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.BancosDAO"%>
<%@page import="modelo.Bancos"%>
<%@page import="dao.VehiculosDAO"%>
<%@page import="modelo.Vehiculo"%>
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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Plan Piso</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="./css/style.css">
 
</head>
<body>
 
    <%@include file="header.jsp" %>
    
    <!-- TABLA DE BUSQUEDA DE VEHICULOS -->
    
    <div class="container-fluid bg-light" style="margin-top:75px; height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra" >Busqueda de vehiculo</h2>
        </div>
    </div>
    
    <div class="container bg-light" style="padding-top: 20px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
        <table class="table table-striped table-bordered tipo-letra text-center" style="width: 100%;" id="table-boostrap">
              <thead>
                  <tr>
                      <th>Vin</th>
                      <th>Status</th>
                      <th>Prioridad</th>
                      <th>Ubicacion</th>
                      <th>Nombre de proveedor</th>
                      <th>Fecha de Financiamiento</th>                        
                      <th>Financiera</th>
                      <th>Dias de credito</th>
                      <th>Dias transcurridos</th>
                      <th>Importe a pagar</th>
                  </tr>
              </thead>
              <tbody>
                <% 

                    //Formato en que se presentan las cantidades 
                    DecimalFormat formateador = new DecimalFormat("###,###,###.00");
                    for(VistaCompras v: VistasDAO.listarVehiculosFiltro()){
                        if(v.getStatusBanco()==1){
                %>
                <tr>
                    <td><%= v.getVin()%></td>
                    <td><%= v.getStatusFinanciamiento() %></td>
                    <td><%= v.getPrioridadPago() %></td>
                    <td><%= v.getUbicacion() %></td>
                    <td><%= v.getCarteraFinanciera()%></td>
                    <td><%= v.getFechaFinanciamiento() %></td>
                    <td><%= v.getNombreBanco() %></td>
                    <td><%= v.getDiasRealesFinanciamiento()%></td>
                    <td><%= v.getDias() %></td>
                    <td> $<%= formateador.format(v.getTotalPago()) %></td>
                </tr>
                <% }
                    } %>
            </tbody>
        </table>
    </div>
            
    <!-- TABLA DE REFINANCIEMIENTO -->
        
    <div class="container-fluid bg-light" style="height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra">Vehiculos Refinanciados</h2>
        </div>
    </div>
            
    <div class="container bg-light" style="padding-bottom: 10px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
        <table class="table  table-striped table-bordered tipo-letra text-center table-boostrap-2" >
            <thead>
                <tr>
                    <th>Vin</th>
                    <th>Marca</th>
                    <th>Fecha de Refinanciamiento</th>
                    <th class="column">Financiado con</th>
                    <th class="column">Dias de Refinanciamiento</th>
                    <th>Dias transcurridos</th>
                    <th class="column">Importe a Pagar</th>
                    <th class="column">Proxima Fecha de Pago</th>
                </tr>
            </thead>
            <tbody>
                <%  SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                                    
                    for(VistaCompras v: VistasDAO.listarVehiculosFiltro()){ 
                       
                        /*Validar que la financiera este activa y su status sea REFINANCIADA*/    
                        if(v.getStatusBanco()==1){
                           
                            if(v.getStatusFinanciamiento().equals("REFINANCIADA")){
                                
                                /*Calcular proxima fecha de pago dependiendo de los dias de 
                                refinanciamiento*/
                                
                                String fechaString = v.getFechaFinanciamiento();
                                Date fechaDate = formato.parse(fechaString);
                                Date proximaFechaDate = VehiculosDAO.sumaFecha(fechaDate, v.getDiasExtra());
                                String proximaFechaString = formato.format(proximaFechaDate);
                                
                %>
                <tr>
                    <td><%= v.getVin() %></td>
                    <td><%= v.getMarca() %></td>
                    <td><%= v.getFechaFinanciamiento() %></td>
                    <td class="column"><%= v.getNombreBanco() %></td>
                    <td class="column"><%= v.getDiasExtra()%></td>
                    <td><%= v.getDias() %></td>
                    <td class="column"> $<%= formateador.format(v.getTotalPago()) %></td>
                    <td> <%= proximaFechaString %> </td>
                </tr>
                <%          }
                        }
                    } 
                
                %>
            </tbody>
        </table>
    </div>
            
    <div class="container-fluid bg-light" style="height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra">Estado de vehiculos</h2>
        </div>
    </div>
	
    <!-- TABLA DE VEHICULOS VENCIDOS -->
    
    <div class="container">
        <div class="row align-items-center justify-content-center h-100" style="background-color: #F2F2F2;">
            <h3 class="text-left text-secondary tipo-letra">Vencidos</h3>
        </div>
    </div>
        
    <div class="container bg-light" style="padding-bottom: 10px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
        <table class="table table-striped table-boostrap-2 table-bordered tipo-letra text-center">
            <thead>
                <tr>
                    <th>Vin</th>
                    <th>Marca</th>
                    <th>Fecha de Financiamiento</th>
                    <th class="column">Financiado con</th>
                    <th class="column">Dias de Financiamiento</th>
                    <th>Dias transcurridos</th>
                    <th class="column">Importe a pagar</th>
                </tr>
            </thead>
            <tbody>
                <%  for(VistaCompras v: VistasDAO.listarVehiculosFiltro()){
                        if(v.getStatusBanco()==1){
                            if(v.getDias() >= v.getDiasRealesFinanciamiento() ){
 
                %>
                <tr>
                        <td><%= v.getVin() %></td>
                        <td><%= v.getMarca() %></td>
                        <td><%= v.getFechaFinanciamiento() %></td>
                        <td class="column"><%= v.getNombreBanco() %></td>
                        <td class="column"><%= v.getDiasRealesFinanciamiento() %></td>
                        <td><%= v.getDias() %></td>
                        <td class="column"> $<%= formateador.format(v.getTotalPago()) %></td>
                </tr>
                <%          }   
                        }
                    }
                %>
                </tbody>
            </table>
        </div>
            
        <!-- TABLA DE VEHICULOS PROXIMOS A VENCER -->
                    
        <div class="container">
            <div class="row align-items-center justify-content-center h-100" style="background-color: #F2F2F2;">
                <h3 class="text-left text-secondary tipo-letra">Proximos a Vencer</h3>
            </div>
        </div>
            
	<div class="container bg-light" style="padding-bottom: 10px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
            <table class="table table-boostrap-2 table-striped table-bordered tipo-letra text-center">
                <thead>
                    <tr>
                        <th>Vin</th>
                        <th>Marca</th>
                        <th>Fecha de Financiamiento</th>
                        <th class="column">Financiado con</th>
                        <th class="column">Dias de Financiamiento</th>
                        <th>Dias transcurridos</th>
                        <th class="column">Importe a pagar</th>
                    </tr>
                </thead>
                <tbody>
                    <%  
                        int diferencia = 0;
                        for(VistaCompras v: VistasDAO.listarVehiculosFiltro()){ 
                            if(v.getStatusBanco()==1){
                            /*Validacion de que si un vehiculo se encuentrar a 20 o menos dias de 
                            cumplir su fecha de financiamineto*/
                                diferencia = v.getDiasRealesFinanciamiento() - v.getDias();
                                
                                if((diferencia <= 10 && diferencia > 0) && (v.getDias() >=1)){
                    %>
                    <tr>
                        <td><%= v.getVin() %></td>
                        <td><%= v.getMarca() %></td>
                        <td><%= v.getFechaFinanciamiento() %></td>
                        <td class="column"><%= v.getNombreBanco() %></td>
                        <td class="column"><%= v.getDiasRealesFinanciamiento() %></td>
                        <td><%= v.getDias() %></td>
                        <td class="column"> $<%= formateador.format(v.getTotalPago()) %></td>
                    </tr>
                    <%          }
                            } 
                        }
                    %>
                </tbody>
            </table>
        </div>
      
        <!-- TABLA DE VEHICULOS CON MAS DE 365 DIAS DE FINANCIAMIENTO -->
                    
       <div class="container-fluid bg-light" style="height: 80px;">
            <div class="row align-items-center justify-content-center h-100">
                <h2 class="text-center text-dark tipo-letra">Vehiculos sin seguro</h2>
            </div>
        </div>
            
	<div class="container bg-light" style="margin-bottom: 80px; padding-bottom: 10px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
            <table class="table table-boostrap-2 table-striped table-bordered tipo-letra text-center">
                <thead>
                    <tr>
                        <th>Vin</th>
                        <th>Fecha Compra</th>
                        <th>Dias transcurridos desde Fecha Compra</th>
                        <th>Marca</th>
                        <th>Fecha de Financiamiento</th>
                        <th class="column">Financiado con</th>
                        <th class="column">Dias de Financiamiento</th>
                        <th>Dias transcurridos</th>
                        <th class="column">Importe a pagar</th>
                    </tr>
                </thead>
                <tbody>
                    <%  
                        int year = 365;
                        for(VistaCompras v: VistasDAO.listarVehiculosSinSeguro()){ 
                            if(v.getStatusBanco()==1){
                            /*Validacion de que si un vehiculo tiene 365 dias o mas desde su fecha
                                de financiamiento, esto indica que el vehiculo se quedo sin seguro*/
                                if(v.getDiasSeguro() >= year){
                    %>
                    <tr>
                        <td><%= v.getVin() %></td>
                        <td> <%= v.getFechaCompra() %> </td>
                        <td><%= v.getDiasSeguro() %></td>
                        <td><%= v.getMarca() %></td>
                        <td><%= v.getFechaFinanciamiento() %></td>
                        <td class="column"><%= v.getNombreBanco() %></td>
                        <td class="column"><%= v.getDiasRealesFinanciamiento() %></td>
                        <td><%= v.getDias() %></td>
                        <td class="column"> $<%= formateador.format(v.getTotalPago()) %></td>
                    </tr>
                    <%          }
                            } 
                        }
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