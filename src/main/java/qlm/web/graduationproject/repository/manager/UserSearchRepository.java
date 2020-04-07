package qlm.web.graduationproject.repository.manager;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.manager.UserSearch;

/**
 * @author qlm
 * @version 1.0 16:55 2020.3.27
 */
public interface UserSearchRepository extends JpaRepository<UserSearch,Long> {
}
