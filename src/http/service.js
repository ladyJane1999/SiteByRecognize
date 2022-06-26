import {$authHost, $host} from "./index"


export const createUserWithEmailAndPassword = (username,password,file)=>{
    console.log(file)
    const data= $host.post('/register/by_finger',{username,password},{params:{file},
        headers: {
            'Content-Type': 'multipart/form-data'
        }}
       )
    return data
}
export const signInWithFinger = (file)=>{
    const data= $host.post('/login/by_figprint', file,{
        headers: {
            'Content-Type': 'multipart/form-data'
        }}
       )
    return data
}

export const addImage = (image) =>{
    const {data}=$authHost.post("/user",image,{
        headers: {
            'Content-Type': 'multipart/form-data'
        }})
    return data
}

export const fetchProduct = () =>{
    const {data}=$host.get("/product")
    return data
}

export const addProduct = (name) =>{
    const {data}=$authHost.post("/product",name)
    return data
}

export const changeProduct = (product) =>{
    const {data}=$authHost.put("/product",product)
    return data
}

export const removeProduct = (id) =>{
    const {data}=$authHost.delete("/product"+'/'+id)
    return data
}

