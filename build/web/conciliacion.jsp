<%-- 
    Document   : conciliacion
    Created on : 14/11/2019, 10:22:38 AM
    Author     : Toshiba
--%>

<%@page import="dao.ReportesDAO"%>
<%@page import="modelo.Reportes"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dao.VehiculosDAO"%>
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
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1">
	
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
            <h2 class="text-center text-dark tipo-letra" >Concilicacion</h2>
        </div>
    </div>
    
    <!-- BOTON PARA GENERAR EXCEL DE CONCILIACION -->
    
    <div class="container col-12 bg-light text-center">
        <button id="btn-exportar-conciliacion" class="btn btn-success">
            EXCEL CONCILIACION
        </button> 
    </div>
    
    <div class="container" style="height: 80px; background-color: #F2F2F2;">
        <div class="row align-items-center justify-content-start h-100">
            <h3 class="text-center text-dark tipo-letra" style="padding-left: 15px;">Conciliacion por financiera</h3>
        </div>
    </div>
    
    <%
        /*Variables para calcular el total de cada columna en la tabla*/
        double diferenciaLinea = 0;
        double totalPromedio = 0;
        int i = 0;
        DecimalFormat formateador = new DecimalFormat("###,###,###.00");


    %>
            
    <form method="post" action="ControlConciliacion" id="contenedor-excel-conciliacion" class="container bg-light" style="margin-bottom: 80px; padding-bottom: 10px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
        <%/*
                List<String> listaFinanciera = new ArrayList();
                String[] lista = null;
                int i = 0;*/
                
                /*Filtro para identificar el total de financieras existentes y verlas sin que se repitan
                Ejemplo en vez de inprimir FCA ALFA, FCA FIAT y FCA SEMI, solo se imprime FCA juntando las
                tres financieras en una sola*/
                /*
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
                */
                
        for(Bancos b : BancosDAO.listarBancos()){
            if(b.getStatus() == 1){
                
        %>
        <div class="container">
            <div class="row align-items-center justify-content-start h-100">
                <h4  class="text-dark tipo-letra">
                     <%= b.getNombreBanco() %>
                </h4>
            </div>
        </div>
               
        <!-- TABLA RESUMEN DE CONCILIACION | TABLA QUE SE DESCARGA EN FORMATO EXCEL -->    
            
        <div class="container" style="padding-bottom: 10px; border-left: 2px solid #DFDFDF; border-right: 2px solid #DFDFDF">
            <table class="table-responsive table table-striped table-bordered tipo-letra text-center " style="width: 100%;">
                <thead>
                    <tr>
                        <th colspan="3"><%= b.getNombreBanco() %></th> 
                    </tr>
                    <tr>
                        <th>Resumen de</th>
                        <th>Total Unidades</th>
                        <th>Linea</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    i++;
                    /*Filtro que identifica a que grupo de financiera se le va a asignar el archivo de conciliacion (archivo csv) previamente 
                    subido en la seccion Financiar (vehiculos.jsp)*/
                    
                    //Variables para hacer el recorrido del archivo CSV
                        int totalVehiculosConciliacion = 0;
                        double totalCfConciliacion = 0;
                        final String SEPARATOR=";";
                        BufferedReader br = null;
                        String str = null;
                        int posicionBusqueda = 0;
                        int tamañoCadena = 0;
                        String vinCarga = null;
                        double valorCarga = 0; 

                        for(Reportes r: ReportesDAO.listarReporteConciliacion()){
                        
                            //Identifica el nombre de la financiera
                            if(r.getConciliacion().indexOf(b.getNombreBanco())==0){
                                
                                try {
                                    //Selecciona el archivo que tenga el nombre de la financiera
                                    br =new BufferedReader(new FileReader("C:\\planpiso\\respaldo_archivos\\"+r.getRuta()+""));
                                    String line = br.readLine();
                                    boolean banderaCSV = false;
                                    while (null!=line) {

                                        String [] fields = line.split(SEPARATOR);
                                        line = br.readLine();

                                        posicionBusqueda = fields[0].indexOf(",");
                                        tamañoCadena = fields[0].length();

                                        if(banderaCSV == true){
                                            vinCarga = fields[0].substring(0, posicionBusqueda);
                                            str = fields[0].substring(posicionBusqueda+1,tamañoCadena);
                                            valorCarga = Double.parseDouble(str);

                                            totalVehiculosConciliacion++;
                                            totalCfConciliacion += valorCarga; 
                                        }
                                        banderaCSV = true;
                                    }
                                } catch (Exception e) {
                                  System.out.println("error: "+e.getLocalizedMessage());
                                } finally {
                                    if (null!=br) {
                                    br.close();
                                    }
                                }
                            }
                        }
                        
                    %>
                    <tr>
                        <td> Continental </td>
                        <td> <%= totalVehiculosConciliacion %> </td>
                        <td> $<%= formateador.format(totalCfConciliacion) %> </td>
                    </tr>
                    <tr>
                        <td> Pagadas por Facturar </td>
                        <td> <input type="text" name="pxf_<%= i %>" value="<%= b.getPxf() %>"> </td>
                        <td> <input type="text" name="valorPxf_<%= i %>" value="<%= b.getValorPxf() %>"> 
                             <input type="hidden" name="id_<%= i %>" value="<%= b.getIdBancos() %>"> 
                        </td>
                    </tr>
                    <tr>
                        <td> Pagadas </td>
                        <td> <input type="text" name="pagadas_<%= i %>" value="<%= b.getPagadas() %>"> </td>
                        <td> <input type="text" name="valorPagadas_<%= i %>" value="<%= b.getValorPagadas() %>"> </td>
                    </tr>
                    <%  
                        totalPromedio = totalCfConciliacion + b.getValorPxf() + b.getValorPagadas();  
                    %>
                    <tr>
                        <td colspan="2"> Total </td>
                        <td> $<%= formateador.format(totalPromedio) %> </td>
                    </tr>
                    <tr>
                        <td colspan="2"> Linea <%= b.getNombreBanco() %> </td>
                        <td> $<%= formateador.format(b.getLinea()) %> </td>
                    </tr>
                    <%
                        diferenciaLinea = b.getLinea() - totalPromedio;
                    %>
                    <tr>
                        <td colspan="2"> Linea disponible </td>
                        <td> $<%= formateador.format(diferenciaLinea) %> </td>
                    </tr>
                </tbody>
            </table>
                     <%
                    /*Devolver los valores a su inicio para que no afecte a la 
                        siguiente tabla a calcular*/
                        totalCfConciliacion = 0;
                        totalVehiculosConciliacion = 0;
                        diferenciaLinea = 0;
                        totalPromedio = 0;
                %>      
        </div>
            <%          
                }
            }
            %>
        <div class="col-12 text-center d-flex justify-content-center align-items-center" style="height: 70px;">
            <input type="hidden" name="repeticiones" value="<%= i %>">
            <input class="btn btn-success" type="submit" name="accion" value="Guardar" style="margin: 10px;">
        </div>
    </form>
    
    <%@include file="footer.jsp" %>
 
    
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.20/js/dataTables.bootstrap4.min.js"></script>
    <script type="text/javascript" src="./js/jquery/jquery.table2excel.min.js"></script>
    <script type="text/javascript" src="./js/main.js"></script>
    
 
</body>
</html>
<% } %>