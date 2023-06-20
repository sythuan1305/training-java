import { useRouter } from 'next/router';
import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import CheckoutWizard from '../components/CheckoutWizard';
import Layout from '../components/Layout';
import { useDispatch, useSelector } from 'react-redux';
import { savePaymentMethod } from '@/redux/slices/cartSlice';

export default function PaymentScreen() {
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState('');

  //   const { state, dispatch } = useContext(Store);
  //   const { cart } = state;
  //   const { shippingAddress, paymentMethod } = cart;

  const shipping = useSelector((state) => state.cart.shipping);

  const dispatch = useDispatch();
  const router = useRouter();

  const submitHandler = (e) => {
    e.preventDefault();
    if (!selectedPaymentMethod) {
      return toast.error('Payment method is required');
    }
    dispatch(savePaymentMethod({ paymentMethod: selectedPaymentMethod }));

    router.push('/placeorder');
  };
  useEffect(() => {
    if (!shipping.address) {
      router.push('/shipping');
    }
    // setSelectedPaymentMethod(paymentMethod || '');
  }, [router, shipping.address]);

  return (
    <Layout title='Payment Method'>
      <CheckoutWizard activeStep={2} />
      <form className='mx-auto max-w-screen-md' onSubmit={submitHandler}>
        <h1 className='mb-4 text-xl'>Payment Method</h1>
        {['CashOnDelivery'].map((payment) => (
          <div key={payment} className='mb-4'>
            <input
              name='paymentMethod'
              className='p-2 outline-none focus:ring-0'
              id={payment}
              type='radio'
            //   checked={false}
              onche
              onChange={() => setSelectedPaymentMethod(payment)}
            />

            <label className='p-2' htmlFor={payment}>
              {payment}
            </label>
          </div>
        ))}
        <div className='mb-4 flex justify-between'>
          <button
            onClick={() => router.push('/shipping')}
            type='button'
            className='default-button'
          >
            Back
          </button>
          <button className='primary-button'>Next</button>
        </div>
      </form>
    </Layout>
  );
}
