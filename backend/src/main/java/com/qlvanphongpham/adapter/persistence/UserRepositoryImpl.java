package com.qlvanphongpham.adapter.persistence;

import com.qlvanphongpham.usecase.port.UserRepository;
import com.qlvanphongpham.domain.User;
import com.qlvanphongpham.domain.valueobjects.Email;
import com.qlvanphongpham.domain.valueobjects.Password;
import com.qlvanphongpham.domain.valueobjects.PhoneNumber;
import java.sql.*;
import java.util.*;

public class UserRepositoryImpl implements UserRepository {
    private DatabaseConnection databaseConnection;

    public UserRepositoryImpl() {
        this.databaseConnection = new DatabaseConnection();
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND deleted = false";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ? AND deleted = false";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE deleted = false ORDER BY created_at DESC";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            return insert(user);
        } else {
            return update(user);
        }
    }

    private User insert(User user) {
        String sql = "INSERT INTO users (username, email, password, full_name, phone_number, role, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, NOW())";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail().getValue());
            stmt.setString(3, user.getPassword().getValue());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getPhoneNumber() != null ? user.getPhoneNumber().getValue() : null);
            stmt.setString(6, user.getRole().name());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User update(User user) {
        String sql = "UPDATE users SET email = ?, full_name = ?, phone_number = ?, role = ?, updated_at = NOW() " +
                    "WHERE id = ? AND deleted = false";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getEmail().getValue());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getPhoneNumber() != null ? user.getPhoneNumber().getValue() : null);
            stmt.setString(4, user.getRole().name());
            stmt.setLong(5, user.getId());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "UPDATE users SET deleted = true, updated_at = NOW() WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND deleted = false";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ? AND deleted = false";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(new Email(rs.getString("email")));
        user.setPassword(new Password(rs.getString("password")));
        user.setFullName(rs.getString("full_name"));
        
        String phoneNumber = rs.getString("phone_number");
        if (phoneNumber != null) {
            user.setPhoneNumber(new PhoneNumber(phoneNumber));
        }
        
        user.setRole(User.Role.valueOf(rs.getString("role")));
        
        // SỬA 2 DÒNG NÀY:
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at") != null ? 
                         rs.getTimestamp("updated_at").toLocalDateTime() : null);
        
        return user;
    }
}