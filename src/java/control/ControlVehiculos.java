package control;

import dao.BancosDAO;
import dao.FinanciarDAO;
import dao.ReportesDAO;
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
import java.text.SimpleDateFormat;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        
        //Variables para guardar el archivo de conciliacion
        String nombre_archivo = null;
        int idBanco = 0;
        String fechaActual = null;
        String vin = null;
        String status = null;
        double interesReal = 0;
        String fechaCompra = null;
        String Banco = null;
        int diasReal = 0;
        
        //Variables para leer el archivo de conciliacion y actualizar el vehiculo
        final String SEPARATOR=";";
        BufferedReader br = null;
        String str = null;
        int posicionBusqueda = 0;
        int tamañoCadena = 0;
        String vinCarga = null;
        double valorCarga = 0; 
        
        FinanciarDAO f = new FinanciarDAO();
        ReportesDAO r = new ReportesDAO();
        
        switch(accion){
            case "Buscar":
                request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
                break;
            case "Financiar":
                
                idBanco = Integer.parseInt(request.getParameter("nomFinanciera"));
                fechaActual = request.getParameter("fechaActual");
                vin = request.getParameter("vin");
                status = request.getParameter("status");
                fechaCompra = request.getParameter("fechaCompra");
                /*    System.out.println("vin:" +vin);
                System.out.println("statsu:" +status);
                System.out.println("financiera:" +idBanco);
                System.out.println("fecha:" +fechaActual);
                System.out.println("interesReal:" +interesReal);*/

                for(Bancos b: BancosDAO.listarBancos()){
                    if(b.getIdBancos() == idBanco){
                        Banco = b.getNombreBanco();
                        diasReal = b.getDiasFinanciamiento();
                        interesReal = b.getInteres();
                    }
                }
                
                int bandera = Banco.indexOf("FCA");
                
                if((idBanco != 0) && (vin != null && (fechaActual != null) && (status != null) && (interesReal >= 0) && (fechaCompra != null)) 
                        && (diasReal >= 0)){
                    if(bandera == 0){
                        f.insertarFinanciamiento(vin,idBanco, fechaCompra,interesReal, diasReal);
                    }else{
                        f.insertarFinanciamiento(vin,idBanco, fechaActual,interesReal, diasReal);
                    }
                    //v.modificarVehiculo(status, vin, diasReales);
                    //System.out.println("SI se financio el vehiculo");
                }else{
                    //System.out.println("NO se financio el vehiculo");
                }
                      
                request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
                break;
            case "Subir Conciliacion":
                
                int posicion = 0;
                String nombre = null;
                int total_vehiculos = 0;
                double total_cf = 0;
                
                String tipo = request.getParameter("tipo");
                String financiera = request.getParameter("financiera");
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String fecha = formato.format(new Date());
                
                //Primera parte
                //Se guarda el archivo de conciliacion en una carpeta de respaldo y dentro del proyecto
                
                final String ruta = "C:/planpiso/respaldo_archivos";
                final String ruta2 = "C:\\glassfish4\\glassfish\\domains\\domain1\\applications\\PlanPiso\\archivos";
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
                
                //Segunda parte
                /*Se lee el archivo de conciliacion y se busca una coincidencia con algun vin de un vehiculo 
                  financiado para actualizar su CF
                */
                try {
                    br =new BufferedReader(new FileReader("C:\\planpiso\\respaldo_archivos\\"+nombre_archivo+""));
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
                        
                            total_vehiculos++;
                            total_cf += valorCarga; 
                            
                            /*System.out.println("STR: "+str);
                            System.out.println("line: "+line);
                            System.out.println("VIN: "+vinCarga);
                            System.out.println("VALOR: "+valorCarga);
                            System.out.println("TOTAL VEHICULOS: "+total_vehiculos);
                            System.out.println("TOTAL CF: "+total_cf); */
                            
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
                
                posicion = nombre_archivo.indexOf(".");
                nombre = nombre_archivo.substring(0, posicion);
                
                r.insertarConciliacion(nombre, nombre_archivo, tipo, financiera, fecha);
                
                request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
                
                break;
            default:
                request.getRequestDispatcher("vehiculos.jsp").forward(request, response);
                System.out.println("Accion desconocida");
                break;
        }
        
    }
    //Funcion para guardar un archivo
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
