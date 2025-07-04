package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Mundos;
import com.guildgate.web.Service.MundosService;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class MundosController {

    @Inject
    MundosService ms;

    public MundosController() {
        this.ms = new MundosService();
    }

    public boolean creacion(Mundos m) {
        boolean estado = ms.create(m);
        return estado;
    }

    public boolean edicion(Mundos m) {
        boolean estado = ms.edit(m);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = ms.delete(id);
        return estado;
    }

    public Mundos buscarPorId(Integer id) {
        return ms.findById(id);
    }

    public ArrayList<Mundos> buscarTodos() {
        return ms.findAll();
    }

}
