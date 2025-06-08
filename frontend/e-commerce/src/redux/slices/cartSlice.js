import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    cartItems: [],
    cartItemQuantity: 0,
    cartTotalPrice: 0
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

export const removeItemFromCartByCartItemId = createAsyncThunk(
    'cart/removeItemFromCartByCartItemId',
    async ({ token, cartItemId }) => {
        const response = await axios.put(
            `${BASIC_PATH}/change-item-quantity?cartItemId=${cartItemId}&quantity=0`, {},
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data
    }
);

export const increaseItemQuantityByCartItemId = createAsyncThunk(
    'cart/increaseItemQuantityByCartItemId',
    async ({ token, cartItemId, quantity }) => {
        console.log(token, cartItemId, quantity)
        if (quantity >= 0 && quantity <= 10) {
            const response = await axios.put(
                `${BASIC_PATH}/change-item-quantity?cartItemId=${cartItemId}&quantity=${quantity + 1}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            return response.data;
        }
        return null;
    }
);

export const decreaseItemQuantityByCartItemId = createAsyncThunk(
    'cart/decreaseItemQuantityByCartItemId',
    async ({ token, cartItemId, quantity }) => {
        console.log(token, cartItemId, quantity)
        if (quantity >= 0 && quantity <= 10) {
            const response = await axios.put(
                `${BASIC_PATH}/change-item-quantity?cartItemId=${cartItemId}&quantity=${quantity - 1}`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            return response.data;
        }
        return null;
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
            state.cartTotalPrice = state.cartItems.reduce((total, item) => {
                return total + (item.productDto.price * item.quantity);
            }, 0);
        })
        builder.addCase(getAllCartItems.fulfilled, (state, action) => {
            state.cartItems = action.payload.cartItemDtos
            state.cartItemQuantity = action.payload.cartItemDtos.length
            state.cartTotalPrice = state.cartItems.reduce((total, item) => {
                return total + (item.productDto.price * item.quantity);
            }, 0);
        })
        builder.addCase(removeItemFromCartByCartItemId.fulfilled, (state, action) => {
            state.cartItems = action.payload.cartItemDtos
            state.cartItemQuantity = action.payload.cartItemDtos.length
            state.cartTotalPrice = state.cartItems.reduce((total, item) => {
                return total + (item.productDto.price * item.quantity);
            }, 0);
        })
        builder.addCase(increaseItemQuantityByCartItemId.fulfilled, (state, action) => {
            state.cartItems = action.payload.cartItemDtos
            state.cartItemQuantity = action.payload.cartItemDtos.length
            state.cartTotalPrice = state.cartItems.reduce((total, item) => {
                return total + (item.productDto.price * item.quantity);
            }, 0);
        })
        builder.addCase(decreaseItemQuantityByCartItemId.fulfilled, (state, action) => {
            state.cartItems = action.payload.cartItemDtos
            state.cartItemQuantity = action.payload.cartItemDtos.length
            state.cartTotalPrice = state.cartItems.reduce((total, item) => {
                return total + (item.productDto.price * item.quantity);
            }, 0);
        })

    }

})

export const { } = cartSlice.actions

export default cartSlice.reducer