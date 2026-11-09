package services;

import java.sql.SQLException;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import dto.CreateUserDTO;
import dto.UserListDTO;
import models.User;
import repositories.UserRepository;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public List<UserListDTO> getAllUser() {
        List<UserListDTO> users = userRepository.getAllUser();
        return users;
    }

    public String create(CreateUserDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()
                || dto.getPassword() == null || dto.getPassword().trim().isEmpty()
                || dto.getFullname() == null || dto.getFullname().trim().isEmpty()
                || dto.getPhone() == null || dto.getPhone().trim().isEmpty()) {
            return "Email, mat khau, sdt va ho ten khong duoc de trong";
        }
        User findByEmail = userRepository.findByEmail(dto.getEmail());
        if (findByEmail != null) {
            return "Email da ton tai";
        }
        String hashPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12));
        User user = new User(dto.getEmail().trim(),
                hashPassword, dto.getFullname(), dto.getPhone(), dto.getCountry(), dto.getRoleId());
        return userRepository.create(user) ? null : "Khong the tao user";
    }

    public List<UserListDTO> getUserPaging(String keywords, int size, int index) {
        List<UserListDTO> users = userRepository.userPaging(keywords, size, index);
        return users;
    }

    public int countUsers(String keyword) throws SQLException {
        return userRepository.countUser(keyword);
    }

    public String deleteById(int id) throws SQLException {
        try {
            boolean delete = userRepository.deleteById(id);
            return delete ? null : "Khong tim thay user";
        } catch (SQLException e) {
            return "User dang co task khong duoc xoa";
        }
    }

    public User findById(int id) throws SQLException {
        return userRepository.getUserById(id);
    }

    public String update(User user) throws SQLException {
        if (user.getFullname() == null || user.getFullname().trim().isEmpty() ||
            user.getPhone() == null || user.getPhone().trim().isEmpty() ||
            user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Email, ho ten hoac so dien thoai khong duoc de trong";
        }
        
        User checkEmail = userRepository.findByEmail(user.getEmail());
        if (checkEmail != null && checkEmail.getId() != user.getId()) { 
            return "Email da duoc su dung";
        }
        
        try {
            boolean check = userRepository.updateUser(user);
            return check ? null : "Loi update";
        } catch (Exception e) {
            return "Loi update";
        }
    }
}