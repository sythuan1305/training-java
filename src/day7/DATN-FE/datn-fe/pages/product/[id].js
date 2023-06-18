import Layout from '@/components/Layout';
import Link from 'next/link';
import React, { useCallback, useEffect, useRef, useState } from 'react';
import Image from 'next/image';
import { useRouter } from 'next/router';
import { ProductDetail } from '@/service/productServices';
import ImageContainer from '@/components/ImageContainer';
import { useDispatch, useSelector } from 'react-redux';
import { addCartItem } from '@/redux/slices/cartSlice';

const ProductDetailScreen = (props) => {
  const { data: productDetail } = props;

  const [quantity, setQuantity] = useState(1);

  const dispatch = useDispatch();

  const buttonRef = useRef(null);

  const router = useRouter();


  const addToCartHandler = useCallback(() => {
    dispatch(
      addCartItem({
        newItem: {
          id: Date.now(),
          product_id: productDetail.id,
          name: productDetail.name,
          price: productDetail.price * quantity,
          quantity,
          stock: productDetail.quantity,
        },
      })
    );
    router.push('/cart');
    // const existItem = cartItems.find((item) => item.id === productDetail.id);
  }, [productDetail, dispatch, quantity, router]);

  useEffect(() => {
    const isDisable = quantity === 0 || quantity > productDetail.quantity;
    buttonRef.current.disabled = isDisable;

    buttonRef.current.className = isDisable ? '' : 'primary-button w-full';
  }, [quantity, productDetail.quantity]);

  if (!productDetail) {
    return <div>Produt Not Found</div>;
  }

  return (
    <Layout title={productDetail.name}>
      <div className='py-2'>
        <Link href='/product/list'>back to products</Link>
      </div>

      <div className='grid md:grid-cols-4 md:gap-3'>
        <div className='md:col-span-2'>
          {/* <Image
            src={productDetail.defaultImageUrl !== "Can't read input file!" ? productDetail.defaultImageUrl : ""}
            alt={productDetail.name}
            width={640}
            height={640}
            layout='responsive'
          ></Image> */}
          <ImageContainer
            defaultImageUrl={productDetail.defaultImageUrl}
            name={productDetail.name}
            items={productDetail.images}
          />
        </div>

        <div>
          <ul>
            <li>
              <h1 className='text-lg'>{productDetail.name}</h1>
            </li>
            <li>Category: {productDetail.categoryName}</li>
            {/* <li>Brand: {productDetail.brand}</li> */}
            {/* <li>
              {productDetail.rating} of {productDetail.numReviews} reviews
            </li> */}
            <li>Description: {productDetail.description}</li>
            <li>
              <input
                type='number'
                value={quantity}
                onChange={(e) => {
                  setQuantity(e.target.valueAsNumber);
                }}
                // pattern='^-?[0-9]\d*\.?\d*$'
                min={1}
                max={productDetail.quantity}
              />
            </li>
          </ul>
        </div>

        <div>
          <div className='card p-5'>
            <div className='mb-2 flex justify-between'>
              <div>Price</div>
              <div>${productDetail.price}</div>
            </div>
            <div className='mb-2 flex justify-between'>
              <div>Status</div>
              <div>
                {productDetail.quantity > 0 ? 'In stock' : 'Unavailable'}
              </div>
            </div>
            <button
              onClick={addToCartHandler}
              ref={buttonRef}
              className='primary-button w-full'
            >
              Add to cart
            </button>
          </div>
        </div>
        
      </div>
    </Layout>
  );
};

export async function getServerSideProps({ params }) {
  const { id } = params;
  const res = await ProductDetail({ id });
  const data = res.data;
  return {
    props: {
      data,
    },
  };
}

export default ProductDetailScreen;
