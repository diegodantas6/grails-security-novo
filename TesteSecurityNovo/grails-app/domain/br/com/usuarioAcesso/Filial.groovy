package br.com.usuarioAcesso

class Filial {
	
	String nome
	Empresa empresa

    static constraints = {
		nome blank: false, unique: true
		empresa blank: false
    }
}
