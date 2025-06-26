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
import com.guildgate.web.Modelo.Controladora;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Bean.UsuarioBean;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;

/**
 *
 * @author Juan - Luis
 */
@WebServlet(name = "SvBanner", urlPatterns = {"/SvBanner"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
                 maxFileSize = 1024 * 1024 * 10,  // 10 MB
                 maxRequestSize = 1024 * 1024 * 15) // 15 MB
public class SvBanner extends HttpServlet {

    Controladora control = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Nothing*/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Bring all predetermined banners*/
        response.setContentType("application/json;charset=UTF-8");

        control = new Controladora();
        ArrayList<ImagenBanner> listaBanners = control.traerListaBannersPredeterminadas();
        List<Map<String, String>> bannersBase64 = new ArrayList<>();

        if (listaBanners != null) {
            for (ImagenBanner imgB : listaBanners) {
                String base64Banner = Base64.getEncoder().encodeToString(imgB.getData());
                Map<String, String> imageData = new HashMap<>();
                imageData.put("bannerBase64", base64Banner);
                imageData.put("bannerName", imgB.getNomArchivo());
                bannersBase64.add(imageData);
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonBannersFiltrados = gson.toJson(bannersBase64);
        response.getWriter().write(jsonBannersFiltrados);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*Upload any personalized banners or set a new Predetermined banner for the user*/
        response.setContentType("application/json;charset=UTF-8");
        HttpSession sesion = request.getSession(false);
        UsuarioBean usuarioBean = (UsuarioBean) sesion.getAttribute("usuarioBean");
        
        control = new Controladora();
        
        String nomOriginalBanner = usuarioBean.getNombreBanner();
        int idUsuarioActual = usuarioBean.getId();
        String tipoCambioPre = request.getParameter("modalBannerPre");
        String tipoCambioPer = request.getParameter("modalBannerPer");
        String opcion = tipoCambioPre != null ? "BannerPredeterminado" : (tipoCambioPer != null ? "BannerPersonalizado" : "");
        
        switch(opcion) {
            case "BannerPredeterminado":
                SvUtils.manejarBannerPredeterminado(request, response, nomOriginalBanner, idUsuarioActual, control);
                break;
            case "BannerPersonalizado":
                SvUtils.manejarBannerPersonalizado(request, response, idUsuarioActual, control);
                break;
            default:
                SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_MODAL_OPCION);
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
