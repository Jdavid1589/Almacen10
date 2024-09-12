<%@page import="Persistencia.DaoProductos"%>
<%@page import="Modelo.Productos"%>
<%@page import="Persistencia.DaoRecibo"%>
<%@page import="Modelo.Recibo"%>
<%@page import="Persistencia.DaoBodega"%>
<%@page import="Modelo.Bodega"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Bodega </title>      
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




        <div class="col-sm-8 mb-4 mt-5 sticky-top">

            <h5 class="text-primary elegant-font"><b>Bodega </b></h5> 

            <div  style="margin-top:50px; margin-bottom:auto ">

                <span  class="btn btn-primary text-warning ml-5 float-start " data-toggle="modal" data-target="#registroBodega">
                    <i class="fas fa-plus"></i> 
                </span>

            </div>

            <div class="table-container ml-3 md-3 table-responsive pagination-container" id="paginationContainer">           
                <table id="myTable" class="table table-striped table-hover sticky-top"> 

                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Productos</th>
                            <th>Cantidad Inventario</th>                      
                            <th>UM</th>
                            <th>Costo</th>                    
                                <%-- <th>Movimientos</th>--%>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Bodega> Lista = (List<Bodega>) request.getAttribute("listaBodega");
                            for (Bodega bodega : Lista) {%>
                        <tr>
                            <td><%= bodega.getIdBodega()%></td>
                            <td><%= DaoProductos.obtenerNombreProductos(bodega.getProductos_idProductos())%></td>
                            <td><%= bodega.getCantidadInventario()%></td>                    
                            <td><%= bodega.getUnidadMedida()%></td>
                            <td><%= bodega.getCosto()%></td>
                            
                            <%--     <td><%= DaoBodega.obtenerNotaMovimiento(bodega.getIdBodega())%></td>--%>


                            <td>
                                <div class="btn-group" role="group" aria-label="Acciones">

                                    <a href="ControladorBodega?accion=eliminar&id=<%= bodega.getIdBodega()%>"
                                       class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro de que deseas eliminar este producto?')">
                                        <i class="fas fa-trash"></i> <!-- Ícono de papelera -->
                                    </a>

                                    <a href="ControladorBodega?accion=editar&id=<%= bodega.getIdBodega()%>" class="btn btn-primary btn-sm">
                                        <i class="fas fa-pencil-alt"></i> <!-- Ícono de lápiz -->
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


<!-- ***** Modal de Registro Bodega****-->

<div class="modal fade" id="registroBodega" tabindex="-1" role="dialog" aria-labelledby="registroModalLabel" aria-hidden="true">

    <div class="modal-dialog modal-dialog-centered modal-lg" role="document"> <!-- Cambia el tamaño del modal aquí -->

        <div class="modal-content" style="background-color: #F2F2F2;"> <!-- Cambia el color de fondo aquí -->
            <div class="modal-header">
                <h5 class="modal-title text-primary  mx-auto"  style="font-size: 22px;" id="registroModalLabel">REGRISTRAR CONSECUTIVO</h5>
                <!-- Botón de cerrar estático -->
                <button type="button" class="close"  data-dismiss="modal" aria-label="Close" style="position: static;">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <!-- Formulario de Registro Productos -->
                <form id="miform2"  action="ControladorBodega"  autocomplete="off" method="POST" onsubmit="return validarFormulario()"  class="custom-form">    

                    <div class="row">
                        <div class="col-md-6 ">

                            <div class="formulario__grupo" id="grupo__tanque">         
                                <label  class="formulario__label" for="nombreProductos">Productos</label>
                                <select 
                                    class="formulario__input"                                    
                                    id="nombreProductos"                                  
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

                            <div class="formulario__grupo" id="grupo__tanque">         
                                <label class="formulario__label"  for="cantidadInventario" class="text-left">Cantidad Total</label>
                                <input 
                                    type="text" 
                                    class="formulario__input" 
                                    id="cantidadInventario" 
                                    name="cantidadInventario"
                                    placeholder="Ingrese cantidad">
                            </div>    

                            <div class="formulario__grupo" id="grupo__tanque">         
                                <label class="formulario__label" for="costo">Costo $</label>
                                <input 
                                    type="text" 
                                    class="formulario__input"  
                                    id="costo"                              
                                    name="costo" 
                                    placeholder="Ingrese Costo Producto">
                            </div>


                        </div>
                        <div class="col-md-6 ">

                            <div class="formulario__grupo" id="grupo__tanque">         
                                <label  class="formulario__label" for="unidadMedida">Unid Medida</label>
                                <input 
                                    type="unidadMedida"                                        
                                    class="formulario__input"  
                                    id="unidadMedida" 
                                    name="unidadMedida" 
                                    placeholder="Ingrese UM">
                            </div>

                            <div class="formulario__grupo" id="grupo__tanque">         
                                <label class="formulario__label" for="movimientos_Id">Movimientos</label>
                                <input 
                                    type="text" 
                                    class="formulario__input"  
                                    id="movimientos_Id"                              
                                    name="movimientos_Id" 
                                    placeholder="Entradas">
                            </div>


                        </div>
                    </div>
            </div>


            <div class="formulario__grupo formulario__grupo-btn-enviar">

                <button  class="formulario__btn" type="submit" name="accion" value="registrar" >
                    <i class="fas fa-save "></i> Agregar
                </button>              

                <button type="submit" name="accion" value="listar"  class="formulario__btn_cancelar">
                    <i class="bi bi-x-lg"></i> Cancelar
                </button>

                </form>
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



<%-- <style>

            body {

                background-image: url('img/tyu.jpg');
                background-size: cover;

            }

            label{

                color: #040505
            }

            .btn {
                border-radius: 20px; /* Redondear los botones */
            }

            h5{

                color: #ffffff
            }


        </style>--%>


</body>
</html>