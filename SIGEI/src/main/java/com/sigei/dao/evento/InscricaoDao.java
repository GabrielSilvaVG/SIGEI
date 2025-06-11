package com.sigei.dao.evento;

import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.model.evento.Inscricao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class InscricaoDao implements IGenericsDao<Inscricao, Integer> {
    @Override
    public void insert(Inscricao obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "INSERT INTO inscricao\n" +
                    "(participanteID,eventoID,dataInscricao,EStatusInscricao)\n" +
                    "VALUES(?,?,?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1,  obj.getParticipante().getId());
        pst.setInt(2,  obj.getEvento().getId());
        pst.setObject(3, obj.getDataInscricao());
        pst.setString(1, obj.getStatusInscricao().toString());
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
