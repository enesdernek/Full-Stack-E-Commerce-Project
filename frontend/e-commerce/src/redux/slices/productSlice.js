import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    products: null,
    loading:false
}

const BASIC_PATH = "http://localhost:8080/products"

export const getAllProducts = createAsyncThunk(
    'products/getAll',
    async (pageNo) => {
        const response = await axios.get(BASIC_PATH+`/paged?pageNo=${pageNo}&pageSize=12`)
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
            if (state.products) {
                const newProducts = action.payload.filter(newProduct => 
                    !state.products.some(existingProduct => existingProduct.productId === newProduct.productId)
                );
                state.products = [...state.products, ...newProducts];
                state.loading = false
            } else {
                state.products = action.payload;
                state.loading = false
            }
        })
        builder.addCase(getAllProducts.pending, (state) => {
            state.loading = true
        })
    }
})

export const { } = productSlice.actions

export default productSlice.reducer