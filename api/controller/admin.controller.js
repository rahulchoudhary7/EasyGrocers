import Admin from '../models/admin.model.js'
import { errorHandler } from '../utils/errorHandler.js'
import asyncHandler from 'express-async-handler'
import bcryptjs from 'bcryptjs'
import jwt from 'jsonwebtoken'

export const registerAdmin = asyncHandler(async (req, res, next) => {
   const admin = req.body
   if (!admin) {
      return next(errorHandler(400, 'Admin data not provided'))
   }
   if (!admin.password) {
      return next(errorHandler(400, 'Password not provided'))
   }

   const hashPassword = bcryptjs.hashSync(admin.password, 10)
   admin.password = hashPassword

   const newAdmin = await Admin.create(admin)
   if (!newAdmin) {
      return next(errorHandler(500, 'Internal Server error'))
   }

   const token = jwt.sign(
      { id: newAdmin._id, userType: newAdmin.userType, role: newAdmin.role },
      process.env.JWT_SECRET,
      {
         expiresIn: '60m',
      },
   )

   const adminWithoutPassword = newAdmin.toObject()
   delete adminWithoutPassword.password

   res.status(201).set('Authorization', `Bearer ${token}`).json({
      success: true,
      message: 'Admin registered successfully',
      admin: adminWithoutPassword,
   })

   console.log(newAdmin)
})

export const loginAdmin = asyncHandler(async (req, res, next) => {
   const { email, password } = req.body
   if (!email || !password) {
      return next(errorHandler(400, 'Email and password are required'))
   }

   const admin = await Admin.findOne({ email: email }).select('+password')
   if (!admin) {
      return next(errorHandler(403, 'Invalid Email or Password'))
   }

   if (!admin.isActive) {
      return next(errorHandler(403, 'Admin account is deactivated'))
   }

   const isMatch = await bcryptjs.compare(password, admin.password)
   if (!isMatch) {
      return next(errorHandler(403, 'Invalid Email or Password'))
   }

   // Update last login
   await Admin.findByIdAndUpdate(admin._id, { lastLogin: new Date() })

   const adminWithoutPassword = admin.toObject()
   delete adminWithoutPassword.password

   const token = jwt.sign(
      { id: admin._id, userType: admin.userType, role: admin.role },
      process.env.JWT_SECRET,
      {
         expiresIn: '60m',
      },
   )

   res.status(200)
      .set('Authorization', `Bearer ${token}`)
      .json({
         success: true,
         message: `Welcome back, ${admin.adminName}`,
         admin: adminWithoutPassword,
      })
})

export const logoutAdmin = (req, res) => {
   res.status(200).json({
      success: true,
      message: 'Logged out successfully',
   })
}

export const getAllAdmins = asyncHandler(async (req, res) => {
   const page = parseInt(req.query.page) || 1
   const limit = parseInt(req.query.limit) || 10
   const skip = (page - 1) * limit

   const { role, isActive } = req.query
   
   const filter = {}
   if (role) filter.role = role
   if (isActive !== undefined) filter.isActive = isActive === 'true'

   const admins = await Admin.find(filter)
      .select('adminName email role isActive permissions lastLogin createdAt')
      .skip(skip)
      .limit(limit)
      .sort({ createdAt: -1 })

   const total = await Admin.countDocuments(filter)

   res.json({
      success: true,
      admins,
      totalPages: Math.ceil(total / limit),
      currentPage: page,
      total
   })
})

export const updateAdminStatus = asyncHandler(async (req, res, next) => {
   const { id } = req.params
   const { isActive } = req.body

   const admin = await Admin.findByIdAndUpdate(
      id,
      { isActive },
      { new: true }
   ).select('-password')

   if (!admin) {
      return next(errorHandler(404, 'Admin not found'))
   }

   res.json({
      success: true,
      message: `Admin ${isActive ? 'activated' : 'deactivated'} successfully`,
      admin
   })
})

export const deleteAdmin = asyncHandler(async (req, res, next) => {
   const { id } = req.params

   const admin = await Admin.findByIdAndDelete(id)
   if (!admin) {
      return next(errorHandler(404, 'Admin not found'))
   }

   res.json({
      success: true,
      message: 'Admin deleted successfully'
   })
})