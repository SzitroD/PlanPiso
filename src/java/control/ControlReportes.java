package control;

import dao.ReportesDAO;
import modelo.Reportes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class ControlReportes extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String nombre= null;
        String nombre_archivo = null;
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formato.format(new Date());
        
        ReportesDAO r = new ReportesDAO();
        
        final String ruta = "C:\\Users\\Toshiba\\Documents\\NetBeansProjects\\PlanPiso\\web\\archivos";
        final String ruta2 = "C:/planpiso/respaldo_archivos";
        //final String ruta = "C:\\glassfish4\\glassfish\\domains\\domain1\\applications\\PlanPiso\\archivos";
        //final String ruta2 = "C:/planpiso/respaldo_archivos";
        final Part archivo = request.getPart("reporte");
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
            System.out.println("Ocurrio un error al guardar el reporte");
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
        
        int posicion = nombre_archivo.indexOf(".");
        nombre = nombre_archivo.substring(0, posicion);
        
        r.insertarReporte(nombre,nombre_archivo,fecha);
        request.getRequestDispatcher("archivos.jsp").forward(request, response);
    
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
