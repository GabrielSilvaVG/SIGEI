package com.sigei.Controller;

import com.sigei.dao.usuariosDao.ParticipanteDao;
import com.sigei.model.usuarios.Participante;

import java.sql.SQLException;

public class RegisterPartController {


    public void Registrar(Participante p) throws SQLException, ClassNotFoundException {

        new ParticipanteDao().insert(p);

    }
}
