<%-- 
    Document   : registrar-financiera
    Created on : 14/10/2019, 12:15:40 PM
    Author     : Toshiba
--%>

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
	<link rel="stylesheet" href="./css/formulario.css">
    </head>
    <body>
        <div class="contenedor-titulo">
            <h2 class="titulo">Formulario de Financiera</h2>
        </div>
        <form class="form-financiera" action="ControlBanco" method="post">
            <label for="nombreBanco">Nombre de la financiera: </label>
            <input type="text" name="nombreBanco">
            <label for="interes">Cantidad de interes primeros dias: </label>
            <input type="text" name="interes">
            <label for="diasFinanciado">Cantidad de dias de financiamiento: </label>
            <input type="text" name="diasFinanciado">
            <label for="diasExtra">Cantidad de dias extra: </label>
            <input type="text" name="diasExtra">
            <label for="interesExtra">Cantidad de interes por dia extra: </label>
            <input type="text" name="interesExtra">
            <label for="diasLibres">Cantidad de dias libre de interes: </label>
            <input type="text" name="diasLibres">
            <div class="contenedor-botones">
                <input type="submit" class="boton-form boton-reg" name="accion" value="Cancelar">
                <input type="submit" class="boton-form boton-can" name="accion" value="Registrar">
            </div>
        </form>
    </body>
</html>
<% } %>