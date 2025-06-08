import React, { useEffect } from 'react'
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ShareIcon from '@mui/icons-material/Share';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import AddShoppingCartIcon from '@mui/icons-material/AddShoppingCart';
import { Box, Chip } from '@mui/material';
import { useNavigate } from "react-router-dom"
import { useDispatch, useSelector } from 'react-redux';
import { addProductToCartByProductId } from '../redux/slices/cartSlice';
import { openSnackbar } from '../redux/slices/appSlice';

function Product({ product }) {

  const navigate = useNavigate()
  const dispatch = useDispatch()
  const token = useSelector((state) => state.user.token)
  const user = useSelector((state) => state.user.user)

  const addProductToCart = (productId) => {
    if (user) {
      dispatch(addProductToCartByProductId({ productId, token }))
      dispatch(openSnackbar({ message: "Ürün sepete eklendi!", severity: "success" }));
    }else{
      dispatch(openSnackbar({message:"Ürünü sepete eklemek için lütfen giriş yapın!",severity:"error"}))
    }

  }

  return (
    <Card
      sx={{
        width: "100%",
        height: 353,
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        bgcolor: 'background.paper',
        border: "1px solid #ccc"

      }}
    >
      <CardHeader
        onClick={() => navigate(`/product/${product.productId}`)}
        titleTypographyProps={{ variant: "h6" }}
        title={product.name}
        sx={{
          backgroundColor: "#f5f5f5",
          "&:hover": {
            backgroundColor: "#f5f5f5	",
            cursor: "pointer"
          }
        }}

      />

      <CardMedia
        onClick={() => navigate(`/product/${product.productId}`)}
        component="img"
        image={"/src" + product.imagePath}
        alt={product.name}
        sx={{
          height: 250,
          width: 420,
          objectFit: "cover",
          "&:hover": {
            cursor: "pointer"
          }
        }}
      />

      <CardActions
        disableSpacing
        sx={{
          justifyContent: "space-between",
          padding: "0px 2px",
          backgroundColor: "#f5f5f5",


        }}
      >

        {product.discount > 0 ? (
          <Box sx={{ display: "flex", alignItems: "center", gap: "0px" }}>
            <Typography
              sx={{
                textDecoration: "line-through",
                color: "gray",
                fontSize: "14px",
                fontWeight: "normal",
              }}
            >
              {product.price} ₺
            </Typography>
            <Chip
              sx={{
                marginBottom: "2px",
                fontWeight: "bold",
                fontSize: "16px",
                backgroundColor: "#F5F5F5",
              }}
              label={`${product.discountedPrice} ₺`}
            />
          </Box>
        ) : (
          <Chip
            sx={{
              marginBottom: "2px",
              fontWeight: "bold",
              fontSize: "16px",
              backgroundColor: "#F5F5F5",
            }}
            label={`${product.price} ₺`}
          />
        )}

        <IconButton onClick={() => addProductToCart(product.productId)} aria-label="share">
          <AddShoppingCartIcon />
        </IconButton>
      </CardActions>
    </Card>
  );

}

export default Product    