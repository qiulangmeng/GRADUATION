package qlm.web.graduationproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qlm.web.graduationproject.service.manager.RoleService;
import qlm.web.graduationproject.service.manager.UserService;

/**
 * @author qlm
 * @version 1.0 18:53 2020.3.21
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
}
