<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Purchase history</title>
                <!-- Google Web Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">

                <!-- Icon Font Stylesheet -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                <!-- Customized Bootstrap Stylesheet -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">
            </head>

            <body>
                <!--header-->
                <jsp:include page="../layout/header.jsp" />

                <!-- Single Page Header start -->
                <div class="container-fluid page-header py-5">
                    <h1 class="text-center text-white display-6">Order history</h1>
                    <ol class="breadcrumb justify-content-center mb-0">
                        <li class="breadcrumb-item"><a href="/">Home</a></li>
                        <li class="breadcrumb-item active text-white">Order history</li>
                    </ol>
                </div>
                <!-- Single Page Header End -->

                <!--Order page start-->
                <div class="container-fluid py-5">
                    <div class="container py-5">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Products</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Price</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Total</th>
                                        <th scope="col">Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${orders.size()>0}">
                                        <c:forEach var="order" items="${orders}" varStatus="status">
                                            <tr>
                                                <td colspan="2" style="color: #81c408;">Order id = ${order.getId()}</td>
                                                <td colspan="2"></td>
                                                <td colspan="1" style="color: #81c408;">
                                                    <fmt:formatNumber value="${order.getTotalPrice()}" /> VND
                                                </td>
                                                <td colspan="1" style="color: #fd5c63;">${order.getStatus()}</td>
                                            </tr>
                                            <c:forEach var="orderDetail" items="${order.getOrderDetails()}">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex align-items-center">
                                                            <img src="/images/products/${orderDetail.getProduct().getImage()}"
                                                                class="img-fluid me-5 rounded-circle"
                                                                style="width: 80px; height: 80px;" alt="">
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <p class="mb-0 mt-4">${orderDetail.getProduct().getName()}</p>
                                                    </td>
                                                    <td>
                                                        <p class="mb-0 mt-4">
                                                            <fmt:formatNumber value="${orderDetail.getPrice()}" />
                                                            VND
                                                        </p>
                                                    </td>
                                                    <td>
                                                        <p class="mb-0 mt-4">${orderDetail.getQuantity()}</p>
                                                    </td>
                                                    <td>
                                                        <p class="mb-0 mt-4">
                                                            <fmt:formatNumber
                                                                value="${orderDetail.getPrice()*orderDetail.getQuantity()}" />
                                                            VND
                                                        </p>
                                                    </td>
                                                    <td></td>
                                                </tr>


                                            </c:forEach>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>
                            <c:if test="${orders.size()==0}">
                                <h3 style="color: #81c408; text-align: center;">Purchase history is empty</h3>
                            </c:if>
                        </div>

                    </div>
                </div>
                <!--Order page end-->

                <!--footer-->
                <jsp:include page="../layout/footer.jsp" />

                <!-- Back to Top -->
                <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                        class="fa fa-arrow-up"></i></a>


                <!-- JavaScript Libraries -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                <!-- Template Javascript -->
                <script src="/client/js/main.js"></script>
            </body>

            </html>