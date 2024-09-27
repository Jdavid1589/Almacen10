

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

        <!-- Incluye los archivos CSS de Bootstrap -->  
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha512-...." crossorigin="anonymous" />

        <!-- SweetAlert CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
        <!-- SweetAlert JS -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

        <!-- Sirven para actualizar la fecha automaticamnente -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Estilos Personalizados -->
        <%--   <link href="Vistas/EstilosCSS/EstilosFacturasFinal.css" rel="stylesheet" type="text/css"/>--%>
        <link href="Vistas/EstilosCSS/EstilosFactUnico.css" rel="stylesheet" type="text/css"/>


    </head>

    <body>

        <header class="container-fluid mt-4">
            <!-- Encabezado de la factura -->
            <div class="row">
                <div class="col-sm-12 text-center mb-1">
                    <br>
                    <h2>Distribuidora Nogales </h2>

                    <hr id="hr_1">
                </div>
            </div>
        </header>

        <main class="container-fluid">
            <section class="row">
                <article class="col-sm-5">
                    <div class="card">
                        <div class="card-body">
                            <form id="formAgregarVenta" action="ControladorFacturaventa" method="POST" autocomplete="off" >

                                <legend class="tituloPrincipal " style="margin-top: -30px">Datos de la Factura</legend>

                                <details class="parte1">
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
                                    <summary style="font-size: 20px; font-weight: 600;">
                                        Datos del Cliente
                                        <i class="fas fa-chevron-down"></i>
                                    </summary>

                                    <fieldset class="border p-3 rounded shadow-sm">
                                        <div class="d-flex">
                                            <legend class="w-auto px-2" style="font-size: 20px; font-weight: 600;">Datos del Cliente</legend>
                                            <span class="btn btn-primary2 text-warning my-2" title="Registrar Cliente Nuevo" data-toggle="modal" data-target="#registroCliente">
                                                <i class="fas fa-plus"></i> Nuevo
                                            </span>
                                        </div>

                                        <div class="row mb-2">
                                            <div class="col-sm-6 d-flex align-items-center">
                                                <input type="text" class="form-control parte1" id="clienteId" name="clienteId" 
                                                       value="${param.clienteId != null ? param.clienteId : 1}" 
                                                       placeholder="Ingrese Nombres">
                                                <button class="btn btn-outline-info ml-2 parte1" type="submit" name="accion" value="BuscarCliente">Buscar</button>
                                            </div>

                                            <div class="col-sm-6">
                                                <input type="text" class="form-control font-weight-bold" name="nombres" 
                                                       value="${clienteEncontrado.nombres}" placeholder="Cliente">
                                            </div>
                                        </div>
                                    </fieldset>
                                </details>
                                <hr id="hr_1">

                                <!-- Datos de Producto -->

                                <fieldset class="border p-3 rounded shadow-sm mt-4 parte1">
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

                                    <fieldset class="formulario__fieldset">
                                        <legend class="formulario__legend">--</legend>
                                        <div class="row mb-2">
                                            <!-- Primera fila con tres columnas -->
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">Precio Compra</label>
                                                <input type="text" id="precioCompra" class="formulario__input form-control styled-input" name="precioCompra" placeholder="$/0.00">
                                            </div>
                                            <div class="col-sm-3">
                                                <label class="formulario__label styled-label">Cantidad</label>
                                                <input style="background: #ffe8a1; color: #00008B; text-align: center; font-size: 18px" 
                                                       type="number" class="formulario__input form-control styled-input"
                                                       id="cantidad" name="cantidad" placeholder="# Cant">
                                            </div>
                                            <div class="col-sm-4">
                                                <label class="formulario__label styled-label">Stock</label>
                                                <input type="text" id="cantidadDisponible" class="formulario__input form-control styled-input" name="cantidadDisponible" placeholder="Stock">
                                            </div>
                                            <div class="row mb-2">
                                                <div class="col-sm-4">
                                                    <label class="formulario__label styled-label">Precio Venta</label>
                                                    <input type="text" id="precioVenta" class="formulario__input form-control styled-input" name="precioVenta" placeholder="$/0.00">
                                                </div>
                                                <div class="col-sm-4">
                                                    <label class="formulario__label styled-label">%Iva</label>
                                                    <input type="text" id="porcIva" class="formulario__input form-control styled-input" name="porcIva" placeholder="%Iva">
                                                </div>
                                            </div>

                                        </div>
                                    </fieldset>
                                </fieldset>                            

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
                        <legend class="tituloPrincipal">Detalles</legend>
                        <div class="card-body">

                            <div class="row align-items-center mb-2">
                                <!-- Campo Fecha de Factura -->
                                <div class="col-sm-3 pr-0 m-0" style="padding: 0; margin-top: -35px">
                                    <label for="fechaFactura" class="formulario__label m-0">Fecha de Factura:</label>
                                </div>
                                <div class="col-sm-3 pr-0 m-0 " style="padding: 0; margin-top: -35px; border: none">
                                    <input type="date" class="formulario__input form-control" id="fechaFactura" name="fechaFactura" value="${param.fechaFactura != null ? param.fechaFactura : ''}" placeholder="Fecha">
                                </div>
                                <!-- Campo Cliente -->
                                <div class="col-sm-2 pr-0 m-0 " style="padding: 0; margin-top: -35px">
                                    <label for="nombres" class="formulario__label m-0 pr-0">Cliente:</label>
                                </div>
                                <div class="col-sm-3 pr-0 m-0 " style="padding: 0; margin-top: -35px">
                                    <input type="text" class="form-control font-weight-bold" name="nombres" value="${clienteEncontrado.nombres}" placeholder="Cliente">
                                </div>
                            </div>

                            <!-- Inicio Tabla -->

                            <div class="table-responsive">
                                <table class="table table-striped table-hover sticky-top">
                                    <thead class="table-header">
                                        <tr>
                                            <th class="parte1">ID Producto</th>
                                            <th>Nombre</th>
                                            <th>Cantidad</th>
                                            <th class="parte1">Precio Unitario</th>
                                            <th class="parte1">Subtotal</th>
                                            <th class="parte1">IVA</th> <!-- Nueva columna para IVA -->
                                            <th>Total</th> <!-- Nueva columna para Precio Neto -->
                                            <th class="parte1">Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
                                            BigDecimal totalCosto = BigDecimal.ZERO;
                                            BigDecimal totalIva = BigDecimal.ZERO;
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
                                                        BigDecimal precioUnitario = detalle.getPrecioVenta(); // Precio de compra
                                                        BigDecimal subtotal = cantidad.multiply(precioUnitario); // Subtotal sin IVA
                                                        int porcIva = detalle.getPorcIva(); // % IVA
                                                        BigDecimal iva = subtotal.multiply(BigDecimal.valueOf(porcIva)).divide(BigDecimal.valueOf(100)); // Cálculo del IVA
                                                        BigDecimal precioNeto = subtotal.add(iva); // Subtotal + IVA (Precio Neto)

                                                        // Acumuladores de los totales
                                                        totalCosto = totalCosto.add(subtotal);
                                                        totalIva = totalIva.add(iva);
                                                        totalVenta = totalVenta.add(precioNeto); // Actualizar totalVenta correctamente

                                        %>
                                        <tr>
                                            <td class="parte1"><%= detalle.getProductosId()%></td>
                                            <td><%= DaoProductos.obtenerNombreProductos(detalle.getProductosId())%></td>
                                            <td><%= cantidad%></td>
                                            <td class="parte1"><%= currencyFormat.format(precioUnitario)%></td>
                                            <td class="parte1"><%= currencyFormat.format(subtotal)%></td>
                                            <td class="parte1"><%= currencyFormat.format(iva)%></td> <!-- Mostrar el IVA -->
                                            <td><%= currencyFormat.format(precioNeto)%></td> <!-- Mostrar el precio neto con iva -->
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
                                            <td colspan="2"><strong>Subtotal:</strong></td>
                                            <td colspan="1"><%= currencyFormat.format(totalCosto)%></td>
                                        </tr>
                                        <tr class="text-right " style="background-color: #fff">
                                            <td colspan="2"><strong>Impuesto:</strong></td>
                                            <td colspan="1"><%= currencyFormat.format(totalIva)%></td>
                                        </tr>

                                        <tr class="text-right " style="background-color: #ffe8a1">
                                            <td colspan="2"><strong>Total Venta:</strong></td>
                                            <td colspan="1"><%= currencyFormat.format(totalVenta)%></td>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>

                            <div class="d-flex justify-content-center mt-3">
                                <!-- Botón para generar compra -->
                                <form action="ControladorFacturaventa" onclick="print()" method="POST" class="mr-2">
                                    <input type="hidden" name="accion" value="GenerarVenta">
                                    <button class="btn btn-success btn-custom" type="submit">Facturar</button>
                                </form>

                                <!-- Botón para cancelar la venta -->
                                <form action="ControladorFacturaventa" method="POST">
                                    <input type="hidden" name="accion" value="CancelarVenta">
                                    <button class="btn btn-secondary btn-custom" type="submit">Cancelar Venta</button>
                                </form>
                            </div>


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
            </section>

        </main>

        <footer class="text-center mt-4 parte1">
            <p>&copy; 2024 Empresa XYZ. Todos los derechos reservados.</p>
        </footer>

        <!-- ***** Modal de Registro Producto****-->

        <div class="modal fade" id="registroCliente" tabindex="-1" role="dialog" aria-labelledby="registroModalLabel" aria-hidden="true">

            <div class="modal-dialog modal-dialog-centered modal-dialog-centered" role="document"> <!-- Cambia el tamaño del modal aquí -->

                <div class="modal-content" style="background-color: #F2F2F2;"> <!-- Cambia el color de fondo aquí -->
                    <div class="modal-header">
                        <h5 class="modal-title text-primary  mx-auto"  style="font-size: 22px; margin-top:  25px; " id="registroModalLabel">REGRISTRAR CLIENTE</h5>
                        <!-- Botón de cerrar estilizado -->
                        <button type="button" class="close elegant-close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <!-- Formulario de Registro Productos -->
                        <form class="formulario" id="formulario"   action="ControladorFacturaventa"  autocomplete="off" method="POST" onsubmit="return validarFormulario()" >    

                            <div class="formulario__grupo" >
                                <label for="nombres"  class="formulario__label" >Nombres</label>
                                <input 
                                    type="text" 
                                    class="formulario__input"                                
                                    id="nombres" 
                                    name="nombres" 
                                    placeholder="Ingrese Nombres">
                            </div>                         

                            <div class="formulario__grupo">
                                <label for="telefono"class="formulario__label" >Telefono</label>
                                <input
                                    type="text" 
                                    class="formulario__input"                          
                                    id="telefono"
                                    name="telefono" 
                                    placeholder="Ingrese Numero">
                            </div>
                            <!-- Grupo: Botones -->
                            <fieldset class="formulario__grupo-btn-enviar my-2 d-flex" >
                                <button class="formulario__btn"  type="submit" name="accion" value="registrarCliente" >
                                    <i class="fas fa-save "></i> AGREGAR  
                                </button>                      

                                <button type="submit" class="formulario__btn_cancelar"  name="accion" value="CancelarVenta">
                                    <i class="bi bi-x-lg"></i> CANCELAR
                                </button>

                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>  


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
                                url: "ControladorFacturaventa?accion=BuscarProductos&productosId=" + codigo,
                                method: "GET",
                                dataType: 'json',
                                success: function (data) {
                                    if (data.nombre) {
                                        $('#producto').val(data.nombre); // Asigna el nombre del producto
                                        $('#precioCompra').val(data.precioCompra); // Asigna el precio de compra
                                        $('#precioVenta').val(data.precioVenta); // Asigna el precio de venta
                                        $('#cantidadDisponible').val(data.cantidadDisponible); // Asigna el stock
                                        $('#porcIva').val(data.porcIva); // Asigna el porcentaje de IVA

                                        // Mover el foco al campo de cantidad
                                        $('#cantidad').focus();
                                    } else {
                                        // Si no se encuentra el producto, limpiar los campos
                                        $('#producto').val('');
                                        $('#precioCompra').val('');
                                        $('#precioVenta').val('');
                                        $('#cantidadDisponible').val('');
                                        $('#porcIva').val('');
                                    }
                                },
                                error: function (xhr, status, error) {
                                    console.error('Error al obtener el producto:', error);
                                }
                            });
                        } else {
                            // Si el código está vacío, limpiar los campos
                            $('#producto').val('');
                            $('#precioCompra').val('');
                            $('#precioVenta').val('');
                            $('#cantidadDisponible').val('');
                            $('#porcIva').val('');
                        }
                    }, doneTypingInterval); // Espera antes de hacer la búsqueda
                });
            });



        </script>

        <!-- Funsion Fecha y Validar Fomr -->
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

            // funsion para validar los campos del formulario
            document.getElementById("formAgregarVenta").addEventListener("submit", function (event) {
                // Obtener el valor del botón submit
                var accion = event.submitter.value;

                // Solo validar los campos si la acción es "AgregarAlCarrito"
                if (accion === "AgregarAlCarrito2") {

                    // Obtener los campos del formulario
                    var fechaFactura = document.getElementById("fechaFactura");
                    var clienteId = document.getElementById("clienteId");
                    var cliente = document.querySelector('input[name="nombres"]');
                    var productosId = document.querySelector('input[name="productosId"]');
                    var producto = document.querySelector('input[name="producto"]');
                    var precio = document.querySelector('input[name="precioCompra"]');
                    var precioventa = document.querySelector('input[name="precioVenta"]');
                    var cantidad2 = document.querySelector('input[name="cantidad"]');
                    var stock = document.querySelector('input[name="stock"]');
                    var porcIva = document.querySelector('input[name="porcIva"]');

                    // Lista de todos los campos a validar
                    var campos = [fechaFactura, clienteId, cliente, productosId, producto, precio, precioventa, cantidad2, stock, porcIva];
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



        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <script type="text/javascript" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>

        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


    </body>
</html>
