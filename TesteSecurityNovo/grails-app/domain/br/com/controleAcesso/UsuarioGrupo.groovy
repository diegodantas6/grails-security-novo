package br.com.controleAcesso

class UsuarioGrupo {

	String nome
	
    static constraints = {
		nome blank: false, unique: true
    }
	
	@Override
	String toString() {
		nome
	}

}
