package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.model.RoleFilter;

import java.util.List;

public interface RoleService {

    Role createRole(String roleName);

    boolean deleteRole(long id);

    Role updateRole(String roleName, long id);

    Role getRole(long id);

    List<Role> getAllRole(RoleFilter filter, Pagination pagination);


}
