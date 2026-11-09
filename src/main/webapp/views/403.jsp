<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>403 - Forbidden</title>
    
    <meta name="decorator" content="none">
    
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/assets/plugins/images/favicon.png">
    <link href="${pageContext.request.contextPath}/assets/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/colors/blue.css" id="theme" rel="stylesheet">
    
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
    <div class="preloader">
        <div class="cssload-speeding-wheel"></div>
    </div>
    <section id="wrapper" class="error-page">
        <div class="error-box">
            <div class="error-body text-center">
                <h1 class="text-danger">403</h1>
                <h3 class="text-uppercase">Access Denied !</h3>
                <p class="text-muted m-t-30 m-b-30">BẠN KHÔNG CÓ QUYỀN TRUY CẬP VÀO CHỨC NĂNG NÀY</p>
                <a href="${pageContext.request.contextPath}/index" class="btn btn-info btn-rounded waves-effect waves-light m-b-40">Quay về trang chủ</a>
            </div>
            <footer class="footer text-center">2018 © myclass.com</footer>
        </div>
    </section>
    
    <script src="${pageContext.request.contextPath}/assets/plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/bootstrap/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $(".preloader").fadeOut();
        });
    </script>
</body>
</html>