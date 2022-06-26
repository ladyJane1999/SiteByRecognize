import React from 'react';
import {useAuth} from "../hooks/use-auth";
import {Button, Container} from "react-bootstrap";
import NavBar from "../components/NavBar";
import ProductList from "../components/ProductList";


const UserAccountPage = () => {
    const { email} = useAuth();

    return (
        <Container>
            <NavBar/>
            <ProductList/>
            <Button>Log out from {email}</Button>
        </Container>
    )
};

export default UserAccountPage;