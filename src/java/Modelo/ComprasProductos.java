package Modelo;

import java.math.BigDecimal;

public class ComprasProductos {

    private int idCompraProducto;
    private int productosId;
    private int comprasId;
    private int porcIva; //Nuevo
    private BigDecimal cantidad; 
    private BigDecimal costoArticulo;
 

    public int getComprasId() {
        return comprasId;
    }

    public void setComprasId(int comprasId) {
        this.comprasId = comprasId;
    }

    public int getPorcIva() {
        return porcIva;
    }

    public void setPorcIva(int porcIva) {
        this.porcIva = porcIva;
    }

    
    
    
    
    @Override
    public String toString() {
        return "ComprasProductos{" + "idCompraProducto=" + idCompraProducto + ", productosId=" + productosId + ", comprasId=" + comprasId + ", porcIva=" + porcIva + ", cantidad=" + cantidad + ", costoArticulo=" + costoArticulo + '}';
    }

    
    
    
    
    public ComprasProductos() {
    }

    public ComprasProductos(int idCompraProducto, int productosId, int comprasId, int porcIva, BigDecimal cantidad, BigDecimal costoArticulo) {
        this.idCompraProducto = idCompraProducto;
        this.productosId = productosId;
        this.comprasId = comprasId;
        this.porcIva = porcIva;
        this.cantidad = cantidad;
        this.costoArticulo = costoArticulo;
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
