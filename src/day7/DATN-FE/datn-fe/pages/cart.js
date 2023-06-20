import React, { useCallback, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useRouter } from 'next/router';
import Layout from '@/components/Layout';
import Link from 'next/link';
import Image from 'next/image';
import { XCircleIcon } from '@heroicons/react/24/solid';
import { addCartItem, removeCartItem, setCartItem } from '@/redux/slices/cartSlice';
import dynamic from 'next/dynamic';
import { InformationCart } from '@/service/cartServices';
import Cookies from 'cookies';
import { toast } from 'react-toastify';
import { ProductDetail } from '@/service/productServices';

const CartScreen = (props) => {
  const productDetails = props.data;
  console.log('productDetail', productDetails[0]);
  const cartItems = useSelector((state) => Object.values(state.cart.cartItems));

  const [itemsBough, setItemsBough] = useState([]);
  const dispatch = useDispatch();
  const router = useRouter();

  const removeItemHandler = useCallback(
    (id) => {
      dispatch(removeCartItem({ id }));
    },
    [dispatch]
  );

  const updateCartHandler = useCallback(
    async (item, quantity) => {
      console.log('item', item);
      const { data } = await ProductDetail({ id: item.product_id });
      if (data.quantity < quantity) {
        return toast.error('Sorry. Product is out of stock');
      }

      dispatch(setCartItem({ existingItem: { ...item, quantity } }));
      return toast.success('Product updated in the cart');
    },
    [dispatch]
  );

  return (
    <Layout title='Shopping Cart'>
      <h1 className='mb-4 text-xl'>Shopping Cart</h1>
      {cartItems.length === 0 ? (
        <div>
          Cart is empty. <Link href='/product/list'>Go shopping</Link>
        </div>
      ) : (
        <div className='grid md:grid-cols-4 md:gap-5'>
          <div className='overflow-x-auto md:col-span-3'>
            <table className='min-w-full '>
              <thead className='border-b'>
                <tr>
                  <th className='p-5 text-left'>Item</th>
                  <th className='p-5 text-right'>Quantity</th>
                  <th className='p-5 text-right'>Price</th>
                  <th className='p-5'>Action</th>
                </tr>
              </thead>
              <tbody>
                {cartItems.map((item, index) => (
                  <tr key={item.id} className='border-b'>
                    <td>
                      {/* check box */}
                      <div className='flex items-center'>
                        <input
                          type='checkbox'
                          className='mr-2 h-5 w-5'
                          // checked={true}
                          onChange={(e) => {
                            if (e.target.checked)
                              setItemsBough([...itemsBough, item]);
                            else {
                              const newItemsBough = itemsBough.filter(
                                (i) => i.id !== item.id
                              );
                              setItemsBough(newItemsBough);
                            }
                          }}
                        />
                      </div>
                    </td>
                    <td>
                      <Link
                        className='flex items-center'
                        href={`/product/${item.product_id}`}
                      >
                        <Image
                          src={
                            productDetails[index]?.product?.defaultImageUrl !==
                            "Can't read input file!"
                              ? 'data:image/png;base64,' +
                                productDetails[index]?.product?.defaultImageUrl
                              : ''
                          }
                          alt={item.name}
                          width={50}
                          height={50}
                        ></Image>
                        &nbsp;
                        {item.name}
                      </Link>
                    </td>
                    <td className='p-5 text-right'>
                      <select
                        value={item.quantity}
                        onChange={(e) =>
                          updateCartHandler(item, e.target.value)
                        }
                      >
                        {[...Array(item.stock).keys()].map((x) => (
                          <option key={x + 1} value={x + 1}>
                            {x + 1}
                          </option>
                        ))}
                      </select>
                    </td>
                    <td className='p-5 text-right'>${item.price}</td>
                    <td className='p-5 text-center'>
                      <button
                        onClick={() => removeItemHandler(item.product_id)}
                      >
                        <XCircleIcon className='h-5 w-5'></XCircleIcon>
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <div className='card p-5'>
            <ul>
              <li>
                <div className='pb-3 text-xl'>
                  Subtotal ({cartItems.reduce((a, c) => a + c.quantity, 0)}) : $
                  {cartItems.reduce((a, c) => a + c.quantity * c.price, 0)}
                </div>
              </li>
              <li>
                <button
                  onClick={() => router.push('/shipping')}
                  className='primary-button w-full'
                >
                  Check Out
                </button>
              </li>
            </ul>
          </div>
        </div>
      )}
    </Layout>
  );
};

export async function getServerSideProps({ req, res }) {
  const cookies = new Cookies(req, res);
  const result = await InformationCart({ cart: cookies.get('cart') });
  console.log('result', result);
  return { props: { data: result.data } };
}

export default dynamic(() => Promise.resolve(CartScreen), { ssr: false });
