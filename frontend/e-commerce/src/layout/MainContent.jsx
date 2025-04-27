import { Box, Container} from '@mui/material'
import React from 'react'
import CategoryList from '../components/CategoryList'
import ProductList from '../components/ProductList'
import Grid from '@mui/material/Grid';

export function MainContent() {
  return (
    <Container maxWidth="xl" sx={{marginTop:"20px"}}>
        <Grid container>
          <Grid size={2}>
            <CategoryList/>
          </Grid>
          <Grid size={10}>
            <ProductList/>
          </Grid>
        
         
        </Grid>
    

    </Container>
  )
}

export default MainContent