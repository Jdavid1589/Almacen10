/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Modelo.Bodega;
import Modelo.Compras;
import Modelo.ComprasProductos;
import Modelo.Productos;
import Persistencia.CompraDAO;
import Persistencia.DaoBodega;
import Persistencia.DaoCompras;
import Persistencia.DaoProductos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Varias {

    /*  public static void main(String[] args) {
        
    List<Bodega> bodegas = DaoBodega. listar();
    for (Bodega bodega : bodegas) {
        System.out.println("ID Bodega: " + bodega.getIdBodega());
        System.out.println("Cantidad Inventario: " + bodega.getCantidadInventario());
        System.out.println("Unidad Medida: " + bodega.getUnidadMedida());
        System.out.println("Costo: " + bodega.getCosto());
        System.out.println("Productos ID: " + bodega.getProductos_idProductos());
    
        System.out.println("Movimientos ID: " + bodega.getMovimientos_Id());
        System.out.println("-------------------------");
    }
}*/
 /*   public static void main(String[] args) {
        // Crear un objeto Bodega con datos de prueba
        Bodega bodega = new Bodega();
        bodega.setCantidadInventario(100.0);
        bodega.setUnidadMedida("kg");
        bodega.setCosto(500.0);
        bodega.setProductos_idProductos(1);  // Id de producto de ejemplo
     bodega.setMovimientos_Id(2);  // Id de movimiento de ejemplo

        // Llamar al método grabar para insertar los datos
        boolean resultado = DaoBodega.grabar(bodega);

        // Mostrar el resultado
        if (resultado) {
            System.out.println("Datos grabados exitosamente.");
        } else {
            System.out.println("Error al grabar los datos.");
        }
    }*/
 /*
     public static void main(String[] args) {
        // Crear un objeto Productos y asignar valores a sus atributos
        Productos producto = new Productos();
        producto.setProductos("Producto de Ejemplo");
        producto.setPlu("123456789");
        producto.setCategoriasId(1);
        producto.setProveedoresId(1);
        producto.setUnidadMedidaId(1);
        producto.setCantidadDisponible(100.0);
        producto.setPrecioCompra(new BigDecimal("50.00"));
        producto.setPrecioVenta(new BigDecimal("75.00"));

        // Intentar grabar el producto en la base de datos
        boolean resultado = DaoProductos.grabar(producto);

        // Verificar si la operación fue exitosa
        if (resultado) {
            System.out.println("El producto se ha grabado exitosamente en la base de datos.");
        } else {
            System.out.println("Hubo un error al intentar grabar el producto en la base de datos.");
        }
    }
     */
   /* public static void main(String[] args) {
        // Crear una compra
        Compras compra = new Compras();
        compra.setFecha("2024-09-13");
        compra.setProveedorId(1);  // ID del proveedor
        compra.setTotalCompra(new BigDecimal("500.00"));  // Total de la compra

        // Crear lista de productos comprados (ComprasProductos)
        List<ComprasProductos> articulos = new ArrayList<>();

        // Crear un producto y agregarlo a la lista
        ComprasProductos producto1 = new ComprasProductos();
        producto1.setProductosId(2);  // ID del producto
        producto1.setCantidad(new BigDecimal("50"));  // Cantidad comprada
        producto1.setCostoArticulo(new BigDecimal("50.00"));  // Costo del artículo
        producto1.setPorcIva(19);  // ID del producto
        articulos.add(producto1);

        // Crear otro producto y agregarlo a la lista
        ComprasProductos producto2 = new ComprasProductos();
        producto2.setProductosId(2);  // ID de otro producto
        producto2.setCantidad(new BigDecimal("55"));
        producto2.setCostoArticulo(new BigDecimal("20.00"));
        producto2.setPorcIva(19);  // ID del producto
        articulos.add(producto2);

        // Asignar los productos a la compra
        compra.setArticulos(articulos);

        // Llamar al método registrarCompra para realizar la prueba
        boolean exito = DaoCompras.registrarCompra(compra);

        // Verificar el resultado
        if (exito) {
            System.out.println("Compra registrada exitosamente.");
        } else {
            System.out.println("Error al registrar la compra.");
        }
    }*/
}
