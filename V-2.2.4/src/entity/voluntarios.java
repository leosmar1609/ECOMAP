package entity;

public class voluntarios {
    private int codvol;
    private String cpfvol;
    private String nomevol;
    private String fonevol;
    private String emailvol;
    private String senhavol;
    private String voluntarioAtual;
    private String estadovol;

    public String getEstadovol() {
        return estadovol;
    }
    public void setEstadovol(String estadovol) {
        this.estadovol = estadovol;
    }
    public String getCpfvol() {
        return cpfvol;
    }
    public void setCpfvol(String cpfvol) {
        this.cpfvol = cpfvol;
    }
    public String getEmailvol() {
        return emailvol;
    }
    public void setEmailvol(String emailvol) {
        this.emailvol = emailvol;
    }
    public String getFonevol() {
        return fonevol;
    }
    public void setFonevol(String fonevol) {
        this.fonevol = fonevol;
    }
    public String getNomevol() {
        return nomevol;
    }
    public void setNomevol(String nomevol) {
        this.nomevol = nomevol;
    }
    public String getSenhavol() {
        return senhavol;
    }
    public void setSenhavol(String senhavol) {
        this.senhavol = senhavol;
    }
    public String getVoluntarioAtual() {
        return voluntarioAtual;
    }
    public void setVoluntarioAtual(String voluntarioAtual) {
        this.voluntarioAtual = voluntarioAtual;
    }
    public int getCodvol() {
        return codvol;
    }
    public void setCodvol(int codvol) {
        this.codvol = codvol;
    }
}