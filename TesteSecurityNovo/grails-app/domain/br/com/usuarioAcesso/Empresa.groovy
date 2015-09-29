package br.com.usuarioAcesso

class Empresa {
	
	String nome

    static constraints = {
		nome blank: false, unique: true
    }
	
	@Override
	String toString() {
		nome
	}

}
