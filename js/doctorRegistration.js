document.addEventListener("DOMContentLoaded", function () {
    const fileInput = document.getElementById("doctorImage");
    const browseBtn = document.querySelector(".browse-btn");

    browseBtn.addEventListener("click", function () {
        fileInput.click();
    });

    fileInput.addEventListener("change", function () {
        if (fileInput.files.length > 0) {
            const fileName = fileInput.files[0].name;
            browseBtn.textContent = fileName;
        }
    });
});