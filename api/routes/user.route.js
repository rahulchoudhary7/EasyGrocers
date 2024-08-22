import express from 'express'
import {
   addAddress,
   deleteAddress,
   getAddress,
   login,
   updateAddress,
} from '../controller/user.controller.js'
import { logout } from '../controller/user.controller.js'
import { register } from '../controller/user.controller.js'
import { isAuthenticated } from '../middleware/auth.js'
const userRouter = express.Router()

userRouter.post('/register', register)
userRouter.post('/login', login)
userRouter.get('/logout', logout)
userRouter.get('/:userId/addresses', isAuthenticated, getAddress)
userRouter.post('/:userId/addresses', isAuthenticated, addAddress)
userRouter.put('/:userId/addresses/:addressId', isAuthenticated, updateAddress)
userRouter.delete(
   '/:userId/addresses/:addressId',
   isAuthenticated,
   deleteAddress,
)

export default userRouter
