package Modelo;

import java.math.BigDecimal;

public class Facturas {

    private int idFacturas;
    private String fecha;
    private int usuariosId;
    private int proveedoresId;
    private int unidadMedidaId;
    private BigDecimal totalCosto;
    private BigDecimal totalIva;
    private BigDecimal totalVenta;

    public Facturas() {
    }

    
    
    public Facturas(int idFacturas, String fecha, int usuariosId, int proveedoresId, int unidadMedidaId, BigDecimal totalCosto, BigDecimal totalIva, BigDecimal totalVenta) {
        this.idFacturas = idFacturas;
        this.fecha = fecha;
        this.usuariosId = usuariosId;
        this.proveedoresId = proveedoresId;
        this.unidadMedidaId = unidadMedidaId;
        this.totalCosto = totalCosto;
        this.totalIva = totalIva;
        this.totalVenta = totalVenta;
    }

    
    
    
    
    
    
    
    public int getIdFacturas() {
        return idFacturas;
    }

    public void setIdFacturas(int idFacturas) {
        this.idFacturas = idFacturas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(int usuariosId) {
        this.usuariosId = usuariosId;
    }

    public int getProveedoresId() {
        return proveedoresId;
    }

    public void setProveedoresId(int proveedoresId) {
        this.proveedoresId = proveedoresId;
    }

    public int getUnidadMedidaId() {
        return unidadMedidaId;
    }

    public void setUnidadMedidaId(int unidadMedidaId) {
        this.unidadMedidaId = unidadMedidaId;
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

}
