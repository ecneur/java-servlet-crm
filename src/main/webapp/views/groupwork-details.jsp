<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="page-wrapper">
    <div class="container-fluid">
        <div class="row bg-title">
            <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                <h4 class="page-title">Chi tiết dự án</h4>
            </div>
            <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                <a href="${pageContext.request.contextPath}/groupwork-table" class="btn btn-sm btn-primary">Quay lại</a>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="white-box">
                    <form class="form-horizontal form-material">
                        <div class="form-group">
                            <label class="col-md-12">Tên dự án</label>
                            <div class="col-md-12">
                                <input type="text" value="${project.projectName}" class="form-control form-control-line" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-12">Ngày bắt đầu</label>
                            <div class="col-md-12">
                                <input type="text" value="${project.startDate}" class="form-control form-control-line" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-12">Ngày kết thúc</label>
                            <div class="col-md-12">
                                <input type="text" value="${project.endDate}" class="form-control form-control-line" readonly>
                            </div>
                        </div>
                        
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>