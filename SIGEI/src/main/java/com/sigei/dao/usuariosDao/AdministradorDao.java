package com.sigei.dao.usuariosDao;

import com.sigei.dao.interfaces.IGenericsDao;
import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.model.usuarios.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministradorDao implements IGenericsDao<Administrador, Integer> {

    //METODOS GENERICOS

    @Override
    public void insert(Administrador obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "INSERT INTO usuario\n" +
                    "(nome,email,senha,tipoUsuario)\n" +
                    "VALUES\n" +
                    "(?,?,md5(?),?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setString(3, obj.getSenha());
        pst.setString(4, obj.getTipoUsuario().toString());
        pst.execute();
    }

    @Override
    public void alter(Administrador obj) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "UPDATE usuario\n" +
                    "SET nome = ?,email = ?,senha = md5(?),tipoUsuario = ?\n" +
                    "WHERE idusuario = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setString(3, obj.getSenha());
        pst.setString(4, obj.getTipoUsuario().toString());
        pst.setInt(5, obj.getId());
        pst.execute();
    }

    @Override
    public void delete(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "DELETE FROM usuario WHERE idusuario = ? AND tipoUsuario = 'ADMIN';";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        pst.execute();
    }

    @Override
    public Administrador findByKey(Integer key) throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario,nome,email,senha,tipoUsuario,empresa,telefone,cpf\n" +
                    "FROM usuario where idusuario = ?;";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setInt(1, key);
        ResultSet rs = pst.executeQuery();
        Administrador a = null;
        if (rs.next()) {
            a = new Administrador(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
            a.setId(rs.getInt("idUsuario"));
            return a;
        }
        return null;
    }

    @Override
    public ArrayList<Administrador> findAll() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT idusuario, nome, email, senha FROM usuario Where tipoUsuario = 'ADMIN';";
        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        ArrayList<Administrador> users = new ArrayList<>();
        while (rs.next()) {
            Administrador u = new Administrador(rs.getString("nome"), rs.getString("email"), rs.getString("senha"));
            u.setId(rs.getInt("idusuario"));
            users.add(u);
        }
        return users;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        Connection c = ConnectionFactory.getConnection();
        String sql = "SELECT COUNT(*) AS total FROM usuario where tipoUsuario = 'ADMIN';";

        PreparedStatement pst = c.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        int qtd = 0;
        if (rs.next()) {
            qtd = rs.getInt("total");
        }
        return qtd;
    }
}
