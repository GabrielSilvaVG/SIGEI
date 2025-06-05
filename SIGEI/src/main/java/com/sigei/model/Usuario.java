package com.sigei.model;

import com.sigei.model.enm.ETipoUsuario;

public abstract class Usuario {
    private int id;
    private String nome, email, senha;
    private ETipoUsuario tipoUsuario;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, ETipoUsuario tipoUsuario) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        setTipoUsuario(tipoUsuario);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
       //.trim remome os espaços vazios e não deixa cadastrar nomes apenas com espaços.
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        } else if (nome.length() > 100) {
            throw new IllegalArgumentException("Nome não pode ter mais de 100 caracteres.");
        } else {
            this.nome = nome;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio.");
        } else if (!email.contains("@")) {
            throw new IllegalArgumentException("Email deve conter @.");
        } else if (email.length() > 100) {
            throw new IllegalArgumentException("Email não pode ter mais de 100 caracteres.");
        } else {
            this.email = email.toLowerCase();
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nulo ou vazio.");
        } else if (senha.length() < 8) {
            throw new IllegalArgumentException("Senha precisa ter no minimo 8 caracteres.");
        } else if (senha.length() > 20) {
            throw new IllegalArgumentException("Senha não pode ter mais de 20 caracteres.");
        } else {
            this.senha = senha;
        }
    }

    public ETipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(ETipoUsuario tipoUsuario) {
        if (tipoUsuario == null) {
            throw new IllegalArgumentException("Tipo de usuário não pode ser nulo ou vazio.");
        }
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Nome: " + nome + ", Email: " + email + ", Tipo: " + tipoUsuario;
    }
}
