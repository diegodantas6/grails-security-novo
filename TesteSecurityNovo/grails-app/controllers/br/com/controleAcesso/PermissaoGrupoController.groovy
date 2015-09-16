package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class PermissaoGrupoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond PermissaoGrupo.list(params), model:[permissaoGrupoInstanceCount: PermissaoGrupo.count()]
    }

    def show(PermissaoGrupo permissaoGrupoInstance) {
        respond permissaoGrupoInstance
    }

    def create() {
        respond new PermissaoGrupo(params)
    }

    @Transactional
    def save(PermissaoGrupo permissaoGrupoInstance) {
        if (permissaoGrupoInstance == null) {
            notFound()
            return
        }

        if (permissaoGrupoInstance.hasErrors()) {
            respond permissaoGrupoInstance.errors, view:'create'
            return
        }

        permissaoGrupoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'permissaoGrupo.label', default: 'PermissaoGrupo'), permissaoGrupoInstance.id])
                redirect permissaoGrupoInstance
            }
            '*' { respond permissaoGrupoInstance, [status: CREATED] }
        }
    }

    def edit(PermissaoGrupo permissaoGrupoInstance) {
        respond permissaoGrupoInstance
    }

    @Transactional
    def update(PermissaoGrupo permissaoGrupoInstance) {
        if (permissaoGrupoInstance == null) {
            notFound()
            return
        }

        if (permissaoGrupoInstance.hasErrors()) {
            respond permissaoGrupoInstance.errors, view:'edit'
            return
        }

        permissaoGrupoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'PermissaoGrupo.label', default: 'PermissaoGrupo'), permissaoGrupoInstance.id])
                redirect permissaoGrupoInstance
            }
            '*'{ respond permissaoGrupoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(PermissaoGrupo permissaoGrupoInstance) {

        if (permissaoGrupoInstance == null) {
            notFound()
            return
        }

        permissaoGrupoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'PermissaoGrupo.label', default: 'PermissaoGrupo'), permissaoGrupoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'permissaoGrupo.label', default: 'PermissaoGrupo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
