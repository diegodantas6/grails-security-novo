package br.com.usuarioAcesso

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

import br.com.controleAcesso.Usuario
@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioFilial implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Filial filial

	UsuarioFilial(Usuario u, Filial f) {
		this()
		usuario = u
		filial = f
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof UsuarioFilial)) {
			return false
		}

		other.usuario?.id == usuario?.id && other.filial?.id == filial?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (filial) builder.append(filial.id)
		builder.toHashCode()
	}

	static UsuarioFilial get(long usuarioId, long filialId) {
		criteriaFor(usuarioId, filialId).get()
	}

	static boolean exists(long usuarioId, long filialId) {
		criteriaFor(usuarioId, filialId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long filialId) {
		UsuarioFilial.where {
			usuario == Usuario.load(usuarioId) &&
			filial == Filial.load(filialId)
		}
	}

	static UsuarioFilial create(Usuario usuario, Filial filial, boolean flush = false) {
		def instance = new UsuarioFilial(usuario, filial)
		instance.save(flush: flush, insert: true)
		instance
	}

	static void removeAll(Usuario u, boolean flush = false) {
		if (u == null) return

		UsuarioFilial.where { usuario == u }.deleteAll()

		if (flush) { UsuarioFilial.withSession { it.flush() } }
	}

	static void removeAll(Usuario u, Empresa e) {
		if (u == null) return
		
		if (e == null) return

		List listUsuarioFIlial = UsuarioFilial.executeQuery("select uf from UsuarioFilial uf where uf.usuario.id = :idUsuario and exists (select 1 from Filial f where f.empresa.id = :idEmpresa and f.id = uf.filial.id)", [idUsuario: u.id, idEmpresa: e.id])
		
		listUsuarioFIlial.each { it.delete(flush:true) }
	}

	static constraints = {
		filial validator: { Filial r, UsuarioFilial ur ->
			if (ur.usuario == null || ur.usuario.id == null) return
			boolean existing = false
			UsuarioFilial.withNewSession {
				existing = UsuarioFilial.exists(ur.usuario.id, r.id)
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
