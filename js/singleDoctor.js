document.addEventListener("DOMContentLoaded", function() {
    // Notification dropdown functionality
    const notificationIcon = document.querySelector('.notification-icon');
    const notificationDropdown = document.querySelector('.notification-dropdown');

    if (notificationIcon && notificationDropdown) {
        notificationIcon.addEventListener('click', function(event) {
            event.stopPropagation();
            const isVisible = notificationDropdown.style.display === 'block';
            notificationDropdown.style.display = isVisible ? 'none' : 'block';
            notificationIcon.style.color = isVisible ? '#555' : '#50f5f2';
        });

        document.addEventListener('click', function(event) {
            if (!notificationIcon.contains(event.target) && 
                !notificationDropdown.contains(event.target)) {
                notificationDropdown.style.display = 'none';
                notificationIcon.style.color = '#555';
            }
        });
    }

    // Search functionality for appointments table
    const searchInput = document.getElementById('appointmentSearch');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            const filter = this.value.toLowerCase();
            const rows = document.querySelectorAll('#appointmentsTable tbody tr');
            
            rows.forEach(row => {
                const text = row.textContent.toLowerCase();
                row.style.display = text.includes(filter) ? '' : 'none';
            });
        });
    }

    // Confirmation for booking appointments
    const bookingForms = document.querySelectorAll('form[th\\:action="@{/patient/bookAppointment}"]');
    bookingForms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!confirm('Are you sure you want to book this appointment?')) {
                e.preventDefault();
            }
        });
    });
});