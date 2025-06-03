import React, { useEffect } from 'react'
import ListSubheader from '@mui/material/ListSubheader';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import SendIcon from '@mui/icons-material/Send';
import { useDispatch, useSelector } from 'react-redux';
import { getAllCategories } from '../redux/slices/categorySlice';
import Category from './Category';
import { useNavigate } from 'react-router-dom';
import CategoryIcon from '@mui/icons-material/Category';


function CategoryList() {

  const dispatch = useDispatch()
  const categories = useSelector((state)=>state.category.categories)
  const navigate = useNavigate()



  useEffect(()=>{
      dispatch(getAllCategories())
  },[])

  


  return (
    <List
    sx={{
      width: '95%',
      maxWidth: 360,
      bgcolor: 'background.paper',
      border: "1px solid #ccc", 
      color:"blue"  
    }}
      component="nav"
      aria-labelledby="nested-list-subheader"
      subheader={
        <ListSubheader 
        sx={{
          backgroundColor:"#f5f5f5",
          fontWeight:"bold"
        }}
        component="div" id="nested-list-subheader">
          Kategoriler
        </ListSubheader>
      }
    >
      <ListItemButton onClick={()=>navigate("/products")} >
            <ListItemIcon>
                <CategoryIcon sx={{color:"blue"}}/>
            </ListItemIcon>
            <ListItemText primary="Bütün Ürünler"/>
        </ListItemButton>
     {
       categories && categories.map((category)=>(
             <Category key={category.categoryId} category={category}/>
       ))
     }
  
      
    </List>
  )
}

export default CategoryList        