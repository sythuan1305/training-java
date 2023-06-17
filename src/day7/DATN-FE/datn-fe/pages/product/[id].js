import Layout from '@/components/Layout';
import Link from 'next/link';
import React, { useEffect, useRef, useState } from 'react';
import Image from 'next/image';
import { useRouter } from 'next/router';
import { ProductDetail } from '@/service/productServices';
import ImageContainer from '@/components/ImageContainer';

const ProductDetailScreen = (props) => {
  console.log('props', props);
  const { data: productDetail } = props;

  // const { query } = useRouter();
  // const { id } = query;
  // const product = useRef({});
  // const [productDetail, setProductDetail] = useState({});
  // useEffect(() => {
  //   async function fetchData() {
  //     if (id === undefined) return;
  //     const res = await ProductDetail({ id });
  //     console.log('res', res);
  //     setProductDetail(res.data);
  //   }
  //   fetchData();
  // }, [id]);


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
              <div>{productDetail.countInStock > 0 ? 'In stock' : 'Unavailable'}</div>
            </div>
            <button className='primary-button w-full'>Add to cart</button>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export async function getServerSideProps({ params }) {
  const { id } = params;
  console.log('id', id);
  const res = await ProductDetail({ id });
  const data = res.data;
  return {
    props: {
      data,
    },
  };
}

export default ProductDetailScreen;
