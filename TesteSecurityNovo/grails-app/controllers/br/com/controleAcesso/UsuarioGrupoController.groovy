package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class UsuarioGrupoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond UsuarioGrupo.list(params), model:[usuarioGrupoInstanceCount: UsuarioGrupo.count()]
    }

    def show(UsuarioGrupo usuarioGrupoInstance) {
        respond usuarioGrupoInstance
    }

    def create() {
        respond new UsuarioGrupo(params)
    }

    @Transactional
    def save(UsuarioGrupo usuarioGrupoInstance) {
        if (usuarioGrupoInstance == null) {
            notFound()
            return
        }

        if (usuarioGrupoInstance.hasErrors()) {
            respond usuarioGrupoInstance.errors, view:'create'
            return
        }

        usuarioGrupoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), usuarioGrupoInstance.id])
                redirect usuarioGrupoInstance
            }
            '*' { respond usuarioGrupoInstance, [status: CREATED] }
        }
    }

    def edit(UsuarioGrupo usuarioGrupoInstance) {
        respond usuarioGrupoInstance
    }

    @Transactional
    def update(UsuarioGrupo usuarioGrupoInstance) {
        if (usuarioGrupoInstance == null) {
            notFound()
            return
        }

        if (usuarioGrupoInstance.hasErrors()) {
            respond usuarioGrupoInstance.errors, view:'edit'
            return
        }

        usuarioGrupoInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'UsuarioGrupo.label', default: 'UsuarioGrupo'), usuarioGrupoInstance.id])
                redirect usuarioGrupoInstance
            }
            '*'{ respond usuarioGrupoInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(UsuarioGrupo usuarioGrupoInstance) {

        if (usuarioGrupoInstance == null) {
            notFound()
            return
        }

        usuarioGrupoInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'UsuarioGrupo.label', default: 'UsuarioGrupo'), usuarioGrupoInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioGrupo.label', default: 'UsuarioGrupo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
