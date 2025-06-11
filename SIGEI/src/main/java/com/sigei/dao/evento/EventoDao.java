package com.sigei.dao.evento;

import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.dao.usuariosDao.OrganizadorDao;
import com.sigei.model.evento.Evento;

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
                    "(nome,tipo,local,dataEvento,vagasTotal,vagasOcupadas,palestrante,organizadorID)\n" +
                    "VALUES (?,?,?,?,?,?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getTipo());
        pst.setString(3, obj.getLocal());
        pst.setObject(4, obj.getDataEvento());
        pst.setInt(5, obj.getVagasTotal());
        pst.setInt(6, obj.getVagasOcupadas());
        pst.setString(7, obj.getPalestrante());
        pst.setInt(8, obj.getOrganizador().getId());
        pst.execute();
    }

    @Override
    public void alter(Evento obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "UPDATE evento\n" +
                "SET nome = ?,tipo = ?,local = ?,dataEvento = ?,vagasTotal = ?,vagasOcupadas = ?,palestrante = ?,organizadorID = ?\n" +
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
        pst.setInt(9, obj.getId());
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
        String sql = "SELECT idevento,nome,tipo,local,dataEvento,vagasTotal,vagasOcupadas,palestrante,organizadorID\n" +
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
            return e;
        }
        return null;
    }

    @Override
    public ArrayList<Evento> findAll() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idevento,nome,tipo,local,dataEvento,vagasTotal,vagasOcupadas,palestrante,organizadorID \n" +
                    "FROM evento;";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ArrayList<Evento> eventos = new ArrayList<>();
        while (rs.next()) {
            Evento e = new Evento(new OrganizadorDao().findByKey(rs.getInt("organizadorID")), rs.getString("palestrante"),
                    rs.getString("local"), (LocalDateTime) rs.getObject("dataEvento"), rs.getString("tipo"), rs.getInt("vagasTotal"), rs.getString("nome"));

            e.setId(rs.getInt("idevento"));
            e.setVagasOcupadas(rs.getInt("vagasOcupadas"));
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

    public void deleteAllFromOrg(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM evento\n" +
                    "WHERE organizadorID = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        pst.execute();
    }

}
