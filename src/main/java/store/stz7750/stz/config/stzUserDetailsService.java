package store.stz7750.stz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import store.stz7750.stz.users.service.UserService;
import store.stz7750.stz.users.vo.UserVO;

@Service
public class stzUserDetailsService implements UserDetailsService{
    

    @Autowired
    UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty()) {
            throw new UsernameNotFoundException("사용자 이름이 비어있습니다.");
        }

        UserVO user = service.findbyUserInfo(username);
        if (user == null) {
            throw new UsernameNotFoundException("해당 사용자는 존재하지 않습니다. 사용자 이름: " + username);
        } else {
            return new SecurityUser(user);
        }
    }
}
