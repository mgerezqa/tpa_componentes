// Repositorio simulado de heladeras
const heladeras = [
    { nombre: "Estación Medrano", coordenadas: [-34.59845242130508, -58.42017359115016], estado: "Activa", direccion: "Calle Medrano 123", temperatura: "5°C", capacidad: "100", viandas: "20", inicioOperacion: "2021-05-12" },
    { nombre: "Estación Campus", coordenadas: [-34.6589172508621, -58.467761023827016], estado: "Activa", direccion: "Calle Campus 456", temperatura: "4°C", capacidad: "150", viandas: "50", inicioOperacion: "2020-11-23" },
    // Agregar más estaciones según sea necesario
];

// Inicializa el mapa y añade marcadores
var map = L.map('map').setView([-34.6037, -58.3816], 12);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 18,
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

// Función para manejar la selección de una heladera y mostrar la información en el formulario
function mostrarInformacionHeladera(heladera) {
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
