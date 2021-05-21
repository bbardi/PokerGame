package edu.bbardi.pokerbackend.user.service;

import edu.bbardi.pokerbackend.user.model.User;
import edu.bbardi.pokerbackend.user.model.dto.UserDetailsImpl;
import edu.bbardi.pokerbackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found with username: "+username));

        return UserDetailsImpl.build(user);
    }
}
