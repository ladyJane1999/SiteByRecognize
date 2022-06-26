import { createSlice } from '@reduxjs/toolkit';

const produstSlice = createSlice({
    name:'product',
    initialState:{
        products: [],
    },
    reducers: {
        addProduct(state, action) {
            state.products.push({
                id: action.payload.id,
                name: action.payload.name,

            })
        },
        toggleComplete(state, action) {
            const toggleProduct = state.products.find(product => product.id === action.payload.id)

        },
        removeProduct(state, action) {
            state.products.filter(product => product.id === action.payload.id)
        }
    }
})

export const {addProduct, toggleComplete, removeProduct} = produstSlice.actions;
export default produstSlice.reducer