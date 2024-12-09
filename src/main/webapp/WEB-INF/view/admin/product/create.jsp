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
                    const imgFile = $("#productFile");
                    imgFile.change(function (e) {
                        const imgURL = URL.createObjectURL(e.target.files[0]);
                        $("#imgPreview").attr("src", imgURL);
                        $("#imgPreview").css({ "display": "block" });
                    });
                });
            </script>
        </head>

        <body>
            <div class="container mt-5">
                <div class="row">
                    <div class="col-md-6 col-12 mx-auto">
                        <h1>Create a product</h1>
                        <hr>
                        <form:form method="post" action="/admin/product/create" modelAttribute="newProduct" class="row"
                            enctype="multipart/form-data">
                            <input class="form-control" type="hidden" name="${_csrf.parameterName}"
                                value="${_csrf.token}" />
                            <div class="mb-3 col-12 col-md-6">
                                <c:set var="errName">
                                    <form:errors path="name" />
                                </c:set>
                                <label for="name" class="form-label">Name</label>
                                <form:input type="text" class="form-control ${not empty errName?'is-invalid':''}"
                                    id="name" path="name" />
                                <form:errors path="name" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <c:set var="errPrice">
                                    <form:errors path="price" />
                                </c:set>
                                <label for="price" class="form-label">Price</label>
                                <form:input type="number" class="form-control ${not empty errPrice?'is-invalid':''}"
                                    id="price" path="price" />
                                <form:errors path="price" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12">
                                <c:set var="errDetailDesc">
                                    <form:errors path="detailDesc" />
                                </c:set>
                                <label for="detailDesc" class="form-label">Detail descriptions</label>
                                <form:textarea type="text" id="detailDesc"
                                    class="form-control ${not empty errDetailDesc?'is-invalid':''}" path="detailDesc" />
                                <form:errors path="detailDesc" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <c:set var="errShortDesc">
                                    <form:errors path="shortDesc" />
                                </c:set>
                                <label for="shortDesc" class="form-label">Short descriptions</label>
                                <form:input type="text" class="form-control ${not empty errShortDesc?'is-invalid':''}"
                                    id="shortDesc" path="shortDesc" />
                                <form:errors path="shortDesc" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <c:set var="errQuantity">
                                    <form:errors path="quantity" />
                                </c:set>
                                <label for="quantity" class="form-label">Quantity</label>
                                <form:input type="number" class="form-control ${not empty errQuantity?'is-invalid':''}"
                                    id="quantity" path="quantity" />
                                <form:errors path="quantity" cssClass="invalid-feedback" />
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label for="factory" class="form-label">Factory</label>
                                <form:select class="form-select" id="factory" path="factory">
                                    <form:option value="HP">HP</form:option>
                                    <form:option value="ACER">ACER</form:option>
                                    <form:option value="ASUS">ASUS</form:option>
                                    <form:option value="DELL">DELL</form:option>
                                    <form:option value="LENOVO">LENOVO</form:option>
                                    <form:option value="APPLE">APPLE</form:option>
                                    <form:option value="LG">LG</form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 col-12 col-md-6">
                                <label for="target" class="form-label">Target</label>
                                <form:select class="form-select" id="target" path="target">
                                    <form:option value="SINHVIEN-VANPHONG">SINHVIEN-VANPHONG</form:option>
                                    <form:option value="GAMING">GAMING</form:option>
                                    <form:option value="THIET-KE-DO-HOA">THIET-KE-DO-HOA</form:option>
                                    <form:option value="DOANH-NHAN">DOANH-NHAN</form:option>
                                    <form:option value="MONG-NHE">MONG-NHE</form:option>
                                </form:select>
                            </div>
                            <div class="mb-3 col-12">
                                <label for="productFile" class="form-label">Image</label>
                                <div class="input-group mb-3">
                                    <input type="file" class="form-control" id="productFile" accept=".png, .jpg, .jpeg"
                                        name="productFile">
                                    <label class="input-group-text" for="productFile">Upload</label>
                                </div>
                            </div>
                            <div class="col-12 mb-3">
                                <label for="imgPreview">Image preview</label>
                                <img alt="Image preview" style="max-height: 250px; display: none;" id="imgPreview" />
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