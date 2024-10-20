<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update user</title>

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
                const imgFile = $("#avatarFile");
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
                    <h1>Update user</h1>
                    <hr>
                    <form:form method="post" action="/admin/user/update" modelAttribute="currentUser"
                        enctype="multipart/form-data">
                        <div class="mb-3" style="display: none;">
                            <label for="userId" class="form-label">ID</label>
                            <form:input type="text" class="form-control" id="userId" path="id" />
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email address</label>
                            <form:input type="email" class="form-control" id="email" aria-describedby="emailHelp"
                                path="email" disabled="true" />
                        </div>
                        <div class="mb-3">
                            <label for="fullname" class="form-label">Full name</label>
                            <form:input type="text" class="form-control" id="fullname" path="fullName" />
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address</label>
                            <form:input type="text" class="form-control" id="address" path="address" />
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">phone</label>
                            <form:input type="text" class="form-control" id="phone" path="phone" />
                        </div>

                        <div class="mb-3">
                            <label for="avatarFile" class="form-label">Avatar</label>
                            <div class="input-group mb-3">
                                <input type="file" class="form-control" id="avatarFile" name="avatarFile"
                                    accept=".png, .jpg, .jpeg">
                                <label class="input-group-text" for="avatarFile">Upload</label>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="imgPreview" class="form-label">Image preview</label>
                            <img alt="Image preview" style="max-height: 250px; display: block;" id="imgPreview"
                                src="/images/avatars/${currentUser.getAvatar()}" />
                        </div>

                        <button type="submit" class="btn btn-warning">Update</button>
                    </form:form>
                </div>
            </div>
        </div>
    </body>

    </html>