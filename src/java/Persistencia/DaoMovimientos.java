package Persistencia;

import Config.Conexion;
import Modelo.Movimientos;
import Modelo.Productos;
import Modelo.Proveedores;
import static Persistencia.DaoProductos.cn;
import static Persistencia.DaoProductos.productos;
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
public class DaoMovimientos {

    static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    static Movimientos movimientos = new Movimientos();

    public static boolean grabar(Movimientos movimientos) {
        try {
            con = cn.getConnection();
            String sql = "INSERT INTO movimientos(nota, fecha, cantidad, productos_idProductos, recibo_idRecibo, costo) "
                    + "VALUES(?,?,?,?,?,?);";

            ps = con.prepareStatement(sql);

            ps.setString(1, movimientos.getNota());
            ps.setString(2, movimientos.getFecha());
            ps.setDouble(3, movimientos.getCantidad());
            ps.setInt(4, movimientos.getProductos_idProductos());
            ps.setInt(5, movimientos.getRecibo_idRecibo());
            ps.setDouble(6, movimientos.getCosto());

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

    public static List<Movimientos> listar() {
        List<Movimientos> lista = new ArrayList<>();
        try {
            con = cn.getConnection();
            String sql = "SELECT * FROM movimientos;";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Movimientos movimientos1 = new Movimientos();

                movimientos1.setIdMovimientos(rs.getInt("idMovimientos"));
                movimientos1.setNota(rs.getString("nota"));
                movimientos1.setFecha(rs.getString("fecha"));
                movimientos1.setCantidad(rs.getDouble("cantidad"));
                movimientos1.setCosto(rs.getDouble("costo"));
                movimientos1.setProductos_idProductos(rs.getInt("productos_idProductos"));
                movimientos1.setRecibo_idRecibo(rs.getInt("recibo_idRecibo"));
                lista.add(movimientos1);
            }
        } catch (Exception e) {
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    //Se debe asegurar el mismo orden
    public static boolean editar(Movimientos movimientos) {
        try {
            con = cn.getConnection();
            String sql = "UPDATE movimientos SET nota =?,fecha = ?, "
                    + " cantidad =?, productos_idProductos =?, costo=?, recibo_idRecibo=?"
                    + " WHERE idMovimientos =?";

            ps = con.prepareStatement(sql);

            ps.setString(1, movimientos.getNota());
            ps.setString(2, movimientos.getFecha());
            ps.setDouble(3, movimientos.getCantidad());
            ps.setInt(4, movimientos.getProductos_idProductos());
            ps.setDouble(5, movimientos.getCosto());
            ps.setInt(6, movimientos.getRecibo_idRecibo());
            ps.setInt(7, movimientos.getIdMovimientos());

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

    public static boolean eliminar(int idMovimientos) {
        try {
            con = cn.getConnection();
            String sql = "DELETE FROM movimientos WHERE idMovimientos=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idMovimientos);

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
    public static Movimientos obtenerMovimientosPorId2(int id) {
        Movimientos movimientos1 = null;

        String sql = "SELECT * FROM movimientos WHERE idMovimientos=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    movimientos1 = new Movimientos();

                    movimientos1.setIdMovimientos(rs.getInt("idMovimientos"));
                    movimientos1.setNota(rs.getString("nota"));
                    movimientos1.setFecha(rs.getString("fecha"));
                    movimientos1.setCantidad(rs.getDouble("cantidad"));
                    movimientos1.setCosto(rs.getDouble("costo"));
                    movimientos1.setProductos_idProductos(rs.getInt("productos_idProductos"));
                    movimientos1.setRecibo_idRecibo(rs.getInt("recibo_idRecibo"));

                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoMovimientos.class
                    .getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);

        }

        return movimientos;
    }

    // Metodo Refactorizado para listar y editar 
    public static Movimientos obtenerMovimientosPorId(int id) {
        Movimientos movimientos1 = null;
        String sql = "SELECT * FROM movimientos WHERE idMovimientos=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {  // Usamos `if` en lugar de `while` porque solo debería haber una fila
                    movimientos1 = new Movimientos();

                    movimientos1.setIdMovimientos(rs.getInt("idMovimientos"));
                    movimientos1.setNota(rs.getString("nota"));
                    movimientos1.setFecha(rs.getString("fecha"));
                    movimientos1.setCantidad(rs.getDouble("cantidad"));
                    movimientos1.setCosto(rs.getDouble("costo"));
                    movimientos1.setProductos_idProductos(rs.getInt("productos_idProductos"));
                    movimientos1.setRecibo_idRecibo(rs.getInt("recibo_idRecibo"));
                }
            }
        } catch (SQLException ex) {
            // Registra el error sin mostrar detalles al usuario
            Logger.getLogger(DaoMovimientos.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return movimientos1;
    }

    public static Productos buscarProducto(int id) {
        Productos producto = null; // Inicializamos como null
        String sql = "SELECT * FROM productos WHERE idProductos = ?"; // Usar consulta parametrizada

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); // Asignar el parámetro PLU

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = new Productos(); // Solo crear si hay un resultado
                    producto.setIdProductos(rs.getInt("idProductos"));
                    producto.setPorcIva(rs.getInt("porcIva"));
                    producto.setProductos(rs.getString("productos"));
                    producto.setFechaActualizacion(rs.getString("fechaActualizacion"));
                    producto.setPlu(rs.getString("plu"));
                    producto.setCategoriasId(rs.getInt("categoriasId"));
                    producto.setUnidadMedidaId(rs.getInt("unidadMedidaId"));
                    producto.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                    producto.setProveedoresId(rs.getInt("proveedoresId"));
                    producto.setPrecioCompra(rs.getBigDecimal("precioCompra"));
                    producto.setPrecioVenta(rs.getBigDecimal("precioVenta"));
                    // Rellenar otros campos necesarios
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoMovimientos.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return producto; // Retornar el producto encontrado o null
    }

    public static Proveedores buscarProveedor(int id) {
        Proveedores proveedores = null; // Inicializamos como null
        String sql = "SELECT * FROM proveedores WHERE idProveedor = ?"; // Usar consulta parametrizada

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); // Asignar el parámetro PLU

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    proveedores = new Proveedores(); // Solo crear si hay un resultado
                    proveedores.setIdProveedor(rs.getInt("idProveedor")); // Usar nombres de columna explícitos
                    proveedores.setAsesor(rs.getString("asesor"));
                    proveedores.setProveedor(rs.getString("proveedor"));

                    // Rellenar otros campos necesarios
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoMovimientos.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return proveedores; // Retornar el producto encontrado o null
    }

    
    
    
    
    
    // Metodo para busqueda   
    public static List<Productos> buscarProducto2(int id) {
        List<Productos> listaProductos = new ArrayList<>();

        String sql = "SELECT * FROM productos WHERE idProductos=?";

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {  // Si solo esperas un resultado
                    Productos producto = new Productos();
                   producto.setIdProductos(rs.getInt("idProductos"));
                    producto.setPorcIva(rs.getInt("porcIva"));
                    producto.setProductos(rs.getString("productos"));
                    producto.setFechaActualizacion(rs.getString("fechaActualizacion"));
                    producto.setPlu(rs.getString("plu"));
                    producto.setCategoriasId(rs.getInt("categoriasId"));
                    producto.setUnidadMedidaId(rs.getInt("unidadMedidaId"));
                    producto.setCantidadDisponible(rs.getDouble("cantidadDisponible"));
                    producto.setProveedoresId(rs.getInt("proveedoresId"));
                    producto.setPrecioCompra(rs.getBigDecimal("precioCompra"));
                    producto.setPrecioVenta(rs.getBigDecimal("precioVenta"));

                    // Agregar el producto a la lista
                    listaProductos.add(producto);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoProductos.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return listaProductos; // Devolvemos la lista de productos
    }

    public static List<Movimientos> buscarMovimientos(String texto) {
        List<Movimientos> listaMovimientos = new ArrayList<>();

        try {
            con = cn.getConnection();

            String sql = "SELECT * FROM Movimientos "
                    + "WHERE nombre LIKE ?"
                    + " OR fecha  LIKE ?";

            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                Movimientos movimientos1 = new Movimientos();

                movimientos1.setIdMovimientos(rs.getInt("idMovimientos"));
                movimientos1.setNota(rs.getString("nota"));
                movimientos1.setFecha(rs.getString("fecha"));
                movimientos1.setCantidad(rs.getDouble("cantidad"));
                movimientos1.setCosto(rs.getDouble("costo"));
                movimientos1.setProductos_idProductos(rs.getInt("productos_idProductos"));
                movimientos1.setIdMovimientos(rs.getInt("recibo_idRecibo"));

                listaMovimientos.add(movimientos1);
            }
        } catch (SQLException e) {
            // Manejo adecuado de la excepción (log, imprimir, etc.)
            e.printStackTrace();
        } finally {
            // Asegúrate de cerrar los recursos (ResultSet, PreparedStatement, Connection)
            cerrarRecursos();
        }

        return listaMovimientos;
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
