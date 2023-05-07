/**
 * Lấy ra cookie theo tên
 * @param cookieName tên cookie
 * @return {null|string}
 */
function getCookie(cookieName) {
    let decodedCookie = decodeURIComponent(document.cookie);
    let cookieArray = decodedCookie.split(';');
    let name = cookieName + "=";
    for(let i = 0; i <cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) === ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) === 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return null;
}

/**
 * Set cookie
 * @param cookieName tên cookie
 * @param cookieValue giá trị cookie
 * @param exdays số ngày tồn tại
 */
function setCookie(cookieName, cookieValue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires="+ d.toUTCString();
    document.cookie = cookieName + "=" + cookieValue + ";" + expires + ";path=/" +
        ";SameSite=Lax"
    ;
}