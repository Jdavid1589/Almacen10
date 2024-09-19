
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

        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <link href="Vistas/EstilosCSS/EstilosIndex.css" rel="stylesheet" type="text/css"/>

    </head>

    <body>
        <nav class="navbar navbar-expand-lg border-3 fixed-top">
            <div class="container-fluid">
                <a>
                    <img src="./img/LogoJB3.jpg" alt="" height="40" width="40" style="left: 30px">
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
                            <a class="btn btn-outline-light etiqueta_a" href="ControladorCompras?accion=FacturaCompra" target="myframe">Nueva Compra</a>
                        </li>

                        <li class="nav-item">
                            <a class="btn btn-outline-light etiqueta_a" href="ControladorFacturaventa?accion=ListaFactura" target="myframe">Nueva Venta</a>
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
                                <li><a class="dropdown-item"  href="ControladorProveedores?accion=listar" target="myframe">Proveedores</a></li>


                            </ul>
                        </li>
                    </ul>

                    <!-- Posiciona el botón de cierre de sesión en la esquina derecha -->
                    <form action="ControladorValidar" method="POST" class="mx-auto">
                        <button class="btn btn-outline-light-salir" name="accion" value="Salir">Cerrar Sesión</button>
                    </form>
                </div>
            </div>
        </nav>

        <div id="welcome-container" class="welcome-container">
            <div id="cuadro" class="elegant-box">
                <img src="./img/LogoJB3.jpg" alt="Logo" class="welcome-logo"/>
                <h1 class="welcome-title">Bienvenido al Sistema de Inventarios</h1>
            </div>
        </div>


        <div class="row">
            <div class="col-12" style="height: 620px;">
                <iframe name="myframe" id="myframe" onload="hideWelcomeTitle()" style="height: 100%; width: 100%; border: none;"></iframe>
            </div>
        </div>
    </div>


    <script>
        // Función para ocultar el título cuando el iframe cargue una nueva página
        function hideWelcomeTitle() {
            var iframe = document.getElementById('myframe');
            var welcomeContainer = document.getElementById('welcome-container');
            if (iframe.contentDocument && iframe.contentDocument.URL !== "about:blank") {
                welcomeContainer.style.display = 'none'; // Oculta el título al cargar contenido
            }
        }
    </script>

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