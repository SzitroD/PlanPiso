<%-- 
    Document   : conciliacion
    Created on : 14/11/2019, 10:22:38 AM
    Author     : Toshiba
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dao.VehiculosDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="dao.VistasDAO"%>
<%@page import="modelo.VistaCompras"%>
<%@page import="dao.BancosDAO"%>
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
        
	<link rel="stylesheet" href="./css/conciliacion.css">
	<link rel="stylesheet" href="./css/header.css">
	<link rel="stylesheet" href="./css/footer.css">

</head>
<body>

    <%@include file="header.jsp" %>
    
    <div class="contenedor-titulo primer-titulo">
        <h2 class="titulo">Conciliaciones de compras</h2>
    </div>
    
    <div class="contenedor-btn-reporte">
        <button id="btn-exportar-conciliacion" class="btn">
            EXCEL CONCILIACION
        </button> 
    </div>
    <div class="contenedor-titulo">
        <h2 class="titulo">Conciliacion de cada financiera</h2>
    </div>

    <div id="contenedor-excel-conciliacion" class="contenedor-general ultimo-contenido">
        
        <%
            
            int total_unidad = 0;
            double total_cf = 0;
            double total_neto = 0;
            double total_interes = 0;
            DecimalFormat formateador = new DecimalFormat("###,###,###.00");
               for(Bancos b: BancosDAO.listarBancos()){
                   if(b.getStatus()==1){
            %>
            
        <div class="contenedor-titulo titulo-secundario">
            <h4 class="titulo">
                <%= b.getNombreBanco() %>
            </h4>
        </div>
            
        <div class="contendor-tabla">
            <table class="table">
                <thead class="thead">
                    <tr>
                        <th colspan="5"><%= b.getNombreBanco() %></th> 
                    </tr>
                    <tr>
                        <th>Prioridad</th>
                        <th>Total Unidades</th>
                        <th>Total Importe Neto</th>
                        <th>Total Interes</th>
                        <th>Total CF</th>
                    </tr>
                </thead>
                <tbody class="tbody">
            <% 
                for(VistaCompras v : VistasDAO.listas_conciliacion()){
                        
                    if((b.getIdBancos() == v.getIdBanco())){ 
                        if(v.getTotalVehiculos() > 0){
                            total_unidad += v.getTotalVehiculos();
                            total_neto += v.getTotalImporteNeto();   
                            total_interes += v.getTotalInteres();
                            total_cf += v.getTotalCF();
            %>
                    <tr>
                        <td> <%= v.getPrioridadPago() %> </td>
                        <td> <%= v.getTotalVehiculos() %> </td>
                        <td> $<%= formateador.format(v.getTotalImporteNeto()) %> </td>
                        <td> $<%= formateador.format(v.getTotalInteres()) %> </td>
                        <td> $<%= formateador.format(v.getTotalCF()) %> </td>
                    </tr>
                <%      
                            }
                    }
                }
               
                %>
                    <tr>
                        <td class="td-resultado"> Total de <%= b.getNombreBanco() %></td>
                        <td> <%= total_unidad %> </td>
                        <td> $<%= formateador.format(total_neto) %> </td>
                        <td> $<%= formateador.format(total_interes) %> </td>
                        <td> $<%= formateador.format(total_cf) %> </td>
                    </tr>
                    </tbody>
                </table>
                        <%
                        total_unidad = 0; 
                        total_cf = 0;
                        total_neto = 0;
                        total_interes = 0;
                %>      
            </div>
            <%      }
                } 
            %>
        		
    </div>
    
    <%@include file="footer.jsp" %>
 
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="./js/jquery/jquery.table2excel.min.js"></script>
    <script type="text/javascript" src= "./js/main.js"></script>
 
</body>
</html>
<% } %>