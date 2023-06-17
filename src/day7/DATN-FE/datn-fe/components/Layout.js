import React from 'react';
import Head from "next/head";
import Link from "next/link";

function Layout({title, children}) {
    return (
        <>
            <Head>
                <title> {title ? title + '- DATN' : 'DATN'}</title>
                <meta name="description" content="Learn how to build a personal website using Next.js"/>
                <link rel="icon" href="/favicon.ico"/>
            </Head>
            <div className={'flex min-h-screen flex-col justify-between'}>
                <header>
                    <nav className={'flex h-12 px-4 justify-between shadow-md'}>
                        <Link className={'text-lg font-bold'} href={'/'}> DATN
                        </Link>
                        <div>
                            <Link className={'px-2'} href={'/cart'}> Cart
                            </Link>
                            <Link className={'px-2'} href={'/login'}> Login
                            </Link>
                        </div>
                    </nav>
                </header>
                <main className={'container m-auto mt-4 px-4'}>
                    {children}
                </main>
                <footer className={'flex h-10 justify-center items-center shadow-inner'}>
                    <p>Copyright @2023 DATN</p>
                </footer>
            </div>
        </>
    );
}

export default Layout;