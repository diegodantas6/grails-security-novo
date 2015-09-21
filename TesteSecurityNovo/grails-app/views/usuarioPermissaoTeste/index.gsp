<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	
	<g:set var="entityName"	value="${message(code: 'usuarioPermissao.label', default: 'UsuarioPermissao')}" />
	
	<title>
		<g:message code="default.list.label" args="[entityName]" />
	</title>
	
	<asset:stylesheet src="highCheckTree.css" />
	<asset:javascript src="highCheckTree.js" />
</head>

<body>

	<div id="main-container" align="center">

		<g:select id="usuario" 
			name="usuario.id" 
			from="${br.com.controleAcesso.Usuario.list()}" 
			optionKey="id" 
			required="" 
			value="${usuarioPermissaoInstance?.usuario?.id}" 
			class="many-to-one" 
			onChange="${remoteFunction(action: 'bookByName')}" />
		
		<div id="tree-container"></div>
		
	</div>

	<script>
		$(document).ready(function() {
			$('#tree-container').highCheckTree({
				data : ${raw(retorno)}
			});
		});
	</script>
	
</body>
</html>
