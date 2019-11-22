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

	<link rel="stylesheet" href="./css/inicio.css">
	<link rel="stylesheet" href="./css/header.css">
	<link rel="stylesheet" href="./css/footer.css">
</head>
<body>
 
    <%@include file="header.jsp" %>

    <div class="contenedor-titulo primer-titulo">
        <h2 class="titulo">Busqueda de vehiculo</h2>
    </div>
    
    <div class="contenedor-busqueda">
        <form action="ControlInicio" method="get" class="form-busqueda">
            <input type="search" name="busqueda" placeholder="Vin del Vehiculo" class="search">
            <input type="submit" name="accion" value="Buscar" class="btn-enviar">
        </form>
    </div>
    
    <div class="contendor-tabla">
        <table class="table">
            <thead class="thead">
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
                    String busqueda = request.getParameter("busqueda");
                    DecimalFormat formateador = new DecimalFormat("###,###,###.00");
                    for(VistaCompras v: VistasDAO.buscarVehiculo(busqueda)){
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
        
    <div class="contenedor-titulo">
        <h2 class="titulo">Vehiculos Refinanciados</h2>
    </div>
            
    <div class="contendor-tabla">
        <table class="table">
            <thead class="thead">
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
                        if(v.getStatusBanco()==1){
                           
                            if(v.getStatusFinanciamiento().equals("REFINANCIADA")){
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
            
    <div class="contenedor-titulo">
        <h2 class="titulo">Estado de vehiculos</h2>
    </div>

    <div class="contenedor-titulo titulo-secundario">
            <h3 class="titulo">Vencidos</h3>
    </div>
	
    <div class="contendor-tabla">
        <table class="table">
            <thead class="thead">
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
                
        <div class="contenedor-titulo titulo-secundario">
            <h3 class="titulo">Proximos a Vencer</h3>
	</div>

	<div class="contendor-tabla  ultimo-contenido">
            <table class="table">
                <thead class="thead">
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
                                diferencia = v.getDiasRealesFinanciamiento() - v.getDias();
                                
                                if((diferencia <= 20 && diferencia > 0) && (v.getDias() >=1)){
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
      
    <%@include file="footer.jsp" %>
 
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>
    
</body>
</html>

<% } %>