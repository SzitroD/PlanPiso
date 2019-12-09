# PlanPiso
Proyecto Plan Piso 

Este proyecto esta diseñado para agilizar el proceso de financiamiento y conciliacion de los vehiculos disponibles dentro de la empresa
Las clases utilizadas en este proyecto se organizan de la siguiente manera:

control --> Nombre de la carpeta donde estan las clases que hacen el funcionamiento logico de las peticiones al servidor
  ConexionMySQL --> Clase que habre la conexion al servidor de base de datos
  ControlBanco --> Clase que hace las funciones de control de financiera.jsp
  ControlCompras --> Clase que hace las funciones de control de compras.jsp
  ControlPagados --> Clase que hace las funciones de control de pagados.jsp
  ControlReportes --> Clase que hace las funciones de control de archivos.jsp
  ControlVehiculos --> Clase que hace las funciones de control de vehiculos.jsp
  Login --> Clase que hace las funciones de control para iniciar una sesion
  Logout --> Clase que hace las funciones de control para cerrar una sesion
  
dao --> Carpeta donde se encuentran todas las funciones CRUD para crear,observar, modificar o eliminar algun registro en la base de datos
  BancoDAO --> Clase con funciones que alteran las propiedades de cada financiera 
  FinanciarDAO --> Clase con funcciones que hacen el financiamiento de un nuevo vehiculo o que alteran las propiedades de algun vehiculo que este financiado 
  ReportesDAO --> Clase con funciones que registran y listan los archivos subidos a la base de datos (pueden ser archivos normales o de conciliacion)
  UsuariosDAO --> Clase con funciones que validad el estado del usuario dentro de la base de datos
  VehiculosDAO --> Clase con funciones que muestran las propiedades de los vehiculos
  VistaDAO --> Clase con funciones que se basan en una vista principal para acceder a informacion relevante del estado de financiamiento de algun vehiculo
  
modelo --> Carpeta donde se encuentran modelos de las tablas de la base de datos, cada modelo posee un getter y setter para acceder a las propiedades de cada columna dentro de una tabla 
  Bancos --> Clase basada en la tabla "bancos" de la base de datos plan_piso
  Financiar --> Clase basada en la tabla "financiar" de la base de datos plan_piso
  Reportes --> Clase basada en la tabla "reportes" de la base de datos plan_piso
  Usuairos --> Clase basada en la tabla "usuario" de la base de datos plan_piso
  Vehiculos --> Clase basada en la tabla "vehiculo" de la base de datos plan_piso
  VistaCompra --> Clase basada en la vista "vista_general" de la base de datos plan_piso
