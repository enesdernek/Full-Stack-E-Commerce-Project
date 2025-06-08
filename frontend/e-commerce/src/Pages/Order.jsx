import React, { useEffect, useState } from 'react'
import cities from "../data/city.json"
import districts from "../data/district.json"
import * as yup from "yup"
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import { Alert, Box, Button, Checkbox, Container, Divider, FormControl, FormControlLabel, Grid, IconButton, Input, InputAdornment, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material'
import LocalPhoneIcon from '@mui/icons-material/LocalPhone';



const validationSchema = yup.object({
    phoneNumber: yup
        .string()
        .required('Phone number is required')
        .length(10, 'Phone number must be exactly 10 digits')
        .matches(/^\d+$/, 'Phone number must contain only digits'),
    city: yup
        .string()
        .required('city is required'),
    district: yup
        .string()
        .required('district is required'),
    deliveryAdress: yup.string("Enter your username")
        .required("delivery adress is required")
        .min(8, "Username has to contain 3 characters at least.")
        .max(256, "Username can't has more than 18 characters.")
});

function Order() {

    const navigate = useNavigate()
    const dispatch = useDispatch()
    const user = useSelector((state) => state.user.user)
    const token = useSelector((state) => state.user.token)
    const cartItems = useSelector((state) => state.cart.cartItems)
    const cartTotalPrice = useSelector((state) => state.cart.cartTotalPrice)
    const cityDatas = cities[2].data;
    const districtDatas = districts[2].data;
    const [city, setCity] = useState("");
    const [cityId, setCityId] = useState("");
    const [district, setDistrict] = useState("");
    const [filteredDistricts, setFilteredDistricts] = useState([]);

    const handleChangeCity = (event) => {
        const selectedCityName = event.target.value;
        setCity(selectedCityName);

        const selectedCityObj = cityDatas.find((c) => c.name === selectedCityName);

        if (selectedCityObj) {
            setCityId(selectedCityObj.id);

            const relatedDistricts = districtDatas.filter(
                (d) => d.il_id === selectedCityObj.id
            );
            setFilteredDistricts(relatedDistricts);
        } else {
            setFilteredDistricts([]);
        }

        setDistrict("");

    };

    const handleChangeDistrict = (event) => {
        setDistrict(event.target.value);
    };


    const formik = useFormik({
        initialValues: {
            phoneNumber: "",
            city: "",
            district: "",
            deliveryAdress: ""
        },
        validationSchema: validationSchema,
        onSubmit: ((values) => {
            const body = {
                phoneNumber: values.phoneNumber,
                city: values.city,
                district: values.district,
                deliveryAdress: values.deliveryAdress,
                totalPrice: 0
            }
            dispatch(authenticate(body))
        })
    })

    return (
        <Container>
            <Grid container spacing={2}>
                <Grid size={{ xs: 12, md: 6, lg: 6, sm: 6 }} sx={{ marginTop: "20px" }}  >
                    <Typography variant='h5'>Sipariş Oluştur</Typography>

                    <TextField
                        sx={{
                            width: "100%",
                            marginTop: "20px"
                        }}
                        id="input-with-icon-textfield"
                        label="Telefon Numarası"
                        slotProps={{
                            input: {
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <LocalPhoneIcon />
                                    </InputAdornment>
                                ),
                            },
                        }}
                        variant="standard"
                    />

                    <FormControl fullWidth sx={{ marginTop: "20px" }}>
                        <InputLabel>Şehir</InputLabel>
                        <Select value={city} label="Şehir" onChange={handleChangeCity}>
                            {cityDatas.map((city) => (
                                <MenuItem key={city.id} value={city.name}>
                                    {city.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <FormControl fullWidth sx={{ marginTop: "20px" }}>
                        <InputLabel>İlçe</InputLabel>
                        <Select value={district} label="İlçe" onChange={handleChangeDistrict} disabled={!city} >
                            {filteredDistricts.map((district) => (
                                <MenuItem key={district.id} value={district.name}>
                                    {district.name}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>


                    <TextField
                        sx={{
                            width: "100%",
                            marginTop: "20px"
                        }}
                        id="outlined-multiline-static"
                        label="Tam Adres"
                        multiline
                        rows={4}
                        defaultValue="Default Value"
                    />

                    <FormControlLabel control={<Checkbox />} label="Sipariş sözleşmesini kabul ediyorum." />
                    <Typography variant="h5">Toplam: {cartTotalPrice} ₺</Typography>
                    <Button variant='contained' color="success">Sipariş Oluştur</Button>
                </Grid>



                <Divider sx={{ marginTop: "20px" }} orientation="vertical" flexItem />

                <Grid size={{ xs: 12, md: 5, lg: 5, sm: 5 }}  >
                    <Typography sx={{ marginTop: "20px" }} variant='h5'>Sepetteki Ürünler</Typography>
                    {
                        cartItems && cartItems.map((cartItem) => (
                            <Box key={cartItem.productDto.productId} sx={{ display: "flex", marginTop: "20px", border: "1px solid black" }}>
                                <Box sx={{ display: "flex" }}>
                                    <img src={"/src" + cartItem.productDto.imagePath}
                                        alt={cartItem.productDto.name || "Ürün görseli"}
                                        style={{
                                            height: "100px",
                                            width: "150px",
                                        }}
                                    />
                                    <Box sx={{ marginLeft: "10px", marginTop: "5px", display: "grid" }}>
                                        <Typography sx={{ color: "orange" }} variant='p'>{cartItem.productDto.brand}</Typography>
                                        <Typography variant='p'>{cartItem.productDto.name}</Typography>
                                        <Typography sx={{ color: "red" }} variant='p'>{cartItem.productDto.price} ₺</Typography>
                                        <Typography variant='p'>Ürün miktarı: {cartItem.quantity}</Typography>
                                    </Box>
                                </Box>




                            </Box>
                        ))

                    }
                </Grid>

            </Grid>

        </Container>
    )
}

export default Order