package br.com.usuarioAcesso

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

import br.com.controleAcesso.Usuario
@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioEmpresa implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Empresa empresa

	UsuarioEmpresa(Usuario u, Empresa e) {
		this()
		usuario = u
		empresa = e
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UsuarioEmpresa)) {
			return false
		}

		other.usuario?.id == usuario?.id && other.empresa?.id == empresa?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (empresa) builder.append(empresa.id)
		builder.toHashCode()
	}

	static UsuarioEmpresa get(long usuarioId, long empresaId) {
		criteriaFor(usuarioId, empresaId).get()
	}

	static boolean exists(long usuarioId, long empresaId) {
		criteriaFor(usuarioId, empresaId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long empresaId) {
		UsuarioEmpresa.where {
			usuario == Usuario.load(usuarioId) &&
			empresa == Empresa.load(empresaId)
		}
	}

	static UsuarioEmpresa create(Usuario usuario, Empresa empresa, boolean flush = false) {
		def instance = new UsuarioEmpresa(usuario, empresa)
		instance.save(flush: flush, insert: true)
		instance
	}

	static void removeAll(Usuario u, boolean flush = false) {
		if (u == null) return

		UsuarioEmpresa.where { usuario == u }.deleteAll()

		if (flush) { UsuarioEmpresa.withSession { it.flush() } }
	}

	static constraints = {
		empresa validator: { Empresa r, UsuarioEmpresa ur ->
			if (ur.usuario == null || ur.usuario.id == null) return
			boolean existing = false
			UsuarioEmpresa.withNewSession {
				existing = UsuarioEmpresa.exists(ur.usuario.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		version false
	}
}
