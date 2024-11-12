/* carga dinamica resulta complicado, mas con los js que se cargan de forma asincronica

function loadComponent(componentId, filePath) {
    fetch(filePath)
        .then(response => response.text())
        .then(data => {
            document.getElementById(componentId).innerHTML = data;
        })
        .catch(error => console.error('Error loading component:', error));
}

function createjscssfile(filename, filetype, fileid){
    if (filetype=="js"){ //if filename is a external JavaScript file
        var fileref=document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src", filename);
        fileref.setAttribute("id", fileid);
    }
    else if (filetype=="css"){ //if filename is an external CSS file
        var fileref=document.createElement('link');
        fileref.setAttribute("rel", "stylesheet");
        fileref.setAttribute("type", "text/css");
        fileref.setAttribute("href", filename);
        fileref.setAttribute("id", fileid);
    }

    return fileref;
}
function createcss(filepath){
    let fileref=document.createElement('link');
    fileref.setAttribute("rel", "stylesheet");
    fileref.setAttribute("type", "text/css");
    fileref.setAttribute("href", filepath);
    fileref.setAttribute("id", fileid);
}
function createjs(filepath){
    let fileref=document.createElement('script');
    fileref.setAttribute("type","text/javascript");
    fileref.setAttribute("src", filepath);
    fileref.setAttribute("id", fileid);
}

function loadtohead(element){
    if (element!=null)
        document.head.appendChild(element)
}
function loadtobody(element){
    if (element!=null)
        document.body.appendChild(element)
}

function loadjscss(filename, filetype, fileid){
    var element = document.getElementById(fileid);
    var replacement = createjscssfile(filename, filetype, fileid);
    if (element!=null){
        element.parentNode.replaceChild(replacement, element);
    } else {
        console.log("el id <"+fileid+"> no existe. creando...");
        if (filetype == 'css'){
            loadtohead(replacement);
        } else if (filetype == 'js'){
            loadtobody(replacement);
        }
    }
}
*/

function ocultar_elemento(elem){
    if (elem!=null){
        elem.classList.add('d-none');
    } else {
        console.error('No se encontro el elemento');
    }
}
function mostrar_elemento(elem){
    if (elem!=null){
        elem.classList.remove('d-none');
    } else {
        console.error('No se encontro el elemento');
    }
}

function mostrar_elemento_id(id){
    mostrar_elemento(document.getElementById(id));
    
}
function ocultar_elemento_id(id){
    ocultar_elemento(document.getElementById(id));
}