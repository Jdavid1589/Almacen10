package Persistencia;

import Config.Conexion;
import Modelo.Facturas;
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
public class DaoFacturas {

    static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    static Facturas facturas = new Facturas();

    public static boolean grabar(Facturas facturas) {
        try {
            con = cn.getConnection();
            String sql = "INSERT INTO facturas(numeroOrden , fechaEntrada , proveedores_idProveedores, "
                    + "usuarios_idUsuarios) "
                    + "VALUES(?,?,?,?);";
            ps = con.prepareStatement(sql);

          /*  ps.setInt(1, facturas.getNumeroOrden());
            ps.setString(2, facturas.getFechaEntrada());
            ps.setInt(4, facturas.getProveedores_idProveedores());
            ps.setInt(3, facturas.getUsuarios_idUsuarios());
*/
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

    public static List<Facturas> listar() {
        List<Facturas> lista = new ArrayList<>();
        try {
            con = cn.getConnection();
            String sql = "SELECT * FROM facturas;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                Facturas facturas1 = new Facturas();
/*
                facturas1.setIdFacturas(rs.getInt("idFacturas"));
                facturas1.setNumeroOrden(rs.getInt("numeroOrden"));
                facturas1.setFechaEntrada(rs.getString("fechaEntrada"));
                facturas1.setUsuarios_idUsuarios(rs.getInt("usuarios_idUsuarios"));
                facturas1.setProveedores_idProveedores(rs.getInt("proveedores_idProveedores"));*/
                lista.add(facturas1);
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(Facturas facturas) {
        try {
            con = cn.getConnection();
            String sql = "UPDATE facturas SET numeroOrden = ?, "
                    + "fechaEntrada =?,proveedores_idProveedores =?, usuarios_idUsuarios =?  WHERE idFacturas =?";

         /*   ps = con.prepareStatement(sql);
            ps.setInt(1, facturas.getNumeroOrden());
            ps.setString(2, facturas.getFechaEntrada());
            ps.setInt(3, facturas.getProveedores_idProveedores());
            ps.setInt(4, facturas.getUsuarios_idUsuarios());*/
            ps.setInt(5, facturas.getIdFacturas());

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

    public static String obtenerNombreFacturas(int id) {
        try {
            con = cn.getConnection();
            String sql = "SELECT nombreFacturas FROM facturas  WHERE idFacturas=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nombreFacturas");
            } else {
                return "--";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "--";
    }

    public static boolean eliminar(int idFacturas) {
        try {
            con = cn.getConnection();
            String sql = "DELETE FROM facturas WHERE idFacturas=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idFacturas);

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
    public static Facturas obtenerFacturasPorId(int id) {
        Facturas facturas = null;

        String sql = "SELECT * FROM facturas WHERE idFacturas=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    facturas = new Facturas();

              /*      facturas.setIdFacturas(rs.getInt("idfacturas"));
                    facturas.setNumeroOrden(rs.getInt("numeroOrden"));
                    facturas.setFechaEntrada(rs.getString("fechaEntrada"));
                    facturas.setProveedores_idProveedores(rs.getInt("proveedores_idProveedores"));
                    facturas.setUsuarios_idUsuarios(rs.getInt("Usuarios_idUsuarios"));*/

                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoFacturas.class
                    .getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);

        }

        return facturas;
    }

    public static List<Facturas> buscarFacturas(String texto) {
        List<Facturas> listaFacturas = new ArrayList<>();

        try {
            con = cn.getConnection();

            String sql = "SELECT * FROM facturas "
                    + "WHERE nombres LIKE ?"
                    + " OR facturas LIKE ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Facturas facturas1 = new Facturas();
/*
                facturas1.setIdFacturas(rs.getInt("idFacturas"));
                facturas1.setNumeroOrden(rs.getInt("NumeroOrden"));
                facturas1.setFechaEntrada(rs.getString("FechaEntrada"));
                facturas1.setProveedores_idProveedores(rs.getInt("Proveedores_idProveedores"));
                facturas1.setUsuarios_idUsuarios(rs.getInt("Usuarios_idUsuarios"));*/

                listaFacturas.add(facturas1);

            }
        } catch (SQLException e) {
            // Manejo adecuado de la excepción (log, imprimir, etc.)
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos (ResultSet, PreparedStatement, Connection)
            cerrarRecursos();
        }

        return listaFacturas;
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
