package br.com.lifeundercontroll.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.lifeundercontroll.entity.UserApiEntity;
import br.com.lifeundercontroll.repository.UserApiRepository;

@Service
public class UserApiServiceImpl implements UserApiService, UserDetailsService {

	@Autowired
	UserApiRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserApiEntity user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserApiDetailsImpl(user);
	}

	@Override
	public UserApiEntity findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
