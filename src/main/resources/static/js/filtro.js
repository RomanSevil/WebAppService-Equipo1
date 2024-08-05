var filtroInput = document.getElementById("filtro");
var tablaBody = document.getElementById("tabla-body").getElementsByTagName("tr");

filtroInput.addEventListener("input", function() {
    var filtro = filtroInput.value.toLowerCase();

    for (var i = 0; i < tablaBody.length; i++) {
        var fila = tablaBody[i];
        var celdas = fila.getElementsByTagName("td");
        var mostrarFila = false;

        for (var j = 0; j < celdas.length; j++) {
            var textoCelda = celdas[j].innerText.toLowerCase();
            if (textoCelda.includes(filtro)) {
                mostrarFila = true;
                break;
            }
        }

        if (mostrarFila) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    }

    let mobileFilas=document.querySelectorAll(".tabla-mobile .fila");
    
    for(let i=0;i < mobileFilas.length;i++){
        let fila=mobileFilas[i];
        let columns= fila.querySelectorAll(".contenido");
        let mostrarFila = false;

        for(let j=0; j< columns.length;j++){
            let textoCelda=columns[j].innerText.toLowerCase();
            if (textoCelda.includes(filtro)) {
                mostrarFila = true;
                break;
            }
        }

        if (mostrarFila) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    }
});
