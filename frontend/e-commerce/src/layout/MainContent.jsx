import { Box, Container} from '@mui/material'
import React, { useState } from 'react'
import CategoryList from '../components/CategoryList'
import ProductList from '../components/ProductList'
import Grid from '@mui/material/Grid';

export function MainContent() {

  const [selectedCategoryId, setSelectedCategoryId] = useState(null);


  return (
    <Container maxWidth="xl" sx={{marginTop:"20px"}}>
        <Grid container>
          <Grid size={2}>
            <CategoryList onCategorySelect={setSelectedCategoryId}/>
          </Grid>
          <Grid size={10}>
            <ProductList categoryId={selectedCategoryId}/>
          </Grid>
        
         
        </Grid>
    

    </Container>
  )
}

export default MainContent