package br.com.cast.treinamento.entidades;

public class Contato implements IEntidade {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private String endereco;
	private String site;
	private String telefone;
	private Float avaliacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Float getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Float avaliacao) {
		this.avaliacao = avaliacao;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Contato) {
			return getId().equals(((Contato) o).getId());
		}
		return super.equals(o);
	}
	
	@Override
	public String toString() {
		return getNome();
	}
}
