import Image from 'next/image';
import React, { useEffect, useRef, useState } from 'react';

import { motion, useScroll, wrap } from 'framer-motion';
import { AnimatePresence } from 'framer-motion';
import ProductItem from './ProductItem';

const MAX_ITEMS_VISIBLE = 4;

const ScrollView = ({ items }) => {
  const items_length_visible = items.length < MAX_ITEMS_VISIBLE ? items.length : MAX_ITEMS_VISIBLE;

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

  const itemsToDisplay = items.slice(current, current + items_length_visible);

  return (
    <div className='flex justify-center items-center'>
      <div>
        <button ref={prevBtn} className={'bg-red-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-md disabled:bg-blue-300'}  onClick={() => handleClick(-1)}>Previous</button>
      </div>
      <div className='grid grid-cols-1 gap-4 md:grid-cols-4 lg:grid-cols-4 flex-1'>
        {itemsToDisplay.map(
          (item, index) =>
            (
              <ProductItem product={item} key={item.id} className='item' />
            )
        )}
      </div>
      <div>
        <button type="button" ref={nextBtn}  className={'bg-red-300 hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded-md disabled:bg-blue-300'} onClick={() => handleClick(1)}>Next</button>
      </div>
    </div>
  );
};

export default ScrollView;
