package entity;

public class rastreamento {
    private int codresiduo;
    private String tiporesiduo;
    private String quantcoletares;
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
    public String getQuantcoletares() {
        return quantcoletares;
    }
    public void setQuantcoletares(String quantcoletares) {
        this.quantcoletares = quantcoletares;
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