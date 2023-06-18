import Image from 'next/image';
import React, { useEffect, useRef, useState } from 'react';

const MAX_ITEMS_VISIBLE = 4;
const ImageContainer = (props) => {
  const { defaultImageUrl, name, items } = props;

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

  const itemsToDisplay = items.slice(current, current + items_length_visible);

  return (
    <div className='grid grid-cols-10'>
      <div className='col-span-1 h-screen items-center'>
        <div>
          <button
            ref={prevBtn}
            className={'arrow-icon -rotate-90'}
            onClick={() => handleClick(-1)}
          ></button>
        </div>

        <div
          id='listImage'
          className=' flex-1 grid-cols-1 gap-4 md:grid-cols-1 lg:grid-cols-1'
        >
          {itemsToDisplay.map((item, index) => {
            return (
              <Image
                key={index}
                src={
                  item !== "Can't read input file!"
                    ? 'data:image/png;base64,' + item
                    : ''
                }
                alt={index}
                width={640}
                height={640}
              ></Image>
            );
          })}
        </div>

        <div>
          <button
            ref={nextBtn}
            className={'arrow-icon rotate-90'}
            onClick={() => handleClick(1)}
          ></button>
        </div>
      </div>
      <Image
        className='col-span-9'
        src={
          defaultImageUrl !== "Can't read input file!"
            ? 'data:image/png;base64,' + defaultImageUrl
            : ''
        }
        alt={name}
        width={1000}
        height={1000}
      ></Image>
    </div>
  );
};

export default ImageContainer;
