/**
 * Reload submit button
 */
function ReloadSubmitBtn() {
    let checkboxes = document.querySelectorAll('input[type="checkbox"]:checked');
    let submitButton = document.getElementById("submit-button");
    // nếu không có checkbox nào được chọn thì disable nút submit
    submitButton.disabled = checkboxes.length === 0;
}