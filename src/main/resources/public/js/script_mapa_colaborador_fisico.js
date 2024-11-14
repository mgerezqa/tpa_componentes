// Inicializar el mapa de Leaflet
var map = L.map('map').setView([-34.6037, -58.3816], 12); // Coordenadas de Buenos Aires (puedes cambiar)

// Agregar el mapa base de OpenStreetMap
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 18,
    attribution: '© OpenStreetMap contributors'
}).addTo(map);

// Variable para el marcador de la ubicación seleccionada
var selectedMarker = null;

// Mostrar tooltip al pasar el cursor sobre el mapa
var tooltip = document.getElementById('tooltip-map');
document.getElementById('map').addEventListener('mouseover', function() {
    tooltip.style.display = 'block';
});
document.getElementById('map').addEventListener('mouseout', function() {
    tooltip.style.display = 'none';
});


// Inicializar tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});

// Función para agregar un marcador en el mapa

function agregarUbicacion(coordenadas, nombre) {
var marker = L.marker(coordenadas).addTo(map);
marker.bindPopup("<b>" + nombre + "</b>").openPopup();
}

// Agregar ubicaciones
agregarUbicacion([-34.59845242130508, -58.42017359115016], "Estación Medrano");
agregarUbicacion([-34.6589172508621, -58.467761023827016], "Estación Campus");
agregarUbicacion([-34.617687126912664, -58.445406804407504], "Estación Ferro");
agregarUbicacion([-34.59670786675178, -58.3912542358698], "Estación Ateneo");
agregarUbicacion([-34.545184590496156,-58.44867979038356], "Estación River");

