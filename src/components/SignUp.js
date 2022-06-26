import React from 'react';
import {Container} from "react-bootstrap";
import {Inputs} from "./Inputs";
import { createUserWithEmailAndPassword} from '../http/service';

const SignUp =  () => {

    const handleRegister = (username,password,file) => {
        console.log(file)
         const newUser = {
             username:username,
             password:password,
             file,
         }
         const newForm = new FormData()
        newForm.append('username',username)
        newForm.append('password',password)
        newForm.append('file',file)
        createUserWithEmailAndPassword(username,password,file)
    }

    return (
        <Container>
            <Inputs
                title="Sign up"
                handleClick={handleRegister}
            />
        </Container>
    );
};

export default SignUp;