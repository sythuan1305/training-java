import Layout from '@/components/Layout';
import ProductItem from '@/components/ProductItem';
import ScrollView from '@/components/ScrollView';
import { ProductList } from '@/service/productServices';
import React from 'react';


const list = (props) => {

  return (
    <Layout title='home'>
      <div>
        {props.data.map((item) => (
          <div key={item.name}>
            <p className='flex items-center justify-between  bg-teal-500 p-6 rounded-md h-3'>{item.name}</p>
            <ScrollView items={item.productEntities} ItemComponent={ProductItem} />
          </div>
        ))}
      </div>
    </Layout>
  );
};

export async function getServerSideProps(context) {
  const { page, size, req, res } = context.query;

  // Fetch data from external API
  const result = await ProductList({ page, size });
  const data = result.data;

  // Pass data to the page via props
  return { props: { data } };
}

export default list;
