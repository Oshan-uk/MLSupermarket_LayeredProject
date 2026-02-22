package lk.ijse.mlsupermarket.dao.custom;

import lk.ijse.mlsupermarket.dao.SuperDAO;
import lk.ijse.mlsupermarket.entity.User;

public interface UserDAO extends SuperDAO {

    boolean existsByUsername(String username) throws Exception;

    boolean isPasswordCorrect(String username, String password) throws Exception;

    boolean isRoleCorrect(String username, String password, String role) throws Exception;
}
