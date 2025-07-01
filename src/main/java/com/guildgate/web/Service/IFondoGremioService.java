package com.guildgate.web.Service;

import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Service.generic.ICrudService;

/**
 *
 * @author Juan - Luis
 */
public interface IFondoGremioService extends ICrudService<FondoGremio, Long> {

    FondoGremio buscarFondoGremioPorNombre(String nombreArchivo);
}
