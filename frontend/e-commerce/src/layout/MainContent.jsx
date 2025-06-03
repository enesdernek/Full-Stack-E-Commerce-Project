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
import ProductListFilteredByPrice from '../components/ProductListFilteredByPrice';
import CampaignList from '../components/CampaignList';

export function MainContent() {
  const { categoryId, filter } = useParams();

  return (
    <Container maxWidth="xl" sx={{ marginTop: "20px" }}>
      <Grid container>
        <Grid item size={{xs:12,sm:12,md:2,lg:2}}>
          <CategoryList />
          <CampaignList/>
        </Grid>

        <Grid item size={{xs:12,sm:12,md:10,lg:10}}>
          <ProductListBar />

          {filter === "Artan" || filter === "Azalan" ? (
            <ProductListFilteredByPrice key= {filter} filter={filter} />
          ) : categoryId ? (
            <ProductListFilteredByCategory categoryId={categoryId} />
          ) : (
            <ProductList />
          )}
        </Grid>
      </Grid>
    </Container>
  );
}

export default MainContent