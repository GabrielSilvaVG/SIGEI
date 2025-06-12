package com.sigei.model.usuarios;

import com.sigei.model.enums.ETipoUsuario;

public class Participante extends Usuario {
    
    private String telefone, cpf;

    public Participante() {
        tipoUsuario = ETipoUsuario.PARTICIPANTE;
    }

    public Participante(String nome, String email, String senha, String telefone, String cpf) {
        super(nome, email, senha);
        setTelefone(telefone);
        setCpf(cpf);
        tipoUsuario = ETipoUsuario.PARTICIPANTE;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio.");
        } else if (telefone.length() > 20) {
            throw new IllegalArgumentException("Telefone não pode conter mais que 20 caracteres.");
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
