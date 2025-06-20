import { combineReducers, configureStore } from '@reduxjs/toolkit'
import { persistStore, persistReducer } from "redux-persist"
import storage from "redux-persist/lib/storage"
import productSlice from './slices/productSlice'
import categorySlice from './slices/categorySlice'
import userSlice from './slices/userSlice'
import appSlice from './slices/appSlice'
import cartSlice from './slices/cartSlice'
import orderSlice from './slices/orderSlice'

const rootReducer = combineReducers({
    product: productSlice,
    category: categorySlice,
    user: userSlice,
    app: appSlice,
    cart: cartSlice,
    order: orderSlice
})

const persistConfig = {
    key: "root",
    storage,
    version: 1,
    blacklist: ["product", "app", "cart","order","category","order"]
}

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleWare) => getDefaultMiddleWare({
        serializableCheck: false
    })
})

export const persistor = persistStore(store)
