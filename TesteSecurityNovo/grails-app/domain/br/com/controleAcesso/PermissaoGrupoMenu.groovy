package br.com.controleAcesso

class PermissaoGrupoMenu {
	
	String nome

	static constraints = {
		nome blank: false, unique: true
	}

	@Override
	String toString() {
		nome
	}
}
