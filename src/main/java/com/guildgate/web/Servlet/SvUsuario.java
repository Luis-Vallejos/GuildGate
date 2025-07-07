package com.guildgate.web.Servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Controller.UsuarioController;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;

/**
 *
 * @author Lavender
 */
@WebServlet(name = "SvUsuario", urlPatterns = {"/SvUsuario"})
public class SvUsuario extends HttpServlet {

    @Inject
    UsuarioController uc;

    @Override
    public void init() throws ServletException {
        this.uc = new UsuarioController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Bring user data to the form*/
        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");

        String nomUsuario = usuarioBean.getUsuarioActual();
        String correoUsuario = usuarioBean.getCorreo();
        String contraUsuario = usuarioBean.getContra();
        String bioUsuario = usuarioBean.getBioUsuario();

        if (SvUtils.isNullOrEmpty(nomUsuario) || SvUtils.isNullOrEmpty(correoUsuario)
                || SvUtils.isNullOrEmpty(contraUsuario) || SvUtils.isNullOrEmpty(bioUsuario)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.USUARIO_NO_AUTENTICADO);
            response.sendRedirect("login.jsp");
        }

        Map<String, String> datosUsuario = new HashMap<>();
        datosUsuario.put("nombre", nomUsuario);
        datosUsuario.put("correo", correoUsuario);
        datosUsuario.put("contra", contraUsuario);
        datosUsuario.put("bio", bioUsuario);

        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonDatos = gson.toJson(datosUsuario);
        response.getWriter().write(jsonDatos);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Send new user data to the database*/
        response.setContentType("application/json;charset=UTF-8");

        HttpSession sesion = request.getSession(false);
        if (sesion == null || sesion.getAttribute("usuarioBean") == null) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");

        String nomUsuario = request.getParameter("nomusuario");
        String correo = request.getParameter("correousuario");
        String contra = request.getParameter("contrausuario");
        String bio = request.getParameter("biousuario");

        if (SvUtils.isNullOrEmpty(nomUsuario) || SvUtils.isNullOrEmpty(correo)
                || SvUtils.isNullOrEmpty(contra) || SvUtils.isNullOrEmpty(bio)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        } else if (nomUsuario.equalsIgnoreCase(usuarioBean.getUsuarioActual())
                && correo.equalsIgnoreCase(usuarioBean.getCorreo())
                && contra.equalsIgnoreCase(usuarioBean.getContra())
                && bio.equalsIgnoreCase(usuarioBean.getBioUsuario())) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.USUARIO_DATOS_DUPLICADOS);
            return;
        }

        try {
            ArrayList<Usuarios> listaJugadores = uc.traerListaUsuarios();
            uc.editarUsuario(nomUsuario, correo, contra, bio, listaJugadores, usuarioBean.getUsuarioActual());

            usuarioBean.setUsuarioActual(nomUsuario);
            usuarioBean.setCorreo(correo);
            usuarioBean.setContra(contra);
            usuarioBean.setBioUsuario(bio);

            SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.USUARIO_DATOS_MODIFICADOS);
        } catch (IOException e) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.ERROR_PROCESAMIENTO);
        }
    }
}
