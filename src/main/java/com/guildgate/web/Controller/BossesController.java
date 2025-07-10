package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.Bosses;
import com.guildgate.web.Service.impl.BossesServiceImpl;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class BossesController {

    @Inject
    BossesServiceImpl bs;

    public BossesController() {
        this.bs = new BossesServiceImpl();
    }

    public boolean creacion(Bosses b) {
        boolean estado = bs.create(b);
        return estado;
    }

    public boolean edicion(Bosses b) {
        boolean estado = bs.edit(b);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = bs.delete(id);
        return estado;
    }

    public Bosses buscarPorId(Integer id) {
        return bs.findById(id);
    }

    public ArrayList<Bosses> buscarTodos() {
        return bs.findAll();
    }
}
