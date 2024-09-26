
<%@page import="Persistencia.DaoCategorias"%>
<%@page import="Modelo.Categorias"%>
<%@page import="Persistencia.DaoProveedores"%>
<%@page import="Modelo.Proveedores"%>
<%@page import="Persistencia.DaoUnidMedida"%>
<%@page import="Modelo.UnidadMedida"%>
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


        <link href="Vistas/EstilosCSS/EstilosGen3.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/Estilos_generales.css" rel="stylesheet" type="text/css"/>
          <link href="Vistas/EstilosCSS/EstilosFactura.css" rel="stylesheet" type="text/css"/>



    </head>
    <body>


        <main>

            <div class="container-fluid text-center" style="position: relative; top: -22px;">
                <div class="row">
                    <div class="col-md-12 mx-auto">
                        <br>
                        <h4 class="text-primary elegant-font mt-5" style="font-weight: bold; font-size: 30px;">EDITAR  PRODUCTOS</h4>
                    </div>
                </div>
            </div>
            <!--Barra de Navegacion -->

            <style>

         
            </style>


            <%
                int id = Integer.parseInt((String) request.getAttribute("idEditar"));
                Productos prod = DaoProductos.obtenerProductosPorId(id);
            %>

            <form  class="formulario" id="formulario"   action="ControladorProductos"  autocomplete="off" method="POST"  >    


                <div class="formulario__grupo" >
                    <label for="fechaActualizacion"  class="formulario__label" >Fecha</label>
                    <input 
                        type="date" 
                        value="<%= prod.getFechaActualizacion()%>"
                        class="formulario__input"                                
                        id="fechaActualizacion" 
                        name="fechaActualizacion" 

                        placeholder="Ingrese Nombre">
                </div>     
                        
                <div class="formulario__grupo" >
                    <label for="productos"  class="formulario__label" >Producto</label>
                    <input 
                        type="text" 
                        value="<%= prod.getProductos()%>"
                        class="formulario__input"                                
                        id="productos" 
                        name="productos" 

                        placeholder="Ingrese Nombre">
                </div>                           

                <div class="formulario__grupo">
                    <label for="plu"class="formulario__label" >Plu</label>
                    <input
                        type="text" 
                        value="<%= prod.getPlu()%>"
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
                        value="<%= prod.getCantidadDisponible()%>"
                        name="cantidadDisponible" 
                        placeholder="Ingrese Cantidad">
                </div>

                <div class="formulario__grupo">
                    <label class="formulario__label" for="unidadMedidaId">Unidad Medida</label>
                    <select class="formulario__input"
                            id="unidadMedidaId" 
                            name="unidadMedidaId">
                        <option value="0">Seleccione Unidad</option>
                        <%
                            List<UnidadMedida> unidadM = DaoUnidMedida.listar();
                            int unidadMedidaSeleccionada = prod.getUnidadMedidaId(); // Obtener el ID seleccionado
                            if (unidadM != null) {
                                for (UnidadMedida un : unidadM) {
                                    if (un != null) {
                                        int idUnidad = un.getIdUnidadMedida();
                                        // Comparar si el valor de la unidad es el mismo que el seleccionado
                                        String selected = (idUnidad == unidadMedidaSeleccionada) ? "selected" : "";
                        %>
                        <option value="<%= idUnidad%>" <%= selected%>><%= un.getUnidadMedida()%></option>
                        <%
                                    }
                                }
                            }
                        %>
                    </select>
                </div>

                <div class="formulario__grupo">
                    <label class="formulario__label" for="proveedoresId">Proveedor</label>
                    <select class="formulario__input" id="proveedoresId" name="proveedoresId">
                        <option value="0">Seleccione Proveedor</option>
                        <%
                            List<Proveedores> provee2 = DaoProveedores.listar();
                            int proveedorSeleccionado = prod.getProveedoresId(); // Obtener el ID del proveedor seleccionado
                            if (provee2 != null) {
                                for (Proveedores pr : provee2) {
                                    if (pr != null) {
                                        int idProveedor = pr.getIdProveedor();
                                        // Comparar si el valor del proveedor es el mismo que el seleccionado
                                        String selected = (idProveedor == proveedorSeleccionado) ? "selected" : "";
                        %>
                        <option value="<%= idProveedor%>" <%= selected%>><%= pr.getProveedor()%></option>
                        <%
                                    }
                                }
                            }
                        %>
                    </select>
                </div>


                <div class="formulario__grupo">
                    <label class="formulario__label" for="categoriasId">Categorías</label>
                    <select class="formulario__input" id="categoriasId" name="categoriasId">
                        <option value="0">Seleccione categoría</option>
                        <%
                            List<Categorias> categorias2 = DaoCategorias.listar();
                            int categoriaSeleccionada = prod.getCategoriasId(); // Obtener el ID de la categoría seleccionada
                            if (categorias2 != null) {
                                for (Categorias cat : categorias2) {
                                    if (cat != null) {
                                        int idCategoria = cat.getIdCategorias();
                                        // Comparar si el valor de la categoría es el mismo que el seleccionado
                                        String selected = (idCategoria == categoriaSeleccionada) ? "selected" : "";
                        %>
                        <option value="<%= idCategoria%>" <%= selected%>><%= cat.getTipos()%></option>
                        <%
                                    }
                                }
                            }
                        %>
                    </select>
                </div>


                <div class="formulario__grupo">
                    <label for="precioCompra"class="formulario__label" >Presio Compra</label>
                    <input
                        type="text" 
                        class="formulario__input"                          
                        id="precioCompra"
                        value="<%= prod.getPrecioCompra()%>"
                        name="precioCompra" 
                        placeholder="Ingrese precio Compra">
                </div>

                <div class="formulario__grupo " id="grupo__ID">
                    <label for="precioVenta"class="formulario__label" >Presio Venta</label>
                    <input
                        type="text" 
                        value="<%= prod.getPrecioVenta()%>"
                        class="formulario__input"                          
                        id="precioVenta"
                        name="precioVenta" 
                        placeholder="Ingrese precio Compra">
                </div>
                <div class="formulario__grupo " id="grupo__ID">
                    <label for="porcIva"class="formulario__label" >% Iva</label>
                    <input
                        type="text" 
                        value="<%= prod.getPorcIva()%>"
                        class="formulario__input"                          
                        id="porcIva"
                        name="porcIva" 
                        placeholder="Ingrese precio Compra">
                </div>
       


                <hr>
                <!-- Grupo: Botones -->
                <fieldset class="formulario__grupo-btn-enviar my-2">
                     <input type="hidden" name="txtid" value="<%=prod.getIdProductos()%>">
                    <button class="formulario__btn"  type="submit" name="accion" value="actualizar" >
                        <i class="fas fa-save "></i> EDITAR  
                    </button>                      

                    <button type="submit" class="formulario__btn_cancelar"  name="accion" value="listar">
                        <i class="bi bi-x-lg"></i> CANCELAR
                    </button>

                </fieldset>



            </form>
        </main>

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
