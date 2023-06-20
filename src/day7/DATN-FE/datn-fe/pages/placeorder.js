import { useRouter } from 'next/router';
import React from 'react'
import { useSelector } from 'react-redux';

const placeOrder = props => {
    const router = useRouter();

    const paymentMethod = useSelector((state) => state.cart.paymentMethod);

    console.log('paymentMethod', paymentMethod);

  return (
    <div>placeOrder</div>
  )
}


export default placeOrder