import React from 'react'
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import SendIcon from '@mui/icons-material/Send';

function Category({ category, onSelect }) {


    

    return (
        <ListItemButton  onClick={() => onSelect(category.categoryId)}>
            <ListItemIcon>
                <SendIcon />
            </ListItemIcon>
            <ListItemText primary={category.name} />
        </ListItemButton>
    )
}

export default Category