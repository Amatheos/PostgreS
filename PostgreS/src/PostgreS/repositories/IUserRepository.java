package PostgreS.repositories;

import java.util.List;

import PostgreS.dtos.UserDTO;

public interface IUserRepository extends IRepository<UserDTO> {

	List<UserDTO> findByName(String username);
}