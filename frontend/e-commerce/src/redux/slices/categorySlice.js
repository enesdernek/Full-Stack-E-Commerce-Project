import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    categories: null
}

const BASIC_PATH = "http://localhost:8080/categories"

export const getAllCategories = createAsyncThunk(
    'categories/getAll',
    async () => {
        const response = await axios.get(BASIC_PATH)
        return response.data
    }

)

export const categorySlice = createSlice({
    name: 'category',
    initialState,
    reducers: {
      
    },
    extraReducers: (builder) => {
        builder.addCase(getAllCategories.fulfilled, (state, action) => {
            state.categories = action.payload
        })
    }
})

export const { } = categorySlice.actions

export default categorySlice.reducer