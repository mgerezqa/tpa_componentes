document.getElementById('btn_editar_form_tecnico').onclick = editar_form_tecnico;
document.getElementById('btn_cancelar_form_tecnico').onclick = cancelar_edicion_tecnico;


function editar_form_tecnico(){
    ocultar_elemento_id('cont_editar_form_tecnico');
    mostrar_elemento_id('cont_cancelar_guardar_form_tecnico');
    habilitar_edicion('form_tecnico', true);
}
function cancelar_edicion_tecnico(){
    // TODO
    mostrar_elemento_id('cont_editar_form_tecnico');
    ocultar_elemento_id('cont_cancelar_guardar_form_tecnico');
    habilitar_edicion('form_tecnico', false);
}
function habilitar_edicion(id_form, booleano){
    let inputs = document.getElementById(id_form).getElementsByClassName('entrada_form');
    if(booleano) {
        for(let i = 0; i < inputs.length; i++){
            inputs[i].removeAttribute('disabled');
        }
    } else {
        for(let i = 0; i < inputs.length; i++){
            inputs[i].setAttribute('disabled','');
        }
    }
}