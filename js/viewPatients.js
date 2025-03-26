function searchPatients() {
    let input = document.getElementById("patientSearch").value.toLowerCase();
    let table = document.getElementById("patientTable");
    let rows = table.getElementsByTagName("tr");

    for (let i = 1; i < rows.length; i++) {
        let patientId = rows[i].getElementsByTagName("td")[0]?.textContent.toLowerCase();
        let patientName = rows[i].getElementsByTagName("td")[1]?.textContent.toLowerCase();
        let patientEmail = rows[i].getElementsByTagName("td")[4]?.textContent.toLowerCase();

        if (patientId.includes(input) || patientName.includes(input) || patientEmail.includes(input)) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
}

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