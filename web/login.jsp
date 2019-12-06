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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css">
        <script type="text/javascript" src="./js/jquery/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    
    </head>
    <body>
        <div class="wrapper fadeInDown">
            <div id="formContent">
             
              <div class="fadeIn first">
                <img src="./img/Logotipo_Azul.png" alt="logo-empresa" id="icon"/>
              </div>

              <form action="Login" method="post" name="inicioSesion">
                <input type="text" id="login" class="fadeIn second" name="usuario" placeholder="Usuario" required>
                <input type="password" id="password" class="fadeIn third" name="contrasena" placeholder="Contraseña" required>
                <input type="submit" class="fadeIn fourth" value="Enviar">
              </form>

            </div>
        </div>
        
   
    </body>
</html>
