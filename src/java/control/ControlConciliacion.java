package control;

import dao.BancosDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControlConciliacion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        switch(accion){
            case "Guardar":
                   
                int repeticiones = Integer.parseInt(request.getParameter("repeticiones"));
                int id = 0;
                int pxf = 0;
                double valorPxf = 0;
                int pagadas = 0;
                double valorPagadas = 0;
               
                
                for(int i = 1; i <= repeticiones; i++){
                    try {
                        id = Integer.parseInt(request.getParameter("id_" + i + ""));
                    }catch (Exception e) {
                            System.out.println("Error al recibir id: "+e.getLocalizedMessage());
                    }
                    try{
                        pxf = Integer.parseInt(request.getParameter("pxf_" + i + ""));
                    }catch (Exception e) {
                            System.out.println("Error al recibir pxf: "+e.getLocalizedMessage());
                    }
                    try{
                        valorPxf = Double.parseDouble(request.getParameter("valorPxf_" + i + ""));
                    }catch (Exception e) {
                            System.out.println("Error al recibir el valor de pxf: "+e.getLocalizedMessage());
                    }
                    try{
                        pagadas = Integer.parseInt(request.getParameter("pagadas_" + i + ""));
                    }catch (Exception e) {
                            System.out.println("Error al recibir pagadas: "+e.getLocalizedMessage());
                    }
                    try{
                        valorPagadas = Double.parseDouble(request.getParameter("valorPagadas_" + i + ""));
                    }catch (Exception e) {
                            System.out.println("Error al recibir el valor de pagadas: "+e.getLocalizedMessage());
                    }
                    
                    BancosDAO b = new BancosDAO();
                    b.actializarConciliacion(pxf, valorPxf, pagadas, valorPagadas, id);
                   
                }
                 request.getRequestDispatcher("conciliacion.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("conciliacion.jsp").forward(request, response);
                System.out.println("Accion desconocida.......");
                break;
        }
    }

}
