<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Create user</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
        <script>
            $(document).ready(() => {
                const imgFile = $("#imgFile");
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
                    <form:form method="post" action="/admin/user/create" modelAttribute="newProduct" class="row"
                        enctype="multipart/form-data">
                        <div class="mb-3 col-12 col-md-6">
                            <label for="name" class="form-label">Name</label>
                            <form:input type="text" class="form-control" id="name" path="name" />
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="price" class="form-label">Price</label>
                            <form:input type="number" class="form-control" id="price" path="price" />
                        </div>
                        <div class="mb-3 col-12">
                            <label for="detailDesc" class="form-label">Detail descriptions</label>
                            <form:textarea type="text" id="detailDesc" class="form-control" path="detailDesc" />
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="shortDesc" class="form-label">Short descriptions</label>
                            <form:input type="text" class="form-control" id="shortDesc" path="shortDesc" />
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="quantity" class="form-label">Quantity</label>
                            <form:input type="number" class="form-control" id="quantity" path="quantity" />
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="factory" class="form-label">Factory</label>
                            <form:select class="form-select" id="factory" path="factory">
                                <form:option value="HP">HP</form:option>
                                <form:option value="ACER">ACER</form:option>
                            </form:select>
                        </div>
                        <div class="mb-3 col-12 col-md-6">
                            <label for="target" class="form-label">Target</label>
                            <form:select class="form-select" id="target" path="target">
                                <form:option value="Notebook">Notebook</form:option>
                                <form:option value="Gaming">Gaming</form:option>
                            </form:select>
                        </div>
                        <div class="mb-3 col-12">
                            <label for="imgFile" class="form-label">Image</label>
                            <div class="input-group mb-3">
                                <input type="file" class="form-control" id="imgFile" accept=".png, .jpg, .jpeg"
                                    name="imgFile">
                                <label class="input-group-text" for="imgFile">Upload</label>
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