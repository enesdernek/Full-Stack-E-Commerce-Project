import React from 'react'
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
import { Chip } from '@mui/material';

function Product({ product }) {

  return (
    <Card
      sx={{
        width: "100%",
        height: 300,
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
        bgcolor: 'background.paper',
        border: "1px solid #ccc"

      }}
    >
      <CardHeader
        titleTypographyProps={{ variant: "h6" }}
        title={product.name}
        sx={{
          backgroundColor: "#f5f5f5"
        }}

      />

      <CardMedia
        component="img"
        image={"/src" + product.imagePath}
        alt={product.name}
        sx={{
          height: 200,
          width: 300,
          objectFit: "cover",
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

        <Chip sx={{marginBottom:"2px",fontWeight:"bold",fontSize:"16px",backgroundColor:"#F5F5F5"}} label={`${product.price + " ₺"}`} />

        <IconButton aria-label="share">
          <AddShoppingCartIcon />
        </IconButton>
      </CardActions>
    </Card>
  );

}

export default Product    