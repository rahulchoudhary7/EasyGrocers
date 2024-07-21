import express from 'express'
import mongoose from 'mongoose'
import dotenv from 'dotenv'
import addressRoutes from './routes/address.routes.js'
const app = express()

dotenv.config()
const port = process.env.PORT || 5000

try {
   await mongoose.connect(process.env.MONGODB_CONNECTION_STRING)
   console.log('Database connected successfully')
} catch (error) {
   console.log(error.message)
}

app.use(express.json())
app.listen(port, () => {
   console.log(`Server is running on port ${port}`)
})

app.use('/api/address', addressRoutes)
