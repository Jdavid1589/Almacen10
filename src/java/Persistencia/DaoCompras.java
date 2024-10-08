package Persistencia;

import Config.Conexion;
import Modelo.Compras;
import Modelo.ComprasProductos;
import Modelo.Productos;
import Modelo.Proveedores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoCompras {

    static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;
    //static Productos productos = new Productos();

    public static boolean registrarCompra1(Compras compra) {

        PreparedStatement psCompra = null;
        PreparedStatement psDetalle = null;
        ResultSet rsCompra = null;

        try {
            con = cn.getConnection();

            String sqlCompra = "INSERT INTO compras (fecha, proveedorId, totalCompra) VALUES (?, ?, ?)";
            String sqlDetalle = "INSERT INTO comprasproductos (comprasId, productosId, cantidad, costoArticulo) VALUES (?, ?, ?, ?)";

            // Obtener conexión y desactivar auto-commit para manejar transacción
            //  Conexion cn = Conexion.getInstance();
            con = cn.getConnection();
            con.setAutoCommit(false);  // Comenzar la transacción

            // Registrar la compra
            psCompra = con.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);
            psCompra.setString(1, compra.getFecha());
            psCompra.setInt(2, compra.getProveedorId());
            psCompra.setBigDecimal(3, compra.getTotalCompra());

            psCompra.executeUpdate();

            // Obtener el ID generado de la compra
            rsCompra = psCompra.getGeneratedKeys();
            int idCompra = 0;
            if (rsCompra.next()) {
                idCompra = rsCompra.getInt(1);  // Recuperar ID generado
            }

            // Registrar los detalles de la compra
            psDetalle = con.prepareStatement(sqlDetalle);

            for (ComprasProductos detalle : compra.getArticulos()) {
                psDetalle.setInt(1, idCompra);  // Asignar ID de la compra
                psDetalle.setInt(2, detalle.getProductosId());
                psDetalle.setBigDecimal(3, detalle.getCantidad());
                psDetalle.setBigDecimal(4, detalle.getCostoArticulo());

                psDetalle.addBatch();  // Agregar a batch
            }

            // Ejecutar el batch para los detalles
            psDetalle.executeBatch();

            // Confirmar la transacción
            con.commit();

        } catch (SQLException e) {
            // Manejar el error y hacer rollback
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
        return false;
    }

    public static boolean registrarCompra(Compras compra) {

        PreparedStatement psCompra = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psUpdateProducto = null;
        ResultSet rsCompra = null;

        try {
            // Obtener conexión y desactivar auto-commit para manejar transacción
            Conexion cn = Conexion.getInstance();
            con = cn.getConnection();
            con.setAutoCommit(false);  // Comenzar la transacción

            // Consulta para registrar la compra
            String sqlCompra = "INSERT INTO compras (fecha, proveedorId, totalCompra) VALUES (?, ?, ?)";

            psCompra = con.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);

            psCompra.setString(1, compra.getFecha());
            psCompra.setInt(2, compra.getProveedorId());
            psCompra.setBigDecimal(3, compra.getTotalCompra());

            psCompra.executeUpdate();

            // Obtener el ID generado de la compra
            rsCompra = psCompra.getGeneratedKeys();
            int idCompra = 0;
            if (rsCompra.next()) {
                idCompra = rsCompra.getInt(1);  // Recuperar ID generado
            }

            // Consulta para registrar los detalles de la compra
            String sqlDetalle = "INSERT INTO comprasproductos (comprasId, productosId, cantidad, costoArticulo, porcIva, precioVenta) VALUES (?, ?, ?, ?, ?, ?)";
            psDetalle = con.prepareStatement(sqlDetalle);

            // Consulta para actualizar la cantidad disponible en la tabla productos
            String sqlUpdateProducto = "UPDATE productos SET cantidadDisponible = cantidadDisponible + ?,  "
                    + "precioCompra = ?, precioVenta = ?, fechaActualizacion = ?, porcIva = ? "
                    + "WHERE idProductos = ?";
            psUpdateProducto = con.prepareStatement(sqlUpdateProducto);

            //Consulta para actulizar precios
            // UPDATE productos SET precioCompra = ?, precioVenta = ? WHERE (`idProductos` = ?);
            // Iterar sobre los productos comprados
            for (ComprasProductos detalle : compra.getArticulos()) {

                // Registrar el detalle de la compra
                psDetalle.setInt(1, idCompra);  // Asignar ID de la compra
                psDetalle.setInt(2, detalle.getProductosId());
                psDetalle.setBigDecimal(3, detalle.getCantidad());
                psDetalle.setBigDecimal(4, detalle.getCostoArticulo());
                psDetalle.setInt(5, detalle.getPorcIva());
                psDetalle.setBigDecimal(6, detalle.getPrecioVenta());

                psDetalle.addBatch();  // Agregar a batch

                // Actualizar el stock del producto
                psUpdateProducto.setBigDecimal(1, detalle.getCantidad());  // Cantidad comprada
                psUpdateProducto.setBigDecimal(2, detalle.getCostoArticulo());  //                   
                psUpdateProducto.setBigDecimal(3, detalle.getPrecioVenta());  //     
                psUpdateProducto.setString(4, compra.getFecha());  // Asignar la fecha de compra
                psUpdateProducto.setInt(5, detalle.getPorcIva());  // ID del producto
                psUpdateProducto.setInt(6, detalle.getProductosId());  // ID del producto
                psUpdateProducto.addBatch();  // Agregar a batch
            }

            // Ejecutar el batch para los detalles de compra
            psDetalle.executeBatch();

            // Ejecutar el batch para actualizar la cantidad disponible de los productos
            psUpdateProducto.executeBatch();

            // Confirmar la transacción
            con.commit();

            return true;  // Éxito

        } catch (SQLException e) {
            // Manejar el error y hacer rollback
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            cerrarRecursos();  // Cerrar recursos
        }

        return false;  // Error
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
            Logger.getLogger(DaoCompras.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
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
            Logger.getLogger(DaoCompras.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return proveedores; // Retornar el producto encontrado o null
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
