package Persistencia;

import Config.Conexion;
import Modelo.Documento;
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
public class DaoDocumento {


     static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    static Documento documento = new Documento();

    public static boolean grabar(Documento documento) {
        try {
            con = cn.getConnection();
            String sql = "INSERT INTO documento(idDocumento,tipoDocumento,usuarios_idUsuarios, "
                    + "VALUES(?,?,?);";
            ps = con.prepareStatement(sql);

            ps.setInt(1, documento.getIdDocumento());
            ps.setString(2, documento.getTipoDocumento());
            ps.setInt(3, documento.getUsuarios_idUsuarios());

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

    public static List<Documento> listar() {
        List<Documento> lista = new ArrayList<>();
        try {
                con = cn.getConnection();
            String sql = "SELECT * FROM documento;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                Documento documento = new Documento();

                documento.setIdDocumento(rs.getInt("idDocumento"));
                documento.setTipoDocumento(rs.getString("tipoDocumento"));
             

                lista.add(documento);
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(Documento documento) {
        try {
                con = cn.getConnection();
            String sql = "UPDATE Documento SET idDocumento = ?, "
                    + "tipoDocumento =?, usuarios_idUsuarios =?, "
                    + " WHERE idDocumento =?";

            ps = con.prepareStatement(sql);
            ps.setInt (1, documento.getIdDocumento());
            ps.setString(2, documento.getTipoDocumento());
            ps.setInt(3, documento.getUsuarios_idUsuarios());
          
           
            

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
    
     public static String obtenerNombreDocumento(int id) {
        try {
            con = cn.getConnection();
            String sql = "SELECT tipoDocumento  FROM documento WHERE idDocumento=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("tipoDocumento");
            } else {
                return "--";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "--";
    }
    
    
    
    

    public static boolean eliminar(int idDocumento) {
        try {
               con = cn.getConnection();
            String sql = "DELETE FROM documento WHERE idDocumento=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idDocumento);

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

    // Metodo Refactorizado para listar y editar 
    public static Documento obtenerDocumentoPorId(int id) {
        Documento documento = null;

        String sql = "SELECT * FROM documento WHERE idDocumento=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    documento = new Documento();
                    documento.setIdDocumento(rs.getInt("idUsuarios"));
                    documento.setTipoDocumento(rs.getString("nombres"));
                    documento.setUsuarios_idUsuarios(rs.getInt("correo"));
                   

                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoDocumento.class
                    .getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);

        }

        return documento;
    }

    public static List<Documento> buscarDocumento(String texto) {
        List<Documento> listaDocumento = new ArrayList<>();

        try {
         con = cn.getConnection();

            String sql = "SELECT * FROM documento "
                    + "WHERE tipoDocumento LIKE ?"
                    + " OR documento LIKE ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
             Documento documento = new Documento();

                documento.setIdDocumento(rs.getInt("iddocumento"));
                documento.setTipoDocumento(rs.getString("tipodocumento"));
                documento.setUsuarios_idUsuarios(rs.getInt("usuarios"));

                listaDocumento.add(documento);
            }
        } catch (SQLException e) {
            // Manejo adecuado de la excepción (log, imprimir, etc.)
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos (ResultSet, PreparedStatement, Connection)
            cerrarRecursos();
        }

        return listaDocumento;
    }
    
    
    
    //**********************************************
    //metodo jasypt para encriptar contraseña
    //Nota:Se debe manejar el mismo orden tanto en l asentencia Sql como en el ps
    // Agrega este método para cerrar las conexiones y recursos
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

}
