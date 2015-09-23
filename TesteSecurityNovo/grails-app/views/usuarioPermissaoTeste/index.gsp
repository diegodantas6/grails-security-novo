<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	
	<title>
		Usuáio Permissão
	</title>
	
	<asset:stylesheet src="highCheckTree.css" />
	<asset:javascript src="highCheckTree.js" />
</head>

<body>

	<div class="nav" role="navigation">
		<ul>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
		</ul>
	</div>

	<div id="edit-usuarioPermissao" class="content scaffold-edit" role="main">
	
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
		
		<g:hasErrors bean="${usuarioPermissaoInstance}">
		
		<ul class="errors" role="alert">
			<g:eachError bean="${usuarioPermissaoInstance}" var="error">
			<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
			</g:eachError>
		</ul>
		
		</g:hasErrors>
		
		<g:form url="[resource:usuarioPermissaoInstance, action:'update']" method="PUT" >
		
			<fieldset class="form">
			
				<div class="fieldcontain ${hasErrors(bean: usuarioPermissaoInstance, field: 'usuario', 'error')} required">
					<label for="usuario">
						<g:message code="usuarioPermissao.usuario.label" default="Usuario" />
						<span class="required-indicator">*</span>
					</label>
			
					<g:select id="usuario" 
						name="usuario.id" 
						from="${br.com.controleAcesso.Usuario.list()}" 
						optionKey="id" 
						required="" 
						value="${usuarioPermissaoInstance?.usuario?.id}" 
						class="many-to-one"
						noSelection="${[0: '']}" 
						onChange="${remoteFunction(action: 'carregaTreeView', params:'\'id=\' + this.value', update:'tree-container')}" />
				</div>
			
				<g:render template="form"/>
				
			</fieldset>
			
			<fieldset class="buttons">
			
				<g:actionSubmit class="save" action="salvar" value="Salvar" />
				
			</fieldset>
			
		</g:form>
		
	</div>

</body>
</html>
