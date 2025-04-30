import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    products: [],
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

export const filterProductsByCategoryId = createAsyncThunk(
    'products/filterProductsByCategoryId',
    async ({categoryId,pageNo}) => {
        const response = await axios.get(BASIC_PATH+`/get-all-by-categoryId?categoryId=${categoryId}&pageNo=${pageNo}&pageSize=12`)
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
        builder.addCase(filterProductsByCategoryId.fulfilled, (state, action) => {
            if (action.meta.arg.pageNo === 1) {
              state.products = action.payload;
            } else {
              const newProducts = action.payload.filter(newProduct =>
                !state.products.some(existingProduct => existingProduct.productId === newProduct.productId)
              );
              state.products = [...state.products, ...newProducts];
            }
            state.loading = false;
          });
        builder.addCase(filterProductsByCategoryId.pending, (state) => {
            state.loading = true
        })
        builder.addCase(getAllProducts.rejected, (state) => {
            state.loading = false;
          })
          builder.addCase(filterProductsByCategoryId.rejected, (state) => {
            state.loading = false;
          })
    }
})

export const { } = productSlice.actions

export default productSlice.reducer