import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    orders:[],
    loading:false
}

const BASIC_PATH = "http://localhost:8080/orders"



export const orderSlice = createSlice({
    name: 'order',
    initialState,
    reducers: {
      
    },
   
    
})

export const { } = orderSlice.actions

export default orderSlice.reducer