package control;

import dao.BancosDAO;
import dao.VehiculosDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Vehiculo;

public class ControlBanco extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        switch(accion){
            case "Buscar":
                request.getRequestDispatcher("financiera.jsp").forward(request, response);
                break;
            default:
                System.out.println("Accion desconocida");
                request.getRequestDispatcher("financiera.jsp").forward(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String accion = request.getParameter("accion");
        
        switch(accion){
            case "Modificar":
            request.getRequestDispatcher("modificar-interes.jsp").forward(request, response);
                break;
            case "Cancelar":
                request.getRequestDispatcher("financiera.jsp").forward(request, response);
                break;
            case "Actualizar":
                actualizar(request, response);
                break;
            case "Registrar":
                registrar(request, response);
                break;
            case "Activar":
                activar(request, response);
                break;
            case "Desactivar":
                desactivar(request, response);
                break;
            default:
                System.out.println("Accion desconocida");
                request.getRequestDispatcher("financiera.jsp").forward(request, response);
                break;
        }
       
    }
    
    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombBanco = request.getParameter("nombreBanco");
        double interes = Double.parseDouble(request.getParameter("interes"));
        int diasInteres = Integer.parseInt(request.getParameter("diasFinanciado"));
        double interesExtra = Double.parseDouble(request.getParameter("interesExtra"));
        int diasExtra = Integer.parseInt(request.getParameter("diasExtra"));
        int diasLibres = Integer.parseInt(request.getParameter("diasLibres"));
        
        BancosDAO b = new BancosDAO();
        
        b.insertarBanco(nombBanco, interes, diasInteres, diasExtra, interesExtra,diasLibres);
        
        request.getRequestDispatcher("financiera.jsp").forward(request, response);
    }
    
    private void actualizar(HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{

        int id = Integer.parseInt(request.getParameter("id"));
        String nombreBanco = request.getParameter("nombreBanco");
        double interes = Double.parseDouble(request.getParameter("interes"));
        int diasFinan = Integer.parseInt(request.getParameter("diasFinanciamiento"));
        int diasExt = Integer.parseInt(request.getParameter("diasExtra"));
        double interesExt = Double.parseDouble(request.getParameter("interesExtra"));
        int diasLibres = Integer.parseInt(request.getParameter("diasLibres"));

        BancosDAO b = new BancosDAO();

        b.modificarBanco(nombreBanco,interes,diasFinan,diasExt,interesExt,id,diasLibres);

        //System.out.println("Financiera Actualizada");
        request.getRequestDispatcher("financiera.jsp").forward(request, response);
    }
    
    private void activar(HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{
        
        int id = Integer.parseInt(request.getParameter("id"));
        BancosDAO b = new BancosDAO();
        b.activarBanco(id);
        request.getRequestDispatcher("financiera.jsp").forward(request, response);
    }
    
    private void desactivar(HttpServletRequest request,HttpServletResponse response)
        throws ServletException, IOException{
        
        int id = Integer.parseInt(request.getParameter("id"));
        BancosDAO b = new BancosDAO();
        b.desactivarBanco(id);
        request.getRequestDispatcher("financiera.jsp").forward(request, response);
    }
}
