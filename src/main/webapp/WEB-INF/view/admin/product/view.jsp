<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product detail ${id}</title>
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
                            Product information
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item" style="text-align: center;"><img
                                    src="/images/products/${currentProduct.getImage()}" alt="Image of product"
                                    style="width: 80%;">
                            </li>
                            <li class="list-group-item">Id: ${id}</li>
                            <li class="list-group-item">Name: ${currentProduct.name} </li>
                            <li class="list-group-item">Price: ${currentProduct.price} </li>
                            <li class="list-group-item">Factory: ${currentProduct.factory} </li>
                            <li class="list-group-item">Detail description: ${currentProduct.detailDesc} </li>
                            <li class="list-group-item">Short description: ${currentProduct.shortDesc} </li>
                            <li class="list-group-item">Sold: ${currentProduct.sold} </li>
                            <li class="list-group-item">Quantity: ${currentProduct.quantity} </li>
                            <li class="list-group-item">Target: ${currentProduct.target} </li>
                        </ul>
                    </div>
                    <a href="/admin/product" class="btn btn-secondary mt-2 mb-5">Back</a>
                </div>
            </div>
        </div>
    </body>

    </html>