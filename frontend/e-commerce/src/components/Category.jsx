import React from 'react'
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import SendIcon from '@mui/icons-material/Send';
import { useNavigate } from 'react-router-dom';

function Category({ category }) {

    const navigate = useNavigate()

    const navigateToCategorizedProductList=(categoryId)=>{
        navigate("/products/"+categoryId)
    }

    return (
        <ListItemButton  onClick={()=>navigateToCategorizedProductList(category.categoryId)}>
            <ListItemIcon>
                <SendIcon />
            </ListItemIcon>
            <ListItemText primary={category.name} />
        </ListItemButton>
    )
}

export default Category