import { Box, Container } from '@mui/material'
import React, { useState } from 'react'
import CategoryList from '../components/CategoryList'
import ProductList from '../components/ProductList'
import Grid from '@mui/material/Grid';
import { Route, Routes } from 'react-router-dom';
import ProductListFilteredByCategory from '../components/ProductListFilteredByCategory';

export function MainContent() {



  return (
    <Container maxWidth="xl" sx={{ marginTop: "20px" }}>
      <Grid container>
        <Grid size={2}>
          <CategoryList />
        </Grid>
        <Grid size={10}>
          <Routes>
          <Route path="/products" element={<ProductList />} />
          <Route path="/" element={<ProductList />} />
          <Route path="/products/:categoryId" element={<ProductListFilteredByCategory />} />
          </Routes>
        </Grid>


      </Grid>


    </Container>
  )
}

export default MainContent