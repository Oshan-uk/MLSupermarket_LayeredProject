package lk.ijse.mlsupermarket.bo.impl;


import lk.ijse.mlsupermarket.bo.custom.UserBO;
import lk.ijse.mlsupermarket.dao.DAOFactory;
import lk.ijse.mlsupermarket.dao.custom.UserDAO;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO =
            (UserDAO) DAOFactory.getInstance()
                    .getDAO(DAOFactory.DAOType.USER);

    @Override
    public int authenticate(String username, String password, String role) throws Exception {

        if (!userDAO.existsByUsername(username)) {
            return 1;
        }

        if (!userDAO.isPasswordCorrect(username, password)) {
            return 2;
        }

        if (!userDAO.isRoleCorrect(username, password, role)) {
            return 3;
        }

        return 4;
    }
}