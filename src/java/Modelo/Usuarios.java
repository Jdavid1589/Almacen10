package Modelo;

public class Usuarios {

    private int idUsuarios;
    private String nombres;
    private String usuario;
    private String clave;
    private int perfilId;

    public Usuarios() {
    }

    public Usuarios(int idUsuarios, String nombres, String usuario, String clave, int perfilId) {
        this.idUsuarios = idUsuarios;
        this.nombres = nombres;
        this.usuario = usuario;
        this.clave = clave;
        this.perfilId = perfilId;
    }

    
    
    public int getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(int idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }

    

   

}
