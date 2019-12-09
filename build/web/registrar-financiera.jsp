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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
        <link rel="stylesheet" href="./css/style.css">
    </head>
    <body>
         <div class="container" style="height: 80px; background-color: #F2F2F2;">
            <div class="row align-items-center justify-content-center h-100">
                <h2 class="text-center text-dark tipo-letra" >Formulario de Financiera</h2>
            </div>
        </div>
        
        <!-- FORMULARIO PARA REGISTRAR UNA NUEVA FINANCIERA -->
        
        <form class="container bg-light d-flex justify-content-between align-items-center tipo-letra" action="ControlBanco" method="post"
              style="border:2px solid #DFDFDF; flex-direction:column;">
            
            <label for="nombreBanco">Nombre de la financiera: </label>
            <input class="form-control" id="nombreBanco" type="text" name="nombreBanco">
            <label for="interes">Cantidad de interes primeros dias: </label>
            <input class="form-control" id="interes" type="text" name="interes">
            <label for="diasFinanciado">Cantidad de dias de financiamiento: </label>
            <input class="form-control" id="diasFinanciamiento" type="text" name="diasFinanciado">
            <label for="diasExtra">Cantidad de dias extra: </label>
            <input class="form-control" id="diasExtra" type="text" name="diasExtra">
            <label for="interesExtra">Cantidad de interes por dia extra: </label>
            <input class="form-control" id="interesExtra" type="text" name="interesExtra">
            <label for="diasLibres">Cantidad de dias libre de interes: </label>
            <input class="form-control" id="diasLibres" type="text" name="diasLibres">
            <div class="row w-100 align-items-center justify-content-center">
                <input type="submit" class="btn btn-danger" style="margin: 10px;" name="accion" value="Cancelar">
                <input type="submit" class="btn btn-primary" style="margin: 10px;" name="accion" value="Registrar">
            </div>
        </form>
    </body>
</html>
<% } %>