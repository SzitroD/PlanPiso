package modelo;

public class Reportes {
    private int idReporte;
    private String nombreReporte;
    private String ruta;
    private String tipo;
    private String fecha;
    private String conciliacion;

    public Reportes() {
        this.idReporte = 0;
        this.nombreReporte = "";
        this.ruta = "";
        this.tipo = "";
        this.fecha = "";
        this.conciliacion = "";
    }
    
    public Reportes(int idReporte, String nombreReporte, String ruta, String tipo, String fecha,String conciliacion) {
        this.idReporte = idReporte;
        this.nombreReporte = nombreReporte;
        this.ruta = ruta;
        this.tipo = tipo;
        this.fecha = fecha;
        this.conciliacion = conciliacion;
        
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConciliacion() {
        return conciliacion;
    }

    public void setConciliacion(String conciliacion) {
        this.conciliacion = conciliacion;
    }
    
    
}
