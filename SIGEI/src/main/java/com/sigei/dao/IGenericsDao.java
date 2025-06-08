package com.sigei.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IGenericsDao<C,K> {

    public void insert(C obj) throws SQLException, ClassNotFoundException;
    public void alter(C obj) throws SQLException, ClassNotFoundException;
    public void delete(K obj) throws SQLException, ClassNotFoundException;
    public C findByKey(K obj) throws SQLException, ClassNotFoundException;
    public ArrayList<C> findAll() throws SQLException, ClassNotFoundException;
    public int count() throws SQLException, ClassNotFoundException;

}
