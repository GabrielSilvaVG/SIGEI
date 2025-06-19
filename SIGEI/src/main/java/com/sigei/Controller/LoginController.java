package com.sigei.Controller;

import com.sigei.dao.usuariosDao.OrganizadorDao;
import com.sigei.dao.usuariosDao.ParticipanteDao;
import com.sigei.model.usuarios.Usuario;


public class LoginController {
    public Usuario authenticate(String email, String senha) throws Exception {

        if (email.isEmpty() || senha.isEmpty()) {
            throw new Exception("Email ou senha não podem ser nulo!");
        }

        Usuario u;

        u = new ParticipanteDao().authenticate(email,senha);
        if (u == null) {
            u = new OrganizadorDao().authenticate(email,senha);
        }
        if (u == null) {
            u = new ParticipanteDao().authenticate(email,senha);
        }

        if (u == null) {
            throw new Exception("Email ou senha incorreto!");
        }

        return u;
    }
}
