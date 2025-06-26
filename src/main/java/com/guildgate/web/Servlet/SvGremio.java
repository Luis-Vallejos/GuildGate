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
import com.guildgate.web.Modelo.Controladora;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvGremio", urlPatterns = {"/SvGremio"})
public class SvGremio extends HttpServlet {

    Controladora control = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Nothing*/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        int idRegion, idMundo;
        control = new Controladora();
        ArrayList<Gremio> listaGremios = control.traerListaGremios();
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
        SvUtils.processGuildLists(response, request, gre, control);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        control = new Controladora();
        HttpSession session = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("usuarioBean");
        String usuarioActual = usuarioBean.getUsuarioActual();

        if (SvUtils.isNullOrEmpty(usuarioActual)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        Optional<Usuarios> optUsuarios = SvUtils.findUserByUsername(usuarioActual, control.traerListaUsuarios());
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

        Optional<Gremio> optGremio = SvUtils.findGremioByName(nombre, control.traerListaGremios());

        if (optGremio.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.GREMIO_NOMBRE_EXISTE);
        } else {
            control.crearGremio(nombre, descripcion, idRegion, idMundo, usuarioActual);

            Optional<Gremio> optNuevoGremio = SvUtils.findGremioByName(nombre, control.traerListaGremios());

            if (optNuevoGremio.isPresent()) {
                Gremio nuevoGremio = optNuevoGremio.get();
                SvUtils.processSuccesfulGuildCreation(response, request, nuevoGremio, user, control);
                SvUtils.respondWithSuccess(response, HttpServletResponse.SC_CREATED, Mensajes.GREMIO_CREADO_EXITO);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}