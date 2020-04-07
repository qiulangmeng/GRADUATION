package qlm.web.graduationproject.service.manager;

import qlm.web.graduationproject.entity.manager.Role;

import java.util.List;

/**
 * @author qlm
 * @version 1.0
 */
public interface RoleService {
    /**
     * 根据id查询权限
     *
     * @param id 用户id
     * @return 用户
     */
    Role getAuthorityById(Long id);

    /**
     * 查询所有角色
     *
     * @return 所有角色
     */
    List<Role> findAll();

    /**
     * 查询某个权限
     *
     * @param role 存入的角色
     * @return 返回权限
     */
    Role saveOrUpdate(Role role);

    /**
     * 删除角色
     *
     * @param id 角色id
     */
    void deleteById(Long id);


}
