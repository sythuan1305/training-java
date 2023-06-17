import Image from 'next/image';
import Link from 'next/link';
import React from 'react';

export default function ProductItem({ product, addToCartHandler, className }) {
  console.log('product', product);
  console.log('className', className);
  return (
    <div className='card'>
      <Link href={`/product/${product.id}`}>
        <Image
          src={
            product.defaultImageUrl !== "Can't read input file!"
              ? 'data:image/png;base64,' + product.defaultImageUrl
              : ''
          }
          width={100}
          height={100}
          alt={product.name}
          className='h-64 w-full rounded object-cover shadow'
        />
      </Link>
      <div className='flex flex-col items-center justify-center p-5'>
        <Link href={`/product/${product.id}`}>
          <h2 className='text-lg'>{product.name}</h2>
        </Link>
        <p className='mb-2'>{product.brand}</p>
        <p>${product.price}</p>
        <button
          className='primary-button'
          type='button'
          onClick={() => addToCartHandler(product)}
        >
          Add to cart
        </button>
      </div>
    </div>
  );
}
