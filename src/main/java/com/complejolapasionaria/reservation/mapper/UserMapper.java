package com.complejolapasionaria.reservation.mapper;

import com.complejolapasionaria.reservation.Enum.ERoles;
import com.complejolapasionaria.reservation.dto.RequestUserDto;
import com.complejolapasionaria.reservation.dto.AuthRegisterResponseDto;
import com.complejolapasionaria.reservation.dto.UserResponseDto;
import com.complejolapasionaria.reservation.model.Role;
import com.complejolapasionaria.reservation.model.User;
import com.complejolapasionaria.reservation.repository.IRoleRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final IRoleRepository iRoleRepository;

    public UserMapper(IRoleRepository iRoleRepository) {
        this.iRoleRepository = iRoleRepository;
    }

    public User toEntity(RequestUserDto dto) {
        User userEntity = new User();
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setEmail(dto.getEmail());
        userEntity.setDateOfBirth(dto.getDateOfBirth());
        userEntity.setAddress(dto.getAddress());
        userEntity.setPhoneNumber(dto.getPhoneNumber());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userEntity.setRole(this.convertRole(dto.getRole()));
        userEntity.setDeleted(false);
        userEntity.setAccountNonExpired(Boolean.TRUE);
        userEntity.setAccountNonLocked(Boolean.TRUE);
        userEntity.setCredentialsNonExpired(Boolean.TRUE);
        return userEntity;
    }

    public UserResponseDto toUserResponseDto(User entity) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setDeleted(entity.getDeleted());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setAddress(entity.getAddress());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setCreationDate(entity.getCreationDate());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setReservationList(entity.getReservationList());
        dto.setAuthorities(entity.getAuthorities());
        dto.setAccountNonExpired(entity.isAccountNonExpired());
        dto.setAccountNonLocked(entity.isAccountNonLocked());
        dto.setCredentialsNonExpired(entity.isCredentialsNonExpired());
        return dto;
    }

    public Role convertRole (String name){
        return name.equalsIgnoreCase("ADMIN") ?
                iRoleRepository.findByName(ERoles.ROLE_ADMIN) : iRoleRepository.findByName(ERoles.ROLE_USER);
    }
}
