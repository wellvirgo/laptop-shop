<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="  - Dự án laptopshop" />
                    <meta name="author" content=" " />
                    <title>Order ${id}</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
                        rel="stylesheet"
                        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
                        crossorigin="anonymous">
                    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
                        crossorigin="anonymous"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
                        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
                        crossorigin="anonymous"></script>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <jsp:include page="../layout/sidebar.jsp" />
                        <div id="layoutSidenav_content">
                            <main>
                                <div class="container-fluid px-4">
                                    <div class="mt-5">
                                        <div class="row">
                                            <div class="col-12 mx-auto">
                                                <h2>Update a order</h2>
                                                <hr>
                                                <div class="d-flex">
                                                    <p style="margin-right: 50px;">Order id: ${currentOrder.getId()}</p>
                                                    <p>Total:
                                                        <fmt:formatNumber value="${currentOrder.getTotalPrice()}" />VND
                                                    </p>
                                                </div>
                                                <form:form method="post" action="/admin/order/update"
                                                    modelAttribute="currentOrder">
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                        value="${_csrf.token}" />
                                                    <form:input type="hidden" path="id" />
                                                    <div class="row">
                                                        <div class="mb-3 col-12 col-md-6">
                                                            <label for="user" class="form-label">User</label>
                                                            <form:input id="user" class="form-control" type="text"
                                                                path="user.fullName" readonly="true" />
                                                        </div>
                                                        <div class="mb-3 col-12 col-md-6">
                                                            <label for="status" class="form-label">Status</label>
                                                            <form:select id="status" class="form-select" path="status">
                                                                <form:option value="PENDING">PENDING</form:option>
                                                                <form:option value="SHIPPING">SHIPPING</form:option>
                                                                <form:option value="COMPLETE">COMPLETE</form:option>
                                                                <form:option value="CANCEL">CANCEL</form:option>
                                                            </form:select>
                                                        </div>
                                                    </div>
                                                    <button type="submit" class="btn btn-warning mb-5">Update</button>

                                                </form:form>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </main>
                            <jsp:include page="../layout/footer.jsp" />
                        </div>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="/js/scripts.js"></script>
                </body>

                </html>