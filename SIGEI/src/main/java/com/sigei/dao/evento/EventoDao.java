package com.sigei.dao.evento;

import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.model.evento.Evento;
import com.sun.source.tree.IfTree;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventoDao implements IGenericsDao<Evento, Integer> {

    @Override
    public void insert(Evento obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void alter(Evento obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void delete(Integer obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public Evento findByKey(Integer obj) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Evento> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
