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
class UsuarioFilialController {

	def index() {
		
	}
		
	private String getTreeViewData( Usuario usuario, Empresa empresa ) {

		JSONArray retorno = new JSONArray()
		
		JSONObject retornoAux = getJsonMenu()
		
		JSONArray retornoAuxItems = new JSONArray()

		List listEmpresa = Empresa.list(sort: "nome")

		for (empresa2 in listEmpresa) {
			
			retornoAuxItems.add(getJsonItem(empresa2, usuario))
					
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
	
	def carregaEmpresas() {
		
		Long idUsuario = Long.valueOf( params.id )
		
		if (idUsuario > 0L) {
			
			List<Empresa> listEmpresa = Empresa.executeQuery("select e from Empresa e where exists (select 1 from UsuarioEmpresa ue where ue.usuario.id = :idUsuario and ue.empresa = e.id)", [idUsuario: idUsuario])
			
			render g.select ( id:'empresa', name:'empresa.id', from:listEmpresa, optionKey:'id', required:'', class:'many-to-one', noSelection:[0:''] )
			
		} else {
		
			render g.select ( id:'empresa', name:'empresa.id', from:null, optionKey:'id', required:'', class:'many-to-one' )
		
		}
		
	}
	
	def carregaTreeView() {
		
		Long idUsuario = Long.valueOf( params.idUsuario )
		
		Long idEmpresa = Long.valueOf( params.idEmpresa )
		
		if (idUsuario > 0L && idEmpresa > 0L) {
			
			Usuario usuario = Usuario.get( idUsuario );
			
			Empresa empresa = Empresa.get( idEmpresa );

			String retorno = getTreeViewData( usuario, empresa )
			
			render template: 'form', model: [retorno: retorno]
						
		} else {
		
			render template: 'form', model: [retorno: null]
		
		}
		
	}
	
	@Transactional
	def save() {

		Usuario usuario = Usuario.get(params.usuario.id)
		
		Empresa empresa = Empresa.get(params.empresa.id)
		
		if (usuario == null) {
			
			render("Favor selecionar o Usuário!")
			
		} else if (empresa == null) {
			
			render("Favor selecionar a Empresa!")
			
		} else {
		
			UsuarioFilial.removeAll(usuario, true)
			
			String[] filiais = params.result.toString().split(",")
			
			for (idFiliais in filiais) {
				
				if (isInteger(idFiliais)) {
					
					Filial filial = Filial.get(idFiliais)
					
					UsuarioFilial.create(usuario, filial, true)
					
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
