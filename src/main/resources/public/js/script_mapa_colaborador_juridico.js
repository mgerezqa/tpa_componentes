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



// Añadir un marcador y obtener dirección al hacer clic en el mapa
map.on('click', async (e) => {
    const { lat, lng } = e.latlng;
    
    // Intentar obtener la dirección
    try {
        const address = await getAddressFromCoords(lat, lng);
        document.getElementById('donationLocation').value = address.fullAddress; // Colocar dirección en el formulario
        document.getElementById('donationNeighborhood').value = address.neighborhood;
        document.getElementById('donationCity').value = address.city;
        document.getElementById('donationState').value = address.state;
        
        // Habilitar el botón de donación
        document.getElementById('boton-donar-heladera').removeAttribute('disabled');
        
        // Opcional: Colocar marcador en el mapa con la dirección
        if (selectedMarker) {
            selectedMarker.setLatLng(e.latlng).bindPopup(`Dirección: ${address.fullAddress}`).openPopup();
        } else {
            selectedMarker = L.marker([lat, lng]).addTo(map)
                .bindPopup(`Dirección: ${address.fullAddress}`)
                .openPopup();
        }

    } catch (error) {
        console.error('Error al obtener la dirección:', error);
        alert('No se pudo obtener la dirección');
    }
});

// Función para obtener la dirección a partir de coordenadas
async function getAddressFromCoords(lat, lng) {
    const response = await fetch(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}`);
    if (!response.ok) throw new Error('Error al obtener la dirección');
    const data = await response.json();
    return {
        fullAddress: `${data.address.road || 'Sin calle'}, ${data.address.house_number || ''}`,
        neighborhood: data.address.suburb || 'Sin barrio',
        city: data.address.city || data.address.town || 'Sin localidad',
        state: data.address.state || 'Sin provincia'
    };
}

// Inicializar tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});


// Enviar el formulario de donación
document.getElementById('donationForm').addEventListener('submit', function(event) {
    event.preventDefault();
    alert('¡Gracias por tu donación! La ubicación seleccionada y la información se han registrado.');
    // Aquí puedes agregar lógica para enviar la información a un servidor
    this.reset(); // Limpiar el formulario después de enviar
    if (selectedMarker) {
        map.removeLayer(selectedMarker); // Eliminar marcador del mapa
        selectedMarker = null; // Resetear marcador
    }
    document.getElementById('boton-donar-heladera').setAttribute('disabled', 'true'); // Deshabilitar botón de donación
});


