package com.restaurante.Ecomerce_backend.service;

import com.restaurante.Ecomerce_backend.model.Permiso;
import com.restaurante.Ecomerce_backend.repositorios.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService {
	
	@Autowired
	private PermisoRepository permisoRepositoriy;
	
	public List<Permiso> listarPermiso(){
		List<Permiso> permisos = permisoRepositoriy.findAll();
		return permisos;
	}
	
	public Optional<Permiso> obtenerPermiso(Long id){
		Optional<Permiso> permiso = permisoRepositoriy.findById(id);
		if (!permiso.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el objeto con el id" + id
					);
		}
		return permiso;
	}
	
	public Permiso guardarPermiso(Permiso permisoDto) {
		Permiso permiso = new Permiso();
		permiso.setNombre(permisoDto.getNombre());
		return permisoRepositoriy.save(permiso);
	}
	
	public Permiso actualizarPermiso(Long id, Permiso permisoDto) {
		Optional<Permiso> permisoOpt = obtenerPermiso(id);
		Permiso permiso = permisoOpt.get();
		permiso.setNombre(permisoDto.getNombre());
		return permisoRepositoriy.save(permiso);
	}

}
