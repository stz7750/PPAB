package store.stz7750.stz.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.stz7750.stz.admin.mapper.MenuMapper;
import store.stz7750.stz.admin.vo.MenuVo;

import java.util.List;
import java.util.Map;

/**
 * packageName    : stz-store.stz7750.stz.admin.service
 * fileName       : MenuService
 * author         : stz
 * date           : 7/21/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/21/24        stz       최초 생성
 */

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;

    public List<MenuVo> getAllMenus(String useYn) {
        return menuMapper.getAllMenus(useYn);
    }
    public MenuVo getMenuById(Long menuId) {
        return menuMapper.getMenuById(menuId);
    }

    public String upsertMenuById(MenuVo menu) {
        return menuMapper.upsertMenuById(menu);
    }

}

