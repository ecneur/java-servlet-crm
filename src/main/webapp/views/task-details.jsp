<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Chi tiết công việc</h4>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-2 col-12"></div>
            <div class="col-md-8 col-xs-12">
                <div class="white-box">
                    <form class="form-horizontal form-material">
                        
                        <div class="form-group">
                            <label class="col-md-12">Tên công việc</label>
                            <div class="col-md-12">
                                <p class="form-control-static" style="font-weight: 500; font-size: 16px;">${taskDTO.taskName}</p>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-12">Thuộc dự án</label>
                            <div class="col-md-12">
                                <p class="form-control-static">${taskDTO.projectName}</p>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-12">Người đảm nhận</label>
                            <div class="col-md-12">
                                <p class="form-control-static">${taskDTO.userName}</p>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-12">Thời gian thực hiện</label>
                            <div class="col-md-12">
                                <p class="form-control-static">${taskDTO.startDate} <i class="fa fa-arrow-right mx-2"></i> ${taskDTO.endDate}</p>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label class="col-md-12">Trạng thái hiện tại</label>
                            <div class="col-md-12">
                                <p class="form-control-static">
                                    <span class="label label-info">${taskDTO.statusName}</span>
                                </p>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <div class="col-sm-12" style="margin-top: 15px;">
                                <a href="${pageContext.request.contextPath}/task-edit?id=${taskDTO.id}" class="btn btn-primary">Sửa công việc</a>
                                <a href="${pageContext.request.contextPath}/task-table" class="btn btn-default">Quay lại</a>
                            </div>
                        </div>
                        
                    </form>
                </div>
            </div>
            <div class="col-md-2 col-12"></div>
        </div>
    </div>
</div>