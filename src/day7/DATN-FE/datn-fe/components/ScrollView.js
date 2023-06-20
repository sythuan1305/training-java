import Image from 'next/image';
import React, { useEffect, useRef, useState } from 'react';

import { motion, useScroll, wrap } from 'framer-motion';
import { AnimatePresence } from 'framer-motion';
import ProductItem from './ProductItem';
import { toast } from 'react-toastify';
import { addCartItem, setCartItem } from '@/redux/slices/cartSlice';
import { useDispatch, useSelector } from 'react-redux';
import { ProductDetail } from '@/service/productServices';

const MAX_ITEMS_VISIBLE = 4;

const ScrollView = ({ items }) => {
  const cartItems = useSelector((state) => Object.values(state.cart.cartItems));

  const dispatch = useDispatch();

  console.log('cartItems', cartItems);

  const items_length_visible =
    items.length < MAX_ITEMS_VISIBLE ? items.length : MAX_ITEMS_VISIBLE;

  const [current, setCurrent] = useState(0);
  const prevBtn = useRef(null);
  const nextBtn = useRef(null);

  useEffect(() => {
    prevBtn.current.disabled = current === 0;
    nextBtn.current.disabled = current === items.length - items_length_visible;
  }, [current, items.length, items_length_visible]);

  const handleClick = (direction) => {
    setCurrent(current + direction);
  };

  const addToCartHandler = async (product) => {
    console.log('product', product);
    console.log('cartItems', cartItems);
    const existItem = cartItems.find((x) => x.product_id === product.id);
    console.log('exitsItem', existItem);


    const quantity = existItem ? existItem.quantity + 1 : 1;
    const { data } = await ProductDetail({ id: product.id });

    if (data.quantity < quantity) {
      return toast.error('Sorry. Product is out of stock');
    }
    existItem
      ? dispatch(setCartItem({ existingItem: { ...existItem, quantity } }))
      : dispatch(
          addCartItem({
            newItem: {
              id: Date.now(),
              product_id: data.id,
              name: data.name,
              price: data.price * quantity,
              quantity,
              stock: data.quantity,
            },
          })
        );

    toast.success('Product added to the cart');
  };

  const itemsToDisplay = items.slice(current, current + items_length_visible);

  return (
    <div className='flex items-center justify-center'>
      <div>
        <button
          ref={prevBtn}
          className={
            'rounded-md bg-red-300 px-4 py-2 font-bold text-gray-800 hover:bg-gray-400 disabled:bg-blue-300'
          }
          onClick={() => handleClick(-1)}
        >
          Previous
        </button>
      </div>
      <div className='grid flex-1 grid-cols-1 gap-4 md:grid-cols-4 lg:grid-cols-4'>
        {itemsToDisplay.map((item, index) => (
          <ProductItem addToCartHandler={addToCartHandler} product={item} key={item.id} className='item' />
        ))}
      </div>
      <div>
        <button
          type='button'
          ref={nextBtn}
          className={
            'rounded-md bg-red-300 px-4 py-2 font-bold text-gray-800 hover:bg-gray-400 disabled:bg-blue-300'
          }
          onClick={() => handleClick(1)}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default ScrollView;
