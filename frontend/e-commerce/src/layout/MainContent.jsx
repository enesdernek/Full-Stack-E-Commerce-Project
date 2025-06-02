import { Box, Container } from '@mui/material'
import React, { useState } from 'react'
import CategoryList from '../components/CategoryList'
import ProductList from '../components/ProductList'
import Grid from '@mui/material/Grid';
import { Route, Routes, useParams } from 'react-router-dom';
import ProductListFilteredByCategory from '../components/ProductListFilteredByCategory';
import Register from '../Pages/Register';
import Login from '../Pages/Login';
import ProductListBar from '../components/ProductListBar';

export function MainContent() {
  const { categoryId } = useParams();

  return (
    <Container maxWidth="xl" sx={{ marginTop: "20px" }}>
      <Grid container>
        <Grid size={2}>
          <CategoryList />
        </Grid>
        
        <Grid size={10}>
                <ProductListBar />

          {categoryId ? (
            <ProductListFilteredByCategory  categoryId={categoryId} />
          ) : (
            <ProductList />
          )}
        </Grid>
      </Grid>
    </Container>
  );
}

export default MainContent