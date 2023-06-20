import { createSlice } from '@reduxjs/toolkit';
import Cookies from 'js-cookie';

export const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    cartItems: Cookies.get('cart') ? JSON.parse(Cookies.get('cart')) : {},
    shipping: {},
    paymentMethod: '',
  },
  reducers: {
    addCartItem: (state, action) => {
      const { newItem } = action.payload;

      const existingItem = state.cartItems[newItem.product_id];

      if (existingItem) {
        state.cartItems[newItem.product_id].quantity += newItem.quantity;
        state.cartItems[newItem.product_id].price += newItem.price;
      } else {
        state.cartItems[newItem.product_id] = newItem;
      }

      Cookies.set('cart', JSON.stringify(Object.values(state.cartItems)));
    },
    setCartItem: (state, action) => {
      const { existingItem } = action.payload;
      state.cartItems[existingItem.product_id] = existingItem;
    },
    removeCartItem: (state, action) => {
      const { id } = action.payload;
      delete state.cartItems[id];

      Cookies.set('cart', JSON.stringify(Object.values(state.cartItems)));
    },
    cartReset: (state, action) => {
      const newState = {
        cartItems: {},
        shipping: {},
        paymentMethod: '',
      };
      state = newState;
    },
    saveShipping: (state, action) => {
      state.shipping = { ...state.shipping, ...action.payload };
    },
    savePaymentMethod: (state, action) => {
      state.paymentMethod = action.payload.paymentMethod;
    },
  },
});

export const {
  addCartItem,
  setCartItem,
  removeCartItem,
  cartReset,
  saveShipping,
  savePaymentMethod,
} = cartSlice.actions;
export default cartSlice.reducer;
