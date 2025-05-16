import { Box, Button, Container, Paper, TextField, Typography } from '@mui/material'
import React from 'react'
import { useNavigate } from "react-router-dom"

function Login() {
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
                        LOG IN
                    </Typography>
                    <TextField
                        id="outlined-basic"
                        label="Username"
                        variant="outlined"
                        sx={{
                            width: {
                                xs: "100%", 
                                sm: "75%",
                                marginBottom:"16px" 
                                    
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
                            width: { xs: "100%", sm: "75%" },
                            marginTop: "12px",
                            marginBottom:"16px" 
                        }}
                    />
                    <Button
                        variant="contained"
                        sx={{
                            width: { xs: "100%", sm: "75%" },
                            marginTop: "20px",
                            fontWeight:"bold",
                            paddingY:"10px"
                        }}
                    >
                        Log In
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
                        Don't you have an account ? Click here to register
                    </Typography>

                </Box>
            </Paper>
        </Container>
    )
}

export default Login