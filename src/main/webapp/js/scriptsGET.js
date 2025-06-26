import {idBannerPerfil, idEditarForm} from './scriptsDatos.js';

export function obtencionBanners(event) {
    event.preventDefault();

    const galeriaBPred = idBannerPerfil.galeriaBanner;
    galeriaBPred.innerHTML = '';

    const form = document.getElementById("formBanners");
    const formData = new FormData(form);
    const urlSearchParams = new URLSearchParams(formData).toString();

    const url = `SvBanner?${urlSearchParams}`;

    fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(bannerBase64 => {
                bannerBase64.forEach(bannerData => {
                    const button = document.createElement("button");
                    button.type = "button";

                    const img = document.createElement("img");
                    img.src = 'data:image/png;base64,' + bannerData.bannerBase64;
                    img.alt = "Banner";
                    img.title = bannerData.bannerName;

                    button.appendChild(img);
                    galeriaBPred.appendChild(button);
                });
                cambiarBannerPreviewPredeterminado();
            })
            .catch(error => showModal('errorModal', error.message));
}

function cambiarBannerPreviewPredeterminado() {
    const galeriaBotones = document.querySelectorAll("#galeria-banners button");
    const imgBanner = document.getElementById("bannerPreviewPred");
    const inputBannerFilename = document.getElementById("bannerFilename");
    
    galeriaBotones.forEach(button => {
       button.addEventListener('click', () => {
          const bannerElement = button.querySelector('img');
          const nuevaSrc = bannerElement.src;
          const nuevoTitle = bannerElement.title;
          imgBanner.src = nuevaSrc;
          imgBanner.title = nuevoTitle;
          inputBannerFilename.value = nuevoTitle;
       });
    });
}

export function obtencionPfps(event) {
    event.preventDefault();

    const galeriaPred = document.getElementById("galeria-fotos");
    galeriaPred.innerHTML = '';

    const form = document.getElementById("formPfps");
    const formData = new FormData(form);
    const urlSearchParams = new URLSearchParams(formData).toString();

    const url = `SvPerfil?${urlSearchParams}`;

    fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(pfpsBase64 => {
                pfpsBase64.forEach(imageData => {
                    const button = document.createElement("button");
                    button.type = "button";

                    const img = document.createElement("img");
                    img.src = 'data:image/png;base64,' + imageData.base64;
                    img.alt = "Foto de Perfil";
                    img.width = 100;
                    img.height = 100;
                    img.title = imageData.filename;

                    button.appendChild(img);
                    galeriaPred.appendChild(button);
                });
                cambiarAvatarPreviewPredeterminado();
            })
            .catch(error => showModal('errorModal', error.message));
}

function cambiarAvatarPreviewPredeterminado() {
    const galeriaBotones = document.querySelectorAll("#galeria-fotos button");
    const imgPerfil = document.getElementById("imgPreviewPred");
    const inputAvatarFilename = document.getElementById("avatarFilename");

    galeriaBotones.forEach(button => {
        button.addEventListener('click', () => {
            const imgElement = button.querySelector('img');
            const nuevaSrc = imgElement.src;
            const nuevoTitle = imgElement.title;
            imgPerfil.src = nuevaSrc;
            imgPerfil.title = nuevoTitle;
            inputAvatarFilename.value = nuevoTitle;
        });
    });
}

export function obtencionDatosUsuario(event) {
    event.preventDefault();

    const form = document.getElementById("formDatosUsuario");
    const formData = new FormData(form);
    const urlSearchParams = new URLSearchParams(formData).toString();
    const url = `SvUsuario?${urlSearchParams}`;

    fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(datosUsuario => {
                idEditarForm.inputNombre.value = datosUsuario.nombre;
                idEditarForm.inputCorre.value = datosUsuario.correo;
                idEditarForm.inputContra.value = datosUsuario.contra;
                idEditarForm.textAreaBio.value = datosUsuario.bio;
            })
            .catch(error => showModal('errorModal', error.message));
}