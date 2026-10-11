<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Danh sách dự án</h4>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                <a href="${pageContext.request.contextPath}/groupwork-add" class="btn btn-sm btn-success">Thêm mới</a>
            </div>
        </div>
        
        <div class="row">
            <div class="col-sm-12">
                <div class="white-box">
                    
                    <form action="${pageContext.request.contextPath}/groupwork-table" method="GET" class="form-inline" style="margin-bottom: 16px;">
                        <input type="text" name="keywords" value="<c:out value='${param.keywords}' />" class="form-control" placeholder="Tìm tên dự án">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    </form>

                    <div class="table-responsive">
                        <table class="table" id="example">
                            <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên Dự Án</th>
                                    <th>Ngày Bắt Đầu</th>
                                    <th>Ngày Kết Thúc</th>
                                    <th>Hành Động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${list}" var="project">
                                    <tr>
                                        <td>${project.id}</td>
                                        <td>${project.projectName}</td>
                                        <td>${project.startDate}</td>
                                        <td>${project.endDate}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/groupwork-edit?id=${project.id}" class="btn btn-sm btn-primary">Sửa</a>
                                            <a href="${pageContext.request.contextPath}/groupwork-delete?id=${project.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa dự án này không?')">Xóa</a>
                                            <a href="${pageContext.request.contextPath}/groupwork-details?id=${project.id}" class="btn btn-sm btn-info">Xem</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        
                        <div class="text-center">
                            <c:if test="${page > 1}">
                                <c:url var="previousUrl" value="/groupwork-table">
                                    <c:param name="page" value="${page - 1}"></c:param>
                                    <c:param name="keywords" value="${param.keywords}"></c:param>
                                </c:url>
                                <a class="btn btn-default" href="${previousUrl}"> Trước </a>
                            </c:if>
                            
                            <c:forEach begin="1" end="${totalPage}" var="pageNumber">
                                <a href="${pageContext.request.contextPath}/groupwork-table?page=${pageNumber}&keywords=${param.keywords}" class="btn ${pageNumber == page ? 'btn-primary' : 'btn-default'}">
                                    ${pageNumber}
                                </a>
                            </c:forEach>
                            
                            <c:if test="${page < totalPage}">
                                <c:url var="nextUrl" value="/groupwork-table">
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