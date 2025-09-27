$(document).ready(function() {
    // Gọi API Category và render
    $.ajax({
        url: '/api/category',
        type: 'GET',
        success: function(response) {
            if (response.status) {
                var categories = response.body;
                categories.forEach(function(cat) {
                    $('#categoryTable').append('<tr><td>' + cat.categoryId + '</td><td>' + cat.categoryName + '</td><td><img src="/upload-dir/' + cat.icon + '" width="50"/></td></tr>');
                });
            }
        }
    });

    // Gọi API Product và render
    $.ajax({
        url: '/api/product',
        type: 'GET',
        success: function(response) {
            if (response.status) {
                var products = response.body;
                products.forEach(function(prod) {
                    $('#productTable').append('<tr><td>' + prod.productId + '</td><td>' + prod.productName + '</td><td>' + prod.unitPrice + '</td></tr>');
                });
            }
        }
    });
});