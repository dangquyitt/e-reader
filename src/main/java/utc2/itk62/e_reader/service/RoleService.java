package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.Role;

import java.util.List;

public interface RoleService {

    Role createRole(String roleName);

    boolean deleteRole(long id);

    Role updateRole(String roleName, long id);

    Role getRole(long id);

    List<Role> getAllRole();
}
