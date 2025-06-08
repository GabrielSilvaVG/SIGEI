package com.sigei.dao;

import com.sigei.dao.conexao.ConnectionFactory;
import com.sigei.model.Administrador;
import com.sigei.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdministradorDao implements IGenericsDao<Administrador, Integer>{
    @Override
    public void insert(Administrador obj) throws SQLException, ClassNotFoundException {

        Connection c = ConnectionFactory.getConnection();

        String sql = "INSERT INTO usuario\n" +
                    "(nome,email,senha,tipoUsuario,empresa,telefone,cpf)\n" +
                    "VALUES\n" +
                    "(?,?,md5(?),?,?,?,?);";

        PreparedStatement pst = c.prepareStatement(sql);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getEmail());
        pst.setString(2, obj.getSenha());
    }

    @Override
    public void alter(Administrador obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public void delete(Integer obj) throws SQLException, ClassNotFoundException {

    }

    @Override
    public Administrador findByKey(Integer obj) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Administrador> findAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public int count() throws SQLException, ClassNotFoundException {
        return 0;
    }
}
