import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getAllProducts } from '../redux/slices/productSlice'
import Product from './Product'
import Grid from '@mui/material/Grid';

function ProductList() {

  const products = useSelector((state) => state.product.products)
  const dispatch = useDispatch()

  useEffect(() => {
    dispatch(getAllProducts())
  }, [])


  return (
    <Grid container spacing={1} alignItems="stretch"
     
    >
      {products && products.map((product) => (
        <Grid xs={12} sm={6} md={4} lg={3} key={product.productId}>
          <Product product={product} />
        </Grid>
      ))}
    </Grid>

  )
}

export default ProductList
