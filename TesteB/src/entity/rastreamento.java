package entity;

public class rastreamento {
    private int codresiduo;
    private String tiporesiduo;
    private String quantcoletada;
    private int codpontocoleta;
    private int codvol;

    public int getCodpontocoleta() {
        return codpontocoleta;
    }
    public void setCodpontocoleta(int codpontocoleta) {
        this.codpontocoleta = codpontocoleta;
    }
    public int getCodresiduo() {
        return codresiduo;
    }
    public void setCodresiduo(int codresiduo) {
        this.codresiduo = codresiduo;
    }
    public String getQuantcoletada() {
        return quantcoletada;
    }
    public void setQuantcoletada(String quantcoletares) {
        this.quantcoletada = quantcoletares;
    }
    public String getTiporesiduo() {
        return tiporesiduo;
    }
    public void setTiporesiduo(String tiporesiduo) {
        this.tiporesiduo = tiporesiduo;
    }
    public int getCodvol() {
        return codvol;
    }
    public void setCodvol(int codvol) {
        this.codvol = codvol;
    }
}