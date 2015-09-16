<%@ page import="br.com.controleAcesso.Permissao" %>



<div class="fieldcontain ${hasErrors(bean: permissaoInstance, field: 'authority', 'error')} required">
	<label for="authority">
		<g:message code="permissao.authority.label" default="Authority" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="authority" required="" value="${permissaoInstance?.authority}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: permissaoInstance, field: 'descricao', 'error')} required">
	<label for="descricao">
		<g:message code="permissao.descricao.label" default="Descricao" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="descricao" required="" value="${permissaoInstance?.descricao}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: permissaoInstance, field: 'grupo', 'error')} required">
	<label for="grupo">
		<g:message code="permissao.grupo.label" default="Grupo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="grupo" name="grupo.id" from="${br.com.controleAcesso.PermissaoGrupo.list()}" optionKey="id" required="" value="${permissaoInstance?.grupo?.id}" class="many-to-one"/>

</div>

