package com.guildgate.web.Service;

import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Service.generic.ICrudService;

/**
 *
 * @author Juan - Luis
 */
public interface IBannerService extends ICrudService<ImagenBanner, Long> {

    ImagenBanner buscarBannerPorNombre(String nombreBanner);
}
