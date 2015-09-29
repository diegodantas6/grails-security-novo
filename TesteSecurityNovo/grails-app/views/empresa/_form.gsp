<%@ page import="br.com.usuarioAcesso.Empresa" %>



<div class="fieldcontain ${hasErrors(bean: empresaInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="empresa.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${empresaInstance?.nome}"/>

</div>

