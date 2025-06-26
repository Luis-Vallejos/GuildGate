import {showModal} from './scriptsMensajes.js';
import {idFotoPerfil, idBannerPerfil, idEditarForm} from './scriptsDatos.js';

export function manejoEditar(event) {
    event.preventDefault();

    const form = idEditarForm.formEditar;
    const formData = new FormData(form);
    const urlSearchParams = new URLSearchParams(formData).toString();
    const url = `SvUsuario?${urlSearchParams}`;

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
                            location.reload();
                        }, 1000);
                        break;
                    case 400:
                        showModal('errorModal', body.error);
                        break;
                    case 401:
                        showModal('errorModal', body.error);
                        setTimeout(() => {
                            window.location.href = 'login.jsp';
                        }, 2000);
                        break;
                    case 500:
                        showModal('errorModal', body.error);
                        break;
                    default:
                        showModal('errorModal', 'Ocurrió un error inesperado.');
                        break;
            }
            }).catch(error => {
        showModal('errorModal', error.message);
    });
}

export function manejoGuardarAvatar(event) {
    event.preventDefault();

    const formPre = document.getElementById("formGuardarAvatarPre");
    const formData = new FormData(formPre);
    const url = `SvPerfil`;

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
                            location.reload();
                        }, 1500);
                        break;
                    case 400:
                        showModal('errorModal', body.error);
                        break;
                    case 401:
                        showModal('errorModal', body.error);
                        setTimeout(() => {
                            window.location.href = 'login.jsp';
                        }, 1500);
                        break;
                    case 409:
                        showModal('errorModal', body.error);
                        break;
                    default:
                        showModal('errorModal', "¡Ocurrio un error inesperado!");
                        break;
            }
            }).catch(error => {
        showModal('errorModal', error.message);
    });
}

export function manejoGuardarAvatarPersonalizado(event) {
    event.preventDefault();

    const formPer = document.getElementById("formGuardarAvatarPer");
    const formData = new FormData(formPer);
    const url = `SvPerfil`;

    fetch(url, {
        method: 'POST',
        body: formData
    })
            .then(response => response.json().then(data => ({status: response.status, body: data})))
            .then(({status, body}) => {
                switch (status) {
                    case 200:
                        showModal('successModal', body.message);
                        setTimeout(() => {
                            location.reload();
                        }, 1500);
                        break;
                    case 400:
                        showModal('errorModal', body.error);
                        break;
                    case 401:
                        showModal('errorModal', body.error);
                        setTimeout(() => {
                            window.location.href = "login.jsp";
                        }, 1500);
                        break;
                    case 500:
                        showModal('errorModal', body.error);
                        break;
                    default:
                        showModal('errorModal', "¡Ocurrio un error inesperado!");
                        break;
            }
            }).catch(error => {
        showModal('errorModal', error.message);
    });
}

export function manejoGuardarBanner(event) {
    event.preventDefault();

    const formPre = document.getElementById("formGuardarBannerPre");
    const formData = new FormData(formPre);
    const url = `SvBanner`;

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
                            location.reload();
                        }, 1500);
                        break;
                    case 400:
                        showModal('errorModal', body.error);
                        break;
                    case 401:
                        showModal('errorModal', body.error);
                        setTimeout(() => {
                            window.location.href = 'login.jsp';
                        }, 1500);
                        break;
                    case 409:
                        showModal('errorModal', body.error);
                        break;
                    default:
                        showModal('errorModal', "¡Ocurrio un error inesperado!");
                        break;
            }
            }).catch(error => {
        showModal('errorModal', error.message);
    });
}

export function manejoGuardarBannerPersonalizado(event) {
    event.preventDefault();

    const formPer = document.getElementById("formGuardarBannerPer");
    const formData = new FormData(formPer);
    const url = `SvBanner`;

    fetch(url, {
        method: 'POST',
        body: formData
    })
    .then(response => response.json().then(data => ({status: response.status, body: data})))
    .then(({status, body}) => {
        switch (status) {
            case 200:
                showModal('successModal', body.message);
                setTimeout(() => {
                    location.reload();
                }, 1500);
                break;
            case 400:
                showModal('errorModal', body.error);
                break;
            case 401:
                showModal('errorModal', body.error);
                setTimeout(() => {
                    window.location.href = "login.jsp";
                }, 1500);
                break;
            case 500:
                showModal('errorModal', body.error);
                break;
            default:
                showModal('errorModal', "¡Ocurrió un error inesperado!");
                break;
        }
    })
    .catch(error => {
        showModal('errorModal', error.message);
    });
}