import React from 'react'
import cities from "../data/city.json"
import districts from "../data/district.json"
import * as yup from "yup"
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';

const validationSchema = yup.object({
    phoneNumber: yup
        .string()
        .required('Phone number is required')
        .length(10, 'Phone number must be exactly 10 digits')
        .matches(/^\d+$/, 'Phone number must contain only digits'),
    city: yup
        .string()
        .required('city is required'),
    district: yup
        .string()
        .required('district is required'),
    deliveryAdress: yup.string("Enter your username")
        .required("delivery adress is required")
        .min(8, "Username has to contain 3 characters at least.")
        .max(256, "Username can't has more than 18 characters.")
});

function Order() {

    const navigate = useNavigate()
    const dispatch = useDispatch()
    const user = useSelector((state) => state.user.user)
    const token = useSelector((state) => state.user.token)

    const formik = useFormik({
        initialValues: {
            phoneNumber: "",
            city: "",
            district:"",
            deliveryAdress:""
        },
        validationSchema: validationSchema,
        onSubmit: ((values) => {
            const body = {
                phoneNumber: values.phoneNumber,
                city: values.city,
                district: values.district,
                deliveryAdress: values.deliveryAdress,
                totalPrice:0
            }
            dispatch(authenticate(body))
        })
    })
    return (
        <></>
    )
}

export default Order