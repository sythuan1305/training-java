// region Kiểm tra nút Previous và Next có bị disable hay không

/**
 * Kiểm tra nút Previous có bị disable hay không
 * @return {boolean} true nếu không bị disable và ngược lại
 */
function isPreviousBtnDisabled() {
    let previous = document.getElementById("li-previous");
    return previous.classList.contains("disabled");
}

/**
 * Kiểm tra nút Next có bị disable hay không
 * @return {boolean} true nếu không bị disable và ngược lại
 */
function isNextBtnDisabled() {
    let next = document.getElementById("li-next");
    return next.classList.contains("disabled");
}

// endregion