import React, { useMemo } from 'react';
import Head from 'next/head';
import Link from 'next/link';
import { useSelector } from 'react-redux';

function Layout({ title, children }) {
  const cartItems = useSelector((state) => Object.values(state.cart.cartItems));


  const cartItemCount = useMemo(() => {
    return cartItems.reduce((a, c) => a + c.quantity, 0);
  }, [cartItems.length]);
  return (
    <>
      <Head>
        <title> {title ? title + '- DATN' : 'DATN'}</title>
        <meta
          name='description'
          content='Learn how to build a personal website using Next.js'
        />
        <link rel='icon' href='/favicon.ico' />
      </Head>
      <div className={'flex min-h-screen flex-col justify-between'}>
        <header>
          <nav className={'flex h-12 justify-between px-4 shadow-md'}>
            <Link className={'text-lg font-bold'} href={'/'}>
              {' '}
              DATN
            </Link>
            <div>
              <Link className={'px-2 p-2'} href={'/cart'}>
                  Cart
                  {cartItemCount > 0 && (
                    <p className='ml-1 rounded-full bg-red-600 px-2 py-1 text-xs font-bold text-white inline-block'>
                      {cartItemCount}
                    </p>
                  )}
              </Link>
              <Link className={'px-2'} href={'/login'}>
                Login
              </Link>
            </div>
          </nav>
        </header>
        <main className={'container m-auto mt-4 px-4'}>{children}</main>
        <footer
          className={'flex h-10 items-center justify-center shadow-inner'}
        >
          <p>Copyright @2023 DATN</p>
        </footer>
      </div>
    </>
  );
}

export default Layout;
