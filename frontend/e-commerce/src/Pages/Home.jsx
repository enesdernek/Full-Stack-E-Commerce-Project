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
import Order from './Order'
import OrderPage from './OrderPage'
import OrderListPage from './OrderListPage'




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
        <Route path="/product/keyword/:keyword" element={<MainContent />} />
        <Route path="/order" element={<Order />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/order" element={<Order />} />
        <Route path="/orders" element={<OrderListPage />} />
        <Route path="/order/:orderId" element={<OrderPage />} />         
      </Routes>
    </>
  );
}

export default Home