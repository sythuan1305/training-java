/**
 * Hàm xử lý khi submit form login
 */
function onLoginFormSubmit()
{
    let cartProducts = document.getElementById("cartProducts");
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cartProducts.value = JSON.stringify(cart);
}