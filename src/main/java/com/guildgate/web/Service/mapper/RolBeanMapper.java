package com.guildgate.web.Service.mapper;

import com.guildgate.web.Bean.PermisoBean;
import com.guildgate.web.Bean.RolBean;
import com.guildgate.web.Modelo.Roles;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Juan - Luis
 */
public class RolBeanMapper {

    public RolBean toBean(Roles entidad) {
        RolBean bean = RolBean.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .descripcion(entidad.getDescripcion())
                .build();

        entidad.getRolPermisos().forEach(rp -> {
            bean.getPermisos().add(
                    PermisoBean.builder()
                            .id(rp.getPermiso().getId())
                            .codigo(rp.getPermiso().getCodigo())
                            .descripcion(rp.getPermiso().getDescripcion())
                            .build()
            );
        });

        return bean;
    }

    public List<RolBean> toBeanList(List<Roles> entidades) {
        return entidades.stream()
                .map(this::toBean)
                .collect(Collectors.toList());
    }
}
