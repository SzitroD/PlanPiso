<%-- 
    Document   : vehiculos
    Created on : 6/11/2019, 01:56:12 PM
    Author     : Toshiba
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.FinanciarDAO"%>
<%@page import="modelo.Financiar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="dao.VistasDAO"%>
<%@page import="modelo.VistaCompras"%>
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

    <link rel="stylesheet" href="./css/vehiculos.css">
    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/footer.css">

</head>
<body>

    <%@include file="header.jsp" %>

    <div class="contenedor-titulo primer-titulo">
        <h2 class="titulo">Financiar vehiculos</h2>
    </div>

    <div class="contenedor-titulo titulo-secundario">
        <h3 class="titulo">Cargar archivo de conciliaciones</h3>
    </div>

    <div class="contenedor-btn-csv">
        <form class="form-csv" method="post" action="ControlVehiculos" enctype="multipart/form-data">
            <label for="archivo" class="titulo-input">Seleccione un archivo en formato CSV para la carga de conciliaciones</label>
            <input type="file" name="archivo">
            <input class="btn-actualizar" type="submit" name="accion" value="Subir Conciliacion">
        </form>
    </div>
    
    <div class="contenedor-titulo titulo-secundario">
            <h3 class="titulo">Buscar vehiculo</h3>
    </div>
    
    <div class="contenedor-busqueda">
        <form action="ControlVehiculos" method="get" class="form-busqueda">
            <input type="search" name="busqueda" class="search">
            <input type="submit" name="accion" value="Buscar" class="btn-enviar">
        </form>
    </div>

        <div class="contendor-tabla ultimo-contenido">
        
            <table class="table">
                <thead class="thead">
                    <tr>
                        <th>Fecha Compra</th>
                        <th>Serie</th>
                        <th>Vin</th>
                        <th>Marca</th>
                        <th>Valor Factura</th>
                        <th>Importe a pagar NETO</th> 
                        <th>Opciones de financiamiento</th> 
                    </tr>
                </thead>
                <tbody class="tbody">
                    <% 
                        String busqueda = request.getParameter("busqueda");
                        
                        for(Vehiculo v : VehiculosDAO.buscarVehiculo(busqueda)){
                            
                            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                            String fechaString = formato.format(new Date());
                            //System.out.println("fecha String: "+fechaString);
                                
                    %>
                    <tr>
                        <td> <%= v.getFechaCompra() %> </td>
                        <td> <%= v.getSerie() %> </td>
                        <td> <%= v.getVin() %> </td>
                        <td> <%= v.getMarca() %> </td>
                        <td> <%= v.getValorFactura() %> </td>
                        <td> <%= v.getImporteNeto() %> </td>
                        <td> 
                            <form class="form-financiar" action="ControlVehiculos" method="post" enctype="multipart/form-data">
                                <label for="nomFinanciera">Financiera </label>
                                <select name="nomFinanciera">
                                    <option value="0"></option>
                                    <% for(Bancos b : BancosDAO.listarBancos()){ 
                                        if(b.getStatus()==1){%>
                                    <option value="<%= b.getIdBancos() %>" > <%= b.getNombreBanco() %> </option>
                                    <% }
                                    } %>
                                </select>
                                <input type="hidden" name="vin" value="<%= v.getVin() %>">   
                                <input type="hidden" name="fechaActual" value="<%= fechaString %>">
                                <input type="hidden" name="fechaCompra" value="<%= v.getFechaCompra() %>">
                                <input type="hidden" name="status" value="FINANCIADA" >
                              <!--  <label for="diasReales">Dias financiamiento </label> -->
                                <input class="opciones-form" type="hidden" name="diasReales" value="0">
                              <!--   <label for="interesReal"></label> -->
                                <input class="opciones-form" type="hidden" name="interesReal" value="0">
                                <input class="btn-actualizar" type="submit" name="accion" value="Financiar">
                            </form>
                        </td>
                    </tr>
                    <%          
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
