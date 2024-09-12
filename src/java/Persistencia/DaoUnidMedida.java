package Persistencia;

import Config.Conexion;

import Modelo.UnidadMedida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoUnidMedida {

    static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;

    public static boolean grabar(UnidadMedida unidadMedida) {
        try {
            con = cn.getConnection();
            String sql = "INSERT INTO unidadmedida (unidadMedida) "
                    + "VALUES(?);";
            ps = con.prepareStatement(sql);

            ps.setString(1, unidadMedida.getUnidadMedida());

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

    public static List<UnidadMedida> listar() {
        List<UnidadMedida> lista = new ArrayList<>();
        try {
            con = cn.getConnection();
            String sql = "SELECT * FROM unidadmedida;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                UnidadMedida undMedida = new UnidadMedida();

                undMedida.setIdUnidadMedida(rs.getInt("idUnidadMedida"));
                undMedida.setUnidadMedida(rs.getString("unidadMedida"));

                lista.add(undMedida);
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    // Metodo Refactorizado para listar y editar 
    public static UnidadMedida obtenerUnidadPorId(int id) {
        UnidadMedida undMedida = null;
        String sql = "SELECT * FROM unidadmedida WHERE idUnidadMedida=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {  // Usamos `if` en lugar de `while` porque solo debería haber una fila
                    undMedida = new UnidadMedida();

                    undMedida.setIdUnidadMedida(rs.getInt("idUnidadMedida"));
                    undMedida.setUnidadMedida(rs.getString("unidadMedida"));

                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoUnidMedida.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return undMedida;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(UnidadMedida undMedida) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = cn.getConnection();
            String sql = "UPDATE unidadmedida SET unidadMedida = ? "                
                    + "WHERE idUnidadMedida = ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, undMedida.getUnidadMedida());         
            ps.setInt(2, undMedida.getIdUnidadMedida());

            int rowsAffected = ps.executeUpdate();

            // Verificar si se actualizó algún registro
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace(); // Imprimir la traza de la excepción para depuración
            return false;
        } finally {
            // Cerrar recursos en el bloque finally
            cerrarRecursos();

        }
    }

    public static String obtenerNombreUnidad(int id) {
        try {
            con = cn.getConnection();
            String sql = "SELECT unidadMedida  FROM unidadmedida WHERE idUnidadMedida=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("unidadMedida");
            } else {
                return "--";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "--";
    }

    public static boolean eliminar(int id) {
        try {
            con = cn.getConnection();
            String sql = "DELETE FROM unidadmedida WHERE idUnidadMedida=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);

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
