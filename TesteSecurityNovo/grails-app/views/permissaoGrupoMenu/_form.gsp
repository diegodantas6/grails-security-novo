<%@ page import="br.com.controleAcesso.PermissaoGrupoMenu" %>



<div class="fieldcontain ${hasErrors(bean: permissaoGrupoMenuInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="permissaoGrupoMenu.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${permissaoGrupoMenuInstance?.nome}"/>

</div>

