package control;

import dao.ReportesDAO;
import dao.VehiculosDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class CargarCSV extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        String nombre_archivo = null;
        
        
        VehiculosDAO v = new VehiculosDAO();
       
        final String ruta = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads";
        final String ruta2 = "C:/planpiso/respaldo_csv";
        final Part archivo = request.getPart("archivo");
        nombre_archivo = getFileName(archivo);
        OutputStream salida = null;
        OutputStream salida2 = null;
        InputStream contenido = null;
        
        File folder = new File(ruta);
        if(!folder.isDirectory()) {
            folder.mkdirs();
        }
        
        File folder2 = new File(ruta2);
        if(!folder2.isDirectory()) {
            folder2.mkdirs();
        }
        
        try {
            salida = new FileOutputStream(new File(ruta + File.separator + nombre_archivo));
            salida2 = new FileOutputStream(new File(ruta2 + File.separator + nombre_archivo));
            contenido = archivo.getInputStream();
            int read = 0;
            final byte[] bytes = new byte[1024];
            while((read = contenido.read(bytes)) != -1) {
                salida.write(bytes, 0 , read);
                salida2.write(bytes, 0 , read);
            }
        } catch (FileNotFoundException fne) {
            System.out.println("Ocurrio un error al guardar el archivo");
        } finally {
            if (salida != null) {
                salida.close();
            }
            if (salida2 != null) {
                salida2.close();
            }
            if (contenido != null) {
                contenido.close();
            }
        }
        
        switch(accion){
            case "Vehiculo":
//                v.ingresarDatosCSV(nombre_archivo);
                request.getRequestDispatcher("financiera.jsp").forward(request, response);
                break;
            default:
                System.out.println("Accion desconocida....");
                request.getRequestDispatcher("financiera.jsp").forward(request, response);
                break;
        }
        System.out.println("accion: "+accion);
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        // LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
}
