package lk.ijse.mlsupermarket.dao.impl;

import lk.ijse.mlsupermarket.dao.custom.UserDAO;
import lk.ijse.mlsupermarket.dao.CrudUtil;

import java.sql.ResultSet;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean existsByUsername(String username) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM user WHERE username=?",
                username
        );

        return rs.next();
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM user WHERE username=? AND password=MD5(?)",
                username,
                password
        );

        return rs.next();
    }

    @Override
    public boolean isRoleCorrect(String username, String password, String role) throws Exception {

        ResultSet rs = CrudUtil.execute(
                "SELECT * FROM user WHERE username=? AND password=MD5(?) AND role=?",
                username,
                password,
                role
        );

        return rs.next();
    }
}
