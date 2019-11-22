package modelo;

public class Reportes {
    private int idReporte;
    private String nombreReporte;
    private String ruta;

    public Reportes() {
        this.idReporte = 0;
        this.nombreReporte = "";
        this.ruta = "";
    }
    
    public Reportes(int idReporte, String nombreReporte, String ruta) {
        this.idReporte = idReporte;
        this.nombreReporte = nombreReporte;
        this.ruta = ruta;
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
    
    
}
