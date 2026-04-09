import { useState , useEffect} from "react";
import { Box, Button, Container, Stack, TextField, Typography } from "@mui/material";
import {useNavigate} from "react-router-dom";

const LoginPage = () => {
    const [credentials, setCredentials] = useState({
        username: "",
        password: ""
    });

    const navigate = useNavigate();

    const [JWT, setJWT] = useState("");

    useEffect(() => {
        if (JWT) {
            localStorage.setItem('JWT', JWT);
            navigate("/home");
        }
    }, [JWT, navigate]);
    const handleLogin = async () => {
        try{
            const response = await sendLoginReq(credentials);
            setJWT(response);
        } catch (error) {
            console.log(error);

        }

    };

    const sendLoginReq = async (credentials) => {
        const loginReq = await fetch('api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(credentials)
        });
        return await loginReq.text();
    };

    return (
        <Box
            sx={{
                height: '100vh',        // Full viewport height
                width: '100vw',         // Full viewport width
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center', // Centers vertically
                alignItems: 'center',     // Centers horizontally
                backgroundColor: 'gray'
            }}
        >
            <Container maxWidth="xs">
                <Stack spacing={3} sx={{ width: '100%' }}>

                    <Typography
                        variant="h4"
                        component="h1"
                        fontWeight="bold"
                        textAlign="center"
                        color="black"
                    >
                        LOGIN
                    </Typography>

                    <Box
                        sx={{
                            p: 4,
                            borderRadius: 3,
                            boxShadow: 3,
                            backgroundColor: 'background.paper',
                            display: 'flex',
                            flexDirection: 'column'
                        }}
                    >
                        <Stack spacing={2}>
                            <TextField
                                label='Username'
                                fullWidth
                                value={credentials.username}
                                onChange={(e) => setCredentials({ ...credentials, username: e.target.value })}
                            />

                            <TextField
                                label='Password'
                                type="password"
                                fullWidth
                                value={credentials.password}
                                onChange={(e) => setCredentials({ ...credentials, password: e.target.value })}
                            />

                            <Button
                                variant="contained"
                                onClick={handleLogin}
                                size="large"
                            >
                                Submit
                            </Button>
                        </Stack>
                    </Box>
                </Stack>
            </Container>
        </Box>
    );
};

export default LoginPage;