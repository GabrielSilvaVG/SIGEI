package com.sigei.model.evento;


import com.sigei.model.usuarios.Organizador;
import com.sigei.model.usuarios.Participante;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Evento {

    private int id, vagasTotal, vagasOcupadas;
    private String nome, tipo, local, palestrante;
    private LocalDateTime dataEvento;
    private Organizador organizador;


    public Evento(Organizador organizador, String palestrante, String local, LocalDateTime dataEvento, String tipo, int vagasTotal, String nome) {
        setOrganizador(organizador);
        setPalestrante(palestrante);
        setLocal(local);
        setDataEvento(dataEvento);
        setTipo(tipo);
        setVagasTotal(vagasTotal);
        setNome(nome);
        this.vagasOcupadas = 0;
    }


    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVagasTotal() {
        return vagasTotal;
    }

    public void setVagasTotal(int vagasTotal) {
        if (vagasTotal <= 0) {
            throw new IllegalArgumentException("O total de vagas deve ser maior que zero.");
        }
        this.vagasTotal = vagasTotal;
    }

    public int getVagasOcupadas() {
        return vagasOcupadas;
    }

    public void setVagasOcupadas(int vagasOcupadas) {
         this.vagasOcupadas = vagasOcupadas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarString(nome, "Nome");
        this.nome = nome;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        if (dataEvento == null) {
            throw new IllegalArgumentException("A data do Evento não pode ser nula.");
        }
        this.dataEvento = dataEvento;
    }

    public String getPalestrante() {
        return palestrante;
    }

    public void setPalestrante(String palestrante) {
        validarString(palestrante, "Palestrante");
        this.palestrante = palestrante;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        if (organizador == null) {
            throw new IllegalArgumentException("Organizador inexistente");
        }
        this.organizador = organizador;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        validarString(tipo, "Tipo");
        this.tipo = tipo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        validarString(local, "Local");
        this.local = local;
    }


    // Métodos

    public boolean temVagasDisponiveis() {
        return vagasOcupadas < vagasTotal;
    }

    public int getVagasDisponiveis() {
        return vagasTotal - vagasOcupadas;
    }


    // Métodos auxiliares

    private void validarString(String campo, String nomeCampo) {
        if (campo == null || campo.trim().isEmpty()) {
            throw new IllegalArgumentException(nomeCampo + " não pode ser nulo ou vazio.");
        }
        if (campo.length() > 100) {
            throw new IllegalArgumentException(nomeCampo + " não pode ter mais de 100 caracteres.");
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Evento: " + nome +
                " | Tipo: " + tipo +
                " | Data: " + dataEvento.format(formatter) +
                " | Local: " + local +
                " | Palestrante: " + palestrante +
                " | Vagas: " + vagasOcupadas + "/" + vagasTotal +
                " | Organizador: " + organizador.getNome();
    }
}
