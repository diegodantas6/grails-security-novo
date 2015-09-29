
<%@ page import="br.com.controleAcesso.PermissaoGrupo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'permissaoGrupo.label', default: 'PermissaoGrupo')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-permissaoGrupo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-permissaoGrupo" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="nome" title="${message(code: 'permissaoGrupo.nome.label', default: 'Nome')}" />
					
						<th><g:message code="permissaoGrupo.menu.label" default="Menu" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${permissaoGrupoInstanceList}" status="i" var="permissaoGrupoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${permissaoGrupoInstance.id}">${fieldValue(bean: permissaoGrupoInstance, field: "nome")}</g:link></td>
					
						<td>${fieldValue(bean: permissaoGrupoInstance, field: "menu")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${permissaoGrupoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
