import React, {useEffect, useState} from 'react';
import Container from "react-bootstrap/Container";
import {addImage, signInWithFinger} from "../http/service";
import {Button} from "react-bootstrap";

const UserAccount = () => {

    const [image,setImage]= useState(null)

    const selectImage = e => {
        console.log(e.target.files[0])
        setImage(e.target.files[0])
    }

    const formData = new FormData()
    formData.append('image', image)

    useEffect(() => {

        addImage().then(data => setImage(data))

    }, [])

    const handleByImage = () => {
        addImage(image)
            .then(file=> {
                console.log(file)
            })
            .catch((e)=> console.log(e.response.message))
    }

    return (
        <Container>
            <input
                className="mt-3"
                type="file"
                onChange={selectImage}
            />
            <Button onClick={handleByImage}>Add</Button>
        </Container>
    );
};

export default UserAccount;