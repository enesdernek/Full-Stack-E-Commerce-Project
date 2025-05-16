import { Box, Button, Checkbox, Container, Paper, TextField, Typography } from '@mui/material'
import React from 'react'
import { useNavigate } from "react-router-dom"

function Register() {

    const navigate = useNavigate()
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
                        REGISTER
                    </Typography>
                    <TextField
                        id="outlined-basic"
                        label="Username"
                        variant="outlined"
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
                        label="Password"
                        type="password"
                        variant="outlined"
                        sx={{
                            width: { xs: "100%", sm: "75%", marginBottom: "16px" },
                            marginTop: "12px"
                        }}
                    />
                      <TextField
                        id="password-verify"
                        label="Verify Password"
                        type="password"
                        variant="outlined"
                        sx={{
                            width: { xs: "100%", sm: "75%", marginBottom: "16px" },
                            marginTop: "12px"
                        }}
                    />

                    <Box
                        sx={{
                            display: "flex",
                            alignItems: "center", 
                            justifyContent: "flex-start", 
                            width: { xs: "100%", sm: "75%" }, 
                            marginRight:"22px"
                        }}
                    >
                        <Checkbox />
                        <Typography variant="body2">
                            I accept the terms
                        </Typography>
                    </Box>


                    <Button
                        variant="contained"
                        sx={{
                            width: { xs: "100%", sm: "75%" },
                            marginTop: "20px",
                            fontWeight: "bold",
                            paddingY: "10px"
                        }}
                    >
                        REGISTER
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
                        Do you have an account? Click here to log in
                    </Typography>

                </Box>
            </Paper>
        </Container>
    )
}

export default Register