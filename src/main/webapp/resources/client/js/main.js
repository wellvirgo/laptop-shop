(function ($) {
    "use strict";

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner(0);


    // Fixed Navbar
    $(window).scroll(function () {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', 0);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        }
    });


    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });


    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 1
            },
            992: {
                items: 2
            },
            1200: {
                items: 2
            }
        }
    });


    // vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: {
                items: 1
            },
            576: {
                items: 1
            },
            768: {
                items: 2
            },
            992: {
                items: 3
            },
            1200: {
                items: 4
            }
        }
    });


    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        console.log($videoSrc);

        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        })

        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        })

        //add class active to header
        const navElement = $('#navbarCollapse');
        const currentUrl = window.location.pathname;
        navElement.find('a.nav-link').each(function () {
            const link = $(this);
            const href = link.attr('href');
            if (currentUrl === href) {
                link.addClass('active');
            } else {
                link.removeClass('active');
            }
        });

    });

    // Product Quantity
    $('.quantity button').on('click', function () {
        let change = 0;
        var button = $(this);
        var oldValue = button.parent().parent().find('input').val();
        if (button.hasClass('btn-plus')) {
            var newVal = parseFloat(oldValue) + 1;
            change = 1;
        } else {
            if (oldValue > 1) {
                var newVal = parseFloat(oldValue) - 1;
                change = -1;
            } else {
                newVal = 1;
            }
        }
        const input = button.parent().parent().find('input');
        input.val(newVal);

        const index = input.attr("data-cart-detail-index");
        const element = document.getElementById(`cartDetails${index}.quantity`);
        $(element).val(newVal);

        // get id and price of product
        const idOfElement = input.attr("data-cart-detail-id");
        const priceOfElement = input.attr("data-cart-detail-price");

        const totalElement = $(`p[data-cart-detail-id='${idOfElement}']`);
        if (totalElement) {
            let newTotal = +priceOfElement * newVal;
            totalElement.text(formatCurrency(newTotal.toFixed(2)));
        }

        // update cartTotal
        let totalElements = $(`p[data-cart-total]`);
        if (totalElements && totalElements.length) {
            let currentCartTotal = totalElements.first().attr('data-cart-total');
            let newTotal = +currentCartTotal;
            if (change === 0) {
                newTotal = +currentCartTotal
            } else {
                newTotal = change * (+priceOfElement) + (+currentCartTotal);
            }
            // reset change
            change = 0;
            totalElements?.each(function (index, element) {
                // update text
                $(totalElements[index]).text(formatCurrency(newTotal.toFixed(2)) + ' VND');
                // update attribute
                $(totalElements[index]).attr('data-cart-total', newTotal);
            });
        }

    });

    function formatCurrency(value) {
        const formatter = new Intl.NumberFormat('vi-VN', {
            style: 'decimal',
            minimumFractionDigits: 0
        });

        let formatted = formatter.format(value);
        formatted = formatted.replace(/\./g, ',');
        return formatted;
    }

    //Filter
    $('#btnFilter').click(function (event) {
        event.preventDefault();
        let factoryArr = [];
        let priceArr = [];
        let targetArr = [];

        //factoryFilter
        $('#factoryFilter .form-check-input:checked').each(function () {
            factoryArr.push($(this).val());
        });

        //targetFilter
        $('#targetFilter .form-check-input:checked').each(function () {
            targetArr.push($(this).val());
        });

        //priceFilter
        $('#priceFilter .form-check-input:checked').each(function () {
            priceArr.push($(this).val());
        });

        //sort
        let sortValue = $('input[name="radio-sort"]:checked').val();

        //update URL
        let currentUrl = new URL(window.location.href);
        let searchParams = currentUrl.searchParams;

        searchParams.delete('factory');
        searchParams.delete('target');
        searchParams.delete('price');

        searchParams.set('page', 1);
        searchParams.set('sort', sortValue);
        if (factoryArr.length > 0) {
            searchParams.set('factory', factoryArr.join(','));
        }
        if (targetArr.length > 0) {
            searchParams.set('target', targetArr.join(','));
        }
        if (priceArr.length > 0) {
            searchParams.set('price', priceArr.join(','));
        }

        window.location.href = currentUrl.toString();
    });

    //handle auto checkbox after page loading
    // Parse the URL parameters
    const params = new URLSearchParams(window.location.search);

    // Set checkboxes for 'factory'
    if (params.has('factory')) {
        const factories = params.get('factory').split(',');
        factories.forEach(factory => {
            $(`#factoryFilter .form-check-input[value="${factory}"]`).prop('checked', true);
        });
    }

    // Set checkboxes for 'target'
    if (params.has('target')) {
        const targets = params.get('target').split(',');
        targets.forEach(target => {
            $(`#targetFilter .form-check-input[value="${target}"]`).prop('checked', true);
        });
    }

    // Set checkboxes for 'price'
    if (params.has('price')) {
        const prices = params.get('price').split(',');
        prices.forEach(price => {
            $(`#priceFilter .form-check-input[value="${price}"]`).prop('checked', true);
        });
    }

    // Set radio buttons for 'sort'
    if (params.has('sort')) {
        const sort = params.get('sort');
        $(`input[type="radio"][name="radio-sort"][value="${sort}"]`).prop('checked', true);
    }

})(jQuery);

