package control;

import dao.FinanciarDAO;
import modelo.Financiar;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlCompras extends HttpServlet {

   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion"); 
        switch(accion){
                case "Buscar": 
                    String busqueda = request.getParameter("busqueda");
                    request.getRequestDispatcher("compras-vin.jsp").forward(request, response);
                    break;
                case "Financiera":
                    request.getRequestDispatcher("compras.jsp").forward(request, response);
                    break;
                default :
                    request.getRequestDispatcher("compras.jsp").forward(request, response);
                    break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            String accion = request.getParameter("accion");
           
            switch(accion){
                case "Cancelar":
                    request.getRequestDispatcher("compras.jsp").forward(request, response);
                    break;
                case "Buscar":
                    request.getRequestDispatcher("compras-vin.jsp").forward(request, response);
                    break;
                case "Financiera":
                    request.getRequestDispatcher("compras.jsp").forward(request, response);
                    break;
                case "Guardar":
                   
                    int repeticiones = Integer.parseInt(request.getParameter("repeticiones"));
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    
                    for(int i = 1; i <= repeticiones; i++){
                   
                        int idBanco = 0;
                        int diaR = 0;
                        String statusFinanciamiento = null;
                        String vin = null;
                        String fechaString = null;
                        String prioridad = null;
                        int diasReales = 0;
                        String observaciones = null;
                       
                        double interesReal = 0;
                        String fechaNVDR = null;
                        String fechaActual = formato.format(new Date());
                        boolean bandera;
                        /*Recibe parametros de cada fila de la tabla*/
                        try {
                            statusFinanciamiento = request.getParameter("statusFinanciamiento_" + i + "");
                        } catch (Exception e) {
                            System.out.println("Error al recibir status: "+e.getLocalizedMessage());
                        }
                        try {
                            idBanco = Integer.parseInt(request.getParameter("nomFinanciera_" + i + ""));
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("Error al recibir idBanco: "+numberFormatException.getLocalizedMessage());
                        }
                        try {
                            vin = request.getParameter("id_" + i + "");
                        } catch (Exception e) {
                            System.out.println("Error al recibir id: "+e.getLocalizedMessage());
                        }
                        try {
                            fechaString = request.getParameter("fechaFinan_"+ i + "");
                        } catch (Exception e) {
                            System.out.println("Error al recibir fecha: "+e.getLocalizedMessage());
                        }
                        try{
                            diasReales = Integer.parseInt(request.getParameter("diasReales_"+i+""));
                        }catch(Exception e){
                            System.out.println("Error al recibir diasReales: "+e.getLocalizedMessage());
                        }
                        try{
                            prioridad = request.getParameter("prioridad_"+i+"");
                        }catch(Exception e){
                            System.out.println("Error al recibir prioridad: "+e.getLocalizedMessage());
                        }
                        try{
                            observaciones = request.getParameter("observaciones_"+i+"");
                        }catch(Exception e){
                            System.out.println("Error al recibir observaciones: "+e.getLocalizedMessage());
                        }
                        try{
                            interesReal = Double.parseDouble(request.getParameter("interesReal_"+i+""));
                        }catch(Exception e){
                            System.out.println("Error al recibir interes real: "+e.getLocalizedMessage());
                        }
                        try{
                            fechaNVDR = request.getParameter("fechaNVDR_"+i+"");
                        }catch(Exception e){
                            System.out.println("Error al recibir fecha NVDR: "+e.getLocalizedMessage());
                        }
                        /*
                        System.out.println("idBanco: "+idBanco);
                        System.out.println("id: "+vin);
                        System.out.println("fecha: "+fechaString);
                        System.out.println("dias: "+diasReales);
                        System.out.println("prioridad: "+prioridad);
                        System.out.println("observaciones: "+observaciones);
                        System.out.println("interesReal: "+interesReal);
                        System.out.println("statusFinanciamiento: "+statusFinanciamiento);
                        System.out.println("fecha actual: "+fechaActual);
                        System.out.println("fecha reporte NVDR: "+fechaNVDR);
                        */
                        
                        FinanciarDAO f = new FinanciarDAO();
                        /*Identifica los dias reales de financiamiento dependiendo del id de banco
                        este dato es en caso de que el status sea REFINANCIADA porque si posee ese status
                        enviara valores nulos en fecha de financiamiento y dias reales*/
                        
                        for(Financiar fi : FinanciarDAO.listarFinanciamiento()){
                            if(fi.getIdBanco() == idBanco){
                                diaR = fi.getDiasReales();
                            }
                        }
                        if(diasReales == 0 || fechaString == null){
                            fechaString = fechaActual;
                            diasReales = diaR;
                        }
                        /*Filtro de status*/
                        bandera = statusFinanciamiento.equals("REFINANCIADA");
                        
                        if(bandera == true){
                            
                            System.out.println("status = REFINANCIADA");
                                if((idBanco != 0) && (vin != null) && (vin != null) && (fechaString != null) && 
                                (diasReales >= 0) && (prioridad != null) && (observaciones != null) && 
                                (interesReal >= 0) && (statusFinanciamiento != null) && (fechaNVDR != null)){

                                    f.refinanciemiento(idBanco, fechaString,vin,diasReales,prioridad,observaciones,interesReal,statusFinanciamiento,fechaNVDR);

                                }else{
                                    System.out.println("Error al actualizar, status = REFINANCIADA");
                                }
                        }else{
                           
                            System.out.println("status != REFINANCIADA");
                                
                                if((idBanco != 0) && (vin != null) && (vin != null) && (fechaString != null) && 
                                (diasReales >= 0) && (prioridad != null) && (observaciones != null) && 
                                (interesReal >= 0) && (statusFinanciamiento != null) && (fechaNVDR != null)){

                                    f.actualizar(idBanco, fechaString,vin,diasReales,prioridad,observaciones,interesReal,statusFinanciamiento,fechaNVDR);

                                }else{
                                    System.out.println("Error al actualizar, status != REFINANCIADA");
                                }
                        }
                        
                       
                   }
                   
                    /*System.out.println("rep control: "+repeticiones);*/
                    request.getRequestDispatcher("compras.jsp").forward(request, response);
                   break;
               default :
                   request.getRequestDispatcher("compras.jsp").forward(request, response);
                   System.out.println("Accion desconocida.......");
                   break;
                   
            }       
    }
    
    
}
