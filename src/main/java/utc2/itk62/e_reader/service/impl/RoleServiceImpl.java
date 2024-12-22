package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.model.RoleFilter;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.PermissionRepository;
import utc2.itk62.e_reader.repository.RoleRepository;
import utc2.itk62.e_reader.service.RoleService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    @Override
    public Role createRole(String roleName) {
        Role role = Role.builder()
                .name(roleName)
                .build();
        return roleRepository.save(role);
    }

    @Override
    public boolean deleteRole(long id) {
        var role = roleRepository.findById(id).orElseThrow(() -> {
            log.error("RoleServiceImpl | id: {}", id);
            return new EReaderException(MessageCode.ROLE_ID_NOT_FOUND);
        });
        roleRepository.delete(role);
        return !roleRepository.existsById(id);
    }

    @Override
    public Role updateRole(String roleName, long id) {
        var role = roleRepository.findById(id).orElseThrow(() -> {
            log.error("RoleServiceImpl | id: {}", id);
            return new EReaderException(MessageCode.ROLE_ID_NOT_FOUND);
        });

        role.setName(roleName);
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(long id) {
        return roleRepository.findById(id).orElseThrow(() -> {
            log.error("RoleServiceImpl | id: {}", id);
            return new EReaderException(MessageCode.ROLE_ID_NOT_FOUND);
        });
    }

    @Override
    public List<Role> getAllRole(RoleFilter filter, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Role> roles = roleRepository.findAll(pageable);
        pagination.setTotal(roles.getTotalElements());
        return roles.toList();
    }


}
