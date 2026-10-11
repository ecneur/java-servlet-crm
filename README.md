# 🏢 Java Web CRM Project (MVC Architecture)

Dự án website quản lý công việc và nhân sự (CRM) áp dụng mô hình **MVC thuần túy**, được xây dựng bằng Java Servlet, JSP, JDBC và Sitemesh. 

## ✨ Tính năng nổi bật

- **Bảng điều khiển (Dashboard):** Thống kê trực quan số lượng và tiến độ công việc theo 3 trạng thái (Chưa bắt đầu, Đang thực hiện, Đã hoàn thành).
- **Quản lý Dự án & Công việc:** Hỗ trợ CRUD (Thêm, sửa, xóa, tìm kiếm) danh sách dự án và phân công task (giao việc) cho từng nhân sự.
- **Quản lý Thành viên & Quyền (User & Role):** Quản lý hồ sơ nhân viên, phân quyền cơ bản.
- **Xác thực người dùng:** Đăng nhập, đăng xuất (Authentication) an toàn thông qua HTTP Session.
- **Giao diện đồng nhất:** Tích hợp Sitemesh 3 để tái sử dụng layout (Header, Sidebar, Footer) cho toàn bộ hệ thống.
- **Xử lý ngoại lệ:** Tự động điều hướng các đường dẫn lỗi về trang 404 (Not Found) thân thiện.

## 🛠️ Công nghệ & Môi trường sử dụng

- **Ngôn ngữ:** Java 21
- **Kiến trúc phần mềm:** MVC (Model - View - Controller)
- **Backend:** Java Servlet, JSP, JDBC, JSTL
- **Frontend:** HTML5, CSS3, JavaScript, Bootstrap (PixelAdmin Template)
- **Database:** MySQL
- **Máy chủ ứng dụng (Web Server):** Apache Tomcat 9
- **Công cụ phát triển:** Eclipse IDE

## 📂 Cấu trúc thư mục chính (MVC)

Hệ thống được tổ chức phân lớp rõ ràng để đảm bảo tính dễ bảo trì và mở rộng:
- `controllers/`: Chứa các Servlet đóng vai trò là Controller, tiếp nhận request và điều hướng trang.
- `models/` & `dto/`: Định nghĩa các đối tượng thực thể (User, Role, Task, Project).
- `dao/` (Data Access Object): Chứa các class kết nối và thao tác trực tiếp với cơ sở dữ liệu MySQL (thực thi câu lệnh SQL).
- `views/`: Chứa các file giao diện `.jsp` (nhận dữ liệu từ Controller để hiển thị cho người dùng).
- `decorators/` & `layout/`: Cấu hình Sitemesh để ghép khung giao diện chung.