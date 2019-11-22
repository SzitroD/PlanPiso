package control;

import dao.BancosDAO;
import dao.FinanciarDAO;
import dao.VehiculosDAO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Bancos;
import modelo.Financiar;

@MultipartConfig
public class ControlVehiculos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if(accion.equals("Buscar")){
            request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
        }else{
            System.out.println("Accion desconocida");
            
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        String nombre_archivo = null;
        int idBanco = 0;
        String fechaActual = null;
        String vin = null;
        String status = null;
        double interesReal = 0;
        String fechaCompra = null;
        String Banco = null;
        
        
        final String SEPARATOR=";";
        BufferedReader br = null;
        String str = null;
        int posicionBusqueda = 0;
        int tamañoCadena = 0;
        String vinCarga = null;
        double valorCarga = 0; 
        
        FinanciarDAO f = new FinanciarDAO();
        VehiculosDAO v = new VehiculosDAO();
        
        switch(accion){
            case "Financiar":
                
                idBanco = Integer.parseInt(request.getParameter("nomFinanciera"));
                fechaActual = request.getParameter("fechaActual");
                vin = request.getParameter("vin");
                status = request.getParameter("status");
                interesReal = Integer.parseInt(request.getParameter("interesReal"));
                fechaCompra = request.getParameter("fechaCompra");
                
                /*    System.out.println("vin:" +vin);
                System.out.println("statsu:" +status);
                System.out.println("financiera:" +idBanco);
                System.out.println("fecha:" +fechaActual);
                System.out.println("interesReal:" +interesReal);*/

                for(Bancos b: BancosDAO.listarBancos()){
                    if(b.getIdBancos() == idBanco){
                        Banco = b.getNombreBanco();
                    }
                }
                
                int bandera = Banco.indexOf("FCA");
                
                if((idBanco != 0) && (vin != null && (fechaActual != null) && (status != null) && (interesReal >= 0) && (fechaCompra != null)) ){
                    if(bandera == 0){
                        f.insertarFinanciamiento(vin,idBanco, fechaCompra,interesReal);
                    }else{
                        f.insertarFinanciamiento(vin,idBanco, fechaActual,interesReal);
                    }
                    //v.modificarVehiculo(status, vin, diasReales);
                    //System.out.println("SI se financio el vehiculo");
                }else{
                    //System.out.println("NO se financio el vehiculo");
                }
                      
                request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
                break;
            case "Subir Conciliacion":

                final String ruta = "C:/planpiso/respaldo_csv";
                final Part archivo = request.getPart("archivo");
                nombre_archivo = getFileName(archivo);
                OutputStream salida = null;
                InputStream contenido = null;
                File folder = new File(ruta);
                if(!folder.isDirectory()) {
                    folder.mkdirs();
                }
        
                try {
                    salida = new FileOutputStream(new File(ruta + File.separator + nombre_archivo));
                    contenido = archivo.getInputStream();
                    int read = 0;
                    final byte[] bytes = new byte[1024];
                    while((read = contenido.read(bytes)) != -1) {
                        salida.write(bytes, 0 , read);
                    }
                } catch (FileNotFoundException fne) {
                    System.out.println("Ocurrio un error al guardar el archivo");
                } finally {
                    if (salida != null) {
                        salida.close();
                    }
                    if (contenido != null) {
                        contenido.close();
                    }
                }
                
                try {
                    br =new BufferedReader(new FileReader("C:\\planpiso\\respaldo_csv\\"+nombre_archivo+""));
                    String line = br.readLine();
                    boolean banderaCSV = false;
                    while (null!=line) {
                        
                        String [] fields = line.split(SEPARATOR);
                        System.out.println(fields[0]);
                        line = br.readLine();

                        posicionBusqueda = fields[0].indexOf(",");
                        tamañoCadena = fields[0].length();

                        //System.out.println("poscionBusqueda: "+posicionBusqueda);
                        //System.out.println("tamaño: "+tamañoCadena);
                        
                        if(banderaCSV == true){
                            vinCarga = fields[0].substring(0, posicionBusqueda);
                            str = fields[0].substring(posicionBusqueda+1,tamañoCadena);
                            valorCarga = Double.parseDouble(str);
                        
                            System.out.println("STR: "+str); 
                            System.out.println("line: "+line); 
                            System.out.println("VIN: "+vinCarga); 
                            System.out.println("VALOR: "+valorCarga); 

                            f.cargarConciliacion(valorCarga, vinCarga);
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
                
                request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
                
                break;
            default:
                request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
                System.out.println("Accion desconocida");
                break;
        }
        
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
