package Modelo;

import java.math.BigDecimal;


// Aca se utiliza como Bodega
public class Productos {

    private int idProductos;
    private String productos;
    private String plu;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private int categoriasId;
    private int proveedoresId;
    private int unidadMedidaId;
    private double cantidadDisponible;
    
    
    

    public Productos() {
    }

    public Productos(int idProductos, String productos, String plu, BigDecimal precioCompra, BigDecimal precioVenta, int categoriasId, int proveedoresId, int unidadMedidaId, double cantidadDisponible) {
        this.idProductos = idProductos;
        this.productos = productos;
        this.plu = plu;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.categoriasId = categoriasId;
        this.proveedoresId = proveedoresId;
        this.unidadMedidaId = unidadMedidaId;
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(int idProductos) {
        this.idProductos = idProductos;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public String getPlu() {
        return plu;
    }

    public void setPlu(String plu) {
        this.plu = plu;
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

    public int getCategoriasId() {
        return categoriasId;
    }

    public void setCategoriasId(int categoriasId) {
        this.categoriasId = categoriasId;
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

    public double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

}
