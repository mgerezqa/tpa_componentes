document.addEventListener('DOMContentLoaded', () => {
    const confirmarCanje = document.getElementById('confirmarCanje');
    const formCanje = document.getElementById('form_canje');
    
    let formularioActivo = null;

    // Manejar el envío del formulario de canje
    if (formCanje) {
        formCanje.addEventListener('submit', (event) => {
            event.preventDefault();
            formularioActivo = formCanje;
            mostrarModalConfirmacion();
        });
    }


    if (confirmarCanje) {
        confirmarCanje.addEventListener('click', (event) => {
            event.preventDefault();
            if (formularioActivo) {
                const formData = new FormData(formularioActivo);
                enviarDatosFormulario(formData);
            }
        });
    }
});


function mostrarModalConfirmacion() {
    const confirmarCanjeModal = new bootstrap.Modal(document.getElementById('confirmarCanje'));
    confirmarCanjeModal.show();
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
                const confirmarCanjeModal = bootstrap.Modal.getInstance(document.getElementById('confirmarCanje'));
                confirmarCanjeModal.hide();

                // Mostrar el modal de éxito
                const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
                modalExito.show();

                // Opcional: redirigir después de mostrar el modal
                modalExito._element.addEventListener('hidden.bs.modal', function () {
                    window.location.href = '/canjes';
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
//             const confirmarCanjeModal = bootstrap.Modal.getInstance(document.getElementById('confirmarCanjeModal'));
//             confirmarCanjeModal.hide();

//             const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
//             modalExito.show();
//         } else {
//             alert('Error al registrar la donación. Intente nuevamente.');
//         }
//     })
//     .catch(error => console.error('Error:', error));
// }