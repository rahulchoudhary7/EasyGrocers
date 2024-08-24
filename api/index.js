import express from 'express'
import dotenv from 'dotenv'
import userRouter from './routes/user.route.js'
import cookieParser from 'cookie-parser'
import swaggerUi from 'swagger-ui-express'
import { errorMiddleWare } from './middleware/error.js'
import { connectdb } from './data/database.js'
import sellerRouter from './routes/seller.route.js'
import inventoryItemRouter from './routes/inventoryitem.route.js'
import categoryRouter from './routes/category.route.js'
import swaggerDocument from './swagger-output.json' with { type: "json" };

const app = express()

dotenv.config()
const port = process.env.PORT || 9000
connectdb()

app.use(express.json())
app.use(cookieParser())

app.use('/api/user', userRouter)
app.use('/api/seller', sellerRouter)
app.use('/api/inventoryItem', inventoryItemRouter)
app.use('/api/categories', categoryRouter)
app.use(errorMiddleWare)

app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument))

app.listen(port, () => {
   console.log(`Server is running on port ${port}`)
})
