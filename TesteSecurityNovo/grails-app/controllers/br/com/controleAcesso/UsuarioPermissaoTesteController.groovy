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

		JSONArray retorno = new JSONArray()
		
		List listPermissaoGrupoMenu = PermissaoGrupoMenu.list(sort: "nome")
		
		for (permissaoGrupoMenu in listPermissaoGrupoMenu) {
			
			JSONObject retornoAux = new JSONObject()
			
			retornoAux.putAt("item", getJsonItemByPermissaoGrupoMenu(permissaoGrupoMenu))

			JSONArray jPermissaoGrupoMenuChildren = new JSONArray()
			
			List listPermissaoGrupo = PermissaoGrupo.findAllByMenu(permissaoGrupoMenu, [sort: "nome"])
			
			for (permissaoGrupo in listPermissaoGrupo) {
				
				JSONObject jPermissaoGrupoMenuChildrenAux = new JSONObject()
				
				jPermissaoGrupoMenuChildrenAux.putAt("item", getJsonItemByPermissaoGrupo(permissaoGrupo))
				
				JSONArray jPermissaoGrupoItem = new JSONArray()
				
				List listPermissao = Permissao.findAllByGrupo(permissaoGrupo, [sort: "descricao"])
				
				for (permissao in listPermissao) {
					
					jPermissaoGrupoItem.add(getJsonItemByPermissao(permissao))
					
				}
				
				jPermissaoGrupoMenuChildrenAux.putAt("children", jPermissaoGrupoItem)
				
				jPermissaoGrupoMenuChildren.add(jPermissaoGrupoMenuChildrenAux)
				
			}
			
			retornoAux.putAt("children", jPermissaoGrupoMenuChildren)
			
			retorno.add(retornoAux)
			
		}
		
		[retorno: retorno.toString().replaceAll("\"", "")]
				
	}

	private JSONObject getJsonItemByPermissao(Permissao permissao) {
		
		JSONObject jPermissao = new JSONObject()

		jPermissao.putAt("id", "'" + permissao.id + "'")
		jPermissao.putAt("label", "'" + permissao.descricao + "'")
		jPermissao.putAt("checked", false)

		JSONObject jPermissaoItem = new JSONObject()

		jPermissaoItem.putAt("item", jPermissao)
		
		return jPermissaoItem
		
	}

	private JSONObject getJsonItemByPermissaoGrupo(PermissaoGrupo permissaoGrupo) {
		
		JSONObject jPermissaoGrupo = new JSONObject()

		jPermissaoGrupo.putAt("id", "'" + permissaoGrupo.id + "r'")
		jPermissaoGrupo.putAt("label", "'" + permissaoGrupo.nome + "'")
		jPermissaoGrupo.putAt("checked", false)

		return jPermissaoGrupo
		
	}

	private JSONObject getJsonItemByPermissaoGrupoMenu(PermissaoGrupoMenu permissaoGrupoMenu) {
		
		JSONObject jPermissaoGrupoMenu = new JSONObject()

		jPermissaoGrupoMenu.putAt("id", "'" + permissaoGrupoMenu.id + "m'")
		jPermissaoGrupoMenu.putAt("label", "'" + permissaoGrupoMenu.nome + "'")
		jPermissaoGrupoMenu.putAt("checked", false)

		return jPermissaoGrupoMenu
		
	}
	
}
