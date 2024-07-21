import User from '../models/user.model.js'
import { errorHandler } from '../utils/errorHandler.js'
import asyncHandler from 'express-async-handler'
import bcryptjs from 'bcryptjs'
import { sendCookie } from '../utils/sendCookie.js'
export const register = asyncHandler(async(req, res, next) => {
   try {
      
      const user = req.body
      if (!user) {
         return next(errorHandler(400, 'User not found'))
      }
      if (!user.password) {
         return next(errorHandler(400, 'Password not found'))
      }

      const hashPassword = bcryptjs.hashSync(user.password, 10)

      user.password = hashPassword
      const newUser = await User.create(user)
      if(!newUser){
        return next(errorHandler(500, 'Internal Server error'))
    }
    sendCookie(newUser,res,"created successfully")

    console.log(newUser);
   } catch (error) {
    next(error);
   }
})

export const login=asyncHandler(async(req,res,next)=>{
    try {
        const {email,password}=req.body;
         if(!email || !password){
          return  next(errorHandler(404,"Not found"))
         }
         const user= await User.findOne({email:email}).select("+password");
         if(!user){
           return next(errorHandler(403,"Invalid Email or Password"))
         }
         const isMatch = await bcryptjs.compare(password, user.password);
          if(!isMatch){
            return next(errorHandler(403, "Password"));
        }
          sendCookie(user,res, `Welcome back, ${user.name}`, 200);

    } catch (error) {
        next(error);
    }
})
export const logout = (req, res) => {
    res
      .status(200)
      .cookie("token", "", {
        expires: new Date(Date.now()),
      })
      .json({
        success: true,
        user: req.user,
      });
  };
  export const getUser = (req, res) => {
    res.status(200).json({
      success: true,
      user: req.user,
    });
  };