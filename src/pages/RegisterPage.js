import  SignUp  from '../components/SignUp';
import {Container} from "react-bootstrap";
import NavBar from "../components/NavBar";


const RegisterPage = () => {

    return (
        <Container>
            <NavBar/>
            <h1>Register</h1>
            <SignUp />

        </Container>
    )
}

export default RegisterPage