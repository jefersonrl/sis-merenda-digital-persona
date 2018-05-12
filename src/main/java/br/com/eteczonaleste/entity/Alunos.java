 package br.com.eteczonaleste.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "alunos")
public class Alunos implements Serializable{

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    private Integer ra;
    @Column
    private String nome;
    @Column
    private String endereco;
    @Column
    private String bairro;
    @Column
    private String cep;
    @Column
    private String cidade;
    @Column
    private String estado;
    @Column
    private String telefone;
    @Column
    private String celular;
    @Column
    private String email;
    @Column
    @Lob
    private byte[] template;

    private Integer status;
    

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public Integer getRa() {
        return ra;
    }

    public void setRa(Integer ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] bs) {
        this.template = bs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((ra == null) ? 0 : ra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Alunos)) {
			return false;
		}
		Alunos other = (Alunos) obj;
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (ra == null) {
			if (other.ra != null) {
				return false;
			}
		} else if (!ra.equals(other.ra)) {
			return false;
		}
		return true;
	}
    
    
}
