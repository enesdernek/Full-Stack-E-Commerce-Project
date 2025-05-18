import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    user: null,
    token: null,
    loading: false
}

const BASIC_PATH = "http://localhost:8080/users"

export const register = createAsyncThunk(
    'user/register',
    async (body) => {
        const response = await axios.post(BASIC_PATH + "/register",body)
        return response.data
    }

)

export const authenticate = createAsyncThunk(
    'user/authenticate',
    async () => {
        const response = await axios.post(BASIC_PATH + "/authenticate")
        return response.data
    }

)

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
         logOut:(state)=>[
            state.user=null,
            state.token = null
         ]
    },
    extraReducers: (builder) => {
        builder.addCase(register.fulfilled, (state, action) => {
            state.loading = false
        })
        builder.addCase(register.pending, (state, action) => {
            state.loading = true
        })
        builder.addCase(register.rejected, (state, action) => {
            state.loading = false
        })
        builder.addCase(authenticate.fulfilled, (state, action) => {
            state.user = action.payload.userDto
            state.token = action.payload.token
            state.loading=false
        })
        builder.addCase(authenticate.pending, (state, action) => {
            state.loading = true
        })
        builder.addCase(register.rejected, (state, action) => {
            state.loading = false
        })
    }
})

export const { } = userSlice.actions

export default userSlice.reducer