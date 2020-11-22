package work.cxlm.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import work.cxlm.model.params.NewMemberParam;
import work.cxlm.service.JoiningService;

import javax.validation.Valid;

/**
 * 成员管理、成员相关信息统计等
 * created 2020/11/21 17:14
 *
 * @author Chiru
 */
@RestController
@RequestMapping("/key3/admin/api/")
public class JoiningController {

    private final JoiningService joiningService;

    public JoiningController(JoiningService joiningService) {
        this.joiningService = joiningService;
    }

    @ApiOperation("加入社团")
    @PostMapping("joining")
    public void addMember(@RequestBody NewMemberParam param) {
        joiningService.addMember(param);
    }

    @ApiOperation("退出社团")
    @DeleteMapping("joining")
    public void removeMember(@RequestBody NewMemberParam param) {
        joiningService.removeMember(param);
    }
}
