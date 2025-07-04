package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.FondoGremio;
import com.guildgate.web.Service.FondoGremioService;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class FondoGremioController {

    @Inject
    FondoGremioService fgs;

    public FondoGremioController() {
        this.fgs = new FondoGremioService();
    }

    public boolean creacion(FondoGremio fg) {
        boolean estado = fgs.create(fg);
        return estado;
    }

    public boolean edicion(FondoGremio fg) {
        boolean estado = fgs.edit(fg);
        return estado;
    }

    public boolean eliminar(Long id) {
        boolean estado = fgs.delete(id);
        return estado;
    }

    public FondoGremio buscarPorId(Long id) {
        return fgs.findById(id);
    }

    public ArrayList<FondoGremio> buscarTodos() {
        return fgs.findAll();
    }

}
