import React, { useEffect, useState } from 'react'
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import AccountCircle from '@mui/icons-material/AccountCircle';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';
import MenuItem from '@mui/material/MenuItem';
import Menu from '@mui/material/Menu';
import { useDispatch, useSelector } from 'react-redux';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { useNavigate } from 'react-router-dom';
import { setProductListBarHeader } from '../redux/slices/appSlice';


function ProductListBar() {

    const [anchorEl, setAnchorEl] = React.useState(null);
    const productListBarHeader = useSelector((state) => state.app.productListBarHeader)
    const [filter, setFilter] = useState("Filtre")
    const navigate = useNavigate()
    const dispatch = useDispatch()

    useEffect(() => {
        setFilter("Filtre")
    }, [navigate])

    const handleSelectChange = (event) => {
        const selected = event.target.value;
        setFilter(selected);
        dispatch(setProductListBarHeader("Fiyata Göre "+selected))
        navigate(`/product/filtered-by-price/${selected}`);
    };

    const handleChange = (event) => {
        setAuth(event.target.checked);
    };

    const handleMenu = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    return (
        <Box sx={{ flexGrow: 1 }}>

            <AppBar position="static">
                <Toolbar>

                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        {
                            productListBarHeader ? productListBarHeader : "Tüm Ürünler"
                        }

                    </Typography>

                    {
                        productListBarHeader && productListBarHeader === "Tüm Ürünler" || "Fiyata Göre Artan" || "Fiyata Göre Azalan" ? 

                         <div>
                        <FormControl variant="filled" sx={{ m: 1, minWidth: 120, backgroundColor: "white" }}>
                            <InputLabel id="demo-simple-select-filled-label">Filtre</InputLabel>
                            <Select
                                labelId="demo-simple-select-filled-label"
                                id="demo-simple-select-filled"
                                value={filter}
                                onChange={handleSelectChange}
                            >

                                <MenuItem value={"Artan"}>Fiyata Göre Artan</MenuItem>
                                <MenuItem value={"Azalan"}>Fiyata Göre Azalan</MenuItem>
                            </Select>
                        </FormControl>
                    </div>
                    : <></>
                    }

                   

                </Toolbar>
            </AppBar>
        </Box>
    );

}

export default ProductListBar    