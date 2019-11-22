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
	<link rel="stylesheet" href="./css/formulario.css">
    </head>
    <body>
        <div class="contenedor-titulo">
            <h2 class="titulo">Formulario de Intereses</h2>
        </div>
        <form class="form-financiera" method="post" action="ControlBanco">
            <input type="hidden" name="id" value="<%= id %>">
            <label for="nombreBanco">Nombre de banco: </label>
            <input type="text" name="nombreBanco" placeholder="Banamex" value="<%= nombreBanco %>" required>
            <label for="diasFinanciamiento">Cantidad de dias de financiamiento: </label>
            <input type="text" name="diasFinanciamiento" value="<%= diasFinan %>" placeholder="180" required>
            <label for="interes">Interes del banco: </label>
            <input type="text" name="interes"  value="<%= interes %>" placeholder="1.26%" required>
            <label for="diasExtra">Cantidad de dias extra: </label>
            <input type="text" name="diasExtra" value="<%= diasExt %>" placeholder="30" required>
            <label for="interesExtra">Interes por dia extra: </label>
            <input type="text" name="interesExtra" value="<%= interesExt %>" placeholder="25%" required>
            <label for="diasLibres">Cantidad de dias libre de interes: </label>
            <input type="text" name="diasLibres" value="<%= diasLibres %>">
            <div class="contenedor-botones">
                <input type="submit" class="boton-form boton-reg" name="accion" value="Cancelar">
                <input type="submit" class="boton-form boton-can" name="accion" value="Actualizar">
            </div>
        </form>
    </body>
</html>

<% } %>

