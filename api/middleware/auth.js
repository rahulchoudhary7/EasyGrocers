import jwt from 'jsonwebtoken'
import User from '../models/user.model.js'
import expressAsyncHandler from 'express-async-handler'
import { errorHandler } from '../utils/errorHandler.js'
export const isAuthenticated = expressAsyncHandler(async (req, res, next) => {
   const { token } = req.cookies

   if (!token) return next(errorHandler(401, 'Unauthorized, no access token'))

   jwt.verify(token, process.env.JWT_SECRET, (error, user) => {
      if (error) {
         return next(errorHandler(401, 'Unauthorized, access token not valid'))
      }

      req.user = user
      next()
   })
})
