package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.enums.RoleName;
import utc2.itk62.e_reader.exception.NotFoundException;
import utc2.itk62.e_reader.repository.RoleRepository;
import utc2.itk62.e_reader.service.RoleService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final Translator translator;
    private final RoleRepository roleRepository;

    @Override
    public Role createRole(String roleName) {
        RoleName parseRoleName;
        try {
            parseRoleName = RoleName.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new NotFoundException(translator.translate(MessageCode.INVALID_ROLE_NAME));
        }
        Role role = Role.builder()
                .roleName(parseRoleName)
                .build();
        return roleRepository.save(role);
    }

    @Override
    public boolean deleteRole(long id) {
        var role = roleRepository.findById(id).orElseThrow(() -> {
            log.error("RoleServiceImpl | id: {} not found", id);
            return new NotFoundException(translator.translate(MessageCode.ROLE_ID_NOT_FOUND));
        });
        roleRepository.delete(role);
        return !roleRepository.existsById(id);
    }

    @Override
    public Role updateRole(String roleName, long id) {
        var role = roleRepository.findById(id).orElseThrow(() -> {
            log.error("RoleServiceImpl | id: {} not found", id);
            return new NotFoundException(translator.translate(MessageCode.ROLE_ID_NOT_FOUND));
        });
        RoleName parseRoleName;
        try {
            parseRoleName = RoleName.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new NotFoundException(translator.translate(MessageCode.INVALID_ROLE_NAME));
        }
        role.setRoleName(parseRoleName);
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(long id) {
        return roleRepository.findById(id).orElseThrow(() -> {
            log.error("RoleServiceImpl | id: {} not found", id);
            return new NotFoundException(translator.translate(MessageCode.ROLE_ID_NOT_FOUND));
        });
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
