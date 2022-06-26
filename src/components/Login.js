import {useNavigate} from 'react-router';
import {signInWithFinger} from '../http/service';
import {Inputs} from './Inputs';
import {useDispatch} from "react-redux";
import {setUser} from "../store/userSlice";


const Login  = () => {
    const {} = useNavigate();
    const dispatch = useDispatch();

    const handleByLogin = async(file) => {
            signInWithFinger(file).then((user) => {
                console.log(file);
                dispatch(setUser({
                    email: user.data.user.username,
                    id:user.data.user.id
                }))
            })
            .catch((e)=> console.log(e.response.message))
    }

    return (
        <Inputs
            title="Sign in"
            handleClick={handleByLogin}
        />
    )
}

export {Login}

