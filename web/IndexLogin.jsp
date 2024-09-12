<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
       
        <link href="css/estilo.css" rel="stylesheet" type="text/css"/>
        <link href="Vistas/EstilosCSS/StyleLogin.css" rel="stylesheet" type="text/css"/>

        <title>zap2</title>

    </head>

   <body>

        <div id="cuadro">
            <form action = "ControladorValidar" method="post">
                <div class="form-group">
                    <p id="titulo" > INICIAR SESION</p> 
                    <label id="subtitulo1">USUARIO </label>              
                    <input type="text" name="txtuser" class ="entrada" />
                </div>
                <div class="form-group">
                    <label id="subtitulo1" > CONTRASEÑA </label>
                    <input type="password" name="txtclave" class ="entrada" />
                </div>
                <input type="submit" name="accion" value ="IniciarSesion" id="boton" />   
                <!-- Add the error message display -->
                <div class="form-group text-center">
                    <p style="color: red;">${errorMensaje}</p>
                </div>
            </form>

            <p id="marca"> ZAP2 INVENTARIO</p>
        </div>  
                <a href="img/trew.avif"></a>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

    </body>



    <style>
        *{
            margin: 0px;
            padding: 10px;
    
         
        }
        body{
            background: url(./img/gali.jpg);
            margin: 0; /* Elimina el margen predeterminado del cuerpo */
            padding: 0; /* Elimina el relleno predeterminado del cuerpo */
            /*background-image: url('ruta/de/la/imagen.jpg'); /* Ruta de tu imagen */
            background-size: cover; /* Ajusta la imagen para cubrir todo el fondo */
            background-position: center; /* Centra la imagen en el fondo */
            background-repeat: no-repeat; /* Evita la repetición de la imagen */
            height: 100vh; /* Establece la altura al 100% de la ventana gráfica visible (viewport height) */
            display: flex; /* Utiliza el modelo de caja flexible para centrar el contenido */
            align-items: center; /* Centra verticalmente el contenido */
            justify-content: center; /* Centra horizontalmente el contenido */
            color: white; /* Color del texto, ajusta según tus necesidades */
            font-size: 24px; /* Tamaño del texto, ajusta según tus necesidades */
        }
        
#cuadro{
   
    border-radius: 40px;
    opacity: calc0,2;
}

 



    </style>

</html>
