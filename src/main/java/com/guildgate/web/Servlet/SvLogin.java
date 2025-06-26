package com.guildgate.web.Servlet;

import java.io.IOException;
import java.util.Optional;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.guildgate.web.Modelo.Controladora;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

    Controladora control = null;
    //Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //control = new Controladora();
        //control.todasLasImagenes();
        //control.todosLosAvataresGremio();
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String nomUser = request.getParameter("username");
        String contra = request.getParameter("contra");

        if (SvUtils.isNullOrEmpty(nomUser) || SvUtils.isNullOrEmpty(contra)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        control = new Controladora();
        Optional<Usuarios> optUsuarios = SvUtils.findUserByUsernameOrEmail(nomUser, control.traerListaUsuarios());

        if (optUsuarios.isPresent()) {
            Usuarios jug = optUsuarios.get();
            if (contra.equals(jug.getContrasena())) {
                SvUtils.processSuccessfulLogin(response, request, jug, control);
                SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.USUARIO_LOGEADO + jug.getNombre());
            } else {
                SvUtils.respondWithError(response, HttpServletResponse.SC_UNAUTHORIZED, Mensajes.USUARIO_CONTRA_INCORRECTA);
            }
        } else {
            SvUtils.respondWithError(response, HttpServletResponse.SC_NOT_FOUND, Mensajes.USUARIO_INEXISTENTE);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}