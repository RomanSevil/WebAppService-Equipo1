// document.addEventListener('DOMContentLoaded', () => {
//     const sugerencias = /*[[${suggestions}]]*/[] 
// })

console.log("filtroIndex.js loaded");
// const sugerencias = /*[[${suggestions}]]*/ [];
function mostrarResultados(query) {
    const cajaResultados = document.getElementById("sugerencias")
    cajaResultados.innerHTML = ''

    if (query.length < 2) {
        return
    }


    sugerencias.forEach(sugerencia => {
        const nombre = sugerencia.nombre
        const apellido = sugerencia.apellido
        const profesion = sugerencia.profesion
        const proveedorId = parseInt(sugerencia.id)

        if (nombre.toLowerCase().includes(query.toLowerCase()) || apellido.toLowerCase().includes(query.toLowerCase()) || profesion.toLowerCase().includes(query.toLowerCase())) {
            const sugerenciaItem = document.createElement('div');
            sugerenciaItem.className = 'suggestion-item';
            // sugerenciaItem.textContent = sugerencia;
            const textoSugerencia = `${nombre} ${apellido} (${profesion})`
            sugerenciaItem.innerHTML = `<a href="#" onclick="seleccionarSugerencia('/proveedor/${proveedorId}')">${textoSugerencia}</a>`
            
            // sugerenciaItem.onclick = () => {
            //     document.getElementById('search-input').value = textoSugerencia;
            //     cajaResultados.innerHTML = ''; 
            // };
            cajaResultados.appendChild(sugerenciaItem);
        }
    })
}

function seleccionarSugerencia(url) {
    limpiarBusqueda();
    setTimeout(() => {
        window.location.href = url;
    }, 100);
}

function limpiarBusqueda() {
    document.getElementById('search-input').value = '';
    document.getElementById('sugerencias').innerHTML = '';
}


function buscarProveedores() {
    const query = document.getElementById('search-input').value;
    if (query) {
        // Perform the search based on the query (e.g., filter the table)
        console.log('Buscar por:', query);

        // Clear the suggestions
        document.getElementById('sugerencias').innerHTML = '';
    }
}

document.addEventListener("DOMContentLoaded", function() {
    limpiarBusqueda();
});