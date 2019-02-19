package PostgreS.repositories;

import java.util.List;

import PostgreS.dtos.GroupDTO;

public interface IGroupRepository extends IRepository<GroupDTO> {

	List<GroupDTO> findByName(String name);
}