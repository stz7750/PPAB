package store.stz7750.stz.users.vo;

import lombok.Data;

@Data
public class UserVO {
    private String id;
    private String password;
    private String name;
    private String ip;
    private String os;
    private String browser;
    private String email;
    private String Role;
}
