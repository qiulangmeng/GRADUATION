package qlm.web.graduationproject.repository.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.manager.Authority;

import java.util.List;

/**
 * @author qlm
 * @version 1.0 23:05 2020.3.25
 */
public interface AuthorityRepository extends JpaRepository<Authority,Long> {
    /**
     * 通过权限名获得权限对象
     * @param name 权限名
     * @return 权限对象
     */
    Authority findByName(String name);
}
