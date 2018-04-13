package br.com.eteczonaleste.entity;

import javax.persistence.*;

@Entity
@Table(name="funcionarios")
public class Funcionarios{

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
        @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private String nome;

        @Column
	private String usuario;
        
        @Column
	private String senha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
	
}
