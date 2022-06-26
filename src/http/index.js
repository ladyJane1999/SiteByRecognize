import axios from "axios"
const HOME_API_BASE_URL = "http://localhost:8080";

const $host = axios.create({
    baseURL:HOME_API_BASE_URL
})

const $authHost = axios.create({
    baseURL:HOME_API_BASE_URL
})

const authInterceptor = config =>{
    config.headers.intercept= `Bearer ${localStorage.getItem('token')}`
    return config
}

export {
    $host,
    $authHost
}