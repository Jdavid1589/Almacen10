
package Modelo;


public class Clientes {
    
    private int id;
    private String nombres;
    private String telefono;

    public Clientes() {
    }

    public Clientes(int id, String nombres, String telefono) {
        this.id = id;
        this.nombres = nombres;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
    
    
    
}
