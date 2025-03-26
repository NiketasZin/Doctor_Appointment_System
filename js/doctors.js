document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.querySelector(".search-input");
    const doctorCards = document.querySelectorAll(".doctor-card");

    searchInput.addEventListener("input", function () {
        const query = searchInput.value.trim().toLowerCase();

        doctorCards.forEach((card) => {
            const doctorName = card.querySelector("h3").textContent.toLowerCase();
            const doctorEmail = card.querySelector("p:nth-of-type(3) span").textContent.toLowerCase();

            if (doctorName.includes(query) || doctorEmail.includes(query)) {
                card.style.display = "block";
            } else {
                card.style.display = "none";
            }
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const notificationIcon = document.querySelector('.notification-icon');
    const notificationDropdown = document.querySelector('.notification-dropdown');

    notificationIcon.addEventListener('click', function (event) {
        event.stopPropagation();
        notificationDropdown.style.display = notificationDropdown.style.display === 'block' ? 'none' : 'block';
        notificationIcon.style.color = '#50f5f2';
    });

    document.addEventListener('click', function (event) {
        if (!notificationIcon.contains(event.target)) {
            notificationDropdown.style.display = 'none';
            notificationIcon.style.color = '#555';
        }
    });
});