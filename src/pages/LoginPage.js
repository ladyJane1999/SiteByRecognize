import React from 'react';
import {Login} from '../components/Login'
import { Link } from 'react-router-dom';
import {Container} from "react-bootstrap";
import NavBar from "../components/NavBar";

const LoginPage = () => {
    return (
        <Container>
            <NavBar/>
             <Login />
        </Container>
    );
};

export default LoginPage;