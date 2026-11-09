# 🏢 Java Web CRM Project (MVC Architecture)

[🇻🇳 Xem bản tiếng Việt ở bên dưới](#-bản-tiếng-việt)

A web-based Customer Relationship Management (CRM) and Task Management project implementing the **pure MVC architecture**, built with Java Servlet, JSP, JDBC, and Sitemesh. The system strongly focuses on Role-Based Access Control (RBAC), Data Isolation, and User Experience (UX/UI) optimization according to real-world business standards.

## ✨ Key Features

- **Authorization & Security:**
  - Core 3-Role system: **Admin** (Full access), **Leader** (Manages own projects), **Employee** (Views and updates assigned task progress).
  - Utilizes **Servlet Filter** to block unauthorized URL access, automatically routing error flows to 403 (Forbidden) or 404 (Not Found) pages.
  - Data integrity protection: Completely prevents the logic flaw of self-deletion and blocks the deletion of Roles currently assigned to active users.
- **Data Isolation:** Displayed data is dynamically queried based on the login Session. Leaders can only view and manipulate projects/personnel within their managed scope.
- **Project & Task Management:**
  - Full CRUD support for projects and tasks.
  - **Business Validation:** Strict validation for date logic (End date must be after Start date; Task duration must fall strictly within the parent Project's timeframe).
- **Optimized UX/UI:**
  - Automatically retains all form data on invalid submissions (prevents blank resets).
  - Displays success/failure alerts and seamlessly redirects via URL parameters (`?add=1`, `?update=1`).
  - Sidebar menu renders dynamically based on Role (hiding/showing relevant feature tabs).
  - Data lists always prioritize the newest entries at the top (`ORDER BY id DESC`), integrating an independent Sequence Number counter decoupled from system IDs.
- **Dashboard:** Visual statistics of task quantities and progress categorized by 3 statuses (Not Started, In Progress, Completed).
- **Consistent UI:** Integrates Sitemesh 3 to reuse the layout (Header, Sidebar, Footer) across the entire system.

## 🛠️ Tech Stack & Environment

- **Language:** Java 21
- **Architecture:** MVC (Model - View - Controller)
- **Backend:** Java Servlet, JSP, JDBC, JSTL, Servlet Filter
- **Frontend:** HTML5, CSS3, JavaScript, Bootstrap (PixelAdmin Template)
- **Database:** MySQL
- **Web Server:** Apache Tomcat 9
- **Development Tool:** Eclipse IDE

## 📂 Folder Structure (MVC)

The system is organized into distinct layers to ensure maintainability and scalability:
- `controllers/`: Contains Servlets handling HTTP requests, flow processing, and page routing.
- `models/` & `dto/`: Defines entity objects (User, Role, Task, Project) and Data Transfer Objects.
- `dao/` & `repositories/`: Contains classes connecting and directly manipulating the MySQL database (executing SQL queries, handling Data Isolation).
- `filters/`: Contains security filters (Authentication, Authorization) validating sessions and permissions prior to access.
- `views/`: Contains `.jsp` interface files, utilizing JSTL to render dynamic data based on Controller logic.
- `decorators/` & `layout/`: Sitemesh configurations for assembling the common UI framework.

---

# 🇻🇳 Bản tiếng Việt

# 🏢 Java Web CRM Project (MVC Architecture)

Dự án website quản lý công việc và nhân sự (CRM) áp dụng mô hình **MVC thuần túy**, được xây dựng bằng Java Servlet, JSP, JDBC và Sitemesh. Hệ thống tập trung mạnh vào phân quyền bảo mật (RBAC), cách ly dữ liệu (Data Isolation) và tối ưu trải nghiệm người dùng (UX/UI) theo chuẩn nghiệp vụ thực tế.

## ✨ Tính năng nổi bật

- **Phân quyền & Bảo mật (Authorization & Security):**
  - Hệ thống 3 Role cốt lõi: **Admin** (Toàn quyền), **Leader** (Quản lý dự án của mình), **Employee** (Chỉ xem và cập nhật tiến độ công việc được giao).
  - Sử dụng **Servlet Filter** để chặn các truy cập trái phép qua URL, tự động điều hướng luồng lỗi về trang 403 (Forbidden) hoặc 404 (Not Found).
  - Bảo vệ toàn vẹn dữ liệu: Ngăn chặn triệt để lỗi logic tự xóa tài khoản của chính mình và chặn xóa Quyền (Role) đang được gán cho nhân viên.
- **Cách ly dữ liệu (Data Isolation):** Dữ liệu hiển thị được query động theo phiên đăng nhập (Session). Leader chỉ được quyền nhìn thấy và thao tác trên dự án/nhân sự thuộc phạm vi mình quản lý.
- **Nghiệp vụ Quản lý Dự án & Công việc:**
  - Hỗ trợ trọn vẹn CRUD với danh sách dự án và task.
  - **Business Validation:** Kiểm tra chặt chẽ logic ngày tháng (Ngày kết thúc phải sau ngày bắt đầu, Thời gian Task phải nằm trọn trong thời gian của Dự án).
- **Trải nghiệm người dùng (UX/UI) tối ưu:**
  - Tự động giữ lại toàn bộ dữ liệu form khi người dùng nhập sai (không bị reset trắng).
  - Hiển thị thông báo (Alert) trạng thái thành công/thất bại và tự động chuyển hướng mượt mà thông qua tham số URL (`?add=1`, `?update=1`).
  - Thanh menu Sidebar tự động render theo Role (ẩn/hiện các tab chức năng tương ứng).
  - Danh sách dữ liệu luôn ưu tiên hiển thị mục mới nhất lên đầu bảng (`ORDER BY id DESC`), tích hợp bộ đếm STT độc lập với ID hệ thống.
- **Bảng điều khiển (Dashboard):** Thống kê trực quan số lượng và tiến độ công việc theo 3 trạng thái (Chưa bắt đầu, Đang thực hiện, Đã hoàn thành).
- **Giao diện đồng nhất:** Tích hợp Sitemesh 3 để tái sử dụng layout (Header, Sidebar, Footer) cho toàn bộ hệ thống.

## 🛠️ Công nghệ & Môi trường sử dụng

- **Ngôn ngữ:** Java 21
- **Kiến trúc phần mềm:** MVC (Model - View - Controller)
- **Backend:** Java Servlet, JSP, JDBC, JSTL, Servlet Filter
- **Frontend:** HTML5, CSS3, JavaScript, Bootstrap (PixelAdmin Template)
- **Database:** MySQL
- **Máy chủ ứng dụng (Web Server):** Apache Tomcat 9
- **Công cụ phát triển:** Eclipse IDE

## 📂 Cấu trúc thư mục chính (MVC)

Hệ thống được tổ chức phân lớp rõ ràng để đảm bảo tính dễ bảo trì và mở rộng:
- `controllers/`: Chứa các Servlet tiếp nhận HTTP request, xử lý luồng đi và điều hướng trang.
- `models/` & `dto/`: Định nghĩa các đối tượng thực thể (User, Role, Task, Project) và Data Transfer Object.
- `dao/` & `repositories/`: Chứa các class kết nối và thao tác trực tiếp với cơ sở dữ liệu MySQL (thực thi câu lệnh SQL, xử lý Data Isolation).
- `filters/`: Chứa các bộ lọc bảo mật (Authentication, Authorization) kiểm tra phiên đăng nhập và phân quyền trước khi cho phép truy cập.
- `views/`: Chứa các file giao diện `.jsp`, sử dụng JSTL để render dữ liệu động theo logic của Controller.
- `decorators/` & `layout/`: Cấu hình Sitemesh để ghép khung giao diện chung.
