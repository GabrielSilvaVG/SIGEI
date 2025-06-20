package com.sigei.Controller;

import com.sigei.dao.usuariosDao.AdministradorDao;
import com.sigei.dao.usuariosDao.OrganizadorDao;
import com.sigei.dao.usuariosDao.ParticipanteDao;
import com.sigei.model.enums.ETipoUsuario;
import com.sigei.model.usuarios.Usuario;


public class LoginController {
    public Usuario autenticar(String email, String senha) throws Exception {

        if (email.isEmpty() || senha.isEmpty()) {
            throw new Exception("Email ou senha não podem ser nulo!");
        }

        Usuario u;

        u = new ParticipanteDao().authenticate(email,senha);
        if (u == null) {
            u = new OrganizadorDao().authenticate(email,senha);
        }
        if (u == null) {
            u = new AdministradorDao().authenticate(email,senha);
        }

        if (u == null) {
            throw new Exception("Email ou senha incorreto!");
        }

        return u;
    }

    public int tipoUsuario(Usuario u) {

        if (u.getTipoUsuario() == ETipoUsuario.PARTICIPANTE) {
            return 1;
        } else if (u.getTipoUsuario() == ETipoUsuario.ORGANIZADOR) {
            return 2;
        } else if (u.getTipoUsuario() == ETipoUsuario.ADMIN) {
            return 3;
        }
        return 0;
    }
}
