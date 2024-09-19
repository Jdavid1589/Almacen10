

<%@page import="Modelo.DetallesFacturas"%>
<%@page import="Modelo.Facturas"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Modelo.Compras"%>
<%@page import="Modelo.ComprasProductos"%>

<%@page import="Persistencia.DaoProductos"%>
<%@page import="Modelo.Productos"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.UnidadMedida"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Factura Venta</title>      

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"
              integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" 
              crossorigin="anonymous" referrerpolicy="no-referrer">
        <!-- DataTable -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.3.3/css/buttons.bootstrap5.min.css">

        <!-- Incluye los archivos CSS de Bootstrap -->  
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-...." crossorigin="anonymous" />

        <link href="Vistas/EstilosCSS/EstilosFacturasFinal.css" rel="stylesheet" type="text/css"/>

        <!-- Sirven para actualizar la fecha automaticamnente -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>


        <!-- Estilos -->
        <style>


            /* Fondo general de la factura */
            body {
                background-color: #f7f9fb;
                font-family: 'Poppins', sans-serif;
            }

            /* Mejora de la tarjeta */
            .card {
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border: none;
            }



            /* Divider customizado */
            #hr_1 {
                border: 1px solid #5A9BD5;
                width: 100%;
            }

            /* Inputs y labels */
            .formulario__input {
                border-radius: 5px;
                box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
                border: 1px solid #ced4da;
                transition: all 0.3s ease;
            }

            .formulario__input:focus {
                border-color: #5A9BD5;
                box-shadow: 0 0 5px rgba(90, 155, 213, 0.3);
            }

            .formulario__label {
                font-weight: 500;
                color: #555;
            }

            /* Botones personalizados */
            .btn-primary {
                background-color: #5A9BD5;
                border: none;
                border-radius: 50px;
                padding: 10px 20px;
                font-size: 16px;
                transition: background-color 0.3s ease;
            }

            .btn-primary:hover {
                background-color: #41729F;
            }

            /* Estilo del botón de búsqueda */
            .btn-outline-info {
                border-radius: 50px;
                transition: background-color 0.3s ease, color 0.3s ease;
            }

            .btn-outline-info:hover {
                background-color: #5A9BD5;
                color: #fff;
            }

            /* Leyendas y subtítulos */
            legend {
                font-size: 18px;
                text-align: center;
                color: #333;
                border-bottom: 2px solid #5A9BD5;
                padding-bottom: 10px;
            }

            /* Mejora del espacio entre campos */
            .form-group {
                margin-bottom: 15px;
            }

            /* Responsive en pantallas más pequeñas */
            @media (max-width: 768px) {
                .col-sm-3, .col-sm-6 {
                    margin-bottom: 15px;
                }
            }

            /* Sombras en las tarjetas */
            .card-body {
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 25px;
            }

            /* Estilo de footer */
            tfoot tr {
                background-color: #ffecb5;
                color: #000;
            }

            .tituloPrincipal {
                font-size: 22px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }
            .tituloPrincipal2 {
                text-transform: uppercase;
                padding: 10px; /* Reducir el padding */
                font-size: 25px;
                font-weight: bold;
                color: #333;
                letter-spacing: 0.5px;
            }

            .table-hover tbody tr:hover {
                background-color: #f8f9fa;
            }

            .table thead th {
                background-color: #343a40;
                color: white;
            }

            .line-before-footer {
                border-top: 2px solid #dee2e6;
            }

            hr#hr_1 {
                border-top: 1px solid #007bff;
            }

            button.btn-success {
                padding: 10px 20px;
                font-size: 18px;
            }

            .card-body {
                padding: 30px;
                background-color: #f8f9fa;
                border-radius: 10px;
            }
            h1, h2 {
                font-weight: 700;
                color: #0056b3; /* Azul elegante */
            }




        </style>

    </head>

    <body>

        <header class="container-fluid mt-4">
            <!-- Encabezado de la factura -->
            <div class="row">
                <div class="col-sm-12 text-center mb-1">
                    <br>
                    <h2>Factura de Venta</h2>
                    <hr id="hr_1">
                </div>
            </div>
        </header>



        <main class="container-fluid">
            <section class="row">
                <article class="col-sm-5">
                    <div class="card">
                        <div class="card-body">
                            <form id="formAgregarProducto" action="ControladorFacturaventa" method="POST" autocomplete="off" class="custom-form">
                                <fieldset>
                                    <legend class="tituloPrincipal">Datos de la Factura</legend>
                                    <div class="row mb-2">
                                        <div class="col-sm-6">
                                            <label for="fechaFactura" class="formulario__label">Fecha de Factura:</label>
                                            <input type="date" class="formulario__input form-control" id="fechaFactura" name="fechaFactura" value="${param.fechaFactura != null ? param.fechaFactura : ''}" placeholder="fecha">
                                        </div>
                                    </div>
                                </fieldset>

                                <fieldset class="border p-3 rounded shadow-sm">
                                    <legend class="w-auto px-2" style="font-size: 20px; font-weight: 600;">Datos del Cliente</legend>
                                    <div class="row mb-2">
                                        <div class="col-sm-6 d-flex align-items-center">
                                            <input type="text" class="form-control" id="clienteId" name="clienteId" value="${param.clienteId}" placeholder="Ingrese Nombres">
                                            <button class="btn btn-outline-primary ml-2" type="submit" name="accion" value="BuscarCliente">Buscar</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control font-weight-bold" name="nombres" value="${clienteEncontrado.nombres}" placeholder="Cliente">
                                        </div>
                                    </div>
                                </fieldset>


                                <hr id="hr_1">

                                <fieldset class="border p-3 rounded shadow-sm mt-4">
                                    <legend class="w-auto px-2" style="font-size: 20px; font-weight: 600;">Datos del Producto</legend>
                                    <div class="row mb-3">
                                        <div class="col-sm-6 d-flex align-items-center">
                                            <input type="text" class="form-control" name="productosId" placeholder="Ingrese Código" value="${param.productosId}">
                                            <button class="btn btn-outline-primary ml-2" type="submit" name="accion" value="BuscarProductos">Buscar</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <input type="text" class="form-control font-weight-bold" name="producto" value="${listapr.productos}" placeholder="Producto">
                                        </div>
                                    </div>
                                    <div class="row mb-2">
                                        <div class="col-sm-3">
                                            <label class="formulario__label">Precio Unidad</label>
                                            <input type="text" class="formulario__input form-control" name="precioCompra" value="${'$ '}${listapr.precioCompra}" placeholder="$/0.00">
                                        </div>
                                        <div class="col-sm-3">
                                            <label class="formulario__label">Cantidad</label>
                                            <input style="background: #fff" type="number" class="formulario__input form-control" name="cantidad" placeholder="">
                                        </div>
                                        <div class="col-sm-3">
                                            <label class="formulario__label">Stock</label>
                                            <input type="text" class="formulario__input form-control" name="stock" value="${listapr.cantidadDisponible}" placeholder="Stock">
                                        </div>
                                        <div class="col-sm-3">
                                            <label class="formulario__label">Precio Venta</label>
                                            <input type="text" class="formulario__input form-control" name="precioVenta" value="${'$ '}${listapr.precioVenta}" placeholder="$/0.00">
                                        </div>
                                        <div class="col-sm-3">
                                            <label class="formulario__label">%Iva</label>
                                            <input type="text" class="formulario__input form-control" name="porcIva" value="${listapr.porcIva}" placeholder="%Iva">
                                        </div>
                                    </div>
                                </fieldset>

                                <hr class="parte1" id="hr_1">

                                <div class="text-center parte1">
                                    <button id="btnAgregarAlCarrito" class="btn btn-primary" type="submit" name="accion" value="AgregarAlCarrito2">Agregar al Carrito</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </article>



                <!-- Sección del carrito -->
                <article class="col-sm-7">
                    <div class="card">
                        <legend class="tituloPrincipal">Carrito de Ventas</legend>
                        <div class="card-body">

                            <div class="table-responsive">
                                <table class="table table-striped table-hover sticky-top">
                                    <thead class="table-header">
                                        <tr>
                                            <th>ID Producto</th>
                                            <th>Nombre</th>
                                            <th>Cantidad</th>
                                            <th>Precio Unitario</th>
                                            <th>Subtotal</th>
                                            <th>IVA</th> <!-- Nueva columna para IVA -->
                                            <th>Precio Neto</th> <!-- Nueva columna para Precio Neto -->
                                            <th class="parte1">Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
                                            BigDecimal totalCosto = BigDecimal.ZERO;
                                            BigDecimal totalIva = BigDecimal.ZERO;
                                            BigDecimal totalPrecioNeto = BigDecimal.ZERO;
                                            BigDecimal totalVenta = BigDecimal.ZERO;

                                            List<Facturas> carrito = (List<Facturas>) request.getSession().getAttribute("carrito");
                                            if (carrito == null || carrito.isEmpty()) {
                                        %>
                                        <tr>
                                            <td colspan="7">No hay productos en el carrito.</td>
                                        </tr>
                                        <%
                                        } else {
                                            for (Facturas venta : carrito) {
                                                List<DetallesFacturas> facturas = venta.getFacturas();
                                                if (facturas != null) {
                                                    for (DetallesFacturas detalle : facturas) {
                                                        BigDecimal cantidad = detalle.getCantidad();
                                                        BigDecimal precioUnitario = detalle.getPrecioCompra(); // Precio de compra
                                                        BigDecimal subtotal = cantidad.multiply(precioUnitario); // Subtotal sin IVA
                                                        int porcIva = detalle.getPorcIva(); // % IVA
                                                        BigDecimal iva = subtotal.multiply(BigDecimal.valueOf(porcIva)).divide(BigDecimal.valueOf(100)); // Cálculo del IVA
                                                        BigDecimal precioNeto = subtotal.add(iva); // Subtotal + IVA (Precio Neto)

                                                        // Acumuladores de los totales
                                                        totalCosto = totalCosto.add(subtotal);
                                                        totalIva = totalIva.add(iva);
                                                        totalPrecioNeto = totalPrecioNeto.add(precioNeto);
                                                        totalVenta = totalVenta.add(precioNeto); // Actualizar totalVenta correctamente

                                        %>
                                        <tr>
                                            <td><%= detalle.getProductosId()%></td>
                                            <td><%= DaoProductos.obtenerNombreProductos(detalle.getProductosId())%></td>
                                            <td><%= cantidad%></td>
                                            <td><%= currencyFormat.format(precioUnitario)%></td>
                                            <td><%= currencyFormat.format(subtotal)%></td>
                                            <td><%= currencyFormat.format(iva)%></td> <!-- Mostrar el IVA -->
                                            <td><%= currencyFormat.format(precioNeto)%></td> <!-- Mostrar el precio neto -->
                                            <td class="parte1">
                                                <a href="ControladorFacturaventa?accion=Eliminar&id=<%= detalle.getFacturasId()%>">
                                                    <i class="fas fa-trash-alt"></i>
                                                </a>
                                            </td>
                                        </tr>
                                        <%
                                                        }
                                                    }
                                                }
                                            }
                                        %>
                                    </tbody>
                                    <tfoot>
                                        <tr class="line-before-footer" style="background-color: #fff">
                                            <td colspan="8"></td> <!-- Asegúrate de que el número de columnas coincide con el de tu tabla -->
                                        </tr>
                                        <tr class="text-right " style="background-color: #fff">
                                            <td colspan="3"><strong>Total Costo:</strong></td>
                                            <td colspan="2"><%= currencyFormat.format(totalCosto)%></td>
                                        </tr>
                                        <tr class="text-right " style="background-color: #fff">
                                            <td colspan="3"><strong>Total IVA:</strong></td>
                                            <td colspan="2"><%= currencyFormat.format(totalIva)%></td>
                                        </tr>

                                        <tr class="text-right " style="background-color: #ffe8a1">
                                            <td colspan="3"><strong>Total Venta:</strong></td>
                                            <td colspan="2"><%= currencyFormat.format(totalVenta)%></td>
                                        </tr>
                                    </tfoot>
                                </table>


                            </div>

                            <!-- Botón para generar compra -->
                            <form action="ControladorCompras" onclick="print()" method="POST">
                                <input type="hidden" name="accion" value="GenerarCompra">
                                <div class="text-center parte1">
                                    <button class="btn btn-success" type="submit">Generar Compra</button>
                                </div>
                            </form>

                            <!-- Mensaje de error si aplica -->
                            <h1>${mensajeError}</h1>
                        </div>
                    </div>
                </article>
            </section>
        </main>

        <footer class="text-center mt-4 parte1">
            <p>&copy; 2024 Empresa XYZ. Todos los derechos reservados.</p>
        </footer>






        <script>



            //Funsion para inicializar la fecha 

            document.addEventListener('DOMContentLoaded', function () {
                flatpickr("#fechaFactura", {
                    dateFormat: "Y-m-d", // Formato de fecha
                    altInput: true, // Muestra un campo de texto alternativo
                    altFormat: "F j, Y", // Formato alternativo que se muestra al usuario
                    defaultDate: "today" // Fecha por defecto (hoy)
                });
            });


            document.getElementById("formAgregarProducto").addEventListener("submit", function (event) {
                // Obtener el valor del botón submit
                var accion = event.submitter.value;

                // Solo validar los campos si la acción es "AgregarAlCarrito"
                if (accion === "AgregarAlCarrito") {
                    // Obtener los campos del formulario
                    var fechaFactura = document.getElementById("fechaFactura");
                    var proveedorId = document.getElementById("proveedorId");
                    var proveedor = document.querySelector('input[name="proveedor"]');
                    var productosId = document.querySelector('input[name="productosId"]');
                    var producto = document.querySelector('input[name="producto"]');
                    var precio = document.querySelector('input[name="precio"]');
                    var cantidad = document.querySelector('input[name="cantidad"]');
                    var stock = document.querySelector('input[name="stock"]');
                    var porcIva = document.querySelector('input[name="porcIva"]');

                    // Lista de todos los campos a validar
                    var campos = [fechaFactura, proveedorId, proveedor, productosId, producto, precio, cantidad, stock, porcIva];
                    var formularioValido = true;

                    // Validar cada campo
                    campos.forEach(function (campo) {
                        if (!campo.value) {
                            // Si el campo está vacío, agrega la clase "campo-vacio" y establece formularioValido como falso
                            campo.classList.add("campo-vacio");
                            formularioValido = false;
                        } else {
                            // Si el campo tiene valor, quita la clase "campo-vacio" si está presente
                            campo.classList.remove("campo-vacio");
                        }
                    });

                    // Si el formulario no es válido, evita que se envíe y muestra SweetAlert
                    if (!formularioValido) {
                        event.preventDefault(); // Evitar que el formulario se envíe
                        Swal.fire({
                            icon: 'error', // Tipo de alerta
                            title: 'Campos obligatorios', // Título del mensaje
                            text: 'Por favor, completa todos los campos obligatorios.', // Texto del mensaje
                            confirmButtonText: 'Aceptar'
                        });
                    }

                }
            });

        </script>

        <!-- Enlace para libreria Sweet Alert -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script type="text/javascript" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- DataTables JS -->
        <script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap4.min.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.7.1/js/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
        <script type="text/javascript" src="https://cdn.datatables.net/buttons/1.7.1/js/buttons.html5.min.js"></script>


    </body>
</html>
