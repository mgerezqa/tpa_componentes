document.addEventListener('DOMContentLoaded', () => {
    const confirmarModal = document.getElementById('confirmarModal');
    const formFisico = document.getElementById('formularioPersona');
    const formJuridico = document.getElementById('formularioPersonaJuridica');
    let formularioActivo = null;

    // Manejar el envío del formulario de registro fisico
    if (formFisico) {
        formFisico.addEventListener('submit', (event) => {
            event.preventDefault();
            formularioActivo = formFisico;
            mostrarModalConfirmacion();
        });
    }
    // Manejar el envío del formulario de registro juridico
        if (formJuridico) {
            formJuridico.addEventListener('submit', (event) => {
                event.preventDefault();
                formularioActivo = formJuridico;
                mostrarModalConfirmacion();
            });
        }


    if (confirmarModal) {
        confirmarModal.addEventListener('click', (event) => {
            event.preventDefault();
            if (formularioActivo) {
                const formData = new FormData(formularioActivo);
                enviarDatosFormulario(formData,formularioActivo.action);
            }
        });
    }
});


function mostrarModalConfirmacion() {
    const confirmarModal = new bootstrap.Modal(document.getElementById('confirmarModal'));
    confirmarModal.show();
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
            const confirmarCanjeModal = bootstrap.Modal.getInstance(document.getElementById('confirmarModal'));
            confirmarCanjeModal.hide();

            // Mostrar el modal de éxito
            const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
            modalExito.show();

            // Opcional: redirigir después de mostrar el modal
            modalExito._element.addEventListener('hidden.bs.modal', function () {
                window.location.href = '/';
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