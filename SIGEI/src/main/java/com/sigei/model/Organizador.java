package com.sigei.model;

import com.sigei.model.enm.ETipoUsuario;

public class Organizador extends Usuario {

    private String empresa, telefone;

    public Organizador() {
        tipoUsuario = ETipoUsuario.ORGANIZADOR;
    }

    public Organizador(String nome, String email, String senha, String empresa, String telefone) {
        super(nome, email, senha);
        setEmpresa(empresa);
        setTelefone(telefone);
        tipoUsuario = ETipoUsuario.ORGANIZADOR;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        if (empresa == null || empresa.trim().isEmpty()) {
            throw new IllegalArgumentException("Empresa não pode ser nula ou vazia.");
        } else {
            this.empresa = empresa;
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo.");
        } else {
            this.telefone = telefone;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Empresa: " + empresa + ", Telefone: " + telefone;
    }
}
