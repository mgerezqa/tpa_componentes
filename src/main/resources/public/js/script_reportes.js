document.getElementById('applyFilter').addEventListener('click', function () {
    const selectedDescriptions = Array.from(document.querySelectorAll('input[name="descripcion"]:checked')).map(checkbox => checkbox.value);
    const selectedDate = document.getElementById('fecha').value;

    document.querySelectorAll('.fila_contribucion').forEach(card => {
        const cardDescription = card.getAttribute('data-descripcion');
        const cardDate = card.getAttribute('data-fecha');

        const matchesDescription = selectedDescriptions.length === 0 || selectedDescriptions.includes(cardDescription);
        const matchesDate = !selectedDate || cardDate === selectedDate;

        if (matchesDescription && matchesDate) {
            card.style.display = '';
        } else {
            card.style.display = 'none';
        }
    });
});