document.addEventListener("DOMContentLoaded", function () {
    // Swiper Initialization
    const swiper = new Swiper('.swiper', {
        autoplay: {
            delay: 2000,
            disableOnInteraction: false,
        },
        loop: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
    });

    // Notification Dropdown Logic
    const notificationIcon = document.querySelector(".notification-icon");
    const notificationDropdown = document.querySelector(".notification-dropdown");

    notificationIcon.addEventListener("click", function (event) {
        event.stopPropagation();
        notificationDropdown.style.display = notificationDropdown.style.display === "block" ? "none" : "block";
        notificationIcon.style.color = "#50f5f2";
    });

    document.addEventListener("click", function (event) {
        if (!notificationIcon.contains(event.target)) {
            notificationDropdown.style.display = "none";
            notificationIcon.style.color = "#555";
        }
    });
});