import {chibiAleatoria, fondoCambio} from './scriptsReutilizables.js';
import {showModal, setupModalCloseButtons} from './scriptsMensajes.js';

document.addEventListener('DOMContentLoaded', function () {
    fondoCambio();
    chibiAleatoria();
    inicializarFormulario();
    setupModalCloseButtons();
});

function inicializarFormulario() {
    const form = document.getElementById('formGremio');
    const btnCrear = document.getElementById('btn-gremio');

    btnCrear.addEventListener('click', (event) => enviarFormulario(event, form));
}

// Función para enviar los datos del formulario al Servlet SvGremio con método POST
function enviarFormulario(event, form) {
    event.preventDefault();

    const formData = new FormData(form);
    const url = `SvGremio`;

    fetch(url, {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
            .then(response => response.json().then(data => ({status: response.status, body: data})))
            .then(({status, body}) => {
                switch (status) {
                    case 201: //SC_CREATED
                        showModal('successModal', body.message);
                        setTimeout(() => {
                            window.location.href = 'index.jsp';
                        }, 2500);
                        break;
                    case 400: //SC_BAC_REQUEST
                        showModal('errorModal', body.error);
                        break;
                    case 401: //SC_UNAUTHORIZED
                        showModal('errorModal', body.error);
                        setTimeout(() => {
                            window.location.href = 'login.jsp';
                        }, 2500);
                        break;
                    case 404: //SC_NOT_FOUND
                        showModal('errorModal', body.error);
                        break;
                    case 409: //SC_CONFLICT
                        showModal('errorModal', body.error);
                        break;
                    case 500: //SC_INTERNAL_SERVER_ERROR
                        showModal('errorModal', body.error);
                        break;
                    default:
                        showModal('errorModal', 'Ocurrió un error inesperado.');
                        break;
            }
            })
            .catch(error => {
                showModal('errorModal', `Ocurrio un error al intentar enviar la solicitud : ${error}`);
            });
}