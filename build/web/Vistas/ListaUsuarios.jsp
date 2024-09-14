<%@page import="Persistencia.DaoPerfil"%>
<%@page import="Modelo.Perfil"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Usuarios"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Usuarios</title>      

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

        


    

        <link href="Vistas/EstilosCSS/EstilosUnidadaM.css" rel="stylesheet" type="text/css"/>

    </head>

    <body>





        <!--Barra de Navegacion -->



        <!--formulario -->

        <div class="container-fluid mt-4">
            <div class="row">
                <div class="col-sm-12 text-center mb-1" style="padding-top: 15px">
                    <br>
                    <h3>USUARIOS</h3>
                    <hr id="hr_1">
                </div>
            </div>
            <div class="row">
                <!-- Formulario de Registro -->
                <div class="col-sm-4">
                    <div class="card shadow-sm border-0">
                        <div class="card-body">


                            <h5>REGISTRO USUARIOS</h5>
                            <!-- Formulario de Registro Productos -->
                            <form action="ControladorUsuarios" method="POST" autocomplete="off"  class="formulario" >

                                <div class="row">
                                    <div class="col-md-6 ">
                                         <div class="mb-3">
                                            <label for="nombres" class="text-left">Nombres</label>
                                            <input 
                                                type="text" 
                                              class="formulario__input form-control-sm"     
                                                value="${User.getNombres()}"  
                                                id="nombres" 
                                                name="nombres"
                                                placeholder="Ingrese Nombres">
                                        </div>  

                                         <div class="mb-3">
                                            <label for="usuario">Usuario</label>
                                            <input 
                                                type="text" 
                                           class="formulario__input form-control-sm"     
                                                id="usuario" 
                                                value="${User.getUsuario()}" 
                                                name="usuario" 
                                                placeholder="Ingrese Usuario">
                                        </div>

                                    </div>
                                    <div class="col-md-6">      

                                           <div class="mb-3">
                                            <label for="clave">Clave</label>
                                            <input
                                                type="password" 
                                                 class="formulario__input form-control-sm"     
                                                id="clave" 
                                                value="${User.getClave()}" 
                                                name="clave" 
                                                placeholder="Ingrese Clave">
                                        </div>

                                          <div class="mb-3">
                                            <label for="tipoPerfil">Perfil</label>
                                            <select
                                                   class="formulario__input form-control-sm"     
                                                id="tipoPerfil"
                                                value="${User.getPerfilId()}"
                                                name="perfilId">
                                                <option value="0">Seleccione Perfil</option>
                                                <% List<Perfil> perfiles = DaoPerfil.listar();
                                                    if (perfiles != null) {
                                                        for (Perfil perfil : perfiles) {
                                                            if (perfil != null) {%>
                                                <option value="<%=perfil.getIdPerfil()%>"><%=perfil.getTipoperfil()%></option>
                                                <% }
                                                        }
                                                    }%>
                                            </select>

                                        </div>
                                    </div>
                                </div>
                        </div>

                        <div class="form-group mt-3 text-center" style="border: none;">

                            <button type="submit" name="accion" value="registrar" class="btn btn-warning">
                                <i class="fas fa-save "></i> Grabar
                            </button>
                            <button type="submit" name="accion" value="editarUsuarios" class="btn btn-success">
                                <i class="bi bi-arrow-repeat"></i> Actualizar
                            </button>
                            <button type="submit" name="accion" value="listar" class="btn btn-secondary">
                                <i class="bi bi-x-lg"></i> Borrar
                            </button>

                        </div>    

                    </div>
                </div>



                <div class="col-sm-8">
                    <div class="card shadow-sm border-0">
                        <div class="card-body">

                            <h5>LISTA</h5>
                            <div class="table-container table-responsive pagination-container shadow-sm p-3 mb-5 bg-white rounded">
                                <table id="myTable" class="table table-striped table-hover table-bordered">
                                    <thead style="background-color: #4e73df; color: white; text-align: center">
                                        <tr>
                                            <th>ID</th>
                                            <th>Nombres</th>                      
                                            <th>Usuario</th>
                                            <th>Clave</th>                              
                                            <th>Perfil</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                            List<Usuarios> Lista = (List<Usuarios>) request.getAttribute("listaUsuarios");
                                            for (Usuarios usuarios : Lista) {%>
                                        <tr>
                                            <td><%= usuarios.getIdUsuarios()%></td>
                                            <td><%= usuarios.getNombres()%></td>                    
                                            <td><%= usuarios.getUsuario()%></td>
                                            <td><%= usuarios.getClave()%></td>                            
                                            <td><%=DaoPerfil.obtenerNombrePerfil(usuarios.getPerfilId())%></td>


                                            <td>
                                                <div class="btn-group" role="group" aria-label="Acciones">

                                                    <a href="ControladorUsuarios?accion=eliminar&id=<%= usuarios.getIdUsuarios()%>"
                                                    class="btn btn-outline-danger btn-sm" onclick="return confirm('¿Estás seguro de que deseas eliminar este usuario?')">
                                                      <i class="fas fa-trash-alt"></i> <!-- Ícono de papelera -->
                                                    </a>

                                                    <a href="ControladorUsuarios?accion=editar&id=<%= usuarios.getIdUsuarios()%>"class="btn btn-outline-primary btn-sm">
                                                        <i class="fas fa-edit"></i> <!-- Ícono de lápiz -->
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
                </div>   
            </div>   
        </div>   



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
                                                               /*  {
                                                                extend: 'excelHtml5',
                                                                text: '<i class="fas fa-file-excel"></i> ',
                                                                titleAttr: 'Exportar a Excel',
                                                                className: 'btn btn-success'
                                                                }*/
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




    </body>
</html>