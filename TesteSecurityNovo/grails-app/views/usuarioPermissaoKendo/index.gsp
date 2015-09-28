<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	
	<title>
		Usuáio Permissão
	</title>
	
	<%--
	<asset:stylesheet src="highCheckTree.css" />
	<asset:javascript src="highCheckTree.js" />
    --%>
    
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
		
		<g:form action="save" >
		
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
						onChange="${remoteFunction(action: 'carregaTreeView', params:'\'id=\' + this.value', update:'example')}" />
				</div>
			
				<g:render template="form"/>
				
			</fieldset>
			
			<fieldset class="buttons">
			
				<g:submitButton name="btnSalvar" class="save" value="Salvar" />
				
			</fieldset>
			
		</g:form>
		
	</div>

</body>
</html>
