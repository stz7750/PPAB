package store.stz7750.stz.admin.vo;

import lombok.Data;

/**
 * packageName    : stz-store.stz7750.stz.admin.vo
 * fileName       : MenuVo
 * author         : stz
 * date           : 7/21/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/21/24        stz       최초 생성
 */
@Data
public class MenuVo {
    private Long menuId;
    private String menuName;
    private Long parentMenuId;
    private int menuSeq;
    private String menuPath;
    private String pathName;
    private String path;
}

