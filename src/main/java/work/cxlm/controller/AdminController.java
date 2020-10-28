package work.cxlm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.cxlm.service.AdminService;

/**
 * created 2020/10/21 14:31
 *
 * @author cxlm
 */
@Slf4j
@RestController
@RequestMapping("/font/api/admin")
public class AdminController {

    private final AdminService adminService;



}
