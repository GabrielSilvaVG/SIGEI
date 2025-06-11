package com.sigei.dao.evento;

import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.dao.usuariosDao.ParticipanteDao;
import com.sigei.model.enums.EStatusInscricao;
import com.sigei.model.evento.Evento;
import com.sigei.model.evento.Inscricao;
import com.sigei.model.usuarios.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class InscricaoDao implements IGenericsDao<Inscricao, Integer> {
    @Override
    public void insert(Inscricao obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "INSERT INTO inscricao\n" +
                    "(participanteID,eventoID,dataInscricao,EStatusInscricao)\n" +
                    "VALUES(?,?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1,  obj.getParticipante().getId());
        pst.setInt(2,  obj.getEvento().getId());
        pst.setObject(3, obj.getDataInscricao());
        pst.setString(4, obj.getStatusInscricao().toString());
        pst.execute();
    }

    @Override
    public void alter(Inscricao obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "UPDATE inscricao\n" +
                    "SET participanteID = ?,eventoID = ?,dataInscricao = ?,EStatusInscricao = ?\n" +
                    "WHERE inscricaoID = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1,  obj.getParticipante().getId());
        pst.setInt(2,  obj.getEvento().getId());
        pst.setObject(3, obj.getDataInscricao());
        pst.setString(4, obj.getStatusInscricao().toString());
        pst.setInt(5, obj.getId());
        pst.execute();
    }

    @Override
    public void delete(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM inscricao\n" +
                    "WHERE inscricaoID = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        pst.execute();

    }

    @Override
    public Inscricao findByKey(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT inscricaoID,participanteID,eventoID,dataInscricao,EStatusInscricao\n" +
                "FROM inscricao where inscricaoID = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        ResultSet rs = pst.executeQuery();
        Inscricao i = null;
        if (rs.next()) {

            Evento evento = new EventoDao().findByKey(rs.getInt("eventoID"));
            Participante participante = new ParticipanteDao().findByKey(rs.getInt("participanteID"));
            EStatusInscricao status = EStatusInscricao.valueOf(rs.getString("EStatusInscricao"));
            LocalDateTime dataInscricao = (LocalDateTime) rs.getObject("dataInscricao");
            i = new Inscricao(evento, participante, status, dataInscricao);
            i.setId(rs.getInt("inscricaoID"));
            return i;
        }
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
