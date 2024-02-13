package store.stz7750.stz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import store.stz7750.stz.users.vo.UserVO;

public class SecurityUser extends User{

    private static final long serialVersionUID = 1L;

    private UserVO userVO;

    public SecurityUser(UserVO userVO){
        super(userVO.getId(), "{noop}"+userVO.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + userVO.getRole().toString().toUpperCase()));

        this.userVO = userVO;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UserVO getUser() {
        return userVO;
    }

    public void setUser(UserVO userVO) {
        this.userVO = userVO;
    }
}
