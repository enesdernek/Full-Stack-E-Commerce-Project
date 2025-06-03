import React from 'react'
import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import FavoriteIcon from '@mui/icons-material/Favorite';import Category from './Category';
import CampaignIcon from '@mui/icons-material/Campaign';

function CampaignList() {
    return (

        <List 
            sx={{
                width: '95%',
                maxWidth: 360,
                bgcolor: 'background.paper',
                border: "1px solid #ccc",
                marginTop:"30px",
                marginBottom:"20px"
            }}
            component="nav"
            aria-labelledby="nested-list-subheader"
           
        >
            <ListItemButton sx={{color:"orange"}} onClick={() => navigate("/products")} >
                <ListItemIcon>
                    <CampaignIcon sx={{color:"orange"}} />
                </ListItemIcon>
                <ListItemText primary="Kampanyalı Ürünler" />
            </ListItemButton>

            <ListItemButton sx={{color:"red"}} onClick={() => navigate("/products")} >
                <ListItemIcon>
                    <FavoriteIcon sx={{color:"red"}} />
                </ListItemIcon>
                <ListItemText primary="Beğendiğim Ürünler" />
            </ListItemButton>
            


        </List>

    )
}

export default CampaignList