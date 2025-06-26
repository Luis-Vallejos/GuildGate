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
import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvDatosGremio", urlPatterns = {"/SvDatosGremio"})
public class SvDatosGremio extends HttpServlet {

    Controladora control = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Nothing*/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String idParam = request.getParameter("id");

        if (SvUtils.isNullOrEmpty(idParam)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        int idGremio;
        try {
            idGremio = SvUtils.parseIntOrDefault(idParam, -1);
        } catch (NumberFormatException e) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.ERROR_PROCESAMIENTO);
            return;
        }

        control = new Controladora();
        ArrayList<Gremio> listaGremios = control.traerListaGremios();
        Optional<Gremio> optGremio = SvUtils.findGremioById(idGremio, listaGremios);

        if (!optGremio.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_NOT_FOUND, Mensajes.GREMIO_NO_ENCONTRADO);
            return;
        }

        Gremio gre = optGremio.get();
        SvUtils.processGuildInfo(response, request, gre, control);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String rolNom = "miembro";

        HttpSession session = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) session.getAttribute("usuarioBean");
        String usuarioActual = usuarioBean.getUsuarioActual();

        if (SvUtils.isNullOrEmpty(usuarioActual)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_NO_AUTENTICADO);
            return;
        }

        String gremioParam = request.getParameter("gremioId");
        if (SvUtils.isNullOrEmpty(gremioParam)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.GREMIO_NO_ENCONTRADO);
            return;
        }

        int idGremio;
        control = new Controladora();

        try {
            idGremio = SvUtils.parseIntOrDefault(gremioParam, -1);
        } catch (NumberFormatException e) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.GREMIO_NO_ENCONTRADO);
            return;
        }

        SvUtils.processJoinGuild(response, request, usuarioActual, idGremio, rolNom, control);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}