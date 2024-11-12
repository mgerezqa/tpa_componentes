document.getElementById('btn_editar_form_fisico').onclick = editar_form_fisico;
document.getElementById('btn_cancelar_form_fisico').onclick = cancelar_edicion_fisico;
document.getElementById('btn_guardar_form_fisico').onclick = guardar_edicion_fisico;


function editar_form_fisico(){
    ocultar_elemento_id('cont_editar_form_fisico');
    mostrar_elemento_id('cont_cancelar_guardar_form_fisico');
    habilitar_edicion('form_fisico', true);
}
function cancelar_edicion_fisico(){
    // TODO
    mostrar_elemento_id('cont_editar_form_fisico');
    ocultar_elemento_id('cont_cancelar_guardar_form_fisico');
    habilitar_edicion('form_fisico', false);
}
function guardar_edicion_fisico(){
    // TODO: posible post
    mostrar_elemento_id('cont_editar_form_fisico');
    ocultar_elemento_id('cont_cancelar_guardar_form_fisico');
    habilitar_edicion('form_fisico', false);
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