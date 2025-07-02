package com.guildgate.web.Service;

import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Service.generic.ICrudService;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public interface IBannerService extends ICrudService<ImagenBanner, Long> {

    ImagenBanner buscarBannerPorNombre(String nombreBanner);

    void editarNuevoBanner(int id, ImagenBanner img);
    
    ArrayList<ImagenBanner> traerListaBannersPredeterminados();

}
