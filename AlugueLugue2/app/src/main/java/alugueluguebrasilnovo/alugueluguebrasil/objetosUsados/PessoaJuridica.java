package alugueluguebrasilnovo.alugueluguebrasil.objetosUsados;

/**
 * Created by Jacson on 28/10/2017.
 */

public class PessoaJuridica {

    private String pfId;
    private String razaoS;
    private String inscricaoE;
    private String email;
    private String tel;
    private String cnpj;
    private String senha;


    public PessoaJuridica() {
    }

    public String getPfId() {
        return pfId;
    }

    public void setPfId(String pfId) {
        this.pfId = pfId;
    }

    public String getRazaoS() {
        return razaoS;
    }

    public void setRazaoS(String razaoS) {
        this.razaoS = razaoS;
    }

    public String getInscricaoE() {
        return inscricaoE;
    }

    public void setInscricaoE(String inscricaoE) {
        this.inscricaoE = inscricaoE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return razaoS;
    }



}
