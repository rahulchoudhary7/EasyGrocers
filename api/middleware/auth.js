import jwt from 'jsonwebtoken';
import expressAsyncHandler from 'express-async-handler';
import { errorHandler } from '../utils/errorHandler.js';

export const isAuthenticated = expressAsyncHandler(async (req, res, next) => {

   const authHeader = req.headers['authorization'];

   if (!authHeader || !authHeader.startsWith('Bearer ')) {
      return next(errorHandler(401, 'Unauthorized, no access token'));
   }

   const token = authHeader.split(' ')[1]; 

   jwt.verify(token, process.env.JWT_SECRET, (error, user) => {
      if (error) {
         return next(errorHandler(401, 'Unauthorized, access token not valid'));
      }

      req.user = user;

      console.log(user)
      next();
   });
});
