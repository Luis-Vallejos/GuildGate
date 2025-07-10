package com.guildgate.web.Utilities.Procesos;

import com.guildgate.web.Modelo.AvatarGremio;
import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Service.impl.AvatarGremioServiceImpl;
import com.guildgate.web.Service.impl.BannerServiceImpl;
import com.guildgate.web.Service.impl.FondoGremioServiceImpl;
import com.guildgate.web.Service.impl.PerfilServiceImpl;
import com.guildgate.web.Utilities.SvUtils;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class SubidaImagenes {

    @Inject
    PerfilServiceImpl ps;

    @Inject
    BannerServiceImpl bs;

    @Inject
    AvatarGremioServiceImpl ags;

    @Inject
    FondoGremioServiceImpl fgs;

    public SubidaImagenes() {
    }

    public SubidaImagenes(PerfilServiceImpl ps, BannerServiceImpl bs, AvatarGremioServiceImpl ags, FondoGremioServiceImpl fgs) {
        this.ps = ps;
        this.bs = bs;
        this.ags = ags;
        this.fgs = fgs;
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
                    ps,
                    filename,
                    route,
                    com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA
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
                    bs,
                    filename,
                    ruta,
                    com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA
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
                    ags,
                    filename,
                    ruta,
                    com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA
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
                    fgs,
                    filename,
                    ruta,
                    com.guildgate.web.Utilities.Enum.OrigenArchivo.PREDETERMINADA
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
