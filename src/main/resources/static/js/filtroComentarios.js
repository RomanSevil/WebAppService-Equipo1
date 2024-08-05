var filtroInput = document.getElementById("filtro");
var tablaBody = document.getElementById("tabla-body").getElementsByTagName("tr");

filtroInput.addEventListener("input", function() {
    var filtro = filtroInput.value.toLowerCase();

    for (var i = 0; i < tablaBody.length; i++) {
        var fila = tablaBody[i];
        var celdas = fila.querySelectorAll("td:nth-child(2), td:nth-child(5)"); // Filtrar por las columnas 2 y 5
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
        let columnaComentario= fila.querySelector(".columna:nth-child(2) .contenido");
        let columnaNombreUsuario= fila.querySelector(".columna:nth-child(5) .contenido");
        let mostrarFila = false;        
           
            if (columnaComentario.innerText.toLowerCase().includes(filtro) || columnaNombreUsuario.innerText.toLowerCase().includes(filtro)) {
                mostrarFila = true;
            }
        

        if (mostrarFila) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    }
});