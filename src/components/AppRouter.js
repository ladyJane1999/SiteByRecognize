import { Route, Routes} from "react-router";
import {
    PRODUCT_ROUTE,
    REGISTRATION_ROUTE,
    LOGIN_ROUTE, USER_ROUTE,

} from "../utils/consts";
import ProductPage from "../pages/ProductPage";
import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import UserAccountPage from "../pages/UserAccountPage";


const AppRouter = ()=> {

    return (
        <Routes>
            <Route path={PRODUCT_ROUTE} element= {<ProductPage/>} />
            <Route path={LOGIN_ROUTE} element= {<LoginPage/>} />
            <Route path={REGISTRATION_ROUTE} element= {<RegisterPage/>} />
            <Route path={USER_ROUTE} element= {<UserAccountPage/>} />
        </Routes>
    )
};

export default AppRouter;