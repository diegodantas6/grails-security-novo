<%@ page import="br.com.teste.Cliente" %>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="cliente.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${clienteInstance?.nome}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'cpf', 'error')} required">
	<label for="cpf">
		<g:message code="cliente.cpf.label" default="Cpf" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="cpf" required="" value="${clienteInstance?.cpf}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: clienteInstance, field: 'dataNascimento', 'error')} required">
	<label for="dataNascimento">
		<g:message code="cliente.dataNascimento.label" default="Data de Nascimento" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="dataNascimento" required="" value="${clienteInstance?.dataNascimento}"/>

</div>

