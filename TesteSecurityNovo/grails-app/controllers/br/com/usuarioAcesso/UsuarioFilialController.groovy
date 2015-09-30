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

		List listFilial = Filial.findAllByEmpresa(empresa, [sort: "nome"])

		for (filial in listFilial) {
			
			retornoAuxItems.add(getJsonItem(filial, usuario))
					
		}
			
		retornoAux.putAt("items", retornoAuxItems)
		
		retorno.add(retornoAux)
			
		return retorno.toString().replaceAll("\"", "")
				
	}

	private JSONObject getJsonItem(Filial filial, Usuario usuario) {
		
		JSONObject json = new JSONObject()

		json.putAt("id", "'" + filial.id + "'")
		json.putAt("text", "'" + filial.nome + "'")
		
		if ( UsuarioFilial.findByUsuarioAndFilial(usuario, filial) == null ) {
			
			json.putAt("checked", false)
			
		} else {
		
			json.putAt("checked", true)
			
		}

		return json
		
	}

	private JSONObject getJsonMenu() {
		
		JSONObject json = new JSONObject()

		json.putAt("id", "'1m'")
		json.putAt("text", "'Filiais'")
		json.putAt("spriteCssClass", "'rootfolder'")
		json.putAt("expanded", true)

		return json
		
	}
	
	def carregaEmpresas() {
		
		Long idUsuario = Long.valueOf( params.id )
		
		if (idUsuario > 0L) {
			
			String strUsuario = String.valueOf( idUsuario )
			
			String param = '\'idEmpresa=\'+this.value+\'&idUsuario=' + strUsuario + '\''
			
			List<Empresa> listEmpresa = Empresa.executeQuery("select e from Empresa e where exists (select 1 from UsuarioEmpresa ue where ue.usuario.id = :idUsuario and ue.empresa = e.id)", [idUsuario: idUsuario])
			
			render g.select ( id:'empresa', name:'empresa.id', from:listEmpresa, optionKey:'id', required:'', class:'many-to-one', noSelection:[0:''], onChange:remoteFunction(action: 'carregaTreeView', params: param, update:'example') )
			
//			render g.select ( id:'empresa', name:'empresa.id', from:listEmpresa, optionKey:'id', required:'', class:'many-to-one', noSelection:[0:''], onSelect:remoteFunction(action: 'carregaTreeView', params: param, update:'example') )
			
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
		
			UsuarioFilial.removeAll(usuario, empresa)
			
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
