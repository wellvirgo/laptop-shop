<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User detail ${id}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
            integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
            crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
            integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
            crossorigin="anonymous"></script>
    </head>

    <body>
        <div class="container mt-5">
            <div class="row">
                <div class="col-12 mx-auto">
                    <div class="d-flex justify-content-between">
                        <h3>User detail with id= ${id}</h3>
                    </div>
                    <hr>
                    <div class="card" style="width: 60%;">
                        <div class="card-header">
                            User information
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Id: ${id}</li>
                            <li class="list-group-item">Full name: ${user.fullName} </li>
                            <li class="list-group-item">Address: ${user.address} </li>
                            <li class="list-group-item">Phone: ${user.phone} </li>
                            <li class="list-group-item">Email: ${user.email} </li>
                            <li class="list-group-item">Password: ${user.password} </li>
                            <li class="list-group-item">Avatar<br>
                                <img src="/images/avatars/${user.avatar}" alt="User's avatar"
                                    style="display: inline-block; position: relative; width: 200px; height: 200px; overflow: hidden; border-radius: 50%;">
                            </li>
                        </ul>
                    </div>
                    <a href="/admin/user" class="btn btn-secondary mt-2">Back</a>
                </div>
            </div>
        </div>
    </body>

    </html>