import {showModal} from './scriptsMensajes.js';
import {idEditarForm} from './scriptsDatos.js';

//Función para volver visible la contraseña
export function togglePassword() {
    if (idEditarForm.btnMostrarContra && idEditarForm.inputContra) {
        idEditarForm.btnMostrarContra.addEventListener('click', function () {
            if (idEditarForm.inputContra.type === 'password') {
                idEditarForm.inputContra.type = 'text';
                idEditarForm.iconOpenEye.style.display = 'none';
                idEditarForm.iconClosedEye.style.display = 'block';
            } else {
                idEditarForm.inputContra.type = 'password';
                idEditarForm.iconOpenEye.style.display = 'block';
                idEditarForm.iconClosedEye.style.display = 'none';
            }
        });
    }
}

export function setupUsuarioValidation(elements) {
    elements.forEach(item => {
        const usuario = item.getAttribute('data-usuario');
        item.addEventListener('mouseover', () => validarUsuario(usuario));
    });
}

// Validar si el usuario está logeado antes de permitir el acceso a Gremios o Raids
export function validarUsuario(usuario) {
    if (usuario === 'null' || usuario === null) {
        showModal('warningModal', 'Primero debes iniciar sesión para acceder a esta sección.');
    }
}

export function validarBotones(botones) {
    return Object.values(botones).every(boton => boton !== null);
}

export function chibiAleatoria() {
    const chibi = document.getElementById("imgChibi");
    const numRandom = Math.floor(Math.random() * 75) + 1;
    chibi.src = `/GremiosYRaids/imagenes/ChibiCharacters/Character (${numRandom}).png`;
}

export function imagenAleatoriaRegistro() {
    const body = document.body;

    if (body.classList.contains('background-register')) {
        body.style.backgroundImage = `url('/GremiosYRaids/imagenes/loginYregistro/Register${Math.floor(Math.random() * 3) + 1}.jpg')`;
    } else {
        console.log("Error la imagen no existe!");
    }
}

// Función para poner en el background una imagen aletoria de las guardadas
export function imagenAleatoriaLogin() {
    const body = document.body;

    if (body.classList.contains('background-login')) {
        body.style.backgroundImage = `url('/GremiosYRaids/imagenes/loginYregistro/Login${Math.floor(Math.random() * 3) + 1}.jpg')`;
    } else {
        console.log("Error la imagen no existe!");
    }
}

export function fondoCambio() {
    const back = document.body;

    const imagenesEspeciales = [
        'BackgroundEspecial1',
        'BackgroundEspecial2',
        'BackgroundEspecial3'
    ];

    const probabilidadEspecial = 0.01;

    const esEspecial = Math.random() < probabilidadEspecial;

    let imagenSeleccionada;

    if (esEspecial) {
        const indiceEspecial = Math.floor(Math.random() * imagenesEspeciales.length);
        imagenSeleccionada = imagenesEspeciales[indiceEspecial];
    } else {
        const numRandom = Math.floor(Math.random() * 29) + 1;
        imagenSeleccionada = `BackgroundGremios${numRandom}`;
    }
    back.style.backgroundImage = `url(/GremiosYRaids/imagenes/Gremio/CrearGremio/${imagenSeleccionada}.jpg)`;
    back.style.backgroundSize = 'cover';
    back.style.backgroundRepeat = 'no-repeat';
    back.style.backgroundPosition = 'center center';
}
