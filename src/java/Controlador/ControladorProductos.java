package Controlador;

import Modelo.Productos;
import Persistencia.DaoProductos;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorProductos extends HttpServlet {

    Productos user = new Productos();
    int ide;  // Variable de instancia para almacenar el ID

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
            //   case "add":
            //  request.getRequestDispatcher("Vistas/RegistrarUsuarios.jsp").forward(request, response);//
            //  break;

            case "registrar":
                registrarProductos(request, response);
                break;

            case "listar":
                listarProductos(request, response);
                break;

            case "editar":
              //  editarProductos(request, response);
                mostrarFormularioEditarProductos(request, response);
                break;

            case "editarProducto2":
                request.setAttribute("EditarProd", request.getParameter("id"));
                request.getRequestDispatcher("Vistas/EditarProducto.jsp").forward(request, response);
                break;

            case "actualizar":
                actualizarProductos(request, response);
                break;

            case "buscar":
                buscarProductos(request, response);
                break;
            case "BuscarProductos":
                buscarProductos(request, response);
                break;

            case "eliminar":
                eliminarProductos(request, response);
                break;

            default:
                // Handle any other actions or provide a default behavior
                break;
        }
    }

    private void registrarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            Productos producto = new Productos();

            producto.setProductos(request.getParameter("productos"));
            producto.setPlu(request.getParameter("plu"));
            producto.setCategoriasId(Integer.parseInt(request.getParameter("categoriasId")));
            producto.setProveedoresId(Integer.parseInt(request.getParameter("proveedoresId")));
            producto.setUnidadMedidaId(Integer.parseInt(request.getParameter("unidadMedidaId")));
            producto.setCantidadDisponible(Double.parseDouble(request.getParameter("cantidadDisponible")));

            //se debe convertir a BigDecimal de manera diferente que los Double
            BigDecimal PrecioCompra = new BigDecimal(request.getParameter("precioCompra"));
            producto.setPrecioCompra(PrecioCompra);
            BigDecimal precioVenta = new BigDecimal(request.getParameter("precioVenta"));
            producto.setPrecioVenta(precioVenta);

            // Convertir el precioCompra y precioVenta a BigDecimal
            /*  BigDecimal precioCompra = null;
            BigDecimal precioVenta = null;

            String precioCompraStr = request.getParameter("precioCompra");
            String precioVentaStr = request.getParameter("precioVenta");

            if (precioCompraStr != null && !precioCompraStr.isEmpty()) {
                try {
                    precioCompra = new BigDecimal(precioCompraStr);
                } catch (NumberFormatException e) {
                    // Manejo de error en caso de formato incorrecto
                    e.printStackTrace();
                }
            }

            if (precioVentaStr != null && !precioVentaStr.isEmpty()) {
                try {
                    precioVenta = new BigDecimal(precioVentaStr);
                } catch (NumberFormatException e) {
                    // Manejo de error en caso de formato incorrecto
                    e.printStackTrace();
                }
            }

            producto.setPrecioCompra(precioCompra);
            producto.setPrecioVenta(precioVenta);*/
            if (DaoProductos.grabar(producto)) {
                request.setAttribute("mensaje", "el Producto fue registrado");
            } else {
                request.setAttribute("mensaje", "el producto no fue registrado, validar campos ingresados");
            }

            List<Productos> lt = DaoProductos.listar();
            request.setAttribute("listaProductos", lt);
            request.getRequestDispatcher("Vistas/ListaProductos.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
            listarProductos(request, response);
        }
    }

    //Metodo con validacion Funsiona
    private void registrarProductos2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Productos producto = new Productos();

            // Obtener y validar parámetros del formulario
            String productosStr = request.getParameter("productos");
            String pluStr = request.getParameter("plu");
            String categoriasIdStr = request.getParameter("categoriasId");
            String proveedoresIdStr = request.getParameter("proveedoresId");
            String unidadMedidaIdStr = request.getParameter("unidadMedidaId");
            String cantidadDisponibleStr = request.getParameter("cantidadDisponible");
            String precioCompraStr = request.getParameter("precioCompra");
            String precioVentaStr = request.getParameter("precioVenta");

            if (productosStr != null && pluStr != null && categoriasIdStr != null
                    && proveedoresIdStr != null && unidadMedidaIdStr != null
                    && cantidadDisponibleStr != null && precioCompraStr != null
                    && precioVentaStr != null) {

                producto.setProductos(productosStr);
                producto.setPlu(pluStr);
                producto.setCategoriasId(Integer.parseInt(categoriasIdStr));
                producto.setProveedoresId(Integer.parseInt(proveedoresIdStr));
                producto.setUnidadMedidaId(Integer.parseInt(unidadMedidaIdStr));
                producto.setCantidadDisponible(Double.parseDouble(cantidadDisponibleStr));

                BigDecimal precioCompra = new BigDecimal(precioCompraStr);
                BigDecimal precioVenta = new BigDecimal(precioVentaStr);

                producto.setPrecioCompra(precioCompra);
                producto.setPrecioVenta(precioVenta);

                if (DaoProductos.grabar(producto)) {
                    request.setAttribute("mensaje", "El Producto fue registrado exitosamente.");
                } else {
                    request.setAttribute("mensaje", "El producto no fue registrado, por favor valide los campos ingresados.");
                }
            } else {
                request.setAttribute("mensaje", "Todos los campos son requeridos, por favor complete el formulario.");
            }

            List<Productos> lt = DaoProductos.listar();
            request.setAttribute("listaProductos", lt);
            request.getRequestDispatcher("Vistas/ListaProductos.jsp").forward(request, response);

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al convertir los valores numéricos. Por favor, revise los datos ingresados.");
            listarProductos(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Producto. Por favor, intente de nuevo.");
            listarProductos(request, response);
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Productos> lt = DaoProductos.listar();
            request.setAttribute("listaProductos", lt);
            request.getRequestDispatcher("Vistas/ListaProductos.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al listar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaProductos.jsp").forward(request, response);
        }
    }

    private void editarProductos2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID del parámetro de solicitud y almacenarlo en la variable de instancia
            ide = Integer.parseInt(request.getParameter("id"));
            Productos pro = DaoProductos.obtenerProductosPorId(ide);
            request.setAttribute("User", pro);

            listarProductos(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al editar el Consecutivo");
            listarProductos(request, response);
        }
    }

 private void mostrarFormularioEditarProductos(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
     
    // Obtener el ID del producto desde el request
    int id = Integer.parseInt(request.getParameter("id"));
    
    request.setAttribute("idEditar", request.getParameter("id"));
       request.getRequestDispatcher("Vistas/EditarProductos.jsp").forward(request, response);
   
}



    private void actualizarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            Productos producto = new Productos();

            producto.setProductos(request.getParameter("productos"));
            producto.setPlu(request.getParameter("plu"));
            producto.setCategoriasId(Integer.parseInt(request.getParameter("categoriasId")));
            producto.setProveedoresId(Integer.parseInt(request.getParameter("proveedoresId")));
            producto.setUnidadMedidaId(Integer.parseInt(request.getParameter("unidadMedidaId")));
            producto.setCantidadDisponible(Double.parseDouble(request.getParameter("cantidadDisponible")));

            //se debe convertir a BigDecimal de manera diferente que los Double
            BigDecimal PrecioCompra = new BigDecimal(request.getParameter("precioCompra"));
            producto.setPrecioCompra(PrecioCompra);
            BigDecimal precioVenta = new BigDecimal(request.getParameter("precioVenta"));
            producto.setPrecioVenta(precioVenta);

            producto.setIdProductos(Integer.parseInt(request.getParameter("txtid")));

            boolean actualizacionExitosa = DaoProductos.editar(producto);

            String mensaje = actualizacionExitosa ? "Consecutivo actualizado correctamente" : "No se pudo actualizar el Consecutivo";
            request.setAttribute("mensaje", mensaje);

           
            listarProductos(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("mensaje", "Error al actualizar el Consecutivo: " + ex.getMessage());
            listarProductos(request, response);
        }
    }

    private void eliminarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idProductos = Integer.parseInt(request.getParameter("id"));

            if (DaoProductos.eliminar(idProductos)) {
                request.setAttribute("mensaje", "El producto fue Eliminado Correctamente");
            } else {
                request.setAttribute("mensaje", "No se pudo eliminar el producto");
            }

            List<Productos> lt = DaoProductos.listar();
            request.setAttribute("listaProductos", lt);
            request.getRequestDispatcher("Vistas/ListaProductos.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al eliminar el Consecutivo");
            listarProductos(request, response);
        }
    }

    private void buscarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String texto = request.getParameter("txtbuscar");
            List<Productos> lt = DaoProductos.buscarProductos(texto);
            request.setAttribute("listaProductos", lt);
            request.getRequestDispatcher("Vistas/ListaProductos.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los Consecutivos");
            request.getRequestDispatcher("Vistas/ListaProductos.jsp").forward(request, response);
        }
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
