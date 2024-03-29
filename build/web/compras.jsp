<%-- 
    Document   : compras
    Created on : 14/10/2019, 09:07:29 AM
    Author     : Toshiba
--%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="dao.VistasDAO"%>
<%@page import="modelo.VistaCompras"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dao.VehiculosDAO"%>
<%@page import="modelo.Vehiculo"%>
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

    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
    <link rel="stylesheet" href="./css/style.css">
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
</head>
<body>

    <%@include file="header.jsp" %>

    <div class="container-fluid bg-light" style="margin-top:75px; height: 80px;">
        <div class="row align-items-center justify-content-center h-100">
            <h2 class="text-center text-dark tipo-letra" >Compra de Vehiculos</h2>
        </div>
    </div>
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Busqueda rapida</h3>
        </div>
    </div>
    
    <!-- FORMULARIO PARA FILTRADO DE VEHICULOS PIR VIN -->
    
    <div class="container bg-light d-flex justify-content-end align-items-center" style="height: 100px;">
        <form action="ControlCompras" method="post" class="form-inline w-80" >
            <input type="search" name="busqueda" class="form-control mr-sm-2">
            <input type="submit" name="accion" value="Buscar" class="btn btn-outline-black my-2 my-sm-0">
        </form>
    </div>
    
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Busqueda por financiera</h3>
        </div>
    </div>
    
    <!-- FORMULARIO PARA FILTRADO DE VEHICULOS POR NOMBRE DE FINANCIERA -->
    
    <div class="container bg-light" style="padding: 15px 0px;">
        <form class="row w-100 p-0 m-0 justify-content-around align-items-center" action="compras.jsp" method="post">
            <input type="hidden" name="accion" value="Financiera">
        <%  for(Bancos b: BancosDAO.listarBancos()){ 
                if(b.getStatus() == 1){
        %>
        <input type="submit" class="btn text-light" name="nomFinanciera" value="<%= b.getNombreBanco() %>" style="margin: 5px; background-color: #023059">
        <%      } 
            }
        %>
        </form>
    </div>
    
    <!-- TABLA DE VEHICULOS FILTRADO POR NOMBRE FINANCIERA -->    
    
    <form method="post" action="ControlCompras" class="container bg-light" style=" border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF " >  
        <div class="col-12 bg-white" style="padding-top:20px;">
            <table class="table table-boostrap-3 table-striped table-bordered tipo-letra text-center" style="width: 100%;">
                <thead>
                    <tr>
                        <th>Fecha de Compra</th>
                        <th>Marca</th>
                        <th>VIN</th>
                        <th>Serie</th>
                        <th>Valor Factura</th>
                        <th>Importe a pagar NETO</th>
                        <th>Proveedor</th>
                        <th>Status Financiamiento</th>
                        <th>Ubicacion</th>
                        <th>Financiado con</th>
                        <th>Fecha Financiamiento</th>
                        <th>Dias de Credito Fijos</th>
                        <th>Dias de Credito Reales</th>
                        <th>Dias Transcurrido</th>
                        <th>Tasa de interes Fija</th>
                        <th>Tasa de interes Real</th>
                        <th>Interes</th>
                        <th>Importe a pagar</th>
                        <th>Reporte Inventario</th>
                        <th>Factura</th>
                        <th>Fecha Factura</th>
                        <th>Nombre Cliente</th>
                        <th>ID Tipo de Venta</th>
                        <th>Tipo de Venta</th>
                        <th>Valor de Venta</th>
                        <th>Pago del Cliente</th>
                        <th>Saldo actual</th>
                        <th>DB</th>
                        <th>Fecha Carga</th>
                        <th>Prioridad Pago</th>
                        <th>Reporte NVDR</th>
                        <th>Observacion</th>
                        <th>CF</th>
                        <th>Diferencia</th>
                    </tr>
                </thead>
                <tbody>
            <%  
                /*Variables para calcular intereses*/
                int i = 0;
                double impPago = 0;
                double diferencia = 0;
                String fechaString = null;
                
                //Lista para recorrer observacion
                //int banderaObs = 0;
                String observacion = null;
                
                //Formato en que se presentan las cantidades 
                DecimalFormat formateador = new DecimalFormat("###,###,###.00");

                //Obtener parametro de la busqueda del formulario 
                String busqueda = request.getParameter("nomFinanciera");
                
                for(VistaCompras v: VistasDAO.buscarCompraFinanciera(busqueda)){ 
                   
                  
                    observacion = v.getNombreBanco();
                    
                    i++;
                    fechaString = v.getFechaFinanciamiento();
                    diferencia = v.getImporteNeto() - v.getCf();
                    impPago = v.getInteresVehiculo() + v.getImporteNeto();

                %>
                <tr>            
                    <td> <%= v.getFechaCompra() %> </td>
                    <td> <%= v.getMarca() %> </td>
                    <td> <%= v.getVin() %> </td>
                    <td> <%= v.getSerie() %> </td>
                    <td> $<%= formateador.format(v.getValorFactura()) %> </td>
                    <td> $<%= formateador.format(v.getImporteNeto()) %> </td>
                    <td> <%= v.getCarteraFinanciera() %> </td>
                    <td>
                        <!-- CAMPO ACTUALIZABLE -->
                        <select name="statusFinanciamiento_<%= i %>" class="statusFinan">
                            <option value="<%= v.getStatusFinanciamiento() %>"><%= v.getStatusFinanciamiento() %></option>
                            <option value="PAGADA" > PAGADA </option>
                            <option value="FINANCIADA" > FINANCIADA </option>
                            <option value="REFINANCIADA" > REFINANCIADA </option>
                            <option value="REDUCCION" > REDUCCION </option>
                            <option value="ENTREGADO" > ENTREGADO </option>

                        </select> 
                            
                            <!-- Activa o desactiva el campo dependiendo si tiene o no el ststus de REFINANCIADA -->
                            
                            <script>

                                $(document).ready(function(){
                                    var status = $("select[name = statusFinanciamiento_<%= i %>]");
                                    var fecha = $("input[name = fechaFinan_<%= i %>]");
                                    var diasR = $("input[name = diasReales_<%= i %>]");
                                    if(status.val() == 'REFINANCIADA'){
                                        fecha.prop('disabled','disabled');
                                        diasR.prop('disabled','disabled');
                                    }
                                });

                            </script>
                    </td>
                    <td> <%= v.getUbicacion() %> </td>
                    <td> 
                        <!-- CAMPO ACTUALIZABLE -->
                        <select name="nomFinanciera_<%= i %>">
                            <option value="<%= v.getIdBanco() %>"><%= v.getNombreBanco() %></option>
                            <% for(Bancos b : BancosDAO.listarBancos()){ 
                                if(b.getStatus()==1){
                            %>
                            <option value="<%= b.getIdBancos() %>" > <%= b.getNombreBanco() %> </option>
                            <% }
                            } %>
                        </select>  
                    </td> 
                    <td>
                        <!-- CAMPO ACTUALIZABLE -->
                        <input type="date" name="fechaFinan_<%= i %>" value="<%= fechaString %>"> 
                        <input type="hidden" name="id_<%= i %>" value="<%= v.getVin() %>"> 
                    </td>
                    <td> 
                        <% if(v.getStatusFinanciamiento().equals("REFINANCIADA")){ %>
                            <%= v.getDiasExtra() %>
                        <%}else{%>
                            <%= v.getDiasFinanciamiento() %> 
                        <%}%> 
                    </td>
                    <td>
                        <!-- CAMPO ACTUALIZABLE -->
                        <input type="number" name="diasReales_<%= i %>" min="0" max="<%= v.getDiasFinanciamiento() %>" value="<%= v.getDiasRealesFinanciamiento() %>" >
                    </td>
                    <td class="calcular"> <%= v.getDias() %> </td>
                    <td class="calcular"> 
                        <% if(v.getStatusFinanciamiento().equals("REFINANCIADA")){ %>
                            <%= formateador.format(v.getInteresExtra())%> % 
                        <%}else{%>
                            <%= formateador.format(v.getInteres())%> % 
                        <%}%>
                    </td>
                    <td class="calcular"> 
                        <!-- CAMPO ACTUALIZABLE -->
                         <input type="text" name="interesReal_<%= i %>" 
                               value = "<% if(v.getStatusFinanciamiento().equals("REFINANCIADA")){ %><%= v.getInteresExtraReal() %><%}else{%><%= v.getInteresReal() %><%}%> "
                        >
                    </td>
                    <td class="calcular"> $<%= formateador.format(v.getInteresVehiculo()) %> </td>
                    <td class="calcular"> $<%= formateador.format(impPago) %> </td>
                    <td> </td>
                    <td> <%= v.getFactura()%> </td>
                    <td> <%= v.getFechaFactura() %> </td>
                    <td> <%= v.getCliente() %> </td>
                    <td> <%= v.getIdTipoVenta() %> </td>
                    <td> <%= v.getTipoVenta() %> </td>
                    <td> $<%= formateador.format(v.getValorVenta()) %> </td>
                    <td> $<%= formateador.format(v.getPagoCliente()) %> </td>
                    <td> $<%= formateador.format(v.getSaldo()) %> </td>
                    <td> <%= v.getDb() %> </td>
                    <td> <%= v.getFechaCarga() %> </td>
                    <td>
                        <!-- CAMPO ACTUALIZABLE -->
                        <select name="prioridad_<%= i %>">
                            <option value="<%= v.getPrioridadPago() %>"> <%= v.getPrioridadPago() %> </option>
                            <option value="NORMAL"> NORMAL </option>
                            <option value="RELEVANTE"> RELEVANTE </option>
                            <option value="IMPORTANTE"> IMPORTANTE </option>
                        </select>

                    </td>
                     <td>
                        <!-- CAMPO ACTUALIZABLE -->
                        <input type="date" name="fechaNVDR_<%= i %>" value="<%= v.getReportadoNVDR() %>"> 
                    </td>
                    <td>
                        <!-- CAMPO ACTUALIZABLE -->
                        <input type="hidden" name="observaciones_<%= i %>" value=" <%= observacion %> ">
                        <%= observacion %> 
                    </td>
                    <td class="calcular"> $<%= formateador.format(v.getCf()) %> </td>
                    <td class="calcular"> $<%= formateador.format(diferencia) %> </td>
                </tr> 
                <%       //System.out.println("Iteraciones : "+ i);
                }
                               //System.out.println("Total Repeticiones: "+ i);
                %>
                </tbody>
            </table>
         
        </div>
        <div class="col-12 text-center d-flex justify-content-center align-items-center" style="height: 70px;">
            <input type="hidden" name="repeticiones" value="<%= i %>">
            <input class="btn btn-success" type="submit" name="accion" value="Guardar">
        </div>
            
    </form>
        
        
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Busqueda rapida</h3>
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
