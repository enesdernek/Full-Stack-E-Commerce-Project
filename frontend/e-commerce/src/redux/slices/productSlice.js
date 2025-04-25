import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    products: null
}

const BASIC_PRODUCT_PATH = "http://localhost:8080/products"

export const getAllProducts = createAsyncThunk(
    'products/getAll',
    async () => {
        const response = await axios.get(BASIC_PRODUCT_PATH)
        return response.data
    }

)

export const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {
      
    },
    extraReducers: (builder) => {
        builder.addCase(getAllProducts.fulfilled, (state, action) => {
            state.products = action.payload
        })
    }
})

export const { } = productSlice.actions

export default productSlice.reducer