package com.guildgate.web.Modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import com.guildgate.web.Persistence.ControladoraPersistencia;
import com.guildgate.web.Persistence.exceptions.AvatarNotFoundException;
import com.guildgate.web.Persistence.exceptions.BannerNotFoundException;
import com.guildgate.web.Utilities.SvUtils;
import com.guildgate.web.Utilities.Enum;

/**
 *
 * @author Joan - Luis
 */
public class Controladora {

    ControladoraPersistencia cors = new ControladoraPersistencia();

    //Metodos para gremios
    // Método para crear un gremio
    public void crearGremio(String nombre, String desc, int region, int mundo, String usuario) throws IOException {
        ArrayList<Region> listaRegion = cors.traerListaRegiones();
        ArrayList<Mundos> listaMundos = cors.traerListaMundos();
        ArrayList<Roles> listaRoles = cors.traerListaRoles();

        //Buscar la región
        Optional<Region> optRegion = SvUtils.findRegionById(region, listaRegion);
        if (!optRegion.isPresent()) {
            System.out.println("Region no encontrada");
            return;
        }

        //Buscar el mundo
        Optional<Mundos> optMundo = SvUtils.findMundoById(mundo, listaMundos);
        if (!optMundo.isPresent()) {
            System.out.println("Mundo no encontrado");
            return;
        }

        //Crear el gremio
        Gremio gre = new Gremio();
        gre.setNombre(nombre);
        gre.setDescripcion(desc);
        gre.setCantidad(30);
        gre.setGremioregion(optRegion.get());
        gre.setGremiomundo(optMundo.get());

        String nombreImgGuild = "DefaultGuildPic.png";
        String path = "D:/USUARIO DATOS/Documents/NetBeansProjects/GremiosYRaids/src/main/webapp/imagenes/Gremio/GuildPics/";

        AvatarGremio img = SvUtils.obtenerOCrearAvatarGremioConPath(cors, nombreImgGuild, path, Enum.OrigenArchivo.PREDETERMINADA);
        gre.setImg(img);

        String nombreFondoGuild = "DefaultGuildBackground.jpg";
        String pathFondo = "D:/USUARIO DATOS/Pictures/GremiosProject/";

        FondoGremio imgF = SvUtils.obtenerOCrearFondoGremioConPath(cors, nombreFondoGuild, pathFondo, Enum.OrigenArchivo.PREDETERMINADA);
        gre.setImgF(imgF);

        int idGremio = cors.crearGremio(gre);

        //Buscar el usuario
        Optional<Usuarios> optUsuario = SvUtils.findUserByUsername(usuario, cors.traerListaUsuarios());
        if (!optUsuario.isPresent()) {
            System.out.println("Usuario no encontrado");
            return;
        }

        //Asignación del rol dueño al creador del gremio
        Optional<Roles> optRol = SvUtils.findRolById(1, listaRoles);
        if (!optRol.isPresent()) {
            System.out.println("Rol no encontrado");
            return;
        }

        Usuarios user = optUsuario.get();
        user.setGremiousuario(cors.traerGremio(idGremio));
        cors.editarUsuario(user);

        UsuarioRoles ur = new UsuarioRoles();
        ur.setGremiouserrol(gre);
        ur.setUsuariouserrol(user);
        ur.setRoluserrol(optRol.get());
        ur.setFechaAsignacion(LocalDate.now());
        cors.crearConexionJR(ur);
    }

    public Gremio traerGremio(int id) {
        return cors.traerGremio(id);
    }

    public ArrayList<Gremio> traerListaGremios() {
        return cors.traerListaGremios();
    }

    //Metodos Raid - DESDE SQL
    //Metodos Bosses - DESDE SQL

    //ImagenPerfil
    public ImagenPerfil traerImagenPerfil(long id) {
        return cors.obtenerImagen(id);
    }

    //ImagenBanner
    public ImagenBanner traerImagenBanner(long id) {
        return cors.obtenerBanner(id);
    }

    //AvatarGremio
    public AvatarGremio traerAvatarGremio(long id) {
        return cors.obtenerAvatarGremio(id);
    }

    //FondoGremio
    public FondoGremio traerFondoGremio(long id) {
        return cors.obtenerFondoGremio(id);
    }

    //Metodos Participaciones
    //Metodos Participaciones Extra
    //Metodos Ronda - DESDE SQL
    //Metodos Roles -DESDE SQL PARCIALMENTE
    public Roles traerRol(int id) {
        return cors.traerRol(id);
    }

    public ArrayList<Roles> traerListaRoles() {
        return cors.traerListaRoles();
    }

    //Metodos UsuarioRoles
    //Imagenes de foto de perfil
    public void cambioAvatarUsuario(String nomArchivo, int usuario) throws AvatarNotFoundException, Exception {
        Usuarios user = cors.traerUsuario(usuario);

        try {
            ImagenPerfil img = cors.buscarImagenPorNombre(nomArchivo);
            if (img == null) {
                throw new AvatarNotFoundException("¡La imagen del avatar no fue encontrada!");
            }
            cors.editarNuevoAvatar(user.getId(), img);
        } catch (AvatarNotFoundException e) {
            throw new Exception("Error al cambiar el avatar del usuario", e);
        }
    }

    public void cambioAvatarPersonalizadoUsuario(int usuario, String nomArchivo, String tipoArchivo, long tamanoArchivo, byte[] archivoByte) throws IOException {
        if (usuario <= 0 || SvUtils.isNullOrEmpty(nomArchivo)
                || SvUtils.isNullOrEmpty(tipoArchivo) || (tamanoArchivo == 0)) {
            throw new IllegalArgumentException("Argumentos inválidos proporcionados.");
        }

        int tamanoMaximo = 10485760; // 10MB en bytes

        if (tamanoArchivo > tamanoMaximo) {
            throw new IOException("El tamaño del archivo supera el límite de 10MB.");
        }

        ImagenPerfil imgNew;
        imgNew = SvUtils.obtenerOCrearPerfilSinPath(cors, nomArchivo, tipoArchivo, archivoByte, Enum.OrigenArchivo.USUARIO);

        cors.editarNuevoAvatar(usuario, imgNew);
    }

    public void cambioBannerUsuario(String nomArchivo, int usuario) throws BannerNotFoundException, Exception {
        Usuarios user = cors.traerUsuario(usuario);

        try {
            ImagenBanner img = cors.buscarBannerPorNombre(nomArchivo);
            if (img == null) {
                throw new BannerNotFoundException("¡La imagen del banner no fue encontrada!");
            }
            cors.editarNuevoBanner(user.getId(), img);
        } catch (BannerNotFoundException e) {
            throw new Exception("Error al cambiar el banner del usuario", e);
        }
    }

    public void cambioBannerPersonalizadoUsuario(int usuario, String nomArchivo, String tipoArchivo, long tamanoArchivo, byte[] byteArchivo) throws IOException {
        if (usuario <= 0 || SvUtils.isNullOrEmpty(nomArchivo)
                || SvUtils.isNullOrEmpty(tipoArchivo) || (tamanoArchivo == 0)) {
            throw new IllegalArgumentException("Argumentos inválidos proporcionados.");
        }

        int tamanoMaximo = 10485760; // 10MB en bytes

        if (tamanoArchivo > tamanoMaximo) {
            throw new IOException("El tamaño del archivo supera el límite de 10MB.");
        }

        ImagenBanner imgBNew;
        try {
            imgBNew = SvUtils.obtenerOCrearBannerSinPath(cors, nomArchivo, tipoArchivo, byteArchivo, Enum.OrigenArchivo.USUARIO);
        } catch (IOException e) {
            throw new IOException("Error al obtener o crear la imagen del banner.", e);
        }

        cors.editarNuevoBanner(usuario, imgBNew);
    }

    public ArrayList<ImagenPerfil> traerListaImagenesPredeterminadas() {
        return cors.traerListaImagenesPredeterminadas();
    }

    //Imagenes de Banners predeterminados
    public ArrayList<ImagenBanner> traerListaBannersPredeterminadas() {
        return cors.traerListaBannersPredeterminados();
    }

    //Metodos para subir imagenes a la base de datos
    //Fotos de Perfil Predeterminadas
    public void guardarImagenesEnLotes(List<String> nombreArchivo, String ruta) {
        int maxLote = 10;
        List<String> lote = new ArrayList<>();

        for (int i = 0; i < nombreArchivo.size(); i++) {
            lote.add(nombreArchivo.get(i));

            if (lote.size() == maxLote || i == nombreArchivo.size() - 1) {
                try {
                    guardarImagenes(lote, ruta);
                } catch (IOException e) {
                    System.out.println("Error al guardar el lote de imagenes " + e.getMessage());
                }
                lote.clear();
            }
        }
    }

    public void guardarImagenes(List<String> archivos, String route) throws IOException {
        for (String filename : archivos) {
            ImagenPerfil imgPerfil = SvUtils.obtenerOCrearImagenPerfilConPath(
                    cors,
                    filename,
                    route,
                    Enum.OrigenArchivo.PREDETERMINADA
            );
            System.out.println("Imagen guardada: " + filename);
        }
    }

    public void todasLasImagenes() {
        List<String> nombresArchivos = Arrays.asList(
                "Alef.png", "Ameris.png", "Andras.png", "Android2.png", "Android99.png",
                "AndroidAA72.png", "Angie.png", "Ara.png", "Arabelle.png", "AscendedKnightFemale.png",
                "AscendedKnigthMale.png", "Bari.png", "BeachAmy.png", "BeachShapira.png",
                "BeachSohee.png", "Beth.png", "Bianca.png", "Camilla.png", "Carol.png",
                "Chrome.png", "ChunRyeo.png", "Clara.png", "Claude.png", "Croselle.png",
                "Eleanor.png", "Erina.png", "Estel.png", "Eugene.png", "Eunha.png",
                "Gabriel.png", "GaramEspecial.png", "GaramNormal.png", "Hana.png",
                "IdolCaptainEva.png", "Kaden.png"
        );

        String routeGlobal = "D:/USUARIO DATOS/Pictures/ProyectoGremiosUploads/GT-AvatarIcono/";

        guardarImagenesEnLotes(nombresArchivos, routeGlobal);
    }

    // Métodos para subir banners del usuario predeterminados
    public void guardarBannersEnLotes(List<String> nombreArchivos, String ruta) {
        int maxBLotes = 10;
        List<String> lote = new ArrayList<>();

        for (int i = 0; i < nombreArchivos.size(); i++) {
            lote.add(nombreArchivos.get(i));

            if (lote.size() == maxBLotes || i == nombreArchivos.size() - 1) {
                try {
                    guardarBanners(lote, ruta);
                } catch (IOException e) {
                    System.out.println("Error al guardar el lote de banners: " + e.getMessage());
                }
                lote.clear();
            }
        }
    }

    public void guardarBanners(List<String> nombreArchivos, String ruta) throws IOException {
        for (String filename : nombreArchivos) {
            ImagenBanner imgBanner = SvUtils.obtenerOCrearImagenBannerConPath(
                    cors,
                    filename,
                    ruta,
                    Enum.OrigenArchivo.PREDETERMINADA
            );
            System.out.println("Banner guardado: " + filename);
        }
    }

    public void todosLosBanners() {
        List<String> nombreBanners = Arrays.asList(
                "BannerOption1.jpg", "BannerOption2.jpg", "BannerOption3.jpg", "BannerOption4.jpg", "BannerOption5.jpg",
                "BannerOption6.jpg", "BannerOption7.jpg", "BannerOption8.jpg", "BannerOption9.jpg", "BannerOption10.jpg",
                "BannerOption11.jpg", "BannerOption12.jpg", "BannerOption13.jpg", "BannerOption14.jpg", "BannerOption15.jpg"
        );

        String rutaGlobal = "D:/USUARIO DATOS/Pictures/ProyectoGremiosUploads/BannerUsuario/";

        guardarBannersEnLotes(nombreBanners, rutaGlobal);
    }

    //Métodos para subir avatares para gremios
    public void guardarAvataresGremioEnLotes(List<String> nombreArchivos, String ruta) throws IOException {
        int maxAGLotes = 10;
        List<String> lote = new ArrayList<>();

        for (int i = 0; i < nombreArchivos.size(); i++) {
            lote.add(nombreArchivos.get(i));

            if (lote.size() == maxAGLotes || i == nombreArchivos.size() - 1) {
                try {
                    guardarAvataresGremio(lote, ruta);
                } catch (IOException e) {
                    throw new IOException("Error al guardar el lote de avatares gremio: " + e.getMessage());
                }
            }
        }
    }

    public void guardarAvataresGremio(List<String> nombreArchivos, String ruta) throws IOException {
        for (String filename : nombreArchivos) {
            AvatarGremio avatar = SvUtils.obtenerOCrearAvatarGremioConPath(
                    cors,
                    filename,
                    ruta,
                    Enum.OrigenArchivo.PREDETERMINADA
            );
            System.out.println("Avatar para el gremio guardado: " + filename);
        }
    }

    public void todosLosAvataresGremio() throws IOException {
        List<String> nombreAvataresGremio = Arrays.asList(
                "Akayuki.jpg", "Kai.png", "Kamael.png", "Lahn.png", "Lapice.png",
                "LifeguardYuze.png", "Lilith.png", "Lucy.png", "Lupina.png", "Lynn.png",
                "Marina.png", "Mayreel.png", "Miya.png", "Morrian.png", "Nari.png",
                "Noxia.png", "Odile.png", "Oghma.png", "Orca.png", "PandaTrio.png",
                "Parvati.png", "Plitvice.png", "Priscilla.png", "Pymon.png", "Rey.png",
                "Rosetta.png", "Rue.png", "Scintilla.png", "Sia.png", "Sumire.png",
                "SummerLoraine.png", "Tinia.png", "Toga.png", "Valencia.png", "Veronica.png",
                "Vishuvac.png", "WhiteSnow.png", "WinLing.png", "Yun.png", "Yuna.png"
        );

        String rutaGlobal = "D:/USUARIO DATOS/Pictures/ProyectoGremiosUploads/GT-AvatarGremio/";

        try {
            guardarAvataresGremioEnLotes(nombreAvataresGremio, rutaGlobal);
        } catch (IOException e) {
            throw new IOException("Error al localizar los avatares gremio: " + e.getMessage());
        }
    }

    //Métodos para subir fondos del gremio
    public void guardarFondosGremioEnLotes(List<String> nombreArchivos, String ruta) throws IOException {
        int maxFGLotes = 10;
        List<String> lote = new ArrayList<>();

        for (int i = 0; i < nombreArchivos.size(); i++) {
            lote.add(nombreArchivos.get(i));

            if (lote.size() == maxFGLotes || i == nombreArchivos.size() - 1) {
                try {
                    guardarFondosGremio(lote, ruta);
                } catch (IOException e) {
                    throw new IOException("Error al guardar el lote de fondos gremio: " + e.getMessage());
                }
            }
        }
    }

    public void guardarFondosGremio(List<String> nombreArchivos, String ruta) throws IOException {
        for (String filename : nombreArchivos) {
            FondoGremio fondo = SvUtils.obtenerOCrearFondoGremioConPath(
                    cors,
                    filename,
                    ruta,
                    Enum.OrigenArchivo.PREDETERMINADA
            );
            System.out.println("Fondo para el gremio guardado: " + filename);
        }
    }

    public void todosLosFondosGremio() throws IOException {
        List<String> nombreFondosGremio = Arrays.asList(
                "FondoOption1.jpg", "FondoOption2.jpg", "FondoOption3.jpg", "FondoOption4.jpg", "FondoOption5.jpg",
                "FondoOption6.jpg", "FondoOption7.jpg", "FondoOption8.jpg", "FondoOption9.jpg", "FondoOption10.jpg",
                "FondoOption11.jpg", "FondoOption12.jpg", "FondoOption13.jpg", "FondoOption14.jpg", "FondoOption15.jpg"
        );

        String rutaGlobal = "D:/USUARIO DATOS/Pictures/ProyectoGremiosUploads/FondoGremios/";

        try {
            guardarFondosGremioEnLotes(nombreFondosGremio, rutaGlobal);
        } catch (IOException e) {
            throw new IOException("Error al localizar los fondos del gremio: " + e.getMessage());
        }
    }
}
