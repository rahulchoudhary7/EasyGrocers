import asyncHandler from 'express-async-handler'
import { errorHandler } from '../utils/errorHandler.js'

export const getAllCategories = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL

   const user = req.user

   const response = await fetch(`${javaUrl}/api/categories/getAllCategories`, {
      headers: {
         'X-API-Key': process.env.API_KEY,
         'X-User-Id': user.id,
         'X-User-Type': user.userType,
      },
   })

   if (!response.ok) {
      return next(errorHandler(500, 'Could not get categories.'))
   }

   const categories = await response.json()

   res.json(categories)
})

export const createCategory = asyncHandler(async (req, res, next) => {
   const user = req.user

   const javaUrl = process.env.JAVA_URL

      if (user.userType !== 'admin') {
         return next(errorHandler(403, 'Forbidden'))
      }

   const categoryToCreate = req.body

   const response = await fetch(`${javaUrl}/api/categories/createCategory`, {
      method: 'POST',
      headers: {
         'X-API-Key': process.env.API_KEY,
         'X-User-Id': user.id,
         'X-User-Type': user.userType,
         'Content-type': 'application/json',
      },
      body: JSON.stringify(categoryToCreate),
   })

   if (!response.ok) {
      return next(errorHandler(500, 'Could not create category.'))
   }

   const createdCategory = await response.json()

   res.json(createdCategory)
})

export const updateCategory = asyncHandler(async (req, res, next) => {
   const user = req.user

   const javaUrl = process.env.JAVA_URL

      if (user.userType !== 'admin') {
         return next(errorHandler(403, 'Forbidden'))
      }

   const categoryToUpdate = req.body

   const { categoryId } = req.query

   const response = await fetch(
      `${javaUrl}/api/categories/updateCategory?categoryId=${categoryId}`,
      {
         method: 'PUT',
         headers: {
            'X-API-Key': process.env.API_KEY,
            'X-User-Id': user.id,
            'X-User-Type': user.userType,
            'Content-type': 'application/json',
         },
         body: JSON.stringify(categoryToUpdate),
      },
   )

   if (!response.ok) {
      return next(errorHandler(500, 'Could not update category.'))
   }

   const category = await response.json()

   res.json(category)
})

export const deleteCategory = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL

   const user = req.user

   const { categoryId } = req.query

      if (user.userType !== 'admin') {
         return next(errorHandler(403, 'Forbidden'))
      }

   const response = await fetch(
      `${javaUrl}/api/categories/deleteCategory?categoryId=${categoryId}`,
      {
         method: 'DELETE',
         headers: {
            'X-API-Key': process.env.API_KEY,
            'X-User-Id': user.id,
            'X-User-Type': user.userType,
         },
      },
   )

   if (!response.ok) {
      return next(errorHandler(500, 'Something went wrong'))
   }



   res.json({message:"Category deleted"})
})
