package com.sigei.model;

import com.sigei.model.enm.ETipoUsuario;

public class Administrador extends Usuario {

    public Administrador() {
    }

    public Administrador(String nome, String email, String senha, ETipoUsuario tipoUsuario) {
        super(nome, email, senha, tipoUsuario);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
