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
        respond usuarioPermissaoInstance
    }

    def create() {
        respond new UsuarioPermissao(params)
    }

    @Transactional
    def save(UsuarioPermissao usuarioPermissaoInstance) {
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
        respond usuarioPermissaoInstance
    }

    @Transactional
    def update(UsuarioPermissao usuarioPermissaoInstance) {
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
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioPermissao.label', default: 'UsuarioPermissao'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
