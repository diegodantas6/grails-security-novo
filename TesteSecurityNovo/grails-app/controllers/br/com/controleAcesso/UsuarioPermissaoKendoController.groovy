package br.com.controleAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional;

import org.codehaus.groovy.grails.cli.support.UaaEnabler;
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class UsuarioPermissaoKendoController {

	def index() {
		
	}
		
	private String getTreeViewData( Usuario usuario ) {

		JSONArray retorno = new JSONArray()
		
		List listPermissaoGrupoMenu = PermissaoGrupoMenu.list(sort: "nome")
		
		for (permissaoGrupoMenu in listPermissaoGrupoMenu) {
			
			JSONObject retornoAux = getJsonItemByPermissaoGrupoMenu(permissaoGrupoMenu)

			JSONArray retornoAuxItems1 = new JSONArray()
			
			List listPermissaoGrupo = PermissaoGrupo.findAllByMenu(permissaoGrupoMenu, [sort: "nome"])
			
			for (permissaoGrupo in listPermissaoGrupo) {
				
				JSONObject itemAux = getJsonItemByPermissaoGrupo(permissaoGrupo)
				
				JSONArray retornoAuxItems2 = new JSONArray()
				
				List listPermissao = Permissao.findAllByGrupo(permissaoGrupo, [sort: "descricao"])
				
				for (permissao in listPermissao) {
					
					retornoAuxItems2.add(getJsonItemByPermissao(permissao, usuario))
					
				}
				
				itemAux.putAt("items", retornoAuxItems2)
				
				retornoAuxItems1.add(itemAux)
				
			}
			
			retornoAux.putAt("items", retornoAuxItems1)
			
			retorno.add(retornoAux)
			
		}
		
		return retorno.toString().replaceAll("\"", "")
				
	}

	private JSONObject getJsonItemByPermissao(Permissao permissao, Usuario usuario) {
		
		JSONObject jPermissao = new JSONObject()

		jPermissao.putAt("id", "'" + permissao.id + "'")
		jPermissao.putAt("text", "'" + permissao.descricao + "'")
		
		if ( UsuarioPermissao.findByUsuarioAndPermissao(usuario, permissao) == null ) {
			
			jPermissao.putAt("checked", false)
			
		} else {
		
			jPermissao.putAt("checked", true)
			
		}

		return jPermissao
		
	}

	private JSONObject getJsonItemByPermissaoGrupo(PermissaoGrupo permissaoGrupo) {
		
		JSONObject jPermissaoGrupo = new JSONObject()

		jPermissaoGrupo.putAt("id", "'" + permissaoGrupo.id + "r'")
		jPermissaoGrupo.putAt("text", "'" + permissaoGrupo.nome + "'")
		jPermissaoGrupo.putAt("spriteCssClass", "'folder'")
		jPermissaoGrupo.putAt("expanded", true)

		return jPermissaoGrupo
		
	}

	private JSONObject getJsonItemByPermissaoGrupoMenu(PermissaoGrupoMenu permissaoGrupoMenu) {
		
		JSONObject jPermissaoGrupoMenu = new JSONObject()

		jPermissaoGrupoMenu.putAt("id", "'" + permissaoGrupoMenu.id + "m'")
		jPermissaoGrupoMenu.putAt("text", "'" + permissaoGrupoMenu.nome + "'")
		jPermissaoGrupoMenu.putAt("spriteCssClass", "'rootfolder'")
		jPermissaoGrupoMenu.putAt("expanded", true)

		return jPermissaoGrupoMenu
		
	}
	
	def carregaTreeView() {
		
		Long idUser = Long.valueOf( params.id )
		
		if (idUser > 0L) {
			
			Usuario usuario = Usuario.get( idUser );

			String retorno = getTreeViewData( usuario )
			
			render template: 'form', model: [retorno: retorno]
						
		} else {
		
			render template: 'form', model: [retorno: null]
		
		}
		
	}
	
	@Transactional
	def save(UsuarioPermissao usuarioPermissaoInstance) {

		Usuario usuario = Usuario.get(params.usuario.id)
		
		if (usuario != null) {
		
			UsuarioPermissao.removeAll(usuario, true)
			
			String[] permissoes = params.result.toString().split(",")
			
			for (idPermissao in permissoes) {
				
				if (isInteger(idPermissao)) {
					
					Permissao permissao = Permissao.get(idPermissao)
					
					UsuarioPermissao usuarioPermissao = new UsuarioPermissao(usuario: usuario, permissao: permissao)
					
					usuarioPermissao.save flush:true
				}
				
			}
				
		}
		
	}
	
	private boolean isInteger(String string) {
		try {
			Integer.parseInt(string)
			return true
		} catch (Exception e) {
			return false
		}
	}
	
}
