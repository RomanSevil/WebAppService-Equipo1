// Obtener la fecha actual
let fechaActual = new Date();
let fechas = document.querySelectorAll(".fechas")
let botones = document.querySelectorAll(".botone")

// Formatear la fecha como "AAAA-MM-DD" para que coincida con el formato ISO DATE esperado por Spring
let fechaFormateada = fechaActual.toISOString().split('T')[0];
//console.log(fechaFormateada)

// Asignar la fecha formateada al campo oculto de fechaCulminacion



fechas.forEach(fecha => {
    fecha.value=fechaFormateada
    // document.querySelector('#fechaCulminacion').value = fechaFormateada;
    // console.log(document.querySelector('#fechaCulminacion').value)
});