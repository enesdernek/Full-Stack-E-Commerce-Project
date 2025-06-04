import React, { useEffect, useState } from 'react'
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import { useDispatch, useSelector } from 'react-redux';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { useNavigate, useParams } from 'react-router-dom';
import { clearFilteredProducts, filterProductsByPriceASC, filterProductsByPriceDESC } from '../redux/slices/productSlice';
import { CircularProgress, Grid } from '@mui/material';
import Product from './Product';
import { getFavoritedProducts } from '../redux/slices/userSlice';
import { setProductListBarHeader } from '../redux/slices/appSlice';

function ProductListFavorited() {

    const favoritedProducts = useSelector((state) => state.user.favoritedProducts)
    const dispatch = useDispatch()
    const [pageNo, setPageNo] = useState(1);
    const loading = useSelector((state) => state.product.loading)
    const [hasMore, setHasMore] = useState(true);
    const navigate = useNavigate()
    const token = useSelector((state)=>state.user.token)
    const productListBarHeader = useSelector((state)=>state.app.productListBarHeader)


    useEffect(() => {
        const fetchProducts = async () => {
            try {
                let newProducts = [];

                    newProducts = await dispatch(getFavoritedProducts({ token,pageNo })).unwrap();
                
                if (newProducts.length < 12) {
                    setHasMore(false);
                }
            } catch (error) {
                console.error("Ürünler yüklenirken hata oluştu", error);
            }
        };

        fetchProducts();
    }, [pageNo, dispatch]);


    useEffect(() => {
        dispatch(setProductListBarHeader("Beğendiğim Ürünler"))
        setHasMore(true);
        setPageNo(1);
    }, [navigate]);

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
    }, [hasMore, loading, pageNo]);




    return (
        <Grid sx={{ marginTop: "20px" }} container spacing={3} alignItems="stretch">
            {favoritedProducts && favoritedProducts.map((favoritedProduct) => (
                <Grid item size={{ xs: 12, sm: 6, md: 4, lg: 4 }} key={favoritedProduct.productId}>
                    <Product product={favoritedProduct} />
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
                    <h4 style={{ marginLeft: "20px" }}>Tüm ürünler yüklendi.</h4>
                </Grid>
            )}
        </Grid>


    )
}

export default ProductListFavorited    