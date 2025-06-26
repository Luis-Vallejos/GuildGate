import {showModal, setupModalCloseButtons} from './scriptsMensajes.js';
import {imagenAleatoriaRegistro, chibiAleatoria} from './scriptsReutilizables.js';

document.addEventListener('DOMContentLoaded', function () {
    imagenAleatoriaRegistro();
    chibiAleatoria();
    inicializarFormulario();
    setupModalCloseButtons();
});

function inicializarFormulario() {
    const form = document.getElementById('registroForm');
    const btnRegistro = document.getElementById('btn-registro');

    btnRegistro.addEventListener('click', (event) => enviarFormulario(event, form));
}

function enviarFormulario(event, form) {
    event.preventDefault();

    const formData = new FormData(form);
    const urlSearchParams = new URLSearchParams(formData).toString();
    const url = `SvRegister?${urlSearchParams}`;

    fetch(url, {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
            .then(response => response.json().then(data => ({status: response.status, body: data})))
            .then(({status, body}) => {
                switch (status) {
                    case 200:
                        showModal('successModal', body.message);
                        setTimeout(() => {
                            window.location.href = 'login.jsp';
                        }, 2500);
                        break;
                    case 400:
                        showModal('errorModal', body.error || "Los campos están vacíos");
                        break;
                    case 409:
                        showModal('errorModal', body.error || "Nombre de usuario o correo electrónico ya están en uso");
                        break;
                    case 500:
                        showModal('errorModal', "Error interno del servidor");
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