package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class UsuarioPermissaoTesteController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index() {
		List listGrupo = PermissaoGrupo.list()
		List<String> retorno = new ArrayList<String>()

		for (i in listGrupo) {

			PermissaoGrupo g = (PermissaoGrupo) i

			List listPermissao = Permissao.findAllByGrupo(i)

			if (!(listPermissao.empty)) {

				List<String> retornoPermissao = new ArrayList<String>()

				for (z in listPermissao) {

					Permissao p = (Permissao) z

					retornoPermissao.add(item: [id: p.id, label: p.descricao, checked: false])
				}

				retorno.add(item: [id: g.id, label: g.nome , checked: false], children: retornoPermissao).toString();
			}
		}

		JSON.use('deep')
		def converter = retorno as JSON
		String vai = converter.toString()
		println vai;

//		println retorno
		
		[retorno : vai]
//		[retorno : retorno]
//		[retorno : retorno.toString()]
//		[retorno : retorno as JSON]
		
	}
}
