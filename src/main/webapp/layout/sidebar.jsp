<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
    @media (min-width: 768px) {
        .sidebar .navbar-collapse {
            display: block !important;
            height: auto !important;
        }
    }
</style>
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse slimscrollsidebar">
        <ul class="nav" id="side-menu">
            
            <!-- Dashboard-->
            <li style="padding: 10px 0 0;">
                <a href="${pageContext.request.contextPath}/index" class="waves-effect">
                    <i class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span class="hide-menu">Dashboard</span>
                </a>
            </li>

            <!-- Thành viên: Admin (1) và Manager (2) thấy -->
            <c:if test="${sessionScope.currentUser.roleId == 1 || sessionScope.currentUser.roleId == 2}">
                <li>
                    <a href="${pageContext.request.contextPath}/user-table" class="waves-effect">
                        <i class="fa fa-user fa-fw" aria-hidden="true"></i><span class="hide-menu">Thành viên</span>
                    </a>
                </li>
            </c:if>

            <!-- Quyền: Chỉ Admin (1) thấy -->
            <c:if test="${sessionScope.currentUser.roleId == 1}">
                <li>
                    <a href="${pageContext.request.contextPath}/role-table" class="waves-effect">
                        <i class="fa fa-modx fa-fw" aria-hidden="true"></i><span class="hide-menu">Quyền</span>
                    </a>
                </li>
            </c:if>

            <!-- Dự án: Admin (1) và Manager (2) thấy -->
            <c:if test="${sessionScope.currentUser.roleId == 1 || sessionScope.currentUser.roleId == 2}">
                <li>
                    <a href="${pageContext.request.contextPath}/groupwork-table" class="waves-effect">
                        <i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Dự án</span>
                    </a>
                </li>
            </c:if>

            <!-- Công việc: Ai cũng thấy (Không bọc c:if) -->
            <li>
                <a href="${pageContext.request.contextPath}/task-table" class="waves-effect">
                    <i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Công việc</span>
                </a>
            </li>

            <!-- Các trang dư thừa (Blank/404): Chỉ để Admin thấy cho sạch giao diện Demo -->
            <c:if test="${sessionScope.currentUser.roleId == 1}">
                <li>
                    <a href="${pageContext.request.contextPath}/blank" class="waves-effect">
                        <i class="fa fa-columns fa-fw" aria-hidden="true"></i><span class="hide-menu">Blank Page</span>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/404" class="waves-effect">
                        <i class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span class="hide-menu">Error 404</span>
                    </a>
                </li>
            </c:if>
            
        </ul>
    </div>
</div>