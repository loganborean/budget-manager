package com.budget.utils;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.budget.dao.UserDao;
import com.budget.dao.UserDaoImpl;
import com.budget.entity.User;
import com.budget.service.UserService;

@Service
public class CurrentUserUtils {
    
    @Autowired @Qualifier("MySql")
    private UserDao userDao;

    public static HttpSession session() {
        ServletRequestAttributes attr = 
                (ServletRequestAttributes) RequestContextHolder
                                            .currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }
    
    public User getCurrentUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
    public User getCurrentUser() {
        Authentication authentication = 
                SecurityContextHolder.getContext()
                                     .getAuthentication(); 

        User currentUser =
                getCurrentUserByUsername(authentication.getName());
        return currentUser;
    }

}
