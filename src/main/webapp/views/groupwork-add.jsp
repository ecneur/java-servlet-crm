<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Thêm mới dự án</h4>
            </div>
        </div>
        
        <div class="row">
            <div class="col-md-2 col-12"></div>
            <div class="col-md-8 col-xs-12">
                <div class="white-box">
                    <form action="${pageContext.request.contextPath}/groupwork-add" method="post" class="form-horizontal form-material">
                        <div class="form-group">
                            <label class="col-md-12">Tên dự án</label>
                            <div class="col-md-12">
                                <input type="text" name="projectName" placeholder="Tên dự án" class="form-control form-control-line" required />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-12">Ngày bắt đầu</label>
                            <div class="col-md-12">
                                <input type="date" name="startDate" class="form-control form-control-line" required />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-12">Ngày kết thúc</label>
                            <div class="col-md-12">
                                <input type="date" name="endDate" class="form-control form-control-line" required />
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <button type="submit" class="btn btn-success">Lưu lại</button>
                                <a href="${pageContext.request.contextPath}/groupwork-table" class="btn btn-primary">Quay lại</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="col-md-2 col-12"></div>
        </div>
    </div>
</div>