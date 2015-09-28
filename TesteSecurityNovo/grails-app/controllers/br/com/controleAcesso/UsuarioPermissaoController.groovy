package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class UsuarioPermissaoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UsuarioPermissao.list(params), model:[usuarioPermissaoInstanceCount: UsuarioPermissao.count()]
    }

    def show(UsuarioPermissao usuarioPermissaoInstance) {
		
		println "passei aqui show"
		
        respond usuarioPermissaoInstance
    }

    def create() {
        respond new UsuarioPermissao(params)
    }

    @Transactional
    def save(UsuarioPermissao usuarioPermissaoInstance) {
		
		println "passei aqui save"
		
		if (usuarioPermissaoInstance.usuario == null) {
			println "favor dar erro"
            notFound()
            return
		}
		
        if (usuarioPermissaoInstance == null) {
            notFound()
            return
        }

        if (usuarioPermissaoInstance.hasErrors()) {
            respond usuarioPermissaoInstance.errors, view:'create'
            return
        }

        usuarioPermissaoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarioPermissao.label', default: 'UsuarioPermissao'), usuarioPermissaoInstance.id])
                redirect usuarioPermissaoInstance
            }
            '*' { respond usuarioPermissaoInstance, [status: CREATED] }
        }
    }

    def edit(UsuarioPermissao usuarioPermissaoInstance) {
		
		println "passei aqui edit"
		
        respond usuarioPermissaoInstance
    }

    @Transactional
    def update(UsuarioPermissao usuarioPermissaoInstance) {
		
		println "passei aqui update"
		
        if (usuarioPermissaoInstance == null) {
            notFound()
            return
        }

        if (usuarioPermissaoInstance.hasErrors()) {
            respond usuarioPermissaoInstance.errors, view:'edit'
            return
        }

        usuarioPermissaoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'UsuarioPermissao.label', default: 'UsuarioPermissao'), usuarioPermissaoInstance.id])
                redirect usuarioPermissaoInstance
            }
            '*'{ respond usuarioPermissaoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(UsuarioPermissao usuarioPermissaoInstance) {

        if (usuarioPermissaoInstance == null) {
            notFound()
            return
        }

        usuarioPermissaoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'UsuarioPermissao.label', default: 'UsuarioPermissao'), usuarioPermissaoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
		
		println "passei aqui notFound"
		
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioPermissao.label', default: 'UsuarioPermissao'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def carregaListaPermissao() {
		
		println "passei aqui carregaListaPermissao"
		
		Long idUser = Long.valueOf( params.id )
		
		if (idUser > 0L) {
			
			List<Permissao> listPermissao = Permissao.executeQuery("select p from Permissao p where not exists (select 1 from UsuarioPermissao up where up.usuario.id = :idUser and up.permissao = p.id)", [idUser: idUser])
			
			render g.select ( id:'permissao', name:'permissao.id', from:listPermissao, optionKey:'id', required:'', class:'many-to-one' )
	
		} else {
			
			render g.select ( id:'permissao', name:'permissao.id', from:Permissao.list(), optionKey:'id', required:'', class:'many-to-one' )
		
		}
	}
}
