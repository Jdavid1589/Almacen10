

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

        <title>Factura Compras</title>      

        <!-- Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"
              integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" 
              crossorigin="anonymous" referrerpolicy="no-referrer">
        
        <!-- SweetAlert CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <!-- SweetAlert JS -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <!-- Incluye los archivos CSS de Bootstrap -->  
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-...." crossorigin="anonymous" />
        <!--link href="https://cdn.jsdelivr.net/npm/bootstrap@5.5.0/dist/css/bootstrap.min.css" rel="stylesheet"-->


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

            .campo-vacio{
                border: 2px solid red
            }



        </style>


    </head>

    <body>

        <header class="container-fluid mt-4">
            <!-- Encabezado de la factura -->
            <div class="row">
                <div class="col-sm-12 text-center mb-1">
                    <br>
                    <h2>Factura de Compra</h2>
                    <hr id="hr_1">
                </div>
            </div>
        </header>

        <main class="container-fluid ">
            <section class="row">
                <article class="col-sm-5">
                    <div class="card">
                        <div class="card-body">
                            <!-- Sección de datos de la factura -->
                            <form id="formAgregarProducto" action="ControladorCompras" method="POST" autocomplete="off" class="custom-form">
                                <!-- Fecha de Factura -->
                                <fieldset>
                                    <legend  class="tituloPrincipal">Datos de la Factura</legend>
                                    <div class="row mb-2">
                                        <div class="col-sm-6">
                                            <label for="fechaFactura" class="formulario__label">Fecha de Factura:</label>
                                            <input type="date" class="formulario__input form-control" id="fechaFactura" name="fechaFactura" value="${param.fechaFactura != null ? param.fechaFactura : ''}" placeholder="fecha">
                                        </div>
                                    </div>
                                </fieldset>

                                <!-- Sección de búsqueda de proveedor -->
                                <fieldset>
                                    <legend class="w-auto px-2" style="font-size: 20px; font-weight: 600;">Datos del Proveedor</legend>
                                    <div class="row">
                                        <div class="col-sm-6 d-flex">
                                            <input type="text" class="formulario__input form-control" id="proveedorId" name="proveedorId" value="${param.proveedorId}" placeholder="Código">
                                            <button class="btn btn-outline-info mx-2 parte1" type="submit" name="accion" value="BuscarProveedor">Buscar</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <input style="font-weight: 600" type="text" class="formulario__input form-control" name="proveedor" value="${proveedorEncontrado.proveedor}" placeholder="Proveedor">
                                        </div>
                                    </div>
                                </fieldset>

                                <hr id="hr_1">

                                <!-- Sección de búsqueda de producto -->
                                <fieldset class="parte1">
                                    <legend class="w-auto px-2" style="font-size: 20px; font-weight: 600;">Datos del Producto</legend>
                                    <div class="row mb-3">
                                        <div class="col-sm-6 d-flex">
                                            <input type="text" class="formulario__input form-control" name="productosId" placeholder="Ingrese Código" value="${param.productosId}">
                                            <button class="btn btn-outline-info mx-2" type="submit" name="accion" value="BuscarProductos">Buscar</button>
                                        </div>
                                        <div class="col-sm-6">
                                            <input type="text" class="formulario__input form-control " style="font-weight: 500;" name="producto" value="${listapr.productos}" placeholder="Producto">
                                        </div>
                                    </div>

                                    <!-- Información del producto -->
                                    <div class="row mb-2">
                                        <div class="col-sm-3">
                                            <label class="formulario__label">Precio Unidad</label>
                                            <input type="text" class="formulario__input form-control" name="precio" value="${'$ '}${listapr.precioCompra}" placeholder="$/0.00">
                                        </div>
                                        <div class="col-sm-3">
                                            <label class="formulario__label">Cantidad</label>
                                            <input style="background: #fff"  type="number" class="formulario__input form-control" name="cantidad" placeholder="">
                                        </div>
                                        <div class="col-sm-3">
                                            <label class="formulario__label">Stock</label>
                                            <input type="text" class="formulario__input form-control" name="stock" value="${listapr.cantidadDisponible}" placeholder="Stock">
                                        </div>
                                        <div class="col-sm-3">
                                            <label class="formulario__label">%Iva</label>
                                            <input type="text" class="formulario__input form-control" name="porcIva" value="${listapr.porcIva}" placeholder="%Iva">
                                        </div>
                                    </div>
                                </fieldset>

                                <hr class="parte1"  id="hr_1">

                                <!-- Botón Agregar al Carrito -->
                                <div class="text-center parte1">
                                    <button id="btnAgregarAlCarrito" class="btn btn-primary" type="submit" name="accion" value="AgregarAlCarrito">Agregar al Carrito</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </article>

                <!-- Sección del carrito -->
                <article class="col-sm-7">
                    <div class="card">
                        <legend class="tituloPrincipal">Carrito de Compras</legend>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-hover sticky-top">
                                    <thead class="table-header">
                                        <tr class="text-center">
                                            <th>ID Producto</th>
                                            <th>Nombre</th>
                                            <th>Cantidad</th>
                                            <th>Precio Unitario</th>
                                            <th>Subtotal</th>
                                            <th class="parte1">Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
                                            BigDecimal total = BigDecimal.ZERO;
                                            List<Compras> carrito = (List<Compras>) request.getSession().getAttribute("carrito");
                                            if (carrito == null || carrito.isEmpty()) {
                                        %>
                                        <tr class="text-center">
                                            <td colspan="5">No hay productos en el carrito.</td>
                                        </tr>
                                        <%
                                        } else {
                                            for (Compras compra : carrito) {
                                                List<ComprasProductos> articulos = compra.getArticulos();
                                                if (articulos != null) {
                                                    for (ComprasProductos detalle : articulos) {
                                                        BigDecimal cantidad = detalle.getCantidad();
                                                        BigDecimal costoArticulo = detalle.getCostoArticulo();
                                                        BigDecimal subtotal = cantidad.multiply(costoArticulo);
                                                        total = total.add(subtotal);
                                        %>
                                        <tr class="text-center">
                                            <td><%= detalle.getProductosId()%></td>
                                            <td><%= DaoProductos.obtenerNombreProductos(detalle.getProductosId())%></td>
                                            <td><%= cantidad%></td>
                                            <td><%= currencyFormat.format(costoArticulo)%></td>
                                            <td><%= currencyFormat.format(subtotal)%></td>
                                            <td class="parte1">
                                                <a href="ControladorCompras?accion=Eliminar&id=<%= detalle.getIdCompraProducto()%>">
                                                    <i class="fas fa-trash-alt"></i> <!-- Ícono de papelera -->
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
                                        <tr class="line-before-footer">
                                            <td colspan="6"></td> <!-- Asegúrate de que el número de columnas coincide con el de tu tabla -->
                                        </tr>
                                        <tr class="text-right " style="background-color: #ffecb5">
                                            <td colspan="2"><strong>Total Factura:</strong></td>
                                            <td colspan="2"><%= currencyFormat.format(total)%></td>
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
            <p>&copy; 2024 Empresa JB. Todos los derechos reservados.</p>
        </footer>





        <!-- Script Limpiar campos  -->
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


        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script type="text/javascript" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
     

    </body>
</html>
