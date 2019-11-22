package modelo;

public class Financiar {
    private int idFinanciar;
    private String vinVehiculo;
    private int idBanco;
    private String fechaFinanciamineto;
    private double cf;
    private int diasReales;
    private String prioridadPago;
    private String reportadoNVDR;
    private String observaciones;
    private double interesReal;
    private String status;

    public Financiar() {
        this.idFinanciar = 0;
        this.vinVehiculo = "";
        this.idBanco = 0;
        this.fechaFinanciamineto = "";
        this.cf = 0;
        this.diasReales = 0;
        this.observaciones = "";
        this.prioridadPago= "";
        this.reportadoNVDR = "";
        this.interesReal = 0;
        this.status = "";
    }
    
    public Financiar(String status,double interesReal,String prioridadPago, String reportadoNVDR, String observaciones,int idFinanciar, String vinVehiculo, int idBanco, String fechaFinanciamineto, double cf, int diasReales) {
        this.idFinanciar = idFinanciar;
        this.vinVehiculo = vinVehiculo;
        this.idBanco = idBanco;
        this.fechaFinanciamineto = fechaFinanciamineto;
        this.cf = cf;
        this.diasReales = diasReales;
        this.observaciones = observaciones;
        this.prioridadPago= prioridadPago;
        this.reportadoNVDR = reportadoNVDR;
        this.interesReal = interesReal;
        this.status = status;
    }

    public double getInteresReal() {
        return interesReal;
    }

    public void setInteresReal(double interesReal) {
        this.interesReal = interesReal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrioridadPago() {
        return prioridadPago;
    }

    public void setPrioridadPago(String prioridadPago) {
        this.prioridadPago = prioridadPago;
    }

    public String getReportadoNVDR() {
        return reportadoNVDR;
    }

    public void setReportadoNVDR(String reportadoNVDR) {
        this.reportadoNVDR = reportadoNVDR;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getCf() {
        return cf;
    }

    public void setCf(double cf) {
        this.cf = cf;
    }
    
    public int getIdFinanciar() {
        return idFinanciar;
    }

    public void setIdFinanciar(int idFinanciar) {
        this.idFinanciar = idFinanciar;
    }

    public String getVinVehiculo() {
        return vinVehiculo;
    }

    public void setVinVehiculo(String vinVehiculo) {
        this.vinVehiculo = vinVehiculo;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getFechaFinanciamineto() {
        return fechaFinanciamineto;
    }

    public void setFechaFinanciamineto(String fechaFinanciamineto) {
        this.fechaFinanciamineto = fechaFinanciamineto;
    }

    public int getDiasReales() {
        return diasReales;
    }

    public void setDiasReales(int diasReales) {
        this.diasReales = diasReales;
    }
    
    
    
    
}
