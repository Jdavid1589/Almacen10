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

            case "GenerarVenta":
                registrarCompra(request, response);
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

    private void agregarProductoAlCarrito2(HttpServletRequest request, HttpServletResponse response)
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
                BigDecimal totalCosto = cantidad.multiply(costo);
                BigDecimal totalVenta = cantidad.multiply(costoVenta);
                BigDecimal totalIva = totalCosto.multiply(BigDecimal.valueOf(porcIva)).divide(BigDecimal.valueOf(100));

                nuevaFactura.setTotalCosto(totalCosto);
                nuevaFactura.setTotalIva(totalIva);
                nuevaFactura.setTotalPrecioNeto(totalCosto.add(totalIva));
                nuevaFactura.setTotalVenta(totalVenta);

                List<DetallesFacturas> detalles = new ArrayList<>();
                detalles.add(nuevoDetalle);
                nuevaFactura.setFacturas(detalles);
                carrito.add(nuevaFactura);
            }

            // Guardar el carrito actualizado en la sesión
            request.getSession().setAttribute("carrito", carrito);
            request.getSession().setAttribute("fechaFactura", fechastr);

            // Calcular totales acumulados
            BigDecimal totalCostoAcumulado = BigDecimal.ZERO;
            BigDecimal totalIvaAcumulado = BigDecimal.ZERO;
            BigDecimal totalVentaAcumulado = BigDecimal.ZERO;

            for (Facturas factura : carrito) {
                totalCostoAcumulado = totalCostoAcumulado.add(factura.getTotalCosto());
                totalIvaAcumulado = totalIvaAcumulado.add(factura.getTotalIva());
                totalVentaAcumulado = totalVentaAcumulado.add(factura.getTotalPrecioNeto());
            }

            // Guardar totales en la sesión
            request.getSession().setAttribute("totalCosto", totalCostoAcumulado);
            request.getSession().setAttribute("totalIva", totalIvaAcumulado);
            request.getSession().setAttribute("totalVentaAcumulado", totalVentaAcumulado);

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

            // Depuración: imprimir los parámetros recibidos
            /* System.out.println("Parámetros recibidos:");
            System.out.println("productoId: " + productoIdStr);
            System.out.println("cantidad: " + cantidadStr);
            System.out.println("costo: " + costoStr);
            System.out.println("precioVenta: " + precioventastr);
            System.out.println("porcIva: " + porcIvastr);
            System.out.println("clienteId: " + clientestr);
            System.out.println("fechaFactura: " + fechastr);*/
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

            // Depuración: imprimir los valores convertidos
            System.out.println("Valores convertidos:");
            System.out.println("productosId: " + productosId);
            System.out.println("clienteId: " + clienteId);
            System.out.println("porcIva: " + porcIva);
            System.out.println("cantidad: " + cantidad);

            // Limpiar y convertir los valores de precio
            BigDecimal costo = new BigDecimal(costoStr.replaceAll("[^\\d.]", ""));
            BigDecimal costoVenta = new BigDecimal(precioventastr.replaceAll("[^\\d.]", ""));

            // Depuración: imprimir precios
            System.out.println("Precios:");
            System.out.println("costo: " + costo);
            System.out.println("costoVenta: " + costoVenta);

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
                BigDecimal totalCosto = cantidad.multiply(costo);
                BigDecimal totalVenta = cantidad.multiply(costoVenta);
                BigDecimal totalIva = totalCosto.multiply(BigDecimal.valueOf(porcIva)).divide(BigDecimal.valueOf(100));

                // Depuración: imprimir totales calculados
                System.out.println("Totales calculados:");
                System.out.println("totalCosto: " + totalCosto);
                System.out.println("totalIva: " + totalIva);
                System.out.println("totalVenta: " + totalVenta);

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

            // Calcular totales acumulados
            BigDecimal totalCostoAcumulado = BigDecimal.ZERO;
            BigDecimal totalIvaAcumulado = BigDecimal.ZERO;
            BigDecimal totalVentaAcumulado = BigDecimal.ZERO;

            for (Facturas factura : carrito) {
                totalCostoAcumulado = totalCostoAcumulado.add(factura.getTotalCosto());
                totalIvaAcumulado = totalIvaAcumulado.add(factura.getTotalIva());
                totalVentaAcumulado = totalVentaAcumulado.add(factura.getTotalVenta());
            }

            // Depuración: imprimir totales acumulados
            System.out.println("Totales acumulados:");
            System.out.println("totalCostoAcumulado: " + totalCostoAcumulado);
            System.out.println("totalIvaAcumulado: " + totalIvaAcumulado);
            System.out.println("totalVentaAcumulado: " + totalVentaAcumulado);

            // Guardar totales acumulados en la sesión, si no hay productos en el carrito
            if (carrito.isEmpty()) {
                request.getSession().setAttribute("totalCosto", BigDecimal.ZERO);
                request.getSession().setAttribute("totalIva", BigDecimal.ZERO);
                request.getSession().setAttribute("totalVentaAcumulado", BigDecimal.ZERO);
            } else {
                request.getSession().setAttribute("totalCosto", totalCostoAcumulado);
                request.getSession().setAttribute("totalIva", totalIvaAcumulado);
                request.getSession().setAttribute("totalVentaAcumulado", totalVentaAcumulado);
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

    private void registrarCompra2(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // IDatos del formulario
        //Se recupera de la getSession
        String fechastr = (String) request.getSession().getAttribute("fechaFactura");

        // Obtener el carrito de la sesión
        List<Facturas> carrito = (List<Facturas>) request.getSession().getAttribute("carrito");

        // Validar si el carrito es nulo o está vacío
        if (fechastr == null || carrito == null || carrito.isEmpty()) {
            // Error, no se puede proceder con la factura
            request.setAttribute("mensajeError", "Validar datos ingresado  Error! .");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
            return;
        }

        // Asumir que el Cliente es el mismo para todas las compras en el carrito
        Facturas primeraCompra = carrito.get(0);
        int idCliente = primeraCompra.getClienteId(); // Tomar el proveedor de la primera factura del carrito

        // Crear una nueva instancia de factura
        Facturas compraFinal = new Facturas();

        compraFinal.setClienteId(idCliente); // Asignar el Cliente
        compraFinal.setFecha(fechastr); // Asignar la fecha de la factura    

        // Inicializar el total de la factura
        BigDecimal totalCompra = BigDecimal.ZERO;

        // Lista de detalles de la factura
        List<DetallesFacturas> detallesCompra = new ArrayList<>();

        // Iterar sobre cada factura en el carrito para procesar los productos
        for (Facturas compra : carrito) {
            for (DetallesFacturas articulo : compra.getFacturas()) {
                // Calcular el subtotal del producto (cantidad * costo unitario)
                BigDecimal subtotalProducto = articulo.getPrecioCompra().multiply(articulo.getCantidad());

                // Sumar el subtotal al total de la factura
                totalCompra = totalCompra.add(subtotalProducto);

                // Agregar el detalle del producto a la lista de detalles
                detallesCompra.add(articulo);
            }
        }

        // Asignar el total calculado a la factura
        compraFinal.setTotalVenta(totalCompra);

        // Asignar los detalles de los productos a la factura final
        compraFinal.setFacturas(detallesCompra);

        // Registrar la factura en la base de datos con todos los productos
        DaoFacturas.registrarVenta(compraFinal);

        // Guardar el proveedor en la sesión para su uso posterior
        request.getSession().setAttribute("clienteId", idCliente);

        // Limpiar carrito y proveedor de la sesión
        request.getSession().removeAttribute("carrito");

        // Redirigir a la confirmación o página de compras vacía
        request.setAttribute("mensajeExito", "Compra registrada con éxito.");
        request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
    }

    private void registrarCompra(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recuperar datos de la sesión
        String fechastr = (String) request.getSession().getAttribute("fechaFactura");
        List<Facturas> carrito = (List<Facturas>) request.getSession().getAttribute("carrito");

        // Validar si el carrito o la fecha son nulos o están vacíos
        if (fechastr == null || carrito == null || carrito.isEmpty()) {
            // Error: datos insuficientes para proceder
            request.setAttribute("mensajeError", "Validar datos ingresados. Error en la fecha o carrito vacío.");
            request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
            return;
        }

        // Obtener el cliente de la primera factura del carrito (se asume que es el mismo cliente para todas las compras)
        Facturas primeraCompra = carrito.get(0);
        int clienteId = primeraCompra.getClienteId();

        // Imprimir valores recuperados de la sesión
        System.out.println("Fecha factura: " + fechastr);
        System.out.println("ID Cliente: " + clienteId);
        System.out.println("Tamaño del carrito: " + carrito.size());

        // Recuperar totales de la sesión
        BigDecimal totalCosto = (BigDecimal) request.getSession().getAttribute("totalCosto");
        BigDecimal totalIva = (BigDecimal) request.getSession().getAttribute("totalIva");
        BigDecimal totalVentaAcumulado = (BigDecimal) request.getSession().getAttribute("totalVentaAcumulado");

        // Asignar valores por defecto si son nulos
        if (totalCosto == null) {
            totalCosto = BigDecimal.ZERO;
        }
        if (totalIva == null) {
            totalIva = BigDecimal.ZERO;
        }
        if (totalVentaAcumulado == null) {
            totalVentaAcumulado = BigDecimal.ZERO;
        }

        // Crear una nueva instancia de Facturas para la compra final
        Facturas compraFinal = new Facturas();
        compraFinal.setClienteId(clienteId);  // Asignar cliente
        compraFinal.setFecha(fechastr);  // Asignar fecha de la factura

        // Asignar los totales recuperados de la sesión a la factura
        compraFinal.setTotalCosto(totalCosto);
        compraFinal.setTotalIva(totalIva);
        compraFinal.setTotalVenta(totalVentaAcumulado);

        // Lista de detalles de la compra
        List<DetallesFacturas> detallesCompra = new ArrayList<>();

        // Iterar sobre cada factura en el carrito
        for (Facturas compra : carrito) {
            // Iterar sobre cada producto en la factura
            for (DetallesFacturas articulo : compra.getFacturas()) {
                // Agregar el detalle del producto a la lista de detalles
                detallesCompra.add(articulo);

                // Imprimir información de cada artículo
                System.out.println("Producto ID: " + articulo.getProductosId());
                System.out.println("Cantidad: " + articulo.getCantidad());
                System.out.println("Precio compra: " + articulo.getPrecioCompra());
            }
        }

        // Asignar los detalles de los productos a la factura final
        compraFinal.setFacturas(detallesCompra);

        // Imprimir total de la compra antes de registrar en la base de datos
        System.out.println("Total de la compra (Costo): " + totalCosto);
        System.out.println("Total de la compra (IVA): " + totalIva);
        System.out.println("Total de la compra (Venta Acumulada): " + totalVentaAcumulado);
        System.out.println("Número de productos en la compra final: " + detallesCompra.size());

        // Intentar registrar la venta en la base de datos
        boolean ventaRegistrada = DaoFacturas.registrarVenta(compraFinal);

        if (ventaRegistrada) {
            // Guardar el cliente en la sesión para uso futuro
            request.getSession().setAttribute("clienteId", clienteId);

            // Después de registrar la venta, limpiar el carrito y los totales en la sesión
            request.getSession().removeAttribute("carrito");
            request.getSession().setAttribute("totalCosto", BigDecimal.ZERO);
            request.getSession().setAttribute("totalIva", BigDecimal.ZERO);
            request.getSession().setAttribute("totalVentaAcumulado", BigDecimal.ZERO);

            // Mensaje de éxito
            request.setAttribute("mensajeExito", "Compra registrada con éxito.");
        } else {
            // Si la venta no se pudo registrar, mostrar mensaje de error
            System.out.println("Error al registrar la compra en la base de datos.");
            request.setAttribute("mensajeError", "Error al registrar la compra. Por favor, intente de nuevo.");
        }

// Redirigir a la vista final
        request.getRequestDispatcher("Vistas/ListaFacturaVenta.jsp").forward(request, response);
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
