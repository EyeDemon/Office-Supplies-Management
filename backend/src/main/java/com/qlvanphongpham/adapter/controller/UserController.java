package com.qlvanphongpham.adapter.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import com.google.gson.*;
import com.qlvanphongpham.usecase.boundary.input.GetUserInputBoundary;
import com.qlvanphongpham.usecase.boundary.input.ManageUserInputBoundary;
import com.qlvanphongpham.usecase.interactor.GetUserInteractor; // THÊM IMPORT
import com.qlvanphongpham.usecase.interactor.ManageUserInteractor; // THÊM IMPORT
import com.qlvanphongpham.usecase.request.*;
import com.qlvanphongpham.usecase.response.*;
import com.qlvanphongpham.adapter.persistence.UserRepositoryImpl;
import com.qlvanphongpham.adapter.security.PasswordHasherImpl;

public class UserController extends HttpServlet {
    private ManageUserInputBoundary manageUserInput;
    private GetUserInputBoundary getUserInput;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        
        // SỬA: Khởi tạo implementation class thay vì interface
        this.manageUserInput = new ManageUserInteractor(userRepository, new PasswordHasherImpl()); // SỬA DÒNG NÀY
        this.getUserInput = new GetUserInteractor(userRepository); // SỬA DÒNG NÀY
        
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        enableCors(response);

        try {
            String pathInfo = request.getPathInfo();
            System.out.println("🔍 UserController GET path: " + pathInfo);

            if (pathInfo == null || pathInfo.equals("/")) {
                UserListResponse userList = getUserInput.getAllUsers();
                ApiResponse apiResponse = new ApiResponse(true, "Lấy danh sách user thành công", userList);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(apiResponse));
            } else {
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length == 2) {
                    long userId = Long.parseLong(pathParts[1]);
                    GetUserRequest getUserRequest = new GetUserRequest(userId);
                    UserResponse userResponse = getUserInput.getUserById(getUserRequest);
                    
                    if (userResponse != null) {
                        ApiResponse apiResponse = new ApiResponse(true, "Lấy thông tin user thành công", userResponse);
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write(gson.toJson(apiResponse));
                    } else {
                        sendErrorResponse(response, "User không tồn tại", HttpServletResponse.SC_NOT_FOUND);
                    }
                } else {
                    sendErrorResponse(response, "Endpoint không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "ID user không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Lỗi server: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        enableCors(response);

        try {
            String pathInfo = request.getPathInfo();
            System.out.println("🔍 UserController POST path: " + pathInfo);

            if (pathInfo == null || pathInfo.equals("/")) {
                BufferedReader reader = request.getReader();
                RegisterUserRequest registerRequest = gson.fromJson(reader, RegisterUserRequest.class);
                
                UserResponse userResponse = manageUserInput.registerUser(registerRequest);
                
                if (userResponse != null) {
                    ApiResponse apiResponse = new ApiResponse(true, "Tạo user thành công", userResponse);
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.getWriter().write(gson.toJson(apiResponse));
                } else {
                    sendErrorResponse(response, "Tạo user thất bại", HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                sendErrorResponse(response, "Endpoint không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Lỗi server: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        enableCors(response);

        try {
            String pathInfo = request.getPathInfo();
            System.out.println("🔍 UserController PUT path: " + pathInfo);

            if (pathInfo != null && pathInfo.split("/").length == 2) {
                String[] pathParts = pathInfo.split("/");
                long userId = Long.parseLong(pathParts[1]);
                
                BufferedReader reader = request.getReader();
                UpdateUserRequest updateRequest = gson.fromJson(reader, UpdateUserRequest.class);
                updateRequest.setId(userId);
                
                UserResponse userResponse = manageUserInput.updateUser(updateRequest);
                
                if (userResponse != null) {
                    ApiResponse apiResponse = new ApiResponse(true, "Cập nhật user thành công", userResponse);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(gson.toJson(apiResponse));
                } else {
                    sendErrorResponse(response, "Cập nhật user thất bại", HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                sendErrorResponse(response, "Endpoint không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "ID user không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Lỗi server: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        enableCors(response);

        try {
            String pathInfo = request.getPathInfo();
            System.out.println("🔍 UserController DELETE path: " + pathInfo);

            if (pathInfo != null && pathInfo.split("/").length == 2) {
                String[] pathParts = pathInfo.split("/");
                long userId = Long.parseLong(pathParts[1]);
                
                DeleteUserRequest deleteRequest = new DeleteUserRequest(userId);
                boolean success = manageUserInput.deleteUser(deleteRequest);
                
                if (success) {
                    ApiResponse apiResponse = new ApiResponse(true, "Xóa user thành công", null);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(gson.toJson(apiResponse));
                } else {
                    sendErrorResponse(response, "Xóa user thất bại", HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                sendErrorResponse(response, "Endpoint không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(response, "ID user không hợp lệ", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            sendErrorResponse(response, "Lỗi server: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
        ApiResponse errorResponse = new ApiResponse(false, message, null);
        response.getWriter().write(gson.toJson(errorResponse));
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        enableCors(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}