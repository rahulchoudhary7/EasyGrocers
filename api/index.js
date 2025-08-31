import express from 'express'
import dotenv from 'dotenv'
import userRouter from './routes/user.route.js'
import cookieParser from 'cookie-parser'
import swaggerUi from 'swagger-ui-express'
import { errorMiddleWare } from './middleware/error.js'
import { connectdb } from './data/database.js'
import sellerRouter from './routes/seller.route.js'
import swaggerDocument from './swagger-output.json' with { type: "json" };
import userAuthRouter from './routes/user.auth.route.js'
import sellerAuthRouter from './routes/seller.auth.route.js'
import { registerEureka } from './EurekaConfig.js'
import adminRouter from './routes/admin.route.js'
import adminAuthRouter from './routes/admin.auth.route.js'

const app = express()

dotenv.config()
const port = process.env.PORT || 9000
connectdb()

registerEureka()



app.use(express.json())
app.use(cookieParser())


app.use('/api/v1/auth/user', userAuthRouter)
app.use('/api/v1/auth/seller', sellerAuthRouter)
app.use('/api/v1/user', userRouter)
app.use('/api/v1/seller', sellerRouter)
app.use('/api/v1/auth/admin', adminAuthRouter)
app.use('/api/v1/admin', adminRouter)

app.use(errorMiddleWare)

app.use('/api/v1/public/auth/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument))

app.listen(port, () => {
   console.log(`Server is running on port ${port}`)
})
