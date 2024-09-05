package entity;

import java.time.LocalDateTime;

public class rastreamento {
    private String tiporesiduo;
    private int codresiduo;
    private int codvol;
    private int codpontocoleta;
    private String quantcoletada;
    private LocalDateTime datahora_coleta;

    // Getters e Setters
    public String getTiporesiduo() {
        return tiporesiduo;
    }

    public void setTiporesiduo(String tiporesiduo) {
        this.tiporesiduo = tiporesiduo;
    }

    public int getCodresiduo() {
        return codresiduo;
    }

    public void setCodresiduo(int codresiduo) {
        this.codresiduo = codresiduo;
    }

    public int getCodvol() {
        return codvol;
    }

    public void setCodvol(int codvol) {
        this.codvol = codvol;
    }

    public int getCodpontocoleta() {
        return codpontocoleta;
    }

    public void setCodpontocoleta(int codpontocoleta) {
        this.codpontocoleta = codpontocoleta;
    }

    public String getQuantcoletada() {
        return quantcoletada;
    }

    public void setQuantcoletada(String quantcoletada) {
        this.quantcoletada = quantcoletada;
    }

    public LocalDateTime getDatahora_coleta() {
        return datahora_coleta;
    }

    public void setDatahora_coleta(LocalDateTime datahora_coleta) {
        this.datahora_coleta = datahora_coleta;
    }
}