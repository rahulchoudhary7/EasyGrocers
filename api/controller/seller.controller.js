import Seller from '../models/seller.model.js'
import { errorHandler } from '../utils/errorHandler.js'
import asyncHandler from 'express-async-handler'
import bcryptjs from 'bcryptjs'
import jwt from 'jsonwebtoken'

export const registerSeller = asyncHandler(async (req, res, next) => {
   const seller = req.body
   if (!seller) {
      return next(errorHandler(400, 'Seller data not provided'))
   }
   if (!seller.password) {
      return next(errorHandler(400, 'Password not provided'))
   }

   const hashPassword = bcryptjs.hashSync(seller.password, 10)
   seller.password = hashPassword

   const newSeller = await Seller.create(seller)
   if (!newSeller) {
      return next(errorHandler(500, 'Internal Server error'))
   }

   const token = jwt.sign(
      { id: newSeller._id, userType: newSeller.userType },
      process.env.JWT_SECRET,
      {
         expiresIn: '60m',
      },
   )

   const sellerWithoutPassword = newSeller.toObject()
   delete sellerWithoutPassword.password

   res.status(201).set('Authorization', `Bearer ${token}`).json({
      success: true,
      message: 'Seller registered successfully',
      seller: sellerWithoutPassword,
   })

   console.log(newSeller)
})

export const loginSeller = asyncHandler(async (req, res, next) => {
   const { email, password } = req.body
   if (!email || !password) {
      return next(errorHandler(400, 'Email and password are required'))
   }

   const seller = await Seller.findOne({ email: email }).select('+password')
   if (!seller) {
      return next(errorHandler(403, 'Invalid Email or Password'))
   }

   const isMatch = await bcryptjs.compare(password, seller.password)
   if (!isMatch) {
      return next(errorHandler(403, 'Invalid Email or Password'))
   }

   const sellerWithoutPassword = seller.toObject()
   delete sellerWithoutPassword.password

   const token = jwt.sign(
      { id: seller._id, userType: seller.userType },
      process.env.JWT_SECRET,
      {
         expiresIn: '60m',
      },
   )

   res.status(200)
      .set('Authorization', `Bearer ${token}`)
      .json({
         success: true,
         message: `Welcome back, ${seller.sellerName}`,
         seller: sellerWithoutPassword,
      })
})

export const logoutSeller = (req, res) => {
   res.status(200).json({
      success: true,
      message: 'Logged out successfully',
   })
}

export const sellerByCategory = asyncHandler(async (req, res) => {
   const page = parseInt(req.query.page) || 1
   const limit = parseInt(req.query.limit) || 10
   const skip = (page - 1) * limit

   const sellers = await Seller.find({ categories: req.params.category })
      .select('sellerName shopName image rating')
      .skip(skip)
      .limit(limit)
   const total = await Seller.countDocuments({
      categories: req.params.category,
   })
   res.json({
      sellers,
      totalPages: Math.ceil(total / limit),
      currentPage: page,
   })
})
