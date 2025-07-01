import { Box, Button, Container, Grid, Typography } from '@mui/material'
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getAllOrders } from '../redux/slices/orderSlice'
import { useNavigate } from 'react-router-dom'

function OrderListPage() {

    const orders = useSelector((state) => state.order.orderList)
    const token = useSelector((state) => state.user.token)
    const dispatch = useDispatch()
    const navigate = useNavigate()


    useEffect(() => {
        dispatch(getAllOrders({ token }))
    }, [dispatch])

    useEffect(() => {
        console.log(orders)
    }, [orders])

    return (
        <Container>
            <Typography sx={{ marginTop: "20px" }} variant='h4'>Siparişlerim</Typography>
            <Grid size={{ xs: 12, md: 12 }}>
                <Box >
                    <Box >
                        {
                            orders && orders.map((order) => (
                                <Box sx={{ border: "1px solid grey", padding: "8px", margin: "8px", display: "flex", justifyContent: "space-between" }}>
                                    <Box>
                                        <Typography variant="body1">
                                            Tarih: {new Date(order.date).toLocaleString('tr-TR')}
                                        </Typography>                                        <Typography variant='body1'>Tutar: {order.totalPrice} ₺</Typography>
                                    </Box>
                                    <Button onClick={() => navigate("/order/" + order.orderId)} variant="contained">Sipariş Ayrıntılarına Git</Button>
                                </Box>
                            ))
                        }
                    </Box>
                </Box>
            </Grid>
        </Container>
    )
}

export default OrderListPage