package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Service.BannerService;
import com.guildgate.web.Service.PerfilService;
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
public class UsuarioController {

    @Inject
    UsuarioService us;

    @Inject
    PerfilService ps;

    @Inject
    BannerService bs;

    public UsuarioController() {
        this.us = new UsuarioService();
        this.ps = new PerfilService();
        this.bs = new BannerService();
    }

    //Metodos Usuarios
    public void anadirUsuario(String nomUser, String email, String contra) throws IOException {
        Usuarios user = new Usuarios();

        user.setNombre(nomUser);
        user.setNivel(1);
        user.setCorreo(email);
        user.setContrasena(contra);

        ImagenPerfil imgPerfil = SvUtils.obtenerOCrearImagenPerfilConPath(
                ps,
                "DefaultUserPic.png",
                "D:/USUARIO DATOS/Documents/NetBeansProjects/GremiosYRaids/src/main/webapp/imagenes/Usuario/UserPics/",
                com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA
        );
        user.setImg(imgPerfil);

        ImagenBanner imgBanner = SvUtils.obtenerOCrearImagenBannerConPath(
                bs,
                "DefaultUserBanner.jpg",
                "D:/USUARIO DATOS/Documents/NetBeansProjects/GremiosYRaids/src/main/webapp/imagenes/Usuario/UserBannerPics/",
                com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA
        );
        user.setImgB(imgBanner);

        us.create(user);
    }

    public void editarUsuario(String nickname, String correo, String contra, String bio, ArrayList<Usuarios> listaUsuarios, String nombreOriginal) {
        if (nickname == null || nickname.isEmpty() || correo == null || correo.isEmpty()
                || contra == null || contra.isEmpty() || bio == null || bio.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios.");
        }

        Optional<Usuarios> optUser = SvUtils.findUserByUsername(nombreOriginal, listaUsuarios);

        if (!optUser.isPresent()) {
            throw new IllegalArgumentException("Error buscando al usuario.");
        }

        Usuarios j = optUser.get();

        try {
            us.editarInformacionBasica(j.getId(), nickname, correo, contra, bio);
        } catch (Exception e) {
            throw new RuntimeException("Error al editar el usuario", e);
        }
    }

    public void asociarGremioaUsuario(String usuario, int gremioId, String rolNom) {
        ArrayList<Usuarios> listaUsuarios = Optional.ofNullable(us.findAll())
                .orElseThrow(() -> new IllegalStateException("Hubo un error las listas están vacías!!"));
        ArrayList<Gremio> listaGremios = Optional.ofNullable(us.traerListaGremios())
                .orElseThrow(() -> new IllegalStateException("Hubo un error las listas están vacías!!"));
        ArrayList<Roles> listaRoles = Optional.ofNullable(us.traerListaRoles())
                .orElseThrow(() -> new IllegalStateException("Hubo un error las listas están vacías!!"));

        Optional<Usuarios> optUsuario = listaUsuarios.stream()
                .filter(j -> usuario.equalsIgnoreCase(j.getNombre()))
                .findFirst();

        optUsuario.ifPresentOrElse(user -> {
            Optional<Gremio> optGremio = listaGremios.stream()
                    .filter(g -> gremioId == g.getId())
                    .findFirst();

            optGremio.ifPresentOrElse(gre -> {
                Optional<Roles> optRoles = listaRoles.stream()
                        .filter(r -> rolNom.equalsIgnoreCase(r.getNombre()))
                        .findFirst();

                optRoles.ifPresentOrElse(rol -> {
                    user.setGremiousuario(gre);
                    us.edit(user);

                    UsuarioRoles ur = new UsuarioRoles();
                    ur.setGremiouserrol(gre);
                    ur.setUsuariouserrol(user);
                    ur.setRoluserrol(rol);
                    ur.setFechaAsignacion(LocalDate.now());
                    us.crearConexionJR(ur);

                    System.out.println("Usuario asociado correctamente al gremio y rol asignado.");
                }, () -> {
                    System.out.println("Error: Rol no encontrado");
                });
            }, () -> {
                System.out.println("Error: Gremio no encontrado");
            });
        }, () -> {
            System.out.println("Error: Usuario no existe!");
        });
    }

    public Usuarios traerUsuario(int id) {
        return us.findById(id);
    }

    public ArrayList<Usuarios> traerListaUsuarios() {
        return us.findAll();
    }

    public long contarUsuarioPorGremio(int gremioId) {
        return us.traerCantidadUsuarios(gremioId);
    }
}
