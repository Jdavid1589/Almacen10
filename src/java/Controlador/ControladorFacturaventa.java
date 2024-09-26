package Controlador;

import Modelo.Clientes;
import Modelo.DetallesFacturas;
import Modelo.Facturas;
import Modelo.Productos;
import Persistencia.DaoFacturas;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

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
                CargarCliente(request, response);
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

            case "GenerarVenta":
                registrarCompra2(request, response);
                break;

            case "registrarCliente":
                CrearCliente(request, response);
                break;

            case "CancelarVenta":
                // Lógica para cancelar la venta
                request.getSession().invalidate(); // Esto limpia toda la sesión
                request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);

                break;
            case "Eliminar":
                eliminarCompra(request, response);
                break;

            default:
                // Manejar el caso en el que la acción no coincida con ninguna opción
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                break;
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
            String clientestr = request.getParameter("clienteId");
            String fechastr = request.getParameter("fechaFactura");

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
            BigDecimal costo = new BigDecimal(costoStr.replaceAll("[^\\d.]", ""));
            BigDecimal costoVenta = new BigDecimal(precioventastr.replaceAll("[^\\d.]", ""));

            // Obtener o inicializar el carrito
            List<Facturas> carrito = (List<Facturas>) request.getSession().getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            // Variable para indicar si el producto ya existe en el carrito
            boolean productoEncontrado = false;

            // Buscar si el producto ya está en el carrito
            for (Facturas venta : carrito) {
                List<DetallesFacturas> detalles = venta.getFacturas();
                for (DetallesFacturas detalle : detalles) {

                    if (detalle.getProductosId() == productosId) {
                        // Actualiza la cantidad si el producto ya está en el carrito
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
                nuevaFactura.setFecha(fechastr);
                nuevaFactura.setClienteId(clienteId);

                // Cálculo de totales
                BigDecimal totalCosto = costoVenta.add(costoVenta);
                BigDecimal totalIva = totalCosto.multiply(BigDecimal.valueOf(porcIva)).divide(BigDecimal.valueOf(100));
                BigDecimal totalVenta = cantidad.multiply(costoVenta);

                nuevaFactura.setTotalCosto(totalCosto);
                nuevaFactura.setTotalIva(totalIva);
                nuevaFactura.setTotalVenta(totalVenta);

                List<DetallesFacturas> detalles = new ArrayList<>();
                detalles.add(nuevoDetalle);
                nuevaFactura.setFacturas(detalles);
                carrito.add(nuevaFactura);
            }

            // Guardar el carrito actualizado en la sesión
            request.getSession().setAttribute("carrito", carrito);
            request.getSession().setAttribute("fechaFactura", fechastr);

            // Redirigir para cargar el cliente predeterminado
            CargarCliente(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Error en el formato del costo o en los números. " + e.getMessage());
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error inesperado al procesar la solicitud.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        }
    }

    private void registrarCompra2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datos del formulario
        String fechastr = (String) request.getSession().getAttribute("fechaFactura");

        // Obtener el carrito de la sesión
        List<Facturas> carrito = (List<Facturas>) request.getSession().getAttribute("carrito");

        // Validar si el carrito es nulo o está vacío
        if (fechastr == null || carrito == null || carrito.isEmpty()) {
            // Error, no se puede proceder con la factura
            request.setAttribute("mensajeError", "Validar datos ingresados. ¡Error!");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
            return;
        }

        // Asumir que el Cliente es el mismo para todas las compras en el carrito
        Facturas primeraCompra = carrito.get(0);
        int idCliente = primeraCompra.getClienteId(); // Tomar el cliente de la primera factura del carrito

        // Crear una nueva instancia de factura
        Facturas compraFinal = new Facturas();
        compraFinal.setClienteId(idCliente); // Asignar el Cliente
        compraFinal.setFecha(fechastr); // Asignar la fecha de la factura

        // Inicializar los totales
        BigDecimal totalCosto = BigDecimal.ZERO;
        BigDecimal totalIva = BigDecimal.ZERO;
        BigDecimal totalVenta = BigDecimal.ZERO;

        // Lista de detalles de la factura
        List<DetallesFacturas> detallesCompra = new ArrayList<>();

        // Iterar sobre cada factura en el carrito para procesar los productos
        for (Facturas compra : carrito) {
            for (DetallesFacturas articulo : compra.getFacturas()) {

                // Calcular el subtotal del producto (cantidad * precio unitario)
                BigDecimal subtotalProducto = articulo.getPrecioVenta().multiply(articulo.getCantidad());

                // Calcular el IVA para este producto (porcIva está en porcentaje)
                BigDecimal ivaProducto = subtotalProducto.multiply(BigDecimal.valueOf(articulo.getPorcIva()))
                        .divide(BigDecimal.valueOf(100));

                // Calcular el precio total del producto (subtotal + IVA)
                BigDecimal precioTotalProducto = subtotalProducto.add(ivaProducto);

                // Acumular los totales
                totalCosto = totalCosto.add(subtotalProducto);   // Sumar el subtotal al costo total
                totalIva = totalIva.add(ivaProducto);           // Sumar el IVA al total de IVA
                totalVenta = totalVenta.add(precioTotalProducto); // Sumar el precio total (subtotal + IVA) a la venta total

                // Agregar el detalle del producto a la lista de detalles
                detallesCompra.add(articulo);
            }
        }

        // Asignar los totales calculados a la factura
        compraFinal.setTotalCosto(totalCosto); // Total del costo (subtotal sin IVA)
        compraFinal.setTotalIva(totalIva);     // Total de IVA acumulado
        compraFinal.setTotalVenta(totalVenta); // Total de la venta (subtotal + IVA)

        // Asignar los detalles de los productos a la factura final
        compraFinal.setFacturas(detallesCompra);

        // Registrar la factura en la base de datos con todos los productos
        DaoFacturas.registrarVenta(compraFinal);

        // Guardar el cliente en la sesión para su uso posterior
        request.getSession().setAttribute("clienteId", idCliente);

        // Limpiar carrito y cliente de la sesión
        request.getSession().removeAttribute("carrito");

        // Redirigir a la confirmación o página de compras vacía
        request.setAttribute("mensajeExito", "Compra registrada con éxito.");
        request.setAttribute("mensajeError", "Validar datos ingresados. ¡Error!");
        request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
    }

    private void CargarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String clienteId = request.getParameter("clienteId");

            // Si clienteId es nulo o es 1, buscar el cliente por defecto
            if (clienteId == null || clienteId.equals("1")) {
                clienteId = "1";  // Valor predeterminado
                Clientes clienteEncontrado = DaoFacturas.buscarCliente(Integer.parseInt(clienteId));  // Lógica para buscar cliente

                // Enviar datos del cliente al JSP
                request.setAttribute("clienteId", clienteEncontrado.getId());
                request.setAttribute("clienteEncontrado", clienteEncontrado);
            }

            // Renderizar la página JSP con los datos
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los datos.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
        }
    }

    /// Buscar producto con respuesta Json
    private void buscarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String idprod = request.getParameter("productosId");
            // Crear un objeto para la respuesta
            ProductoResponse productoResponse = new ProductoResponse();

            if (idprod != null && !idprod.isEmpty()) {
                try {
                    int productoId = Integer.parseInt(idprod);
                    Productos producto = DaoFacturas.buscarProducto(productoId);

                    if (producto != null) {

                        productoResponse.setNombre(producto.getProductos());
                        productoResponse.setPrecioVenta(producto.getPrecioVenta());
                        productoResponse.setPrecioCompra(producto.getPrecioCompra());
                        productoResponse.setCantidadDisponible(producto.getCantidadDisponible());
                        productoResponse.setPorcIva(producto.getPorcIva());

                    } else {
                        productoResponse.setNombre(""); // Limpia el campo si no se encuentra el producto
                    }
                } catch (NumberFormatException e) {
                    productoResponse.setMensaje("ID de producto no válido.");
                    productoResponse.setNombre(""); // Limpia el campo si hay error
                }
            } else {
                productoResponse.setMensaje("ID de producto no proporcionado.");
                productoResponse.setNombre(""); // Limpia el campo si no se proporciona ID
            }

            // Escribir el objeto respuesta en formato JSON
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, productoResponse);
            out.flush();

        } catch (Exception ex) {
            ex.printStackTrace(); // Para depuración
            ProductoResponse productoResponse = new ProductoResponse();

            productoResponse.setMensaje("Error al buscar los datos.");
            productoResponse.setNombre(""); // Limpia el campo si hay error
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, productoResponse);
            out.flush();
        }
    }

// Clase para la respuesta JSON
    public class ProductoResponse {

        private String nombre;
        private String mensaje;
        private BigDecimal precioVenta;
        private BigDecimal precioCompra;
        private Double cantidadDisponible;
        private int porcIva;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public BigDecimal getPrecioVenta() {
            return precioVenta;
        }

        public void setPrecioVenta(BigDecimal precioVenta) {
            this.precioVenta = precioVenta;
        }

        public BigDecimal getPrecioCompra() {
            return precioCompra;
        }

        public void setPrecioCompra(BigDecimal precioCompra) {
            this.precioCompra = precioCompra;
        }

        public Double getCantidadDisponible() {
            return cantidadDisponible;
        }

        public void setCantidadDisponible(Double cantidadDisponible) {
            this.cantidadDisponible = cantidadDisponible;
        }

        public int getPorcIva() {
            return porcIva;
        }

        public void setPorcIva(int porcIva) {
            this.porcIva = porcIva;
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

                 // Redirigir para cargar el cliente predeterminado
            CargarCliente(request, response);
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

    /*------------------------------------------------------------------------------------------------------------------------*/
 /* Zona para Crear Clientes*/
    private void CrearCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            Clientes clientes = new Clientes();

            // . Se toma la inforacion  del for y se le pasa directamente al obj
            clientes.setNombres(request.getParameter("nombres"));
            clientes.setTelefono(request.getParameter("telefono"));

            if (DaoFacturas.grabarCliente(clientes)) {
                request.setAttribute("mensajeExito", "el Cliente fue registrado");
            } else {
                request.setAttribute("mensajeErrro", "el usuario no fue registrado, validar campos ingresados");
            }

            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al registrar el Consecutivo");
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
