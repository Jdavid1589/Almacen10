package Persistencia;

import Config.Conexion;
import Modelo.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoProductos {

    static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    static Productos productos = new Productos();

    public static boolean grabar(Productos productos) {
        try {
            con = cn.getConnection();
            String sql = "INSERT INTO productos (productos, plu, categoriasId, proveedoresId, unidadMedidaId,"
                    + "cantidadDisponible, precioCompra, precioVenta, porcIva, fechaActualizacion) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?);";
            ps = con.prepareStatement(sql);

            ps.setString(1, productos.getProductos());
            ps.setString(2, productos.getPlu());
            ps.setInt(3, productos.getCategoriasId());
            ps.setInt(4, productos.getProveedoresId());
            ps.setInt(5, productos.getUnidadMedidaId());
            ps.setDouble(6, productos.getCantidadDisponible());
            ps.setBigDecimal(7, productos.getPrecioCompra());
            ps.setBigDecimal(8, productos.getPrecioVenta());
            ps.setInt(9, productos.getPorcIva());
            ps.setString(10, productos.getFechaActualizacion());

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

    public static List<Productos> listar() {
        List<Productos> lista = new ArrayList<>();
        try {
            con = cn.getConnection();
            String sql = "SELECT * FROM productos;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {

                Productos productos1 = new Productos();

                productos1.setIdProductos(rs.getInt("idProductos"));
                productos1.setProductos(rs.getString("productos"));
                productos1.setPlu(rs.getString("plu"));
                productos1.setCategoriasId(rs.getInt("categoriasId"));
                productos1.setUnidadMedidaId(rs.getInt("unidadMedidaId"));
                productos1.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                productos1.setProveedoresId(rs.getInt("proveedoresId"));
                productos1.setPrecioCompra(rs.getBigDecimal("precioCompra"));
                productos1.setPrecioVenta(rs.getBigDecimal("precioVenta"));
                productos1.setPorcIva(rs.getInt("porcIva"));
                productos1.setFechaActualizacion(rs.getString("fechaActualizacion"));

                lista.add(productos1);
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    // Metodo Refactorizado para listar y editar 
    public static Productos obtenerProductosPorId(int id) {

        String sql = "SELECT * FROM productos WHERE idProductos=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {  // Usamos `if` en lugar de `while` porque solo debería haber una fila
                    //   Productos  productos1 = new Productos();

                    productos.setIdProductos(rs.getInt("idProductos"));
                    productos.setProductos(rs.getString("productos"));
                    productos.setPlu(rs.getString("plu"));
                    productos.setCategoriasId(rs.getInt("categoriasId"));
                    productos.setUnidadMedidaId(rs.getInt("unidadMedidaId"));
                    productos.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                    productos.setProveedoresId(rs.getInt("proveedoresId"));
                    productos.setPrecioCompra(rs.getBigDecimal("precioCompra"));
                    productos.setPrecioVenta(rs.getBigDecimal("precioVenta"));
                    productos.setPorcIva(rs.getInt("porcIva"));
                    productos.setFechaActualizacion(rs.getString("fechaActualizacion"));
                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoProductos.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return productos;
    }

    // Metodo para busqueda   
    public static Productos buscarProducto(int id) {

        String sql = "SELECT * FROM productos WHERE idProductos=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {  // Usamos `if` en lugar de `while` porque solo debería haber una fila
                    //   Productos  productos1 = new Productos();

                    productos.setIdProductos(rs.getInt("idProductos"));
                    productos.setProductos(rs.getString("productos"));
                    productos.setPlu(rs.getString("plu"));
                    productos.setCategoriasId(rs.getInt("categoriasId"));
                    productos.setUnidadMedidaId(rs.getInt("unidadMedidaId"));
                    productos.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                    productos.setProveedoresId(rs.getInt("proveedoresId"));
                    productos.setPrecioCompra(rs.getBigDecimal("precioCompra"));
                    productos.setPrecioVenta(rs.getBigDecimal("precioVenta"));
                    productos.setPorcIva(rs.getInt("porcIva"));
                    productos.setFechaActualizacion(rs.getString("fechaActualizacion"));
                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoProductos.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return productos;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(Productos productos) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = cn.getConnection();
            String sql = "UPDATE productos SET productos = ?, plu = ?, "
                    + "categoriasId = ?, proveedoresId = ?, unidadMedidaId = ?, "
                    + "cantidadDisponible = ?, precioCompra = ?, precioVenta  = ?, porcIva = ?, fechaActualizacion = ? "
                    + "WHERE idProductos = ?";

            ps = con.prepareStatement(sql);

            ps.setString(1, productos.getProductos());
            ps.setString(2, productos.getPlu());
            ps.setInt(3, productos.getCategoriasId());
            ps.setInt(4, productos.getProveedoresId());
            ps.setInt(5, productos.getUnidadMedidaId());
            ps.setDouble(6, productos.getCantidadDisponible());
            ps.setBigDecimal(7, productos.getPrecioCompra());
            ps.setBigDecimal(8, productos.getPrecioVenta());
            ps.setInt(9, productos.getPorcIva());
            ps.setString(10, productos.getFechaActualizacion());
            ps.setInt(11, productos.getIdProductos());

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

    public static String obtenerNombreProductos(int id) {
        try {
            con = cn.getConnection();
            String sql = "SELECT productos  FROM productos WHERE idProductos=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("productos");
            } else {
                return "--";
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "--";
    }

    public static boolean eliminar(int idProductos) {
        try {
            con = cn.getConnection();
            String sql = "DELETE FROM productos WHERE idProductos=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProductos);

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

    public static List<Productos> buscarProductos(String texto) {
        List<Productos> listaProductos = new ArrayList<>();

        try {
            con = cn.getConnection();

            String sql = "SELECT * FROM productos "
                    + "WHERE nombreProductos LIKE ?"
                    + " OR plu LIKE ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Productos productos1 = new Productos();

                productos1.setIdProductos(rs.getInt("idProductos"));
                productos1.setProductos(rs.getString("productos"));
                productos1.setPlu(rs.getString("plu"));
                productos1.setCategoriasId(rs.getInt("categoriasId"));
                productos1.setUnidadMedidaId(rs.getInt("unidadMedidaId"));
                productos1.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                productos1.setProveedoresId(rs.getInt("proveedoresId"));
                productos1.setPrecioCompra(rs.getBigDecimal("precioCompra"));
                productos1.setPrecioVenta(rs.getBigDecimal("precioVenta"));
                listaProductos.add(productos1);
            }
        } catch (SQLException e) {
            // Manejo adecuado de la excepción (log, imprimir, etc.)
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos (ResultSet, PreparedStatement, Connection)
            cerrarRecursos();
        }

        return listaProductos;
    }

    //**********************************************
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
