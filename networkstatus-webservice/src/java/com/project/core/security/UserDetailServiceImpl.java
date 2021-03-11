package com.project.core.security;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project.core.models.entities.user.Role;
import com.project.core.models.entities.user.User;
import com.project.core.services.user.RoleService;
import com.project.core.services.user.UserService;

/**
 *
 * @author Samrit
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Account account = service.findByAccountName(name);
//        if (account == null) {
//            throw new UsernameNotFoundException("no user found with " + name);
//        }
//        return new AccountUserDetails(account);

        //test
//        User user = userService.findUserByUsername(username);
        try {
            User user = userService.findValidUserByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("no user found with " + username);
            } else {
                System.out.println("User Name : " + user.getUsername() + " Password :" + user.getPassword());
                Set<Role> roles = user.getRoles();
                System.out.println("Size : " + roles.size());
                for (Role role : roles) {
                    System.out.println("Role : " + role.getName());
                }
            }

            return new UsersUserDetails(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Internal server error with " + username);
        }

        //end of test
    }
}
