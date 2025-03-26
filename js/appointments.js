document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.querySelector(".search-input");
    const table = document.querySelector(".table tbody");
    const rows = table.getElementsByTagName("tr");

    searchInput.addEventListener("input", function () {
        let query = searchInput.value.toLowerCase();

        for (let i = 0; i < rows.length; i++) {
            let date = rows[i].getElementsByTagName("td")[0]?.textContent.toLowerCase() || "";
            let doctorName = rows[i].getElementsByTagName("td")[3]?.textContent.toLowerCase() || "";
            
            if (date.includes(query) || doctorName.includes(query)) {
                rows[i].style.display = "";
            } else {
                rows[i].style.display = "none";
            }
        }
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