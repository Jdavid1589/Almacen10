package Modelo;

public class Bodega {

    private int idBodega;
    private int productos_idProductos;
    private Double cantidadInventario;
    private String unidadMedida;
    private Double costo;
    private int movimientos_Id; //Nuevo

    public int getMovimientos_Id() {
        return movimientos_Id;
    }

    public void setMovimientos_Id(int movimientos_Id) {
        this.movimientos_Id = movimientos_Id;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public Double getCantidadInventario() {
        return cantidadInventario;
    }

    public void setCantidadInventario(Double cantidadInventario) {
        this.cantidadInventario = cantidadInventario;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public int getProductos_idProductos() {
        return productos_idProductos;
    }

    public void setProductos_idProductos(int productos_idProductos) {
        this.productos_idProductos = productos_idProductos;
    }
   

}
