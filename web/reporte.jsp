<%-- 
    Document   : reporte
    Created on : 14/10/2019, 09:08:47 AM
    Author     : Toshiba
--%>

<%@page import="dao.ReportesDAO"%>
<%@page import="modelo.Reportes" %>
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

    <link rel="stylesheet" href="./css/reportes.css">
    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/footer.css">

</head>
<body>

    <%@include file="header.jsp" %>
	
    <div class="contenedor-titulo primer-titulo">
        <h2 class="titulo">Archivos</h2>
    </div>
    
    <div class="contenedor-general ultimo-contenido">
        <div class="contenedor-busqueda">
            <form class="form-reporte" action="ControlReportes" method="post" enctype="multipart/form-data">
                <label for="reporte" class="titulo-input">Subir un archivo </label>
                <input type="file" name="reporte">
                <input class="btn-subir-reporte" type="submit" value="Subir">
            </form> 
            <form action="ControlReportes" method="get" class="form-busqueda">
                <input type="search" name="busqueda" class="search">
                <input type="submit" name="accion" value="Buscar" class="btn-enviar">
            </form>
        </div>

        <div class="contenedor-vista">
            <table class="table">
                <thead class="thead">
                    <tr>
                        <th>Nombre del Archivo</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody class="tbody">
                    <%  String busqueda = request.getParameter("busqueda");
                        for(Reportes r : ReportesDAO.buscarReporte(busqueda)){ %>
                    <tr>        
                        <td> <%= r.getNombreReporte() %> </td>
                        <td> 
                            <a class="btn-descargar" href="./reportes/<%= r.getRuta() %>"  download="<%= r.getRuta() %>">Descargar</a>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

    </div>
    
        
    <%@include file="footer.jsp" %>
 
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>

</body>
</html>
<% } %>