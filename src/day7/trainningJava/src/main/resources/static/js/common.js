/**
 * Load lại nút submit khi có thay đổi ở form
 * @param form form cần kiểm tra
 * @param btn nút submit cần load lại
 * @param isRadioChecked có kiểm tra radio hay không
 */
function reloadSubmitBtnByForm(form, btn, isRadioChecked = true) {
    let elements = form.elements;
    for (let i = 0; i < elements.length; i++) {
        if (elements[i].tagName.toLowerCase() === "input" && elements[i].value.length === 0 && !isRadioChecked) {
            btn.disabled = true;
            return;
        }
    }
    btn.disabled = false;
}

/**
 * Lấy cart product trong cookie và parse thành json
 * @return {*[]|any} cart product json
 */
function getCartProductInCookieAndParseToJson() {
    return getCookie('cart') === null ? [] : JSON.parse(decodeURIComponent(getCookie('cart')));
}

/**
 * Giải mã chuỗi và parse thành json
 * @param string chuỗi cần decode và parse
 * @return {any} json sau khi parse
 */
function decodedStringAndParseJson(string) {
    let parser = new DOMParser();
    let dom = parser.parseFromString('<!doctype html><body>' + string, 'text/html');
    let decodedString = dom.body.textContent;
    return JSON.parse(decodedString);
}