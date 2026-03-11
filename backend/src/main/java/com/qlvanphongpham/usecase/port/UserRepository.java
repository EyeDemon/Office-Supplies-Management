package com.qlvanphongpham.usecase.port;

import com.qlvanphongpham.domain.User;
import java.util.List;

public interface UserRepository {
    User findByUsername(String username);
    User findById(Long id);
    List<User> findAll();
    User save(User user);
    boolean delete(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}