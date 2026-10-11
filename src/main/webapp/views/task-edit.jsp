<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Sửa công việc</h4>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-2 col-12"></div>
            <div class="col-md-8 col-xs-12">
                <div class="white-box">
                    <form action="${pageContext.request.contextPath}/task-edit" method="post" class="form-horizontal form-material">
                        <input type="hidden" name="id" value="${task.id}" />
                        
                        <div class="form-group">
                            <label class="col-md-12">Dự án</label>
                            <div class="col-md-12">
                                <select name="projectId" class="form-control form-control-line" required>
                                    <c:forEach items="${projects}" var="p">
                                        <option value="${p.id}" <c:if test="${task.projectId == p.id}">selected</c:if>>${p.projectName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Tên công việc</label>
                            <div class="col-md-12">
                                <input type="text" name="taskName" value="${task.taskName}" placeholder="Tên công việc" class="form-control form-control-line" required />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Người thực hiện</label>
                            <div class="col-md-12">
                                <select name="userId" class="form-control form-control-line" required>
                                    <c:forEach items="${users}" var="u">
                                        <option value="${u.id}" <c:if test="${task.userId == u.id}">selected</c:if>>${u.fullname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Ngày bắt đầu</label>
                            <div class="col-md-12">
                                <input type="date" name="startDate" value="${task.startDate}" class="form-control form-control-line" required />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Ngày kết thúc</label>
                            <div class="col-md-12">
                                <input type="date" name="endDate" value="${task.endDate}" class="form-control form-control-line" required />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Trạng thái</label>
                            <div class="col-md-12">
                                <select name="statusId" class="form-control form-control-line" required>
                                    <c:forEach items="${statuses}" var="s">
                                        <option value="${s.id}" <c:if test="${task.statusId == s.id}">selected</c:if>>${s.nameStatus}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <div class="col-sm-12">
                                <button type="submit" class="btn btn-success">Lưu Thay Đổi</button>
                                <a href="${pageContext.request.contextPath}/task-table" class="btn btn-primary">Quay lại</a>
                            </div>
                        </div>
                        
                    </form>
                </div>
            </div>
            <div class="col-md-2 col-12"></div>
        </div>
    </div>
</div>