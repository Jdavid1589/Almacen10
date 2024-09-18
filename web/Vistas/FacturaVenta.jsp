

<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Modelo.Compras"%>
<%@page import="Modelo.ComprasProductos"%>
<%@page import="Persistencia.DaoMovimientos"%>
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
        <!--link href="https://cdn.jsdelivr.net/npm/bootstrap@5.5.0/dist/css/bootstrap.min.css" rel="stylesheet"-->


        <!-- Sirven para actualizar la fecha automaticamnente -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
        <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>


        <link href="Vistas/EstilosCSS/EstilosGen3.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/Estilos_generales.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/EstilosFactura.css" rel="stylesheet" type="text/css"/>



        <!-- Estilos -->
        <style>

            #hr_1{

                border: #6c757d solid 2px;

                margin-bottom: -0.09rem !important;
            }
            h5 {
                margin-bottom: 0.1rem !important; /* Menos espacio debajo del título */
            }

            hr {
                border: #6c757d solid 2px;
                margin-top: 0.5rem !important; /* Reduce el espacio entre el título y el campo */
                margin-bottom: 0.8rem !important;

            }

            .formulario__input {
                margin-top: 0.5px !important; /* Evita margen extra encima de los campos */
            }
        </style>

    </head>

    <body>




        <div class="container-fluid mt-4">
            <!-- Encabezado de la factura -->
            <div class="row">
                <div class="col-sm-12 text-center mb-1" style="padding-top: 15px">
                    <br>

                    <h3 style="color: #0056b3;font-family: 'Roboto', sans-serif;font-weight: 600 ">Factura de Compra</h3>
                    <hr id="hr_1">
                </div>
            </div>

            <!-- Sección de Datos de la Factura y Proveedor -->
            <div class="row">
                <div class="col-sm-5">
                    <div class="card">
                        <div class="card-body">
                            <!-- Fecha de Factura -->
                            <form id="formAgregarProducto" action="ControladorFacturas" method="POST" autocomplete="off" class="custom-form">
                                <div class="row mb-2">
                                    <div class="col-sm-6">
                                        <label for="fechaFactura" class="formulario__label">Fecha de Factura:</label>
                                        <input type="date" class="formulario__input form-control" id="fechaFactura" name="fechaFactura" value="${param.fechaFactura != null ? param.fechaFactura : ''}" placeholder="fecha">
                                    </div>
                                </div>

                                <!-- Sección de búsqueda de proveedor -->


                                <div class="row">
                                    <div class="col-sm-12">
                                        <h5>Datos Proveedor</h5>
                                        <hr >
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-sm-6 d-flex">
                                        <input type="text" class="formulario__input form-control parte1" id="proveedorId" name="proveedorId" value="${param.proveedorId}" placeholder="Ingrese Código">
                                        <button class="btn btn-outline-info mx-2 parte1" type="submit" name="accion" value="BuscarProveedor">Buscar</button>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" class="formulario__input form-control" style="font-weight: 600" name="proveedor" value="${proveedorEncontrado.proveedor}" placeholder="Proveedor">
                                    </div>
                                </div>

                                <hr style="border: 2px solid #000">

                                <!-- Sección de búsqueda de producto -->
                                <div class="row parte1 ">
                                    <div class="col-sm-12 ">
                                        <h5>Datos del Producto</h5>
                                        <hr class="parte1">
                                    </div>
                                </div>

                                <div class="row parte1 mb-3">
                                    <div class="col-sm-6 d-flex">
                                        <input type="text" class="formulario__input form-control" name="productosId" placeholder="Ingrese Código" value="${param.productosId}">
                                        <button class="btn btn-outline-info mx-2" type="submit" name="accion" value="BuscarProductos">Buscar</button>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" class="formulario__input form-control" style="font-weight: 600" name="producto" value="${listapr.productos}" placeholder=" Producto">
                                    </div>
                                </div>

                                <!-- Información del producto -->
                                <div class="row mb-2 parte1">
                                    <div class="col-sm-3">
                                        <label class="formulario__label">Precio Unidad</label>
                                        <input type="text" class="formulario__input form-control" name="precio" value="${'$ '}${listapr.precioCompra}" placeholder="$/0.00">
                                    </div>
                                    <div class="col-sm-3 parte1">
                                        <label class="formulario__label">Cantidad</label>
                                        <input type="number" class="formulario__input form-control" name="cantidad" placeholder="">
                                    </div>
                                    <div class="col-sm-3">
                                        <label class="formulario__label">Stock</label>
                                        <input type="text" class="formulario__input form-control" name="stock" value="${listapr.cantidadDisponible}" placeholder="Stock">
                                    </div>
                                    <div class="col-sm-2">
                                        <label class="formulario__label">%Iva</label>
                                        <input type="text" class="formulario__input form-control" name="porcIva" value="${listapr.porcIva}" placeholder="%Iva">
                                    </div>
                                </div>

                                <hr  class="parte1" style="border: 2px solid #000">

                                <!-- Botón Agregar al Carrito -->
                                <div class="row mb-2 text-center parte1">
                                    <div class="col-sm-12">
                                        <button id="btnAgregarAlCarrito" class="btn btn-primary" type="submit" name="accion" value="AgregarAlCarrito">Agregar al Carrito</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Sección de Carrito -->
                <div class="col-sm-7">
                    <div class="card">
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
                                                    <i id="mipapelera" class="fas fa-trash"></i>
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
                            <form action="ControladorCompras" method="POST">
                                <input type="hidden" name="accion" value="GenerarCompra">
                                <div class="text-center">
                                    <button class="btn btn-success parte1"  onclick="print()" type="submit">Generar Compra</button>
                                </div>
                            </form>

                            <!-- Mensaje de error si aplica -->
                            <h1>${mensajeError}</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>




        <!-- Script Limpiar campos  -->
        <script>
            // Función para limpiar los campos del formulario
            function limpiarCampos() {
                document.getElementById('fecha').value = '';
                document.getElementById('Idproveedor').value = '';
                document.getElementById('proveedor').value = '';
                document.getElementById('IdProducto').value = '';
                document.getElementById('producto').value = '';
                document.getElementById('precio').value = '';
                document.getElementById('cantidad').value = '';
                document.getElementById('stock').value = '';
            }
            
            // Manejar el clic en el botón "Agregar al Carrito"
            document.getElementById('btnAgregarAlCarrito').addEventListener('click', function () {
                // Enviar el formulario
                document.getElementById('formAgregarProducto').submit();
                
                // Limpiar los campos después de enviar el formulario
                // Nota: En este caso, los campos se limpiarán después de la redirección, así que no es necesario aquí.
                // limpiarCampos();
            });
            
            //Funsion para inicializar la fecha 
            
            document.addEventListener('DOMContentLoaded', function () {
                flatpickr("#fechaFactura", {
                    dateFormat: "Y-m-d", // Formato de fecha
                    altInput: true, // Muestra un campo de texto alternativo
                    altFormat: "F j, Y", // Formato alternativo que se muestra al usuario
                    defaultDate: "today", // Fecha por defecto (hoy)
                });
            });
            
        </script>


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
