package com.sigei.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Evento {

    private int id, vagasTotal, vagasOcupadas;
    private String nome, tipo, local, palestrante;
    private LocalDate dataEvento;
    private Organizador organizador;
    private ArrayList<Participante> participantes;


    public Evento(Organizador organizador, String palestrante, String local, LocalDate dataEvento, String tipo, int vagasTotal, String nome) {
        setOrganizador(organizador);
        setPalestrante(palestrante);
        setLocal(local);
        setDataEvento(dataEvento);
        setTipo(tipo);
        setVagasTotal(vagasTotal);
        setNome(nome);
        this.vagasOcupadas = 0;
        this.participantes = new ArrayList<>();
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarString(nome, "Nome");
        this.nome = nome;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
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


    // Métodos de negócio

    public void addParticipante(Participante participante) {
        if (participante == null) {
            throw new IllegalArgumentException("Participante não pode ser nulo.");
        }
        if (!temVagasDisponiveis()) {
            throw new IllegalArgumentException("Total de vagas atingido.");
        }
        participantes.add(participante);
        vagasOcupadas++;
    }

    public void removerParticipante(int participanteID) {
        boolean removido = false;
        for (int i = 0; i < participantes.size(); i++) {
            if (participantes.get(i).getId() == participanteID) {
                participantes.remove(i);
                vagasOcupadas--;
                removido = true;
                break;
            }
        }
        if (!removido) {
            throw new IllegalArgumentException("Participante com ID " + participanteID + " não encontrado.");
        }
    }

    public ArrayList<Participante> getParticipantes() {
        return new ArrayList<>(participantes); // Retorna uma cópia da lista
    }

    public Participante buscarParticipante(int id) {
        for (Participante p : participantes) {
            if (p.getId() == id) return p;
        }
        throw new IllegalArgumentException("Participante com ID " + id + " não encontrado.");
    }

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
        return "Evento: " + nome +
                " | Tipo: " + tipo +
                " | Data: " + dataEvento +
                " | Local: " + local +
                " | Palestrante: " + palestrante +
                " | Vagas: " + vagasOcupadas + "/" + vagasTotal +
                " | Organizador: " + organizador.getNome();
    }
}
