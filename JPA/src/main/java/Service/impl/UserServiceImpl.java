package Service.impl;

import Dao.UserDao;
import Dao.impl.UserDaoImpl;
import Service.UserService;
import entity.User;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void register(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }
}
