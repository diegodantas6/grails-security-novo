<%@ page import="br.com.controleAcesso.UsuarioGrupo" %>



<div class="fieldcontain ${hasErrors(bean: usuarioGrupoInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="usuarioGrupo.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${usuarioGrupoInstance?.nome}"/>

</div>

