import {showModal, setupModalCloseButtons} from './scriptsMensajes.js';
import {imagenAleatoriaLogin, chibiAleatoria} from './scriptsReutilizables.js';

document.addEventListener('DOMContentLoaded', function () {
    imagenAleatoriaLogin();
    chibiAleatoria();
    inicializarFormulario();
    setupModalCloseButtons();
});

function inicializarFormulario() {
    const form = document.getElementById("loginForm");
    const btnLogearse = document.getElementById("btn-login");

    btnLogearse.addEventListener('click', (event) => manejarEnvio(event, form));
}

function manejarEnvio(event, form) {
    event.preventDefault();

    const formData = new FormData(form);
    const url = 'SvLogin';

    fetch(url, {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
            .then(response => response.json().then(data => ({status: response.status, body: data})))
            .then(({ status, body }) => {
                switch (status) {
                    case 200:
                        showModal('successModal', body.message);
                        setTimeout(() => {
                            window.location.href = 'index.jsp';
                        }, 2500);
                        break;
                    case 401:
                        showModal('errorModal', body.error);
                        break;
                    case 404:
                        showModal('warningModal', body.error);
                        break;
                    case 400:
                        showModal('warningModal', body.error);
                        break;
                    default:
                        showModal('errorModal', "Ocurrió un error desconocido");
                        break;
            }
            })
            .catch(error => {
                showModal('errorModal', "Ocurrió un error al conectar con el servidor: " + error.message);
            });
}
