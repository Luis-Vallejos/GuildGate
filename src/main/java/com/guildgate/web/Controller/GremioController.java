package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Service.AvatarGremioService;
import com.guildgate.web.Service.FondoGremioService;
import com.guildgate.web.Service.GremioService;
import com.guildgate.web.Service.MundosService;
import com.guildgate.web.Service.RegionService;
import com.guildgate.web.Service.RoleService;
import com.guildgate.web.Service.UsuarioRolesService;
import com.guildgate.web.Service.UsuarioService;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Juan - Luis
 */
public class GremioController {

    @Inject
    RegionService res;

    @Inject
    MundosService ms;

    @Inject
    RoleService rs;

    @Inject
    GremioService gs;

    @Inject
    UsuarioService us;

    @Inject
    UsuarioRolesService urs;

    @Inject
    AvatarGremioService ags;

    @Inject
    FondoGremioService fgs;

    public GremioController() {
        this.res = new RegionService();
        this.ms = new MundosService();
        this.rs = new RoleService();
        this.gs = new GremioService();
        this.us = new UsuarioService();
        this.urs = new UsuarioRolesService();
        this.ags = new AvatarGremioService();
        this.fgs = new FondoGremioService();
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

    public Gremio traerGremio(int id) {
        return gs.findById(id);
    }

    public ArrayList<Gremio> traerListaGremios() {
        return gs.findAll();
    }

}
