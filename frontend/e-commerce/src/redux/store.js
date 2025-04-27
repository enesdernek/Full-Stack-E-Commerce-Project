import { combineReducers, configureStore } from '@reduxjs/toolkit'
import { persistStore, persistReducer } from "redux-persist"
import storage from "redux-persist/lib/storage"
import productSlice from './slices/productSlice'
import categorySlice from './slices/categorySlice'

const rootReducer = combineReducers({
    product: productSlice,
    category: categorySlice
})

const persistConfig = {
    key: "root",
    storage,
    version: 1,
    blacklist: [""]
}

const persistedReducer = persistReducer(persistConfig, rootReducer)

export const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleWare) => getDefaultMiddleWare({
        serializableCheck: false
    })
})

export const persistor = persistStore(store)
