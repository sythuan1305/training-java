import Layout from '@/components/Layout';
import ProductItem from '@/components/ProductItem';
import ScrollView from '@/components/ScrollView';
import { ProductList } from '@/service/productServices';
import React, { StrictMode } from 'react';

const list = (props) => {
  console.log(props);

  return (
    <Layout title='home'>
      <div>
        {props.data.map((item) => (
          <div key={item.name}>
            <p className='flex items-center justify-between  bg-teal-500 p-6 rounded-md h-3'>{item.name}</p>
            <ScrollView items={item.productEntities} ItemComponent={ProductItem}>
            </ScrollView>
          </div>
        ))}
      </div>
    </Layout>
  );
};

export async function getServerSideProps(context) {
  const { page, size } = context.query;
  // Fetch data from external API
  const res = await ProductList({ page, size });
  const data = res.data;
  console.log('data', data);

  // Pass data to the page via props
  return { props: { data } };
}

export default list;
