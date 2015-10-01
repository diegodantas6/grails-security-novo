
<%@ page import="br.com.controleAcesso.Permissao" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'permissao.label', default: 'Permissao')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-permissao" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
			
				<sec:ifAllGranted roles="ROLE_CREATE_USUARIO">
					<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				</sec:ifAllGranted>
				
				<sec:ifAllGranted roles="ROLE_EDIT_USUARIO">
					<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				</sec:ifAllGranted>
				
			</ul>
		</div>
		<div id="list-permissao" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="authority" title="${message(code: 'permissao.authority.label', default: 'Authority')}" />
					
						<g:sortableColumn property="descricao" title="${message(code: 'permissao.descricao.label', default: 'Descricao')}" />
					
						<th><g:message code="permissao.grupo.label" default="Grupo" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${permissaoInstanceList}" status="i" var="permissaoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${permissaoInstance.id}">${fieldValue(bean: permissaoInstance, field: "authority")}</g:link></td>
					
						<td>${fieldValue(bean: permissaoInstance, field: "descricao")}</td>
					
						<td>${fieldValue(bean: permissaoInstance, field: "grupo")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${permissaoInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
