/**
 * Reload payment submit button
 */
function reloadPaymentSubmitBtn() {
    // get radio elements is checked using jquery
    let radios = document.querySelectorAll('input[name="discount"]:checked');
    let submitBtn = document.getElementById('submitBtn');
    submitBtn.disabled = radios.length === 0;
}