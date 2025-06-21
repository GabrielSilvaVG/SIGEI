package com.sigei.Controller;

import com.sigei.dao.evento.EventoDao;
import com.sigei.model.evento.Evento;

import java.sql.SQLException;

public class NovoEventoController {

    public void criarEvento(Evento e) throws SQLException, ClassNotFoundException {

        new EventoDao().insert(e);

    }
}
