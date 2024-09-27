

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

        <!-- Sirven para actualizar la fecha automaticamnente -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <%--   <link href="Vistas/EstilosCSS/EstilosFacturasFinal.css" rel="stylesheet" type="text/css"/>--%>
        <link href="Vistas/EstilosCSS/EstilosFactUnico.css" rel="stylesheet" type="text/css"/>

        <!-- Estilos Modal -->
        <style>



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

                                <legend  class="tituloPrincipal"  style="margin-top: -30px">Datos de la Factura</legend>
                                <!-- Fecha de Factura -->
                                <fieldset>
                                    <div class="row align-items-center mb-2">
                                        <!-- Ajustamos el tamaño del label y le quitamos margen -->
                                        <div class="col-sm-4 pr-0">
                                            <label for="fechaFactura" class="formulario__label m-0">Fecha de Factura:</label>                                          
                                        </div>

                                        <!-- Ajustamos el tamaño del input y eliminamos margen innecesario -->
                                        <div class="col-sm-8 pl-0">
                                            <input type="date" class="formulario__input form-control" id="fechaFactura" name="fechaFactura" 
                                                   value="${param.fechaFactura != null ? param.fechaFactura : ''}" placeholder="fecha">
                                        </div>
                                    </div>
                                </fieldset>

                                <!-- Sección de búsqueda de proveedor -->
                                <fieldset>
                                    <legend class="w-auto px-2" style="font-size: 20px; font-weight: 600;">Datos del Proveedor</legend>
                                    <div class="row">
                                        <div class="col-sm-6 d-flex">

                                            <input type="text" class="formulario__input form-control" id="proveedorId" name="proveedorId" value="${param.proveedorId}" placeholder="Código">
                                            <%--  <button class="btn btn-outline-info mx-2 parte1" type="submit" name="accion" value="BuscarProveedor">Buscar</button>--%>
                                        </div>
                                        <div class="col-sm-6">
                                            <input style="font-weight: 600" type="text" class="formulario__input form-control"
                                                   id="proveedor" name="proveedor" value="${proveedorEncontrado.proveedor}" placeholder="Proveedor">
                                        </div>
                                    </div>
                                </fieldset>

                                <hr id="hr_1">

                                <!-- Sección de búsqueda de producto -->
                                <fieldset class="parte1">
                                    <legend class="w-auto px-2" style="font-size: 20px; font-weight: 600;">Datos del Producto</legend>
                                    <div class="row mb-3">

                                        <div class="col-sm-6 d-flex align-items-center ">
                                            <input type="text" class="form-control" id="productosId" name="productosId" placeholder="Ingrese Código" >
                                            <%--  <button class="btn btn-outline-info ml-2" type="submit" name="accion" value="BuscarProductos">Buscar</button>--%>
                                        </div>

                                        <div class="col-sm-6">
                                            <input type="text" class="form-control font-weight-bold" id="producto" name="producto" value="${listapr.productos}" placeholder="Producto" readonly>
                                        </div>
                                    </div>

                                    <!-- Información del producto -->
                                    <fieldset class="formulario__fieldset">
                                        <legend class="formulario__legend">Campos para Actualizar en el Inventario!</legend>

                                        <div class="row mb-2">
                                            <!-- Primera fila con tres columnas -->
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">$Costo Articulo</label>
                                                <input type="text" class="formulario__input form-control styled-input" 
                                                       id="precio" name="precio" value="${'$ '}" placeholder="$/0.00">
                                            </div>
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">$ Precio Venta</label>
                                                <input type="text" class="formulario__input form-control styled-input" 
                                                       id="precioVenta"    name="precioVenta" value="${'$ '}" placeholder="$/0.00">
                                            </div>
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">Cantidad</label>
                                                <input style="background: #ffe8a1; color: #00008B; text-align: center; font-size: 18px" 
                                                       type="number" class="formulario__input form-control styled-input"
                                                       id="cantidad" name="cantidad" placeholder="Cantid">
                                            </div>
                                        </div>
                                        <div class="row mb-2">
                                            <!-- Segunda fila con tres columnas -->
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">Stock</label>
                                                <input type="text"  id="cantidadDisponible" class="formulario__input form-control styled-input" 
                                                       name="stock"  placeholder="Stock">
                                            </div>
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">%Iva</label>
                                                <input type="text" class="formulario__input form-control styled-input" 
                                                       id="porcIva" name="porcIva" value="${'% '}"  placeholder="%Iva">
                                            </div>
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">Unidad Medida</label>
                                                <input type="text" class="formulario__input form-control styled-input" 
                                                       id="unidadmedidad" name="unidadmedidad" 
                                                       value="${nombreUnidadMedida}" placeholder="Unidad Med">
                                            </div>

                                        </div>
                                    </fieldset>
                                </fieldset>                               

                                <!-- Botón Agregar al Carrito -->
                                <div class="text-center parte1">
                                    <button id="btnAgregarAlCarrito" class="btn btn-primary" type="submit" name="accion" value="AgregarAlCarrito">Agregar al Carrito</button>
                                </div>
                            </form>

                            <!-- Mensaje Con Sweet Alert-->                     

                            <%
                                String mensajeExito = (String) request.getAttribute("mensajeExito");
                                String mensajeError = (String) request.getAttribute("mensajeError");
                            %>

                            <script>
                                <% if (mensajeExito != null) {%>
                                Swal.fire({
                                    title: 'Éxito',
                                    text: '<%= mensajeExito%>',
                                    icon: 'success',
                                    confirmButtonText: 'Aceptar'
                                });
                                <% } else if (mensajeError != null) {%>
                                Swal.fire({
                                    title: 'Error',
                                    text: '<%= mensajeError%>',
                                    icon: 'error',
                                    confirmButtonText: 'Aceptar'
                                });
                                <% }%>
                            </script>
                        </div>
                    </div>
                </article>

                <!-- Sección del carrito -->
                <article class="col-sm-7">
                    <div class="card">
                        <legend class="tituloPrincipal">Detalles</legend>
                        <div class="card-body">
                            <div class="table-responsive" style="margin-top: -30px">
                                <table class="table table-striped table-hover sticky-top">
                                    <thead class="table-header">
                                        <tr class="text-center">
                                            <th>ID Producto</th>
                                            <th>Nombre</th>
                                            <th>Cantidad</th>
                                            <th>Costo Compra</th>
                                            <th>Precio Venta</th>
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
                                            <td colspan="7">No hay productos en el carrito.</td>
                                        </tr>
                                        <%
                                        } else {
                                            for (Compras compra : carrito) {
                                                List<ComprasProductos> articulos = compra.getArticulos();
                                                if (articulos != null) {
                                                    for (ComprasProductos detalle : articulos) {
                                                        BigDecimal cantidad = detalle.getCantidad();
                                                        BigDecimal costoArticulo = detalle.getCostoArticulo();
                                                        BigDecimal CostoVenta = detalle.getPrecioVenta();
                                                        BigDecimal subtotal = cantidad.multiply(costoArticulo);
                                                        total = total.add(subtotal);
                                        %>
                                        <tr class="text-center">
                                            <td><%= detalle.getProductosId()%></td>
                                            <td><%= DaoProductos.obtenerNombreProductos(detalle.getProductosId())%></td>
                                            <td><%= cantidad%></td>
                                            <td><%= currencyFormat.format(costoArticulo)%></td>
                                            <td><%= currencyFormat.format(CostoVenta)%></td>
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
                                        <tr class="line-before-footer"  style="background-color: #fff">
                                            <td colspan="7"></td> <!-- Asegúrate de que el número de columnas coincide con el de tu tabla -->
                                        </tr>
                                        <tr class="text-right " style="background-color: #ffecb5">
                                            <td colspan="2"><strong>Total Factura:</strong></td>
                                            <td colspan="2"><%= currencyFormat.format(total)%></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>                       


                            <div class="d-flex justify-content-center mt-3">
                                <!-- Botón para generar compra -->
                                <form action="ControladorCompras" onclick="print()" method="POST" class="mr-2">
                                    <input type="hidden" name="accion" value="GenerarCompra">
                                    <button class="btn btn-success btn-custom" type="submit">Facturar</button>
                                </form>

                                <!-- Botón para cancelar la venta -->
                                <form action="ControladorCompras" method="POST">
                                    <input type="hidden" name="accion" value="CancelarVenta">
                                    <button class="btn btn-secondary btn-custom" type="submit">Cancelar</button>
                                </form>
                            </div>


                            <!-- Mensaje de error si aplica -->
                            <h1>${mensajeError}</h1>
                        </div>
                    </div>
                    <p class="text-center pt-5">         Cada que se realiza una compra, se ingresa al sistema para actulizar inventarios y los precios del articulo </p>
                </article>

            </section>

        </main>

        <footer class="text-center mt-4 parte1">
            <p>&copy; 2024 Sistemas JB.</p>
        </footer>



        <!-- Funsion Buscar producto -->
        <script>
            $(document).ready(function () {
                let typingTimer; // Timer identifier
                const doneTypingInterval = 500; // Tiempo en milisegundos (500 ms = 0.5 segundos)

                $('#productosId').on('input', function () {
                    clearTimeout(typingTimer); // Limpiar el temporizador si el usuario sigue escribiendo
                    const codigo = $(this).val();

                    // Configurar un nuevo temporizador para la búsqueda
                    typingTimer = setTimeout(function () {
                        // Solo proceder si el campo no está vacío
                        if (codigo) {
                            $.ajax({
                                url: "ControladorCompras?accion=BuscarProductos&productosId=" + codigo,
                                method: "GET",
                                dataType: 'json',
                                success: function (data) {
                                    if (data.nombre) {
                                        $('#producto').val(data.nombre); // Asigna el nombre del producto
                                        $('#precio').val(data.precioCompra); // Asigna el precio de compra
                                        $('#precioVenta').val(data.precioVenta); // Asigna el precio de venta
                                        $('#cantidadDisponible').val(data.cantidadDisponible); // Asigna el stock
                                        $('#porcIva').val(data.porcIva); // Asigna el porcentaje de IVA
                                        $('#unidadmedidad').val(data.nombreUnidad); // Asigna la unidad de medida

                                        // Mover el foco al campo de cantidad
                                        $('#cantidad').focus();
                                    } else {
                                        // Si no se encuentra el producto, limpiar los campos
                                        $('#producto').val('');
                                        $('#precio').val('');
                                        $('#precioVenta').val('');
                                        $('#cantidadDisponible').val('');
                                        $('#porcIva').val('');
                                        $('#unidadmedidad').val('');
                                    }
                                },
                                error: function (xhr, status, error) {
                                    console.error('Error al obtener el producto:', error);
                                }
                            });
                        } else {
                            // Si el código está vacío, limpiar los campos
                            $('#producto').val('');
                            $('#precio').val('');
                            $('#precioVenta').val('');
                            $('#cantidadDisponible').val('');
                            $('#porcIva').val('');
                            $('#unidadmedidad').val('');
                        }
                    }, doneTypingInterval); // Espera antes de hacer la búsqueda
                });
            });





            /*-------------------------------------------------------------------------------------*/
            // Funsion para buscar Proveedor
            $(document).ready(function () {
                $('#proveedorId').on('input', function () {
                    var codigo = $(this).val();

                    if (codigo) {
                        $.ajax({
                            url: "ControladorCompras?accion=BuscarProveedor&proveedorId=" + codigo,
                            method: "GET",
                            dataType: 'json',
                            success: function (data) {
                                if (data.nombre2) {
                                    $('#proveedor').val(data.nombre2); // Asigna el nombre del producto

                                } else {
                                    $('#proveedor').val(''); // Limpia el campo si no se encuentra el producto

                                }
                            },
                            error: function (xhr, status, error) {
                                console.error('Error al obtener el proveedor', error);
                            }
                        });
                    } else {
                        $('#proveedor').val(''); // Limpia el campo si el código está vacío

                    }
                });
            });





        </script>

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
                var accion = event.submitter.value;

                if (accion === "AgregarAlCarrito") {
                    var fechaFactura = document.getElementById("fechaFactura");
                    var proveedorId = document.getElementById("proveedorId");
                    var proveedor = document.querySelector('input[name="proveedor"]');
                    var productosId = document.querySelector('input[name="productosId"]');
                    var producto = document.querySelector('input[name="producto"]');
                    var precio = document.querySelector('input[name="precio"]');
                    var cantidad2 = document.querySelector('input[name="cantidad"]');
                    var stock = document.querySelector('input[name="stock"]');
                    var porcIva = document.querySelector('input[name="porcIva"]');
                    var unidad = document.querySelector('input[name="unidadmedidad"]');

                    var campos = [fechaFactura, proveedorId, proveedor, productosId, producto, precio, cantidad2, stock, porcIva, unidad];
                    var formularioValido = true;

                    campos.forEach(function (campo) {
                        if (!campo.value) {
                            campo.classList.add("campo-vacio");
                            formularioValido = false;
                        } else {
                            campo.classList.remove("campo-vacio");
                        }
                    });

                    if (!formularioValido) {
                        event.preventDefault(); // Prevent form submission
                        Swal.fire({
                            icon: 'error',
                            title: 'Campos obligatorios',
                            text: 'Por favor, completa todos los campos obligatorios.',
                            confirmButtonText: 'Aceptar'
                        });
                    }
                }
            });


        </script>



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script type="text/javascript" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    </body>
</html>
