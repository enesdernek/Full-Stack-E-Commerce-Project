import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    productListBarHeader: "Tüm Ürünler"
}




export const appSlice = createSlice({
    name: 'app',
    initialState,
    reducers: {
      setProductListBarHeader:(state,action)=>{
              state.productListBarHeader = action.payload
      }
    },
   
})

export const {setProductListBarHeader } = appSlice.actions

export default appSlice.reducer