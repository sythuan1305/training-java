import { createSlice } from '@reduxjs/toolkit';
import Cookies from 'js-cookie';

export const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    cartItems: Cookies.get("cart") ? JSON.parse(Cookies.get("cart")) : {},
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
    removeCartItem: (state, action) => {
      const { id } = action.payload;
      delete state.cartItems[id];

      Cookies.set('cart', JSON.stringify(Object.values(state.cartItems)));
    },
  },
});

export const { addCartItem, removeCartItem } = cartSlice.actions;
export default cartSlice.reducer;
