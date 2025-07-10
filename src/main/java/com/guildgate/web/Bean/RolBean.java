package com.guildgate.web.Bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Juan - Luis
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class RolBean implements Serializable {

    private Integer id;
    private String nombre;
    private String descripcion;

    @Builder.Default
    private Set<PermisoBean> permisos = new HashSet<>();
}
