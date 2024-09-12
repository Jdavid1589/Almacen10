package Persistencia;

import Config.Conexion;
import Modelo.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*crear metodos*/

 /*adicionar*/
public class DaoUsuarios {

     static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    static Usuarios usuarios = new Usuarios();

    public static boolean grabar(Usuarios usuarios) {
        try {
               con = cn.getConnection();
            String sql = "INSERT INTO usuarios(nombres, usuario, clave, perfilId) "
                    + "VALUES(?,?,?,?);";
            ps = con.prepareStatement(sql);

            ps.setString(1, usuarios.getNombres());
            ps.setString(2, usuarios.getUsuario());
            ps.setString(3, usuarios.getClave());     
            ps.setInt(4, usuarios.getPerfilId());

            if (ps.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return false;
    }

    public static List<Usuarios> listar() {
        List<Usuarios> lista = new ArrayList<>();
        try {
          con = cn.getConnection();
            String sql = "SELECT * FROM usuarios;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuarios1 = new Usuarios();
                usuarios1.setIdUsuarios(rs.getInt("idUsuarios"));
                usuarios1.setNombres(rs.getString("nombres"));          
                usuarios1.setUsuario(rs.getString("usuario"));
                usuarios1.setClave(rs.getString("clave"));
                usuarios1.setPerfilId(rs.getInt("perfilId"));
                lista.add(usuarios1);
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(Usuarios usuarios) {
        try {
              con = cn.getConnection();
            String sql = "UPDATE usuarios SET nombres = ?, "
                    + "usuario =?, clave =?, "
                    + "perfilId =? WHERE idUsuarios =?";

            ps = con.prepareStatement(sql);
            ps.setString(1, usuarios.getNombres());
            ps.setString(2, usuarios.getUsuario());
            ps.setString(3, usuarios.getClave());          
            ps.setInt(4, usuarios.getPerfilId());
            ps.setInt(5, usuarios.getIdUsuarios());

            ps.executeUpdate();

            // Ejecutar la consulta de actualización
            int rowsAffected = ps.executeUpdate();

            // Verificar si se actualizó algún registro
            if (rowsAffected > 0) {
                return true; // La actualización fue exitosa

            }

        } catch (SQLException ex) {

        } finally {
            // Cerrar recursos (conexión, declaración, etc.) en el bloque "finally"
            // para garantizar que se cierren incluso si ocurre una excepción.
            cerrarRecursos();
        }
        return false;
    }

    public static boolean eliminar(int idUsuarios) {
        try {
               con = cn.getConnection();
            String sql = "DELETE FROM usuarios WHERE idUsuarios=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuarios);

            if (ps.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Maneja las excepciones de mejor manera, por ejemplo, lanzando una excepción personalizada.
        } finally {
            cerrarRecursos();
        }
        return false;
                    
    } 
    
        public static String obtenerNombreUsuarios(int id) {
        try {
               con = cn.getConnection();
            String sql = "SELECT nombres  FROM usuarios WHERE idUsuarios=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nombres");
            } else {
                return "--";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "--";
    }
    
    

    // Metodo Refactorizado para listar y editar 
    public static Usuarios obtenerUsuarioPorId(int id) {
        Usuarios usuario = null;

        String sql = "SELECT * FROM usuarios WHERE idUsuarios=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    usuario = new Usuarios();
                    usuario.setIdUsuarios(rs.getInt("idUsuarios"));
                    usuario.setNombres(rs.getString("nombres"));                 
                    usuario.setUsuario(rs.getString("usuario"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setPerfilId(rs.getInt("perfilId"));

                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoUsuarios.class
                    .getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);

        }

        return usuario;
    }

    public static List<Usuarios> buscarUsuarios(String texto) {
        List<Usuarios> listaUsuarios = new ArrayList<>();

        try {
              con = cn.getConnection();

            String sql = "SELECT * FROM usuarios "
                    + "WHERE nombres LIKE ?"
                    + " OR usuario LIKE ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Usuarios usuarios1 = new Usuarios();
                usuarios1.setIdUsuarios(rs.getInt("idUsuarios"));
                usuarios1.setNombres(rs.getString("nombres"));
                usuarios1.setUsuario(rs.getString("usuario"));
                usuarios1.setClave(rs.getString("clave"));               
                usuarios1.setPerfilId(rs.getInt("perfilId"));
                listaUsuarios.add(usuarios1);
            }
        } catch (SQLException e) {
            // Manejo adecuado de la excepción (log, imprimir, etc.)
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos (ResultSet, PreparedStatement, Connection)
            cerrarRecursos();
        }

        return listaUsuarios;
    }

    
    private static void cerrarRecursos() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     //metodo jasypt para encriptar contraseña
    public Usuarios validarUsuario(String usuario, String clave) {
        Usuarios user = null;
      //  Encriptador en = new Encriptador();
        String sql = "SELECT * FROM usuarios WHERE usuario=? AND clave=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);     
           // ps.setString(2, (en.desencriptar(clave)));
           ps.setString(2, clave);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new Usuarios();
                    user.setIdUsuarios(rs.getInt("idUsuarios"));             
                    user.setUsuario(rs.getString("usuario"));
                    user.setClave(rs.getString("clave")); // No es recomendable devolver la contraseña en el objeto Usuarios, pero lo mantengo por coherencia con tu código original
                    user.setPerfilId(rs.getInt("perfilId"));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            // Puedes agregar un mensaje de error adicional si es necesario
        } finally {
            cerrarRecursos(); // Asegúrate de cerrar los recursos de manera correcta
        }
        return user;

    }

    
}
