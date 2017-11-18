package alugueluguebrasilnovo.alugueluguebrasil.objetosUsados;

/**
 * Created by Jacson on 14/10/2017.
 */

public class PessoaFisica {

    private String pfId;
    private String nome;
    private String sobrenome;
    private String email;
    private String tel;
    private String cpf;
    private String senha;

    public PessoaFisica() {
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPfId() {
        return pfId;
    }

    public void setPfId(String pfId) {
        this.pfId = pfId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nome;
    }
}
