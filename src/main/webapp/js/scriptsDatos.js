export const usuario = {
  usuarioElements: document.querySelectorAll(".link-item"),
  modalElements: document.querySelectorAll(".dialog.hidden"),
  cuerpo: document.body,
  cabeza: document.getElementById('navegador'),
  pie: document.getElementById('pie')
};

export const idGremioAvatarFondo = {
  modalEditarFondo: document.getElementById('modal-editar-fondo'),
  tarjetaFondoGremio: document.getElementById('tarjeta-fondo-gremio'),

  encabezadoGremio : document.getElementById("encabezado-gremio"),
  tituloRoles : document.getElementById("titulo-roles"),
  tarjetaRol : document.querySelectorAll(".tarjeta-rol"),
  tituloParticipaciones : document.getElementById("titulo-parti"),
  tituloRolesTablas : document.getElementById("titulo-rol"),
  tituloRaids : document.getElementById("titulo-raids"),
  participantes : document.getElementById("participantes"),
  roles : document.getElementById("roles"),
  raids : document.getElementById("raids")
};

export const idFotoPerfil = {
    overlay: document.getElementById('overlay'),
    cambiarFotoPerfil: document.getElementById('boton-cambiar-imagen'),
    fotoPerfil: document.getElementById('perfil-imagen'),

    modalPfp: document.getElementById('modal-pfp'),
    cerrarModalPerfil: document.getElementById('btnSalir-pfp'),
    imgModalAvatarPredeterminado: document.getElementById('pfp'),
    btnModalAvatarPredeterminado: document.getElementById('btnAvatarPredeterminado'),
    btnModalAvatarPersonalizado: document.getElementById('btnSubirAvatar'),

    tarjetaAvatarPredeterminado: document.getElementById('tarjeta-avatar-predeterminado'),
    imgAvatarPredeterminadoPreview: document.getElementById('imgPreviewPred'),
    galeriaFotos: document.getElementById('galeria-fotos'),
    galeriaTarjetaAvatarPredeterminado: document.querySelectorAll('#galeria-fotos button img'),
    btnGaleriaTarjetaAvatarPredeterminado: document.querySelectorAll('#galeria-fotos button'),
    btnCancelarTarjetaAvatarPredeterminado: document.getElementById('boton-cancelar'),
    btnGuardarTarjetaAvatarPredeterminado: document.getElementById('boton-guardar'),
    inputAvatarTipoPredeterminado : document.getElementById('modalAvatarTipoPre'),

    tarjetaAvatarPersonalizado: document.getElementById('tarjeta-avatar-personalizado'),
    imgAvatarPersonalizadoPreview: document.getElementById('personalizableImg'),
    btnSubirAvatarPersonalizado: document.getElementById('boton-subir-personalizado'),
    inputFile: document.getElementById('input-archivo'),
    imgAvatarPersonalizadoPequeno: document.getElementById('foto-pequena'),
    btnCancelarTarjetaAvatarPersonalizado: document.getElementById('boton-cancelar-personalizado'),
    btnGuardarTarjetaAvatarPersonalizado: document.getElementById('boton-guardar-personalizado'),
    inputAvatarTipoPersonalizado : document.getElementById('modalAvatarTipoPer')
};

export const idBannerPerfil = {
    overlay: document.getElementById('overlay'),
    cambiarBannerPerfil: document.getElementById('banner-gradiente'),
    bannerPerfil: document.getElementById('banner-imagen'),

    modalBanner: document.getElementById('modal-banner'),
    cerrarModalBanner: document.getElementById('btnSalir-banner'),
    imgModalBannerPredeterminado: document.getElementById('ban'),
    btnModalBannerPredeterminado: document.getElementById('btnBannerPredeterminado'),
    btnModalBannerPersonalizado: document.getElementById('btnSubirBanner'),

    tarjetaBannerPredeterminado: document.getElementById('tarjeta-banner-predeterminado'),
    imgBannerPredeterminadoPreview: document.getElementById('bannerPreviewPred'),
    galeriaBanner: document.getElementById('galeria-banners'),
    galeriaTarjetaBannerPredeterminado: document.querySelectorAll('#galeria-banners button img'),
    btnGaleriaTarjetaBannerPredeterminado: document.querySelectorAll('#galeria-banners button'),
    btnCancelarTarjetaBannerPredeterminado: document.getElementById('btnCancelarBanner'),
    btnGuardarTarjetaBannerPredeterminado: document.getElementById('btnGuardarBanner'),
    inputTipoBannerPre: document.getElementById('modalBannerTipoPre'),

    tarjetaBannerPersonalizado: document.getElementById('tarjeta-banner-personalizado'),
    imgBannerPersonalizadoPreview: document.getElementById('bannerPreviewPers'),
    btnSubirBannerPersonalizado: document.getElementById('btnUploadBanner'),
    inputBanner: document.getElementById('input-banner'),
    imgBannerPersonalizadoPequeno: document.getElementById('banner-pequeno'),
    btnCancelarTarjetaBannerPersonalizado: document.getElementById('btnCancelarBanner-Personalizado'),
    btnGuardarTarjetaBannerPersonalizado: document.getElementById('btnGuardarBanner-Personalizado'),
    inputTipoBannerPer: document.getElementById('modalBannerTipoPer')
};

export const idEditarForm = {
    overlay: document.getElementById('overlay'),
    btnEditar: document.getElementById('boton-editar-perfil'),
    btnCerrar: document.getElementById('btnCerrar'),

    modalEditar: document.getElementById('modal-editar'),
    formUsuario: document.getElementById('formDatosUsuario'),
    formEditar: document.getElementById('formPerfil'),

    btnMostrarContra: document.getElementById('btnMostrarContra'),
    inputNombre: document.getElementById('nomuser'),
    inputCorre: document.getElementById('correo'),
    inputContra: document.getElementById('contra'),
    iconOpenEye: document.getElementById('iconoOjoAbierto'),
    iconClosedEye: document.getElementById('iconoOjoCerrado'),
    textAreaBio: document.getElementById('bio'),

    btnGuardar: document.getElementById('btnGuardar')
};