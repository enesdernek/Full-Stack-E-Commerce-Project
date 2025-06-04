import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getAllCartItems } from '../redux/slices/cartSlice'
import { Box, Button, Container, Grid, IconButton, Typography } from '@mui/material'
import AddCircleIcon from '@mui/icons-material/AddCircle';
import RemoveCircleIcon from '@mui/icons-material/RemoveCircle';
import "../style/cart.css"
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';

function Cart() {

    const cartItems = useSelector((state) => state.cart.cartItems)
    const dispatch = useDispatch()
    const token = useSelector((state) => state.user.token)

    useEffect(() => {
        dispatch(getAllCartItems({ token }))
    }, [])



    return (
        <Container sx={{ marginTop: "20px" }}>
            <Grid container >
                <Grid sx={{ marginTop: "20px" }} item size={{ xs: 12, md: 12, lg: 12, sm: 12 }} >
                    <Box sx={{ display: "flex", justifyContent: "space-between" }}>
                        <Box sx={{display:"flex"}}>
                            <ShoppingCartIcon sx={{ marginTop: "2px" }} />
                            <Typography sx={{ marginLeft: "5px" }} variant='h6'>Sepetteki Ürünler</Typography>
                        </Box>
                        <Button color="warning" variant="contained">Siparişi Tamamla</Button>
                    </Box>

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
                                        <Typography sx={{ color: "orange" }} variant='h4'>{cartItem.productDto.brand}</Typography>
                                        <Typography variant='h6'>{cartItem.productDto.name}</Typography>
                                    </Box>
                                </Box>

                                <Box >
                                    <Box sx={{ display: "flex" }}>
                                        <Typography variant='h6' sx={{ marginLeft: "10px", marginTop: "5px" }}>Ürün miktarı: </Typography>
                                        <Typography variant='h6' sx={{ marginLeft: "10px", marginTop: "5px" }}>{cartItem.quantity}</Typography>
                                        <AddCircleIcon className='cart-icon cart-icon-plus' sx={{ marginTop: "8px", marginLeft: "5px", color: "green" }} />
                                        <RemoveCircleIcon className='cart-icon cart-icon-minus' sx={{ marginTop: "8px", marginLeft: "5px", marginRight: "10px", color: "red" }} />
                                    </Box>
                                    <Box sx={{ marginTop: "25px" }}>
                                        <Button variant='contained' color="error">Ürünü Sepetten Çıkar</Button>
                                    </Box>
                                </Box>



                            </Box>
                        ))

                    }
                </Grid>


            </Grid>
        </Container>
    )
}

export default Cart