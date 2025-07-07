package com.guildgate.web.Servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Controller.ImagenPerfilController;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvPerfil", urlPatterns = {"/SvPerfil"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15) // 15 MB
public class SvPerfil extends HttpServlet {

    @Inject
    ImagenPerfilController ipc;

    @Override
    public void init() throws ServletException {
        this.ipc = new ImagenPerfilController();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Bring all predetermined profile pictures*/
        response.setContentType("application/json;charset=UTF-8");

        ArrayList<ImagenPerfil> listaPfps = ipc.traerListaImagenesPredeterminadas();
        List<Map<String, String>> imagenesBase64 = new ArrayList<>();

        if (listaPfps != null) {
            for (ImagenPerfil imgPfp : listaPfps) {
                String base64Image = Base64.getEncoder().encodeToString(imgPfp.getData());
                Map<String, String> imageData = new HashMap<>();
                imageData.put("base64", base64Image);
                imageData.put("filename", imgPfp.getNomArchivo());
                imagenesBase64.add(imageData);
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonDataFiltrada = gson.toJson(imagenesBase64);
        response.getWriter().write(jsonDataFiltrada);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Upload any personalized profile pictures or set any predetermined pfp*/
        response.setContentType("application/json;charset=UTF-8");
        HttpSession sesion = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");

        String nomOriginalAvatar = usuarioBean.getNombreAvatar();
        int idUsuarioActual = usuarioBean.getId();
        String tipoCambioPre = request.getParameter("modalAvatarPre"); //value = AvatarPredeterminado
        String tipoCambioPer = request.getParameter("modalAvatarPer"); //value = AvatarPersonalizado
        String opcion = tipoCambioPre != null ? "AvatarPredeterminado" : (tipoCambioPer != null ? "AvatarPersonalizado" : "");

        switch (opcion) {
            case "AvatarPredeterminado":
                ipc.manejarAvatarPredeterminado(request, response, nomOriginalAvatar, idUsuarioActual);
                break;
            case "AvatarPersonalizado":
                ipc.manejarAvatarPersonalizado(request, response, idUsuarioActual);
                break;
            default:
                SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_MODAL_OPCION);
                break;
        }
    }
}
