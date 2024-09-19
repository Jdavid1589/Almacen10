package Modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Facturas {

    private int id;
    private String fecha;
    private int clienteId;
    private BigDecimal totalCosto;
    private BigDecimal totalIva;
    private BigDecimal totalVenta;
    private BigDecimal totalPrecioNeto;

    private  List<DetallesFacturas> facturas = new ArrayList<>(); // Inicializado

    public Facturas() {
    }

    public Facturas(int id, String fecha, int clienteId, BigDecimal totalCosto, BigDecimal totalIva, BigDecimal totalVenta, BigDecimal totalPrecioNeto) {
        this.id = id;
        this.fecha = fecha;
        this.clienteId = clienteId;
        this.totalCosto = totalCosto;
        this.totalIva = totalIva;
        this.totalVenta = totalVenta;
        this.totalPrecioNeto = totalPrecioNeto;
    }

    @Override
    public String toString() {
        return "Facturas{" + "id=" + id + ", fecha=" + fecha + ", clienteId=" + clienteId + ", totalCosto=" + totalCosto + ", totalIva=" + totalIva + ", totalVenta=" + totalVenta + ", totalPrecioNeto=" + totalPrecioNeto + ", facturas=" + facturas + '}';
    }

    

    public List<DetallesFacturas> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<DetallesFacturas> facturas) {
        this.facturas = facturas;
    }

    
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public BigDecimal getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(BigDecimal totalCosto) {
        this.totalCosto = totalCosto;
    }

    public BigDecimal getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(BigDecimal totalIva) {
        this.totalIva = totalIva;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getTotalPrecioNeto() {
        return totalPrecioNeto;
    }

    public void setTotalPrecioNeto(BigDecimal totalPrecioNeto) {
        this.totalPrecioNeto = totalPrecioNeto;
    }

  

}
