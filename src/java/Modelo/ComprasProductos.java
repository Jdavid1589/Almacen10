package Modelo;

import java.math.BigDecimal;

public class ComprasProductos {

    private int idCompraProducto;
    private int productosId;
    private int comprasId;
    private BigDecimal cantidad; //kf 
    private BigDecimal costoArticulo;
   // private int porcIva;

    public ComprasProductos() {
    }

    public ComprasProductos(int idCompraProducto, int productosId, int comprasId, BigDecimal cantidad, BigDecimal costoArticulo) {
        this.idCompraProducto = idCompraProducto;
        this.productosId = productosId;
        this.comprasId = comprasId;
        this.cantidad = cantidad;
        this.costoArticulo = costoArticulo;
    }

    @Override
    public String toString() {
        return "ComprasProductos{" + "idCompraProducto=" + idCompraProducto + ", productosId=" + productosId + ", comprasId=" + comprasId + ", cantidad=" + cantidad + ", costoArticulo=" + costoArticulo + '}';
    }

    

    
    public int getIdCompraProducto() {
        return idCompraProducto;
    }

    public void setIdCompraProducto(int idCompraProducto) {
        this.idCompraProducto = idCompraProducto;
    }

    public int getProductosId() {
        return productosId;
    }

    public void setProductosId(int productosId) {
        this.productosId = productosId;
    }

    public int getCompraId() {
        return comprasId;
    }

    public void setCompraId(int compraId) {
        this.comprasId = compraId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoArticulo() {
        return costoArticulo;
    }

    public void setCostoArticulo(BigDecimal costoArticulo) {
        this.costoArticulo = costoArticulo;
    }

    
    
    
    
    
    

}
