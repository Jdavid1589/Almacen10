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
    
   /*public static void main(String[] args) {
       
        // Crear instancia de la compra
        Compras compra = new Compras();
        compra.setFecha("2024-09-10");
        compra.setProveedorId(1);  // Suponiendo que el proveedor con ID 1 existe
        compra.setTotalCompra(new BigDecimal("500.00"));

        // Crear lista de productos comprados
        List<ComprasProductos> productos = new ArrayList<>();

        // Producto 1
        ComprasProductos producto1 = new ComprasProductos();
        producto1.setProductosId(25);  // Suponiendo que el producto con ID 101 existe
        producto1.setCantidad(new BigDecimal("2"));
        producto1.setCostoArticulo(new BigDecimal("150.00"));
        

        // Producto 2
        ComprasProductos producto2 = new ComprasProductos();
        producto2.setProductosId(24);  // Suponiendo que el producto con ID 102 existe
        producto2.setCantidad(new BigDecimal("1"));
        producto2.setCostoArticulo(new BigDecimal("200.00"));
       

        // Agregar productos a la compra
        productos.add(producto1);
        productos.add(producto2);

        // Asignar los productos a la compra
        compra.setArticulos(productos);

        // Llamar al método para registrar la compra
       CompraDAO.registrarCompra(compra);

        System.out.println("Compra registrada correctamente.");
    }*/
}


