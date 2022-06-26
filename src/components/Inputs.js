import React, {AriaAttributes, DOMAttributes, FC, useState} from 'react';
import '../css/login.css';
import {Button, Container, Form} from "react-bootstrap";
import {Link} from "react-router-dom";

  const Inputs  = ({title, handleClick}) => {
    const [username, setUsername] = useState('');
    const [pass, setPass] = useState('');
    const [file, setFile] = useState(null)

    const selectFile = (e ) => {
        console.log(e.target.files[0])
        setFile(e.target.files[0])
    }

        const formData = new FormData()
        formData.append('file', file)

    return (
        <Container className="Login" >
        <Form  className="card shadow bg-white rounded ">
            {title == 'Sign up' ?
                <><b>
                    <strong>Регистрация</strong>
                </b><Form.Control
                    className="card-body pt-1 "
                    type="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="email"/><Form.Control
                    className="Login-inputs "
                    type="password"
                    value={pass}
                    onChange={(e) => setPass(e.target.value)}
                    placeholder="password"/>
                    <input
                        className="mt-3"
                        type="file"
                        onChange={selectFile}/>
                    <Button
                        className="Login-inputs "
                        onClick={() => handleClick(username,pass,formData)}
                    >
                        {title}
                    </Button>
                    <p>
                        Already have an account? <Link to="/login" >Sign in</Link>
                    </p>
                </>
                :
                <><b>
                    <strong>Авторизация</strong>
                </b><input
                    className="mt-3"
                    type="file"
                    onChange={selectFile}/>
                    <Button
                        onClick={() => handleClick(formData)}
                    >
                        {title}
                    </Button>
                    <p>
                        No account? <Link to="/registration" >Sign in</Link>
                    </p>
                </>
            }

        </Form>
        </Container>
    )
}

export {Inputs}