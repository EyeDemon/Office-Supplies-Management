<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Quản lý văn phòng phẩm</title>
    <meta charset="UTF-8">
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background: #f5f5f5; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); text-align: center; }
        h1 { color: #333; margin-bottom: 20px; }
        .btn { display: inline-block; padding: 12px 24px; margin: 10px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; }
        .btn:hover { background: #0056b3; }
        .demo-info { background: #f8f9fa; padding: 15px; border-radius: 4px; margin-top: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🏢 Quản lý văn phòng phẩm</h1>
        <p>Chào mừng đến với hệ thống quản lý văn phòng phẩm</p>
        
        <div style="margin-top: 30px;">
            <a href="login" class="btn">Đăng nhập</a>
            <a href="register" class="btn">Đăng ký</a>
        </div>
        
        <div class="demo-info">
            <h3>Tài khoản demo:</h3>
            <p><strong>Admin:</strong> admin / 123456</p>
            <p><strong>User:</strong> user1 / 123456</p>
        </div>
    </div>
</body>
</html>