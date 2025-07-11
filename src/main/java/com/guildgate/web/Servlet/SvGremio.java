package com.guildgate.web.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Bean.SessionUserBean;
import com.guildgate.web.Controller.GremioController;
import com.guildgate.web.Controller.UsuarioController;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvGremio", urlPatterns = {"/SvGremio"})
public class SvGremio extends HttpServlet {

    @Inject
    UsuarioController uc;

    @Inject
    GremioController gc;

    @Override
    public void init() throws ServletException {
        this.uc = new UsuarioController();
        this.gc = new GremioController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        int idRegion, idMundo;
        ArrayList<Gremio> listaGremios = gc.traerListaGremios();
        String regionParam = request.getParameter("region");
        String mundoParam = request.getParameter("mundo");

        if (SvUtils.isNullOrEmpty(mundoParam) || SvUtils.isNullOrEmpty(regionParam)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        try {
            idRegion = SvUtils.parseIntOrDefault(regionParam, -1);
            idMundo = SvUtils.parseIntOrDefault(mundoParam, -1);
        } catch (NumberFormatException e) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.ERROR_PARAMETROS);
            return;
        }

        Optional<Gremio> optGremio = SvUtils.findGremioByRegionAndMundo(idRegion, idMundo, listaGremios);

        if (!optGremio.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_NOT_FOUND, Mensajes.GREMIO_LISTA_NO_ENCONTRADO);
        }

        Gremio gre = optGremio.get();
        gc.processGuildLists(response, request, gre);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        HttpSession session = request.getSession(false);
        SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("sessionUserBean");
        String usuarioActual = sessionUserBean.getPerfil().getNomUserActual();

        if (SvUtils.isNullOrEmpty(usuarioActual)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        Optional<Usuarios> optUsuarios = SvUtils.findUserByUsername(usuarioActual, uc.traerListaUsuarios());
        Usuarios user = optUsuarios.get();

        int idRegion, idMundo;
        String nombre = request.getParameter("nomGremio");
        String descripcion = request.getParameter("descGremio");
        String regionParam = request.getParameter("regionGremio");
        String mundoParam = request.getParameter("mundoGremio");

        if (SvUtils.isNullOrEmpty(nombre) || SvUtils.isNullOrEmpty(descripcion) || SvUtils.isNullOrEmpty(regionParam) || SvUtils.isNullOrEmpty(mundoParam)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        try {
            idRegion = SvUtils.parseIntOrDefault(regionParam, -1);
            idMundo = SvUtils.parseIntOrDefault(mundoParam, -1);
        } catch (NumberFormatException e) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.ERROR_PROCESAMIENTO);
            return;
        }

        Optional<Gremio> optGremio = SvUtils.findGremioByName(nombre, gc.traerListaGremios());

        if (optGremio.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.GREMIO_NOMBRE_EXISTE);
        } else {
            gc.crearGremio(nombre, descripcion, idRegion, idMundo, usuarioActual);

            Optional<Gremio> optNuevoGremio = SvUtils.findGremioByName(nombre, gc.traerListaGremios());

            if (optNuevoGremio.isPresent()) {
                Gremio nuevoGremio = optNuevoGremio.get();
                gc.processSuccesfulGuildCreation(response, request, nuevoGremio, user);
                SvUtils.respondWithSuccess(response, HttpServletResponse.SC_CREATED, Mensajes.GREMIO_CREADO_EXITO);
            }
        }
    }
}
