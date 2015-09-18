package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class UsuarioPermissaoTesteController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index() {
		
		List listGrupo = PermissaoGrupo.list()
		List<String> retorno = new ArrayList<String>()
		
		JSONArray retorno2 = new JSONArray()
		
		for (i in listGrupo) {

			PermissaoGrupo g = (PermissaoGrupo) i
			
			JSONObject jGrupo = new JSONObject()
			
			jGrupo.putAt("id", g.id)
			jGrupo.putAt("label", g.nome)
			jGrupo.putAt("checked", false)
			
			JSONObject jGrupoItem = new JSONObject()
			
			jGrupoItem.putAt("item", jGrupo)

			List listPermissao = Permissao.findAllByGrupo(i)

			if (!(listPermissao.empty)) {

				List<String> retornoPermissao = new ArrayList<String>()
				
				JSONArray jRetornoPermissaoList = new JSONArray()
				
				for (z in listPermissao) {

					Permissao p = (Permissao) z

					retornoPermissao.add(item: [id: p.id, label: p.descricao, checked: false])

					JSONObject jRetornoPermissao = new JSONObject()
										
					jRetornoPermissao.putAt("id", p.id)
					jRetornoPermissao.putAt("label", p.descricao)
					jRetornoPermissao.putAt("checked", false)
					
					JSONObject jRetornoPermissaoItem = new JSONObject()
					
					jRetornoPermissaoItem.putAt("item", jRetornoPermissao)
					
					jRetornoPermissaoList.add(jRetornoPermissaoItem)
				}
				
				jGrupoItem.putAt("children", jRetornoPermissaoList)
				
				retorno2.add(jGrupoItem)
				
				
//				retorno.add(item: [id: g.id, label: g.nome , checked: false], children: retornoPermissao).toString()
				retorno.add(item: [id: g.id, label: g.nome , checked: false], children: retornoPermissao)
			}
		}

		println retorno2
		
		[retorno : retorno2.toString().replaceAll("\"", "")]
		
//		JSON.use('deep')
//		def converter = retorno as JSON
//		String vai = converter.toString()
		
//		println vai;
//		println retorno
		
//		[retorno : vai]
//		[retorno : retorno.toString().replaceAll("=", ":")]
//		[retorno : retorno]
//		[retorno : retorno.toString()]
//		[retorno : retorno as JSON]
		
//		render(view: 'index.gsp', model: [retorno: vai])
		
	}
}
