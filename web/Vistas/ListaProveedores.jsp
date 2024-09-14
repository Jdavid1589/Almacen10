<%@page import="Persistencia.DaoProveedores"%>
<%@page import="Modelo.Documento"%>
<%@page import="Persistencia.DaoDocumento"%>
<%@page import="Modelo.Proveedores"%>
<%@page import="java.util.List"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Proveedor</title>      

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
        <link href="Vistas/EstilosCSS/EstilosFactura.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/EstilosProveedor.css" rel="stylesheet" type="text/css"/>
        
    </head>

    <body>


        <style>
            /* Cambiar el color de fondo y de texto del encabezado */
            thead th {

                color: #fff; /* Color del texto */
                font-size: 14px;
            }

        </style>


        <!--formulario -->

        <div class="container-fluid mt-4">
            <div class="row">
                <!-- Formulario de Registro -->
                <div class="col-sm-5">
                    <div class="card shadow-sm border-0">
                        <div class="card-body">
                            <br>
                            <br>
                            <h5 class="text-primary elegant-font text-center"><b>REGISTRO</b></h5>
                            <form id="formAgregarProducto" action="ControladorProveedores" method="POST" autocomplete="off" class="formulario">
                                <!-- Grupo Proveedor -->
                                <div class="mb-3">
                                    <label for="proveedor" class="formulario__label">Proveedor</label>
                                    <input 
                                        type="text" 
                                        value="${ProvLista.getProveedor()}" 
                                        class="formulario__input form-control-sm"
                                        id="proveedor" 
                                        name="proveedor" 
                                        placeholder="Ingrese Nombre">
                                </div>
                                <!-- Grupo Asesor Comercial -->
                                <div class="mb-3">
                                    <label for="asesor"class="formulario__label">Asesor Comercial</label>
                                    <input 
                                        type="text" 
                                        value="${ProvLista.getAsesor()}" 
                                        class="formulario__input form-control-sm"
                                        id="asesor" 
                                        name="asesor" 
                                        placeholder="Ingrese Nombre">
                                </div>
                                <!-- Grupo Telefono -->
                                <div class="mb-3">
                                    <label for="telefono" class="formulario__label">Teléfono</label>
                                    <input 
                                        type="text" 
                                        value="${ProvLista.getTelefono()}" 
                                        class="formulario__input form-control-sm"
                                        id="telefono" 
                                        name="telefono" 
                                        placeholder="Ingrese Número">
                                </div>
                                <div class="mb-3">
                                </div>
                                <!-- Botones -->
                                <div class=" d-md-block text-center" style=" grid-column: span 2;">
                                    <button class="btn btn-primary btn-sm" type="submit" name="accion" value="registrar">
                                        <i class="fas fa-save"></i> AGREGAR
                                    </button>
                                    <button class="btn btn-warning btn-sm" type="submit" name="accion" value="editarProveedores">
                                        <i class="bi bi-arrow-repeat"></i> ACTUALIZAR
                                    </button>
                                    <button class="btn btn-secondary btn-sm" type="submit" name="accion" value="listar">
                                        <i class="bi bi-x-lg"></i> CANCELAR
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <!-- Tabla de Proveedores -->
                <div class="col-sm-7">
                    <div class="card shadow-sm border-0">
                        <div class="card-body">
                            <br>
                            <br>
                            <h5 class="text-primary elegant-font text-center"><b>PROVEEDORES</b></h5>
                            <div class="table-container table-responsive pagination-container shadow-sm p-3 mb-5 bg-white rounded">
                                <table id="myTable" class="table table-striped table-hover table-bordered">
                                    <thead style="background-color: #4e73df; color: white; text-align: center">
                                        <tr>
                                            <th>ID Proveedor</th>
                                            <th>Proveedor</th>
                                            <th>Asesor</th>
                                            <th>Teléfono</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% List<Proveedores> Lista = (List<Proveedores>) request.getAttribute("listaProveedores");
                                            for (Proveedores prov : Lista) {%>
                                        <tr style="text-align: center">
                                            <td><%= prov.getIdProveedor()%></td>
                                            <td><%= prov.getProveedor()%></td>
                                            <td><%= prov.getAsesor()%></td>
                                            <td><%= prov.getTelefono()%></td>
                                            <td>
                                                <div class="btn-group text-center" role="group">
                                                    <a href="ControladorProveedores?accion=eliminar&id=<%= prov.getIdProveedor()%>" class="btn btn-outline-danger btn-sm" onclick="return confirm('¿Estás seguro de que deseas eliminar este proveedor?')">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </a>
                                                    <a href="ControladorProveedores?accion=editar&id=<%= prov.getIdProveedor()%>" class="btn btn-outline-primary btn-sm">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                        <% }%>
                                    </tbody>
                                </table>
                                <div class="dataTable_bottom align-items-center " >
                                    <div class="dataTables_info "></div>
                                    <div class="dataTables_paginate"></div>
                                </div>
                            </div>
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
                                                                // $.get('ControladorProductos?accion=editar&id=' + id, function(data) {
                                                                //     // Actualiza los campos del modal con los datos recibidos
                                                                // });
                                                                
                                                                // Muestra el modal
                                                                $('#editarProducto').modal('show');
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



        <!-- Scrip DataTable -->
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
                                                                        className: 'btn btn-success btn-elegant'
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
                                                                pageLength: 4,
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

        <!-- Estilos -->
        <style>



        </style>


    </body>
</html>