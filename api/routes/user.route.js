import express from 'express'
import {
   addAddress,
   deleteAddress,
   getAddress,
   login,
   updateAddress,
} from '../controller/user.controller.js'

import { isAuthenticated } from '../middleware/auth.js'
const userRouter = express.Router()


userRouter.get('/:userId/addresses',  getAddress)
userRouter.post('/:userId/addresses', addAddress)
userRouter.put('/:userId/addresses/:addressId',  updateAddress)
userRouter.delete(
   '/:userId/addresses/:addressId',
   isAuthenticated,
   deleteAddress,
)

export default userRouter
