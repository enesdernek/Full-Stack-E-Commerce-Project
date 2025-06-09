import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
  productListBarHeader: "Tüm Ürünler",
  snackbarOpen: false,
  snackbarMessage: "",
  snackbarSeverity:"",
}




export const appSlice = createSlice({
  name: 'app',
  initialState,
  reducers: {
    setProductListBarHeader: (state, action) => {
      state.productListBarHeader = action.payload
    },
    openSnackbar: (state, action) => {
      state.snackbarOpen = true;
      state.snackbarMessage = action.payload.message
      state.snackbarSeverity = action.payload.severity
      console.log(state.snackbarMessage)
    },
    closeSnackbar: (state) => {
      state.snackbarOpen = false;
      state.snackbarMessage = "";
    },
  },

})

export const { setProductListBarHeader,openSnackbar,closeSnackbar } = appSlice.actions

export default appSlice.reducer