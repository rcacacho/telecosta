function setFocusToComponent(componentId) {

    var component = document.getElementById(componentId);
    component.focus();

}
function alerta() {

    alert("HOLA MUNDO");


}


function autotab(original, destination) {

    if
            (original.value.length == original.getAttribute("maxlength"))
        destination.focus();

}