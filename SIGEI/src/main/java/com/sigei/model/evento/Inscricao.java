package com.sigei.model.evento;

import com.sigei.model.enums.EStatusInscricao;
import com.sigei.model.usuarios.Participante;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Inscricao {
    private int id;
    private Evento evento;
    private Participante participante;
    private LocalDateTime dataInscricao;
    private EStatusInscricao statusInscricao;


    public Inscricao(Evento evento, Participante participante, EStatusInscricao statusInscricao, LocalDateTime dataInscricao) {
        this.evento = evento;
        this.participante = participante;
        this.statusInscricao = statusInscricao;
        this.dataInscricao = dataInscricao;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EStatusInscricao getStatusInscricao() {
        return statusInscricao;
    }

    public void setStatusInscricao(EStatusInscricao statusInscricao) {
        this.statusInscricao = statusInscricao;
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
                "| dataInscricao: " + dataInscricao.format(formatter) + "| statusInscricao: " + statusInscricao;
    }
}
