package com.geekbrains.geekmarket.services;

import com.geekbrains.geekmarket.entities.Role;
import com.geekbrains.geekmarket.entities.SystemUser;
import com.geekbrains.geekmarket.entities.User;
import com.geekbrains.geekmarket.repositories.RoleRepository;
import com.geekbrains.geekmarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User findByUserName(String userName) {
        return userRepository.findOneByUserName(userName);
    }

    @Override
    @Transactional
    public void save(SystemUser systemUser) {
        User user = new User();
        user.setUserName(systemUser.getUserName());

        //TODO: BCrypt doesn't work. You must disable encrypt
 //       user.setPassword(passwordEncoder.encode(systemUser.getPassword()));

        //TODO: костыль из-за неработающего шифрования
        user.setPassword("{noop}"+systemUser.getPassword());

        user.setFirstname(systemUser.getFirstName());
        user.setLastname(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
        user.setPhone(systemUser.getPhone());

//        user.setRolls(Arrays.asList(roleRepository.findOneByName("ROLE_EMPLOYEE")));
        user.setRolls(Arrays.asList(roleRepository.findOneByName("ROLE_USER")));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findOneByUserName(userName);
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        } else {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRolls()));

        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
