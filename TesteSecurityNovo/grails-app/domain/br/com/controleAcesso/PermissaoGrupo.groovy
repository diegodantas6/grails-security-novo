package br.com.controleAcesso

class PermissaoGrupo {

	String nome
	PermissaoGrupoMenu menu

	static constraints = {
		nome blank: false, unique: true
	}

	@Override
	String toString() {
		nome
	}
	
}
