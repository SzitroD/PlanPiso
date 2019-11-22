$(document).ready(function(){
   
    var altura = $('.primer-titulo').offset().top+100;
    var slide = $(".contenedor-menu");
    var header = $(".header");
    var contIcono = $(".contenedor-icono");
    var ancho = $(window).width();

    //console.log(ancho);

    $(window).on('scroll',function(){
       if( $(window).scrollTop() > altura ){
           $('.header').addClass('menu-fixed');
       }else{
           $('.header').removeClass('menu-fixed');
       } 
    });
    
    $("#btn-exportar-vehiculos").click(function(){
        //console.log("entro al evento");
       
        var fecha = new Date();
        var fechaActual = (fecha.getFullYear())+"-"+(fecha.getMonth()+1)+"-"+(fecha.getDate());
       
        $("#contenedor-excel-vehiculos").table2excel({
           name: "Resumen_Vehiculos",
           filename: "Resumen_Vehiculos_"+fechaActual,
           fileext: ".xls",
           preserveColors: true
        });
    });
    
     $("#btn-exportar-resumen").click(function(){
        //console.log("entro al evento");
       
        var fecha = new Date();
        var fechaActual = (fecha.getFullYear())+"-"+(fecha.getMonth()+1)+"-"+(fecha.getDate());
       
        $("#contenedor-excel-resumen").table2excel({
           name: "Resumen",
           filename: "Resumen_"+fechaActual,
           fileext: ".xls",
           preserveColors: true
        });
    });
    
     $("#btn-exportar-conciliacion").click(function(){
        //console.log("entro al evento");
       
        var fecha = new Date();
        var fechaActual = (fecha.getFullYear())+"-"+(fecha.getMonth()+1)+"-"+(fecha.getDate());
       
        $("#contenedor-excel-conciliacion").table2excel({
           name: "Conciliacion",
           filename: "Conciliacion_"+fechaActual,
           fileext: ".xls",
           preserveColors: true
        });
    });


    $(".icon-menu").click(function(){
        if(slide.hasClass("menu-telefono")){
          slide.removeClass("menu-telefono");
          header.addClass("slide-menu");
          contIcono.css("height","15%");
        }else{
          slide.addClass("menu-telefono");
          header.removeClass("slide-menu");
          contIcono.css("height","100%");
        }
    });
    
});


