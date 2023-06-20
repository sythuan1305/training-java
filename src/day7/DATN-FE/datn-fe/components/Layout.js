import React, { useMemo } from 'react';
import Head from 'next/head';
import Link from 'next/link';
import { useDispatch, useSelector } from 'react-redux';
import { signOut, useSession } from 'next-auth/react';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { cartReset } from '@/redux/slices/cartSlice';
import { Menu } from '@headlessui/react';

function Layout({ title, children }) {
  const { status, data: session } = useSession();
  const cartItems = useSelector((state) => Object.values(state.cart.cartItems));

  const dispatch = useDispatch();

  const cartItemCount = useMemo(() => {
    return cartItems.reduce((a, c) => a + c.quantity, 0);
  }, [cartItems]);

  const logoutClickHandler = () => {
    dispatch(cartReset());
    signOut();
  };
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
      <ToastContainer position='bottom-center' limit={1} />
      <div className={'flex min-h-screen flex-col justify-between'}>
        <header>
          <nav className={'flex h-12 justify-between px-4 shadow-md'}>
            <Link className={'text-lg font-bold'} href={'/produt/list'}>
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
                <Menu as='div' className='relative inline-block'>
                  <Menu.Button className='text-blue-600'>
                    {session.user.username}
                  </Menu.Button>
                  <Menu.Items className='absolute right-0 w-56 origin-top-right bg-white  shadow-lg '>
                    <Menu.Item>
                      <Link className='dropdown-link' href='/profile'>
                        Profile
                      </Link>
                    </Menu.Item>
                    <Menu.Item>
                      <Link className='dropdown-link' href='/order-history'>
                        Order History
                      </Link>
                    </Menu.Item>
                    <Menu.Item>
                      <Link
                        className='dropdown-link'
                        href='#'
                        onClick={logoutClickHandler}
                      >
                        Logout
                      </Link>
                    </Menu.Item>
                  </Menu.Items>
                </Menu>
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
