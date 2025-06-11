package com.sigei.dao.usuariosDao;

import com.sigei.dao.evento.EventoDao;
import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.model.usuarios.Organizador;
import com.sigei.model.usuarios.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrganizadorDao implements IGenericsDao<Organizador, Integer> {
    @Override
    public void insert(Organizador obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "INSERT INTO usuario\n" +
                "(nome,email,senha,tipoUsuario,empresa,telefone)\n" +
                "VALUES\n" +
                "(?,?,md5(?),?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setString(3, obj.getSenha());
        pst.setString(4, obj.getTipoUsuario().toString());
        pst.setString(5, obj.getEmpresa());
        pst.setString(6, obj.getTelefone());
        pst.execute();
    }

    @Override
    public void alter(Organizador obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "UPDATE usuario\n" +
                "SET nome = ?,email = ?,senha = md5(?),tipoUsuario = ?, empresa = ?, telefone = ?\n" +
                "WHERE idusuario = ? and tipoUsuario = 'ORGANIZADOR';";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setString(3, obj.getSenha());
        pst.setString(4, obj.getTipoUsuario().toString());
        pst.setString(5, obj.getEmpresa());
        pst.setString(6, obj.getTelefone());
        pst.setInt(7, obj.getId());
        pst.execute();
    }

    @Override
    public void delete(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM usuario WHERE idusuario = ? AND tipoUsuario = 'ORGANIZADOR';";

        new EventoDao().deleteAllFromOrg(key);

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        pst.execute();
    }

    @Override
    public Organizador findByKey(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario,nome,email,senha,tipoUsuario,empresa,telefone\n" +
                    "FROM usuario where idusuario = ? and tipoUsuario = 'ORGANIZADOR';";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        ResultSet rs = pst.executeQuery();
        Organizador o = null;
        if (rs.next()) {
            o = new Organizador(rs.getString("nome"),rs.getString("email"),rs.getString("senha"),rs.getString("empresa"),rs.getString("telefone"));
            o.setId(rs.getInt("idUsuario"));
            return o;
        }
        return null;
    }

    @Override
    public ArrayList<Organizador> findAll() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario, nome, email, senha, empresa, telefone FROM usuario where tipoUsuario = 'ORGANIZADOR';";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ArrayList<Organizador> users = new ArrayList<>();
        while (rs.next()) {
            Organizador u = new Organizador(rs.getString("nome"), rs.getString("email"), rs.getString("senha"), rs.getString("empresa"), rs.getString("telefone"));
            u.setId(rs.getInt("idusuario"));
            users.add(u);
        }
        return users;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT COUNT(*) AS total FROM usuario where tipoUsuario = 'ORGANIZADOR';";

        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int qtd = 0;
        if (rs.next()) {
            qtd = rs.getInt("total");
        }
        return qtd;
    }

    public Organizador authenticate(String email, String senha) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario,nome,email,senha,tipoUsuario,empresa,telefone\n" +
                "FROM usuario where email = ? and senha = md5(?) and tipoUsuario = 'ORGANIZADOR';";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, senha);
        ResultSet rs = pst.executeQuery();
        Organizador o = null;
        if (rs.next()) {
            o = new Organizador(rs.getString("nome"),rs.getString("email"),rs.getString("senha"),rs.getString("empresa"),rs.getString("telefone"));
            o.setId(rs.getInt("idUsuario"));
            return o;
        }
        return null;
    }
}
