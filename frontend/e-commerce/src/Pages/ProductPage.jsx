import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import { getProductByProductId } from '../redux/slices/productSlice'
import { Box, Container, Grid, Rating, Stack, Typography } from '@mui/material'

function ProductPage() {

    const { productId } = useParams()
    const dispatch = useDispatch()
    const product = useSelector((state) => state.product.product)

    useEffect(() => {
        dispatch(getProductByProductId(productId))
    }, [])

    useEffect(() => {
        console.log(product)
    }, [product])

    return (
        <Container sx={{ marginTop: "20px" }}>
            {
                product &&
                <Grid container spacing={2}>
                    <Grid size={{ xs: 12, md: 6 }}>
                        <img src={"/src" + product.imagePath}
                            alt={product.name || "Ürün görseli"}
                            style={{
                                height: "100%",
                                width: "100%",
                                borderRadius: "10px"
                            }}
                        />
                    </Grid>
                    <Grid size={{ xs: 12, md: 6 }}>
                        <Box>
                            <Box sx={{ display: "flex",justifyContent:"space-between" }}>
                                <Box>
                                    <Typography sx={{ color: "orange", marginLeft: "7px" }} variant="h4">{product.brand}</Typography>
                                    <Typography sx={{ marginLeft: "6px" }} variant="h4">{product.name}</Typography>
                                </Box>
                                <span>a</span>
                                <Rating
                                    name="simple-controlled"
                                    value={1}
                                    sx={{
                                        fontSize: "36px"
                                    }}
                                    onChange={(event, newValue) => {
                                        setValue(newValue);
                                    }}
                                />
                            </Box>
                            <Typography sx={{ marginLeft: "8px", fontSize: "20px" }} variant="span">{product.description}</Typography>
                        </Box>
                    </Grid>
                </Grid>
            }

        </Container>
    )
}

export default ProductPage