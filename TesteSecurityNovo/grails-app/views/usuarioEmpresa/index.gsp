<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	
	<title>
		Usuário Empresa
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

	<div id="edit-usuarioEmpresa" class="content scaffold-edit" role="main">
	
		<div class="message" id="message" ></div>

		<g:formRemote  name="frmUsuarioEmpresa" url="[controller: 'usuarioEmpresa', action: 'save']" update="message" >
		
			<fieldset class="form">
			
				<div class="fieldcontain ${hasErrors(bean: usuarioEmpresaInstance, field: 'usuario', 'error')} required">
				
					<label for="usuario">
					
						<g:message code="usuarioEmpresa.usuario.label" default="Usuario" />
						
						<span class="required-indicator">*</span>
						
					</label>
			
					<g:select id="usuario" 
						name="usuario.id" 
						from="${br.com.controleAcesso.Usuario.list()}" 
						optionKey="id" 
						required="" 
						value="${usuarioEmpresaInstance?.usuario?.id}" 
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
