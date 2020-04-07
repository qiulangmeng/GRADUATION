package qlm.web.graduationproject.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;
import qlm.web.graduationproject.entity.address.Address;
import qlm.web.graduationproject.entity.manager.User;

import java.util.List;

/**
 * @author qlm
 * @version 1.0 0:13 2020.3.26
 */
public interface AddressRepository  extends JpaRepository<Address,Long> {
//    /**
//     * 通过用户对象找到所有属于该用户的地址
//     * @param user 用户
//     * @return 该用户所有地址对象
//     */
//    List<Address> findAllByUser(User user);
}
