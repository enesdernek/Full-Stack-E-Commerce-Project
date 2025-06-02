import { Box, Container, Typography } from '@mui/material'
import React from 'react'
import { useSelector } from "react-redux"
import FavoriteIcon from '@mui/icons-material/Favorite';
import FeaturedPlayListIcon from '@mui/icons-material/FeaturedPlayList';
import MiscellaneousServicesIcon from '@mui/icons-material/MiscellaneousServices';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

function Profile() {
    const user = useSelector((state) => state.user.user)


    return (
        <Container>
            <Box sx={{
                textAlign: "center",
                justifyContent: "center",
                backgroundColor: "#E3F2FD",
                borderRadius: "10px",
                marginTop: "20px",
                padding: "15px",
                display: "flex",
                gap: "10px"
            }
            }>
                <AccountCircleIcon sx={{ fontSize: "40px" }} />
                <Typography variant="h4" sx={{ fontWeight: "bold", color: "#333" }}>

                    Hoş geldiniz
                    {
                        user && " " + user.username
                    }
                </Typography>
            </Box>
            <Box sx={{
                display: "flex", flexWrap: "wrap", direction: "row", marginTop: "30px", gap: "20px", alignContent: "center", justifyContent: "center"
            }}>
                <Box  sx={{
                    alignContent: "center", justifyContent: "center", textAlign: "center", backgroundColor: "#C97C7C", padding: "40px", borderRadius: "20px", width: "290px",
                    transition: "all 0.3s ease-in-out",
                    boxShadow: "0px 4px 10px rgba(0,0,0,0.1)",
                    "&:hover": {
                        transform: "scale(1.05)",
                        boxShadow: "0px 8px 20px rgba(0,0,0,0.2)",
                        cursor: "pointer",
                    },
                }}>
                    <FavoriteIcon sx={{ fontSize: "100px", color: "#E63946" }} />
                    <Typography sx={{ color: "white" }} variant='h5'>
                        Beğendiğim Ürünler
                    </Typography>
                </Box>
                <Box sx={{
                    alignContent: "center", justifyContent: "center", textAlign: "center", backgroundColor: "blue", padding: "40px", borderRadius: "20px", width: "290px",
                    transition: "all 0.3s ease-in-out",
                    boxShadow: "0px 4px 10px rgba(0,0,0,0.1)",
                    "&:hover": {
                        transform: "scale(1.05)",
                        boxShadow: "0px 8px 20px rgba(0,0,0,0.2)",
                        cursor: "pointer",
                    },
                }}>
                    <FeaturedPlayListIcon sx={{ fontSize: "100px", color: "#A9D6E5" }} />
                    <Typography sx={{ color: "white" }} variant='h5'>
                        Siparişlerim
                    </Typography>
                </Box>
                <Box sx={{
                    alignContent: "center", justifyContent: "center", textAlign: "center", backgroundColor: "#B0B0B0", padding: "40px", borderRadius: "20px", width: "290px",
                    transition: "all 0.3s ease-in-out",
                    boxShadow: "0px 4px 10px rgba(0,0,0,0.1)",
                    "&:hover": {
                        transform: "scale(1.05)",
                        boxShadow: "0px 8px 20px rgba(0,0,0,0.2)",
                        cursor: "pointer",
                    },
                }}>
                    <MiscellaneousServicesIcon sx={{ fontSize: "100px", color: "white" }} />
                    <Typography sx={{ color: "white" }} variant='h5'>
                        Kullanıcı Ayarları
                    </Typography>
                </Box>
            </Box>
        </Container>
    )
}

export default Profile