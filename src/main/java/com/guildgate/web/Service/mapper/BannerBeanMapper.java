package com.guildgate.web.Service.mapper;

import com.guildgate.web.Bean.BannerBean;
import com.guildgate.web.Modelo.ImagenBanner;
import com.guildgate.web.Service.impl.BannerServiceImpl;
import com.guildgate.web.Utilities.SvUtils;

/**
 *
 * @author Juan - Luis
 */
public class BannerBeanMapper {

    public BannerBean toBean(ImagenBanner imgB, BannerServiceImpl bs) {
        String url = null;
        if (imgB != null) {
            url = SvUtils.getBannerDataUrl(imgB, bs);
        }
        return BannerBean.builder()
                .id(imgB != null ? imgB.getId() : null)
                .nombreBanner(imgB != null ? imgB.getNomArchivo() : null)
                .bannerUsuario(url)
                .posicionXBanner(imgB != null ? imgB.getPosicionX() : null)
                .posicionYBanner(imgB != null ? imgB.getPosicionY() : null)
                .build();
    }
}
