package com.qlvanphongpham.adapter.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import com.google.gson.*;
import com.qlvanphongpham.usecase.boundary.input.ManageUserInputBoundary;
import com.qlvanphongpham.usecase.interactor.ManageUserInteractor; // THÊM IMPORT
import com.qlvanphongpham.usecase.request.LoginRequest;
import com.qlvanphongpham.usecase.response.LoginResponse;
import com.qlvanphongpham.adapter.persistence.UserRepositoryImpl;
import com.qlvanphongpham.adapter.security.PasswordHasherImpl;

public class AuthController extends HttpServlet {
    private ManageUserInputBoundary userInputBoundary;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        // SỬA: Khởi tạo implementation class thay vì interface
        this.userInputBoundary = new ManageUserInteractor( // SỬA DÒNG NÀY
            new UserRepositoryImpl(),
            new PasswordHasherImpl()
        );
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        enableCors(response);
        
        String pathInfo = request.getPathInfo();
        System.out.println("🔍 AuthController path: " + pathInfo);

        if (pathInfo == null || pathInfo.equals("/")) {
            sendErrorResponse(response, "Endpoint không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            switch (pathInfo) {
                case "/login":
                    handleLogin(request, response);
                    break;
                case "/logout":
                    handleLogout(request, response);
                    break;
                default:
                    sendErrorResponse(response, "Endpoint không tồn tại", HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Lỗi server: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        BufferedReader reader = request.getReader();
        LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);
        
        System.out.println("🔐 Login attempt: " + loginRequest.getUsername());

        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            sendErrorResponse(response, "Username và password là bắt buộc", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        LoginResponse loginResponse = userInputBoundary.login(loginRequest);
        
        System.out.println("📊 Login response - Success: " + loginResponse.isSuccess());
        System.out.println("📝 Message: " + loginResponse.getMessage());

        if (loginResponse.isSuccess()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", loginResponse.getUser().getId());
            session.setAttribute("username", loginResponse.getUser().getUsername());
            session.setAttribute("role", loginResponse.getUser().getRole());
            session.setMaxInactiveInterval(30 * 60);

            AuthResponse authResponse = new AuthResponse(
                true,
                loginResponse.getMessage(),
                loginResponse.getUser(),
                "session-" + session.getId()
            );

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(authResponse));
            
            System.out.println("✅ Login successful for user: " + loginRequest.getUsername());
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            AuthResponse authResponse = new AuthResponse(false, loginResponse.getMessage(), null, null);
            response.getWriter().write(gson.toJson(authResponse));
        }
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        AuthResponse authResponse = new AuthResponse(true, "Đăng xuất thành công", null, null);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(gson.toJson(authResponse));
    }

    private void enableCors(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) 
            throws IOException {
        response.setStatus(status);
        AuthResponse errorResponse = new AuthResponse(false, message, null, null);
        response.getWriter().write(gson.toJson(errorResponse));
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        enableCors(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static class AuthResponse {
        private boolean success;
        private String message;
        private Object user;
        private String token;

        public AuthResponse(boolean success, String message, Object user, String token) {
            this.success = success;
            this.message = message;
            this.user = user;
            this.token = token;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Object getUser() { return user; }
        public String getToken() { return token; }
    }
}