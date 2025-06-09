package com.sigei.dao.evento;

import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.model.evento.Inscricao;

import java.sql.SQLException;
import java.util.ArrayList;

public class InscricaoDao implements IGenericsDao<Inscricao, Integer> {
    @Override
    public void insert(Inscricao obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void alter(Inscricao obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void delete(Integer obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public Inscricao findByKey(Integer obj) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Inscricao> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
