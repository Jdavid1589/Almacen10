package Modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Compras {   //Compra

    private int idCompra; 
    private String fecha;   
    private int proveedorId; //kf
    private BigDecimal totalCompra;

 private List<ComprasProductos> articulos = new ArrayList<>(); // Inicializado

    
    @Override
    public String toString() {
        return "Compras{" + "idCompra=" + idCompra + ", fecha=" + fecha + ", proveedorId=" + proveedorId + ", totalCompra=" + totalCompra + ", articulos=" + articulos + '}';
    }
    
    
    
    
    

    public List<ComprasProductos> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ComprasProductos> articulos) {
        this.articulos = articulos;
    }
    
    
    public Compras() {
    }

    public Compras(int idCompra, String fecha,  int proveedorId, BigDecimal totalCompra) {
        this.idCompra = idCompra;
        this.fecha = fecha;
      
        this.proveedorId = proveedorId;
        this.totalCompra = totalCompra;
    }

    //Metodos Get y Set
    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    
    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    
    
  

}
