package br.com.usuarioAcesso



import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

import br.com.controleAcesso.Permissao
import br.com.controleAcesso.PermissaoGrupo
import br.com.controleAcesso.PermissaoGrupoMenu
import br.com.controleAcesso.Usuario
import br.com.controleAcesso.UsuarioPermissao

@Secured("IS_AUTHENTICATED_ANONYMOUSLY")
@Transactional(readOnly = true)
class UsuarioEmpresaController {

	def index() {
		
	}
		
	private String getTreeViewData( Usuario usuario ) {

		JSONArray retorno = new JSONArray()
		
		JSONObject retornoAux = getJsonMenu()
		
		JSONArray retornoAuxItems = new JSONArray()

		List listEmpresa = Empresa.list(sort: "nome")

		for (empresa in listEmpresa) {
			
			retornoAuxItems.add(getJsonItem(empresa, usuario))
					
		}
			
		retornoAux.putAt("items", retornoAuxItems)
		
		retorno.add(retornoAux)
			
		return retorno.toString().replaceAll("\"", "")
				
	}

	private JSONObject getJsonItem(Empresa empresa, Usuario usuario) {
		
		JSONObject json = new JSONObject()

		json.putAt("id", "'" + empresa.id + "'")
		json.putAt("text", "'" + empresa.nome + "'")
		
		if ( UsuarioEmpresa.findByUsuarioAndEmpresa(usuario, empresa) == null ) {
			
			json.putAt("checked", false)
			
		} else {
		
			json.putAt("checked", true)
			
		}

		return json
		
	}

	private JSONObject getJsonMenu() {
		
		JSONObject json = new JSONObject()

		json.putAt("id", "'1m'")
		json.putAt("text", "'Empresas'")
		json.putAt("spriteCssClass", "'rootfolder'")
		json.putAt("expanded", true)

		return json
		
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
	def save() {

		Usuario usuario = Usuario.get(params.usuario.id)
		
		if (usuario == null) {
			
			render("Favor selecionar o Usuário!")
			
		} else {
		
			UsuarioEmpresa.removeAll(usuario, true)
			
			String[] empresas = params.result.toString().split(",")
			
			for (idEmpresas in empresas) {
				
				if (isInteger(idEmpresas)) {
					
					Empresa empresa = Empresa.get(idEmpresas)
					
					UsuarioEmpresa.create(usuario, empresa, true)
					
				}
				
			}
				
			render("Salvo com sucesso!")
			
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
