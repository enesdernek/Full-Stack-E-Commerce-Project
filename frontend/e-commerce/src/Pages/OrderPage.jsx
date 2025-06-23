import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useNavigate, useParams } from 'react-router-dom'
import { getByOrderId } from '../redux/slices/orderSlice'

function OrderPage() {

    const {orderId} = useParams()
    const dispatch = useDispatch()
    const navigate = useNavigate()
    const order = useSelector((state)=>state.order.order)
    const token = useSelector((state)=>state.user.token)
    
    useEffect(()=>{
       dispatch(getByOrderId({token,orderId}))
    },[])

    useEffect(()=>{
      console.log(order)
    },[order])

  return (
    <div>OrderPage</div>
  )
}

export default OrderPage