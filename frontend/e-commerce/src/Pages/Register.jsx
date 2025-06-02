import { Box, Button, Checkbox, Container, FormHelperText, Paper, TextField, Typography } from '@mui/material'
import React from 'react'
import { useNavigate } from "react-router-dom"
import { useFormik } from "formik";
import * as yup from "yup"
import { register } from '../redux/slices/userSlice';
import { useDispatch } from 'react-redux';

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
    passwordCheck: yup.string("Please verify your password")
        .oneOf([yup.ref('password'), null], "Passwords must match")
        .required("Password verification is required"),
    term: yup.boolean()
        .oneOf([true], "You must accept the terms")

});

function Register() {

    const navigate = useNavigate()
    const dispatch = useDispatch()

    const formik = useFormik({
        initialValues: {
            username: "",
            password: "",
            passwordCheck: "",
            term: false,

        },
        validationSchema: validationSchema,
        onSubmit: (values) => {
               const body = {
                    username: values.username,
                    password: values.password
                }
                dispatch(register(body))
    
        },
    })

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
                            Üye Ol
                        </Typography>
                        <TextField
                            id="username"
                            name="username"
                            label="Kullanıcı Adı"
                            variant="outlined"
                            sx={{
                                width: {
                                    xs: "100%",
                                    sm: "75%",
                                    marginBottom: "16px"
                                },
                                marginTop: "12px"
                            }}
                            value={formik.values.username}
                            onChange={formik.handleChange}
                            error={formik.touched.username && Boolean(formik.errors.username)}
                        />
                        {formik.touched.username && formik.errors.username && (
                            <FormHelperText error>{formik.errors.username}</FormHelperText>
                        )}

                        <TextField
                            id="password"
                            name="password"
                            label="Şifre"
                            type="password"
                            variant="outlined"
                            sx={{
                                width: { xs: "100%", sm: "75%", marginBottom: "16px" },
                                marginTop: "12px"
                            }}
                            value={formik.values.password}
                            onChange={formik.handleChange}
                            error={formik.touched.username && Boolean(formik.errors.username)}
                        />
                        {formik.touched.password && formik.errors.password && (
                            <FormHelperText error>{formik.errors.password}</FormHelperText>
                        )}

                        <TextField
                            id="passwordCheck"
                            name="passwordCheck"
                            label="Şifreyi Onayla"
                            type="password"
                            variant="outlined"
                            sx={{
                                width: { xs: "100%", sm: "75%", marginBottom: "16px" },
                                marginTop: "12px"
                            }}
                            value={formik.values.passwordCheck}
                            onChange={formik.handleChange}
                            error={formik.touched.passwordCheck && Boolean(formik.errors.passwordCheck)}
                        />
                        {formik.touched.passwordCheck && formik.errors.passwordCheck && (
                            <FormHelperText error>{formik.errors.passwordCheck}</FormHelperText>
                        )}

                        <Box
                            sx={{
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "flex-start",
                                width: { xs: "100%", sm: "75%" },
                                paddingRight: "20px"
                            }}
                        >
                            <Checkbox name="term" id="term"
                                value={formik.values.term}
                                onChange={formik.handleChange}
                                error={formik.touched.passwordCheck && Boolean(formik.errors.term)}
                            />
                            <Typography variant="body2">
                                Şartları kabul ediyorum
                            </Typography>
                            {formik.touched.term && formik.errors.term && (
                                <FormHelperText sx={{marginLeft:"5px",marginTop:"5px"}} error>{formik.errors.term}</FormHelperText>
                            )}
                        </Box>


                        <Button
                            type='submit'
                            variant="contained"
                            sx={{
                                width: { xs: "100%", sm: "75%" },
                                marginTop: "20px",
                                fontWeight: "bold",
                                paddingY: "10px"
                            }}
                        >
                            Üye Ol
                        </Button>

                        <Typography
                            component="a"
                            onClick={() => navigate("/login")}
                            sx={{
                                marginTop: "20px",
                                textDecoration: "underline",
                                color: "primary.main",
                                cursor: "pointer"
                            }}
                            gutterBottom
                        >
                            Zaten bir hesabın var mı ? Giriş yapmak için tıklayın.
                        </Typography>

                    </Box>
                </form>

            </Paper>
        </Container>
    )
}

export default Register