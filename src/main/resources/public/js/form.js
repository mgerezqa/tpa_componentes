
// Función para inicializar el formulario de persona física
function inicializarFormularioPersona() {
    const formulario = document.getElementById("formularioPersona");

    if (!formulario) return;  // Asegurarse de que el formulario exista

    // Botón reset para limpiar el formulario
    formulario.querySelector("#resetBtn").addEventListener("click", function () {
        formulario.reset();  // Reiniciar el formulario actual
        formulario.querySelector("#whatsappContainer").style.display = 'none';
        formulario.querySelector("#telegramContainer").style.display = 'none';
        formulario.querySelector("#emailContainer").style.display = 'none';
    });

    // Mostrar/ocultar contenedores según el checkbox seleccionado
    formulario.querySelector("#whatsapp").addEventListener("change", function () {
        const whatsappContainer = formulario.querySelector("#whatsappContainer");
        whatsappContainer.style.display = this.checked ? 'block' : 'none';
    });

    formulario.querySelector("#telegram").addEventListener("change", function () {
        const telegramContainer = formulario.querySelector("#telegramContainer");
        telegramContainer.style.display = this.checked ? 'block' : 'none';
    });

    formulario.querySelector("#email").addEventListener("change", function () {
        const emailContainer = formulario.querySelector("#emailContainer");
        emailContainer.style.display = this.checked ? 'block' : 'none';
    });

    // Autocompletar email de registro si se selecciona el checkbox
    formulario.querySelector("#registrarConMail").addEventListener("change", function () {
        const emailContacto = formulario.querySelector("#emailInput").value;
        const emailUsuario = formulario.querySelector("#emailUsuario");
        if (this.checked && emailContacto) {
            emailUsuario.value = emailContacto;
            emailUsuario.setAttribute('readonly', true);
        } else {
            emailUsuario.value = "";
            emailUsuario.removeAttribute('readonly');
        }
    });

    // Validar contraseñas al enviar el formulario
    formulario.addEventListener("submit", function (event) {
        const password = formulario.querySelector("#password").value;
        const repeatPassword = formulario.querySelector("#repeatPassword").value;

        if (password !== repeatPassword) {
            alert("Las contraseñas no coinciden");
            event.preventDefault(); // Evitar que el formulario se envíe
        }
    });
}

// Función para inicializar el formulario de persona jurídica
function inicializarFormularioPersonaJuridica() {
    const formulario = document.getElementById("formularioPersonaJuridica");

    if (!formulario) return;  // Asegurarse de que el formulario exista

    // Botón reset para limpiar el formulario
    formulario.querySelector("#resetBtn").addEventListener("click", function () {
        formulario.reset();  // Reiniciar el formulario actual
        formulario.querySelector("#whatsappContainer").style.display = 'none';
        formulario.querySelector("#telegramContainer").style.display = 'none';
        formulario.querySelector("#emailContainer").style.display = 'none';
    });

    // Mostrar/ocultar contenedores según el checkbox seleccionado
    formulario.querySelector("#whatsapp").addEventListener("change", function () {
        const whatsappContainer = formulario.querySelector("#whatsappContainer");
        whatsappContainer.style.display = this.checked ? 'block' : 'none';
    });

    formulario.querySelector("#telegram").addEventListener("change", function () {
        const telegramContainer = formulario.querySelector("#telegramContainer");
        telegramContainer.style.display = this.checked ? 'block' : 'none';
    });

    formulario.querySelector("#email").addEventListener("change", function () {
        const emailContainer = formulario.querySelector("#emailContainer");
        emailContainer.style.display = this.checked ? 'block' : 'none';
    });

    // Autocompletar email de registro si se selecciona el checkbox
    formulario.querySelector("#registrarConMail").addEventListener("change", function () {
        const emailContacto = formulario.querySelector("#emailInput").value;
        const emailUsuario = formulario.querySelector("#emailUsuario");
        if (this.checked && emailContacto) {
            emailUsuario.value = emailContacto;
            emailUsuario.setAttribute('readonly', true);
        } else {
            emailUsuario.value = "";
            emailUsuario.removeAttribute('readonly');
        }
    });

    // Validar contraseñas al enviar el formulario
    formulario.addEventListener("submit", function (event) {
        const password = formulario.querySelector("#password").value;
        const repeatPassword = formulario.querySelector("#repeatPassword").value;

        if (password !== repeatPassword) {
            alert("Las contraseñas no coinciden");
            event.preventDefault(); // Evitar que el formulario se envíe
        }
    });
}

// Inicializar interacciones para ambos formularios al cargar la página o abrir el modal
document.addEventListener("DOMContentLoaded", function () {
    inicializarFormularioPersona();           // Inicializa el formulario de persona física
    inicializarFormularioPersonaJuridica();   // Inicializa el formulario de persona jurídica
});