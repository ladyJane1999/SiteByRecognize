import {useSelector} from 'react-redux';

export function useAuth (){
    const {username,token,id}= useSelector(state=>state.user)
    return {
        isAuth : !username,
        username,
        token,
        id
    }
}