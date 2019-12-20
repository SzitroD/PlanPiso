package modelo;

public class VistaCompras {
    
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
    private String prioridadPago;
    private String reportadoNVDR;
    private String observaciones;
    private int diasRealesFinanciamiento;
    private double interesReal;
    private double interesExtraReal;
    private String statusFinanciamiento;
    
    private int idBanco;
    private String fechaFinanciamiento;
    private double cf;
    
    private String nombreBanco;
    private int diasFinanciamiento;
    private double interes;
    private int diasExtra;
    private double interesExtra;
    private int diasLibres;
    private int statusBanco;
    private double linea;
    
    private double totalCF;
    private int totalVehiculos;
    private double totalImporteNeto;
    private double totalInteres;
    private double totalPago;
            
    private int dias;
    private double tasa;
    private double interesVehiculo;
    private int diasSeguro;
    
    public VistaCompras() {
        this.totalInteres = 0;
        this.totalPago = 0;
        this.dias = 0;
        this.tasa = 0;
        this.interesVehiculo = 0;
        this.totalImporteNeto = 0;
        this.totalVehiculos=0;
        this.totalCF=0;
        this.interesReal = 0;
        this.statusFinanciamiento = "";
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
        this.prioridadPago = "";
        this.reportadoNVDR = "";
        this.observaciones = "";
        this.idBanco = 0;
        this.fechaFinanciamiento = "";
        this.nombreBanco = "";
        this.diasFinanciamiento = 0;
        this.interes = 0;
        this.diasExtra = 0;
        this.interesExtra = 0;
        this.diasLibres = 0;
        this.diasRealesFinanciamiento = 0;
        this.cf = 0;
        this.statusBanco = 0;
        this.interesExtraReal = 0;
        this.diasSeguro = 0;
        this.linea = 0;
    }
    
    public VistaCompras(double linea,int diasSeguro,double interesExtraReal,double totalInteres,double totalPago,int dias,double tasa,double interesVehiculo,double totalImporteNeto, double totalCF,int totalVehiculos,double interesReal, String statusFinanciamiento,int statusBanco,double cf,int diasRealesFinanciamiento, String fechaCompra, String marca, String vin, String serie, double valorFactura, double importeNeto, String situacion, String ubicacion, String carteraFinanciera, String status, String factura, String cliente, String idTipoVenta, String tipoVenta, String fechaFactura, double valorVenta, double pagoCliente, double saldo, String db, String fechaCarga, String prioridadPago, String reportadoNVDR, String observaciones, int idBanco, String fechaFinanciamiento, String nombreBanco, int diasFinanciamiento, double interes, int diasExtra, double interesExtra, int diasLibres) {
        
        this.linea = linea;
        this.interesExtraReal = interesExtraReal;
        this.totalInteres = totalInteres;
        this.totalPago = totalPago;
        this.dias = dias;
        this.tasa = tasa;
        this.interesVehiculo = interesVehiculo;
        this.totalImporteNeto= totalImporteNeto;
        this.totalVehiculos=totalVehiculos;
        this.totalCF=totalCF;
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
        this.prioridadPago = prioridadPago;
        this.reportadoNVDR = reportadoNVDR;
        this.observaciones = observaciones;
        this.idBanco = idBanco;
        this.fechaFinanciamiento = fechaFinanciamiento;
        this.nombreBanco = nombreBanco;
        this.diasFinanciamiento = diasFinanciamiento;
        this.interes = interes;
        this.diasExtra = diasExtra;
        this.interesExtra = interesExtra;
        this.diasLibres = diasLibres;
        this.diasRealesFinanciamiento = diasRealesFinanciamiento;
        this.cf = cf;
        this.statusBanco = statusBanco;
        this.statusFinanciamiento = statusFinanciamiento;
        this.interesReal = interesReal;
        this.diasSeguro = diasSeguro;
    }

    public double getLinea() {
        return linea;
    }

    public void setLinea(double linea) {
        this.linea = linea;
    }

    public int getDiasSeguro() {
        return diasSeguro;
    }

    public void setDiasSeguro(int diasSeguro) {
        this.diasSeguro = diasSeguro;
    }

    public double getInteresExtraReal() {
        return interesExtraReal;
    }

    public void setInteresExtraReal(double interesExtraReal) {
        this.interesExtraReal = interesExtraReal;
    }

    
    public double getTotalInteres() {
        return totalInteres;
    }

    public void setTotalInteres(double totalInteres) {
        this.totalInteres = totalInteres;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public double getInteresVehiculo() {
        return interesVehiculo;
    }

    public void setInteresVehiculo(double interesVehiculo) {
        this.interesVehiculo = interesVehiculo;
    }

    public double getTotalImporteNeto() {
        return totalImporteNeto;
    }

    public void setTotalImporteNeto(double totalImporteNeto) {
        this.totalImporteNeto = totalImporteNeto;
    }

    public double getTotalCF() {
        return totalCF;
    }

    public void setTotalCF(double totalCF) {
        this.totalCF = totalCF;
    }

    public int getTotalVehiculos() {
        return totalVehiculos;
    }

    public void setTotalVehiculos(int totalVehiculos) {
        this.totalVehiculos = totalVehiculos;
    }

    public double getInteresReal() {
        return interesReal;
    }

    public void setInteresReal(double interesReal) {
        this.interesReal = interesReal;
    }

    public String getStatusFinanciamiento() {
        return statusFinanciamiento;
    }

    public void setStatusFinanciamiento(String statusFinanciamiento) {
        this.statusFinanciamiento = statusFinanciamiento;
    }
   
    public int getStatusBanco() {
        return statusBanco;
    }

    public void setStatusBanco(int statusBanco) {
        this.statusBanco = statusBanco;
    }

    public double getCf() {
        return cf;
    }

    public void setCf(double cf) {
        this.cf = cf;
    }
    
    public int getDiasRealesFinanciamiento() {
        return diasRealesFinanciamiento;
    }

    public void setDiasRealesFinanciamiento(int diasRealesFinanciamiento) {
        this.diasRealesFinanciamiento = diasRealesFinanciamiento;
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

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public String getFechaFinanciamiento() {
        return fechaFinanciamiento;
    }

    public void setFechaFinanciamiento(String fechaFinanciamiento) {
        this.fechaFinanciamiento = fechaFinanciamiento;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public int getDiasFinanciamiento() {
        return diasFinanciamiento;
    }

    public void setDiasFinanciamiento(int diasFinanciamiento) {
        this.diasFinanciamiento = diasFinanciamiento;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public int getDiasExtra() {
        return diasExtra;
    }

    public void setDiasExtra(int diasExtra) {
        this.diasExtra = diasExtra;
    }

    public double getInteresExtra() {
        return interesExtra;
    }

    public void setInteresExtra(double interesExtra) {
        this.interesExtra = interesExtra;
    }

    public int getDiasLibres() {
        return diasLibres;
    }

    public void setDiasLibres(int diasLibres) {
        this.diasLibres = diasLibres;
    }
    
    
}
