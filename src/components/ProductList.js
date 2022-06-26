import React, {useEffect, useState} from 'react';
import Container from "react-bootstrap/Container";
import {fetchProduct} from "../http/service";
import {Card, Row} from "react-bootstrap";
import academy_and_land from '../images/academy_and_land.jpg'

const ProductList = () => {

    const [products, setProducts] = useState([]);

    useEffect(()=>{
        fetchProduct().then(product=> setProducts(product)).catch(e=>e.response.message)
    },[])

    return (
        <Container>
            {products.map(product=>
                <Row className="e-card-content">
                    <Card key={product.id} className="e-card" style ={{width : 100  , cursor: 'pointer' }} border={"light"}>
                        <Card.Img variant="top" src={academy_and_land}/>
                        <div className="d-flex justify-content-between align-items-center ">
                            <div>{product.name}</div>
                        </div>
                        <div>{product.author}</div>
                    </Card>
                </Row>
            )}
        </Container>
    );
};

export default ProductList;