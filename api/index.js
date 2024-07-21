import express from 'express'
import mongoose from 'mongoose'
import dotenv from 'dotenv'
import addressRoutes from './routes/address.routes.js'
import userRouter from './routes/user.route.js'
import cookieParser from 'cookie-parser'
import { errorMiddleWare } from './middleware.js/error.js'
import { connectdb } from './data/database.js'
const app = express()

dotenv.config()
const port = process.env.PORT || 9000
connectdb();


app.use(express.json())
app.use(cookieParser())

app.use('/api/address', addressRoutes)
app.use('/api/user',userRouter)
app.use(errorMiddleWare)

app.listen(port, () => {
   console.log(`Server is running on port ${port}`)
})