import process from "next/dist/build/webpack/loaders/resolve-url-loader/lib/postcss";

export const AppConfig = {
    API_URL: process.env.NEXT_PUBLIC_API_BASE
}