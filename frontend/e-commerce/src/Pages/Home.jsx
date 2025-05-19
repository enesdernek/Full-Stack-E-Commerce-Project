import React from 'react'
import Header from '../layout/Header'
import MainContent from '../layout/MainContent'
import { Grid } from '@mui/material'
import { Routes, Route } from "react-router-dom"
import Register from './Register'
import Login from './Login'




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
      </Routes>
    </>
  );
}

export default Home