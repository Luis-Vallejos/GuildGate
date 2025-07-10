package com.guildgate.web.Service.mapper;

import com.guildgate.web.Bean.AvatarBean;
import com.guildgate.web.Modelo.ImagenPerfil;
import com.guildgate.web.Service.impl.PerfilServiceImpl;
import com.guildgate.web.Utilities.SvUtils;

/**
 *
 * @author Juan - Luis
 */
public class AvatarBeanMapper {

    public AvatarBean toBean(ImagenPerfil img, PerfilServiceImpl ps) {
        String dataUrl = null;
        if (img != null) {
            dataUrl = SvUtils.getImageDataUrl(img, ps);
        }
        return AvatarBean.builder()
                .id(img != null ? img.getId() : null)
                .nombreAvatar(img != null ? img.getNomArchivo() : null)
                .avatarUsuario(dataUrl)
                .build();
    }
}
