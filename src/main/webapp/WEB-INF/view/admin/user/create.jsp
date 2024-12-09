<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Create user</title>

            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
                integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
                crossorigin="anonymous">
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
                integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
                crossorigin="anonymous"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
                integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
                crossorigin="anonymous"></script>
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            <script>
                $(document).ready(() => {
                    const avatarFile = $("#avatarFile");
                    avatarFile.change(function (e) {
                        const imgURL = URL.createObjectURL(e.target.files[0]);
                        $("#avatarPreview").attr("src", imgURL);
                        $("#avatarPreview").css({ "display": "block" });
                    });
                });
            </script>
        </head>

        <body>
            <div class="container mt-5">
                <div class="row">
                    <div class="col-md-6 col-12 mx-auto">
                        <h1>Create a user</h1>
                        <hr>
                        <form:form method="post" action="/admin/user/create" modelAttribute="newUser" class="row"
                            enctype="multipart/form-data">
                            <input class="form-control" type="hidden" name="${_csrf.parameterName}"
                                value="${_csrf.token}" />
                            <div class="mb-3 col-12 col-md-6">
                                <c:set var="errorEmail">
                                    <form:errors path="email" />
                                </c:set>
                                <label for="email" class="form-label">Email address</label>
                                <form:input type="email" class="form-control ${not empty errorEmail?'is-invalid':''}"
                                    id="email" path="email" />
                                <form:errors path="email" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <c:set var="errorPassword">
                                    <form:errors path="password" />
                                </c:set>
                                <label for="password" class="form-label">Password</label>
                                <form:input type="password"
                                    class="form-control ${not empty errorPassword?'is-invalid':''}" id="password"
                                    path="password" />
                                <form:errors path="password" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <c:set var="errorFullName">
                                    <form:errors path="fullName" />
                                </c:set>
                                <label for="fullname" class="form-label">Full name</label>
                                <form:input type="text" class="form-control ${not empty errorFullName?'is-invalid':''}"
                                    id="fullname" path="fullName" />
                                <form:errors path="fullName" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label for="phone" class="form-label">Phone</label>
                                <form:input type="text" class="form-control" id="phone" path="phone" />
                            </div>
                            <div class="mb-3 col-12">
                                <label for="address" class="form-label">Address</label>
                                <form:input type="text" class="form-control" id="address" path="address" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label for="role" class="form-label">Role</label>
                                <form:select class="form-select" id="role" path="role.name">
                                    <form:option value="ADMIN">Admin</form:option>
                                    <form:option value="USER">User</form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label for="avatarFile" class="form-label">Avatar</label>
                                <div class="input-group mb-3">
                                    <input type="file" class="form-control" id="avatarFile" accept=".png, .jpg, .jpeg"
                                        name="avatarFile">
                                    <label class="input-group-text" for="avatarFile">Upload</label>
                                </div>
                            </div>
                            <div class="col-12 mb-3">
                                <label for="avatarPreview">Avatar preview</label>
                                <img alt="Avatar preview" style="max-height: 250px; display: none;"
                                    id="avatarPreview" />
                            </div>
                            <div class="mb-3 col-12">
                                <button type="submit" class="btn btn-primary">Create</button>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </body>

        </html>