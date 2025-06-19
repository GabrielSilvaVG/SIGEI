package com.sigei.Controller;

import com.sigei.dao.usuariosDao.OrganizadorDao;
import com.sigei.model.usuarios.Organizador;

import java.sql.SQLException;

public class RegisterOrgController {


    public void Registrar(Organizador o) throws SQLException, ClassNotFoundException {
            new OrganizadorDao().insert(o);

    }

}
