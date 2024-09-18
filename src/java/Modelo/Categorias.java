package Modelo;

public class Categorias {

    private int idCategorias;
    private String tipos;
    private String descripcion;

    
    
    //Counstructor Vacio 
    public Categorias() {
    }

           //Counstructor Con datos 
    public Categorias(int idCategorias, String tipos, String descripcion) {
        this.idCategorias = idCategorias;
        this.tipos = tipos;
        this.descripcion = descripcion;
    }
  
    
    //Metodos Get y Set
    public int getIdCategorias() {
        return idCategorias;
    }

    public void setIdCategorias(int idCategorias) {
        this.idCategorias = idCategorias;
    }

    public String getTipos() {
        return tipos;
    }

    public void setTipos(String tipos) {
        this.tipos = tipos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    


   

}
