package com.guildgate.web.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.guildgate.web.Bean.RolBean;
import com.guildgate.web.Bean.SessionUserBean;
import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Service.impl.AvatarGremioServiceImpl;
import com.guildgate.web.Service.impl.FondoGremioServiceImpl;
import com.guildgate.web.Service.impl.GremioServiceImpl;
import com.guildgate.web.Service.impl.MundosServiceImpl;
import com.guildgate.web.Service.impl.RegionServiceImpl;
import com.guildgate.web.Service.impl.RoleServiceImpl;
import com.guildgate.web.Service.impl.UsuarioRolesServiceImpl;
import com.guildgate.web.Service.impl.UsuarioServiceImpl;
import com.guildgate.web.Service.mapper.RolBeanMapper;
import com.guildgate.web.Utilities.Mensajes;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Juan - Luis
 */
public class GremioController {

    @Inject
    RegionServiceImpl res;
    @Inject
    MundosServiceImpl ms;
    @Inject
    RoleServiceImpl rs;
    @Inject
    GremioServiceImpl gs;
    @Inject
    UsuarioServiceImpl us;
    @Inject
    UsuarioRolesServiceImpl urs;
    @Inject
    AvatarGremioServiceImpl ags;
    @Inject
    FondoGremioServiceImpl fgs;

    // Mappers
    @Inject
    RolBeanMapper rbm;

    public GremioController() {
        this.res = new RegionServiceImpl();
        this.ms = new MundosServiceImpl();
        this.rs = new RoleServiceImpl();
        this.gs = new GremioServiceImpl();
        this.us = new UsuarioServiceImpl();
        this.urs = new UsuarioRolesServiceImpl();
        this.ags = new AvatarGremioServiceImpl();
        this.fgs = new FondoGremioServiceImpl();
        this.rbm = new RolBeanMapper();
    }

    //Metodos para gremios
    // Método para crear un gremio
    public void crearGremio(String nombre, String desc, int region, int mundo, String usuario) throws IOException {
        ArrayList<Region> listaRegion = res.findAll();
        ArrayList<Mundos> listaMundos = ms.findAll();
        ArrayList<Roles> listaRoles = rs.findAll();

        // Validar región
        Optional<Region> optRegion = SvUtils.findRegionById(region, listaRegion);
        if (!optRegion.isPresent()) {
            System.out.println("Región no encontrada");
            return;
        }

        // Validar mundo
        Optional<Mundos> optMundo = SvUtils.findMundoById(mundo, listaMundos);
        if (!optMundo.isPresent()) {
            System.out.println("Mundo no encontrado");
            return;
        }

        // Validar usuario
        Optional<Usuarios> optUsuario = SvUtils.findUserByUsername(usuario, us.findAll());
        if (!optUsuario.isPresent()) {
            System.out.println("Usuario no encontrado");
            return;
        }

        // Validar rol
        Optional<Roles> optRol = SvUtils.findRolById(1, listaRoles);
        if (!optRol.isPresent()) {
            System.out.println("Rol no encontrado");
            return;
        }

        // Crear gremio
        Gremio gre = new Gremio();
        gre.setNombre(nombre);
        gre.setDescripcion(desc);
        gre.setCantidad(30);
        gre.setGremioregion(optRegion.get());
        gre.setGremiomundo(optMundo.get());

        // Avatar por defecto
        String nombreImgGuild = "DefaultGuildPic.png";
        String path = "D:/USUARIO DATOS/Documents/NetBeansProjects/GremiosYRaids/src/main/webapp/imagenes/Gremio/GuildPics/";
        AvatarGremio img = SvUtils.obtenerOCrearAvatarGremioConPath(ags, nombreImgGuild, path, com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA);
        gre.setImg(img);

        // Fondo por defecto
        String nombreFondoGuild = "DefaultGuildBackground.jpg";
        String pathFondo = "D:/USUARIO DATOS/Pictures/GremiosProject/";
        FondoGremio imgF = SvUtils.obtenerOCrearFondoGremioConPath(fgs, nombreFondoGuild, pathFondo, com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA);
        gre.setImgF(imgF);

        // Persistir gremio
        boolean creado = gs.create(gre);
        if (!creado) {
            System.out.println("Error al crear el gremio");
            return;
        }

        // Asociar gremio al usuario
        Usuarios user = optUsuario.get();
        user.setGremiousuario(gre);
        boolean usuarioEditado = us.edit(user);
        if (!usuarioEditado) {
            System.out.println("No se pudo asociar el gremio al usuario");
            return;
        }

        // Crear relación UsuarioRoles
        UsuarioRoles ur = new UsuarioRoles();
        ur.setGremiouserrol(gre);
        ur.setUsuariouserrol(user);
        ur.setRoluserrol(optRol.get());
        ur.setFechaAsignacion(LocalDate.now());

        boolean rolAsignado = urs.create(ur);
        if (!rolAsignado) {
            System.out.println("No se pudo asignar el rol al usuario");
        }

        System.out.println("Gremio creado correctamente.");
    }

    //Metodo para asignar los variables como la imagen del gremio y el nombre a la sesión del usuario
    public void processSuccesfulGuildCreation(HttpServletResponse response, HttpServletRequest request, Gremio gre, Usuarios user) throws IOException {
        Optional<Gremio> optGremio = SvUtils.findGremioById(gre.getId(), gs.findAll());

        if (!optGremio.isPresent()) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.GREMIO_NO_ENCONTRADO);
            return;
        }

        Gremio gremio = optGremio.get();
        Optional<Roles> optRoles = SvUtils.obtenerObjetoRolPorId(1, rs.findAll());
        Roles roles = optRoles.get();

        try {
            Optional<AvatarGremio> optAvatarGremio = Optional.ofNullable(ags.findById(gre.getImg().getId()));
            Optional<FondoGremio> optFondoGremio = Optional.ofNullable(fgs.findById(gre.getImgF().getId()));

            if (!optAvatarGremio.isPresent() || !optFondoGremio.isPresent()) {
                SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
                return;
            }

            RolBean rolBean = rbm.toBean(roles);

            AvatarGremio ava = optAvatarGremio.get();
            FondoGremio fondo = optFondoGremio.get();
            String imagenBase64DataUrl = SvUtils.encodeAvatarGremioToBase64(ava);
            String fondoBase64DataUrl = SvUtils.encodeFondoGremioToBase64(fondo);
            String descripcionGremio = (gremio != null) ? gremio.getDescripcion() : "No contienen descripción...";

            HttpSession session = request.getSession(false);
            SessionUserBean sessionUserBean = (SessionUserBean) session.getAttribute("sessionUserBean");

            if (sessionUserBean == null) {
                sessionUserBean = new SessionUserBean();
                session.setAttribute("sessionUserBean", sessionUserBean);
            }

            sessionUserBean.getGremio().setNomGremioActual(gremio.getNombre());
            sessionUserBean.getGremio().setDescripcion(descripcionGremio);
            sessionUserBean.getRoles().clear();
            sessionUserBean.getRoles().add(rolBean);

            sessionUserBean.getGremio().getGv().setNomAvatarGremio(ava.getNomArchivo());
            sessionUserBean.getGremio().getGv().setAvatarGremio(imagenBase64DataUrl);
            sessionUserBean.getGremio().getGv().setNomFondoGremio(fondo.getNomArchivo());
            sessionUserBean.getGremio().getGv().setFondoGremio(fondoBase64DataUrl);
        } catch (IOException e) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
        }
    }

    //Método para procesar la lista de gremios
    public void processGuildLists(HttpServletResponse response, HttpServletRequest request, Gremio gre) throws IOException {
        Map<String, Object> gremioInfo = new HashMap<>();
        gremioInfo.put("id", gre.getId());
        gremioInfo.put("nombre", gre.getNombre());

        Optional<AvatarGremio> optAvatarGremio;
        try {
            optAvatarGremio = Optional.ofNullable(ags.findById(gre.getImg().getId()));
            if (optAvatarGremio.isPresent()) {
                AvatarGremio ava = optAvatarGremio.get();
                String imagenBase64DataUrl = SvUtils.encodeAvatarGremioToBase64(ava);
                gremioInfo.put("Imagen", imagenBase64DataUrl);
            }
        } catch (Exception e) {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
            return;
        }

        ArrayList<Map<String, Object>> gremiosFiltrados = new ArrayList<>();
        gremiosFiltrados.add(gremioInfo);

        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonGremiosFiltrados = gson.toJson(gremiosFiltrados);
        response.getWriter().write(jsonGremiosFiltrados);
    }

    //Método para traer la información de un solo gremio
    public void processGuildInfo(HttpServletResponse response, HttpServletRequest request, Gremio gre) throws IOException {
        Map<String, Object> gremioInfo = new HashMap<>();
        gremioInfo.put("id", gre.getId());
        gremioInfo.put("nombre", gre.getNombre());
        gremioInfo.put("descripcion", gre.getDescripcion());
        gremioInfo.put("miembros", us.traerCantidadUsuarios(gre.getId()));
        gremioInfo.put("limiteMiembros", gre.getCantidad());

        AvatarGremio img = gre.getImg();
        if (img != null) {
            String imagenBase64DataUrl = SvUtils.encodeAvatarGremioToBase64(img);
            gremioInfo.put("Imagen", imagenBase64DataUrl);
        } else {
            SvUtils.respondWithError(response, HttpServletResponse.SC_BAD_REQUEST, Mensajes.IMAGEN_NO_ENCONTRADA);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        Gson gson = new Gson();
        String jsonGremio = gson.toJson(gremioInfo);
        response.getWriter().write(jsonGremio);
    }

    //Método para unirse a un gremio
    public void processJoinGuild(HttpServletResponse response, HttpServletRequest request, String usuario, int gremio, String rol, UsuarioController uc) throws IOException {
        uc.asociarGremioaUsuario(usuario, gremio, rol);

        Gremio gre = gs.findById(gremio);
        String nomGremio = gre.getNombre();
        String descripcionGremio = (gre != null) ? gre.getDescripcion() : "No contienen descripción...";

        Optional<Roles> optRol = SvUtils.obtenerObjetoRolPorId(2, rs.findAll());
        Roles roles = optRol.get();

        RolBean rolBean = rbm.toBean(roles);

        HttpSession sesion = request.getSession();
        SessionUserBean sessionUserBean = (SessionUserBean) sesion.getAttribute("sessionUserBean");

        if (sessionUserBean == null) {
            sessionUserBean = new SessionUserBean();
            sesion.setAttribute("sessionUserBean", sessionUserBean);
        }

        sessionUserBean.getGremio().setNomGremioActual(nomGremio);
        sessionUserBean.getGremio().setDescripcion(descripcionGremio);
        sessionUserBean.getRoles().clear();
        sessionUserBean.getRoles().add(rolBean);

        Optional<AvatarGremio> optAvatar = Optional.ofNullable(ags.findById(gre.getImg().getId()));
        Optional<FondoGremio> optFondo = Optional.ofNullable(fgs.findById(gre.getImgF().getId()));

        if (optAvatar.isPresent() || optFondo.isPresent()) {
            AvatarGremio ava = optAvatar.get();
            FondoGremio fon = optFondo.get();

            String imagenBase64DataUrl = SvUtils.encodeAvatarGremioToBase64(ava);
            String fondoBase64DataUrl = SvUtils.encodeFondoGremioToBase64(fon);

            sessionUserBean.getGremio().getGv().setNomAvatarGremio(ava.getNomArchivo());
            sessionUserBean.getGremio().getGv().setAvatarGremio(imagenBase64DataUrl);
            sessionUserBean.getGremio().getGv().setNomFondoGremio(fon.getNomArchivo());
            sessionUserBean.getGremio().getGv().setFondoGremio(fondoBase64DataUrl);
            SvUtils.respondWithSuccess(response, HttpServletResponse.SC_OK, Mensajes.USUARIO_UNIDO_GREMIO + nomGremio + "!");
        } else {
            SvUtils.respondWithError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, Mensajes.IMAGEN_ERROR_OBTENCION);
        }
    }

    public Gremio traerGremio(int id) {
        return gs.findById(id);
    }

    public ArrayList<Gremio> traerListaGremios() {
        return gs.findAll();
    }
}
