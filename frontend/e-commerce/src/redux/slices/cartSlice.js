import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    cartItems: [],
    cartItemQuantity: 0
}

const BASIC_PATH = "http://localhost:8080/cart"

export const addProductToCartByProductId = createAsyncThunk(
    'cart/addProductToCartByProductId',
    async ({ productId, token }) => {
        const response = await axios.post(
            `${BASIC_PATH}?productId=${productId}&quantity=1`,
            null,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data
    }
);

export const getAllCartItems = createAsyncThunk(
    'cart/getAllCartItems',
    async ({ token }) => {
        const response = await axios.get(
            `${BASIC_PATH}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data
    }
);

export const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {

    },
    extraReducers: (builder) => {
        builder.addCase(addProductToCartByProductId.fulfilled, (state, action) => {
            state.cartItems = action.payload.cartItemDtos
            state.cartItemQuantity = action.payload.cartItemDtos.length

        })
        builder.addCase(getAllCartItems.fulfilled, (state, action) => {
            state.cartItems = action.payload.cartItemDtos
            state.cartItemQuantity = action.payload.cartItemDtos.length
        })

    }

})

export const { } = cartSlice.actions

export default cartSlice.reducer