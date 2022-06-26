
import { useAuth } from '../hooks/use-auth';
import ProductList from '../components/ProductList'

import React from 'react';
import {Button, Container} from "react-bootstrap";
import NavBar from "../components/NavBar";
import {NavLink} from "react-router-dom";

const ProductPage = () => {

    const {isAuth, email} = useAuth();

    return isAuth ? (
        <Container>
            <NavBar/>
            <ProductList/>
            <Button>Log out from {email}</Button>
        </Container>
    ) : (
        <NavLink to="/login" />
    )
};

export default ProductPage;