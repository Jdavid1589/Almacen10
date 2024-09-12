package Persistencia;

import Config.Conexion;
import Modelo.Categorias;
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
public class DaoCategorias {

   static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    static Categorias categorias = new Categorias();

    public static boolean grabar(Categorias categorias) {
        try {
           con = cn.getConnection();
            String sql = "INSERT INTO categorias (tipos,descripcion)"
                    + "VALUES(?,?);";
            ps = con.prepareStatement(sql);

            ps.setString(1, categorias.getTipos());
            ps.setString(2, categorias.getDescripcion());
       

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

    public static List<Categorias> listar() {
        List<Categorias> lista = new ArrayList<>();
        try {
         con = cn.getConnection();
            String sql = "SELECT * FROM categorias;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                Categorias categorias = new Categorias();

                categorias.setIdCategorias(rs.getInt("idCategorias"));
                categorias.setTipos(rs.getString("tipos"));
                categorias.setDescripcion(rs.getString("descripcion"));
             
                lista.add(categorias);
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(Categorias categorias) {
        try {
              con = cn.getConnection();
            String sql = "UPDATE categorias SET tipos = ?, "
                    + "descripcion =? WHERE idCategorias =?";

            ps = con.prepareStatement(sql);

            ps.setString(1, categorias.getTipos());
            ps.setString(2, categorias.getDescripcion());     
            ps.setInt(3, categorias.getIdCategorias());

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

    public static String obtenerNombreCategorias(int id) {
        try {
             con = cn.getConnection();
            String sql = "SELECT tipos  FROM categorias WHERE idCategorias=? ";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("tipos");
            } else {
                return "--";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoCategorias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "--";
    }

    public static boolean eliminar(int idCategorias) {
        try {
            con = cn.getConnection();
            String sql = "DELETE FROM categorias WHERE idCategorias=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idCategorias);

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
    public static Categorias obtenerCategoriasPorId(int id) {
        Categorias categorias = null;

        String sql = "SELECT * FROM categorias WHERE idCategorias=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    categorias = new Categorias();

                    categorias.setIdCategorias(rs.getInt("idCategorias"));
                    categorias.setTipos(rs.getString("tipos"));
                    categorias.setDescripcion(rs.getString("descripcion"));
                   

                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoCategorias.class
                    .getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);

        }

        return categorias;
    }

    public static List<Categorias> buscarCategorias(String texto) {
        List<Categorias> listaCategorias = new ArrayList<>();

        try {
              con = cn.getConnection();

            String sql = "SELECT * FROM categorias "
                    + "WHERE tipos LIKE ?"
                    + " OR descripcion LIKE ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Categorias categorias = new Categorias();

                categorias.setIdCategorias(rs.getInt("idCategorias"));
                categorias.setTipos(rs.getString("tipos"));
                categorias.setDescripcion(rs.getString("descripcion"));
              
                listaCategorias.add(categorias);

            }
        } catch (SQLException e) {
            // Manejo adecuado de la excepción (log, imprimir, etc.)
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos (ResultSet, PreparedStatement, Connection)
            cerrarRecursos();
        }

        return listaCategorias;
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
