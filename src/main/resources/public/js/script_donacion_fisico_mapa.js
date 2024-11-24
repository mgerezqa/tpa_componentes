// Inicializa el mapa y añade marcadores

console.log(heladeras);
var map = L.map('map').setView([-34.6037, -58.3816], 12);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 18,
    attribution: '© OpenStreetMap contributors'
}).addTo(map);


// Detectar el evento de expansión
document.getElementById('contenedor_donar_por_heladera').addEventListener('shown.bs.collapse', function () {
    map.invalidateSize();
});


// Función para manejar la selección de una heladera y mostrar la información en el formulario
function mostrarInformacionHeladera(heladera) {
    document.getElementById("heladera").value = heladera.id;
    document.getElementById("estado").value = heladera.estado;
    document.getElementById("estacion-name").value = heladera.nombre;
    document.getElementById("direccion").value = heladera.direccion;
    document.getElementById("temperatura").value = heladera.temperatura;
    document.getElementById("capacidad-total").value = heladera.capacidad;
    document.getElementById("viandas-disponibles").value = heladera.viandas;
    document.getElementById("inicio-operacion").value = heladera.inicioOperacion;
    document.getElementById("boton-informe-reparacion").disabled = false;

    // Completar modal con datos
    document.getElementById("nombre-estacion-modal").value = heladera.nombre;
}

// Añade marcadores al mapa con la información de cada heladera
heladeras.forEach(heladera => {
    var marker = L.marker(heladera.coordenadas).addTo(map);
    marker.bindPopup("<b>" + heladera.nombre + "</b>");

    // Manejar clic en el marcador
    marker.on('click', function() {
        mostrarInformacionHeladera(heladera);
    });
});

