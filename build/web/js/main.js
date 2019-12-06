$(document).ready(function(){
   
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
        console.log("entro al evento");
       
        var fecha = new Date();
        var fechaActual = (fecha.getFullYear())+"-"+(fecha.getMonth()+1)+"-"+(fecha.getDate());
       
        $("#contenedor-excel-conciliacion").table2excel({
           name: "Conciliacion",
           filename: "Conciliacion_"+fechaActual,
           fileext: ".xls",
           preserveColors: true
        });
    });
    
    var datosTabla = $('#table-boostrap').DataTable( {
        "scrollY": 300,
        "scrollX": true,
        language: {
            "decimal": "",
            "emptyTable": "No hay información",
            "info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
            "infoEmpty": "Mostrando 0 to 0 of 0 Entradas",
            "infoFiltered": "(Filtrado de _MAX_ total entradas)",
            "infoPostFix": "",
            "thousands": ",",
            "lengthMenu":"Mostrar _MENU_ registros",
            "loadingRecords": "Cargando...",
            "processing": "Procesando...",
            "search": "Buscar:",
            "zeroRecords": "Sin resultados encontrados",
            "paginate": {
                "first": "Primero",
                "last": "Ultimo",
                "next": "Siguiente",
                "previous": "Anterior"
            }
        },
        "lengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "Todos"] ],
        pageLength: 10
    });
    
     var datosTabla2 = $('.table-boostrap-2').DataTable( {
        "scrollY": 300,
        "scrollX": true,
        language: {
            "decimal": "",
            "emptyTable": "No hay información",
            "info": "Mostrando _START_ a _END_ de _TOTAL_ Entradas",
            "infoEmpty": "Mostrando 0 to 0 of 0 Entradas",
            "infoFiltered": "(Filtrado de _MAX_ total entradas)",
            "infoPostFix": "",
            "thousands": ",",
            "lengthMenu":"Mostrar _MENU_ registros",
            "loadingRecords": "Cargando...",
            "processing": "Procesando...",
            "search": " ",
            "zeroRecords": "Sin resultados encontrados",
            "paginate": {
                "first": "Primero",
                "last": "Ultimo",
                "next": "Siguiente",
                "previous": "Anterior"
            }
        },
        "bFilter": false,
        "lengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "Todos"] ],
        "pageLength": 10
    });
    
    var datosTabla3 = $('.table-boostrap-3').DataTable( {
        "scrollY": 300,
        "scrollX": true,
        language: {
            "decimal": "",
            "emptyTable": "No hay información",
            "info": "Mostrando _TOTAL_ Entradas",
            "infoEmpty": "",
            "infoFiltered": "",
            "infoPostFix": "",
            "thousands": ",",
            "lengthMenu":"",
            "loadingRecords": "Cargando...",
            "processing": "Procesando...",
            "search": " ",
            "zeroRecords": "Sin resultados encontrados",
            "paginate": {
                "first": "",
                "last": "",
                "next": "",
                "previous": ""
            }
        },
        "bFilter": false,
        "paging":false,
        "lengthMenu": false,
        "pageLength": -1
    });
    /*
    var info = datosTabla.page.info();
    var count = info.end;
    console.log(count);*/
});


