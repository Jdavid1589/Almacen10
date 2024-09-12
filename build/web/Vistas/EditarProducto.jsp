
<%@page import="Persistencia.DaoCategorias"%>
<%@page import="Modelo.Categorias"%>
<%@page import="Persistencia.DaoProveedores"%>
<%@page import="Modelo.Proveedores"%>
<%@page import="Persistencia.DaoProductos"%>
<%@page import="Modelo.Productos"%>
<%@page import="Modelo.Usuarios"%>
<%@page import="java.util.List"%>
<%@page import="Persistencia.DaoDocumento"%>
<%@page import="Modelo.Documento"%>
<%@page import="Persistencia.DaoPerfil"%>
<%@page import="Modelo.Perfil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>    
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- DataTable -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.1/css/dataTables.bootstrap5.min.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/2.3.3/css/buttons.bootstrap5.min.css">

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link href="Vistas/EstilosCSS/EstilosTablas_Form.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/Estilos_generales.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/Estilos4/EstilosTablas.css" rel="stylesheet" type="text/css"/>



        <link href="Vistas/Estilos_css/EstilosPL2.css" rel="stylesheet" type="text/css"/>

        <title>Editar Consecutivo</title>

    </head>
    <body>


        <!-- Stilos -->
        <style>

            #cuadro {
                /* Estilos para tu cuadro de formulario */
                max-width: 600px;
                margin: 50px auto;
                background-color: rgba(255, 255, 255, 0.8); /* el 0.6 es el grado de transparencia/* Fondo semitransparente para que la imagen de fondo sea visible */
                padding: 20px;

                border-radius: 10px;

                background-color: #ffcc0; /* Cambia #ffcc00 por el color que desees */
                padding: 20px; /* Ajusta el relleno según sea necesario */


            }
        </style>
        <main>
            <div class="container-fluid text-center" style="position: relative; top: -22px;">           
                <div class="row">
                    <div class="col-md-12 mx-auto">
                        <br>
                        <h4 class="text-primary elegant-font mt-5"><b>EDITAR PRODUCTOS</b></h4>                  
                    </div>
                </div>
            </div>

            <!--Barra de Navegacion -->
            <%
                int id = Integer.parseInt((String) request.getAttribute("EditarProd"));
                Productos prod = DaoProductos.obtenerProductosPorId(id);

            %>
            <div class="container-fluid">
                <div class="col-12">


                    <form class="formulario" id="formulario" action="ControladorProductos" method="post">

                        <div class="row">
                            <div class="col-8">
                                <div class="form-group text-left">
                                    <label for="nombreProductos" class="text-left">Nombres</label>
                                    <input
                                        type="text" 
                                        class="form-control"
                                        value="<%=prod.getProductos()%>" 
                                        id="nombreProductos" 
                                        name="nombreProductos" 
                                        placeholder="Ingrese Nombre">
                                </div>

                                <div class="form-group text-left">
                                    <label for="plu" class="text-left">Plu</label>
                                    <input
                                        type="text" 
                                        class="form-control" 
                                        value="<%=prod.getPlu()%>" 
                                        name="plu" 
                                        placeholder="Ingrese Plu">
                                </div>
                            </div>

                            <div class="col-8">
                                <!-- Grupo: Referencia -->
                                <div class="formulario__grupo" id="grupo__referencia">
                                    <label class="formulario__label" for="proveedores_idProveedores"> PROVEEDORES </label>
                                    <select
                                        class="formulario__input" 
                                        name="proveedores_idProveedores"
                                        id="proveedores_idProveedores">
                                        <option value="0">Seleccione Proveedor</option>
                                        <% for (Proveedores proveedores : DaoProveedores.listar()) {
                                                if (proveedores != null) {%>
                                        <option value="<%=proveedores.getIdProveedor()%>" <%=proveedores.getIdProveedor()== prod.getProveedoresId()? "selected" : ""%>> 
                                            <%=proveedores.getAsesor()%>
                                        </option>
                                        <% }
                                            } %>
                                    </select>
                                </div>

                                <!-- Grupo: Categorías -->
                                <div class="formulario__grupo" id="grupo__categorias">
                                    <label class="formulario__label" for="categorias_idCategorias"> CATEGORÍAS </label>
                                    <select
                                        class="formulario__input" 
                                        name="categorias_idCategorias"
                                        id="categorias_idCategorias">
                                        <option value="0">Seleccione Categoría</option>
                                        <% for (Categorias categorias5 : DaoCategorias.listar()) {
                                                if (categorias5 != null) {%>
                                        <option value="<%=categorias5.getIdCategorias()%>" <%=categorias5.getIdCategorias() == prod.getCategoriasId()? "selected" : ""%>> 
                                            <%=categorias5.getTipos()%>
                                        </option>
                                        <% }
                                            }%>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <fieldset class="formulario__grupo-btn-enviar">
                            <input type="hidden" name="txtid" value="<%=prod.getIdProductos()%>">
                            <button type="submit" name="accion" value="actualizar" class="formulario__btn"> 
                                <i class="fas fa-sync"></i> ACTUALIZAR
                            </button>
                            <a href="ControladorProductos?accion=listar" class="btn btn-secondary">
                                Cancelar
                                <i class="bi bi-x-lg"></i>
                            </a>
                        </fieldset>
                    </form>

                </div>
            </div>
        </main>


        <style>

            /* Estilos personalizados para el formulario */
            .formulario {
                width: 100%; /* Asegura que el formulario ocupe el ancho completo del contenedor */
                margin: 0 auto; /* Centra el formulario horizontalmente si hay un contenedor con un ancho fijo */
            }

            .formulario .form-group {
                margin-bottom: 1rem; /* Espaciado inferior entre grupos de formulario */
            }

            .formulario .form-control {
                width: 100%; /* Asegura que los campos de entrada ocupen todo el ancho disponible */
            }

            .formulario__input {
                width: 100%; /* Asegura que los campos de selección ocupen todo el ancho disponible */
            }

            .formulario__grupo-btn-enviar {
                display: flex;
                justify-content: space-between; /* Espacia los botones a los extremos del contenedor */
                margin-top: 1rem; /* Espaciado superior entre el contenido del formulario y los botones */
            }

            .formulario__btn {
                background-color: #ffc107; /* Color de fondo del botón */
                border: none; /* Sin borde */
                color: #fff; /* Color del texto */
                padding: 0.5rem 1rem; /* Espaciado interno del botón */
                font-size: 1rem; /* Tamaño del texto del botón */
                cursor: pointer; /* Cursor de mano al pasar por encima */
            }

            .btn-secondary {
                background-color: #6c757d; /* Color de fondo del botón secundario */
                border: none; /* Sin borde */
                color: #fff; /* Color del texto */
                padding: 0.5rem 1rem; /* Espaciado interno del botón */
                font-size: 1rem; /* Tamaño del texto del botón */
                cursor: pointer; /* Cursor de mano al pasar por encima */
            }

            .formulario__input:focus, .form-control:focus {
                border-color: #ffdf7e; /* Color del borde cuando el campo está en foco */
                box-shadow: 0 0 0 0.2rem rgba(255, 223, 126, 0.25); /* Sombra del campo en foco */
            }


        </style>

        <h1> ${mensaje}</h1>


        <!-- Bootstrap CSS y JavaScript -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                crossorigin="anonymous"
        ></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
                integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
                crossorigin="anonymous"
        ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
                integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
                crossorigin="anonymous"
        ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" 
                integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
                crossorigin="anonymous"
        ></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script><%--lo de las alertas--%>
    </body>
</html>
