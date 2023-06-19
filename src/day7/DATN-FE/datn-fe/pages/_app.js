import '@/styles/globals.css';
import Layout from '@/components/Layout';
import '@/configs/axios.config';
import ScrollView from '@/components/ScrollView';
import { Provider } from 'react-redux';
import { store } from './../redux/store';
import { useEffect, useState } from 'react';
import { SessionProvider } from 'next-auth/react';


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
          <Component {...pageProps} />
        </Provider>
        </SessionProvider>
        {/* > */}
        {/* </Layout> */}
      </>
    )
  );
}
