package com.guildgate.web.Bean;

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

public class BannerBean {

    private Long id;
    private String nombreBanner;
    private String bannerUsuario;
    private String posicionXBanner;
    private String posicionYBanner;
}
