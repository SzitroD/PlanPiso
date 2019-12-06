<%-- 
    Document   : cuentas
    Created on : 14/10/2019, 09:08:22 AM
    Author     : Toshiba
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.logging.Logger"%>
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
        <link rel="stylesheet" href="./css/style.css">    
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
        <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>

</head>
<body>

    <%@include file="header.jsp" %>
    
    <div class="container-fluid bg-light" style="margin-top:75px; height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra" >Resumen de compras</h2>
        </div>
    </div>
    
    <div class="container col-12 bg-light text-center">
        <button id="btn-exportar-resumen" class="btn btn-success">
            EXCEL RESUMEN
        </button>  
        <button id="btn-exportar-vehiculos" class="btn btn-success">
            EXCEL VEHICULOS
        </button> 
    </div>
    
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Resumen de cada financiera</h3>
        </div>
    </div>
    <div id="contenedor-excel-resumen" class="container bg-light" style="margin-bottom:80px" >
        
        <%
            int total_unidad = 0;
            double total_cf = 0;
            double total_neto = 0;
            double total_interes = 0;
            double total_pago = 0;
            DecimalFormat formateador = new DecimalFormat("###,###,###.00");
            
               for(Bancos b: BancosDAO.listarBancos()){
                   if(b.getStatus()==1){
            %>
            
        <div class="container">
            <div class="row align-items-center justify-content-start h-100">
                <h4  class="text-dark tipo-letra">
                    <%= b.getNombreBanco() %>
                </h4>
            </div>
        </div>
            
        <div class="container" style=" padding-bottom: 10px;border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
             <table class="table table-responsive table-striped table-bordered tipo-letra text-center" style="width: 100%;">
                <thead>
                    <tr>
                        <th colspan="6"><%= b.getNombreBanco() %></th> 
                    </tr>
                    <tr>
                        <th>Prioridad</th>
                        <th>Total Unidades</th>
                        <th>Total Importe Neto</th>
                        <th>Total Interes</th>
                        <th>Total Pago</th>
                        <th>Total CF</th>
                    </tr>
                </thead>
                <tbody>
            <% 
                for(VistaCompras v : VistasDAO.listas_conciliacion()){
                        
                    if((b.getIdBancos() == v.getIdBanco())){ 
                        if(v.getTotalVehiculos() > 0){
                            total_unidad += v.getTotalVehiculos();
                            total_neto += v.getTotalImporteNeto();   
                            total_interes += v.getTotalInteres();
                            total_cf += v.getTotalCF();
                            total_pago += v.getTotalPago();
            %>
                    <tr>
                        <td> <%= v.getPrioridadPago() %> </td>
                        <td> <%= v.getTotalVehiculos() %> </td>
                        <td> $<%= formateador.format(v.getTotalImporteNeto()) %> </td>
                        <td> $<%= formateador.format(v.getTotalInteres()) %> </td>
                        <td> $<%= formateador.format(v.getTotalPago()) %> </td>
                        <td> $<%= formateador.format(v.getTotalCF())  %> </td>
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
                        <td> $<%= formateador.format(total_pago) %> </td>
                        <td> $<%= formateador.format(total_cf) %> </td>
                    </tr>
                    </tbody>
                </table>
                        <%
                        total_unidad = 0; 
                        total_cf = 0;
                        total_neto = 0;
                        total_interes = 0;
                        total_pago= 0;
                %>      
            </div>
            <%      }
                } 
            %>
        		
    </div>
     <div id="contenedor-excel-vehiculos" class="container bg-light" style="display: none">
        
        <%
            int total_u = 0;
            double total_i = 0;
            double total_p = 0;
            double total_c = 0;
            double total_n = 0;
               
            for(Bancos b: BancosDAO.listarBancos()){
                if(b.getStatus()==1){
        %>
            
        <div class="container">
            <div class="row align-items-center justify-content-start h-100">
                <h4  class="text-dark tipo-letra">
                    <%= b.getNombreBanco() %>
                </h4>
            </div>
        </div>
            
        <div class="container" style=" border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
             <table class="table-responsive table table-striped table-bordered tipo-letra text-center" style="width: 100%; ">
                <thead>
                    <tr>
                        <th colspan="7"><%= b.getNombreBanco() %></th> 
                    </tr>
                    <tr>
                        <th>Vin vehiculos</th>
                        <th>Prioridad</th>
                        <th>Tipo de Venta</th>
                        <th>Importe a Pagar NETO</th>
                        <th>Interes a Pagar</th>
                        <th>Importe Total a Pagar</th>
                        <th>CF</th>
                    </tr>
                </thead>
                <tbody>
            <% 
                for(VistaCompras v : VistasDAO.listarVehiculosExcel()){
                        
                    if(b.getIdBancos() == v.getIdBanco()){ 
                        total_u ++;
                        total_n += v.getImporteNeto();
                        total_i += v.getInteres();
                        total_p += v.getTotalPago();
                        total_c += v.getCf();
                %>
                    <tr>
                        <td> <%= v.getVin() %> </td>
                        <td> <%= v.getPrioridadPago() %> </td>
                        <td> <%= v.getTipoVenta() %> </td>
                        <td> $<%= formateador.format(v.getImporteNeto()) %> </td>
                        <td> $<%= formateador.format(v.getInteres()) %> </td>
                        <td> $<%= formateador.format(v.getTotalPago()) %> </td>
                        <td> $<%= formateador.format(v.getCf()) %> </td>
                    </tr>
                <%      }  
                    }
                    
                %>
                    <tr>
                        <td>Vehiculos Totales</td>
                        <td> <%= total_u %> </td>
                        <td class="td-resultado"> Total de <%= b.getNombreBanco() %></td>
                        <td> $<%= formateador.format(total_n) %> </td>
                        <td> $<%= formateador.format(total_i) %> </td>
                        <td> $<%= formateador.format(total_p) %> </td>
                        <td> $<%= formateador.format(total_c) %> </td>
                    </tr>
                    </tbody>
                </table>
                   <%
                        total_u = 0; 
                        total_c = 0;
                        total_n = 0;
                        total_i = 0;
                        total_p= 0;
                %>      
            </div>
            <% 
                    }    
                } 
            %>
        		
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