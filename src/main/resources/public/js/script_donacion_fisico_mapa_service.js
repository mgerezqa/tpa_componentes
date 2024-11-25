document.addEventListener('DOMContentLoaded', async function() {
    const map2 = L.map('map-service').setView([-34.6037, -58.3816], 12);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
        attribution: '© OpenStreetMap contributors'
    }).addTo(map2);

    let selectedMarker = null;

    map2.on('click', async (e) => {
        const { lat, lng } = e.latlng;

        try {
            const address = await getAddressFromCoords(lat, lng);
            actualizarPanelInformacion(address);
            manejarMarcadorMapa(e.latlng, address.fullAddress);

            // Enviar coordenadas al backend para obtener comunidades recomendadas
            const comunidades = await obtenerComunidadesRecomendadas(lat, lng, 10, 5.0);
            mostrarComunidadesEnMapa(comunidades.comunidades); // Acceder a la propiedad 'comunidades'
        } catch (error) {
            console.error('Error al obtener la dirección o las comunidades:', error);
            alert('No se pudo obtener la dirección o las comunidades. Por favor, intenta de nuevo.');
        }
    });

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

    function actualizarPanelInformacion(address) {
        document.getElementById('comunidad-direccion').value = address.fullAddress;
        document.getElementById('comunidad-barrio').value = address.neighborhood;
        document.getElementById('comunidad-ciudad').value = address.city;
        document.getElementById('comunidad-provincia').value = address.state;
    }

    function manejarMarcadorMapa(latlng, fullAddress) {
        if (selectedMarker) {
            selectedMarker.setLatLng(latlng).bindPopup(`Dirección: ${fullAddress}`).openPopup();
        } else {
            selectedMarker = L.marker([latlng.lat, latlng.lng]).addTo(map2)
                .bindPopup(`Dirección: ${fullAddress}`)
                .openPopup();
        }
    }

    document.getElementById('contenedor_donar_por_ubicacion_service').addEventListener('shown.bs.collapse', function () {
        map2.invalidateSize();
    });

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

        getAddressFromCoords(lat, lon)
            .then(address => {
                actualizarPanelInformacion(address);
                manejarMarcadorMapa({ lat, lng: lon }, address.fullAddress);

                // Enviar coordenadas al backend para obtener comunidades recomendadas
                obtenerComunidadesRecomendadas(lat, lon, 10, 5.0).then(comunidades => {
                    mostrarComunidadesEnMapa(comunidades.comunidades); // Acceder a la propiedad 'comunidades'
                });
            })
            .catch(error => {
                console.error('Error al obtener la dirección o las comunidades:', error);
                alert('No se pudo obtener la dirección o las comunidades. Por favor, intenta de nuevo.');
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

    // Función para obtener las comunidades recomendadas desde el backend
    async function obtenerComunidadesRecomendadas(latitud, longitud, max, distanciaMax) {
        try {
            const response = await fetch(`http://localhost:8081/recomendacion-comunidades?latitud=${latitud}&longitud=${longitud}&max=${max}&distanciaMax=${distanciaMax}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Error al obtener las comunidades recomendadas');
            }

            const textResponse = await response.text();
            console.log("Text Response:", textResponse);
            const data = JSON.parse(textResponse);
            console.log("Comunidades obtenidas:", data);
            return data;
        } catch (error) {
            console.error('Error al obtener las comunidades recomendadas:', error);
        }
    }

    // Función para mostrar las comunidades en el mapa
    function mostrarComunidadesEnMapa(comunidades) {
        comunidades.forEach(comunidad => {
            L.marker([comunidad.lat, comunidad.lon]).addTo(map2)
                .bindPopup(`<b>${comunidad.nombre}</b><br>${comunidad.direccion}`);
        });
    }
});