
package Modelo;


public class Movimientos {
    
     private int idMovimientos;
    private String fecha;
    private int productos_idProductos;
    private int recibo_idRecibo;
     private String nota; //Modificado
    private Double cantidad;
    private Double costo;

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
    
    
    

    public int getRecibo_idRecibo() {
        return recibo_idRecibo;
    }

    public void setRecibo_idRecibo(int recibo_idRecibo) {
        this.recibo_idRecibo = recibo_idRecibo;
    }  
  
    
    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
    
 
 
    public int getIdMovimientos() {
        return idMovimientos;
    }

    public void setIdMovimientos(int idMovimientos) {
        this.idMovimientos = idMovimientos;
    }

    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public int getProductos_idProductos() {
        return productos_idProductos;
    }

    public void setProductos_idProductos(int productos_idProductos) {
        this.productos_idProductos = productos_idProductos;
    }

}