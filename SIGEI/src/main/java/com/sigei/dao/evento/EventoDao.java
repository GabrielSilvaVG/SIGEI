package com.sigei.dao.evento;

import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.dao.usuariosDao.OrganizadorDao;
import com.sigei.model.enums.EStatusEvento;
import com.sigei.model.evento.Evento;
import com.sigei.model.usuarios.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EventoDao implements IGenericsDao<Evento, Integer> {

    @Override
    public void insert(Evento obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "INSERT INTO evento\n" +
                    "(nome,tipo,local,dataEvento,vagasTotal,vagasOcupadas,palestrante,organizadorID,statusEvento)\n" +
                    "VALUES (?,?,?,?,?,?,?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getTipo());
        pst.setString(3, obj.getLocal());
        pst.setObject(4, obj.getDataEvento());
        pst.setInt(5, obj.getVagasTotal());
        pst.setInt(6, obj.getVagasOcupadas());
        pst.setString(7, obj.getPalestrante());
        pst.setInt(8, obj.getOrganizador().getId());
        pst.setString(9, obj.getStatusEvento().toString());
        pst.execute();
    }

    @Override
    public void alter(Evento obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "UPDATE evento\n" +
                "SET nome = ?,tipo = ?,local = ?,dataEvento = ?,vagasTotal = ?,vagasOcupadas = ?,palestrante = ?,organizadorID = ?, statusEvento = ?\n" +
                "WHERE idevento = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getTipo());
        pst.setString(3, obj.getLocal());
        pst.setObject(4, obj.getDataEvento());
        pst.setInt(5, obj.getVagasTotal());
        pst.setInt(6, obj.getVagasOcupadas());
        pst.setString(7, obj.getPalestrante());
        pst.setInt(8, obj.getOrganizador().getId());
        pst.setString(9, obj.getStatusEvento().toString());
        pst.setInt(10, obj.getId());
        pst.execute();

    }

    @Override
    public void delete(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM evento\n" +
                    "WHERE idevento = ?;\n";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1,key);
        pst.execute();
    }

    @Override
    public Evento findByKey(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idevento,nome,tipo,local,dataEvento,vagasTotal,vagasOcupadas,palestrante,organizadorID, statusEvento\n" +
                    "FROM evento where idevento = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        ResultSet rs = pst.executeQuery();
        Evento e = null;
        if (rs.next()) {
            e = new Evento(new OrganizadorDao().findByKey(rs.getInt("organizadorID")), rs.getString("palestrante"),
                    rs.getString("local"), (LocalDateTime) rs.getObject("dataEvento"), rs.getString("tipo"), rs.getInt("vagasTotal"), rs.getString("nome"));

            e.setId(rs.getInt("idevento"));
            e.setVagasOcupadas(rs.getInt("vagasOcupadas"));
            e.setStatusEvento(EStatusEvento.valueOf(rs.getString("statusEvento")));
            return e;
        }
        return null;
    }

    @Override
    public ArrayList<Evento> findAll() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idevento,nome,tipo,local,dataEvento,vagasTotal,vagasOcupadas,palestrante,organizadorID,statusEvento \n" +
                    "FROM evento;";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ArrayList<Evento> eventos = new ArrayList<>();
        while (rs.next()) {
            Evento e = new Evento(new OrganizadorDao().findByKey(rs.getInt("organizadorID")), rs.getString("palestrante"),
                    rs.getString("local"), (LocalDateTime) rs.getObject("dataEvento"), rs.getString("tipo"), rs.getInt("vagasTotal"), rs.getString("nome"));

            e.setId(rs.getInt("idevento"));
            e.setVagasOcupadas(rs.getInt("vagasOcupadas"));
            e.setStatusEvento(EStatusEvento.valueOf(rs.getString("statusEvento")));
            eventos.add(e);
        }
        return eventos;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT COUNT(*) AS total FROM evento;";

        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int qtd = 0;
        if (rs.next()) {
            qtd = rs.getInt("total");
        }
        return qtd;
    }

    //METODOS ESPECIFICOS

    public void deleteAllFromOrg(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM evento\n" +
                    "WHERE organizadorID = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        pst.execute();
    }

    public ArrayList<Participante> getAllParticipantFromEvent(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario,nome,email,senha,telefone,cpf\n" +
                    "FROM usuario JOIN inscricao ON usuario.idusuario = inscricao.participanteID\n" +
                    "WHERE inscricao.eventoID = ?;\n" +
                    "\n";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        ResultSet rs = pst.executeQuery();
        ArrayList<Participante> participantes = new ArrayList<>();
        while(rs.next()) {
            Participante p = new Participante(rs.getString("nome"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone"), rs.getString("cpf"));
            p.setId(rs.getInt("idusuario"));
            participantes.add(p);
        }
        return participantes;
    }

}
