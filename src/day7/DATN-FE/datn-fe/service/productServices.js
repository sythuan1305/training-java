import axios from 'axios';

export async function ProductList({ page = 0, size = 4 }) {
    console.log('params', { page, size });
  const res = await axios.post('/user/product/list', { page, size });
  //   console.log(res.data);
  return res.data;
}

export async function ProductDetail(params) {
    console.log('params', params);
  const res = await axios.post(`/user/product/information?id=${params.id}`);
  return res.data;
}
