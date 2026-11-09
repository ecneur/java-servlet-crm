<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
  
<div id="page-wrapper">  
    <div class="container-fluid">  
        <div class="row bg-title">  
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">  
                <h4 class="page-title">Danh sách quyền</h4>  
            </div>  
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">  
                <a href="${pageContext.request.contextPath}/role-add" class="btn btn-sm btn-success">Thêm mới</a>  
            </div>  
        </div>
        <c:if test="${param.add =='1'}">
            <div class="alert alert-success">Them moi thanh cong!</div>
        </c:if>
        <c:if test="${param.update =='1'}">
            <div class="alert alert-success">Cap nhat quyen thanh cong!</div>
        </c:if>
        <c:if test="${param.delete =='1'}">
            <div class="alert alert-success">Xoa quyen thanh cong!</div>
        </c:if>
        <c:if test="${param.err =='1'}">
            <div class="alert alert-danger">Xoa that bai! Quyen nay dang duoc gan cho thanh vien.</div>
        </c:if>  
        <div class="row">  
            <div class="col-sm-12">  
                <div class="white-box">  
                    <div class="table-responsive">  
                        <table class="table" id="example">  
                            <thead>  
                                <tr>  
                                    <th>STT</th>  
                                    <th>Tên Quyền</th>  
                                    <th>Mô Tả</th>  
                                    <th>Hành Động</th>  
                                </tr>  
                            </thead>  
                            <tbody>  
                                <c:choose>
                                    <c:when test="${empty list}">
                                        <tr>
                                            <td colspan="4" class="text-center text-danger font-weight-bold" style="padding: 20px;">Không tìm thấy kết quả nào!</td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${list}" var="role" varStatus="loop">  
                                            <tr>  
                                                <td>${loop.count}</td>  
                                                <td>${role.nameRole}</td>  
                                                <td>${role.description}</td>  
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/role-edit?id=${role.id}" class="btn btn-sm btn-primary">Sửa</a> 
                                                    <a href="${pageContext.request.contextPath}/role-delete?id=${role.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa quyền này không?')">Xóa</a>
                                                </td>  
                                            </tr>  
                                        </c:forEach>  
                                    </c:otherwise>
                                </c:choose>
                            </tbody>  
                        </table>  
                    </div>  
                </div>  
            </div>  
        </div>  
    </div>  
    <footer class="footer text-center"> 2018 &copy; myclass.com </footer>  
</div>