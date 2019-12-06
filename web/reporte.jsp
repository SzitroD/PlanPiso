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
    <link rel="stylesheet" href="./css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>

</head>
<body>

    <%@include file="header.jsp" %>
	
    <div class="container-fluid bg-light" style="margin-top:75px; height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra" >Archivos</h2>
        </div>
    </div>
    
    <div class="container bg-light" style="margin-bottom:80px" >
        
        <form class="row justify-content-center" action="ControlReportes" method="post" enctype="multipart/form-data" style="height: 100px;">
            <label class="tipo-letra" for="archivo">Seleccionar Archivo</label>
            <input type="file" name="reporte"  class="form-control-file" id="archivo" lang="es">
            <input class="btn btn-success" type="submit" value="Subir">
        </form> 
        
       <div class=" container" style="padding-bottom: 10px;  border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
            <table class="table table-striped table-bordered tipo-letra text-center" style="width: 100%;" id="table-boostrap">
                <thead >
                    <tr>
                        <th>Nombre del Archivo</th>
                        <th>Fecha de Subida</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody >
                    <%  
                        for(Reportes r : ReportesDAO.listarReporte()){ %>
                    <tr>        
                        <td> <%= r.getNombreReporte() %> </td>
                        <td> <%= r.getFecha() %> </td>
                        <td> 
                            <a class="btn btn-primary" href="./archivos/<%= r.getRuta() %>"  download="<%= r.getRuta() %>">Descargar</a>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

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