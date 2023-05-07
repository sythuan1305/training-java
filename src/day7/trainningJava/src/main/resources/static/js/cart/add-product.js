/**
 * Reload submit button by form
 */
function reloadAddProductBtnByProductQuantity() {
    let quantity = document.getElementById("product_quantity");
    document.getElementById("btn_add_product").disabled = quantity.value <= 0;
}

/**
 * Tính tổng tiền của sản phẩm
 */
function calculateTotalAmount() {
    let quantity = document.getElementById("product_quantity");
    let price_total = document.getElementById("product_price");
    let price = "[[${product.getPrice()}]]";
    price_total.value = price * quantity.value;
}

/**
 * Thêm sản phẩm của giỏ hàng vào cookie
 * @param cart_product sản phẩm của giỏ hàng
 */
function addCartProductToCookie(cart_product) {
    // lấy ra giỏ hàng từ cookie
    let cart = getCartProductInCookieAndParseToJson() || [];

    // kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
    // nếu có thì cộng thêm số lượng và giá
    let index = -1;
    if (cart.length > 0)
        index = cart.findIndex(x => x.product_id === cart_product.product_id);

    // nếu chưa có thì thêm mới
    if (index !== -1) {
        cart[index].quantity = +cart[index].quantity + parseInt(cart_product.quantity);
        cart[index].price = +cart[index].price + parseFloat(cart_product.price);
    } else {
        cart.push(cart_product);
    }

    setCookie('cart', encodeURIComponent(JSON.stringify(cart)), 1);
}

/**
 * set giá trị cho input cart_product
 * @param cart_product
 */
function setCartProductToInputForm(cart_product) {
    document.getElementById("cart_product").value = JSON.stringify(cart_product);
}

/**
 * Xử lý khi submit form thêm sản phẩm vào giỏ hàng
 */
function onAddFormSubmit() {
    let cart_product = createCartProductFromAddForm();
    if (isAuthenticated === "false") {
        addCartProductToCookie(cart_product);
    }
    setCartProductToInputForm([cart_product]);
}

/**
 * Tạo ra một đối tượng cart_product từ form thêm sản phẩm vào giỏ hàng
 * @return một đối tượng cart_product
 */
function createCartProductFromAddForm() {
    let add_form = document.getElementById("add_form");
    return {
        // auto generate integer id unique
        id: Date.now(),
        product_id: add_form.elements["product_id"].value,
        name: add_form.elements["product_name"].value,
        price: add_form.elements["product_price"].value,
        quantity: add_form.elements["product_quantity"].value,
    };
}

/**
 * Onload add product page
 */
function onLoadAddProduct() {
    let cart_product = createCartProductFromAddForm();
    setCartProductToInputForm(cart_product);
    reloadSubmitBtnByForm(document.getElementById('add_form'), document.getElementById('btn_add_product'));
}

// for test only
// function onChange()
// {
//     let cart_product = createCartProductFromAddForm();
//     addCartProductToCookie(cart_product); // bug add to cart when change quantity (WARNING)
//     setCartProduct(cart_product);
// }