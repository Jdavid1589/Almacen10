package Controlador;

import Modelo.Compras;
import Modelo.ComprasProductos;

import Modelo.Productos;
import Modelo.Proveedores;
import Persistencia.DaoCompras;
import Persistencia.DaoFacturas;
import Persistencia.DaoProductos;
import Persistencia.DaoUnidMedida;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/ControladorCompras")
public class ControladorCompras extends HttpServlet {

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
            case "FacturaCompra":
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
                break;
            case "BuscarProveedor":
                buscarProveedor(request, response);
                break;

            case "CancelarVenta":
                // Lógica para cancelar la venta
                request.getSession().invalidate(); // Esto limpia toda la sesión
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);

            case "BuscarProductos":
                buscarProducto(request, response);
                break;

            case "AgregarAlCarrito":
                agregarProductoAlCarrito(request, response);
                break;

            case "GenerarCompra":
                registrarCompra(request, response);
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

    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String productoIdStr = request.getParameter("productosId");
            String costoCompraStr = request.getParameter("precio");
            String costoVentaStr = request.getParameter("precioVenta");
            String cantidadStr = request.getParameter("cantidad");

            if (productoIdStr == null || productoIdStr.isEmpty()) {
                request.setAttribute("mensaje", "El ID del producto es obligatorio.");
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
                return;
            }

            if (costoCompraStr == null || !costoCompraStr.matches("\\d+(\\.\\d{1,2})?")
                    || costoVentaStr == null || !costoVentaStr.matches("\\d+(\\.\\d{1,2})?")) {
                request.setAttribute("mensaje", "Formato incorrecto en los precios.");
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
                return;
            }

            if (cantidadStr == null || !cantidadStr.matches("\\d+")) {
                request.setAttribute("mensaje", "La cantidad debe ser un número entero.");
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
                return;
            }

            int productosId = Integer.parseInt(productoIdStr);
            BigDecimal precioCompra = new BigDecimal(costoCompraStr.replaceAll("[^\\d.]", ""));
            BigDecimal precioVenta = new BigDecimal(costoVentaStr.replaceAll("[^\\d.]", ""));
            Double cantidad = Double.valueOf(cantidadStr);

            // Actualizar el producto en la base de datos
            Productos producto = DaoProductos.obtenerProductosPorId(productosId);
            if (producto != null) {
                producto.setPrecioCompra(precioCompra);
                producto.setPrecioVenta(precioVenta);
                producto.setCantidadDisponible(cantidad);

                DaoProductos.editar(producto); // Método que actualiza en la BD
            } else {
                request.setAttribute("mensaje", "Producto no encontrado.");
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
            }

        } catch (NumberFormatException ex) {
            request.setAttribute("mensaje", "Error en el formato de los datos numéricos.");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("mensaje", "Error inesperado al procesar la solicitud.");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        }
    }

    private void agregarProductoAlCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            // Obtener los parámetros de la solicitud
            String productoIdStr = request.getParameter("productosId");
            String cantidadStr = request.getParameter("cantidad");
            String CostoCompraStr = request.getParameter("precio");
            String costoVentaStr = request.getParameter("precioVenta");
            String proveedorStr = request.getParameter("proveedorId"); // Debe ser el ID del proveedor
            String fechastr = request.getParameter("fechaFactura");
            String porcIvastr = request.getParameter("porcIva");

            // Verificar que todos los campos estén presentes
            if (productoIdStr == null || productoIdStr.isEmpty()
                    || cantidadStr == null || cantidadStr.isEmpty()
                    || fechastr == null || fechastr.isEmpty()
                    || CostoCompraStr == null || CostoCompraStr.isEmpty()
                    || costoVentaStr == null || costoVentaStr.isEmpty()
                    || porcIvastr == null || porcIvastr.isEmpty()
                    || proveedorStr == null || proveedorStr.isEmpty()) {
                request.setAttribute("errorMessage", "Uno o más campos están vacíos.");
                request.getRequestDispatcher("/Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
                return;
            }

            // Convertir valores a tipos adecuados
            int productosId = Integer.parseInt(productoIdStr);
            int porcIva = Integer.parseInt(porcIvastr);
            BigDecimal cantidad = new BigDecimal(cantidadStr);

            // Limpiar el valor del costo
            String cleanedCostoStr = CostoCompraStr.replaceAll("[^\\d.]", ""); // Elimina caracteres no numéricos, pero mantiene el punto decimal
            String cleanedCostoVentaStr = costoVentaStr.replaceAll("[^\\d.]", ""); // Elimina caracteres no numéricos, pero mantiene el punto decimal

            // Verificar si el valor limpiado está vacío
            if (cleanedCostoStr.isEmpty() || cleanedCostoVentaStr.isEmpty()) {
                throw new NumberFormatException("El valor de costo está vacío después de la limpieza.");
            }

            BigDecimal costo = new BigDecimal(cleanedCostoStr);
            BigDecimal costoVenta = new BigDecimal(cleanedCostoVentaStr);

            // Convertir el proveedor a entero
            int proveedorId = Integer.parseInt(proveedorStr); // Debe ser un ID numérico

            // Obtener el carrito de la sesión o crear una nueva lista
            List<Compras> carrito = (List<Compras>) request.getSession().getAttribute("carrito");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            boolean productoEncontrado = false;

            // Verificar si el producto ya está en el carrito
            for (Compras compra : carrito) {
                List<ComprasProductos> articulos = compra.getArticulos();
                if (articulos != null) {

                    for (ComprasProductos detalle : articulos) {

                        if (detalle.getProductosId() == productosId) {
                            // Actualiza la cantidad y el costo del producto existente
                            detalle.setCantidad(detalle.getCantidad().add(cantidad));
                            detalle.setCostoArticulo(costo);
                            detalle.setPrecioVenta(costoVenta);
                            detalle.setPorcIva(porcIva);

                            // Actualizar el total de la compra
                            BigDecimal subtotal = detalle.getCantidad().multiply(costo);
                            BigDecimal totalCompra = compra.getTotalCompra().add(subtotal);
                            compra.setTotalCompra(totalCompra);

                            productoEncontrado = true;
                            break;
                        }
                    }
                }
                if (productoEncontrado) {
                    break;
                }
            }

            // Si el producto no está en el carrito, agrégalo como nuevo
            if (!productoEncontrado) {
                ComprasProductos detalleCompraProducto = new ComprasProductos();
                detalleCompraProducto.setProductosId(productosId);
                detalleCompraProducto.setCantidad(cantidad);
                detalleCompraProducto.setCostoArticulo(costo);
                detalleCompraProducto.setPrecioVenta(costoVenta);
                detalleCompraProducto.setPorcIva(porcIva);

                Compras detalleCompra = new Compras();

                detalleCompra.setFecha(fechastr);
                detalleCompra.setProveedorId(proveedorId); // Asumir proveedorId como entero
                detalleCompra.setTotalCompra(cantidad.multiply(costo)); // Inicializar con el subtotal del nuevo producto

                // Inicializa la lista de artículos si no existe
                List<ComprasProductos> articulos = new ArrayList<>();
                articulos.add(detalleCompraProducto);
                detalleCompra.setArticulos(articulos);

                carrito.add(detalleCompra);
            }

            // Guardar el carrito actualizado en la sesión
            request.getSession().setAttribute("carrito", carrito);
            request.getSession().setAttribute("fechaFactura", fechastr);
            // request.getSession().setAttribute("proveedorId", proveedorId); // Guardar proveedor en sesión

            mantenerBusquedaProveedor(request);

          
            // Redirigir a la página del carrito
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Error en el formato del costo o en los números. " + e.getMessage());
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error inesperado al procesar la solicitud.");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        }
    }

    private void registrarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // IDatos del formulario
        //Se recupera de la getSession
        String fechastr = (String) request.getSession().getAttribute("fechaFactura");

        // Obtener el carrito de la sesión
        List<Compras> carrito = (List<Compras>) request.getSession().getAttribute("carrito");

        // Validar si el carrito es nulo o está vacío
        if (fechastr == null || carrito == null || carrito.isEmpty()) {
            // Error, no se puede proceder con la compra
            request.setAttribute("mensajeError", "Validar datos ingresado  Error! .");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
            return;
        }

        // Asumir que el proveedor es el mismo para todas las compras en el carrito
        Compras primeraCompra = carrito.get(0);
        int proveedorId = primeraCompra.getProveedorId(); // Tomar el proveedor de la primera compra del carrito

        // Crear una nueva instancia de compra
        Compras compraFinal = new Compras();

        compraFinal.setProveedorId(proveedorId); // Asignar el proveedor
        compraFinal.setFecha(fechastr); // Asignar la fecha de la compra    

        // Inicializar el total de la compra
        BigDecimal totalCompra = BigDecimal.ZERO;

        // Lista de detalles de la compra
        List<ComprasProductos> detallesCompra = new ArrayList<>();

        // Iterar sobre cada compra en el carrito para procesar los productos
        for (Compras compra : carrito) {
            for (ComprasProductos articulo : compra.getArticulos()) {
                // Calcular el subtotal del producto (cantidad * costo unitario)
                BigDecimal subtotalProducto = articulo.getCostoArticulo().multiply(articulo.getCantidad());

                // Sumar el subtotal al total de la compra
                totalCompra = totalCompra.add(subtotalProducto);

                // Agregar el detalle del producto a la lista de detalles
                detallesCompra.add(articulo);
            }
        }

        // Asignar el total calculado a la compra
        compraFinal.setTotalCompra(totalCompra);

        // Asignar los detalles de los productos a la compra final
        compraFinal.setArticulos(detallesCompra);

        // Registrar la compra en la base de datos con todos los productos
        DaoCompras.registrarCompra(compraFinal);

        // Guardar el proveedor en la sesión para su uso posterior
        request.getSession().setAttribute("proveedorId", proveedorId);

        // Limpiar carrito y proveedor de la sesión
        request.getSession().removeAttribute("carrito");

        // Redirigir a la confirmación o página de compras vacía
        request.setAttribute("mensajeExito", "Compra registrada con éxito.");
        request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
    }

 
    /// Buscar producto con respuesta Json
    private void buscarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Obtener el ID del producto desde el formulario
            String idprod = request.getParameter("productosId");

            // Crear un objeto para la respuesta
            ProductoResponse productoResponse = new ProductoResponse();

            if (idprod != null && !idprod.isEmpty()) {
                try {
                    // Convertir el ID del producto a un entero
                    int productoId = Integer.parseInt(idprod);

                    // Buscar el producto en la base de datos
                    Productos producto = DaoFacturas.buscarProducto(productoId);

                    if (producto != null) {
                        // Asignar detalles del producto a la respuesta
                        productoResponse.setNombre(producto.getProductos());
                        productoResponse.setPrecioVenta(producto.getPrecioVenta());
                        productoResponse.setPrecioCompra(producto.getPrecioCompra());
                        productoResponse.setCantidadDisponible(producto.getCantidadDisponible());
                        productoResponse.setPorcIva(producto.getPorcIva());

                        // Obtener el ID de la unidad de medida
                        int unidadMedidaId = producto.getUnidadMedidaId();
                        productoResponse.setUnidadmedidad(unidadMedidaId);  // Asignar el ID de la unidad de medida

                        // Obtener el nombre de la unidad de medida usando el ID
                        String nombreUnidadMedida = DaoUnidMedida.obtenerNombreUnidad(unidadMedidaId);

                        // Asignar el nombre de la unidad de medida a la respuesta
                        productoResponse.setNombreUnidad(nombreUnidadMedida);

                    } else {
                        productoResponse.setMensaje("Producto no encontrado.");
                        productoResponse.setNombre(""); // Limpia el campo si no se encuentra el producto
                    }
                } catch (NumberFormatException e) {
                    productoResponse.setMensaje("ID de producto no válido.");
                    productoResponse.setNombre(""); // Limpia el campo si hay error en la conversión del ID
                }
            } else {
                productoResponse.setMensaje("ID de producto no proporcionado.");
                productoResponse.setNombre(""); // Limpia el campo si no se proporciona ID
            }

            // Escribir la respuesta en formato JSON
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

    /// Buscar producto con respuesta Json
    private void buscarProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Crear un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String idprov = request.getParameter("proveedorId");

            // Crear un objeto para la respuesta
            ProductoResponse productoResponse = new ProductoResponse();

            if (idprov != null && !idprov.isEmpty()) {
                try {
                    int productoId = Integer.parseInt(idprov);
                    Proveedores proveedores = DaoCompras.buscarProveedor(productoId);

                    if (proveedores != null) {

                        productoResponse.setNombre2(proveedores.getProveedor());

                    } else {
                        productoResponse.setNombre2(""); // Limpia el campo si no se encuentra el producto
                    }
                } catch (NumberFormatException e) {
                    productoResponse.setMensaje("ID de producto no válido.");
                    productoResponse.setNombre(""); // Limpia el campo si hay error
                }
            } else {
                productoResponse.setMensaje("ID de producto no proporcionado.");
                productoResponse.setNombre2(""); // Limpia el campo si no se proporciona ID
            }

            // Escribir el objeto respuesta en formato JSON
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, productoResponse);
            out.flush();

        } catch (Exception ex) {
            ex.printStackTrace(); // Para depuración

            ProductoResponse productoResponse = new ProductoResponse();

            productoResponse.setMensaje("Error al buscar los datos.");
            productoResponse.setNombre2(""); // Limpia el campo si hay error
            PrintWriter out = response.getWriter();
            objectMapper.writeValue(out, productoResponse);
            out.flush();
        }
    }

// Clase para la respuesta JSON
    public class ProductoResponse {

        private String nombre;
        private String nombre2;
        private String mensaje;
        private int unidadmedidad;
        private String nombreUnidad;

        private BigDecimal precioVenta;
        private BigDecimal precioCompra;
        private Double cantidadDisponible;
        private int porcIva;

        public String getNombreUnidad() {
            return nombreUnidad;
        }

        public void setNombreUnidad(String nombreUnidad) {
            this.nombreUnidad = nombreUnidad;
        }

        public String getNombre2() {
            return nombre2;
        }

        public void setNombre2(String nombre2) {
            this.nombre2 = nombre2;
        }

        public int getUnidadmedidad() {
            return unidadmedidad;
        }

        public void setUnidadmedidad(int unidadmedidad) {
            this.unidadmedidad = unidadmedidad;
        }

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

    private void mantenerBusquedaProducto(HttpServletRequest request) {
        String idprod = request.getParameter("productosId");

        if (idprod != null && !idprod.isEmpty()) {
            request.setAttribute("productosId", "");

            try {
                int productoId = Integer.parseInt(idprod);
                Productos producto = DaoCompras.buscarProducto(productoId);
                request.setAttribute("listapr", producto);
            } catch (NumberFormatException e) {
                request.setAttribute("mensaje", "ID de producto no válido.");
            }
        }
    }

    private void mantenerBusquedaProveedor(HttpServletRequest request) {
        String idProveedor = request.getParameter("proveedorId");

        try {
            int proveedorId = Integer.parseInt(idProveedor);
            Proveedores proveedor = DaoCompras.buscarProveedor(proveedorId);
            request.setAttribute("proveedorEncontrado", proveedor);
        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "ID de proveedor no válido.");
        }

    }

    private void eliminarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Obtener el ID de la compra a eliminar del parámetro de la solicitud
            String idCompraStr = request.getParameter("id");

            // Validar que el ID no sea nulo o vacío
            if (idCompraStr == null || idCompraStr.isEmpty()) {
                request.setAttribute("errorMessage", "ID de compra no proporcionado.");
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
                return;
            }

            // Convertir el ID a un entero
            int idCompra = Integer.parseInt(idCompraStr);

            // Obtener el carrito de la sesión
            List<Compras> carrito = (List<Compras>) request.getSession().getAttribute("carrito");

            if (carrito != null) {
                // Buscar y eliminar la compra con el ID proporcionado
                Iterator<Compras> iterator = carrito.iterator();
                while (iterator.hasNext()) {
                    Compras compra = iterator.next();
                    if (compra.getIdCompra() == idCompra) { // Asume que 'getId()' devuelve el ID de la compra
                        iterator.remove();
                        break;
                    }
                }

                // Actualizar el carrito en la sesión
                request.getSession().setAttribute("carrito", carrito);

                // Redirigir o forward a la página adecuada
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Carrito no encontrado.");
                request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "ID de compra inválido.");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error inesperado al eliminar la compra.");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        }
    }

    private void buscarProveedor2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idProveedor = request.getParameter("proveedorId");

            if (idProveedor != null && !idProveedor.isEmpty()) {
                try {
                    int proveedorId = Integer.parseInt(idProveedor);
                    Proveedores proveedor = DaoCompras.buscarProveedor(proveedorId);
                    request.setAttribute("proveedorEncontrado", proveedor);
                } catch (NumberFormatException e) {
                    request.setAttribute("mensaje", "ID de proveedor no válido.");
                }
            }

            request.setAttribute("proveedorId", "");

            mantenerBusquedaProducto(request);

            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);

        } catch (IOException | ServletException ex) {
            request.setAttribute("mensaje", "Error al buscar los datos.");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        }
    }

    private void buscarProducto2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idprod = request.getParameter("productosId");

            if (idprod != null && !idprod.isEmpty()) {
                try {
                    int productoId = Integer.parseInt(idprod);

                    // Buscar el producto utilizando el ID
                    Productos producto = DaoCompras.buscarProducto(productoId);

                    if (producto != null) {
                        // Obtener el ID de la unidad de medida desde el producto
                        int unidadMedidaId = producto.getUnidadMedidaId();

                        // Llamar al método que obtiene el nombre de la unidad de medida
                        String nombreUnidadMedida = DaoUnidMedida.obtenerNombreUnidad(unidadMedidaId);

                        // Asignar el producto y el nombre de la unidad de medida a la solicitud
                        request.setAttribute("listapr", producto);
                        request.setAttribute("nombreUnidadMedida", nombreUnidadMedida);
                    } else {
                        request.setAttribute("mensaje", "Producto no encontrado.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("mensaje", "ID de producto no válido.");
                }
            }

            // Mantener la búsqueda del proveedor
            mantenerBusquedaProveedor(request);

            // Reenviar la solicitud a la vista
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("mensaje", "Error al buscar los datos.");
            request.getRequestDispatcher("Vistas/Lista_Compras_Articulos.jsp").forward(request, response);
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
