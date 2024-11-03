<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!-- Navbar start -->
        <div class="container-fluid fixed-top">
            <div class="container px-0">
                <nav class="navbar navbar-light bg-white navbar-expand-xl">
                    <a href="/" class="navbar-brand">
                        <h1 class="text-primary display-6">LaptopShop</h1>
                    </a>
                    <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse"
                        data-bs-target="#navbarCollapse">
                        <span class="fa fa-bars text-primary"></span>
                    </button>
                    <div class="collapse navbar-collapse bg-white justify-content-between mx-5" id="navbarCollapse">
                        <div class="navbar-nav">
                            <a href="/" class="nav-item nav-link">Trang chủ</a>
                            <a href="/products" class="nav-item nav-link">Sản phẩm</a>
                        </div>

                        <c:if test="${not empty pageContext.request.userPrincipal}">
                            <div class="d-flex m-3 me-0">
                                <button
                                    class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4"
                                    data-bs-toggle="modal" data-bs-target="#searchModal"><i
                                        class="fas fa-search text-primary"></i></button>
                                <a href="/cart" class="position-relative me-4 my-auto">
                                    <i class="fa fa-shopping-bag fa-2x"></i>
                                    <span
                                        class="position-absolute bg-secondary rounded-circle d-flex align-items-center justify-content-center text-dark px-1"
                                        style="top: -5px; left: 15px; height: 20px; min-width: 20px;">${sessionScope.sumInCart}</span>
                                </a>
                                <div class="dropdown my-auto">
                                    <a href="" class="dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
                                        id="dropdownMenuLink" role="button">
                                        <i class="fas fa-user fa-2x"></i>
                                    </a>
                                    <ul class="dropdown-menu dropdown-menu-end mt-2">
                                        <li class="d-flex align-items-center flex-column">
                                            <c:if test="${sessionScope.avatarFile.length()>0}">
                                                <img style="width: 150px;height: 150px;border-radius: 50%; overflow: hidden;"
                                                    src="/images/avatars/${sessionScope.avatarFile}" alt="">
                                            </c:if>
                                            <div class="text-center my-3" style="color: #81c408;">
                                                ${sessionScope.fullName}
                                            </div>
                                        </li>
                                        <li><a class="dropdown-item" href="#">Account management</a></li>
                                        <li><a class="dropdown-item" href="/history-order">Purchase history</a></li>
                                        <li>
                                            <hr class="dropdown-divider">
                                        </li>
                                        <li>
                                            <form method="post" action="/logout">
                                                <input class="form-control" type="hidden" name="${_csrf.parameterName}"
                                                    value="${_csrf.token}" />
                                                <button type="submit" class="dropdown-item">Logout</button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${empty pageContext.request.userPrincipal}">
                            <div class="d-flex m-3 me-0">
                                <button
                                    class="btn-search btn border border-secondary btn-md-square rounded-circle bg-white me-4"
                                    data-bs-toggle="modal" data-bs-target="#searchModal"><i
                                        class="fas fa-search text-primary"></i></button>
                                <a href="/login" class="my-auto btn btn-primary" style="color: #fff;">Login</a>

                            </div>
                        </c:if>
                    </div>
                </nav>
            </div>
        </div>
        <!-- Navbar End -->