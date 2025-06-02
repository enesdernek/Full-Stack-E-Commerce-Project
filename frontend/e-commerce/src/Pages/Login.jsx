import { Box, Button, CircularProgress, Container, Paper, TextField, Typography } from '@mui/material'
import { useFormik } from 'formik';
import React, { useEffect } from 'react'
import { useNavigate } from "react-router-dom"
import { authenticate } from '../redux/slices/userSlice';
import { useDispatch, useSelector } from 'react-redux';
import * as yup from "yup"


const validationSchema = yup.object({
    username: yup.string("Enter your username")
        .required("username is required")
        .min(3, "Username has to contain 3 characters at least.")
        .max(18, "Username can't has more than 18 characters.")
    ,
    password: yup
        .string('Enter your password')
        .required('Password is required')
        .min(8, "Password has to contain 8 characters at least ")
        .max(128, "Password can't has more than 128 characters"),

});

function Login() {

    const navigate = useNavigate()
    const dispatch = useDispatch()
    const user = useSelector((state)=>state.user.user)
    const loading = useSelector((state)=>state.user.loading)

    const formik = useFormik({
        initialValues: {
            username: "",
            password: ""
        },
        validationSchema: validationSchema,
        onSubmit: ((values) => {
            const body = {
                username: values.username,
                password: values.password
            }
            console.log(body)
            dispatch(authenticate(body))
        })
    })

    useEffect(()=>{
        if(user){
             navigate("/products")
        }
    },[user])

    return (
        <Container sx={{ marginTop: "30px" }}>
            <Paper
                elevation={3}
                sx={{
                    padding: "32px",
                    margin: "auto",
                    marginTop: "50px",
                    textAlign: "center",
                }}
            >

                <form onSubmit={formik.handleSubmit}>
                    <Box sx={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        margin: "10px",
                        padding: "10px",
                        borderRadius: "10px",
                    }}>
                        <Typography
                            variant="h4"
                            sx={{ fontWeight: "bold", marginBottom: "20px", color: "primary.main" }}
                        >
                            Giriş Yap
                        </Typography>
                        <TextField
                            id="username"
                            label="Kullanıcı Adı"
                            name="username"
                            variant="outlined"
                            onChange={formik.handleChange}
                            value={formik.values.username}
                            sx={{
                                width: {
                                    xs: "100%",
                                    sm: "75%",
                                    marginBottom: "16px"

                                },
                                marginTop: "12px"
                            }}
                        />

                        <TextField
                            id="password"
                            label="Şifre"
                            type="password"
                            variant="outlined"
                            name="password"
                            onChange={formik.handleChange}
                            value={formik.values.password}
                            sx={{
                                width: { xs: "100%", sm: "75%" },
                                marginTop: "12px",
                                marginBottom: "16px"
                            }}
                        />

                        <Button
                            variant="contained"
                            type='submit'
                            sx={{
                                width: { xs: "100%", sm: "75%" },
                                marginTop: "20px",
                                fontWeight: "bold",
                                paddingY: "10px"
                            }}
                        >
                            {
                                loading ? 
                                <CircularProgress/> :
                                <span>Giriş Yap</span>
                            }
                            
                        </Button>

                        <Typography
                            component="a"
                            onClick={() => navigate("/register")}
                            sx={{
                                marginTop: "20px",
                                textDecoration: "underline",
                                color: "primary.main",
                                cursor: "pointer"
                            }}
                            gutterBottom
                        >
                            Hesabınız yok mu ? Oluşturmak için tıklayın.
                        </Typography>

                    </Box>
                </form>
            </Paper>
        </Container >
    )
}

export default Login