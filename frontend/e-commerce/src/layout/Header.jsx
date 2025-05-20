import React, { useSyncExternalStore } from 'react'
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import StorefrontIcon from '@mui/icons-material/Storefront';
import AccountCircle from '@mui/icons-material/AccountCircle';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import { Button, Container, Grid, Icon, TextField } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import "../style/header.css"
import Badge from '@mui/material/Badge';
import { useDispatch, useSelector } from "react-redux"
import { logOut } from '../redux/slices/userSlice';
import { styled } from '@mui/material/styles';


export function Header() {

  const [auth, setAuth] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const user = useSelector((state) => state.user.user)

  const handleChange = (event) => {
    setAuth(event.target.checked);
  };

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const logOutFunc = () => {
    dispatch(logOut())
    handleClose()
    navigate("/login")
  }

  const navigateToProfile=()=>{
       navigate("/profile")
       handleClose()
  }

  const StyledBadge = styled(Badge)(({ theme }) => ({
  '& .MuiBadge-badge': {
    right: -3,
    top: 13,
    border: `2px solid ${(theme.vars ?? theme).palette.background.paper}`,
    padding: '0 4px',
  },
}));

  return (



    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Container maxWidth="xl">
          <Toolbar >
            <IconButton onClick={() => navigate("/products")}
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
            >
              <StorefrontIcon />

            </IconButton>

            <Typography onClick={() => navigate("/products")} variant="h6" component="div" sx={{ flexGrow: 1 }}>
              <span id="header-text">E-Commerce</span>
            </Typography>

            <TextField
              sx={{ backgroundColor: "white", marginRight: "40px", marginY: "10px" }}
              id="outlined-basic" label="Ürün ara..." variant="outlined" />

            <IconButton aria-label="cart">
              <StyledBadge badgeContent={4} color="secondary">
                <ShoppingCartIcon sx={{color:"white"}} />
              </StyledBadge>
            </IconButton>

            {
              user && user ?
                <div>
                  <IconButton
                    size="large"
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    onClick={handleMenu}
                    color="inherit"

                  >
                    <AccountCircle />
                  </IconButton>
                  <Menu
                    id="menu-appbar"
                    anchorEl={anchorEl}
                    anchorOrigin={{
                      vertical: 'top',
                      horizontal: 'right',
                    }}
                    keepMounted
                    transformOrigin={{
                      vertical: 'top',
                      horizontal: 'right',
                    }}
                    open={Boolean(anchorEl)}
                    onClose={handleClose}
                  >
                    <MenuItem onClick={()=>navigateToProfile()}>Profile</MenuItem>
                    <MenuItem onClick={handleClose} >My Orders</MenuItem>
                    <MenuItem onClick={() => logOutFunc()}>Log Out</MenuItem>
                  </Menu>
                </div>
                :
                <Button onClick={() => navigate("/login")} sx={{ marginLeft: "16px" }} variant="contained" color="warning">Log In</Button>

            }


          </Toolbar>
        </Container>
      </AppBar>
    </Box>

  )
}

export default Header