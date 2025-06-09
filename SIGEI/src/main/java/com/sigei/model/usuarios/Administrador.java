package com.sigei.model.usuarios;

import com.sigei.model.enums.ETipoUsuario;

public class Administrador extends Usuario {

    public Administrador() {
        tipoUsuario = ETipoUsuario.ADMIN;
    }

    public Administrador(String nome, String email, String senha) {
        super(nome, email, senha);
        tipoUsuario = ETipoUsuario.ADMIN;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
