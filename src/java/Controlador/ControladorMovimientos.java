package Controlador;

import Modelo.Compras;
import Modelo.ComprasProductos;
import Modelo.Movimientos;
import Modelo.Productos;
import Modelo.Proveedores;
import Persistencia.DaoMovimientos;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControladorMovimientos extends HttpServlet {

    Movimientos mov = new Movimientos();
    Productos pr = new Productos();

    int ide;  // Variable de instancia para almacenar el ID
    int idprodcucto;

    //La variable serialVersionUID se utiliza en Java para asignar una versión única 
    //a una clase Serializable.
    //En este contexto, private static final long serialVersionUID = 1L; simplemente está estableciendo 
    //el serialVersionUID de la clase ControladorConsecutivo
    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
       

        String action = request.getParameter("accion");

        switch (action) {
         
            case "registrar":
                registrarMovimientos(request, response);
                break;

            case "listar":
                listarMovimientos(request, response);
                break;

            case "editar":
                editarMovimientos(request, response);
                break;

            case "editarMovimientos":
                actualizarMovimientos(request, response);
                break;

            case "buscar":
                buscarMovimientos(request, response);
                break;
            case "BuscarProductos":
                buscarProductos(request, response);
                break;
            case "BuscarProveedor":
                buscarProveedor(request, response);
                break;
            case "AgregarAlCarrito":
                AgregarCarrito(request, response);
                break;

            case "eliminar":
                eliminarMovimientos(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarMovimientos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            //Modelo Movmientno
            Movimientos mov = new Movimientos();

            mov.setFecha(request.getParameter("fecha"));
            mov.setProductos_idProductos(Integer.parseInt(request.getParameter("productos_idProductos")));
            mov.setRecibo_idRecibo(Integer.parseInt(request.getParameter("recibo_idRecibo")));
            mov.setCantidad(Double.valueOf(request.getParameter("cantidad")));
            mov.setCosto(Double.valueOf(request.getParameter("costo")));
            mov.setNota(request.getParameter("nota"));

            if (DaoMovimientos.grabar(mov)) {

                request.setAttribute("mensaje", "el movimiento  fue registrado");
            } else {
                request.setAttribute("mensaje", "el movimiento no fue registrado, validar campos ingresados");
            }

            List<Movimientos> lt = DaoMovimientos.listar();
            request.setAttribute("listaMovimientos", lt);
            request.getRequestDispatcher("Vistas/ListaMovimientos.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarMovimientos(request, response);
        }
    }

    private void listarMovimientos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Movimientos> lt = DaoMovimientos.listar();
            request.setAttribute("listaComprar", lt);
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        }
    }

    private void editarMovimientos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Movimientos mv = DaoMovimientos.obtenerMovimientosPorId(ide);
            request.setAttribute("Mov", mv);

            listarMovimientos(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarMovimientos(request, response);
        }
    }

    private void actualizarMovimientos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Utilizar la variable de instancia para obtener el ID
            Movimientos mv = DaoMovimientos.obtenerMovimientosPorId(ide);
            request.setAttribute("Mov", mv);

            Movimientos mov = new Movimientos();

            mov.setFecha(request.getParameter("fecha"));
            mov.setProductos_idProductos(Integer.parseInt(request.getParameter("productos_idProductos")));
            mov.setRecibo_idRecibo(Integer.parseInt(request.getParameter("recibo_idRecibo")));
            mov.setCantidad(Double.valueOf(request.getParameter("cantidad")));
            mov.setCosto(Double.valueOf(request.getParameter("costo")));
            mov.setNota(request.getParameter("nota"));
            mov.setIdMovimientos(ide);

            boolean actualizacionExitosa = DaoMovimientos.editar(mov);

            if (actualizacionExitosa) {
                request.setAttribute("mensaje", "Consecutivo actualizado correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo actualizar el Consecutivo");
            }

            listarMovimientos(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarMovimientos(request, response);
        }
    }

    private void eliminarMovimientos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idMovimientos = Integer.parseInt(request.getParameter("id"));

            if (DaoMovimientos.eliminar(idMovimientos)) {
                request.setAttribute("mensaje", "El movimiento fue Eliminado Correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el movimiento");
            }

            List<Movimientos> lt = DaoMovimientos.listar();
            request.setAttribute("listaMovimientos ", lt);
            request.getRequestDispatcher("Vistas/ListaMovimientos.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el Consecutivo");
            listarMovimientos(request, response);
        }
    }

    private void buscarMovimientos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscarProducto");
            List<Movimientos> lt = DaoMovimientos.buscarMovimientos(texto);
            request.setAttribute("listaMovimientos", lt);
            request.getRequestDispatcher("Vistas/ListaMovimientos.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaMovimientos.jsp").forward(request, response);
        }
    }

    protected void buscarProductos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        String idprod = request.getParameter("txtIdProducto");
         int productoId = Integer.parseInt(idprod); // Si esto puede ser no numérico, usa una verificación previa
        
        Productos producto = null;

        if (idprod != null && !idprod.isEmpty()) {
            producto = DaoMovimientos.buscarProducto(productoId);
            request.setAttribute("listapr", producto);
        }

        // Mantener la búsqueda del proveedor si ya fue realizada
        String idProveedor = request.getParameter("txtbuscarproveedor");
        if (idProveedor != null && !idProveedor.isEmpty()) {
            Proveedores proveedor = DaoMovimientos.buscarProveedor(Integer.parseInt(idProveedor));
            request.setAttribute("proveedorEncontrado", proveedor);
        }

        request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
    } catch (Exception ex) {
        ex.printStackTrace();
        request.setAttribute("mensaje", "Error al buscar los datos.");
        request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
    }
}

   protected void AgregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        HttpSession session = request.getSession();
        List<Compras> listaCarrito = (List<Compras>) session.getAttribute("listaCarrito");
        if (listaCarrito == null) {
            listaCarrito = new ArrayList<>();
        }

        try {
            // Obtener datos del formulario
            int idProducto = Integer.parseInt(request.getParameter("txtIdProducto"));
            int nombreProducto = Integer.parseInt(request.getParameter("producto")); // Usar String para el nombre del producto
            BigDecimal precio = new BigDecimal(request.getParameter("precio"));
            BigDecimal cantidad = new BigDecimal(request.getParameter("cantidad"));

            Compras comp = new Compras();
            comp.setIdCompra(idProducto);
            comp.setTotalCompra(precio); // Asegúrate de tener un campo nombre en tu modelo

          

            // Agregar el producto a la lista del carrito
            listaCarrito.add(comp);
            session.setAttribute("listaCarrito", listaCarrito);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Manejo de errores
        }

        // Redirigir o mostrar el carrito actualizado
          request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
    }

 protected void buscarProveedor(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    try {
        String idProveedor = request.getParameter("txtbuscarproveedor");
        Proveedores proveedor = null;

        if (idProveedor != null && !idProveedor.isEmpty()) {
            proveedor = DaoMovimientos.buscarProveedor(Integer.parseInt(idProveedor));
            request.setAttribute("proveedorEncontrado", proveedor);
        }

        // Limpiar el campo de búsqueda después de la búsqueda
        request.setAttribute("txtbuscarproveedor", "");

        // Mantener la búsqueda del producto si ya fue realizada
        String idprod = request.getParameter("txtIdProducto");
         int productoId = Integer.parseInt(idprod); // Si esto puede ser no numérico, usa una verificación previa
        if (idprod != null && !idprod.isEmpty()) {
            Productos producto = DaoMovimientos.buscarProducto(productoId);
            request.setAttribute("listapr", producto);
        }

        // Limpiar el campo del producto después de la búsqueda
        request.setAttribute("txtIdProducto", "");

        request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
    } catch (Exception ex) {
        ex.printStackTrace();
        request.setAttribute("mensaje", "Error al buscar los datos.");
        request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        
        eliminarProducto(request, response);
    }
}
 
 

protected void eliminarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<ComprasProductos> listaCarrito = (List<ComprasProductos>) session.getAttribute("listaCarrito");
        if (listaCarrito == null) {
            listaCarrito = new ArrayList<>();
        }

        try {
            int idProductoEliminar = Integer.parseInt(request.getParameter("idProducto"));

            // Filtrar la lista para eliminar el producto seleccionado
            listaCarrito.removeIf(p -> p.getProductosId() == idProductoEliminar);
            session.setAttribute("listaCarrito", listaCarrito);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Manejo de errores
        }

        // Redirigir o mostrar el carrito actualizado
        request.getRequestDispatcher("paginaCarrito.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
