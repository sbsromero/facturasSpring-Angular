package com.example.demo.models.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.dao.IUsuarioDao;
import com.example.demo.models.entity.Role;
import com.example.demo.models.entity.Usuario;

@Service("jpaUserDetailsService")
public class JpaUserDetailService implements UserDetailsService {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(JpaUserDetailService.class);

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error login: El usuario no existe " + username);
			throw new UsernameNotFoundException("El usuario no existe "+username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : usuario.getRoles()) {
			logger.info("Roles: ".concat(role.getNombre()));
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		}
		
		if(authorities.isEmpty()) {
			logger.error("Error login: El usuario no tiene roles asignados");
			throw new UsernameNotFoundException("El usuario no tiene roles asignados");
		}

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

}
