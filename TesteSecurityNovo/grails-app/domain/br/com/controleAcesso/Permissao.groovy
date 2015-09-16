package br.com.controleAcesso

class Permissao implements Serializable {

	private static final long serialVersionUID = 1

	String authority
	String descricao
	PermissaoGrupo grupo

	Permissao(String authority) {
		this()
		this.authority = authority
	}

	@Override
	int hashCode() {
		authority?.hashCode() ?: 0
	}

	@Override
	boolean equals(other) {
		is(other) || (other instanceof Permissao && other.authority == authority)
	}

	@Override
	String toString() {
		authority
	}

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
