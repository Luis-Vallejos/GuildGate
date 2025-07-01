package com.guildgate.web.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Modelo.Bosses;
import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Modelo.UsuarioRoles;
import com.guildgate.web.Modelo.Usuarios;
import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Modelo.Raid;
import com.guildgate.web.Modelo.Region;
import com.guildgate.web.Modelo.Roles;
import com.guildgate.web.Persistence.exceptions.IllegalOrphanException;
import com.guildgate.web.Persistence.exceptions.NonexistentEntityException;

/**
 *
 * @author Juan - Luis
 */
public class ControladoraPersistencia {

    GremioJpaController gjc = new GremioJpaController();
    RaidJpaController rajc = new RaidJpaController();
    UsuariosJpaController ujc = new UsuariosJpaController();
    BossesJpaController bjc = new BossesJpaController();
    //ParticipacionesJpaController pjc = new ParticipacionesJpaController();
    //ParticipacionesExtraJpaController pejc = new ParticipacionesExtraJpaController();
    //RondaJpaController rojc = new RondaJpaController();
    RegionJpaController rejc = new RegionJpaController();
    MundosJpaController mjc = new MundosJpaController();
    RolesJpaController roljc = new RolesJpaController();
    UsuarioRolesJpaController urjc = new UsuarioRolesJpaController();
    ImagenPerfilJpaController ijc = new ImagenPerfilJpaController();
    ImagenBannerJpaController ibjc = new ImagenBannerJpaController();
    AvatarGremioJpaController agjc = new AvatarGremioJpaController();
    FondoGremioJpaController fgjc = new FondoGremioJpaController();

    //Metodos para Gremio
    public int crearGremio(Gremio gre) {
        gjc.create(gre);
        return gre.getId();
    }

    public void editarGremio(Gremio gre) {
        try {
            gjc.edit(gre);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarGremio(Integer id) {
        try {
            gjc.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public long traerCantidadUsuarios(int gremioId) {
        return ujc.contarUsuariosPorGremio(gremioId);
    }

    public Gremio traerGremio(int id) {
        return gjc.findGremio(id);
    }

    public ArrayList<Gremio> traerListaGremios() {
        List<Gremio> lista = gjc.findGremioEntities();
        ArrayList<Gremio> listaGremios = new ArrayList<>(lista);
        return listaGremios;
    }

    //Metodos para Raid
    public void crearRaid(Raid rad) {
        rajc.create(rad);
    }

    public void editarRaid(Raid rad) {
        try {
            rajc.edit(rad);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminaRaid(Integer id) {
        try {
            rajc.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Raid traerRaid(int id) {
        return rajc.findRaid(id);
    }

    public ArrayList<Raid> traerListaRaids() {
        List<Raid> lista = rajc.findRaidEntities();
        ArrayList<Raid> listaRaids = new ArrayList<>(lista);
        return listaRaids;
    }

    //Metodos para Usuarios
    public void crearUsuario(Usuarios user) {
        ujc.create(user);
    }

    public void editarUsuario(Usuarios user) {
        try {
            ujc.edit(user);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarInformacionBasica(int id, String nombre, String correo, String contrasena, String bio) {
        try {
            ujc.editBasicInfo(id, nombre, correo, contrasena, bio);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarUsuario(Integer id) {
        try {
            ujc.destroy(id);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Metodos para elegir un nuevo Avatar o Banner para el usuario ya sea Predeterminado o Personalizado
    public void editarNuevoAvatar(int id, ImagenPerfil img) {
        try {
            ujc.editAvatarUser(id, img);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarNuevoBanner(int id, ImagenBanner img) {
        try {
            ujc.editBannerUser(id, img);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuarios traerUsuario(int id) {
        return ujc.findUsuarios(id);
    }

    public ArrayList<Usuarios> traerListaUsuarios() {
        List<Usuarios> lista = ujc.findUsuariosEntities();
        ArrayList<Usuarios> listaUsuarios = new ArrayList<>(lista);
        return listaUsuarios;
    }

    //Metodos para Bosses - DESDE SQL PARCIALMENTE
    public Bosses traerBoss(int id) {
        return bjc.findBosses(id);
    }

    public ArrayList<Bosses> traerListaBosses() {
        List<Bosses> lista = bjc.findBossesEntities();
        ArrayList<Bosses> listaBosses = new ArrayList<>(lista);
        return listaBosses;
    }

    //Metodos para Participaciones
    //Metodos para Participaciones Extra
    //Metodos para Ronda - DESDE SQL
    //Metodos para Region - DESDE SQL PARCIALMENTE
    public Region traerRegion(int id) {
        return rejc.findRegion(id);
    }

    public ArrayList<Region> traerListaRegiones() {
        List<Region> lista = rejc.findRegionEntities();
        ArrayList<Region> listaRegiones = new ArrayList<>(lista);
        return listaRegiones;
    }

    //Metodos para Mundo - DESDE SQL PARCIALMENTE
    public Mundos traerMundo(int id) {
        return mjc.findMundos(id);
    }

    public ArrayList<Mundos> traerListaMundos() {
        List<Mundos> lista = mjc.findMundosEntities();
        ArrayList<Mundos> listaMundos = new ArrayList<>(lista);
        return listaMundos;
    }

    //Metodos para Roles
    public void crearRol(Roles rol) {
        roljc.create(rol);
    }

    public void editarRol(Roles rol) {
        try {
            roljc.edit(rol);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarRol(Integer id) {
        try {
            roljc.destroy(id);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Roles traerRol(int id) {
        return roljc.findRoles(id);
    }

    public ArrayList<Roles> traerListaRoles() {
        List<Roles> lista = roljc.findRolesEntities();
        ArrayList<Roles> listaRoles = new ArrayList<>(lista);
        return listaRoles;
    }

    //Metodos para jugador Roles
    public void crearConexionJR(UsuarioRoles jr) {
        urjc.create(jr);
    }

    public UsuarioRoles traerJRoles(long id) {
        return urjc.findUsuarioRoles(id);
    }

    public ArrayList<UsuarioRoles> traerListaJRoles() {
        List<UsuarioRoles> lista = urjc.findUsuarioRolesEntities();
        ArrayList<UsuarioRoles> listaJRoles = new ArrayList<>(lista);
        return listaJRoles;
    }

    //Metodos para ImagenPerfil
    public void guardarImagen(ImagenPerfil img) {
        ijc.create(img);
    }

    public void editarImagen(ImagenPerfil img) throws Exception {
        ijc.edit(img);
    }

    public ImagenPerfil obtenerImagen(long id) {
        return ijc.findImagenPerfil(id);
    }

    public ImagenPerfil buscarImagenPorNombre(String nombreArchivo) {
        return ijc.findImagenByNombre(nombreArchivo);
    }

    public ArrayList<ImagenPerfil> traerListaImagenes() {
        List<ImagenPerfil> lista = ijc.findImagenPerfilEntities();
        ArrayList<ImagenPerfil> listaImagenes = new ArrayList<>(lista);
        return listaImagenes;
    }

    public ArrayList<ImagenPerfil> traerListaImagenesPredeterminadas() {
        List<ImagenPerfil> lista = ijc.getPredeterminedPfps();
        ArrayList<ImagenPerfil> listaImagenesPredeterminadas = new ArrayList<>(lista);
        return listaImagenesPredeterminadas;
    }

    //Métodos para ImagenBanner
    public void guardarBanner(ImagenBanner imgB) {
        ibjc.create(imgB);
    }

    public void editarImagen(ImagenBanner imgB) throws Exception {
        ibjc.edit(imgB);
    }

    public ImagenBanner obtenerBanner(long id) {
        return ibjc.findImagenBanner(id);
    }

    public ImagenBanner buscarBannerPorNombre(String nombreBanner) {
        return ibjc.findBannerByNombre(nombreBanner);
    }

    public ArrayList<ImagenBanner> traerListaBanner() {
        List<ImagenBanner> lista = ibjc.findImagenBannerEntities();
        ArrayList<ImagenBanner> listaBanners = new ArrayList<>(lista);
        return listaBanners;
    }

    public ArrayList<ImagenBanner> traerListaBannersPredeterminados() {
        List<ImagenBanner> lista = ibjc.getPredeterminedBanners();
        ArrayList<ImagenBanner> listaBannersPredeterminados = new ArrayList<>(lista);
        return listaBannersPredeterminados;
    }

    //Metodos para avatarGremio
    public void guardarAvatarGremio(AvatarGremio ava) {
        agjc.create(ava);
    }

    public AvatarGremio obtenerAvatarGremio(long id) {
        return agjc.findAvatarGremio(id);
    }

    public AvatarGremio buscarAvatarGremioPorNombre(String nombreArchivo) {
        return agjc.findImagenByNombre(nombreArchivo);
    }

    public ArrayList<AvatarGremio> traerListaAvatarGremio() {
        List<AvatarGremio> lista = agjc.findAvatarGremioEntities();
        ArrayList<AvatarGremio> listaAvataresGremio = new ArrayList<>(lista);
        return listaAvataresGremio;
    }

    public ArrayList<AvatarGremio> traerAvataresGremioPredeterminados() {
        List<AvatarGremio> lista = agjc.getPredeterminedAvatarGremio();
        ArrayList<AvatarGremio> listaAvataresGremioPredeterminado = new ArrayList<>(lista);
        return listaAvataresGremioPredeterminado;
    }

    //Metodo para el fondo del Gremio
    public void guardarFondoGremio(FondoGremio fon) {
        fgjc.create(fon);
    }

    public FondoGremio obtenerFondoGremio(long id) {
        return fgjc.findFondoGremio(id);
    }

    public FondoGremio buscarFondoGremioPorNombre(String nombreArchivo) {
        return fgjc.findImagenByNombre(nombreArchivo);
    }

    public ArrayList<FondoGremio> traerListaFondoGremio() {
        List<FondoGremio> lista = fgjc.findFondoGremioEntities();
        ArrayList<FondoGremio> listaFondosGremio = new ArrayList<>(lista);
        return listaFondosGremio;
    }

    public ArrayList<FondoGremio> traerFondosGremioPredeterminado() {
        List<FondoGremio> lista = fgjc.getPredeterminedFondoGremio();
        ArrayList<FondoGremio> listaFondosGremioPredeterminado = new ArrayList<>(lista);
        return listaFondosGremioPredeterminado;
    }
}
