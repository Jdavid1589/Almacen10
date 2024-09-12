

<%@page import="java.util.List"%>
<%@page import="Modelo.UnidadMedida"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Unidad Medida</title>      

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

          <style>
    /* Cambiar el color de fondo y de texto del encabezado */
    thead th {
       
        color: #fff; /* Color del texto */
       font-size: 14px;
    }
    
  
</style>


        <div class="row ">  


            <div class="card-body col-4 mt-2 sticky-top mr-5">
                <%--Formulario para registrar y sirve para editar --%>
                <br>
                <h5 class=" text-center elegant-font mx-2 mr-5 " style="margin-top: 30px"><b>FORMULARIO DE REGISTRO</b></h5> 
                <br>
                <!-- Formulario de Registro Productos -->
                <form class="formulario" id="formulario"  action="ControladorUnidMedida"  autocomplete="off" method="POST" >    

                    <div class="formulario__grupo"  style="margin-left:  20px; text-align: center">
                        <label for="unidadMedida"  class="formulario__label" >Unidad Medida</label>
                        <input 
                            type="text" 
                            value="${Unid.getUnidadMedida()}"
                            class="formulario__input"                                
                            id="unidadMedida" 
                            name="unidadMedida" 
                            placeholder="Ingrese Nombre">
                    </div>                         



                    <!-- Grupo: Botones -->
                    <fieldset class="formulario__grupo-btn-enviar mt-5">
                        <button class="formulario__btn"  type="submit" name="accion" value="registrar" >
                            <i class="fas fa-save "></i> AGREGAR  
                        </button>   
                        <button type="submit" name="accion" value="actualizar" class="formulario__btn2">
                            <i class="bi bi-arrow-repeat"></i> Actualizar
                        </button>

                        <button type="submit" class="formulario__btn_cancelar"  name="accion" value="listar">
                            <i class="bi bi-x-lg"></i> CANCELAR
                        </button>

                    </fieldset>



                </form>
            </div>  

            <!-- Tabla Unidad Medida -->

            <div class="col-sm-8 mb-4 mt-5 sticky-top">
                <h5 class="text-primary elegant-font text-center"><b>UNIDAD MEDIDAS</b></h5> 

                <div class="table-container ml-3 md-3 table-responsive pagination-container" id="paginationContainer">             
                    <table id="myTable" class="table table-striped table-hover sticky-top">          
                             <thead style="background-color: #286090;" >
                            <tr>
                                <th>ID</th>
                                <th>Unidad Medida</th>                           
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Aquí se mostrarán los datos de la tabla -->
                            <%
                                List<UnidadMedida> Lista = (List<UnidadMedida>) request.getAttribute("listaUnidad");
                                for (UnidadMedida undMedida : Lista) {%>
                            <tr>
                                <td><%= undMedida.getIdUnidadMedida()%></td>
                                <td><%= undMedida.getUnidadMedida()%></td>                    


                                <td>
                                    <div class="btn-group" role="group" aria-label="Acciones">

                                        <a href="ControladorUnidMedida?accion=eliminar&id=<%= undMedida.getIdUnidadMedida()%>"
                                           class="btn  btn-sm  ms-1"  onclick="return confirm('¿Estás seguro de que deseas eliminar este producto?')">
                                            <i  id="mipapelera" class="fas fa-trash"></i> <!-- Ícono de papelera -->
                                        </a>

                                        <a  href="ControladorUnidMedida?accion=editar&id=<%=undMedida.getIdUnidadMedida()%>"class="btn btn-sm ms-1">
                                            <i class="fas fa-pencil-alt" style="font-size: 20px; color: #09f"></i> <!-- Ícono de lápiz -->
                                        </a> 


                                    </div>
                                </td>
                            </tr>
                            <% }%>
                        </tbody>
                    </table>

                    <div  class="dataTable_bottom">  
                        <div class="dataTables_info"></div>
                        <div class="dataTables_paginate"></div> 
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

                                                   // Mueve los botones a la izquierda de la tabla
                                                   $('.dt-buttons', table.table().container()).appendTo($('#myTable_wrapper .dataTables_filter'));

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

       





            /* CSS personalizado para DataTables */

            /*  .dataTables_wrapper //  Cambia el estilo  de letra mas negrita*/
            .dataTables_wrapper {
                font-family: 'Roboto', sans-serif;
            }
            .dataTables_info {
                /* margin-top:  25px;*/
                font-weight: 700;
            }
            /* Con la propiedad bottom y letf controlo la aubicacion de la info */
            .dataTables_paginate {
                font-family: 'Roboto', sans-serif;
                font-weight: 700;
                /*   bottom: -15%;*/

                /*  margin-bottom: 5px; /* Ajusta según sea necesario */
            }

            /* Move the buttons below the table */

            .dataTables_wrapper .dt-buttons {
                position: absolute;
                bottom: -50px;
                left: 50%;
                transform: translateX(-40%);
                margin-bottom: 20px; /* Ajusta según sea necesario */
            }

            /* Estilos redondeados para los btn */

            .btn {
                border-radius: 20px; /* Redondear los botones */
            }




            a #mipapelera{
                font-size: 25px;
                color: #66ff66;
                transition: 0.3s;

            }
            a #mipapelera:hover{
                color: #FF0000;
                box-shadow: 3px 0px 30px rgba(246, 78, 60, 1.5);
            }

            /*Estilos Tabla  */
            .th__Ref{
                font-size: 10px;
            }


            .campo-invalido {
                border: 1px solid red; /* Cambia el borde a rojo para resaltar el campo */
                background-color: #ffcccc; /* Cambia el fondo a un tono rojo claro */
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
