package com.example.demo.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
//	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername(String username);
}
