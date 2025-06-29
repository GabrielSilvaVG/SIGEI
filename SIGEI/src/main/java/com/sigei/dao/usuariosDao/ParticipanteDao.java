package com.sigei.dao.usuariosDao;

import com.sigei.dao.evento.InscricaoDao;
import com.sigei.dao.IGenericsDao;
import com.sigei.dao.ConnectionFactory;
import com.sigei.model.usuarios.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParticipanteDao implements IGenericsDao<Participante, Integer> {
    @Override
    public void insert(Participante obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "INSERT INTO usuario\n" +
                    "(nome,email,senha,tipoUsuario,telefone,cpf)\n" +
                    "VALUES\n" +
                    "(?,?,md5(?),?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setString(3, obj.getSenha());
        pst.setString(4, obj.getTipoUsuario().toString());
        pst.setString(5, obj.getTelefone());
        pst.setString(6, obj.getCpf());
        pst.execute();

    }

    @Override
    public void alter(Participante obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "UPDATE usuario\n" +
                    "SET nome = ?,email = ?,senha = md5(?),tipoUsuario = ?, telefone = ?, cpf = ?\n" +
                    "WHERE idusuario = ? and tipoUsuario = 'PARTICIPANTE';";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setString(3, obj.getSenha());
        pst.setString(4, obj.getTipoUsuario().toString());
        pst.setString(5, obj.getTelefone());
        pst.setString(6, obj.getCpf());
        pst.setInt(7, obj.getId());
        pst.execute();
    }

    @Override
    public void delete(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM usuario WHERE idusuario = ? AND tipoUsuario = 'PARTICIPANTE';";

        new InscricaoDao().deleteAllFromPart(key);

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        pst.execute();
    }

    @Override
    public Participante findByKey(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario,nome,email,senha,tipoUsuario,telefone,cpf\n" +
                    "FROM usuario where idusuario = ? and tipoUsuario = 'PARTICIPANTE';";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        ResultSet rs = pst.executeQuery();
        Participante p = null;
        if (rs.next()) {
            p = new Participante(rs.getString("nome"),rs.getString("email"),rs.getString("senha"),rs.getString("telefone"),rs.getString("cpf"));
            p.setId(rs.getInt("idusuario"));
            return p;
        }
        return null;
    }

    @Override
    public ArrayList<Participante> findAll() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario, nome, email, senha, telefone, cpf FROM usuario where tipoUsuario = 'PARTICIPANTE';";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ArrayList<Participante> users = new ArrayList<>();
        while (rs.next()) {
            Participante u = new Participante(rs.getString("nome"), rs.getString("email"), rs.getString("senha"), rs.getString("telefone"), rs.getString("cpf"));
            u.setId(rs.getInt("idusuario"));
            users.add(u);
        }
        return users;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT COUNT(*) AS total FROM usuario where tipoUsuario = 'PARTICIPANTE';";

        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int qtd = 0;
        if (rs.next()) {
            qtd = rs.getInt("total");
        }
        return qtd;
    }

    public Participante authenticate(String email, String senha) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario,nome,email,senha,tipoUsuario,telefone,cpf\n" +
                    "FROM usuario where email = ? and senha = md5(?) and tipoUsuario = 'PARTICIPANTE';";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, senha);
        ResultSet rs = pst.executeQuery();
        Participante p = null;
        if (rs.next()) {
            p = new Participante(rs.getString("nome"),rs.getString("email"),rs.getString("senha"),rs.getString("telefone"),rs.getString("cpf"));
            p.setId(rs.getInt("idusuario"));
            return p;
        }
        return null;
    }
}
