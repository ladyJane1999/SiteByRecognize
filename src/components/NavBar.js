import React from 'react';
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import {PRODUCT_ROUTE, REGISTRATION_ROUTE, LOGIN_ROUTE, USER_ROUTE} from "../utils/consts";
import '../css/navbar.css';

import Container from "react-bootstrap/Container";
const NavBar = () => {

    return (
        <Navbar  className=" navbar-custom navbar navbar-expand-md  fixed-top">
            <Container >
                <Nav className="navbar-nav mr-auto">
                    <Nav.Link className="nav-item" href={PRODUCT_ROUTE}>Главная</Nav.Link>
                    <Nav.Link href={REGISTRATION_ROUTE}>Регистрация</Nav.Link>
                    <Nav.Link href={LOGIN_ROUTE}>Авторизация</Nav.Link>
                    <Nav.Link href={USER_ROUTE}>Аккаунт пользователя</Nav.Link>
                </Nav>
            </Container>
        </Navbar>
    );
};

export default NavBar;