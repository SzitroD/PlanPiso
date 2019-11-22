<%-- 
    Document   : financiera
    Created on : 14/10/2019, 09:08:37 AM
    Author     : Toshiba
--%>

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
 	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>Plan Piso</title>

	<link rel="stylesheet" href="./css/financiera.css">
	<link rel="stylesheet" href="./css/header.css">
	<link rel="stylesheet" href="./css/footer.css">

</head>
<body>
 
    <%@include file="header.jsp" %>

    <div class="contenedor-titulo primer-titulo">
        <h2 class="titulo">Estado de financieras</h2>
    </div>

    <div class="contenedor-btn">
        <a href="registrar-financiera.jsp" class="btn-registrar">Nueva Financiera</a>
    </div>
                
    <div class="contendor-tabla ultimo-contenido">
        <table class="table">
            <thead class="thead">
                <tr>
                    <th>Financiera</th>
                    <th>Dias Libres de Financiamiento</th>
                    <th>Dias de Financiamiento</th>
                    <th class="interes">Interes Actual %</th>
                    <th>Dias Refinanciamiento</th>
                    <th class="interes">Interes por dia de Refinanciamiento %</th>
                    <th colspan="2">Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% for(Bancos b : BancosDAO.listarBancos()){ %>
                <tr>
                    <td> <%= b.getNombreBanco() %> </td>
                    <td> <%= b.getDiasLibres() %> </td>
                    <td> <%= b.getDiasFinanciamiento() %> </td>
                    <td class="interes"> <%= b.getInteres() %> </td>
                    <td> <%= b.getDiasExtra() %> </td>
                    <td class="interes"> <%= b.getInteresExtra() %> </td>
                    <td>
                        <form action="ControlBanco" method="post" class="form-mod-interes">
                            <input type="hidden" name="id" value="<%= b.getIdBancos() %>">
                            <input type="hidden" name="nomBanco" value="<%= b.getNombreBanco() %>">
                            <input type="hidden" name="diasFinan" value="<%= b.getDiasFinanciamiento() %>">
                            <input type="hidden" name="interes" value="<%= b.getInteres() %>">
                            <input type="hidden" name="diasExt" value="<%= b.getDiasExtra() %>">
                            <input type="hidden" name="interesExt" value="<%= b.getInteresExtra() %>">
                            <input type="hidden" name="diasLibres" value="<%= b.getDiasLibres() %>">
                            <input type="submit" class="btn-actualizar" name="accion" value="Modificar">
                        </form>
                    </td>
                    <td>
                        <form action="ControlBanco" method="post" class="form-mod-interes">
                            <input type="hidden" name="id" value="<%= b.getIdBancos() %>">
                            <% if(b.getStatus() == 1){ %>
                                <input class="btn-mod btn-desactivar" type="submit" name="accion" value="Desactivar"> 
                            <% }else{ %>
                                <input class="btn-mod btn-activar" type="submit" name="accion" value="Activar">
                            <% } %>
                        </form>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
            
    <%@include file="footer.jsp" %>

    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>
</body>
</html>

<% } %>