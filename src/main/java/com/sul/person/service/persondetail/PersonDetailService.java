package com.sul.person.service.persondetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sul.person.service.dto.PersonDetailDTO;
import com.sul.person.service.repository.PersonDetailJpaRepository;

/**
 * @author Sulaiman Abboud
 */
@Service
public class PersonDetailService implements UserDetailsService {

	@Autowired
	private PersonDetailJpaRepository personDetailJpaRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PersonDetailDTO user = personDetailJpaRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Person not found with user-name: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthorities(user));
	}

	private Collection<GrantedAuthority> getAuthorities(PersonDetailDTO personDetail) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities = AuthorityUtils.createAuthorityList(personDetail.getRole());
		return authorities;
	}

}
