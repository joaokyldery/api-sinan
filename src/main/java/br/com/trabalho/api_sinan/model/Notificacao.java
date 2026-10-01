package br.com.trabalho.api_sinan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Notificacao {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;

    //RN01(verificação de dublicidade) 
    private String agravo;
    private LocalDate dataNotificacao;
    private String nomePaciente;
    private LocalDate dataNascimento;
    private String nomeMae;
    //RN02(Condicional)
    private Integer idade;
    private Boolean gestante;

    //RN03(Residência)
    private String paisResidencia;
    private String ufResidencia;
    private String municipioResidencia;

    public Notificacao() {
    }

    
    public Long getId() {
        return id;
    }


    public String getAgravo() {
        return agravo;
    }


    public LocalDate getDataNotificacao() {
        return dataNotificacao;
    }


    public String getNomePaciente() {
        return nomePaciente;
    }


    public LocalDate getDataNascimento() {
        return dataNascimento;
    }


    public String getNomeMae() {
        return nomeMae;
    }


    public Integer getIdade() {
        return idade;
    }


    public Boolean getGestante() {
        return gestante;
    }


    public String getPaisResidencia() {
        return paisResidencia;
    }


    public String getUfResidencia() {
        return ufResidencia;
    }


    public String getMunicipioResidencia() {
        return municipioResidencia;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setAgravo(String agravo) {
        this.agravo = agravo;
    }

    public void setDataNotificacao(LocalDate dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setGestante(Boolean gestante) {
        this.gestante = gestante;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    public void setUfResidencia(String ufResidencia) {
        this.ufResidencia = ufResidencia;
    }

    public void setMunicipioResidencia(String municipioResidencia) {
        this.municipioResidencia = municipioResidencia;
    }



}
