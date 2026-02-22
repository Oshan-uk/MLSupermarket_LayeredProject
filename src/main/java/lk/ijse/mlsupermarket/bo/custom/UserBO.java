package lk.ijse.mlsupermarket.bo.custom;

import lk.ijse.mlsupermarket.bo.SuperBO;

public interface UserBO extends SuperBO {

    int authenticate(String username, String password, String role) throws Exception;
}
