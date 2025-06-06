import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    user: null,
    token: null,
    loading: false,
    favoritedProducts:[]
}

const BASIC_PATH = "http://localhost:8080/users"

export const getUser = createAsyncThunk(
  'user/getUser',
  async (token) => {
    const response = await axios.get(`${BASIC_PATH}/me`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  }
);

export const register = createAsyncThunk(
    'user/register',
    async (body) => {
        const response = await axios.post(BASIC_PATH + "/register", body)
        return response.data
    }

)

export const authenticate = createAsyncThunk(
    'user/authenticate',
    async (body) => {
        const response = await axios.post(BASIC_PATH + "/authenticate", body)
        return response.data
    }

)

export const addProductToFavoritedListByProductId = createAsyncThunk(
    'user/addProductToFavoritedListByProductId',
    async ({ token, productId }) => {
        await axios.post(
            `${BASIC_PATH}/add-product-to-favoriteds-list`,
            null,
            {
                params: { productId },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
    }
);

export const removeProductFromFavoritedListByProductId = createAsyncThunk(
    'user/removeProductFromFavoritedListByProductId',
    async ({ token, productId }) => {
        await axios.delete(
            `${BASIC_PATH}/delete-product-from-favoriteds-list`,
            {
                params: { productId },
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
    }
);


export const getFavoritedProducts = createAsyncThunk(
    'user/getFavoritedProducts',
    async ({ token,pageNo}) => {
        const response = await axios.get(
            `${BASIC_PATH}/favorited-products?pageNo=${pageNo}&pageSize=12`,
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data
    }
);

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        logOut: (state) => {
            state.user = null,
                state.token = null
        }
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
            state.loading = false
        })
        builder.addCase(authenticate.pending, (state, action) => {
            state.loading = true
        })
        builder.addCase(authenticate.rejected, (state, action) => {
            state.loading = false
        })
        builder.addCase(getUser.fulfilled, (state, action) => {
            state.user = action.payload
        })
        builder.addCase(getFavoritedProducts.fulfilled, (state, action) => {
            const { pageNo } = action.meta.arg;

            if (pageNo === 1) {
                state.favoritedProducts = action.payload;
            } else {
                const newProducts = action.payload.filter(newProduct =>
                    !state.favoritedProducts.some(existingProduct => existingProduct.productId === newProduct.productId)
                );
                state.favoritedProducts = [...state.favoritedProducts, ...newProducts];
            }

            state.loading = false;
        })

    }
})

export const { logOut } = userSlice.actions

export default userSlice.reducer