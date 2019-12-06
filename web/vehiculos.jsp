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

    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
    <link rel="stylesheet" href="./css/style.css">
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>

</head>
<body>

    <%@include file="header.jsp" %>
    
    <div class="container-fluid bg-light" style="margin-top:75px; height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra">Nuevos Financiamientos</h2>
        </div>
    </div>
    
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Cargar archivo de conciliacion</h3>
        </div>
    </div>

    <%
        List<String> listaFinanciera = new ArrayList();
        String[] lista = null;
        int i = 0;

        for(Bancos b: BancosDAO.listarBancos()){
            if(b.getStatus()==1){
                 lista = b.getNombreBanco().split(" ");
                 if(listaFinanciera.size()==0){
                     listaFinanciera.add(lista[0].trim());
                 }else if(listaFinanciera.size() > 0 && !(listaFinanciera.get(i).equals(lista[0].trim()))){
                     listaFinanciera.add(lista[0].trim()); 
                     i++;
                 }
            }
        }
    
    %>
    
    <div class="container bg-light">
        <form class="row justify-content-center" method="post" action="ControlVehiculos" enctype="multipart/form-data" style="padding-bottom: 20px;">
            <label for="financiera" class="tipo-letra exampleFormControlFile1">Elige a que financiera pertenece la conciliacion</label>
            <select id="financiera" name="financiera" class="form-control">
                <% for(int j = 0; j < listaFinanciera.size(); j++){ %>
                <option value="<%= listaFinanciera.get(j) %>"> <%= listaFinanciera.get(j) %> </option>
                <% } %>
            </select>
            <label for="archivo" class="tipo-letra">Seleccione un archivo en formato CSV para la carga de conciliaciones</label>
            <input type="file" class="form-control-file" id="archivo" name="archivo">
            <input type="hidden" name="tipo" value="conciliacion">
            <input class="btn btn-success" type="submit" name="accion" value="Subir Conciliacion">
        </form>
    </div>
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Financiar vehiculos</h3>
        </div>
    </div>
   
    <div class="container bg-light d-flex justify-content-end align-items-center" style="height: 100px;">
        <form action="ControlVehiculos" method="get" class="form-inline w-80">
            <input type="search" name="busqueda" class="form-control mr-sm-2">
            <input type="submit" name="accion" value="Buscar" class="btn btn-outline-black my-2 my-sm-0">
        </form>
    </div>
            
            
    <div class="container bg-light" style="padding-bottom: 10px; margin-bottom: 80px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF;">
        <table class="table table-boostrap-3 table-striped table-bordered tipo-letra text-center" style="width: 100%;">
                <thead>
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
                <tbody>
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
                                <label for="nomFinanciera" class="tipo-letra">Financiera </label>
                                <select id="nomFinanciera" name="nomFinanciera" class="control-form">
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
                                <input class="btn btn-warning" type="submit" name="accion" value="Financiar">
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
 
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>
    
</body>
</html>
<% } %>
