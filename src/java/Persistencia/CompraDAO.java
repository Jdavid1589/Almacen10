
package Persistencia;

import Config.Conexion;
import Modelo.Compras;
import Modelo.ComprasProductos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompraDAO {

   public static void registrarCompra(Compras compra) {
    String sqlCompra = "INSERT INTO compras (fecha, proveedorId, totalCompra) VALUES (?, ?, ?)";
    String sqlDetalle = "INSERT INTO comprasproductos (comprasId, productosId, cantidad, costoArticulo) VALUES (?, ?, ?, ?)";

    Connection con = null;
    PreparedStatement psCompra = null;
    PreparedStatement psDetalle = null;
    ResultSet rsCompra = null;

    try {
        // Obtener conexión y desactivar auto-commit para manejar transacción
        Conexion cn = Conexion.getInstance();
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
        // Cerrar todos los recursos y restaurar auto-commit
        try {
            if (psCompra != null) psCompra.close();
            if (psDetalle != null) psDetalle.close();
            if (rsCompra != null) rsCompra.close();
            if (con != null) {
                con.setAutoCommit(true);  // Restaurar auto-commit
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

}



