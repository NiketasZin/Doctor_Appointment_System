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