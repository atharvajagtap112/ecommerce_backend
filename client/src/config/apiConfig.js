import axios from "axios"

export const API_BASE_URL ="https://ecommerce-backend-5vxu.onrender.com"

const jwt=localStorage.getItem("jwt")

export const api=axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Authorization": `Bearer ${jwt}`,
        "Content-Type": "application/json",
     },
})