package com.sigei.dao.evento;

import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.dao.usuariosDao.ParticipanteDao;
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
        String sqlVerifica = "SELECT statusEvento, vagasTotal, vagasOcupadas FROM evento WHERE idevento = ?";

        PreparedStatement pstVerifica = c.prepareStatement(sqlVerifica);
        pstVerifica.setInt(1, obj.getEvento().getId());
        ResultSet rs = pstVerifica.executeQuery();

        if (rs.next()) {
            if (rs.getString("statusEvento").equals("FINALIZADO")) {
                throw new IllegalArgumentException("Não é possível se inscrever. O evento já foi finalizado.");
            }
        }

        if (rs.getInt("vagasOcupadas") >= rs.getInt("vagasTotal")) {
            throw new IllegalArgumentException("Não é possível se inscrever. O evento não possui vagas disponíveis.");
        }

        // Verifica se o participante já está inscrito neste evento
        String sqlDuplicata = "SELECT COUNT(*) as total FROM inscricao WHERE participanteID = ? AND eventoID = ?";
        PreparedStatement pstDuplicata = c.prepareStatement(sqlDuplicata);
        pstDuplicata.setInt(1, obj.getParticipante().getId());
        pstDuplicata.setInt(2, obj.getEvento().getId());
        ResultSet rsDuplicata = pstDuplicata.executeQuery();

        if (rsDuplicata.next() && rsDuplicata.getInt("total") > 0) {
            throw new IllegalArgumentException("O participante já está inscrito neste evento.");
        }

        String sql = "INSERT INTO inscricao\n" +
                    "(participanteID,eventoID,dataInscricao)\n" +
                    "VALUES(?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1,  obj.getParticipante().getId());
        pst.setInt(2,  obj.getEvento().getId());
        pst.setObject(3, obj.getDataInscricao());
        pst.execute();

        // Atualiza o número de vagas ocupadas do evento
        String sqlUpdateVagas = "UPDATE evento SET vagasOcupadas = vagasOcupadas + 1 WHERE idevento = ?";
        PreparedStatement pstUpdate = c.prepareStatement(sqlUpdateVagas);
        pstUpdate.setInt(1, obj.getEvento().getId());
        pstUpdate.execute();
    }

    @Override
    public void alter(Inscricao obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "UPDATE inscricao\n" +
                    "SET participanteID = ?,eventoID = ?,dataInscricao = ?\n" +
                    "WHERE inscricaoID = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1,  obj.getParticipante().getId());
        pst.setInt(2,  obj.getEvento().getId());
        pst.setObject(3, obj.getDataInscricao());
        pst.setInt(4, obj.getId());
        pst.execute();
    }

        @Override
        public void delete(Integer key) throws SQLException, ClassNotFoundException {
            Connection c = ConnectionFactory.getConnection();

            Inscricao i = new InscricaoDao().findByKey(key);
            if (i == null) {
                throw new IllegalArgumentException("Inscrição não encontrada.");
            }

            String sql = "DELETE FROM inscricao\n" +
                        "WHERE inscricaoID = ?;";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, key);
            pst.execute();

            String sqlUpdateVagas = "UPDATE evento SET vagasOcupadas = vagasOcupadas - 1 WHERE idevento = ?";
            PreparedStatement pstUpdate = c.prepareStatement(sqlUpdateVagas);
            pstUpdate.setInt(1,i.getEvento().getId());
            pstUpdate.execute();
        }

    @Override
    public Inscricao findByKey(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT inscricaoID,participanteID,eventoID,dataInscricao  \n" +
                "FROM inscricao where inscricaoID = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        ResultSet rs = pst.executeQuery();
        Inscricao i = null;
        if (rs.next()) {

            Evento evento = new EventoDao().findByKey(rs.getInt("eventoID"));
            Participante participante = new ParticipanteDao().findByKey(rs.getInt("participanteID"));
            LocalDateTime dataInscricao = (LocalDateTime) rs.getObject("dataInscricao");
            i = new Inscricao(evento, participante, dataInscricao);
            i.setId(rs.getInt("inscricaoID"));
            return i;
        }
        return null;
    }

    @Override
    public ArrayList<Inscricao> findAll() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT inscricaoID,participanteID,eventoID,dataInscricao\n" +
                "FROM inscricao;";

        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ArrayList<Inscricao> inscricoes = new ArrayList<>();
        while (rs.next()) {
            Evento evento = new EventoDao().findByKey(rs.getInt("eventoID"));
            Participante participante = new ParticipanteDao().findByKey(rs.getInt("participanteID"));
            LocalDateTime dataInscricao = (LocalDateTime) rs.getObject("dataInscricao");
            Inscricao i = new Inscricao(evento, participante, dataInscricao);
            i.setId(rs.getInt("inscricaoID"));
            inscricoes.add(i);
        }
        return inscricoes;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT COUNT(*) AS total FROM inscricao;";

        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int qtd = 0;
        if (rs.next()) {
            qtd = rs.getInt("total");
        }
        return qtd;
    }


}
