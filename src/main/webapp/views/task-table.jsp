<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Danh sách công việc</h4>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                <c:if test="${sessionScope.currentUser.roleId == 1 || sessionScope.currentUser.roleId == 2}">
                    <a href="${pageContext.request.contextPath}/task-add" class="btn btn-sm btn-success">Thêm mới</a>
                </c:if>
            </div>
        </div>
        <c:if test="${param.add =='1'}">
            <div class="alert alert-success">Them moi thanh cong!</div>
        </c:if>
        <c:if test="${param.update =='1'}">
            <div class="alert alert-success">Cap nhat cong viec thanh cong!</div>
        </c:if>
        <div class="row">
            <div class="col-sm-12">
                <div class="white-box">
                    <form action="${pageContext.request.contextPath}/task-table" method="GET" class="form-inline" style="margin-bottom: 16px;">
                        <input type="text" name="keywords" value="<c:out value='${param.keywords}' />" class="form-control" placeholder="Tìm tên công việc">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    </form>

                    <div class="table-responsive">
                        <table class="table" id="example">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên Công Việc</th>
                                    <th>Dự Án</th>
                                    <th>Người Thực Hiện</th>
                                    <th>Ngày Bắt Đầu</th>
                                    <th>Ngày Kết Thúc</th>
                                    <th>Trạng Thái</th>
                                    <th>Hành Động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty list}">
                                        <tr>
                                            <td colspan="8" class="text-center text-danger font-weight-bold" style="padding: 20px;">Không tìm thấy kết quả nào!</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${list}" var="task" varStatus="loop">
                                            <tr>
                                                <td>${loop.count}</td>
                                                <td>${task.taskName}</td>
                                                <td>${task.projectName}</td>
                                                <td>${task.userName}</td>
                                                <td>${task.startDate}</td>
                                                <td>${task.endDate}</td>
                                                <td>${task.statusName}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sessionScope.currentUser.roleId == 1 || sessionScope.currentUser.roleId == 2}">
                                                            <a href="${pageContext.request.contextPath}/task-edit?id=${task.id}" class="btn btn-sm btn-primary">Sửa</a>
                                                            <a href="${pageContext.request.contextPath}/task-delete?id=${task.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa công việc này không?')">Xóa</a>
                                                            <a href="${pageContext.request.contextPath}/task-details?id=${task.id}" class="btn btn-sm btn-info">Xem</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a href="${pageContext.request.contextPath}/task-details?id=${task.id}" class="btn btn-sm btn-success">Cập nhật tiến độ</a>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>

                        <div class="text-center">
                            <c:if test="${page > 1}">
                                <c:url var="previousUrl" value="/task-table">
                                    <c:param name="page" value="${page - 1}"></c:param>
                                    <c:param name="keywords" value="${param.keywords}"></c:param>
                                </c:url>
                                <a class="btn btn-default" href="${previousUrl}"> Trước </a>
                            </c:if>
                            
                            <c:forEach begin="1" end="${totalPage}" var="pageNumber">
                                <a href="${pageContext.request.contextPath}/task-table?page=${pageNumber}&keywords=${param.keywords}" class="btn ${pageNumber == page ? 'btn-primary' : 'btn-default'}">
                                    ${pageNumber}
                                </a>
                            </c:forEach>
                            
                            <c:if test="${page < totalPage}">
                                <c:url var="nextUrl" value="/task-table">
                                    <c:param name="page" value="${page + 1}"></c:param>
                                    <c:param name="keywords" value="${param.keywords}"></c:param>
                                </c:url>
                                <a class="btn btn-default" href="${nextUrl}"> Sau </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>