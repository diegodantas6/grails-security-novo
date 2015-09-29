<%@ page import="br.com.usuarioAcesso.Filial" %>



<div class="fieldcontain ${hasErrors(bean: filialInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="filial.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${filialInstance?.nome}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: filialInstance, field: 'empresa', 'error')} required">
	<label for="empresa">
		<g:message code="filial.empresa.label" default="Empresa" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="empresa" name="empresa.id" from="${br.com.usuarioAcesso.Empresa.list()}" optionKey="id" required="" value="${filialInstance?.empresa?.id}" class="many-to-one"/>

</div>
