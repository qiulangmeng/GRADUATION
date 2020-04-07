package qlm.web.graduationproject.repository.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.manager.Role;

/**
 * @author qlm
 * @version 1.0 21:50 2020.3.21
 */
public interface RoleRepository extends JpaRepository<Role,Long> {
    /**
     * 通过角色名获取角色
     * @param name 角色名
     * @return 角色对象
     */
    Role findByName(String name);
}
