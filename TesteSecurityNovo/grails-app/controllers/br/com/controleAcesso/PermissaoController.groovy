package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class PermissaoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Permissao.list(params), model:[permissaoInstanceCount: Permissao.count()]
    }

    def show(Permissao permissaoInstance) {
        respond permissaoInstance
    }

    def create() {
        respond new Permissao(params)
    }

    @Transactional
    def save(Permissao permissaoInstance) {
        if (permissaoInstance == null) {
            notFound()
            return
        }

        if (permissaoInstance.hasErrors()) {
            respond permissaoInstance.errors, view:'create'
            return
        }

        permissaoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'permissao.label', default: 'Permissao'), permissaoInstance.id])
                redirect permissaoInstance
            }
            '*' { respond permissaoInstance, [status: CREATED] }
        }
    }

    def edit(Permissao permissaoInstance) {
        respond permissaoInstance
    }

    @Transactional
    def update(Permissao permissaoInstance) {
        if (permissaoInstance == null) {
            notFound()
            return
        }

        if (permissaoInstance.hasErrors()) {
            respond permissaoInstance.errors, view:'edit'
            return
        }

        permissaoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Permissao.label', default: 'Permissao'), permissaoInstance.id])
                redirect permissaoInstance
            }
            '*'{ respond permissaoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Permissao permissaoInstance) {

        if (permissaoInstance == null) {
            notFound()
            return
        }

        permissaoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Permissao.label', default: 'Permissao'), permissaoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'permissao.label', default: 'Permissao'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
