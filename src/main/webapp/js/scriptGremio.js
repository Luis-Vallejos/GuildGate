import {setupUsuarioValidation} from './scriptsReutilizables.js';
import {showModal, setupModalCloseButtons} from './scriptsMensajes.js';
import {usuario} from './scriptsDatos.js';

document.addEventListener("DOMContentLoaded", () => {
    initializeEventListeners();
    initializeForm();
    setupModalCloseButtons();
    setupUsuarioValidation(usuario.usuarioElements);
    setupOverlayClickListener();
    inicializarFormUnirse();
});

function initializeEventListeners() {
    const video = document.getElementById('backgroundVideo');
    const source = document.getElementById('srcVid');
    const buscar = document.getElementById('btn-buscar');
    const controles = document.getElementById('controles');
    const form = document.getElementById('formBGremio');
    const tabla = document.getElementById('container-tablaGremios');
    const start = document.getElementById('btn-empezar');
    const pause = document.getElementById('pS');
    const reproduce = document.getElementById('rP');
    const subVol = document.getElementById('volumeUp');
    const noVol = document.getElementById('noVolume');

    if (buscar) {
        buscar.addEventListener('click', function () {
            source.src = "/GremiosYRaids/imagenes/Gremio/VisualizarGremios/LoadingScreen.mp4";
            video.load();
            video.play();
            controles.style.display = 'none';
            form.style.transform = 'translate(-50%, -200%)';
            tabla.style.display = 'block';
        });
    }

    if (start) {
        start.addEventListener('click', function () {
            video.muted = false;
            video.play();
            start.style.display = 'none';
            controles.style.display = 'flex';
            form.style.display = 'flex';
        });
    }

    if (pause) {
        pause.addEventListener('click', function () {
            video.pause();
            reproduce.style.display = 'inline-block';
            pause.style.display = 'none';
        });
    }

    if (reproduce) {
        reproduce.addEventListener('click', function () {
            video.play();
            pause.style.display = 'inline-block';
            reproduce.style.display = 'none';
        });
    }

    if (noVol) {
        noVol.addEventListener('click', function () {
            video.muted = false;
            subVol.style.display = 'inline-block';
            noVol.style.display = 'none';
        });
    }

    if (subVol) {
        subVol.addEventListener('click', function () {
            video.muted = true;
            noVol.style.display = 'inline-block';
            subVol.style.display = 'none';
        });
    }
}

function initializeForm() {
    const formBGremio = document.getElementById('formBGremio');
    const btnBuscar = document.getElementById('btn-buscar');

    btnBuscar.addEventListener('click', (event) => handleSubmit(event, formBGremio));
}

function handleSubmit(event, formBGremio) {
    event.preventDefault();

    const formData = new FormData(formBGremio);
    const urlSearchParams = new URLSearchParams(formData).toString();

    const url = `SvGremio?${urlSearchParams}`;

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
            .then(response => handleResponse(response))
            .catch(error => showModal('errorModal', error.message));
}

function handleResponse(response) {
    switch (response.status) {
        case 200:
            return response.json().then(data => {
                handleData(data);
                showModal('successModal', 'Gremios obtenidos correctamente');
            });
        case 400:
            return response.json().then(data => {
                showModal('errorModal', data.error || 'Solicitud inválida. Verifique los datos y vuelva a intentarlo.');
            });
        case 404:
            return response.json().then(data => {
                showModal('errorModal', data.error || 'No se encontraron gremios.');
            });
        case 500:
            return response.json().then(data => {
                showModal('errorModal', data.error || 'Error del servidor. Inténtelo más tarde.');
            });
        default:
            return response.json().then(data => {
                showModal('errorModal', data.error || 'Error inesperado del servidor.');
            });
    }
}

function handleData(data) {
    const tablaGremios = document.getElementById('tablaGremios');
    const containerTablaGremios = document.getElementById('container-tablaGremios');

    if (data.error) {
        showModal('errorModal', data.error);
    } else {
        tablaGremios.querySelector('tbody').innerHTML = '';

        containerTablaGremios.style.display = 'block';

        data.forEach(gremio => {
            const row = document.createElement('tr');
            row.classList.add('clickable-row');

            const cellImg = document.createElement('th');
            cellImg.classList.add('backgroundGImg');
            const img = document.createElement('img');
            img.classList.add('gremioImg');
            img.src = gremio.Imagen;
            img.alt = 'Imagen del gremio';
            cellImg.appendChild(img);

            const cellNombre = document.createElement('td');
            cellNombre.textContent = gremio.nombre;

            row.appendChild(cellImg);
            row.appendChild(cellNombre);

            row.addEventListener('click', () => {
                handleRowClick(gremio.id);
                passGuildId(gremio.id);
            });

            tablaGremios.querySelector('tbody').appendChild(row);
        });
    }
}

function handleRowClick(gremioId) {
    fetch(`SvDatosGremio?id=${gremioId}`, {
        method: 'GET'
    })
            .then(response => response.json())
            .then(data => showGremioCard(data))
            .catch(error => showModal('errorModal', error.message));
}

function showGremioCard(gremio) {
    const overlay = document.getElementById('overlay');
    document.getElementById('imagenGremio').src = gremio.Imagen;
    document.getElementById('etiquetaGremio').textContent = gremio.nombre;
    document.getElementById('nombreGremio').textContent = gremio.nombre;
    document.getElementById('descripcionGremio').textContent = gremio.descripcion;
    document.getElementById('miembrosGremio').textContent = `(${gremio.miembros}/${gremio.limiteMiembros})`;
    document.getElementById('botonUnirse').textContent = `Unirse a ${gremio.nombre}`;

    overlay.classList.add('visible');
}

function setupOverlayClickListener() {
    const overlay = document.getElementById('overlay');
    overlay.addEventListener('click', (event) => {
        if (event.target === overlay) {
            overlay.classList.remove('visible');
        }
    });
}

function inicializarFormUnirse() {
    const formUnirse = document.getElementById('formUnirse');
    const btnUnirse = document.getElementById('botonUnirse');

    btnUnirse.addEventListener('click', (event) => unirseForm(event, formUnirse));
}

function passGuildId(gremioId) {
    const idGre = document.getElementById('gremioId');

    idGre.value = gremioId;
}

function unirseForm(event, form) {
    event.preventDefault();

    const formData = new FormData(form);
    const urlSearchParams = new URLSearchParams(formData).toString();
    const url = `SvDatosGremio?${urlSearchParams}`;

    fetch(url, {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
            .then(response => response.json().then(data => ({status: response.status, body: data})))
            .then(({status, body}) => {
                switch (status) {
                    case 200:
                        showModal('successModal', body.message || '¡Te has unido exitosamente!');
                        setTimeout(() => {
                            window.location.href = 'index.jsp';
                        }, 2500);
                        break;
                    case 400: // SC_BAD_REQUEST
                        showModal('errorModal', body.error || 'Solicitud inválida. Verifique los datos y vuelva a intentarlo.');
                        break;
                    case 409: // SC_CONFLICT
                        showModal('errorModal', body.error || 'Ha habido un problema con su sesión. ¡Ingrese nuevamente!');
                        setTimeout(() => {
                            window.location.href = 'login.jsp';
                        }, 2000);
                        break;
                    case 500: // SC_INTERNAL_SERVER_ERROR
                        showModal('errorModal', body.error || 'Ocurrió un error al unirse al gremio.');
                        break;
                    default:
                        showModal('errorModal', 'Ocurrió un error inesperado.');
                        break;
            }
            })
            .catch(error => {
                showModal('errorModal', `Ocurrió un error al intentar enviar la solicitud: ${error}`);
            });
}