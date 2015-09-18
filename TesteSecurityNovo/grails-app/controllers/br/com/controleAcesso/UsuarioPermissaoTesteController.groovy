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
			
			JSONObject jPermissaoGrupoMenu = new JSONObject()
			
			jPermissaoGrupoMenu.putAt("id", "'" + permissaoGrupoMenu.id + "'")
			jPermissaoGrupoMenu.putAt("label", "'" + permissaoGrupoMenu.nome + "'")
			jPermissaoGrupoMenu.putAt("checked", false)
			
			JSONObject jPermissaoGrupoMenuItem = new JSONObject()
			
			jPermissaoGrupoMenuItem.putAt("item", jPermissaoGrupoMenu)
			
			retorno.add(jPermissaoGrupoMenuItem)

			JSONArray jPermissaoGrupoMenuChildren = new JSONArray()
			
			List listPermissaoGrupo = PermissaoGrupo.findAllByMenu(permissaoGrupoMenu, [sort: "nome"])
			
			for (permissaoGrupo in listPermissaoGrupo) {
				
				JSONObject jPermissaoGrupo = new JSONObject()
				
				jPermissaoGrupo.putAt("id", "'" + permissaoGrupo.id + "'")
				jPermissaoGrupo.putAt("label", "'" + permissaoGrupo.nome + "'")
				jPermissaoGrupo.putAt("checked", false)
	
				JSONObject jPermissaoGrupoItem = new JSONObject()
				
				jPermissaoGrupoItem.putAt("item", jPermissaoGrupo)
				
				jPermissaoGrupoMenuChildren.add(jPermissaoGrupoItem)
				
				JSONArray jPermissaoGrupoChildren = new JSONArray()
				
				List listPermissao = Permissao.findAllByGrupo(permissaoGrupo, [sort: "descricao"])
				
				for (permissao in listPermissao) {
					
					JSONObject jPermissao = new JSONObject()
					
					jPermissao.putAt("id", "'" + permissao.id + "'")
					jPermissao.putAt("label", "'" + permissao.descricao + "'")
					jPermissao.putAt("checked", false)
	
					JSONObject jPermissaoItem = new JSONObject()
					
					jPermissaoItem.putAt("item", jPermissao)
					
					jPermissaoGrupoChildren.add(jPermissaoItem)
					
				}

				
				
			}
			
			retorno.add(jPermissaoGrupoMenuChildren)
			
		}
		
		[retorno: retorno.toString().replaceAll("\"", "")]
				
	}
	
	// DANDO CERTO - sem o menu
	def index2() {
		
		List listGrupo = PermissaoGrupo.list(sort: "nome")
		
		JSONArray retorno = new JSONArray()
		
		for (i in listGrupo) {

			PermissaoGrupo g = (PermissaoGrupo) i
			
			JSONObject jGrupo = new JSONObject()
			
			jGrupo.putAt("id", "'" + g.id + "'")
			jGrupo.putAt("label", "'" + g.nome + "'")
			jGrupo.putAt("checked", false)
			
			JSONObject jGrupoItem = new JSONObject()
			
			jGrupoItem.putAt("item", jGrupo)

			List listPermissao = Permissao.findAllByGrupo(i)
			
			if (!(listPermissao.empty)) {

				JSONArray jRetornoPermissaoList = new JSONArray()
				
				for (z in listPermissao) {

					Permissao p = (Permissao) z

					JSONObject jRetornoPermissao = new JSONObject()
										
					jRetornoPermissao.putAt("id", "'" + p.id + "'")
					jRetornoPermissao.putAt("label", "'" + p.descricao + "'")
					
					if (p.descricao.equals("Alterar") || g.nome.equals("Cliente")) {
						jRetornoPermissao.putAt("checked", true)
					} else {
						jRetornoPermissao.putAt("checked", false)
					}
					
					JSONObject jRetornoPermissaoItem = new JSONObject()
					
					jRetornoPermissaoItem.putAt("item", jRetornoPermissao)
					
					jRetornoPermissaoList.add(jRetornoPermissaoItem)
				}
				
				jGrupoItem.putAt("children", jRetornoPermissaoList)
				
				retorno.add(jGrupoItem)
				
			}
		}

		[retorno : retorno.toString().replaceAll("\"", "")]
		
	}
}
