package br.com.controleAcesso

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioPermissao implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Permissao permissao

	UsuarioPermissao(Usuario u, Permissao r) {
		this()
		usuario = u
		permissao = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UsuarioPermissao)) {
			return false
		}

		other.usuario?.id == usuario?.id && other.permissao?.id == permissao?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (permissao) builder.append(permissao.id)
		builder.toHashCode()
	}

	static UsuarioPermissao get(long usuarioId, long permissaoId) {
		criteriaFor(usuarioId, permissaoId).get()
	}

	static boolean exists(long usuarioId, long permissaoId) {
		criteriaFor(usuarioId, permissaoId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long permissaoId) {
		UsuarioPermissao.where {
			usuario == Usuario.load(usuarioId) &&
			permissao == Permissao.load(permissaoId)
		}
	}

	static UsuarioPermissao create(Usuario usuario, Permissao permissao, boolean flush = false) {
		def instance = new UsuarioPermissao(usuario, permissao)
		instance.save(flush: flush, insert: true)
		instance
	}

	static void removeAll(Usuario u, boolean flush = false) {
		if (u == null) return

		UsuarioPermissao.where { usuario == u }.deleteAll()

		if (flush) { UsuarioPermissao.withSession { it.flush() } }
	}

	static constraints = {
		permissao validator: { Permissao r, UsuarioPermissao ur ->
			if (ur.usuario == null || ur.usuario.id == null) return
			boolean existing = false
			UsuarioPermissao.withNewSession {
				existing = UsuarioPermissao.exists(ur.usuario.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		version false
		permissao lazy: false 
	}
}
