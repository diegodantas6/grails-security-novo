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

	<div id="main-container">
		<h1>jQuery highchecktree Plugin Demo</h1>
		<div id="tree-container"></div>
		
		<input id="teste" type="text" value="${retorno}" />
		
	</div>

	<script>
		$('#tree-container').highCheckTree({
			//data : $('#teste').val();
			data : "${retorno}";
		});
	</script>
	
</body>
</html>
