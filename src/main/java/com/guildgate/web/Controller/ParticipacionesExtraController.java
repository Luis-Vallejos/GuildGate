package com.guildgate.web.Controller;

import com.guildgate.web.Modelo.ParticipacionesExtra;
import com.guildgate.web.Service.ParticipacionesExtraService;
import jakarta.inject.Inject;
import java.util.ArrayList;

/**
 *
 * @author Juan - Luis
 */
public class ParticipacionesExtraController {

    @Inject
    ParticipacionesExtraService pes;

    public ParticipacionesExtraController() {
        this.pes = new ParticipacionesExtraService();
    }

    public boolean creacion(ParticipacionesExtra pe) {
        boolean estado = pes.create(pe);
        return estado;
    }

    public boolean edicion(ParticipacionesExtra pe) {
        boolean estado = pes.edit(pe);
        return estado;
    }

    public boolean eliminar(Integer id) {
        boolean estado = pes.delete(id);
        return estado;
    }

    public ParticipacionesExtra buscarPorId(Integer id) {
        return pes.findById(id);
    }

    public ArrayList<ParticipacionesExtra> buscarTodos() {
        return pes.findAll();
    }
}
