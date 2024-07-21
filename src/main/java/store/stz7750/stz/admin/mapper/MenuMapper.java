package store.stz7750.stz.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import store.stz7750.stz.admin.vo.MenuVo;

/**
 * packageName    : stz-store.stz7750.stz.admin.mapper
 * fileName       : MenuMapper
 * author         : stz
 * date           : 7/21/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/21/24        stz       최초 생성
 */
import java.util.List;
@Mapper
public interface MenuMapper {

    List<MenuVo> getAllMenus();

    MenuVo getMenuById(@Param("menuId") Long menuId);

    //upsert 시 menu_name을 리턴받아서 String 타입입니다.
    String upsertMenuById(MenuVo menu);

}
