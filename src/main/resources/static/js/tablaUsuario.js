// document.getElementById("openModal").addEventListener("click", function() {
//     document.getElementById("myModal").style.display = "block";
//   });

//   document.getElementsByClassName("close")[0].addEventListener("click", function() {
//     document.getElementById("myModal").style.display = "none";
//   });

// Obtener el modal y el botón de cerrar
//var modal = document.getElementById("myModal");
//var closeBtn = document.getElementsByClassName("close")[0];

// Abrir el modal al hacer clic en el botón
//document.getElementById("openModal").addEventListener("click", function() {
//   modal.style.display = "block";
// });

// // Cerrar el modal al hacer clic en la X o fuera del modal
// closeBtn.addEventListener("click", function() {
//   modal.style.display = "none";
// });

// window.addEventListener("click", function(event) {
//   if (event.target == modal) {
//     modal.style.display = "none";
//   }
// });

let modal = document.getElementById("myModal");
let responsiveModal =document.getElementById("myModalMobile")
let closeBtn = document.getElementsByClassName("close")[0];
let closeResponsiveBtn = document.querySelector("#myModalMobile .close");
let openModalBtns = document.querySelectorAll(".openModalBtn");


// Obtener el botón para abrir el modal
//let abrirModalBtn = document.getElementById('abrirModalBtn');

// Agregar un event listener para el evento click
openModalBtns.forEach(function (boton) {
    boton.addEventListener('click', function () {

        let contratoId = this.getAttribute('data-id');
        console.log("ID del contrato:", contratoId);
        document.getElementById('contratoId').value = contratoId;
        document.getElementById('contratoIdMobile').value = contratoId;

        if (window.innerWidth < 700) {
            responsiveModal.style.display = 'block';
        } else {
            modal.style.display = 'block';
        }

    });
})


// openModalBtns.forEach(function (btn) {
//     btn.addEventListener("click", function () {
//         modal.style.display = "block";
//     });
// });

// Cerrar el modal al hacer clic en la X o fuera del modal
closeBtn.addEventListener("click", function () {
    modal.style.display = "none";
});

closeResponsiveBtn.addEventListener("click", function () {
    responsiveModal.style.display = "none";
});

window.addEventListener("click", function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }else if(event.target == responsiveModal){
        responsiveModal.style.display = "none";
    }
});

// Obtener la fecha actual
let fechaActual = new Date();

// Formatear la fecha como "AAAA-MM-DD" para que coincida con el formato ISO DATE esperado por Spring
let fechaFormateada = fechaActual.toISOString().split('T')[0];

// Asignar la fecha formateada al campo oculto de fechaCulminacion
document.getElementById('fechaCulminacion').value = fechaFormateada;
document.getElementById('fechaCulminacionMobile').value = fechaFormateada;


calificacionInputs = document.querySelectorAll(".calificacionInput")
calificacionInputsMobile = document.querySelectorAll(".calificacionInputMobile")

calificacionInputs.forEach(input => {
    input.addEventListener('click', function(e) {       
        const calificacionSeleccionada=e.target.value
        document.getElementById('calificacionSeleccionada').value = calificacionSeleccionada;
    });
});

calificacionInputsMobile.forEach(input => {
    input.addEventListener('click', function(e) {       
        const calificacionSeleccionada=e.target.value
        document.getElementById('calificacionSeleccionadaMobile').value = calificacionSeleccionada;
    });
});


/*

let calificacion1= document.getElementById('star-1')
let calificacion2= document.getElementById('star-2')
let calificacion3= document.getElementById('star-3')
let calificacion4= document.getElementById('star-4')
let calificacion5= document.getElementById('star-5')*/