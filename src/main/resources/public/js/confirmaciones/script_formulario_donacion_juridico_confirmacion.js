document.addEventListener('DOMContentLoaded', () => {
    const confirmarDonacionFinal = document.getElementById('confirmarDonacionFinal');
    const formDinero = document.getElementById('formDinero');
    const formProductoServicio = document.getElementById('formProductoServicio');

    let formularioActivo = null;

    // Manejar el envío del formulario de dinero
    if (formDinero) {
        formDinero.addEventListener('submit', (event) => {
            event.preventDefault();
            formularioActivo = formDinero;
            mostrarModalConfirmacion();
        });
    }

    // Manejar el envío del formulario de producto/servicio
    if (formProductoServicio) {
        formProductoServicio.addEventListener('submit', (event) => {
            event.preventDefault();
            formularioActivo = formProductoServicio;
            mostrarModalConfirmacion();
        });
    }

    if (confirmarDonacionFinal) {
        confirmarDonacionFinal.addEventListener('click', (event) => {
            event.preventDefault();
            if (formularioActivo) {
                const formData = new FormData(formularioActivo);
                enviarDatosFormulario(formData,formularioActivo.action);
            }
        });
    }
});


function mostrarModalConfirmacion() {
    const confirmarDonacionModal = new bootstrap.Modal(document.getElementById('confirmarDonacionModal'));
    confirmarDonacionModal.show();
}


function enviarDatosFormulario(datos, action) {
    fetch(action, {
        method: 'POST',
        body: datos
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error en la respuesta del servidor');
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            if (data.success) {
                // Cerrar el modal de confirmación
                const confirmarCanjeModal = bootstrap.Modal.getInstance(document.getElementById('confirmarDonacionModal'));
                confirmarCanjeModal.hide();

                // Mostrar el modal de éxito
                const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
                modalExito.show();

                // Opcional: redirigir después de mostrar el modal
                modalExito._element.addEventListener('hidden.bs.modal', function () {
                    window.location.href = '/donaciones';
                });
            } else {
                alert(data.message || 'Error al procesar el canje');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al procesar la solicitud');
        });
}


// function enviarDatosFormulario(datos) {
//     fetch('/ruta-para-enviar-datos', {
//         method: 'POST',
//         body: datos
//     })
//     .then(response => {
//         if (response.ok) {
//             const confirmarDonacionModal = bootstrap.Modal.getInstance(document.getElementById('confirmarDonacionModal'));
//             confirmarDonacionModal.hide();

//             const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
//             modalExito.show();
//         } else {
//             alert('Error al registrar la donación. Intente nuevamente.');
//         }
//     })
//     .catch(error => console.error('Error:', error));
// }