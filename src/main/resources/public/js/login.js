function initializeLoginForm(){

    document.querySelector('#register-link').addEventListener('click', function(event) {
        event.preventDefault(); // Evita la redirección inmediata

        var modalElement = document.getElementById('loginModal');

        // Validación previa: Asegurarse de que el modal está en el DOM
        if (modalElement) {
            var modal = bootstrap.Modal.getInstance(modalElement); // Obtiene la instancia del modal

            if (modal) {
                modal.hide(); // Cierra el modal si la instancia existe
            } else {
                console.warn('El modal no tiene una instancia activa.');
            }

            // Redirige a la sección indicada en el href del enlace y previene el scroll hacia la parte superior
            const targetElement = document.querySelector(this.getAttribute('href'));

            if (targetElement) {
                // Espera a que el modal se cierre antes de hacer el scroll
                setTimeout(function() {
                    targetElement.scrollIntoView({ behavior: 'smooth' });
                    history.pushState(null, null, '#identify-to-colaborate-placeholder'); // Cambia la URL sin refrescar la página
                }, 300);  // Tiempo suficiente para que el modal se cierre
            } else {
                console.warn('No se encontró el objetivo en el DOM para desplazarse.');
            }
        } else {
            console.error('Modal no encontrado en el DOM.');
        }
      });
}

document.addEventListener("DOMContentLoaded", function () {
    initializeLoginForm();           // Inicializa el formulario de persona física
});