🚀 RESTful User Management API
Dự án này là một RESTful API được xây dựng bằng Spring Boot, cung cấp các chức năng quản lý người dùng cơ bản bao gồm đăng ký, đăng nhập và quản lý thông tin người dùng với phân quyền (Authentication & Authorization).

✨ Chức năng chính
Đăng ký tài khoản mới: Cho phép người dùng tạo tài khoản với vai trò mặc định là USER.
Đăng nhập & Xác thực JWT: Người dùng đăng nhập để nhận JWT, sử dụng JWT để truy cập các tài nguyên được bảo vệ.
Quản lý người dùng (ROLE_ADMIN): Người dùng có vai trò ADMIN có toàn quyền xem danh sách tất cả người dùng chưa xóa, xem chi tiết và xóa mềm (soft delete) người dùng.
Bảo mật: Sử dụng Spring Security và JWT để xử lý xác thực và phân quyền.
Validation: Đảm bảo dữ liệu đầu vào hợp lệ và an toàn.

🛠️ Yêu cầu hệ thống
Java Development Kit (JDK): Phiên bản 17 trở lên.
Apache Maven: Dự án này sử dụng Maven Wrapper, vì vậy bạn không cần cài đặt Maven riêng biệt trên hệ thống. Phiên bản Maven sẽ tự động được tải xuống và sử dụng là 3.9.10.
Hệ quản trị Cơ sở dữ liệu: MySQL.

🚀 Hướng dẫn cài đặt và chạy ứng dụng
1. Clone Repository

Sử dụng Git để clone dự án về máy:

git clone https://github.com/vuonngg/dts_user_api.git

vào thư mục dự án

cd dts_user_api

2. Cấu hình Cơ sở dữ liệu: khi chạy thì hibernate sẽ tự tạo database và thêm bảng.
   
3. Cập nhật application.yml trong project:

Mở file src/main/resources/application.yml.

Cập nhật 2 thông tin kết nối database cho phù hợp với môi trường:
             
     spring:
       datasource:
         url: jdbc:mysql://localhost:3306/dts_test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'
         username: root(cần đổi lại phù hợp với database)
         password: vuong(cần đổi lại phù hợp với database)
         driver-class-name: com.mysql.cj.jdbc.Driver
       jpa:
         hibernate:
           ddl-auto: update
     jwt:
       signerKey: czti5ae8oJ9QCCZ1Lito40GO+HQk8i+4h4uPLNj1+KeC61NbqUikgYQgg8hTrbhQ

 
4. Khởi chạy Ứng dụng
Chạy ứng dụng từ IDE (IntelliJ IDEA, VS Code) hoặc bằng Maven Command Line:

mvn clean install
mvn spring-boot:run

Ứng dụng sẽ chạy trên cổng mặc định là 8080.

🔑 Tài khoản Mặc định & Vai trò
Để thuận tiện cho việc kiểm tra, ứng dụng sẽ tự động tạo một tài khoản ADMIN mặc định (nếu chưa tồn tại) khi khởi động lần đầu.

Tài khoản ADMIN mặc định:

Username: admin

Password: admin

Tài khoản USER:
Đăng ký tài khoản USER thông qua API http://localhost:8080/auth/signup. Tất cả các tài khoản đăng ký qua API này sẽ có vai trò USER.

📈 Hướng dẫn sử dụng API (API Endpoints)
Các API được cung cấp bởi dịch vụ này chạy trên cổng mặc định 8080. URL cơ sở (Base URL) là http://localhost:8080. Dưới đây là các endpoint chính:

1. Xác thực và Đăng ký (Authentication & Registration)
   
POST /auth/signup

Mô tả: Đăng ký tài khoản người dùng mới.

(Full URL ví dụ: http://localhost:8080/auth/signup)

Request Body (JSON):

    {
    "name": "vuong",
    "username": "vuong111",
    "password": "vuong",
    "email": "123@gmail.com",
    "phone": "0338957640",
    "avatar":"https://avatar" //không bắt buộc
   }

-------------------------------------------------------

POST /api/auth/login 

Mô tả: Đăng nhập để lấy JWT.

(Full URL ví dụ: http://localhost:8080/auth/login)

Request Body (JSON):

    {
    "username":"admin",
    "password":"admin"
    }

-------------------------------------------------------

DELETE /admin/delete/(id của user cần xóa)

Mô tả: Xóa người dùng.

Yêu cầu xác thực: Cần có JWT hợp lệ của tài khoản ADMIN

Authorization: Bearer <jwt>

(Full URL ví dụ: http://localhost:8080/admin/delete/6)

-------------------------------------------------------

GET /admin/users

Mô tả: Lấy tất cả người dùng chưa xóa.

Yêu cầu xác thực: Cần có JWT hợp lệ của tài khoản ADMIN

Authorization: Bearer <jwt>

(Full URL ví dụ: http://localhost:8080/admin/users)


