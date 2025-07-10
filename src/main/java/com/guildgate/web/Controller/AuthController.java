package com.guildgate.web.Controller;

import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Service.impl.AvatarGremioServiceImpl;
import com.guildgate.web.Service.impl.BannerServiceImpl;
import com.guildgate.web.Service.impl.FondoGremioServiceImpl;
import com.guildgate.web.Service.impl.GremioServiceImpl;
import com.guildgate.web.Service.impl.PerfilServiceImpl;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author Juan - Luis
 */
public class AuthController {

    @Inject
    GremioServiceImpl gs;

    @Inject
    PerfilServiceImpl ps;

    @Inject
    BannerServiceImpl bs;

    @Inject
    AvatarGremioServiceImpl ags;

    @Inject
    FondoGremioServiceImpl fgs;

    public AuthController() {
        this.gs = new GremioServiceImpl();
        this.ps = new PerfilServiceImpl();
        this.bs = new BannerServiceImpl();
        this.ags = new AvatarGremioServiceImpl();
        this.fgs = new FondoGremioServiceImpl();
    }

    //Metodo para procesar el login y asignar variables a la sesión
    @SuppressWarnings("null")
    public void processSuccessfulLogin(HttpServletResponse response, HttpServletRequest request, Usuarios user) throws IOException {
        Gremio gremioUsuario = user.getGremiousuario();
        Optional<Gremio> optGremios = gremioUsuario != null ? SvUtils.findGremioById(gremioUsuario.getId(), gs.findAll()) : Optional.empty();
        Gremio gre = optGremios.orElse(null);

        Optional<Roles> optRoles = SvUtils.obtenerObjetoRolPorUsuario(user);
        Roles roles = optRoles.orElse(null);

        String imagenBase64DataUrl = user.getImg() != null ? SvUtils.getImageDataUrl(user.getImg(), ps) : null;
        String imagenBase64DataUrlG = (gre != null && gre.getImg() != null) ? SvUtils.getAvatarGremioDataUrl(gre.getImg(), ags) : null;
        String imagenBase64DataUrlBanner = user.getImgB() != null ? SvUtils.getBannerDataUrl(user.getImgB(), bs) : null;
        String imagenBase64DataUrlGFondo = (gre != null && gre.getImgF() != null) ? SvUtils.getFondoGremioDataUrl(gre.getImgF(), fgs) : null;
        String nombreGremio = (gremioUsuario != null) ? gremioUsuario.getNombre() : "Sin Gremio";
        String descripcionGremio = (gremioUsuario != null) ? gremioUsuario.getDescripcion() : "No contienen descripción...";
        String bioUsuario = user.getBio() != null ? user.getBio() : "¡Esta es una bio salvaje!";
        int nivelUsuario = user.getNivel() != 0 ? user.getNivel() : 1;
        String nombreRol = roles != null ? roles.getNombre() : "Sin Rol";
        String contraUsuario = user.getContrasena() != null ? user.getContrasena() : null;
        String emailUsuario = user.getCorreo() != null ? user.getCorreo() : null;

        HttpSession sesion = request.getSession(false);

        UsuarioBean usuarioBean = new UsuarioBean();
        usuarioBean.setId(user.getId());
        usuarioBean.setUsuarioActual(user.getNombre());
        usuarioBean.setDescripcionGremio(descripcionGremio);
        usuarioBean.setGremioActual(nombreGremio);
        usuarioBean.setRolUsuario(nombreRol);
        usuarioBean.setBioUsuario(bioUsuario);
        usuarioBean.setNivelUsuario(nivelUsuario);
        usuarioBean.setNombreAvatar(user.getImg().getNomArchivo());
        usuarioBean.setImagenUsuario(imagenBase64DataUrl);
        usuarioBean.setNombreAvatarGremio(gre != null ? gre.getImg().getNomArchivo() : null);
        usuarioBean.setImagenGremio(imagenBase64DataUrlG);
        usuarioBean.setNombreFondoGremio(gre != null ? gre.getImgF().getNomArchivo() : null);
        usuarioBean.setImagenFondoGremio(imagenBase64DataUrlGFondo);
        usuarioBean.setNombreBanner(user.getImgB().getNomArchivo());
        usuarioBean.setBanner(imagenBase64DataUrlBanner);
        usuarioBean.setPosicionXBanner(user.getImgB().getPosicionX());
        usuarioBean.setPosicionYBanner(user.getImgB().getPosicionY());
        usuarioBean.setContra(contraUsuario);
        usuarioBean.setCorreo(emailUsuario);

        sesion.setAttribute("usuarioBean", usuarioBean);
    }
}
