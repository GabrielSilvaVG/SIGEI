package com.sigei.Controller;

import com.sigei.dao.evento.EventoDao;
import com.sigei.model.enums.EStatusEvento;
import com.sigei.model.evento.Evento;
import com.sigei.model.usuarios.Organizador;

import java.sql.SQLException;
import java.util.ArrayList;

public class MenuOrgController {
    public ArrayList<Evento> getEventos(Organizador o) throws SQLException, ClassNotFoundException {

        ArrayList<Evento> Todoseventos = new EventoDao().findAll();
        ArrayList<Evento> eventosOrg = new ArrayList<>();
        for (Evento e : Todoseventos) {
            if(e.getOrganizador().getId() == o.getId()) {
                eventosOrg.add(e);
            }
        }
        return eventosOrg;
    }

    public void apagarEvento(int idEvento) throws SQLException, ClassNotFoundException {
        new EventoDao().delete(idEvento);
    }

    public void finalizarEvento(int idEvento) throws SQLException, ClassNotFoundException {
        Evento e = new EventoDao().findByKey(idEvento);
        e.setStatusEvento(EStatusEvento.FINALIZADO);
        new EventoDao().alter(e);
    }
}
