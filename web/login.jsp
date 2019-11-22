<%-- 
    Document   : login
    Created on : 14/10/2019, 01:53:07 PM
    Author     : Toshiba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
 	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	
        <title>Plan Piso</title>
	<link rel="stylesheet" href="./css/login.css">
    </head>
    <body>
        <div class="contenedor-general"> 
            <div class="contenedor-logo-general">
                <figure class="contenedor-logo">
                    <img class="logo" src="./img/Logotipo_Azul.png" alt="logo-empresa">
                </figure>
            </div>

            <div class="contenedor-formulario">

                <div class="contenedor-titulo">
                    <h3 class="titulo">INICIAR SESION</h3>   
                </div>
                 <form class="form-inicio" action="Login" method="post" name="inicioSesion">
                    <input type="text" name="usuario" placeholder="Usuario" required>
                    <input type="password" name="contrasena" placeholder="Contraseña" required >
                    <input class="btn-enviar" type="submit" value="Enviar">
                </form>

            </div>
    </div>
    </body>
</html>
