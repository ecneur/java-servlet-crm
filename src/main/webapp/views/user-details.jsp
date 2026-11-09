<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Chi tiết thành viên</h4>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                <a href="${pageContext.request.contextPath}/user-table" class="btn btn-sm btn-primary">Quay lại</a>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 col-xs-12">
                <div class="white-box">
                    <div class="user-bg"> <img width="100%" alt="user" src="${pageContext.request.contextPath}/assets/plugins/images/large/img1.jpg">
                        <div class="overlay-box">
                            <div class="user-content">
                                <a href="javascript:void(0)"><img src="${pageContext.request.contextPath}/assets/plugins/images/users/genu.jpg" class="thumb-lg img-circle" alt="img"></a>
                                <h4 class="text-white">${userDetail.fullname}</h4>
                                <h5 class="text-white">${userDetail.email}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-8 col-xs-12">
                <div class="row">
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-xs-12">
                                    <h3 class="counter text-right m-t-15 text-danger">${notStartedPct}%</h3>
                                </div>
                                <div class="col-xs-12">
                                    <i data-icon="E" class="linea-icon linea-basic"></i>
                                    <h5 class="text-muted vb text-center">CHƯA BẮT ĐẦU</h5>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="${notStartedPct}" aria-valuemin="0" aria-valuemax="100" style="width: ${notStartedPct}%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-xs-12">
                                    <h3 class="counter text-right m-t-15 text-megna">${inProgressPct}%</h3>
                                </div>
                                <div class="col-xs-12">
                                    <i class="linea-icon linea-basic" data-icon="&#xe01b;"></i>
                                    <h5 class="text-muted vb text-center">ĐANG THỰC HIỆN</h5>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-megna" role="progressbar" aria-valuenow="${inProgressPct}" aria-valuemin="0" aria-valuemax="100" style="width: ${inProgressPct}%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-xs-12">
                                    <h3 class="counter text-right m-t-15 text-primary">${completedPct}%</h3>
                                </div>
                                <div class="col-xs-12">
                                    <i class="linea-icon linea-basic" data-icon="&#xe00b;"></i>
                                    <h5 class="text-muted vb text-center">HOÀN THÀNH</h5>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuenow="${completedPct}" aria-valuemin="0" aria-valuemax="100" style="width: ${completedPct}%"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <h4>DANH SÁCH CÔNG VIỆC</h4>
        <div class="row">
            <div class="col-md-4">
                <div class="white-box">
                    <h3 class="box-title">Chưa thực hiện</h3>
                    <div class="message-center">
                        <c:forEach items="${notStartedTasks}" var="task">
                            <a href="#">
                                <div class="mail-contnet">
                                    <h5>${task.taskName}</h5>
                                    <span class="mail-desc">Dự án: ${task.projectName}</span>
                                    <span class="time">Bắt đầu: ${task.startDate}</span>
                                    <span class="time">Kết thúc: ${task.endDate}</span>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="white-box">
                    <h3 class="box-title">Đang thực hiện</h3>
                    <div class="message-center">
                        <c:forEach items="${inProgressTasks}" var="task">
                            <a href="#">
                                <div class="mail-contnet">
                                    <h5>${task.taskName}</h5>
                                    <span class="mail-desc">Dự án: ${task.projectName}</span>
                                    <span class="time">Bắt đầu: ${task.startDate}</span>
                                    <span class="time">Kết thúc: ${task.endDate}</span>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="white-box">
                    <h3 class="box-title">Đã hoàn thành</h3>
                    <div class="message-center">
                        <c:forEach items="${completedTasks}" var="task">
                            <a href="#">
                                <div class="mail-contnet">
                                    <h5>${task.taskName}</h5>
                                    <span class="mail-desc">Dự án: ${task.projectName}</span>
                                    <span class="time">Bắt đầu: ${task.startDate}</span>
                                    <span class="time">Kết thúc: ${task.endDate}</span>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>