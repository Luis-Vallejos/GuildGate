import {togglePassword, validarBotones, setupUsuarioValidation} from './scriptsReutilizables.js';
import {showModal, setupModalCloseButtons} from './scriptsMensajes.js';
import {idFotoPerfil, idBannerPerfil, idEditarForm, usuario} from './scriptsDatos.js?v=2';
import {obtencionBanners, obtencionPfps, obtencionDatosUsuario} from './scriptsGET.js?v=1';
import {manejoEditar, manejoGuardarAvatar, manejoGuardarAvatarPersonalizado, manejoGuardarBanner, manejoGuardarBannerPersonalizado} from './scriptsPOST.js?v=5';

document.addEventListener("DOMContentLoaded", () => {
    configurarModalPfp();
    configurarCambioFotoPerfil();

    configurarModalBanner();
    configurarCambioDeBanner();

    configurarModalEditar();
    togglePassword();

    setupUsuarioValidation(usuario.usuarioElements);
    setupModalCloseButtons();
});

let pfpOriginal, bannerOriginal;

//Función que configura al modal y llama al que activara el modal y el  overlay
function configurarModalEditar() {
    idEditarForm.btnEditar.addEventListener('click', (event) => {
        toggleModal('openModalEditar', event);
    });

    idEditarForm.btnCerrar.addEventListener('click', () => toggleModal('closeModalEditar'));

    idEditarForm.btnGuardar.addEventListener('click', (event) => {
        guardarDatos('editar', event);
    });

    idEditarForm.overlay.addEventListener('click', (event) => {
        if (event.target === idEditarForm.overlay) {
            toggleModal('closeModalEditar');
        }
    });
}

function configurarModalPfp() {
    if (validarBotones(idFotoPerfil)) {
        idFotoPerfil.cambiarFotoPerfil.addEventListener('click', () => {
            pfpOriginal = idFotoPerfil.fotoPerfil.src;
            toggleModal('openPrincipalPerfil');
        });

        idFotoPerfil.cerrarModalPerfil.addEventListener('click', () => toggleModal('closePrincipalPerfil'));

        idFotoPerfil.btnModalAvatarPredeterminado.addEventListener('click', (event) => {
            toggleModal('openTarjetaAvatarPredeterminado', event);
            toggleModal('closeOnlyPerfilModal');

        });

        idFotoPerfil.btnModalAvatarPersonalizado.addEventListener('click', () => {
            toggleModal('openTarjetaAvatarPersonalizado');
            toggleModal('closeOnlyPerfilModal');
        });

        idFotoPerfil.btnSubirAvatarPersonalizado.addEventListener('click', () => {
            idFotoPerfil.inputFile.click();
        });

        idFotoPerfil.btnCancelarTarjetaAvatarPredeterminado.addEventListener('click', () => {
            actualizarImagen('avatar', pfpOriginal);
            toggleModal('closePrincipalPerfil');
        });

        idFotoPerfil.btnGuardarTarjetaAvatarPredeterminado.addEventListener('click', (event) => {
            guardarDatos('avatarPredeterminado', event);
            toggleModal('closePrincipalPerfil');
        });

        idFotoPerfil.btnCancelarTarjetaAvatarPersonalizado.addEventListener('click', () => {
            actualizarImagen('avatar', pfpOriginal);
            toggleModal('closePrincipalPerfil');
        });

        idFotoPerfil.btnGuardarTarjetaAvatarPersonalizado.addEventListener('click', (event) => {
            guardarDatos('avatarPersonalizado', event);
            toggleModal('closePrincipalPerfil');
        });

        idFotoPerfil.overlay.addEventListener('click', (event) => {
            if (event.target === idFotoPerfil.overlay) {
                toggleModal('closePrincipalPerfil');
            }
        });
    }
}

function configurarModalBanner() {
    if (validarBotones(idBannerPerfil)) {
        idBannerPerfil.cambiarBannerPerfil.addEventListener('click', () => {
            bannerOriginal = idBannerPerfil.bannerPerfil.src;
            toggleModal('openPrincipalBanner');
        });

        idBannerPerfil.cerrarModalBanner.addEventListener('click', () => toggleModal('closeBanner'));

        idBannerPerfil.btnModalBannerPredeterminado.addEventListener('click', (event) => {
            toggleModal('openTarjetaBannerPredeterminado', event);
            toggleModal('closeOnlyBannerModal');
        });

        idBannerPerfil.btnModalBannerPersonalizado.addEventListener('click', () => {

            toggleModal('openTarjetaBannerPersonalizado');
            toggleModal('closeOnlyBannerModal');
        });

        idBannerPerfil.btnSubirBannerPersonalizado.addEventListener('click', () => {
            idBannerPerfil.inputBanner.click();
        });

        idBannerPerfil.btnCancelarTarjetaBannerPredeterminado.addEventListener('click', () => {
            actualizarImagen('banner', bannerOriginal);
            toggleModal('closeBanner');
        });

        idBannerPerfil.btnGuardarTarjetaBannerPredeterminado.addEventListener('click', (event) => {
            guardarDatos('bannerPredeterminado', event);
            toggleModal('closeBanner');
        });

        idBannerPerfil.btnCancelarTarjetaBannerPersonalizado.addEventListener('click', () => {
            actualizarImagen('banner', bannerOriginal);
            toggleModal('closeBanner');
        });

        idBannerPerfil.btnGuardarTarjetaBannerPersonalizado.addEventListener('click', (event) => {
            guardarDatos('bannerPersonalizado', event);
            toggleModal('closeBanner');
        });

        idBannerPerfil.overlay.addEventListener('click', (event) => {
            if (event.target === idBannerPerfil.overlay) {
                toggleModal('closeBanner');
            }
        });
    }
}

function toggleModal(accion, event) {
    switch (accion) {
        case 'openModalEditar':
            mostrarModalPred('editar', 'modalEditar', event);
            break;
        case 'closeModalEditar':
            ocultarModal('editar');
            break;
        case 'openPrincipalPerfil':
            mostrarModal('avatar', 'modalPfp');
            break;
        case 'closePrincipalPerfil':
            ocultarModal('avatar');
            break;
        case 'closeOnlyPerfilModal':
            ocultarModal('onlyPrincipalPerfil');
            break;
        case 'closeOnlyBannerModal':
            ocultarModal('onlyPrincipalBanner');
            break;
        case 'openTarjetaAvatarPredeterminado':
            mostrarModalPred('avatar', 'tarjetaAvatarPredeterminado', event);
            idFotoPerfil.inputAvatarTipoPredeterminado.value = "AvatarPredeterminado";
            break;
        case 'openTarjetaAvatarPersonalizado':
            mostrarModal('avatar', 'tarjetaAvatarPersonalizado');
            idFotoPerfil.inputAvatarTipoPersonalizado.value = "AvatarPersonalizado";
            break;
        case 'openPrincipalBanner':
            mostrarModal('banner', 'modalBanner');
            break;
        case 'closeBanner':
            ocultarModal('banner');
            break;
        case 'openTarjetaBannerPredeterminado':
            mostrarModalPred('banner', 'tarjetaBannerPredeterminado', event);
            idBannerPerfil.inputTipoBannerPre.value = "BannerPredeterminado";
            break;
        case 'openTarjetaBannerPersonalizado':
            mostrarModal('banner', 'tarjetaBannerPersonalizado');
            idBannerPerfil.inputTipoBannerPer.value = "BannerPersonalizado";
            break;
        default:
            ocultarModal();
            break;
    }
}

/*Funciones que muestren el modal y lo ocultan*/
function mostrarModal(denom, nombreModal) {
    if (denom === 'avatar') {
        idFotoPerfil.overlay.classList.add('activo-overlay');
        idFotoPerfil[nombreModal].classList.add('activo');
    } else if (denom === 'banner') {
        idBannerPerfil.overlay.classList.add('activo-overlay');
        idBannerPerfil[nombreModal].classList.add('activo');
    }
}

function mostrarModalPred(denom, nombreModal, event) {
    if (denom === 'avatar') {
        idFotoPerfil.overlay.classList.add('activo-overlay');
        idFotoPerfil[nombreModal].classList.add('activo');
        obtencionPfps(event);
    } else if (denom === 'banner') {
        idBannerPerfil.overlay.classList.add('activo-overlay');
        idBannerPerfil[nombreModal].classList.add('activo');
        obtencionBanners(event);
    } else if (denom === 'editar') {
        idEditarForm.overlay.classList.add('activo-overlay');
        idEditarForm[nombreModal].classList.add('activo');
        obtencionDatosUsuario(event);
    }
}

function ocultarModal(denom) {
    if (denom === 'avatar') {
        idFotoPerfil.overlay.classList.remove('activo-overlay');
        for (let key in idFotoPerfil) {
            if (idFotoPerfil[key] && idFotoPerfil[key].classList) {
                idFotoPerfil[key].classList.remove('activo');
            }
        }
    } else if (denom === 'banner') {
        idBannerPerfil.overlay.classList.remove('activo-overlay');
        for (let key in idBannerPerfil) {
            if (idBannerPerfil[key] && idBannerPerfil[key].classList) {
                idBannerPerfil[key].classList.remove('activo');
            }
        }
    } else if (denom === 'editar') {
        idEditarForm.overlay.classList.remove('activo-overlay');
        for (let key in idEditarForm) {
            if (idEditarForm[key] && idEditarForm[key].classList) {
                idEditarForm[key].classList.remove('activo');
            }
        }
    } else if (denom === 'onlyPrincipalPerfil') {
        idFotoPerfil.modalPfp.classList.remove('activo');
    } else if (denom === 'onlyPrincipalBanner') {
        idBannerPerfil.modalBanner.classList.remove('activo');
    } else {
        idFotoPerfil.overlay.classList.remove('activo-overlay');
        for (let key in idFotoPerfil) {
            if (idFotoPerfil[key] && idFotoPerfil[key].classList) {
                idFotoPerfil[key].classList.remove('activo');
            }
        }
        idBannerPerfil.overlay.classList.remove('activo-overlay');
        for (let key in idBannerPerfil) {
            if (idBannerPerfil[key] && idBannerPerfil[key].classList) {
                idBannerPerfil[key].classList.remove('activo');
            }
        }
        idEditarForm.overlay.classList.remove('activo-overlay');
        for (let key in idEditarForm) {
            if (idEditarForm[key] && idEditarForm[key].classList) {
                idEditarForm[key].classList.remove('activo');
            }
        }
    }
}

function actualizarImagen(denom, src) {
    if (denom === 'avatar') {
        idFotoPerfil.fotoPerfil.src = src;
        idFotoPerfil.imgModalAvatarPredeterminado.src = src;
        idFotoPerfil.imgAvatarPredeterminadoPreview.src = src;
        idFotoPerfil.imgAvatarPersonalizadoPreview.src = src;
        idFotoPerfil.imgAvatarPersonalizadoPequeno.src = src;
    } else if (denom === 'banner') {
        idBannerPerfil.bannerPerfil.src = src;
        idBannerPerfil.imgModalBannerPredeterminado.src = src;
        idBannerPerfil.imgBannerPredeterminadoPreview.src = src;
        idBannerPerfil.imgBannerPersonalizadoPreview.src = src;
        idBannerPerfil.imgBannerPersonalizadoPequeno.src = src;
    }
}

function guardarDatos(denom, event) {
    switch (denom) {
        case 'avatarPredeterminado':
            manejoGuardarAvatar(event);
            idFotoPerfil.inputAvatarTipoPredeterminado.value = "";
            ocultarModal('avatar');
            break;
        case 'avatarPersonalizado':
            manejoGuardarAvatarPersonalizado(event);
            idFotoPerfil.inputAvatarTipoPersonalizado.value = "";
            ocultarModal('avatar');
            break;
        case 'bannerPredeterminado':
            manejoGuardarBanner(event);
            idBannerPerfil.inputTipoBannerPre.value = "";
            ocultarModal('banner');
            break;
        case 'bannerPersonalizado':
            manejoGuardarBannerPersonalizado(event);
            idBannerPerfil.inputTipoBannerPer.value = "";
            ocultarModal('banner');
            break;
        case 'editar':
            manejoEditar(event);
            ocultarModal('editar');
            break;
        default:
            break;
    }
}

function configurarCambioFotoPerfil() {
    const imgSubir = document.getElementById("input-archivo");
    const imgPerfil = document.getElementById("personalizableImg");
    const imgPerfilPequena = document.getElementById("foto-pequena");
    const maxFileSize = 10485760; // 10MB en bytes

    if (imgSubir) {
        imgSubir.addEventListener('change', () => {
            const archivo = imgSubir.files[0];
            if (archivo) {
                const validImageTypes = ['image/jpeg', 'image/png', 'image/gif'];

                if (!validImageTypes.includes(archivo.type)) {
                    showModal('errorModal', "¡No se permite ese tipo de archivo!");
                    return;
                }

                if (archivo.size > maxFileSize) {
                    showModal('errorModal', "El tamaño del archivo supera el límite de 10MB.");
                    return;
                }

                const lector = new FileReader();
                lector.onload = (event) => {
                    imgPerfil.src = event.target.result;
                    imgPerfilPequena.src = event.target.result;
                };
                lector.readAsDataURL(archivo);
            }
        });
    }
}

function configurarCambioDeBanner() {
    const imgSubir = document.getElementById("input-banner");
    const imgBanner = document.getElementById("bannerPreviewPers");
    const imgBannerPequeno = document.getElementById("banner-pequeno");
    const maxFileSize = 10485760; // 10 mb

    if (imgSubir) {
        imgSubir.addEventListener('change', () => {
            const archivo = imgSubir.files[0];
            if (archivo) {
                const validImageTypes = ['image/jpeg', 'image/png', 'image/gif'];

                if (!validImageTypes.includes(archivo.type)) {
                    showModal('errorModal', "¡No se permite ese tipo de archivo!");
                    return;
                }

                if (archivo.size > maxFileSize) {
                    showModal('errorModal', "El tamaño del archivo supera el límite de 10mb.");
                    return;
                }

                const lector = new FileReader();
                lector.onload = (event) => {
                    imgBanner.src = event.target.result;
                    imgBannerPequeno.src = event.target.result;
                };
                lector.readAsDataURL(archivo);
            }
        });
    }
}