package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class PermissaoGrupoMenuController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PermissaoGrupoMenu.list(params), model:[permissaoGrupoMenuInstanceCount: PermissaoGrupoMenu.count()]
    }

    def show(PermissaoGrupoMenu permissaoGrupoMenuInstance) {
        respond permissaoGrupoMenuInstance
    }

    def create() {
        respond new PermissaoGrupoMenu(params)
    }

    @Transactional
    def save(PermissaoGrupoMenu permissaoGrupoMenuInstance) {
        if (permissaoGrupoMenuInstance == null) {
            notFound()
            return
        }

        if (permissaoGrupoMenuInstance.hasErrors()) {
            respond permissaoGrupoMenuInstance.errors, view:'create'
            return
        }

        permissaoGrupoMenuInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'permissaoGrupoMenu.label', default: 'PermissaoGrupoMenu'), permissaoGrupoMenuInstance.id])
                redirect permissaoGrupoMenuInstance
            }
            '*' { respond permissaoGrupoMenuInstance, [status: CREATED] }
        }
    }

    def edit(PermissaoGrupoMenu permissaoGrupoMenuInstance) {
        respond permissaoGrupoMenuInstance
    }

    @Transactional
    def update(PermissaoGrupoMenu permissaoGrupoMenuInstance) {
        if (permissaoGrupoMenuInstance == null) {
            notFound()
            return
        }

        if (permissaoGrupoMenuInstance.hasErrors()) {
            respond permissaoGrupoMenuInstance.errors, view:'edit'
            return
        }

        permissaoGrupoMenuInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'PermissaoGrupoMenu.label', default: 'PermissaoGrupoMenu'), permissaoGrupoMenuInstance.id])
                redirect permissaoGrupoMenuInstance
            }
            '*'{ respond permissaoGrupoMenuInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(PermissaoGrupoMenu permissaoGrupoMenuInstance) {

        if (permissaoGrupoMenuInstance == null) {
            notFound()
            return
        }

        permissaoGrupoMenuInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'PermissaoGrupoMenu.label', default: 'PermissaoGrupoMenu'), permissaoGrupoMenuInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'permissaoGrupoMenu.label', default: 'PermissaoGrupoMenu'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
