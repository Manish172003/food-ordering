package com.example.demo.services.auth.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class UserServiceImpl implements UserService{

	
	public UserServiceImpl(UserRepository userRepository) {
//		super();
		this.userRepository = userRepository;
	}

	private final UserRepository userRepository;
	
	@Override
	public UserDetailsService userDetailsService()
	{
		return new  UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return userRepository.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User ot Found"));
			}
		};
	}

}
