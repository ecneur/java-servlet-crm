<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <c:choose>
                    <c:when test="${sessionScope.currentUser.roleId == 3}">
                        <h4 class="page-title">Cập nhật tiến độ công việc</h4>
                    </c:when>
                    <c:otherwise>
                        <h4 class="page-title">Chi tiết công việc</h4>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                <a href="${pageContext.request.contextPath}/task-table" class="btn btn-sm btn-primary">Quay lại</a>
            </div>
        </div>

        <div class="row">
            <div class="col-md-2 col-12"></div>
            <div class="col-md-8 col-xs-12">
                <div class="white-box">
                    <form action="${pageContext.request.contextPath}/task-details" method="post" class="form-horizontal form-material">
                        <input type="hidden" name="id" value="${task.id}">

                        <div class="form-group">
                            <label class="col-md-12">Dự án</label>
                            <div class="col-md-12">
                                <select class="form-control form-control-line" disabled>
                                    <c:forEach items="${projects}" var="p">
                                        <option value="${p.id}" ${p.id == task.projectId ? 'selected' : ''}>${p.projectName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Tên công việc</label>
                            <div class="col-md-12">
                                <input type="text" value="${task.taskName}" class="form-control form-control-line" disabled />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Người thực hiện</label>
                            <div class="col-md-12">
                                <select class="form-control form-control-line" disabled>
                                    <c:forEach items="${users}" var="u">
                                        <option value="${u.id}" ${u.id == task.userId ? 'selected' : ''}>${u.fullname}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Ngày bắt đầu</label>
                            <div class="col-md-12">
                                <input type="date" value="${task.startDate}" class="form-control form-control-line" disabled />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-12">Ngày kết thúc</label>
                            <div class="col-md-12">
                                <input type="date" value="${task.endDate}" class="form-control form-control-line" disabled />
                            </div>
                        </div>

                        <div class="form-group">
                            <c:choose>
                                <c:when test="${sessionScope.currentUser.roleId == 3}">
                                    <label class="col-md-12 text-primary font-weight-bold">Trạng thái (Cho phép cập nhật)</label>
                                    <div class="col-md-12">
                                        <select name="statusId" class="form-control form-control-line" required style="border-bottom: 2px solid #2b2b2b;">
                                            <c:forEach items="${statuses}" var="s">
                                                <option value="${s.id}" ${s.id == task.statusId ? 'selected' : ''}>${s.nameStatus}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <label class="col-md-12">Trạng thái</label>
                                    <div class="col-md-12">
                                        <select class="form-control form-control-line" disabled>
                                            <c:forEach items="${statuses}" var="s">
                                                <option value="${s.id}" ${s.id == task.statusId ? 'selected' : ''}>${s.nameStatus}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-12">
                                <c:if test="${sessionScope.currentUser.roleId == 3}">
                                    <button type="submit" class="btn btn-success">Lưu trạng thái</button>
                                </c:if>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
            <div class="col-md-2 col-12"></div>
        </div>
    </div>
</div>