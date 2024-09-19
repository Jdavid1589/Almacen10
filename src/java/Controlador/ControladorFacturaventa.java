package Controlador;

import Modelo.Clientes;
import Modelo.DetallesFacturas;
import Modelo.Facturas;
import Modelo.Productos;
import Persistencia.DaoFacturas;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControladorFacturaventa extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set the character encoding for the request
        request.setCharacterEncoding("UTF-8");
        // Set the content type and character encoding for the response
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("accion");
        // Ensure UTF-8 encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        switch (action) {
            case "ListaFactura":
                request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
                break;

            case "BuscarProductos":
                buscarProducto(request, response);
                break;

            case "BuscarCliente":
                buscarClientenuevo(request, response);
                break;

            case "AgregarAlCarrito2":
                agregarProductoAlCarrito(request, response);
                break;

            /*   case "GenerarCompra":
                registrarCompra(request, response);
                break;*/
            case "Eliminar":
                eliminarCompra(request, response);
                break;

            default:
                // Manejar el caso en el que la acción no coincida con ninguna opción
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                break;
        }

    }

    private void buscarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idprod = request.getParameter("productosId");

            if (idprod != null && !idprod.isEmpty()) {
                try {
                    int productoId = Integer.parseInt(idprod);
                    Productos producto = DaoFacturas.buscarProducto(productoId);
                    request.setAttribute("listapr", producto);
                } catch (NumberFormatException e) {
                    request.setAttribute("mensaje", "ID de producto no válido.");
                }
            }

            mantenerBusquedaCliente(request);
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los datos.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        }
    }

    private void buscarClientenuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idCliente = request.getParameter("clienteId");

            if (idCliente != null && !idCliente.isEmpty()) {
                try {
                    int clienteId = Integer.parseInt(idCliente);
                    Clientes clientes = DaoFacturas.buscarCliente(clienteId);
                    request.setAttribute("clienteEncontrado", clientes);
                } catch (NumberFormatException e) {
                    request.setAttribute("mensaje", "ID de proveedor no válido.");
                }
            }

            request.setAttribute("clienteId", "");

            mantenerBusquedaProducto(request);

            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);

        } catch (IOException | ServletException ex) {
            request.setAttribute("mensaje", "Error al buscar los datos.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        }
    }

    private void agregarProductoAlCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener los parámetros de la solicitud
            String productoIdStr = request.getParameter("productosId");
            String cantidadStr = request.getParameter("cantidad");
            String costoStr = request.getParameter("precioCompra");
            String precioventastr = request.getParameter("precioVenta");
            String porcIvastr = request.getParameter("porcIva");
            String clientestr = request.getParameter("clienteId"); // Debe ser el ID del cliente
            String fechastr = request.getParameter("fechaFactura");

            System.out.println("Producto ID: " + productoIdStr);
            System.out.println("Cantidad: " + cantidadStr);
            System.out.println("Costo: " + costoStr);
            System.out.println("Precio Venta: " + precioventastr);
            System.out.println("Porcentaje IVA: " + porcIvastr);
            System.out.println("Cliente ID: " + clientestr);
            System.out.println("Fecha Factura: " + fechastr);

            // Verificar que todos los campos estén presentes
            if (productoIdStr == null || productoIdStr.isEmpty()
                    || cantidadStr == null || cantidadStr.isEmpty()
                    || fechastr == null || fechastr.isEmpty()
                    || costoStr == null || costoStr.isEmpty()
                    || precioventastr == null || precioventastr.isEmpty()
                    || porcIvastr == null || porcIvastr.isEmpty()
                    || clientestr == null || clientestr.isEmpty()) {

                request.setAttribute("errorMessage", "Uno o más campos están vacíos.");
                request.getRequestDispatcher("/Vistas/ListaFacturaVenta.jsp").forward(request, response);
                return;
            }

            // Convertir valores a tipos adecuados
            int productosId = Integer.parseInt(productoIdStr);
            int clienteId = Integer.parseInt(clientestr);
            int porcIva = Integer.parseInt(porcIvastr);
            BigDecimal cantidad = new BigDecimal(cantidadStr);

            // Limpiar y convertir los valores de precio
            String cleanedCostoStr = costoStr.replaceAll("[^\\d.]", "");
            String cleanedCostoVentaStr = precioventastr.replaceAll("[^\\d.]", "");
            if (cleanedCostoStr.isEmpty()) {
                throw new NumberFormatException("El valor de costo está vacío después de la limpieza.");
            }
            BigDecimal costo = new BigDecimal(cleanedCostoStr);
            BigDecimal costoVenta = new BigDecimal(cleanedCostoVentaStr);

            // Obtener o inicializar el carrito
            List<Facturas> carrito = (List<Facturas>) request.getSession().getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            System.out.println("Productos ID: " + productosId);
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Costo: " + costo);
            System.out.println("Carrito actual: " + carrito.size());

            boolean productoEncontrado = false;

            for (Facturas venta : carrito) {
                List<DetallesFacturas> detalles = venta.getFacturas();
                for (DetallesFacturas detalle : detalles) {
                    if (detalle.getProductosId() == productosId) {
                        // Si el producto ya está en el carrito, actualiza la cantidad
                        detalle.setCantidad(detalle.getCantidad().add(cantidad));
                        productoEncontrado = true;
                        break;
                    }
                }
                if (productoEncontrado) {
                    break;
                }
            }

            // Si el producto no está en el carrito, agrégalo
            if (!productoEncontrado) {
                DetallesFacturas nuevoDetalle = new DetallesFacturas();
                nuevoDetalle.setProductosId(productosId);
                nuevoDetalle.setCantidad(cantidad);
                nuevoDetalle.setPrecioCompra(costo);
                nuevoDetalle.setPrecioVenta(costoVenta);
                nuevoDetalle.setPorcIva(porcIva);

                // Crear nueva factura si es necesario
                Facturas nuevaFactura = new Facturas();
                List<DetallesFacturas> detalles = new ArrayList<>();
                detalles.add(nuevoDetalle);
                nuevaFactura.setFacturas(detalles);
                carrito.add(nuevaFactura);
            }

            // Guardar el carrito actualizado en la sesión
            request.getSession().setAttribute("carrito", carrito);
            request.getSession().setAttribute("fechaFactura", fechastr);

            // Imprimir el contenido del carrito en la consola para depuración
            System.out.println("Contenido del carrito:");
            for (Facturas factura : carrito) {
                System.out.println("Fecha: " + factura.getFecha() + ", Cliente ID: " + factura.getClienteId() + ", Total Venta: " + factura.getTotalVenta());
                for (DetallesFacturas detalle : factura.getFacturas()) {
                    System.out.println("    Producto ID: " + detalle.getProductosId() + ", Cantidad: " + detalle.getCantidad() + ", Costo Unitario: " + detalle.getPrecioCompra());
                }
            }

            // Redirigir a la página del carrito
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Error en el formato del costo o en los números. " + e.getMessage());
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error inesperado al procesar la solicitud.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        }
    }

    private void mantenerBusquedaProducto(HttpServletRequest request) {
        String idprod = request.getParameter("productosId");

        if (idprod != null && !idprod.isEmpty()) {
            request.setAttribute("productosId", "");

            try {
                int productoId = Integer.parseInt(idprod);
                Productos producto = DaoFacturas.buscarProducto(productoId);
                request.setAttribute("listapr", producto);
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "ID de producto no válido.");
            }
        }
    }

    private void mantenerBusquedaCliente(HttpServletRequest request) {
        String idProveedor = request.getParameter("clienteId");

        try {
            int Idcliente = Integer.parseInt(idProveedor);
            Clientes clientes = DaoFacturas.buscarCliente(Idcliente);
            request.setAttribute("clienteEncontrado", clientes);
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "ID de proveedor no válido.");
        }

    }

    /*private void registrarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // IDatos del formulario
        //Se recupera de la getSession
        String fechastr = (String) request.getSession().getAttribute("fechaFactura");

        // Obtener el carrito de la sesión
        List<Compras> carrito = (List<Compras>) request.getSession().getAttribute("carrito");

        // Validar si el carrito es nulo o está vacío
        if (fechastr == null || carrito == null || carrito.isEmpty()) {
            // Error, no se puede proceder con la factura
            request.setAttribute("mensajeError", "Validar datos ingresado  Error! .");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
            return;
        }

        // Asumir que el proveedor es el mismo para todas las compras en el carrito
        Compras primeraCompra = carrito.get(0);
        int proveedorId = primeraCompra.getProveedorId(); // Tomar el proveedor de la primera factura del carrito

        // Crear una nueva instancia de factura
        Compras compraFinal = new Compras();

        compraFinal.setProveedorId(proveedorId); // Asignar el proveedor
        compraFinal.setFecha(fechastr); // Asignar la fecha de la factura    

        // Inicializar el total de la factura
        BigDecimal totalCompra = BigDecimal.ZERO;

        // Lista de detalles de la factura
        List<ComprasProductos> detallesCompra = new ArrayList<>();

        // Iterar sobre cada factura en el carrito para procesar los productos
        for (Compras compra : carrito) {
            for (ComprasProductos articulo : compra.getArticulos()) {
                // Calcular el subtotal del producto (cantidad * costo unitario)
                BigDecimal subtotalProducto = articulo.getCostoArticulo().multiply(articulo.getCantidad());

                // Sumar el subtotal al total de la factura
                totalCompra = totalCompra.add(subtotalProducto);

                // Agregar el detalle del producto a la lista de detalles
                detallesCompra.add(articulo);
            }
        }

        // Asignar el total calculado a la factura
        compraFinal.setTotalCompra(totalCompra);

        // Asignar los detalles de los productos a la factura final
        compraFinal.setArticulos(detallesCompra);

        // Registrar la factura en la base de datos con todos los productos
        DaoCompras.registrarCompra(compraFinal);

        // Guardar el proveedor en la sesión para su uso posterior
        request.getSession().setAttribute("proveedorId", proveedorId);

        // Limpiar carrito y proveedor de la sesión
        request.getSession().removeAttribute("carrito");

        // Redirigir a la confirmación o página de compras vacía
        request.setAttribute("mensajeExito", "Compra registrada con éxito.");
        request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
    }*/
    private void eliminarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID de la factura a eliminar del parámetro de la solicitud
            String idVentaStr = request.getParameter("id");

            // Validar que el ID no sea nulo o vacío
            if (idVentaStr == null || idVentaStr.isEmpty()) {
                request.setAttribute("errorMessage", "ID de compra no proporcionado.");
                request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
                return;
            }

            // Convertir el ID a un entero
            int idVenta = Integer.parseInt(idVentaStr);

            // Obtener el carrito de la sesión
            List<Facturas> carritoVenta = (List<Facturas>) request.getSession().getAttribute("carrito");

            if (carritoVenta != null) {
                // Buscar y eliminar la factura con el ID proporcionado
                Iterator<Facturas> iterator = carritoVenta.iterator();
                while (iterator.hasNext()) {
                    Facturas venta = iterator.next();
                    if (venta.getId() == idVenta) { // Asume que 'getId()' devuelve el ID de la factura
                        iterator.remove();
                        break;
                    }
                }

                // Actualizar el carrito en la sesión
                request.getSession().setAttribute("carrito", carritoVenta);

                // Redirigir o forward a la página adecuada
                request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Carrito no encontrado.");
                request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "ID de compra inválido.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error inesperado al eliminar la compra.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ensure UTF-8 encoding
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Call processRequest to handle the logic
        processRequest(request, response);

        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
