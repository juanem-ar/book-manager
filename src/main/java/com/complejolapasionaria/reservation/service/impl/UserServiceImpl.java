package com.complejolapasionaria.reservation.service.impl;

import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.IUserRepository;
import com.complejolapasionaria.reservation.security.service.impl.UserDetailsImpl;
import com.complejolapasionaria.reservation.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = iUserRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("username or password not found");
        }
        return UserDetailsImpl.build(userEntity);
    }
}
