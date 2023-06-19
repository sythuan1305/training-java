import React, { useMemo } from 'react';
import Head from 'next/head';
import Link from 'next/link';
import { useSelector } from 'react-redux';
import { useSession } from 'next-auth/react';
import { ToastContainer } from 'react-toastify';

function Layout({ title, children }) {
  const { status, data: session } = useSession();
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
        <ToastContainer position='bottom-center' limit={1} />
      </Head>
      <div className={'flex min-h-screen flex-col justify-between'}>
        <header>
          <nav className={'flex h-12 justify-between px-4 shadow-md'}>
            <Link className={'text-lg font-bold'} href={'/'}>
              {' '}
              DATN
            </Link>
            <div>
              <Link className={'p-2 px-2'} href={'/cart'}>
                Cart
                {cartItemCount > 0 && (
                  <p className='ml-1 inline-block rounded-full bg-red-600 px-2 py-1 text-xs font-bold text-white'>
                    {cartItemCount}
                  </p>
                )}
              </Link>
              {status === 'loading' ? (
                'Loading'
              ) : session?.user ? (
                session.user.name
              ) : (
                <Link href='/login' className='p-2'>
                  Login
                </Link>
              )}
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
