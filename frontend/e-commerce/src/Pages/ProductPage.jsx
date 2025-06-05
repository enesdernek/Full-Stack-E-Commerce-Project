import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import { getProductByProductId } from '../redux/slices/productSlice'
import { Box, Button, Container, Grid, IconButton, Rating, Stack, Typography } from '@mui/material'
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import { addProductToFavoritedListByProductId, getUser, removeProductFromFavoritedListByProductId } from '../redux/slices/userSlice'
import { addProductToCartByProductId } from '../redux/slices/cartSlice'
import { openSnackbar } from '../redux/slices/appSlice'

function ProductPage() {

    const { productId } = useParams()
    const dispatch = useDispatch()
    const product = useSelector((state) => state.product.product)
    const user = useSelector((state) => state.user.user)
    const token = useSelector((state) => state.user.token)

    useEffect(() => {
        dispatch(getProductByProductId(productId))
    }, [productId])

    const addProductToFavoritedList = async (productId) => {
        await dispatch(addProductToFavoritedListByProductId({ token, productId }));
        await dispatch(getUser(token));
        dispatch(openSnackbar({ message: "Ürün favorilerim listesine eklendi.", severity: "success" }))
    };

    const removeProductFromFavoritedList = async (productId) => {
        await dispatch(removeProductFromFavoritedListByProductId({ token, productId }));
        await dispatch(getUser(token));
        dispatch(openSnackbar({ message: "Ürün favorilerim listesinden silindi", severity: "warning" }))

    };

    const addProductToCart = () => {
        dispatch(addProductToCartByProductId({ productId, token }))
        dispatch(openSnackbar({ message: "Ürün sepete eklendi!", severity: "success" }));
    }



    return (
        <Container sx={{ marginTop: "20px" }}>
            {
                product &&
                <Grid container spacing={2}>
                    <Grid size={{ xs: 12, md: 6 }}>
                        <Box sx={{ position: "relative" }}>
                            <img src={"/src" + product.imagePath}
                                alt={product.name || "Ürün görseli"}
                                style={{
                                    height: "100%",
                                    width: "100%",
                                    borderRadius: "10px"
                                }}
                            />
                            <IconButton
                                sx={{
                                    position: "absolute",
                                    top: 10,
                                    right: 10,
                                    backgroundColor: "rgba(255, 255, 255, 0.7)",
                                    "&:hover": {
                                        backgroundColor: "rgba(255, 255, 255, 0.9)"
                                    }
                                }}
                                onClick={() => {
                                    if (user?.favoritedProductDtos?.some(fp => fp.productId === product.productId)) {
                                        removeProductFromFavoritedList(product.productId);
                                    } else {
                                        addProductToFavoritedList(product.productId);
                                    }
                                }}
                            >
                                {
                                    user?.favoritedProductDtos?.some(fp => fp.productId === product.productId)
                                        ? <FavoriteIcon sx={{ color: "red" }} />
                                        : <FavoriteBorderIcon sx={{ color: "red" }} />
                                }
                            </IconButton>
                        </Box>
                    </Grid>
                    <Grid size={{ xs: 12, md: 6 }}>
                        <Box>
                            <Box sx={{ display: "flex", justifyContent: "space-between" }}>
                                <Box>
                                    <Typography sx={{ color: "orange", marginLeft: "7px" }} variant="h4">{product.brand}</Typography>
                                    <Typography sx={{ marginLeft: "6px" }} variant="h4">{product.name}</Typography>
                                </Box>
                                <Box sx={{ display: "flex" }}>
                                    <Typography sx={{ marginTop: "8px", marginRight: "14px" }} variant='span'>Değerlendirme Sayısı: {product.ratingCount}</Typography>
                                    <Rating
                                        name="simple-controlled"
                                        value={product.rating}
                                        precision={0.5}
                                        sx={{
                                            fontSize: "30px"
                                        }}
                                        onChange={(event, newValue) => {
                                            setValue(newValue);
                                        }}
                                    />
                                </Box>

                            </Box>
                            <Typography sx={{ marginLeft: "8px", fontSize: "20px" }} variant="span">{product.description}</Typography>
                        </Box>
                        <Button onClick={() => addProductToCart()} sx={{ marginTop: "20px" }} variant='contained' color="warning">Sepete Ekle</Button>
                    </Grid>
                </Grid>
            }

        </Container>
    )
}

export default ProductPage