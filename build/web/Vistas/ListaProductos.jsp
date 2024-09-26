<%@page import="Persistencia.DaoUnidMedida"%>
<%@page import="Modelo.UnidadMedida"%>
<%@page import="Persistencia.DaoProductos"%>
<%@page import="Modelo.Productos"%>
<%@page import="Persistencia.DaoCategorias"%>
<%@page import="Modelo.Categorias"%>
<%@page import="Persistencia.DaoProveedores"%>
<%@page import="Modelo.Proveedores"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title> Productos</title>      

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

        <link href="Vistas/EstilosCSS/EstilosGen3.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/Estilos_generales.css" rel="stylesheet" type="text/css"/>



    </head>

    <body>

        <!-- Estilos -->
        <style>
            /* Cambiar el color de fondo y de texto del encabezado */
            thead th {

                color: #fff; /* Color del texto */
                font-size: 14px;
            }


            #myTable_buttons_bottom {
                text-align: center; /* Centra el contenido del contenedor */
                margin-top: -40px;   /* Añadir un poco de espacio superior si es necesario */

            }
        </style>

        <div class="card-body col-sm-12 ">
            <div  style="margin-top:60px; margin-left: 20px ">
                <span  class="btn btn-primary text-warning my-2 " title="Agregar Productos Nuevos" data-toggle="modal" data-target="#registroCosecutivo">
                    <i class="fas fa-plus"></i> 
                </span>
            </div>
            <h5 class=" elegant-font text-center text-primary"><b>INVENTARIO "BODEGA"</b></h5> 

            <div class="table-container ml-3 md-3 table-responsive pagination-container" id="paginationContainer">             
                <table id="myTable" class="table table-striped table-hover table-bordered sticky-top shadow-sm">          
                    <thead style="background-color: #4e73df; color: white;">
                        <tr>
                            <th>ID</th>
                            <th>FECHA</th>
                            <th>Producto</th>
                            <th>PLU</th>                               
                            <th>Proveedor</th>
                            <th>Categoría</th>
                            <th>Precio Venta</th>
                            <th>Precio Compra</th>
                            <th>% Iva</th>
                            <th>Unid. Medida</th>
                            <th>Cant Disponible</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Productos> Lista = (List<Productos>) request.getAttribute("listaProductos");
                            for (Productos producto : Lista) {%>
                        <tr>
                            <td><%= producto.getIdProductos()%></td>
                            <td><%= producto.getFechaActualizacion()%></td>
                            <td><%= producto.getProductos()%></td>                    
                            <td><%= producto.getPlu()%></td>                                   
                            <td><%=DaoProveedores.obtenerNombreProveedores(producto.getProveedoresId())%></td>                          
                            <td><%=DaoCategorias.obtenerNombreCategorias(producto.getCategoriasId())%></td>
                            <td>$<%= producto.getPrecioVenta()%></td>   
                            <td>$<%= producto.getPrecioCompra()%></td>   
                            <td><%= producto.getPorcIva()%></td>     
                            <td><%= DaoUnidMedida.obtenerNombreUnidad(producto.getUnidadMedidaId())%></td>   
                            <td><%= producto.getCantidadDisponible()%></td>   
                            <td>
                                <div class="btn-group" role="group" aria-label="Acciones">
                                    <a href="ControladorProductos?accion=eliminar&id=<%= producto.getIdProductos()%>"
                                       class="btn btn-sm btn-outline-danger ms-1"  onclick="return confirm('¿Estás seguro de que deseas eliminar este producto?')">
                                        <i class="fas fa-trash-alt"></i>
                                    </a>
                                    <a href="ControladorProductos?accion=editar&id=<%=producto.getIdProductos()%>"
                                       class="btn btn-sm btn-outline-primary ms-1">
                                        <i class="fas fa-edit"></i>
                                    </a> 
                                </div>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

                <div class="dataTable_bottom d-flex justify-content-between align-items-center mt-2">  
                    <div class="dataTables_info"></div>
                    <div class="dataTables_paginate"></div> 
                </div>
            </div>


            <!-- ***** Modal de Registro Producto****-->

            <div class="modal fade" id="registroCosecutivo" tabindex="-1" role="dialog" aria-labelledby="registroModalLabel" aria-hidden="true">

                <div class="modal-dialog modal-dialog-centered modal-xl" role="document"> <!-- Cambia el tamaño del modal aquí -->

                    <div class="modal-content" style="background-color: #F2F2F2;"> <!-- Cambia el color de fondo aquí -->
                        <div class="modal-header">

                            <h5 class="modal-title text-primary  mx-auto"  style="font-size: 22px; margin-top:  25px; " id="registroModalLabel">REGRISTRAR PRODUCTO</h5>

                            <!-- Botón de cerrar estilizado -->
                            <button type="button" class="close elegant-close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">

                            <!-- Formulario de Registro Productos -->

                            <form class="formulario" id="formulario"   action="ControladorProductos"  autocomplete="off" method="POST" onsubmit="return validarFormulario()" >    

                                <div class="formulario__grupo" >
                                    <label for="fechaActualizacion"  class="formulario__label" >Fecha</label>
                                    <input 
                                        type="date" 
                                        class="formulario__input"                                
                                        id="fechaActualizacion" 
                                        name="fechaActualizacion" 
                                        placeholder="Ingrese Nombre">
                                </div>    

                                <div class="formulario__grupo" >
                                    <label for="productos"  class="formulario__label" >Producto</label>
                                    <input 
                                        type="text" 
                                        class="formulario__input"                                
                                        id="productos" 
                                        name="productos" 
                                        placeholder="Ingrese Nombre">
                                </div>                           


                                <div class="formulario__grupo">
                                    <label for="plu"class="formulario__label" >Plu</label>
                                    <input
                                        type="text" 
                                        class="formulario__input"                          
                                        id="plu"
                                        name="plu" 
                                        placeholder="Ingrese Plu">
                                </div>

                                <div class="formulario__grupo">
                                    <label for="cantidadDisponible"class="formulario__label" >Cantidad Disponible</label>
                                    <input
                                        type="text" 
                                        class="formulario__input"                          
                                        id="cantidadDisponible"
                                        name="cantidadDisponible" 
                                        placeholder="Ingrese Cantidad">
                                </div>

                                <div class="formulario__grupo" >
                                    <label class="formulario__label"  for="unidadMedidaId">Unidad Medida</label>
                                    <select
                                        class="formulario__input"
                                        id="unidadMedidaId" 
                                        name="unidadMedidaId">
                                        <option value="0">Seleccione Unidad</option>
                                        <% List<UnidadMedida> unidadMedidas = DaoUnidMedida.listar();
                                            if (unidadMedidas != null) {
                                                for (UnidadMedida Un : unidadMedidas) {
                                                    if (Un != null) {%>
                                        <option value="<%=Un.getIdUnidadMedida()%>"><%=Un.getUnidadMedida()%></option>
                                        <% }
                                                }
                                            }%>
                                    </select>
                                </div>

                                <div class="formulario__grupo" >
                                    <label class="formulario__label"  for="proveedoresId">Proveedor</label>
                                    <select
                                        class="formulario__input"
                                        id="proveedoresId" 
                                        name="proveedoresId">
                                        <option value="0">Seleccione Proveedor</option>
                                        <% List<Proveedores> provee = DaoProveedores.listar();
                                            if (provee != null) {
                                                for (Proveedores pr : provee) {
                                                    if (pr != null) {%>
                                        <option value="<%=pr.getIdProveedor()%>"><%=pr.getProveedor()%></option>
                                        <% }
                                                }
                                            }%>
                                    </select>
                                </div>

                                <div class="formulario__grupo" >
                                    <label class="formulario__label"  for="categoriasId">Categorias</label>
                                    <select 
                                        class="formulario__input"
                                        id="categoriasId"
                                        name="categoriasId">
                                        <option value="0">Seleccione categorias</option>
                                        <% List<Categorias> categorias = DaoCategorias.listar();
                                            if (categorias != null) {
                                                for (Categorias pr : categorias) {
                                                    if (pr != null) {%>
                                        <option value="<%=pr.getIdCategorias()%>"><%=pr.getTipos()%></option>
                                        <% }
                                                }
                                            }%>
                                    </select>
                                </div>

                                <div class="formulario__grupo">
                                    <label for="precioCompra"class="formulario__label" >Precio Compra</label>
                                    <input
                                        type="text" 
                                        class="formulario__input"                          
                                        id="precioCompra"
                                        name="precioCompra" 
                                        placeholder="Ingrese precio Compra">
                                </div>

                                <div class="formulario__grupo">
                                    <label for="precioVenta"class="formulario__label" >Precio Venta</label>
                                    <input
                                        type="text" 
                                        class="formulario__input"                          
                                        id="precioVenta"
                                        name="precioVenta" 
                                        placeholder="Ingrese precio Compra">
                                </div>

                                <div class="formulario__grupo">
                                    <label for="porcIva"class="formulario__label" >% Iva</label>
                                    <input
                                        type="text" 
                                        class="formulario__input"                          
                                        id="porcIva"
                                        name="porcIva" 
                                        placeholder="Ingrese %">
                                </div>



                                <!-- Grupo: Botones -->
                                <fieldset class="formulario__grupo-btn-enviar my-2 d-flex" >
                                    <button class="formulario__btn"  type="submit" name="accion" value="registrar" >
                                        <i class="fas fa-save "></i> AGREGAR  
                                    </button>                      

                                    <button type="submit" class="formulario__btn_cancelar"  name="accion" value="listar">
                                        <i class="bi bi-x-lg"></i> CANCELAR
                                    </button>

                                </fieldset>


                            </form>
                        </div>
                    </div>
                </div>
            </div>   
        </div>



        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

        <!-- Funsion para Modal editar -->
        <script>
                                $(document).ready(function () {
                                $('.edit-btn').on('click', function (e) {
                                e.preventDefault(); // Evita la redirección predeterminada

                                        var id = $(this).data('id'); // Obtén el ID del producto
                                        // Aquí puedes hacer una llamada AJAX para cargar los datos del producto si es necesario
                                        $.get('ControladorProductos?accion=editar&id=' + id, function(data) {
                                        //     // Actualiza los campos del modal con los datos recibidos
                                        // });

                                        // Muestra el modal
                                        $('#registroEditar').modal('show');
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




        <script>

                                        $(document).ready(function () {
                                // Inicializa la tabla DataTables
                                var table = $('#myTable').DataTable({
                                dom: 'Blftrip', // Mueve los elementos de paginación al final de la tabla
                                        buttons: [
                                        {
                                        extend: 'excelHtml5',
                                                text: '<i class="fas fa-file-excel"></i> ',
                                                titleAttr: 'Exportar a Excel',
                                                className: 'btn btn-success'
                                        }
                                        /*,
                                         {
                                         extend: 'pdfHtml5',
                                         text: '<i class="fas fa-file-pdf"></i> ',
                                         titleAttr: 'Exportar a PDF',
                                         className: 'btn btn-danger',
                                         orientation: 'landscape', // Establece la orientación horizontal
                                         customize: function (doc) {
                                         // Ajusta las márgenes
                                         doc.pageMargins = [5, 5, 5, 5]; // [left, top, right, bottom]
                                         doc.defaultStyle.fontSize = 10;
                                         
                                         // Filtra las columnas que quieres imprimir
                                         var filteredColumns = [0, 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 23]; // Índices de las columnas que quieres imprimir
                                         
                                         // Remueve las columnas no deseadas
                                         doc.content[1].table.body.forEach(function (row) {
                                         row.splice(0, row.length, ...row.filter((cell, index) => filteredColumns.includes(index)));
                                         });
                                         }
                                         }*/
                                        ],
                                        lengthMenu: [10, 15, 20, 50],
                                        columnDefs: [
                                        {className: 'centered', targets: '_all'}, // Aplica la clase 'centered' a todas las columnas
                                        {orderable: false, targets: [0, 2, ]},
                                        {searchable: false, targets: [0, ]}
                                        ],
                                        pageLength: 5,
                                        destroy: true,
                                        // order: [[1, 'desc']], // Ordena la tabla por la Segunda columna en orden descendente
                                        pagingType: 'simple_numbers', // Tipo de paginación simple con números
                                        language: {
                                        processing: 'Procesando...',
                                                "lengthMenu": "<span style='color: #09f; font-size: 18px;   '>Mostrar _MENU_ Registros </span>",
                                                "zeroRecords": "No se Encontraron Resultados",
                                                "emptyTable": "Ningún dato disponible en esta tabla",
                                                "info": "<span style='color: #09f; font-size: 18px;'>Mostrando _START_ a _END_ de _TOTAL_ Entradas</span>",
                                                "infoEmpty": "<span style='color: #09f; font-size: 18px;'>Mostrando 0 a 0 de 0 Entradas</span>",
                                                "infoFiltered": "<span style='color: purple; font-size: 14px;'>(filtrado de un total de _MAX_ entradas)</span>",
                                                "search": "<span style='color: #09f; font-size: 18px; border-bottom: 2px  solid #ccc;'>Buscar:</span>",
                                                paginate: {
                                                first: 'Primero',
                                                        last: 'Último',
                                                        next: 'Siguiente',
                                                        previous: 'Anterior'
                                                },
                                                aria: {
                                                sortAscending: ': Activar para ordenar la columna ascendente',
                                                        sortDescending: ': Activar para ordenar la columna descendente'
                                                }
                                        }
                                });
                                        // Mueve los botones a la izquierda de la tabla
                                        // $('.dt-buttons', table.table().container()).appendTo($('#myTable_wrapper .dataTables_filter'));
                                        // 
                                        // Crear un nuevo contenedor para los botones debajo de la tabla
                                        $('#myTable_wrapper').append('<div id="myTable_buttons_bottom"></div>');
                                        // Mover los botones a este nuevo contenedor
                                        $('.dt-buttons', table.table().container()).appendTo($('#myTable_buttons_bottom'));
                                        // Función para limitar los botones de paginación
                                                function limitPagination(table) {
                                                var pagination = $(table.table().container()).find('.dataTables_paginate .paginate_button');
                                                        var numPages = 4; // Limita a 4 botones de paginación

                                                        pagination.each(function (index, element) {
                                                        if (index > numPages && !$(element).hasClass('next') && !$(element).hasClass('previous')) {
                                                        $(element).css('display', 'none');
                                                        }
                                                        });
                                                }

                                        // Llama a la función limitPagination cuando se redibuja la tabla
                                        table.on('draw', function () {
                                        limitPagination(table);
                                        });
                                                // Llama a la función limitPagination inicialmente
                                                limitPagination(table);
                                        });
        </script>

        <style>

            .btn {
                border-radius: 20px; /* Redondear los botones */
            }


            /* Aumentando la especificidad del selector */
            button.close.elegant-close {
                position: absolute !important; /* Posiciona el botón en la esquina del modal */
                top: 15px !important;
                right: 15px !important;
                width: 35px !important;
                height: 35px !important;
                border: none !important;
                border-radius: 50% !important;
                background-color: #f8f9fa !important; /* Color de fondo suave */
                color: #333 !important; /* Color del icono */
                display: flex !important;
                align-items: center !important;
                justify-content: center !important;
                transition: background-color 0.3s, color 0.3s !important;
            }

            button.close.elegant-close:hover {
                background-color: #333 !important; /* Cambia el color de fondo al pasar el mouse */
                color: #fff !important; /* Cambia el color del icono al pasar el mouse */
            }

            button.close.elegant-close span {
                font-size: 20px !important;
            }


        </style>


    </body>
</html>