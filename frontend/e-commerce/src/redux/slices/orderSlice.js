import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    orderList: [],
    order:null,
    loading: false
}

const BASIC_PATH = "http://localhost:8080/orders"

export const createOrder = createAsyncThunk(
    'order/createOrder',
    async ({ token, body }) => {
        const response = await axios.post(
            `${BASIC_PATH}`, body,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data
    }
);

export const getByOrderId = createAsyncThunk(
    'order/getByOrderId',
    async ({ token,orderId }) => {
        const response = await axios.get(
            `${BASIC_PATH}/${orderId}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data
    }
);

export const getAllOrders = createAsyncThunk(
    'order/getAllOrders',
    async ({ token }) => {
        const response = await axios.get(
            `${BASIC_PATH}`, {},
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data
    }
);

export const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {

    },
    extraReducers: (builder) => {
        builder.addCase(getAllOrders.fulfilled, (state, action) => {
            state.orderList = action.payload
        })
        builder.addCase(getByOrderId.fulfilled, (state, action) => {
            state.order = action.payload
        })
       
    }

})

export const { } = orderSlice.actions

export default orderSlice.reducer