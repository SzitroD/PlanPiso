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
    <link rel="stylesheet" href="./css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>

</head>
<body>
 
    <%@include file="header.jsp" %>

    <div class="container-fluid bg-light" style="margin-top:75px; height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra" >Estado de financieras</h2>
        </div>
    </div>

    <!-- BOTON DE NUEVA FINANCIERA -->
    
    <div class="container bg-light text-right" style="height: 50px;">
        <a href="registrar-financiera.jsp" class="btn btn-success">Nueva Financiera</a>
    </div>
              
    <!-- TABLA DE FINANCIERAS -->
    
    <div class="container bg-light" style="padding-bottom: 10px;margin-bottom: 80px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
        <table class="table-responsive table table-striped table-bordered tipo-letra text-center" style="width: 100%;">
            <thead>
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
                        <!-- FORMULARIO PARA MODIFICAR FINANCIERAS -->
                        <form action="ControlBanco" method="post" class="form-mod-interes">
                            <input type="hidden" name="id" value="<%= b.getIdBancos() %>">
                            <input type="hidden" name="nomBanco" value="<%= b.getNombreBanco() %>">
                            <input type="hidden" name="diasFinan" value="<%= b.getDiasFinanciamiento() %>">
                            <input type="hidden" name="interes" value="<%= b.getInteres() %>">
                            <input type="hidden" name="diasExt" value="<%= b.getDiasExtra() %>">
                            <input type="hidden" name="interesExt" value="<%= b.getInteresExtra() %>">
                            <input type="hidden" name="diasLibres" value="<%= b.getDiasLibres() %>">
                            <input type="submit" class="btn btn-warning" name="accion" value="Modificar">
                        </form>
                    </td>
                    <td>
                        <!-- FORMULARIO PARA ACTIVAR Y DESACTIVAR FINANCIERAS -->
                        <form action="ControlBanco" method="post" class="form-mod-interes">
                            <input type="hidden" name="id" value="<%= b.getIdBancos() %>">
                            <% if(b.getStatus() == 1){ %>
                                <input class="btn btn-danger" type="submit" name="accion" value="Desactivar"> 
                            <% }else{ %>
                                <input class="btn btn-success" type="submit" name="accion" value="Activar">
                            <% } %>
                        </form>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
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