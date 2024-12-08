
// Script de seleccion de opciones
// Variables para seleccionar botones y tooltips
const personaBtn = document.getElementById("personaBtn");
const personaJuridicaBtn = document.getElementById("personaJuridicaBtn");
const continuarBtn = document.getElementById("continuarBtn");
const personaTooltip = document.getElementById("personaTooltip");
const juridicaTooltip = document.getElementById("juridicaTooltip");

const formularioPersona = document.getElementById("formularioPersona");
const formularioJuridica = document.getElementById("formularioPersonaJuridica");

let selectedType = '';

// Función para restablecer la selección
function resetSelection() {
    personaBtn.classList.remove("selected");
    personaJuridicaBtn.classList.remove("selected");
    personaTooltip.style.display = 'none';
    juridicaTooltip.style.display = 'none';
    continuarBtn.disabled = true;
    selectedType = '';
}

// Funciones para los botones de selección
personaBtn.addEventListener("click", function (event) {
    event.stopPropagation();
    resetSelection();
    personaBtn.classList.add("selected");
    personaTooltip.style.display = 'block';
    selectedType = 'persona';
    continuarBtn.disabled = false;
});

personaJuridicaBtn.addEventListener("click", function (event) {
    event.stopPropagation();
    resetSelection();
    personaJuridicaBtn.classList.add("selected");
    juridicaTooltip.style.display = 'block';
    selectedType = 'juridica';
    continuarBtn.disabled = false;
});

// Evento para abrir el modal con el formulario correspondiente
continuarBtn.addEventListener("click", function () {
    let modalTitle = document.getElementById("formularioModalLabel");

    // Ocultar ambos formularios antes de mostrar el seleccionado
    formularioPersona.style.display = 'none';
    formularioJuridica.style.display = 'none';

    // Mostrar el formulario según la selección
    if (selectedType === 'persona') {
        modalTitle.innerText = "Registro para colaborador físico";
        formularioPersona.style.display = 'block';
    } else if (selectedType === 'juridica') {
        modalTitle.innerText = "Registro para Persona Jurídica";
        formularioJuridica.style.display = 'block';
    }

    // Abrir el modal
    let formularioModal = new bootstrap.Modal(document.getElementById("formularioModal"));
    formularioModal.show();
});

// Desmarcar opciones si se hace clic fuera de los botones
document.addEventListener("click", function (event) {
    if (!personaBtn.contains(event.target) && !personaJuridicaBtn.contains(event.target)) {
        resetSelection();
    }
});