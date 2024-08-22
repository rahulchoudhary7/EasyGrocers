import User from '../models/user.model.js'
import { errorHandler } from '../utils/errorHandler.js'
import asyncHandler from 'express-async-handler'
import bcryptjs from 'bcryptjs'
import Address from '../models/address.model.js'
import jwt from 'jsonwebtoken'

export const register = asyncHandler(async (req, res, next) => {
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
   if (!newUser) {
      return next(errorHandler(500, 'Internal Server error'))
   }

   const token = jwt.sign({ id: newUser._id }, process.env.JWT_SECRET, {
      expiresIn: '60m',
   })

   const userWithoutPassword = newUser.toObject()
   delete userWithoutPassword.password

   res.status(201).set('Authorization', `Bearer ${token}`).json({
      success: true,
      message: 'User registered successfully',
      user: userWithoutPassword,
   })

   console.log(newUser)
})

export const login = asyncHandler(async (req, res, next) => {
   const { email, password } = req.body
   if (!email || !password) {
      return next(errorHandler(404, 'Not found'))
   }
   const user = await User.findOne({ email: email }).select('+password')
   if (!user) {
      return next(errorHandler(403, 'Invalid Email or Password'))
   }
   const isMatch = await bcryptjs.compare(password, user.password)
   if (!isMatch) {
      return next(errorHandler(403, 'Invalid Email or Password'))
   }

   const userWithoutPassword = user.toObject()
   delete userWithoutPassword.password
   delete userWithoutPassword.addresses

   const token = jwt.sign(
      { id: user._id, userType: user.userType },
      process.env.JWT_SECRET,
      {
         expiresIn: '60m',
      },
   )

   res.status(200)
      .set('Authorization', `Bearer ${token}`)
      .json({
         success: true,
         message: `Welcome back, ${user.name}`,
         user: userWithoutPassword,
      })
})

export const logout = (req, res) => {
   res.status(200).json({
      success: true,
      message: 'Logged out successfully',
   })
}

export const getAddress = asyncHandler(async (req, res, next) => {
   if (req.user.id !== req.params.userId) {
      return next(errorHandler(403, 'You are not authorized'))
   }

   const userId = req.params.userId

   const user = await User.findById(userId)

   if (!user) {
      return next(errorHandler(404, 'User not found'))
   }

   const addresses = user.addresses

   res.status(200).json({
      addresses: addresses,
   })
})

export const addAddress = asyncHandler(async (req, res, next) => {
   if (req.user.id !== req.params.userId) {
      return next(errorHandler(403, 'You are not authorized'))
   }

   const userId = req.params.userId
   const { houseNumber, floor, area, landmark, name, phone } = req.body

   const user = await User.findById(userId)
   if (!user) {
      return next(errorHandler(404, 'User not found'))
   }

   const newAddress = new Address({
      houseNumber,
      floor,
      area,
      landmark,
      name,
      phone,
   })

   user.addresses.push(newAddress)
   await user.save()

   res.status(201).json({
      success: true,
      message: 'Address added successfully',
      address: newAddress,
   })
})

export const updateAddress = asyncHandler(async (req, res, next) => {
   if (req.user.id !== req.params.userId) {
      return next(errorHandler(403, 'You are not authorized'))
   }

   const userId = req.params.userId
   const addressId = req.params.addressId
   const { houseNumber, floor, area, landmark, name, phone } = req.body

   const user = await User.findById(userId)
   if (!user) {
      return next(errorHandler(404, 'User not found'))
   }

   const addressIndex = user.addresses.findIndex(
      address => address._id.toString() === addressId,
   )

   if (addressIndex === -1) {
      return next(errorHandler(404, 'Address not found'))
   }

   user.addresses[addressIndex] = {
      ...user.addresses[addressIndex],
      houseNumber,
      floor,
      area,
      landmark,
      name,
      phone,
   }

   await user.save()

   res.status(200).json({
      success: true,
      message: 'Address updated successfully',
      address: user.addresses[addressIndex],
   })
})

export const deleteAddress = asyncHandler(async (req, res, next) => {
   if (req.user.id !== req.params.userId) {
      return next(errorHandler(403, 'You are not authorized'))
   }

   const userId = req.params.userId
   const addressId = req.params.addressId

   const user = await User.findById(userId)
   if (!user) {
      return next(errorHandler(404, 'User not found'))
   }

   const addressIndex = user.addresses.findIndex(
      address => address._id.toString() === addressId,
   )

   if (addressIndex === -1) {
      return next(errorHandler(404, 'Address not found'))
   }

   user.addresses.splice(addressIndex, 1)
   await user.save()

   res.status(200).json({
      success: true,
      message: 'Address deleted successfully',
   })
})
