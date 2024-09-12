<<%@page import="Persistencia.DaoMovimientos"%>
<%@page import="Modelo.Movimientos"%>
<%@page import="Persistencia.DaoProductos"%>
<%@page import="Modelo.Productos"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Movimientos</title>      
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



        <link href="Vistas/EstilosCSS/EstilosTablas_Form.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/Estilos_generales.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/Estilos4/EstilosTablas.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>   

        <!-- Formulario -->
        <div class="row">

            <div class="col-sm-4 bg-darkt card-body">
                <h4 class="text-primary elegant-font"><b>Formulario Movimientos </b></h4>


                <!-- Inicio Formulario -->
                <form   id="miform2" action="ControladorMovimientos" method="POST" autocomplete="off" >

                    <div class="row">
                        <div class="col-md-6">                                               

                            <!-- Grupo: Fecha -->
                            <div class="form-group">
                                <label for="fecha" class="formulario__label" >Fecha</label>
                                <input 
                                    type="date" 
                                    class="formulario__input"      
                                    id="fecha"
                                    name="fecha"
                                    value="${Mov.getFecha()}"
                                    placeholder="Ingrese Fecha">
                            </div>

                            <!-- Grupo: Productos -->
                            <div class="form-group">
                                <label class="formulario__label" for="nombreProductos">Productos</label>
                                <select
                                    class="formulario__input"     
                                    id="nombreProductos" 
                                    value="${Mov.getProductos_idProductos()}"
                                    name="productos_idProductos">
                                    <option value="0">Seleccione Producto</option>
                                    <% List<Productos> productos = DaoProductos.listar();
                                        if (productos != null) {
                                            for (Productos pr : productos) {
                                                if (pr != null) {%>
                                    <option value="<%=pr.getIdProductos()%>"><%=pr.getNombreProductos()%></option>
                                    <% }
                                            }
                                        }%>
                                </select>
                            </div>     

                            <!-- Grupo: Costo -->
                            <div class="form-group text-left">
                                <label  class="formulario__label" for="recibo_idRecibo">Entrada Productos</label>
                                <input
                                    type="text" 
                                    class="formulario__input"     
                                    id="costo"
                                    name="recibo_idRecibo"
                                    value="${Mov.getRecibo_idRecibo()}" 
                                    placeholder="Ingrese ">
                            </div>

                        </div>

                        <div class="col-md-6">                           

                            <!-- Grupo: Cantidad -->
                            <div class="form-group">
                                <label class="formulario__label" for="cantidad">Cantidad</label>
                                <input 
                                    type="text" 
                                    class="formulario__input"      
                                    id="cantidad"
                                    name="cantidad" 
                                    value="${Mov.getCantidad()}" 
                                    placeholder="Ingrese Cantidad">
                            </div>

                            <!-- Grupo: Costo -->
                            <div class="form-group">
                                <label class="formulario__label" for="costo">Costo / Unid $</label>
                                <input
                                    type="text" 
                                    class="formulario__input"     
                                    id="costo"
                                    name="costo"
                                    value="${Mov.getCosto()}" 
                                    placeholder="Ingrese Cantidad">
                            </div>


                            <!-- Grupo: Notas -->
                            <div class="form-group text-left">
                                <label for="nota" class="formulario__label">Nota</label>
                                <input 
                                    type="text" 
                                    class="formulario__input"     
                                    id="nota" 
                                    name="nota" 
                                    value="${Mov.getNota()}" 
                                    placeholder="Ingrese Nota">
                            </div>

                        </div>
                    </div>

                    <!--Botones -->
                    <div class="form-group mt-3 text-center">
                        <button type="submit" name="accion" value="registrar" class="btn btn-warning">
                            <i class="fas fa-save"></i> Grabar </button>

                        <button type="submit" name="accion" value="editarMovimientos" class="btn btn-success">
                            <i class="bi bi-arrow-repeat"></i> Actualizar  </button> 

                        <button type="submit" name="accion" value="listar" class="btn btn-secondary">
                            <i class="bi bi-x-lg"></i> Cancelar</button>

                    </div>

                </form> 

                <!-- Aquí se cierra el formulario -->

            </div>


            <!-- Contenedor lista usuarios donde aparecen los datos -->
            <div class="col-sm-8 mb-4 mt-2 sticky-top">

                <h5 class="text-primary elegant-font"><b>Lista movimientos</b></h5> 

                <div class="col-sm-12 mb-4 sticky-top" >
                    <div class="table-container ml-3 md-3 table-responsive pagination-container" id="paginationContainer">             
                        <table id="myTable" class="table table-striped table-hover sticky-top">      
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Fecha</th>
                                    <th>Producto</th>
                                    <th>Cantidad</th>
                                    <th>Costo</th>
                                    <th>Notas</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    List<Movimientos> Lista = (List<Movimientos>) request.getAttribute("listaMovimientos");
                                    for (Movimientos movi : Lista) {%>
                                <tr>
                                    <td><%= movi.getIdMovimientos()%></td>
                                    <td><%= movi.getFecha()%></td>
                                    <td><%= DaoProductos.obtenerNombreProductos(movi.getProductos_idProductos())%></td>
                                    <td><%= movi.getCantidad()%></td>
                                    <td><%= movi.getCosto()%></td>
                                    <td><%= movi.getNota()%></td>

                                    <td>
                                        <div class="btn-group" role="group" aria-label="Acciones">

                                            <a href="ControladorMovimientos?accion=eliminar&id=<%= movi.getIdMovimientos()%>"
                                               class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro de que deseas eliminar este movimiento?')">
                                                <i class="fas fa-trash"></i> <!-- Ícono de papelera -->
                                            </a>

                                            <a href="ControladorMovimientos?accion=editar&id=<%= movi.getIdMovimientos()%>" class="btn btn-primary btn-sm">
                                                <i class="fas fa-pencil-alt"></i> <!-- Ícono de lápiz -->
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                                <% }%>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div  class="dataTable_bottom">  
            <div class="dataTables_info"></div>
            <div class="dataTables_paginate"></div> 
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
                                                       $('#myTable').DataTable({
                                                           "paging": true, // Habilita la paginación
                                                           "pageLength": 7, // Número de registros por página
                                                           "language": {
                                                               "processing": "Procesando...",
                                                               "lengthMenu": "Mostrar _MENU_ registros por página",
                                                               "zeroRecords": "No se encontraron resultados",
                                                               "emptyTable": "Ningún dato disponible en esta tabla",
                                                               "info": "Mostrando _START_ a _END_ de _TOTAL_ entradas",
                                                               "infoEmpty": "Mostrando 0 a 0 de 0 entradas",
                                                               "infoFiltered": "(filtrado de un total de _MAX_ entradas)",
                                                               "search": "Buscar:",
                                                               "paginate": {
                                                                   "first": "Primero",
                                                                   "last": "Último",
                                                                   "next": "Siguiente",
                                                                   "previous": "Anterior"
                                                               },
                                                               "aria": {
                                                                   "sortAscending": ": Activar para ordenar la columna ascendente",
                                                                   "sortDescending": ": Activar para ordenar la columna descendente"
                                                               }
                                                           },
                                                           "dom": 'Bfrtip',
                                                           "buttons": [
                                                               {
                                                                   extend: 'pdfHtml5',
                                                                   text: '<i class="fas fa-file-pdf"></i>',
                                                                   titleAttr: 'Exportar a PDF',
                                                                   className: 'btn btn-danger btn-sm' // Añadir clase "btn-sm" para hacer los botones más pequeños
                                                               },
                                                               {
                                                                   extend: 'excelHtml5',
                                                                   text: '<i class="fas fa-file-excel" ></i>', // Aplicar el radio de borde al ícono de Excel
                                                                   titleAttr: 'Exportar a Excel',
                                                                   className: 'btn btn-success btn-sm' // Añadir clase "btn-sm" para hacer los botones más pequeños
                                                               }
                                                           ]
                                                       });
                                                   });
</script>




</body>
</html>
