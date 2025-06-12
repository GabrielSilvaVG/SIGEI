package com.sigei.model.evento;

import com.sigei.model.usuarios.Participante;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Inscricao {
    private int id;
    private Evento evento;
    private Participante participante;
    private LocalDateTime dataInscricao;


    public Inscricao(Evento evento, Participante participante, LocalDateTime dataInscricao) {
        this.evento = evento;
        this.participante = participante;
        this.dataInscricao = dataInscricao;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        return "id: " + id + "| evento: " + evento + "| participante: " + participante +
                "| dataInscricao: " + dataInscricao.format(formatter);
    }
}
