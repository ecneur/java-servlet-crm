<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Danh sách thành viên</h4>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                <c:if test="${sessionScope.currentUser.roleId == 1}">
                    <a href="${pageContext.request.contextPath}/user-add" class="btn btn-sm btn-success">Thêm mới</a>
                </c:if>
            </div>
        </div>
        
        <c:if test="${param.add =='1'}">
            <div class="alert alert-success">Them moi thanh cong!</div>
        </c:if>
        <c:if test="${param.update =='1'}">
            <div class="alert alert-success">Cap nhat thanh vien thanh cong!</div>
        </c:if>
        <c:if test="${param.delete =='1'}">
            <div class="alert alert-success">Xoa thanh vien thanh cong!</div>
        </c:if> 
        <c:if test="${param.err =='1'}">
            <div class="alert alert-danger">Xoa thanh vien that bai!</div>
        </c:if>
        <c:if test="${param.err =='2'}">
            <div class="alert alert-warning">Ban khong the tu xoa chinh minh!</div>
        </c:if>

        <div class="row">
            <div class="col-sm-12">
                <div class="white-box">
                    <form action="${pageContext.request.contextPath}/user-table" method="GET" class="form-inline" style="margin-bottom: 16px;">
                        <input type="text" name="keywords" value="<c:out value='${param.keywords}' />" class="form-control" placeholder="Tìm tên hoặc email">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    </form>
                    
                    <div class="table-responsive">
                        <table class="table" id="example">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Full Name</th>
                                    <th>Phone</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>#</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${empty users}">
                                        <tr>
                                            <td colspan="6" class="text-center text-danger font-weight-bold" style="padding: 20px;">Khong tim thay ket qua nao!</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${users}" var="user" varStatus="loop">
                                            <tr>
                                                <td>${loop.count}</td>
                                                <td>${user.fullname}</td>
                                                <td>${user.phone }</td>
                                                <td>${user.email}</td>
                                                <td>${user.roleName}</td>
                                                <td>
                                                    <c:if test="${sessionScope.currentUser.roleId == 1}">
                                                        <a href="${pageContext.request.contextPath}/user-edit?id=${user.id}" class="btn btn-sm btn-primary">Sửa</a>
                                                        
                                                        <c:if test="${user.id != sessionScope.currentUser.id}">
                                                            <form style="display: inline;" action="${pageContext.request.contextPath}/user-delete" method="post" onsubmit="return confirm('Bạn chắc chắn muốn xóa thành viên này?')">
                                                                <input type="hidden" name="id" value="${user.id}">
                                                                <button type="submit" class="btn btn-sm btn-danger">Xóa</button>
                                                            </form>
                                                        </c:if>
                                                    </c:if>
                                                    <a href="${pageContext.request.contextPath}/user-details?id=${user.id}" class="btn btn-sm btn-info">Xem</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                        
                        <div class="text-center">
                            <c:if test="${page > 1}">
                                <c:url var="previousUrl" value="/user-table">
                                    <c:param name="page" value="${page - 1}"></c:param>
                                    <c:param name="keywords" value="${param.keywords}"></c:param>
                                </c:url>
                                <a class="btn btn-default" href="${previousUrl}"> Trước </a>
                            </c:if>
                            
                            <c:forEach begin="1" end="${totalPage}" var="pageNumber">
                                <a href="${pageContext.request.contextPath}/user-table?page=${pageNumber}&keywords=${param.keywords}" class="btn ${pageNumber == page ? 'btn-primary': 'btn-default'} ">
                                    ${pageNumber}
                                </a>
                            </c:forEach>
                            
                            <c:if test="${page < totalPage}">
                                <c:url var="nextUrl" value="/user-table">
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