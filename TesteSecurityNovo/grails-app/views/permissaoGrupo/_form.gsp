<%@ page import="br.com.controleAcesso.PermissaoGrupo" %>



<div class="fieldcontain ${hasErrors(bean: permissaoGrupoInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="permissaoGrupo.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${permissaoGrupoInstance?.nome}"/>

</div>

