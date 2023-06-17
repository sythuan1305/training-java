import React from 'react'
import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox-next'
import {PaletteTree} from './palette'
import Home from "@/pages";
import Document from "@/pages/_document";
import App from "@/pages/_app";
import Layout from "@/components/Layout";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/Home">
                <Home/>
            </ComponentPreview>
            <ComponentPreview path="/Document">
                <Document/>
            </ComponentPreview>
            <ComponentPreview path="/App">
                <App/>
            </ComponentPreview>
            <ComponentPreview path="/Layout">
                <Layout/>
            </ComponentPreview>
        </Previews>
    )
}

export default ComponentPreviews