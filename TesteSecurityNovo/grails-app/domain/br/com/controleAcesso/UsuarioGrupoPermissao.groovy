package br.com.controleAcesso

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioGrupoPermissao implements Serializable {

	private static final long serialVersionUID = 1

	UsuarioGrupo usuarioGrupo
	Permissao permissao

	UsuarioGrupoPermissao(UsuarioGrupo u, Permissao r) {
		this()
		usuarioGrupo = u
		permissao = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UsuarioGrupoPermissao)) {
			return false
		}

		other.usuarioGrupo?.id == usuarioGrupo?.id && other.permissao?.id == permissao?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuarioGrupo) builder.append(usuarioGrupo.id)
		if (permissao) builder.append(permissao.id)
		builder.toHashCode()
	}

	static UsuarioGrupoPermissao get(long usuarioGrupoId, long permissaoId) {
		criteriaFor(usuarioGrupoId, permissaoId).get()
	}

	static boolean exists(long usuarioGrupoId, long permissaoId) {
		criteriaFor(usuarioGrupoId, permissaoId).count()
	}
	
	private static DetachedCriteria criteriaFor(long usuarioGrupoId, long permissaoId) {
		UsuarioGrupoPermissao.where {
			usuarioGrupo == UsuarioGrupo.load(usuarioGrupoId) &&
			permissao == Permissao.load(permissaoId)
		}
	}

	static UsuarioGrupoPermissao create(UsuarioGrupo usuarioGrupo, Permissao permissao, boolean flush = false) {
		def instance = new UsuarioGrupoPermissao(usuarioGrupo, permissao)
		instance.save(flush: flush, insert: true)
		instance
	}

	static void removeAll(UsuarioGrupo u, boolean flush = false) {
		if (u == null) return

		UsuarioGrupoPermissao.where { usuarioGrupo == u }.deleteAll()

		if (flush) { UsuarioGrupoPermissao.withSession { it.flush() } }
	}

	static constraints = {
		permissao validator: { Permissao r, UsuarioGrupoPermissao ur ->
			if (ur.usuarioGrupo == null || ur.usuarioGrupo.id == null) return
			boolean existing = false
			UsuarioGrupoPermissao.withNewSession {
				existing = UsuarioGrupoPermissao.exists(ur.usuarioGrupo.id, r.id)
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
