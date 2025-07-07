package com.guildgate.web.Servlet;

import com.guildgate.web.Controller.UsuarioController;
import java.io.IOException;
import java.util.Optional;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.Procesos.SubidaImagenes;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvRegister", urlPatterns = {"/SvRegister"})
public class SvRegister extends HttpServlet {

    @Inject
    UsuarioController uc;

    @Inject
    SubidaImagenes si;

    @Override
    public void init() throws ServletException {
        this.uc = new UsuarioController();
        this.si = new SubidaImagenes();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        si.todosLosBanners();
        si.todosLosFondosGremio();
        response.sendRedirect("registro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("correo");
        String contra = request.getParameter("contra");

        if (SvUtils.isNullOrEmpty(nombre) || SvUtils.isNullOrEmpty(email) || SvUtils.isNullOrEmpty(contra)) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.CAMPOS_VACIOS);
            return;
        }

        Optional<Usuarios> optJugadores = SvUtils.findUsersByUsernameOrEmail(nombre, email, uc.traerListaUsuarios());

        if (optJugadores.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_CONFLICT, Mensajes.USUARIO_NOMBRE_EXISTE);
            return;
        }

        uc.anadirUsuario(nombre, email, contra);
        SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.USUARIO_REGISTRADO);
    }
}
