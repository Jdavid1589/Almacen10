
// Funsion para DataTable Consecutivo 2
$(document).ready(function () {
    // Inicializa la tabla DataTables
    var table = $('#myTable').DataTable({
        dom: 'Blftrip', // Mueve los elementos de paginación al final de la tabla
        buttons: [
            {
                extend: 'excelHtml5',
                text: '<i class="fas fa-file-excel"></i> ',
                titleAttr: 'Exportar a Excel',
                className: 'btn btn-success'
            }
        ],
        lengthMenu: [10, 15, 20, 100],
        columnDefs: [
            {className: 'centered', targets: '_all'}, // Aplica la clase 'centered' a todas las columnas
            {orderable: false, targets: [0, 2, 3, 4, 5, 6, 7, 8, 9, 10]},
            {searchable: false, targets: [0, 6, 7, 8, 9, 10]}
        ],
        pageLength: 5,
        destroy: true,
        
        order: [[1, 'desc']], // Asegura el orden descendente por la columna de fecha
        pagingType: 'simple_numbers', // Tipo de paginación simple con números
        language: {
            processing: 'Procesando...',
            "lengthMenu": "<span style='color: #09f; font-size: 18px;  padding:0px 10px '>Mostrar _MENU_ Registros </span>",
            "zeroRecords": "No se Encontraron Resultados",
            "emptyTable": "Ningún dato disponible en esta tabla",
            "info": "<span style='color: #09f; font-size: 18px;'>Mostrando _START_ a _END_ de _TOTAL_ Entradas</span>",
            "infoEmpty": "<span style='color: #09f; font-size: 18px;'>Mostrando 0 a 0 de 0 Entradas</span>",
            "infoFiltered": "<span style='color: purple; font-size: 14px;'>(filtrado de un total de _MAX_ entradas)</span>",
            "search": "<span style='color: #09f; font-size: 18px; border-bottom: 2px  solid #ccc;'>Buscar:</span>",
            paginate: {
                first: 'Primero',
                last: 'Último',
                next: 'Siguiente',
                previous: 'Anterior'
            },
            aria: {
                sortAscending: ': Activar para ordenar la columna ascendente',
                sortDescending: ': Activar para ordenar la columna descendente'
            }
        }
    });
    
     // Mueve los botones a una posición personalizada en la página
    $('.dt-buttons', table.table().container()).appendTo('#custom-buttons-container'); // Mueve los botones al contenedor deseado

    // Mueve los botones a la izquierda de la tabla
    $('.dt-buttons', table.table().container()).appendTo($('#miTabla_wrapper .dataTables_filter'));
    
    // Función para limitar los botones de paginación
        function limitPagination(table) {
        var pagination = $(table.table().container()).find('.dataTables_paginate .paginate_button');
        var numPages = 4; // Limita a 4 botones de paginación

        pagination.each(function (index, element) {
            if (index > numPages && !$(element).hasClass('next') && !$(element).hasClass('previous')) {
                $(element).css('display', 'none');
            }
        });
    }

    // Llama a la función limitPagination cuando se redibuja la tabla
    table.on('draw', function () {
        limitPagination(table);
    });
    // Llama a la función limitPagination inicialmente
    limitPagination(table);
})
        ;




