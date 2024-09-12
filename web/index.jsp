
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> 

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>ZAP INVENTARIOS</title>

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


    </head>

    <body>
        <nav class="navbar navbar-expand-lg border-3 fixed-top">
            <div class="container-fluid">
                <a>
                    <img src="./img/llogo.jpg" alt="" height="40" width="40" style="left: 30px">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item ml-5">
                            <a class="btn btn-outline-light etiqueta_a" href="ControladorProductos?accion=listar" target="myframe">Bodega/Productos</a>
                        </li>

                        <li class="nav-item">
                            <a class="btn btn-outline-light etiqueta_a" href="ControladorMovimientos?accion=listar" target="myframe">Nueva Compra</a>
                        </li>

                        <li class="nav-item">
                            <a class="btn btn-outline-light etiqueta_a" href="ControladorFacturas?accion=listar" target="myframe">Facturas</a>
                        </li>

                        <li class="nav-item">
                            <a class="btn btn-outline-light etiqueta_a" href="ControladorProveedores?accion=listar" target="myframe">Proveedores</a>
                        </li>

                        <li class="nav-item dropdown">
                            <a class="btn btn-outline-light dropdown-toggle etiqueta_a" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Registros Varios
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="ControladorUsuarios?accion=listar" target="myframe">Usuarios</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="ControladorUnidMedida?accion=listar" target="myframe">Unidad Medida</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="ControladorCategorias?accion=listar" target="myframe">Categorias</a></li>
                                <li><hr class="dropdown-divider"></li>
                           
                            </ul>
                        </li>
                    </ul>

                    <form action="ControladorValidar" method="POST" class="mx-auto" style="margin-left: 25px">
                        <!-- Botón de cierre de sesión -->
                        <div class="me-auto">
                            <button class="btn btn-outline-dark my-btn salir-btn" name="accion" value="Salir">Cerrar Sesión</button>
                        </div>
                    </form>
                </div>
            </div>
        </nav>

  

        <div class="container-fluid p-2">
            <div class="row">
                <div class="col-12" style="height: 620px;">
                    <iframe name="myframe" style="height: 100%; width: 100%;"></iframe>
                </div>
            </div>
        </div>
    </body>



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


    <!-- Funsion para ajustar el ancho automaticamente -->
    <script>
        function resizeIframe() {
            var iframe = document.getElementsByName('myframe')[0];
            iframe.style.height = window.innerHeight - iframe.getBoundingClientRect().top + 'px';
        }

        window.addEventListener('resize', resizeIframe);
        window.addEventListener('load', resizeIframe);
    </script>



</body>


</html>
