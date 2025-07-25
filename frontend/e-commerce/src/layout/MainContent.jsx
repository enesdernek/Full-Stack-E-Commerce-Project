import { Alert, Box, Container } from '@mui/material'
import React, { useState } from 'react'
import CategoryList from '../components/CategoryList'
import ProductList from '../components/ProductList'
import Grid from '@mui/material/Grid';
import { Route, Routes, useLocation, useParams } from 'react-router-dom';
import ProductListFilteredByCategory from '../components/ProductListFilteredByCategory';
import Register from '../Pages/Register';
import Login from '../Pages/Login';
import ProductListBar from '../components/ProductListBar';
import ProductListFilteredByPrice from '../components/ProductListFilteredByPrice';
import CampaignList from '../components/CampaignList';
import ProductListFavorited from '../components/ProductListFavorited';
import ProductListCampaign from '../components/ProductListCampaign';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import { useDispatch, useSelector } from 'react-redux';
import { closeSnackbar } from '../redux/slices/appSlice';
import ProductListFilteredBySearch from '../components/ProductListFilteredBySearch';


export function MainContent() {

  const { categoryId, keyword, filter } = useParams();
  const location = useLocation()
  const isFavoritedPageFavorited = location.pathname.includes("/products/favorited-products");
  const isFavoritedPageCampaign = location.pathname.includes("/products/campaign-products");


  return (
    <Container maxWidth="xl" sx={{ marginTop: "20px" }}>
      <Grid container>
        <Grid item size={{ xs: 12, sm: 12, md: 2, lg: 2 }}>
          <CategoryList />
          <CampaignList />
        </Grid>

        <Grid item size={{ xs: 12, sm: 12, md: 10, lg: 10 }}>
          <ProductListBar />

          {isFavoritedPageFavorited ? (
            <ProductListFavorited />
          ) : isFavoritedPageCampaign ? (
            <ProductListCampaign />
          ) : keyword ? (
            <ProductListFilteredBySearch keyword={keyword} />
          ) : filter === "Artan" || filter === "Azalan" ? (
            <ProductListFilteredByPrice key={filter} filter={filter} />
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