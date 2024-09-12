package Modelo;

public class Proveedores {

    private int idProveedor;
    private String proveedor;
    private String asesor;
    private String telefono;

    //Constructor Vacio
    public Proveedores() {
    }

    //Constructor con Datos
    public Proveedores(int idProveedor, String proveedor, String asesor, String telefono) {
        this.idProveedor = idProveedor;
        this.proveedor = proveedor;
        this.asesor = asesor;
        this.telefono = telefono;
    }

    //Metodos Get y Set
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
