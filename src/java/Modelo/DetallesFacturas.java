
package Modelo;

import java.math.BigDecimal;

public class DetallesFacturas {    
    
    private int facturasId;
    private int productosId;
    private BigDecimal cantidad;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private int porcIva;

    
     // constructor Vacio
    public DetallesFacturas() {
    }

    
    
    
    // constructor lleno
    public DetallesFacturas(int facturasId, int productosId, BigDecimal cantidad, BigDecimal precioCompra, BigDecimal precioVenta, int porcIva) {
        this.facturasId = facturasId;
        this.productosId = productosId;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.porcIva = porcIva;
    }
    
    // Metodos Get y Set

    public int getFacturasId() {
        return facturasId;
    }

    public void setFacturasId(int facturasId) {
        this.facturasId = facturasId;
    }

    public int getProductosId() {
        return productosId;
    }

    public void setProductosId(int productosId) {
        this.productosId = productosId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getPorcIva() {
        return porcIva;
    }

    public void setPorcIva(int porcIva) {
        this.porcIva = porcIva;
    }
    
    
    
    

    
}
