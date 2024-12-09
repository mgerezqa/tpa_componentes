document.addEventListener('DOMContentLoaded', function() {
    const selectOferta = document.getElementById('campo_tipo_canje_fisico');
    const campoPuntos = document.getElementById('campo_costo_puntos_fisico');
    const imagenProducto = document.getElementById('imagen_producto');

    // Función para actualizar los datos mostrados
    function actualizarDatosOferta() {
        const opcionSeleccionada = selectOferta.options[selectOferta.selectedIndex];

        // Actualizar puntos
        campoPuntos.value = opcionSeleccionada.dataset.puntos;

        // Actualizar imagen
        imagenProducto.src = opcionSeleccionada.dataset.imagen;
    }

    // Actualizar al cargar la página
    actualizarDatosOferta();

    // Actualizar cuando cambie la selección
    selectOferta.addEventListener('change', actualizarDatosOferta);
});