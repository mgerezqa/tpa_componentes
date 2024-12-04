document.addEventListener('DOMContentLoaded', () => {
    const confirmarDonacionFinal = document.getElementById('confirmarDonacionFinal');
    const formDinero = document.getElementById('formDinero');
    const formDonarPorHeladera = document.getElementById('formDonarPorHeladera');
    const formPorReparto = document.getElementById('donarPorReparto');
    const formPorRegistroPV = document.getElementById('donarPorRegistroPV');

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
    if (formDonarPorHeladera) {
        formDonarPorHeladera.addEventListener('submit', (event) => {
            event.preventDefault();
            formularioActivo = formDonarPorHeladera;
            mostrarModalConfirmacion();
        });
    }

    // Manejar el envío del formulario de producto/servicio
    if (formPorReparto) {
        formPorReparto.addEventListener('submit', (event) => {
            event.preventDefault();
            formularioActivo = formPorReparto;
            mostrarModalConfirmacion();
        });
    }

    if (formPorRegistroPV) {
        formPorRegistroPV.addEventListener('submit', (event) => {
            event.preventDefault();
            formularioActivo = formPorRegistroPV;
            mostrarModalConfirmacion();
        });
    }

    if (confirmarDonacionFinal) {
        confirmarDonacionFinal.addEventListener('click', (event) => {
            event.preventDefault();
            if (formularioActivo) {
                const formData = new FormData(formularioActivo);
                enviarDatosFormulario(formData);
            }
        });
    }
});


function mostrarModalConfirmacion() {
    const confirmarDonacionModal = new bootstrap.Modal(document.getElementById('confirmarDonacionModal'));
    confirmarDonacionModal.show();
}


function enviarDatosFormulario(datos) {
    // Simulación de una respuesta exitosa del servidor
    setTimeout(() => {
        const response = { ok: true }; // Simular una respuesta con código 200

        if (response.ok) {
            const confirmarDonacionModal = bootstrap.Modal.getInstance(document.getElementById('confirmarDonacionModal'));
            confirmarDonacionModal.hide();

            const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
            modalExito.show();
        } else {
            alert('Error al registrar la donación. Intente nuevamente.');
        }
    }, 1000); // Simular un retraso en la respuesta del servidor
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