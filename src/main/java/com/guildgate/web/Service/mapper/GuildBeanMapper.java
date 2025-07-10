package com.guildgate.web.Service.mapper;

import com.guildgate.web.Bean.GuildBean;
import com.guildgate.web.Bean.GuildVisualBean;
import com.guildgate.web.Modelo.Gremio;
import com.guildgate.web.Service.impl.FondoGremioServiceImpl;
import com.guildgate.web.Service.impl.AvatarGremioServiceImpl;
import com.guildgate.web.Utilities.SvUtils;

/**
 *
 * @author Juan - Luis
 */
public class GuildBeanMapper {

    public GuildBean toBean(Gremio gremio) {
        if (gremio == null) {
            return null;
        }

        GuildVisualBean visual = GuildVisualBean.builder()
                .idAvatar(gremio.getImg() != null ? gremio.getImg().getId() : null)
                .nomAvatarGremio(gremio.getImg() != null ? gremio.getImg().getNomArchivo() : null)
                .avatarGremio(SvUtils.getAvatarGremioDataUrl(
                        gremio.getImg(), new AvatarGremioServiceImpl()))
                .idBackground(gremio.getImgF() != null ? gremio.getImgF().getId() : null)
                .nomFondoGremio(gremio.getImgF() != null ? gremio.getImgF().getNomArchivo() : null)
                .fondoGremio(SvUtils.getFondoGremioDataUrl(
                        gremio.getImgF(), new FondoGremioServiceImpl()))
                .build();

        return GuildBean.builder()
                .id(gremio.getId())
                .nomGremioActual(gremio.getNombre())
                .descripcion(gremio.getDescripcion())
                .gv(visual)
                .build();
    }
}
