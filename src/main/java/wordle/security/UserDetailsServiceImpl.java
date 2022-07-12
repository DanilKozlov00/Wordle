package wordle.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wordle.model.dto.User;
import wordle.services.dao.UserDao;

import static wordle.controller.rest.LoginController.USER_DOESNT_EXISTS;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(USER_DOESNT_EXISTS);
        }
        return SecurityUser.fromUser(user);
    }
}
