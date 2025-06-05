package com.sigei.model;

import com.sigei.model.enm.ETipoUsuario;

public class Participante extends Usuario {
    
    private String telefone, cpf;

    public Participante() {
    }

    public Participante(String nome, String email, String senha, ETipoUsuario tipoUsuario, String telefone, String cpf) {
        super(nome, email, senha, tipoUsuario);
        setTelefone(telefone);
        setCpf(cpf);
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio.");
        } else {
            this.telefone = telefone;
        }
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("Cpf não pode ser nulo ou vazio.");
        } else if (cpf.length()>11) {
            throw new IllegalArgumentException("Cpf não pode conter mais que 11 caracteres.");
        } else {
            this.cpf = cpf;
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", Telefone: " + telefone + ", cpf: " + cpf;
    }
}
