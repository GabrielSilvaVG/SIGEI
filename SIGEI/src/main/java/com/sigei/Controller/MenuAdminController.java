package com.sigei.Controller;

import com.sigei.dao.evento.EventoDao;
import com.sigei.dao.evento.InscricaoDao;
import com.sigei.dao.usuariosDao.OrganizadorDao;
import com.sigei.dao.usuariosDao.ParticipanteDao;
import com.sigei.model.enums.EStatusEvento;
import com.sigei.model.evento.Evento;
import com.sigei.model.usuarios.Usuario;

import java.sql.SQLException;
import java.util.ArrayList;

public class MenuAdminController {


    public int getEventosNFinalizados() throws SQLException, ClassNotFoundException {

        ArrayList<Evento> eventos = new EventoDao().findAll();
        int eventosNFinalizados = 0;

        for (Evento evento : eventos) {
            if (evento.getStatusEvento() == EStatusEvento.NAO_FINALIZADO) {
                eventosNFinalizados++;
            }
        }
        return eventosNFinalizados;
    }

    public int getIscricoesAtivas() throws SQLException, ClassNotFoundException {

        return new InscricaoDao().count();
    }

    public ArrayList<Usuario> getUsuarios() throws SQLException, ClassNotFoundException {

        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(new ParticipanteDao().findAll());
        usuarios.addAll(new OrganizadorDao().findAll());

        return usuarios;
    }

    public void apagarUsuario(int idUsuario, String tipoUsuario) throws SQLException, ClassNotFoundException {
        if ("PARTICIPANTE".equals(tipoUsuario)) {
            new ParticipanteDao().delete(idUsuario);
        } else if ("ORGANIZADOR".equals(tipoUsuario)) {
            new OrganizadorDao().delete(idUsuario);
        }
    }

    public ArrayList<Usuario> buscarUsuariosPorNome(String nomePesquisado) throws SQLException, ClassNotFoundException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(new ParticipanteDao().findAll());
        usuarios.addAll(new OrganizadorDao().findAll());

        if (!usuarios.isEmpty()) {

            for (int i = usuarios.size() - 1; i >= 0; i--) {
                if (!usuarios.get(i).getNome().toLowerCase().contains(nomePesquisado.toLowerCase())) {
                    usuarios.remove(i);
                }
            }
        }
        return usuarios;
    }

    public ArrayList<Evento> getEventos() throws SQLException, ClassNotFoundException {

        return new EventoDao().findAll();

    }

    public void apagarEvento(int idEvento) throws SQLException, ClassNotFoundException {
        new EventoDao().delete(idEvento);
    }

    public ArrayList<Evento> buscarEventosPorNome(String nomePesquisado) throws SQLException, ClassNotFoundException {

        ArrayList<Evento> eventos = new EventoDao().findAll();

        if (!eventos.isEmpty()) {
            for (int i = eventos.size() - 1; i >= 0; i--) {
                if (!eventos.get(i).getNome().toLowerCase().contains(nomePesquisado.toLowerCase())) {
                    eventos.remove(i);
                }
            }
        }
        return eventos;
    }

    public void finalizarEvento(int idEvento) throws SQLException, ClassNotFoundException {
        Evento e = new EventoDao().findByKey(idEvento);
        e.setStatusEvento(EStatusEvento.FINALIZADO);
        new EventoDao().alter(e);
    }

    public Evento getEvento(int idEvento) throws SQLException, ClassNotFoundException {
        return new EventoDao().findByKey(idEvento);
    }
}
