<%-- 
    Document   : modificar-interes
    Created on : 14/10/2019, 12:52:35 PM
    Author     : Toshiba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    HttpSession sesion = request.getSession();
    String usuarioValidado = (String) sesion.getAttribute("usuario");
    
    if (usuarioValidado == null) {
        response.sendRedirect("login.jsp");
    }else{

        int id = Integer.parseInt(request.getParameter("id"));
        String nombreBanco = request.getParameter("nomBanco");
        double interes = Float.parseFloat(request.getParameter("interes"));
        int diasFinan = Integer.parseInt(request.getParameter("diasFinan"));
        int diasExt = Integer.parseInt(request.getParameter("diasExt"));
        double interesExt = Float.parseFloat(request.getParameter("interesExt"));
        int diasLibres = Integer.parseInt(request.getParameter("diasLibres"));

%>
<!DOCTYPE html>
<html lang="es">
    <head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
	<title>Plan Piso</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
        <link rel="stylesheet" href="./css/style.css">
    </head>
    <body>
        <div class="container" style="height: 80px; background-color: #F2F2F2;">
            <div class="row align-items-center justify-content-center h-100">
                <h2 class="text-center text-dark tipo-letra" >Formulario de Intereses</h2>
            </div>
        </div>
        <form class="container bg-light d-flex justify-content-between align-items-center tipo-letra" method="post" action="ControlBanco" style="border:2px solid #DFDFDF; flex-direction:column;">
            <input type="hidden" name="id" value="<%= id %>">
            <label for="nombreBanco">Nombre de banco: </label>
            <input class="form-control" type="text" id="nombreBanco" name="nombreBanco" placeholder="Banamex" value="<%= nombreBanco %>" required>
            <label for="diasFinanciamiento">Cantidad de dias de financiamiento: </label>
            <input class="form-control" type="text" id="diasFinanciamiento" name="diasFinanciamiento" value="<%= diasFinan %>" placeholder="180" required>
            <label for="interes">Interes del banco: </label>
            <input class="form-control" type="text" id="interes" name="interes"  value="<%= interes %>" placeholder="1.26%" required>
            <label for="diasExtra">Cantidad de dias extra: </label>
            <input class="form-control" type="text" id="diasExtra" name="diasExtra" value="<%= diasExt %>" placeholder="30" required>
            <label for="interesExtra">Interes por dia extra: </label>
            <input class="form-control" type="text" id="interesExtra" name="interesExtra" value="<%= interesExt %>" placeholder="25%" required>
            <label for="diasLibres">Cantidad de dias libre de interes: </label>
            <input class="form-control" type="text" id="diasLibres" name="diasLibres" value="<%= diasLibres %>">
            <div class="row w-100 align-items-center justify-content-center">
                <input type="submit" class="btn btn-danger" name="accion" value="Cancelar" style="margin: 10px;">
                <input type="submit" class="btn btn-primary" name="accion" value="Actualizar" style="margin: 10px;">
            </div>
        </form>
    </body>
</html>

<% } %>

