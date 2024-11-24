document.addEventListener('DOMContentLoaded', function() {
    // Inicialización del mapa y configuración inicial
    const map2 = L.map('map-service').setView([-34.6037, -58.3816], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: '© OpenStreetMap contributors'
    }).addTo(map2);

    // Variable para el marcador seleccionado
    let selectedMarker = null;

    // Añadir evento para obtener dirección al hacer clic en el mapa
    map2.on('click', async (e) => {
        const { lat, lng } = e.latlng;

        try {
            // Obtener dirección a partir de las coordenadas
            const address = await getAddressFromCoords(lat, lng);

            // Actualizar campos en el panel de información
            actualizarPanelInformacion(address);

            // Añadir o actualizar marcador en el mapa
            manejarMarcadorMapa(e.latlng, address.fullAddress);
        } catch (error) {
            console.error('Error al obtener la dirección:', error);
            alert('No se pudo obtener la dirección. Por favor, intenta de nuevo.');
        }
    });

    // Función para obtener la dirección a partir de coordenadas
    async function getAddressFromCoords(lat, lng) {
        const response = await fetch(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lng}`);
        if (!response.ok) throw new Error('Error al obtener la dirección');
        const data = await response.json();
        return {
            fullAddress: `${data.address.road || 'Sin calle'}, ${data.address.house_number || ''}`.trim(),
            neighborhood: data.address.suburb || 'Sin barrio',
            city: data.address.city || data.address.town || 'Sin localidad',
            state: data.address.state || 'Sin provincia'
        };
    }

    // Función para actualizar el panel de información con la dirección obtenida
    function actualizarPanelInformacion(address) {
        document.getElementById('comunidad-direccion').value = address.fullAddress;
        document.getElementById('comunidad-barrio').value = address.neighborhood;
        document.getElementById('comunidad-ciudad').value = address.city;
        document.getElementById('comunidad-provincia').value = address.state;
    }

    // Función para manejar la creación o actualización del marcador en el mapa
    function manejarMarcadorMapa(latlng, fullAddress) {
        if (selectedMarker) {
            selectedMarker.setLatLng(latlng).bindPopup(`Dirección: ${fullAddress}`).openPopup();
        } else {
            selectedMarker = L.marker([latlng.lat, latlng.lng]).addTo(map2)
                .bindPopup(`Dirección: ${fullAddress}`)
                .openPopup();
        }
    }

    // Detectar el evento de expansión del panel para recalcular el tamaño del mapa
    document.getElementById('contenedor_donar_por_ubicacion_service').addEventListener('shown.bs.collapse', function () {
        map2.invalidateSize();
    });

    // Evento para el botón "Según mi ubicación actual"
    document.getElementById('btn-mi-ubicacion-actual').addEventListener('click', function() {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition, showError);
        } else {
            alert("Geolocation is not supported by this browser.");
        }
    });

    function showPosition(position) {
        const lat = position.coords.latitude;
        const lon = position.coords.longitude;

        // Usar la función existente para obtener la dirección y actualizar el panel
        getAddressFromCoords(lat, lon)
            .then(address => {
                actualizarPanelInformacion(address);
                manejarMarcadorMapa({ lat, lng: lon }, address.fullAddress);
            })
            .catch(error => {
                console.error('Error al obtener la dirección:', error);
                alert('No se pudo obtener la dirección. Por favor, intenta de nuevo.');
            });
    }

    function showError(error) {
        switch(error.code) {
            case error.PERMISSION_DENIED:
                alert("User denied the request for Geolocation.");
                break;
            case error.POSITION_UNAVAILABLE:
                alert("Location information is unavailable.");
                break;
            case error.TIMEOUT:
                alert("The request to get user location timed out.");
                break;
            case error.UNKNOWN_ERROR:
                alert("An unknown error occurred.");
                break;
        }
    }
});