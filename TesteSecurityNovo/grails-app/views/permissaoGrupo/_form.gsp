<%@ page import="br.com.controleAcesso.PermissaoGrupo" %>



<div class="fieldcontain ${hasErrors(bean: permissaoGrupoInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="permissaoGrupo.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${permissaoGrupoInstance?.nome}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: permissaoGrupoInstance, field: 'menu', 'error')} required">
	<label for="menu">
		<g:message code="permissaoGrupo.menu.label" default="Menu" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="menu" name="menu.id" from="${br.com.controleAcesso.PermissaoGrupoMenu.list()}" optionKey="id" required="" value="${permissaoGrupoInstance?.menu?.id}" class="many-to-one"/>

</div>

