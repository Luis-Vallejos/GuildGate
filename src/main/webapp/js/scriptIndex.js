import {setupUsuarioValidation} from './scriptsReutilizables.js';
import {setupModalCloseButtons} from './scriptsMensajes.js';
import {usuario} from './scriptsDatos.js';

document.addEventListener("DOMContentLoaded", function () {
    initCarousel();
    initializeModals();
    obtenerTitulos();
    setupUsuarioValidation(usuario.usuarioElements);
    setupModalCloseButtons();
});

// Función principal para inicializar el carrusel
function initCarousel() {
    const currentIndex = {value: 0}; // Usamos un objeto para mantener el estado
    const slides = document.querySelectorAll('.carousel-slide');
    const indicators = document.querySelectorAll('.carousel-indicator');
    const prevButton = document.getElementById('prev');
    const nextButton = document.getElementById('next');
    const prevImage = document.querySelector('.carousel-control-left .Mayreel');
    const nextImage = document.querySelector('.carousel-control-right .Mayreel');

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.style.display = i === index ? 'block' : 'none';
        });
        indicators.forEach((indicator, i) => {
            indicator.classList.toggle('active', i === index);
        });
    }

    function prevSlide() {
        currentIndex.value = (currentIndex.value > 0) ? currentIndex.value - 1 : slides.length - 1;
        showSlide(currentIndex.value);
    }

    function nextSlide() {
        currentIndex.value = (currentIndex.value < slides.length - 1) ? currentIndex.value + 1 : 0;
        showSlide(currentIndex.value);
    }

    function goToSlide(index) {
        currentIndex.value = index;
        showSlide(currentIndex.value);
    }

    function handleMouseOver(image) {
        image.style.opacity = 1;
    }

    function handleMouseOut(image) {
        image.style.opacity = 0;
    }

    function addEventListeners() {
        prevButton.addEventListener('click', prevSlide);
        nextButton.addEventListener('click', nextSlide);

        indicators.forEach((indicator, index) => {
            indicator.addEventListener('click', () => goToSlide(index));
        });

        prevButton.addEventListener('mouseover', () => handleMouseOver(prevImage));
        prevButton.addEventListener('mouseout', () => handleMouseOut(prevImage));
        nextButton.addEventListener('mouseover', () => handleMouseOver(nextImage));
        nextButton.addEventListener('mouseout', () => handleMouseOut(nextImage));
    }

    // Inicializa el carrusel
    showSlide(currentIndex.value);
    addEventListeners();
}

// Función para inicializar los modales
function initializeModals() {
    var modal = document.getElementById("gt-Memorias-Modal");

    var imgs = document.getElementsByClassName("imgCaballera");

    var span = document.getElementsByClassName("close")[0];

    for (let i = 0; i < imgs.length; i++) {
        imgs[i].onclick = function () {
            modal.style.display = "block";
        };
    }

    span.onclick = function () {
        modal.style.display = "none";
    };

    window.onclick = function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
}

function obtenerTitulos() {
    fetch('/GremiosYRaids/json/modalIndex.json')
            .then(response => response.json())
            .then(data => {
                const memorias = data.memorias;

                memorias.forEach(memoria => {
                    const article = document.querySelector(`article[data-product="${memoria.id}"]`);
                    if (article) {
                        const tituloElement = article.querySelector(".titulo-memoria");
                        tituloElement.textContent = memoria.titulo;
                    }
                });
            })
            .catch(error => console.error('Error al cargar el JSON:', error));
}