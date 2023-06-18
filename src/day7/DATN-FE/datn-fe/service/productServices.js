import axios from 'axios';

export async function ProductList({ page = 0, size = 4 }) {
  const res = await axios.post('/user/product/list', { page, size });
  //   console.log(res.data);
  return res.data;
}

export async function ProductDetail(params) {
  const res = await axios.post(`/user/product/information?id=${params.id}`);
  return res.data;
}
