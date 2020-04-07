package qlm.web.graduationproject.service.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qlm.web.graduationproject.entity.manager.Role;
import qlm.web.graduationproject.repository.manager.RoleRepository;
import qlm.web.graduationproject.service.manager.RoleService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author qlm
 * @version 1.0 19:10 2020.3.21
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Role getAuthorityById(Long id) {
        Optional<Role> opt = roleRepository.findById(id);
        return opt.orElseGet(Role::new);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Role saveOrUpdate(@Valid Role role) {
        try {
            roleRepository.save(role);
        }catch (Exception e){
            throw new RuntimeException("Add Role Error: "+e.getMessage());
        }
        return role;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteById(Long id) {
        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete Role Error"+ e.getMessage());
        }
    }
}
