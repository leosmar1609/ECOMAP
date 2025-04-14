package entity;

public class pontosdecoleta {
    private int codpontocoleta;
    private String endpontocoleta;
    private String ceppontocoleta;
    private String bairropontocoleta;

    public String getBairropontocoleta() {
        return bairropontocoleta;
    }
    public void setBairropontocoleta(String bairropontocoleta) {
        this.bairropontocoleta = bairropontocoleta;
    }
    public String getCeppontocoleta() {
        return ceppontocoleta;
    }
    public void setCeppontocoleta(String ceppontocoleta) {
        this.ceppontocoleta = ceppontocoleta;
    }
    public int getCodpontocoleta() {
        return codpontocoleta;
    }
    public void setCodpontocoleta(int codpontocoleta) {
        this.codpontocoleta = codpontocoleta;
    }
    public String getEndpontocoleta() {
        return endpontocoleta;
    }
    public void setEndpontocoleta(String endpontocoleta) {
        this.endpontocoleta = endpontocoleta;
    }
}