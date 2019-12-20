package modelo;

public class Bancos {
    private int idBancos;
    private String nombreBanco;
    private double interes;
    private int diasFinanciamiento;
    private double interesExtra;
    private int diasExtra;
    private int diasLibres;
    private int status;
    private double linea;
    
    private int pxf;
    private double valorPxf;
    private int pagadas;
    private double valorPagadas;

    public Bancos() {
        this.idBancos = 0;
        this.nombreBanco = "";
        this.interes = 0;
        this.diasFinanciamiento = 0;
        this.interesExtra = 0;
        this.diasExtra = 0;
        this.diasLibres = 0;
        this.status = 0;
        this.linea = 0;
        this.pxf = 0;
        this.valorPxf = 0;
        this.pagadas = 0;
        this.valorPagadas = 0;
    }
    
    public Bancos(int pxf, double valorPxf, int pagadas,double valorPagadas,double linea,int status,int idBancos, String nombreBanco, double interes, int diasFinanciamiento, double interesExtra, int diasExtra, int diasLibres) {
        this.idBancos = idBancos;
        this.nombreBanco = nombreBanco;
        this.interes = interes;
        this.diasFinanciamiento = diasFinanciamiento;
        this.interesExtra = interesExtra;
        this.diasExtra = diasExtra;
        this.diasLibres = diasLibres;
        this.status = status;
        this.linea = linea;
        this.pxf = pxf;
        this.valorPxf = valorPxf;
        this.pagadas = pagadas;
        this.valorPagadas = valorPagadas;
    }

    public int getPxf() {
        return pxf;
    }

    public void setPxf(int pxf) {
        this.pxf = pxf;
    }

    public double getValorPxf() {
        return valorPxf;
    }

    public void setValorPxf(double valorPxf) {
        this.valorPxf = valorPxf;
    }

    public int getPagadas() {
        return pagadas;
    }

    public void setPagadas(int pagadas) {
        this.pagadas = pagadas;
    }

    public double getValorPagadas() {
        return valorPagadas;
    }

    public void setValorPagadas(double valorPagadas) {
        this.valorPagadas = valorPagadas;
    }
    
    public double getLinea() {
        return linea;
    }

    public void setLinea(double linea) {
        this.linea = linea;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public int getIdBancos() {
        return idBancos;
    }

    public void setIdBancos(int idBancos) {
        this.idBancos = idBancos;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public int getDiasFinanciamiento() {
        return diasFinanciamiento;
    }

    public void setDiasFinanciamiento(int diasFinanciamiento) {
        this.diasFinanciamiento = diasFinanciamiento;
    }

    public double getInteresExtra() {
        return interesExtra;
    }

    public void setInteresExtra(double interesExtra) {
        this.interesExtra = interesExtra;
    }

    public int getDiasExtra() {
        return diasExtra;
    }

    public void setDiasExtra(int diasExtra) {
        this.diasExtra = diasExtra;
    }

    public int getDiasLibres() {
        return diasLibres;
    }

    public void setDiasLibres(int diasLibres) {
        this.diasLibres = diasLibres;
    }
    
}
