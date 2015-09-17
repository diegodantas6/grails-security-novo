<%@ page import="br.com.controleAcesso.UsuarioPermissao" %>



<div class="fieldcontain ${hasErrors(bean: usuarioPermissaoInstance, field: 'permissao', 'error')} required">
	<label for="permissao">
		<g:message code="usuarioPermissao.permissao.label" default="Permissao" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="permissao" name="permissao.id" from="${br.com.controleAcesso.Permissao.list()}" optionKey="id" required="" value="${usuarioPermissaoInstance?.permissao?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: usuarioPermissaoInstance, field: 'usuario', 'error')} required">
	<label for="usuario">
		<g:message code="usuarioPermissao.usuario.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="usuario" name="usuario.id" from="${br.com.controleAcesso.Usuario.list()}" optionKey="id" required="" value="${usuarioPermissaoInstance?.usuario?.id}" class="many-to-one"/>

</div>

