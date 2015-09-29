<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	
	<title>
		Grupo Usuáio Permissão
	</title>
	
    <asset:stylesheet href="kendo/kendo.common-material.min.css" />
    <asset:stylesheet href="kendo/kendo.material.min.css" />

    <asset:javascript src="kendo/jquery.min.js" />
    <asset:javascript src="kendo/kendo.all.min.js" />
	
</head>

<body>

	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		</ul>
	</div>

	<div id="edit-usuarioGrupoPermissao" class="content scaffold-edit" role="main">
	
		<div class="message" id="message" ></div>

		<g:formRemote  name="frmUsuarioGrupoPermissao" url="[controller: 'usuarioGrupoPermissao', action: 'save']" update="message" >
		
			<fieldset class="form">
			
				<div class="fieldcontain ${hasErrors(bean: usuarioInstance, field: 'grupo', 'error')} required">

					<label for="grupo">

						<g:message code="usuario.grupo.label" default="Grupo Usuário" />

						<span class="required-indicator">*</span>

					</label>

					<g:select id="grupo" 
						name="grupo.id" 
						from="${br.com.controleAcesso.UsuarioGrupo.list()}" 
						optionKey="id" 
						required="" 
						value="${usuarioInstance?.grupo?.id}" 
						class="many-to-one"
						noSelection="${[0: '']}" 
						onChange="${remoteFunction(action: 'carregaTreeView', params:'\'id=\' + this.value', update:'example')}" />
				
				</div>
			
				<g:render template="form"/>
				
			</fieldset>
			
			<fieldset class="buttons">
			
				<g:submitButton name="btnSalvar" class="save" value="Salvar" />
				
			</fieldset>
			
		</g:formRemote>
		
	</div>

</body>
</html>
