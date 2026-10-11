<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Danh sách công việc</h4>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                <a href="${pageContext.request.contextPath}/task-add" class="btn btn-sm btn-success">Thêm mới</a>
            </div>
        </div>
        
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
                                <c:forEach items="${list}" var="task">
                                    <tr>
                                        <td>${task.id}</td>
                                        <td>${task.taskName}</td>
                                        <td>${task.projectName}</td>
                                        <td>${task.userName}</td>
                                        <td>${task.startDate}</td>
                                        <td>${task.endDate}</td>
                                        <td>${task.statusName}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/task-edit?id=${task.id}" class="btn btn-sm btn-primary">Sửa</a>
                                            <a href="${pageContext.request.contextPath}/task-delete?id=${task.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa công việc này không?')">Xóa</a>
                                            <a href="${pageContext.request.contextPath}/task-details?id=${task.id}" class="btn btn-sm btn-info">Xem</a>
                                        </td>
                                    </tr>
                                </c:forEach>
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
                                <a href="${pageContext.request.contextPath}/task-table?page=${pageNumber}&keywords=${param.keywords}" 
                                   class="btn ${pageNumber == page ? 'btn-primary' : 'btn-default'}">
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