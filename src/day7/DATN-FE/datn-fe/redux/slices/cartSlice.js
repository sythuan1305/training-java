import { createSlice } from '@reduxjs/toolkit';

export const cartSlice = createSlice({
  name: 'cart',
  initialState: {
    cartItems: {},
  },
  reducers: {
    addCartItem: (state, action) => {
      const { newItem } = action.payload;

      const existingItem = state.cartItems[newItem.product_id];
      console.log('existingItem', existingItem);

        if (existingItem) {
            state.cartItems[newItem.product_id].quantity += newItem.quantity;
            state.cartItems[newItem.product_id].price += newItem.price;
        } else {
            state.cartItems[newItem.product_id] = newItem;
        }
    },
    removeCartItem: (state, action) => {
      const { id } = action.payload;
      delete state.cartItems[id];
    },
  },
});

export const { addCartItem, removeCartItem } = cartSlice.actions;
export default cartSlice.reducer;
