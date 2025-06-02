import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { filterProductsByCategoryId, getAllProducts } from '../redux/slices/productSlice'
import Product from './Product'
import Grid from '@mui/material/Grid';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import { useNavigate, useParams } from 'react-router-dom';
import { setProductListBarHeader } from '../redux/slices/appSlice';
import { getCategoryById } from '../redux/slices/categorySlice';

function ProductListFilteredByCategory() {

    const categorizedProducts = useSelector((state) => state.product.categorizedProducts)
    const category = useSelector((state)=>state.category.category)
    const dispatch = useDispatch()
    const [pageNo, setPageNo] = useState(1);
    const loading = useSelector((state) => state.product.loading)
    const [hasMore, setHasMore] = useState(true);
    const navigate = useNavigate()
    const {categoryId} = useParams()
  
  
    useEffect(() => {
      const fetchProducts = async () => {
        try {
          const newProducts = await dispatch(filterProductsByCategoryId({categoryId,pageNo})).unwrap();
          if (newProducts.length < 12) {
            setHasMore(false);
          }
        } catch (error) {
          console.error("Ürünler yüklenirken hata oluştu", error);
        }
      };
  
  
      fetchProducts();
    }, [pageNo, dispatch,categoryId]);

    useEffect(()=>{
      dispatch(getCategoryById(categoryId))
    },[navigate])

    useEffect(()=>{
           dispatch(setProductListBarHeader(category.name))   
    },[category])

    useEffect(() => {
      setPageNo(1);       
      setHasMore(true);
    }, [categoryId]);
  
    useEffect(() => {
      const handleScroll = () => {
        if (
          window.innerHeight + window.scrollY >= document.documentElement.scrollHeight - 100
        ) {
          if (hasMore && !loading) {
            setPageNo((prevPageNo) => prevPageNo + 1);
          }
        }
      };
  
      window.addEventListener('scroll', handleScroll);
      return () => window.removeEventListener('scroll', handleScroll);
    }, [hasMore, loading]);
  
    return (
      <Grid sx={{marginTop:"20px"}} container spacing={3} alignItems="stretch">
        {categorizedProducts && categorizedProducts.map((categorizedProduct) => (
          <Grid item size={{xs:12,sm:6,md:4,lg:4}} key={categorizedProduct.productId}>
            <Product product={categorizedProduct} />
          </Grid>
        ))}
  
        {loading && (
          <Grid item xs={12} style={{ marginTop: "20px" }}>
            <Box
              sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                width: '100%',
                minHeight: "100px",
              }}
            >
              <CircularProgress />
            </Box>
          </Grid>
        )}
  
        {!hasMore && (
          <Grid item xs={12} style={{ textAlign: "center", marginTop: "20px" }}>
            <h4 style={{marginLeft:"20px"}}>Tüm ürünler yüklendi.</h4>
          </Grid>
        )}
      </Grid>
  
  
    )
}

export default ProductListFilteredByCategory
