package Persistencia;

import Config.Conexion;
import Modelo.Clientes;
import Modelo.DetallesFacturas;
import Modelo.Facturas;
import Modelo.Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

/*crear metodos*/

 /*adicionar*/
public class DaoFacturas {

    static Conexion cn = Conexion.getInstance();
    static Connection con;
    static PreparedStatement ps;
    static ResultSet rs;

    public static boolean registrarVenta(Facturas facturas) {

        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psUpdateProducto = null;
        ResultSet rsVenta = null;

        try {
            // Obtener conexión y desactivar auto-commit para manejar transacción
            Conexion cn = Conexion.getInstance();
            con = cn.getConnection();
            con.setAutoCommit(false);  // Comenzar la transacción

            // Consulta para registrar la compra
            String sqlCompra = "INSERT INTO facturas (fecha, clienteId, totalCosto, totalIva, totalVenta) VALUES (?, ?, ?, ?, ?)";

            psVenta = con.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS);

            psVenta.setString(1, facturas.getFecha());
            psVenta.setInt(2, facturas.getClienteId());
            psVenta.setBigDecimal(3, facturas.getTotalCosto());
            psVenta.setBigDecimal(4, facturas.getTotalIva());       
            psVenta.setBigDecimal(5, facturas.getTotalVenta());

            psVenta.executeUpdate();

            // Obtener el ID generado de la compra
            rsVenta = psVenta.getGeneratedKeys();
            int idVenta = 0;
            if (rsVenta.next()) {
                idVenta = rsVenta.getInt(1);  // Recuperar ID generado
            }

            // Consulta para registrar los detalles de la compra
            String sqlDetalle = "INSERT INTO detalles_facturas (facturasId, productosId, cantidad, precioCompra, precioVenta, porcIva) VALUES (?, ?, ?, ?, ?, ?)";
            psDetalle = con.prepareStatement(sqlDetalle);

            // Consulta para actualizar la cantidad disponible en la tabla productos
            String sqlUpdateProducto = "UPDATE productos SET cantidadDisponible = cantidadDisponible - ? WHERE idProductos = ?";
            psUpdateProducto = con.prepareStatement(sqlUpdateProducto);

            // Iterar sobre los productos comprados
            for (DetallesFacturas detalle : facturas.getFacturas()) {
                
                // Registrar el detalle de la compra
                psDetalle.setInt(1, idVenta);  // Asignar ID de la compra
                psDetalle.setInt(2, detalle.getProductosId());
                psDetalle.setBigDecimal(3, detalle.getCantidad());
                psDetalle.setBigDecimal(4, detalle.getPrecioCompra());
                psDetalle.setBigDecimal(5, detalle.getPrecioVenta());
                psDetalle.setInt(6, detalle.getPorcIva());
           

                psDetalle.addBatch();  // Agregar a batch

                // Actualizar el stock del producto
                psUpdateProducto.setBigDecimal(1, detalle.getCantidad());  // Cantidad comprada
                psUpdateProducto.setInt(2, detalle.getProductosId());  // ID del producto
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
            Logger.getLogger(DaoFacturas.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return producto; // Retornar el producto encontrado o null
    }
     
     public static Clientes buscarCliente(int id) {
        Clientes clientes = null; // Inicializamos como null
        String sql = "SELECT * FROM clientes WHERE id = ?"; // Usar consulta parametrizada

        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); // Asignar el parámetro PLU

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    clientes = new Clientes(); // Solo crear si hay un resultado
                    clientes.setId(rs.getInt("id"));
                    clientes.setNombres(rs.getString("nombres"));
                    clientes.setTelefono(rs.getString("telefono"));
                  
                    // Rellenar otros campos necesarios
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoFacturas.class.getName()).log(Level.SEVERE, "Error al acceder a la base de datos", ex);
        }

        return clientes; // Retornar el producto encontrado o null
    }
     
     
     /*Zona Clientes*/
     
        public static boolean grabarCliente(Clientes clientes) {
        try {
               con = cn.getConnection();
            String sql = "INSERT INTO clientes(nombres, telefono) "
                    + "VALUES(?,?);";
            ps = con.prepareStatement(sql);

            ps.setString(1, clientes.getNombres());
            ps.setString(2, clientes.getTelefono());
          

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
