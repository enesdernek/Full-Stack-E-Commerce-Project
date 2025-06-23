import React, { useEffect, useState } from 'react'
import cities from "../data/city.json"
import districts from "../data/district.json"
import * as yup from "yup"
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import { Alert, Box, Button, Checkbox, Container, Divider, FormControl, FormControlLabel, FormHelperText, Grid, IconButton, Input, InputAdornment, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material'
import LocalPhoneIcon from '@mui/icons-material/LocalPhone';
import { createOrder } from '../redux/slices/orderSlice';



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
        .min(8, "Sipariş adresi en az 8 karakterden oluşmalı.")
        .max(256, "Sipariş adresi 256 karakterden fazla olamaz."),
    term: yup
        .boolean()
        .oneOf([true], "Sipariş sözleşmesini kabul etmelisiniz!")
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
            deliveryAdress: "",
            term: false
        },
        validationSchema: validationSchema,
        onSubmit: ((values) => {
            const body = {
                phoneNumber: values.phoneNumber,
                city: values.city,
                district: values.district,
                deliveryAdress: values.deliveryAdress,
                totalPrice: cartTotalPrice
            }
            dispatch(createOrder({ token, body })).then(result => {
                navigate("/order/"+result.payload.orderId)
            });

        })
    })


    return (
        <Container>
            <Grid container spacing={2}>
                <Grid size={{ xs: 12, md: 6, lg: 6, sm: 6 }} sx={{ marginTop: "20px" }}  >
                    <Typography variant='h5'>Sipariş Oluştur</Typography>

                    <form onSubmit={formik.handleSubmit}>
                        <TextField
                            error={formik.touched.phoneNumber && Boolean(formik.errors.phoneNumber)}
                            sx={{
                                width: "100%",
                                marginTop: "20px"
                            }}
                            id="input-with-icon-textfield"
                            label="Telefon Numarası"
                            name="phoneNumber"
                            type='number'
                            value={formik.values.phoneNumber}
                            onChange={formik.handleChange}
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
                        {formik.touched.phoneNumber && formik.errors.phoneNumber && (
                            <FormHelperText error>{formik.errors.phoneNumber}</FormHelperText>
                        )}

                        <FormControl fullWidth sx={{ marginTop: "20px" }}>
                            <InputLabel>Şehir</InputLabel>
                            <Select
                                error={formik.touched.city && Boolean(formik.errors.city)}
                                name="city" value={formik.values.city} label="Şehir" onChange={(e) => {
                                    formik.handleChange(e);
                                    handleChangeCity(e);
                                }}>
                                {cityDatas.map((city) => (
                                    <MenuItem key={city.id} value={city.name}>
                                        {city.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        {formik.touched.city && formik.errors.city && (
                            <FormHelperText error>{formik.errors.city}</FormHelperText>
                        )}

                        <FormControl fullWidth sx={{ marginTop: "20px" }}>
                            <InputLabel>İlçe</InputLabel>
                            <Select
                                error={formik.touched.district && Boolean(formik.errors.district)}
                                name="district" value={formik.values.district} label="İlçe" onChange={(e) => {
                                    formik.handleChange(e);
                                    handleChangeDistrict(e);
                                }} disabled={!city} >
                                {filteredDistricts.map((district) => (
                                    <MenuItem key={district.id} value={district.name}>
                                        {district.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        {formik.touched.district && formik.errors.district && (
                            <FormHelperText error>{formik.errors.district}</FormHelperText>
                        )}


                        <TextField
                            sx={{
                                width: "100%",
                                marginTop: "20px"
                            }}
                            error={formik.touched.deliveryAdress && Boolean(formik.errors.deliveryAdress)}
                            value={formik.values.deliveryAdress}
                            onChange={formik.handleChange}
                            id="outlined-multiline-static"
                            name="deliveryAdress"
                            label="Tam Adres"
                            multiline
                            rows={4}
                        />
                        {formik.touched.deliveryAdress && formik.errors.deliveryAdress && (
                            <FormHelperText error>{formik.errors.deliveryAdress}</FormHelperText>
                        )}

                        <FormControlLabel
                            control={
                                <Checkbox
                                    name="term"
                                    checked={formik.values.term}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                />
                            }
                            label="Sipariş sözleşmesini kabul ediyorum."
                        />
                        {formik.touched.term && formik.errors.term && (
                            <FormHelperText error>{formik.errors.term}</FormHelperText>
                        )}
                        <Typography variant="h5">Toplam: {cartTotalPrice} ₺</Typography>
                        <Button type='submit' variant='contained' color="success">Sipariş Oluştur</Button>
                    </form>
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