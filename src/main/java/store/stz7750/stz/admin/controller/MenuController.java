package store.stz7750.stz.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import store.stz7750.stz.admin.service.MenuService;
import store.stz7750.stz.admin.vo.MenuVo;

import java.util.List;

/**
 * packageName    : stz-store.stz7750.stz.admin.controller
 * fileName       : MenuController
 * author         : stz
 * date           : 7/21/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 7/21/24        stz       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api/")
public class MenuController {
    //AUTOWIRED는 Derpecated는 아니지만, 경고 수준의 메세지가 뜸 공부 필요함.
    private final MenuService menuService;


    @GetMapping("/getAllMenus")
    public List<MenuVo> getAllMenus() {
        return menuService.getAllMenus();
    }

    @PostMapping("/upsertMenuById")
    public String upsertMenuById(@RequestBody MenuVo vo) {
        return menuService.upsertMenuById(vo);
    }

}

