import React from 'react'
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import CategoryIcon from '@mui/icons-material/Category';
import { useNavigate } from 'react-router-dom';

function Category({ category }) {

    const navigate = useNavigate()

    const navigateToCategorizedProductList=(categoryId)=>{
        navigate("/products/"+categoryId)
    }

    return (
        <ListItemButton  onClick={()=>navigateToCategorizedProductList(category.categoryId)}>
            <ListItemIcon>
                <CategoryIcon sx={{color:"blue"}} />
            </ListItemIcon>
            <ListItemText primary={category.name} />
        </ListItemButton>
    )
}

export default Category