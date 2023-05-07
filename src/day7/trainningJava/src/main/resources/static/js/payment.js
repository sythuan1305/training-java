function isRadioChecked() {
    const radios = document.querySelectorAll('input[name="paymentMethod"]:checked');
    return radios.length > 0;
}

/**
 * Onload payment page
 */
function onLoadPayment() {
    // validate form payment
    reloadSubmitBtnByForm(document.getElementById('form-payment'), document.getElementById('btn-order'), isRadioChecked());
}