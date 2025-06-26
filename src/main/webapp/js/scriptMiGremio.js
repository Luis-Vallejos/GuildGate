import { usuario, idFotoPerfil, idEditarForm, idGremioAvatarFondo } from "./scriptsDatos.js";
import { showModal, setupModalCloseButtons } from "./scriptsMensajes.js";
import { setupUsuarioValidation } from "./scriptsReutilizables.js";

document.addEventListener("DOMContentLoaded", () => {
  setupModalCloseButtons();

  setupUsuarioValidation(usuario.usuarioElements);

  visualizarFondoCompleto();
});

function visualizarFondoCompleto() {
  usuario.cuerpo.addEventListener("click", (event) => {
    if (
      !isClickInside(event, idFotoPerfil.modalPfp) &&
      !isClickInside(event, idFotoPerfil.tarjetaAvatarPredeterminado) &&
      !isClickInside(event, idFotoPerfil.tarjetaAvatarPersonalizado) &&
      !isClickInside(event, idEditarForm.modalEditar) &&
      !isClickInside(event, idGremioAvatarFondo.modalEditarFondo) &&
      !isClickInside(event, idGremioAvatarFondo.tarjetaFondoGremio) &&
      !isClickInside(event, idFotoPerfil.overlay) &&
      !isClickInside(event, usuario.cabeza) &&
      !isClickInside(event, usuario.pie) &&
      !isClickInside(event, idGremioAvatarFondo.encabezadoGremio) &&
      !isClickInside(event, idGremioAvatarFondo.tituloRoles) &&
      !isClickInside(event, idGremioAvatarFondo.tituloParticipaciones) &&
      !isClickInside(event, idGremioAvatarFondo.tituloRolesTablas) &&
      !isClickInside(event, idGremioAvatarFondo.tituloRaids) &&
      !isClickInside(event, idGremioAvatarFondo.participantes) &&
      !isClickInside(event, idGremioAvatarFondo.roles) &&
      !isClickInside(event, idGremioAvatarFondo.raids) &&
      !isAnyClickInside(event, idGremioAvatarFondo.tarjetaRol) &&
      !isAnyClickInside(event, usuario.modalElements)
    ) {
      if (idGremioAvatarFondo.encabezadoGremio.style.display === "none") {
        idGremioAvatarFondo.encabezadoGremio.style.display = "flex";
        idGremioAvatarFondo.tituloRoles.style.display = "";
        idGremioAvatarFondo.tituloParticipaciones.style.display = "";
        idGremioAvatarFondo.tituloRolesTablas.style.display = "";
        idGremioAvatarFondo.tituloRaids.style.display = "";
        idGremioAvatarFondo.participantes.style.display = "";
        idGremioAvatarFondo.roles.style.display = "";
        idGremioAvatarFondo.raids.style.display = "";
        usuario.cuerpo.style.paddingBottom = "12em";
        showAllTarjetaRol(idGremioAvatarFondo.tarjetaRol);
        scrollToPercentage(0);
      } else {
        idGremioAvatarFondo.encabezadoGremio.style.display = "none";
        idGremioAvatarFondo.tituloRoles.style.display = "none";
        hideAllTarjetaRol(idGremioAvatarFondo.tarjetaRol);
        idGremioAvatarFondo.tituloParticipaciones.style.display = "none";
        idGremioAvatarFondo.tituloRolesTablas.style.display = "none";
        idGremioAvatarFondo.tituloRaids.style.display = "none";
        idGremioAvatarFondo.participantes.style.display = "none";
        idGremioAvatarFondo.roles.style.display = "none";
        idGremioAvatarFondo.raids.style.display = "none";
        usuario.cuerpo.style.paddingBottom = "17em";
        scrollToPercentage(30);
      }
    }
  });
}

function isClickInside(event, element) {
  return element.contains(event.target);
}

function isAnyClickInside(event, elements) {
  return Array.from(elements).some(element => element.contains(event.target));
}

function hideAllTarjetaRol(elements) {
  elements.forEach(element => {
    element.style.display = "none";
  });
}

function showAllTarjetaRol(elements) {
  elements.forEach(element => {
    element.style.display = "";
  });
}

function scrollToPercentage(percentage) {
  const scrollHeight = document.body.scrollHeight - window.innerHeight;
  const scrollTo = scrollHeight * (percentage / 100);

  window.scrollTo({
    top: scrollTo,
    behavior: "smooth"
  });
}