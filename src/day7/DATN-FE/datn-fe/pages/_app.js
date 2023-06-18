import '@/styles/globals.css';
import Layout from '@/components/Layout';
import '@/configs/axios.config';
import ScrollView from '@/components/ScrollView';
import { Provider } from 'react-redux';
import { store } from './../redux/store';

export default function App({ Component, pageProps }) {
  return (
    <>
      {/* <Layout */}
      {/* title='home' */}
      <Provider store={store}>
        <Component {...pageProps} />
      </Provider>
      {/* > */}
      {/* </Layout> */}
    </>
  );
}
