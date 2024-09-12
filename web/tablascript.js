document.addEventListener("DOMContentLoaded", function() {
    // Simular datos de inventario (puedes reemplazar esto con datos reales obtenidos del servidor)
    var inventarioData = [
        { producto: " A", existencias: 50  },
        { producto: " B", existencias: 100 },
        { producto: " C", existencias: 75 },
        


    ];

    // Obtener la tabla
    var tablaInventario = document.getElementById("tablaInventario");

    // Iterar sobre los datos de inventario y agregar filas a la tabla
    inventarioData.forEach(function(item) {
        var fila = tablaInventario.insertRow();
        var celdaProducto = fila.insertCell(0);
        var celdaExistencias = fila.insertCell(1);
        var celdaExistencias = fila.insertCell(2);
        var celdaExistencias = fila.insertCell(3);
        var celdaExistencias = fila.insertCell(3);


        celdaProducto.textContent = item.producto;
        celdaExistencias.textContent = item.existencias;
    });
});



