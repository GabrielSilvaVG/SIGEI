package com.sigei.Controller;

import com.sigei.dao.evento.EventoDao;
import com.sigei.dao.evento.InscricaoDao;
import com.sigei.model.enums.EStatusEvento;
import com.sigei.model.evento.Evento;
import com.sigei.model.evento.Inscricao;
import com.sigei.model.usuarios.Participante;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MenuPartController {


    public ArrayList<Evento> EventosDisponiveis(Participante p) throws SQLException, ClassNotFoundException {
        ArrayList<Evento> todosEventos = new EventoDao().findAll();
        ArrayList<Evento> eventosParticipante = new EventoDao().EventosParticipante(p.getId());
        ArrayList<Evento> eventosDisponiveis = new ArrayList<>();


        for (int i = 0; i < todosEventos.size(); i++) {
            Evento evento = todosEventos.get(i);

            if (!evento.getStatusEvento().equals(EStatusEvento.FINALIZADO)) {

                boolean jaInscrito = false;

                for (Evento eventoInscrito : eventosParticipante) {
                    if (eventoInscrito.getId() == evento.getId()) {
                        jaInscrito = true;
                        break;
                    }
                }

                if (!jaInscrito) {
                    eventosDisponiveis.add(evento);
                }
            }
        }

        return eventosDisponiveis;
    }

    public ArrayList<Evento> EventosParticipantes(Participante p) throws SQLException, ClassNotFoundException {

        return new EventoDao().EventosParticipante(p.getId());

    }

    public void GerarInscricao(int idEvento, Participante p) throws SQLException, ClassNotFoundException {

        Evento e = new EventoDao().findByKey(idEvento);

        Inscricao i = new Inscricao(e,p, LocalDateTime.now());

        new InscricaoDao().insert(i);

    }

    public void removerInscricao(int idEvento, Participante p) throws SQLException, ClassNotFoundException {
        new InscricaoDao().deleteByEventoAndParticipante(idEvento, p.getId());
        Evento e = new EventoDao().findByKey(idEvento);
        if (e.getVagasOcupadas() > 0) {
            e.setVagasOcupadas(e.getVagasOcupadas() - 1);
            new EventoDao().alter(e);
        }
    }
}
