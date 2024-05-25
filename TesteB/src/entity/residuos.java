package entity;

public class residuos {
    private int codresiduo;
    private String tiporesiduo;
    private String categoria;
    private String descricao;
    private String descarte;

    public residuos() {
    }

    public residuos(String tiporesiduo, String categoria, String descricao, String descarte) {
        this.tiporesiduo = tiporesiduo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.descarte = descarte;
    }
    

    public String getTiporesiduo() {
        return tiporesiduo;
    }

    public void setTiporesiduo(String tiporesiduo) {
        this.tiporesiduo = tiporesiduo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescarte() {
        return descarte;
    }

    public void setDescarte(String descarte) {
        this.descarte = descarte;
    }

    public int getCodresiduo() {
        return codresiduo;
    }

    public void setCodresiduo(int codresiduo) {
        this.codresiduo = codresiduo;
    }
}
