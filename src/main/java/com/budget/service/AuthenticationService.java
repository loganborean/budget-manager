package com.budget.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.budget.dao.UserDao;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    @Qualifier("MySql")
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.budget.entity.User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(
                    "User " + username + " was not found");
        }
        
        List<GrantedAuthority> grantList = new ArrayList<>();
        grantList.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        UserDetails details = (UserDetails) new User(user.getUsername(),
                                                     user.getPassword(),
                                                     grantList);
        return details;
    }

}
