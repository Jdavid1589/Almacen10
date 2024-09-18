<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><!-- comment -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>    
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

        <link href="./Vistas/EstilosCSS/EstilosLogin.css" rel="stylesheet" type="text/css"/>
        <title>Sistema Control Inventarios</title>

    </head>

    <body>

        <div id="login-container">
            <form action="ControladorValidar" method="post">
                <h2 class="form-title">Iniciar Sesión</h2>

                <div class="form-group">
                    <label for="username">Usuario</label>
                    <input type="text" id="username" name="txtuser" class="form-input" required />
                </div>

                <div class="form-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="txtclave" class="form-input" required />
                </div>

                <button type="submit" name="accion" value="IniciarSesion" class="submit-btn">Iniciar Sesión</button>

                <div class="error-message">
                    ${errorMensaje}
                </div>
            </form>

                <p class="brand"><strong>DISTRIBUIDORA NOGALES</strong></p>
        </div>

        <!-- Estilos -->
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                color: #333;
                margin: 0;
                padding: 0;
                background-image: url('./img/img_abarrotes.avif'); /* Cambia esta ruta por la ubicación de tu imagen */
                background-size: cover; /* Asegura que la imagen cubra todo el fondo */
                background-repeat: no-repeat; /* Evita que la imagen se repita */
                background-attachment: fixed; /* Fija la imagen de fondo para un efecto más elegante */
            }
        </style>


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

    </body>
</html>
