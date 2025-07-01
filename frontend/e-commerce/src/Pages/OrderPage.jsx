import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'
import { getByOrderId } from '../redux/slices/orderSlice'
import { Alert, Box, Container, Divider, Grid, Typography } from '@mui/material'

function OrderPage() {

  const { orderId } = useParams()
  const dispatch = useDispatch()
  const navigate = useNavigate()
  const order = useSelector((state) => state.order.order)
  const token = useSelector((state) => state.user.token)
  const orderItems = useSelector((state) => state.order.order?.orderItemDtos || []);
  

  useEffect(() => {
    dispatch(getByOrderId({ token, orderId }))
  }, [])

  return (
    <Container>
 
      <Grid>
        <Typography sx={{ marginTop: "20px" }} variant='h4'>Sipariş Detayları</Typography>
        {
          order ?
            <Box>
              <Typography>Adres :{order.deliveryAdress}</Typography>
              <Typography>İletişim Numarası :{order.phoneNumber}</Typography>
              <Typography>İl :{order.city}</Typography>
              <Typography>İlçe :{order.district}</Typography>
              <Typography>Tutar :{order.totalPrice} ₺</Typography>
            </Box>
            :
            <Box></Box>
        }

        <Divider sx={{marginTop:"20px"}} component="p" />

        <Typography sx={{marginTop:"20px"}} variant="h5">Sipariş Edilen Ürünler</Typography>

        {
          orderItems && orderItems.map((orderItem) => (
            <Box key={orderItem.productDto.productId} sx={{ display: "flex", justifyContent: "space-between", marginTop: "8px", border: "1px solid black" }}>
              <Box sx={{ display: "flex" }}>
                <img src={"/src" + orderItem.productDto.imagePath}
                  alt={orderItem.productDto.name || "Ürün görseli"}
                  style={{
                    height: "100px",
                    width: "150px",
                  }}
                />
                <Box sx={{ marginLeft: "10px", marginTop: "5px" }}>
                  <Typography sx={{ color: "orange" }} variant='h5'>{orderItem.productDto.brand}</Typography>
                  <Typography variant='h6'>{orderItem.productDto.name}</Typography>
                  <Typography sx={{ color: "red" }} variant='h6'>{orderItem.productDto.price} ₺</Typography>
                </Box>
              </Box>
              <Box>
                <Typography sx={{ padding: "10px" }} variant='h6'>Adet: {orderItem.quantity}</Typography>
              </Box>
            </Box>
          ))
        }


      </Grid>
    </Container>
  )
}

export default OrderPage