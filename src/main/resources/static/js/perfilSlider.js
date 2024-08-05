$(document).ready(function(){
    $('.slider').slick({
        infinite: false,
        slidesToShow: 3,
        slidesToScroll: 1,
        dots: true,
        arrows: true,
        autoplay: true,
        autoplaySpeed: 3000,
        vertical: false,
        responsive: [
            {
                breakpoint: 1024, // A partir de 1024px hacia abajo
                settings: {
                    slidesToShow: 2,
                    slidesToScroll: 1
                }
            },
            {
                breakpoint: 768, // A partir de 768px hacia abajo
                settings: {
                    slidesToShow: 1,
                    slidesToScroll: 1
                }
            }
        ]
    });
});