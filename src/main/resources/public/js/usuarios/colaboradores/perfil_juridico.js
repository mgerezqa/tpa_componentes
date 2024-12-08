document.getElementById('btn_editar_form_juridico').onclick = editar_form_juridico;
document.getElementById('btn_cancelar_form_juridico').onclick = cancelar_edicion_juridico;
document.getElementById('btn_guardar_form_juridico').onclick = guardar_edicion_juridico;


function editar_form_juridico(){
    ocultar_elemento_id('cont_editar_form_juridico');
    mostrar_elemento_id('cont_cancelar_guardar_form_juridico');
    habilitar_edicion('form_juridico', true);
}
function cancelar_edicion_juridico(){
    
    mostrar_elemento_id('cont_editar_form_juridico');
    ocultar_elemento_id('cont_cancelar_guardar_form_juridico');
    habilitar_edicion('form_juridico', false);
}
function guardar_edicion_juridico(){
    // TODO: posible post
    mostrar_elemento_id('cont_editar_form_juridico');
    ocultar_elemento_id('cont_cancelar_guardar_form_juridico');
    habilitar_edicion('form_juridico', false);
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