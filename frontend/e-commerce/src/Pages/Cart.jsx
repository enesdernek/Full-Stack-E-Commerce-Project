import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { decreaseItemQuantityByCartItemId, getAllCartItems, increaseItemQuantityByCartItemId, removeItemFromCartByCartItemId } from '../redux/slices/cartSlice'
import { Alert, Box, Button, Container, Grid, IconButton, Typography } from '@mui/material'
import AddCircleIcon from '@mui/icons-material/AddCircle';
import RemoveCircleIcon from '@mui/icons-material/RemoveCircle';
import "../style/cart.css"
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { openSnackbar } from '../redux/slices/appSlice';
import { useNavigate } from 'react-router-dom';


function Cart() {

    const cartItems = useSelector((state) => state.cart.cartItems)
    const dispatch = useDispatch()
    const token = useSelector((state) => state.user.token)
    const user = useSelector((state) => state.user.user)
    const navigate = useNavigate()
    const cartTotalPrice = useSelector((state) => state.cart.cartTotalPrice)

    useEffect(() => {
        dispatch(getAllCartItems({ token }))
    }, [])

    const removeItemFromCart = (cartItemId) => {
        dispatch(removeItemFromCartByCartItemId({ token, cartItemId }))
        dispatch(openSnackbar({ message: "Ürün sepetten silindi.", severity: "warning" }))
    }

    const increaseItemQuantity = (cartItemId, quantity) => {
        dispatch(increaseItemQuantityByCartItemId({ token, cartItemId, quantity }))
    }

    const decreaseItemQuantity = (cartItemId, quantity) => {
        dispatch(decreaseItemQuantityByCartItemId({ token, cartItemId, quantity }))
    }

    return (
        <>
            {
                user ? <Container sx={{ marginTop: "20px" }
                } >
                    <Grid container >
                        <Grid sx={{ marginTop: "20px" }} item size={{ xs: 12, md: 12, lg: 12, sm: 12 }} >
                            {
                                cartItems && cartItems.length > 0 ? <Box sx={{ display: "flex", justifyContent: "space-between" }}>
                                    <Box sx={{ display: "flex" }}>
                                        <ShoppingCartIcon sx={{ marginTop: "2px" }} />
                                        <Typography sx={{ marginLeft: "5px" }} variant='h6'>Sepetteki Ürünler</Typography>
                                    </Box>
                                    <Box>
                                        <Typography variant='h5'>Toplam tutar: {cartTotalPrice} ₺</Typography>
                                        <Button onClick={() => navigate("/order")} color="warning" variant="contained">Siparişi Tamamla</Button>
                                    </Box>
                                </Box>
                                    : <Alert variant="filled" color="warning">Sepetinizde şuan ürün bulunmamaktadır.</Alert>

                            }

                        </Grid>

                        <Grid item size={{ xs: 12, md: 12, lg: 12, sm: 12 }}  >
                            {
                                cartItems && cartItems.map((cartItem) => (
                                    <Box key={cartItem.productDto.productId} sx={{ display: "flex", justifyContent: "space-between", marginTop: "20px", border: "1px solid black" }}>
                                        <Box sx={{ display: "flex" }}>
                                            <img src={"/src" + cartItem.productDto.imagePath}
                                                alt={cartItem.productDto.name || "Ürün görseli"}
                                                style={{
                                                    height: "100px",
                                                    width: "150px",
                                                }}
                                            />
                                            <Box sx={{ marginLeft: "10px", marginTop: "5px" }}>
                                                <Typography sx={{ color: "orange" }} variant='h5'>{cartItem.productDto.brand}</Typography>
                                                <Typography variant='h6'>{cartItem.productDto.name}</Typography>
                                                <Typography sx={{ color: "red" }} variant='h6'>{cartItem.productDto.price} ₺</Typography>
                                            </Box>
                                        </Box>

                                        <Box >
                                            <Box sx={{ display: "flex" }}>
                                                <Typography variant='h6' sx={{ marginLeft: "10px", marginTop: "5px" }}>Ürün miktarı: </Typography>
                                                <Typography variant='h6' sx={{ marginLeft: "10px", marginTop: "5px" }}>{cartItem.quantity}</Typography>
                                                <AddCircleIcon onClick={() => increaseItemQuantity(cartItem.cartItemId, cartItem.quantity)} className='cart-icon cart-icon-plus' sx={{ marginTop: "8px", marginLeft: "5px", color: "green" }} />
                                                <RemoveCircleIcon
                                                    onClick={() => cartItem.quantity > 1 && decreaseItemQuantity(cartItem.cartItemId, cartItem.quantity)}
                                                    className='cart-icon cart-icon-minus'
                                                    sx={{
                                                        marginTop: "8px",
                                                        marginLeft: "5px",
                                                        marginRight: "10px",
                                                        color: cartItem.quantity > 1 ? "red" : "gray",
                                                        cursor: cartItem.quantity > 1 ? "pointer" : "not-allowed",
                                                        opacity: cartItem.quantity > 1 ? 1 : 0.5
                                                    }}
                                                />                                    </Box>
                                            <Box sx={{ marginTop: "25px" }}>
                                                <Button onClick={() => removeItemFromCart(cartItem.cartItemId)} variant='contained' color="error">Ürünü Sepetten Çıkar</Button>
                                            </Box>
                                        </Box>



                                    </Box>
                                ))

                            }
                        </Grid>


                    </Grid>

                </Container > :
                    <Container sx={{ marginTop: "30px" }}>
                        <Alert variant='filled' color="warning">Sepet işlemleri için giriş yapınız!</Alert>
                        <Button variant='contained' color="success" sx={{ marginTop: "20px" }} onClick={() => navigate("/login")}>Giriş yap</Button>
                    </Container>
            }
        </>
    )
}

export default Cart