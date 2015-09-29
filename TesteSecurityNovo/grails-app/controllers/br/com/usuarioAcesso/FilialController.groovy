package br.com.usuarioAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class FilialController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Filial.list(params), model:[filialInstanceCount: Filial.count()]
    }

    def show(Filial filialInstance) {
        respond filialInstance
    }

    def create() {
        respond new Filial(params)
    }

    @Transactional
    def save(Filial filialInstance) {
        if (filialInstance == null) {
            notFound()
            return
        }

        if (filialInstance.hasErrors()) {
            respond filialInstance.errors, view:'create'
            return
        }

        filialInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'filial.label', default: 'Filial'), filialInstance.id])
                redirect filialInstance
            }
            '*' { respond filialInstance, [status: CREATED] }
        }
    }

    def edit(Filial filialInstance) {
        respond filialInstance
    }

    @Transactional
    def update(Filial filialInstance) {
        if (filialInstance == null) {
            notFound()
            return
        }

        if (filialInstance.hasErrors()) {
            respond filialInstance.errors, view:'edit'
            return
        }

        filialInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Filial.label', default: 'Filial'), filialInstance.id])
                redirect filialInstance
            }
            '*'{ respond filialInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Filial filialInstance) {

        if (filialInstance == null) {
            notFound()
            return
        }

        filialInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Filial.label', default: 'Filial'), filialInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'filial.label', default: 'Filial'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
