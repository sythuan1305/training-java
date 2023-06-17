import '@/styles/globals.css'
import Layout from "@/components/Layout";
import '@/configs/axios.config';
import ScrollView from '@/components/ScrollView';


export default function App({Component, pageProps}) {
    return <>
        {/* <Layout */}
            {/* title='home' */}
            <Component {...pageProps} />
        {/* > */}
        {/* </Layout> */}
    </>
}
