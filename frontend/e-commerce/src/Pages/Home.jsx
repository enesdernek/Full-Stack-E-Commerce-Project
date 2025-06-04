import React from 'react'
import Header from '../layout/Header'
import MainContent from '../layout/MainContent'
import { Grid } from '@mui/material'
import { Routes, Route } from "react-router-dom"
import Register from './Register'
import Login from './Login'
import Profile from './Profile'
import Product from '../components/Product'
import ProductPage from './ProductPage'
import ProductListFilteredByCategory from '../components/ProductListFilteredByCategory'
import Cart from './Cart'




export function Home() {
   return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<MainContent />} />
        <Route path="/products" element={<MainContent />} />
        <Route path="/products/:categoryId" element={<MainContent />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/product/:productId" element={<ProductPage />} />
        <Route path="/product/filtered-by-price/:filter" element={<MainContent />} />
        <Route path="/product/favorited-products" element={<MainContent />} />
        <Route path="/product/campaign-products" element={<MainContent />} />
        <Route path="/cart" element={<Cart />} />
      </Routes>
    </>
  );
}

export default Home