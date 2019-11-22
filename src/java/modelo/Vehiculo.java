package modelo;

public class Vehiculo {

    private String fechaCompra;
    private String marca;
    private String vin;
    private String serie;
    private double valorFactura;
    private double importeNeto;
    private String situacion;
    private String ubicacion;
    private String carteraFinanciera;
    private String status;
    private String factura;
    private String cliente;
    private String idTipoVenta;
    private String tipoVenta;
    private String fechaFactura;
    private double valorVenta;
    private double pagoCliente;
    private double saldo;
    private String db;
    private String fechaCarga;
    private String statusFactura;

    public Vehiculo() {
        this.fechaCompra = "";
        this.marca = "";
        this.vin = "";
        this.serie = "";
        this.valorFactura = 0;
        this.importeNeto = 0;
        this.situacion = "";
        this.ubicacion = "";
        this.carteraFinanciera = "";
        this.status = "";
        this.factura = "";
        this.cliente = "";
        this.idTipoVenta = "";
        this.tipoVenta = "";
        this.fechaFactura = "";
        this.valorVenta = 0;
        this.pagoCliente = 0;
        this.saldo = 0;
        this.db = "";
        this.fechaCarga = "";
        this.statusFactura = "";
    }

    public Vehiculo(String statusFactura,String fechaCompra, String marca, String vin, String serie, double valorFactura, double importeNeto, String situacion, String ubicacion, String carteraFinanciera, String status, String factura, String cliente, String idTipoVenta, String tipoVenta, String fechaFactura, double valorVenta, double pagoCliente, double saldo, String db, String fechaCarga) {
        this.fechaCompra = fechaCompra;
        this.marca = marca;
        this.vin = vin;
        this.serie = serie;
        this.valorFactura = valorFactura;
        this.importeNeto = importeNeto;
        this.situacion = situacion;
        this.ubicacion = ubicacion;
        this.carteraFinanciera = carteraFinanciera;
        this.status = status;
        this.factura = factura;
        this.cliente = cliente;
        this.idTipoVenta = idTipoVenta;
        this.tipoVenta = tipoVenta;
        this.fechaFactura = fechaFactura;
        this.valorVenta = valorVenta;
        this.pagoCliente = pagoCliente;
        this.saldo = saldo;
        this.db = db;
        this.fechaCarga = fechaCarga;
        this.statusFactura = statusFactura;
    }

    public String getStatusFactura() {
        return statusFactura;
    }

    public void setStatusFactura(String statusFactura) {
        this.statusFactura = statusFactura;
    }

    
    
    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public double getValorFactura() {
        return valorFactura;
    }

    public void setValorFactura(double valorFactura) {
        this.valorFactura = valorFactura;
    }

    public double getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(double importeNeto) {
        this.importeNeto = importeNeto;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCarteraFinanciera() {
        return carteraFinanciera;
    }

    public void setCarteraFinanciera(String carteraFinanciera) {
        this.carteraFinanciera = carteraFinanciera;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getIdTipoVenta() {
        return idTipoVenta;
    }

    public void setIdTipoVenta(String idTipoVenta) {
        this.idTipoVenta = idTipoVenta;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public double getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(double valorVenta) {
        this.valorVenta = valorVenta;
    }

    public double getPagoCliente() {
        return pagoCliente;
    }

    public void setPagoCliente(double pagoCliente) {
        this.pagoCliente = pagoCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

}
