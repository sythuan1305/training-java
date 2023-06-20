import '@/styles/globals.css';
import Layout from '@/components/Layout';
import '@/configs/axios.config';
import ScrollView from '@/components/ScrollView';
import { Provider } from 'react-redux';
import { store } from './../redux/store';
import { useEffect, useState } from 'react';
import { SessionProvider, useSession } from 'next-auth/react';
import { useRouter } from 'next/router';

export default function App({
  Component,
  pageProps: { session, ...pageProps },
}) {
  const [mounted, setMounted] = useState(false);
  useEffect(() => {
    setMounted(true);
  }, []);
  return (
    mounted && (
      <>
        {/* <Layout */}
        {/* title='home' */}
        <SessionProvider session={session}>
          <Provider store={store}>
            {Component.auth ? (
              <Auth>
                <Component {...pageProps} />
              </Auth>
            ) : (
              <Component {...pageProps} />
            )}
          </Provider>
        </SessionProvider>
        {/* > */}
        {/* </Layout> */}
      </>
    )
  );
}

function Auth({ children }) {
  const router = useRouter();
  const { status } = useSession({
    required: true,
    onUnauthenticated() {
      router.push('/unauthorized?message=login required');
    },
  });
  if (status === 'loading') {
    return <div>Loading...</div>;
  }

  return children;
}
