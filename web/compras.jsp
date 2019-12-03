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

    <link rel="stylesheet" href="./css/compras.css">
    <link rel="stylesheet" href="./css/header.css">
    <link rel="stylesheet" href="./css/footer.css">
    <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
</head>
<body>

    <%@include file="header.jsp" %>

    <div class="contenedor-titulo primer-titulo">
        <h2 class="titulo">Compras</h2>
    </div>

    <div class="contenedor-busqueda">
        <form action="ControlCompras" method="get" class="form-busqueda">
            <input type="search" name="busqueda" placeholder="Vin del Vehiculo" class="search">
            <input type="submit" name="accionVin" value="Buscar" class="btn-enviar">
        </form>
    </div>
    
    <div class="contenedor-btn-financiera">
        <form class="form-btn" action="ControlCompras" method="get">
        <% for(Bancos b : BancosDAO.listarBancos()){ 
                if(b.getStatus() == 1){
        %>
            <input class="btn" type="submit" name="accionFin" value="<%= b.getNombreBanco() %>">
        <% } 
        }
        %>
        </form>
    </div>

    <div class="contenedor-titulo">
        <h2 class="titulo">Resultado</h2>
    </div>
         
    <form method="post" action="ControlCompras" class="form-tabla ultimo-contenido">  
        <div class="contendor-tabla">
            <table class="table">
                <thead>
                    <tr class="thead">
                        <th class="dato-auto">Fecha de Compra</th>
                        <th class="dato-auto">Marca</th>
                        <th class="dato-auto">VIN</th>
                        <th class="dato-auto">Serie</th>
                        <th class="dato-auto">Valor Factura</th>
                        <th class="dato-auto">Importe a pagar NETO</th>
                        <th class="dato-auto">Proveedor</th>
                        <th class="dato-auto">Status Financiamiento</th>
                        <th class="dato-auto">Ubicacion</th>
                        <th class="dato-auto">Financiado con</th>
                        <th class="dato-auto">Fecha Financiamiento</th>
                        <th class="dato-compra">Dias de Credito Fijos</th>
                        <th class="dato-compra">Dias de Credito Reales</th>
                        <th class="dato-compra">Dias Transcurrido</th>
                        <th class="dato-compra">Tasa de interes Fija</th>
                        <th class="dato-compra">Tasa de interes Real</th>
                        <th class="dato-compra">Interes</th>
                        <th class="dato-compra">Importe a pagar</th>
                        <th class="dato-compra">Reporte Inventario</th>
                        <th class="dato-cliente">Factura</th>
                        <th class="dato-cliente">Fecha Factura</th>
                        <th class="dato-cliente">Nombre Cliente</th>
                        <th class="dato-cliente">ID Tipo de Venta</th>
                        <th class="dato-cliente">Tipo de Venta</th>
                        <th class="dato-cliente">Valor de Venta</th>
                        <th class="dato-cliente">Pago del Cliente</th>
                        <th class="dato-cliente">Saldo actual</th>
                        <th class="dato-cliente">DB</th>
                        <th class="dato-cliente">Fecha Carga</th>
                        <th class="dato-cliente">Prioridad Pago</th>
                        <th class="dato-cliente">Reporte NVDR</th>
                        <th class="dato-cliente">Observacion</th>
                        <th class="dato-cliente">CF</th>
                        <th class="dato-cliente">Diferencia</th>
                    </tr>
                </thead>
                <tbody class="tbody">
            <%  
                String financiera = null;
                String Busqueda = null;
                try{
                    Busqueda = request.getParameter("busqueda");
                }catch(Exception e){
                    System.out.println("Error al recibir busqueda: "+e.getLocalizedMessage());
                }   
                try{
                    financiera = request.getParameter("accionFin");
                }catch(Exception e){
                    System.out.println("Error al recibir financiera: "+e.getLocalizedMessage());
                } 
                int i = 0;
                double impPago = 0;
                double diferencia = 0;
                String fechaString = null;
                DecimalFormat formateador = new DecimalFormat("###,###,###.00");

                String observacion = null;
                
                if(Busqueda != null){
                
                    System.out.println("Busqueda ++: "+Busqueda);
                    System.out.println("Financiera: "+financiera);
                    
                    for(VistaCompras v: VistasDAO.buscarCompraVin(Busqueda)){ 
                        if(v.getStatusBanco()==1){
                        i++;
                        fechaString = v.getFechaFinanciamiento();
                        
                        observacion = v.getNombreBanco();
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
                            <select name="statusFinanciamiento_<%= i %>" class="statusFinan">
                                <option value="<%= v.getStatusFinanciamiento() %>"><%= v.getStatusFinanciamiento() %></option>
                                <option value="PAGADA" > PAGADA </option>
                                <option value="FINANCIADA" > FINANCIADA </option>
                                <option value="REFINANCIADA" > REFINANCIADA </option>
                                
                            </select> 
                                
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
                            <select name="prioridad_<%= i %>">
                                <option value="<%= v.getPrioridadPago() %>"> <%= v.getPrioridadPago() %> </option>
                                <option value="NORMAL"> NORMAL </option>
                                <option value="RELEVANTE"> RELEVANTE </option>
                                <option value="IMPORTANTE"> IMPORTANTE </option>
                            </select>
                                
                        </td>
                        <td> <%= v.getReportadoNVDR() %> </td>
                        <td>
                            <input type="hidden" name="observaciones_<%= i %>" value=" <%= observacion %> ">
                            <%= v.getObservaciones() %> 
                        </td>
                        <td class="calcular"> $<%= formateador.format(v.getCf()) %> </td>
                        <td class="calcular"> $<%= formateador.format(diferencia) %> </td>
                    </tr> 
                    <%  }          //System.out.println("Iteraciones : "+ i);
                    }
                                //System.out.println("Total Repeticiones: "+ i);
                }else if(financiera != null){ 
                    
                    System.out.println("Busqueda: "+Busqueda);
                    System.out.println("Financiera ++: "+financiera);
                    for(VistaCompras v: VistasDAO.buscarCompraFinanciera(financiera)){ 
                        if(v.getStatusBanco() == 1){
                        i++;
                        fechaString = v.getFechaFinanciamiento();
                        observacion = v.getNombreBanco();
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
                           <select name="statusFinanciamiento_<%= i %>">
                                <option value="<%= v.getStatusFinanciamiento() %>"><%= v.getStatusFinanciamiento() %></option>
                                <option value="PAGADA" > PAGADA </option>
                                <option value="FINANCIADA" > FINANCIADA </option>
                                <option value="REFINANCIADA" > REFINANCIADA </option>
                            </select> 
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
                            <input type="text" name="interesReal_<%= i %>" 
                                   value = "<% if(v.getStatusFinanciamiento().equals("REFINANCIADA")){ %><%= v.getInteresExtraReal() %><%}else{%><%= v.getInteresReal() %><%}%> "
                            >
                        </td>
                        <td class="calcular"> $<%= formateador.format(v.getInteresVehiculo()) %> </td>
                        <td class="calcular"> $ <%= formateador.format(impPago) %> </td>
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
                            <select name="prioridad_<%= i %>">
                                <option value="<%= v.getPrioridadPago() %>"> <%= v.getPrioridadPago() %> </option>
                                <option value="NORMAL"> NORMAL </option>
                                <option value="RELEVANTE"> RELEVANTE </option>
                                <option value="IMPORTANTE"> IMPORTANTE </option>
                            </select>
                        </td>
                        <td> <%= v.getReportadoNVDR() %> </td>
                        <td>
                            <input type="hidden" name="observaciones_<%= i %>" value=" <%= observacion %> ">
                            <%= v.getObservaciones() %> 
                        </td>
                        <td class="calcular"> $<%= formateador.format(v.getCf()) %> </td>
                        <td class="calcular"> $<%= formateador.format(diferencia) %> </td>
                    </tr>  
        <%              }
                    }
                }else{
                    System.out.println("Valores nulos");
                }
        %>
                </tbody>
            </table>
         
        </div>
        <div class="contenedor-btn-guardar">
            <input type="hidden" name="repeticiones" value="<%= i %>">
            <input class="btn-guardar" type="submit" name="accion" value="Guardar">
        </div>

    </form>
         
    <%@include file="footer.jsp" %>
 
    <script type="text/javascript" src="./js/main.js"></script>
    
</body>
</html>

<% } %>
