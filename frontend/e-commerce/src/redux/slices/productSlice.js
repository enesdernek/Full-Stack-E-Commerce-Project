import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'
import axios from "axios"

const initialState = {
    products: [],
    product: null,
    categorizedProducts: [],
    loading: false,
    filteredProducts: [],
    campaignProducts:[]
}

const BASIC_PATH = "http://localhost:8080/products"

export const getAllProducts = createAsyncThunk(
    'products/getAll',
    async (pageNo) => {
        const response = await axios.get(BASIC_PATH + `/paged?pageNo=${pageNo}&pageSize=12`)
        return response.data
    }

)

export const getProductByProductId = createAsyncThunk(
    'products/getProductById',
    async (productId) => {
        const response = await axios.get(BASIC_PATH + `/${productId}`)
        return response.data
    }

)

export const filterProductsByCategoryId = createAsyncThunk(
    'products/filterProductsByCategoryId',
    async ({ categoryId, pageNo }) => {
        const response = await axios.get(BASIC_PATH + `/get-all-by-categoryId?categoryId=${categoryId}&pageNo=${pageNo}&pageSize=12`)

        return response.data
    }

)

export const filterProductsByPriceASC = createAsyncThunk(
    'products/filterProductsByPriceASC',
    async ({ pageNo }) => {
        const response = await axios.get(BASIC_PATH + `/sorted-by-price-asc?pageNo=${pageNo}&pageSize=12`)
        return response.data
    }

)

export const filterProductsByPriceDESC = createAsyncThunk(
    'products/filterProductsByPriceDESC',
    async ({ pageNo }) => {
        const response = await axios.get(BASIC_PATH + `/sorted-by-price-desc?pageNo=${pageNo}&pageSize=12`)
        return response.data
    }

)

export const getAllCampaignProducts = createAsyncThunk(
    'products/getAllCampaignProducts',
    async ({ pageNo }) => {
        const response = await axios.get(BASIC_PATH + `/get-all-discounted-products?pageNo=${pageNo}&pageSize=12`)
        return response.data
    }

)

export const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {
       clearFilteredProducts:(state)=>{
           state.filteredProducts = []
       }
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

        builder.addCase(filterProductsByCategoryId.fulfilled, (state, action) => {
            const { pageNo } = action.meta.arg;

            if (pageNo === 1) {
                state.categorizedProducts = action.payload;
            } else {
                const newProducts = action.payload.filter(newProduct =>
                    !state.categorizedProducts.some(existingProduct => existingProduct.productId === newProduct.productId)
                );
                state.categorizedProducts = [...state.categorizedProducts, ...newProducts];
            }

            state.loading = false;
        });

        builder.addCase(getAllProducts.pending, (state) => {
            state.loading = true
        })
        builder.addCase(filterProductsByCategoryId.pending, (state) => {
            state.loading = true
        })
        builder.addCase(getAllProducts.rejected, (state) => {
            state.loading = false;
        })
        builder.addCase(filterProductsByCategoryId.rejected, (state) => {
            state.loading = false;
        })
        builder.addCase(getProductByProductId.rejected, (state) => {
            state.loading = false;
        })
        builder.addCase(getProductByProductId.pending, (state) => {
            state.loading = true;
        })
        builder.addCase(getProductByProductId.fulfilled, (state, action) => {
            state.product = action.payload
            state.loading = false;
        })
        builder.addCase(filterProductsByPriceASC.fulfilled, (state, action) => {
            const { pageNo } = action.meta.arg;
            console.log(pageNo)

            if (pageNo === 1) {
                state.filteredProducts = action.payload;
            } else {
                const newProducts = action.payload.filter(newProduct =>
                    !state.filteredProducts.some(existingProduct => existingProduct.productId === newProduct.productId)
                );
                state.filteredProducts = [...state.filteredProducts, ...newProducts];
            }

            state.loading = false;
        });
        builder.addCase(filterProductsByPriceASC.rejected, (state) => {
            state.loading = false;
        })
        builder.addCase(filterProductsByPriceASC.pending, (state) => {
            state.loading = true;
        })
        builder.addCase(filterProductsByPriceDESC.fulfilled, (state, action) => {
            const { pageNo } = action.meta.arg;

            if (pageNo === 1) {
                state.filteredProducts = action.payload;
            } else {
                const newProducts = action.payload.filter(newProduct =>
                    !state.filteredProducts.some(existingProduct => existingProduct.productId === newProduct.productId)
                );
                state.filteredProducts = [...state.filteredProducts, ...newProducts];
            }

            state.loading = false;
        });
        builder.addCase(filterProductsByPriceDESC.rejected, (state) => {
            state.loading = false;
        })
        builder.addCase(filterProductsByPriceDESC.pending, (state) => {
            state.loading = true;
        })
        builder.addCase(getAllCampaignProducts.fulfilled, (state,action) => {
            const { pageNo } = action.meta.arg;

            if (pageNo === 1) {
                state.campaignProducts = action.payload;
            } else {
                const newProducts = action.payload.filter(newProduct =>
                    !state.campaignProducts.some(existingProduct => existingProduct.productId === newProduct.productId)
                );
                state.campaignProducts = [...state.campaignProducts, ...newProducts];
            }

            state.loading = false;
        })
        builder.addCase(getAllCampaignProducts.pending, (state) => {
            state.loading = true;
        })
        builder.addCase(getAllCampaignProducts.rejected, (state) => {
            state.loading = false;
        })

    }
})

export const { clearFilteredProducts} = productSlice.actions

export default productSlice.reducer