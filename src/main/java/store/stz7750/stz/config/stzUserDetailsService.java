package store.stz7750.stz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import store.stz7750.stz.users.service.UserService;
import store.stz7750.stz.users.vo.UserVO;

@Service
public class stzUserDetailsService implements UserDetailsService{
    

    @Autowired
    UserService service;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserVO user = service.findbyUserInfo(id);
        if(user == null){
            throw new UsernameNotFoundException("해당 사용자는 존재하지 않습니다. 사용자 이름: "+id);
        }else {
            return new SecurityUser(user);
        }
    }
}
