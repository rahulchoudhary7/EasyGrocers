import asyncHandler from 'express-async-handler'
import { errorHandler } from '../utils/errorHandler.js'

export const fetchAllInventoryItemsBySeller = asyncHandler(
   async (req, res, next) => {
      const javaUrl = process.env.JAVA_URL

      const user = req.user

      const userId = user?.id || 'guest'
      const userType = user?.userType || 'guest'

      const { sellerId } = req.query

      const response = await fetch(
         `${javaUrl}/api/InventoryItem/getInventoryItemsBySeller?sellerId=${sellerId}`,
         {
            headers: {
               'X-API-Key': process.env.API_KEY,
               'X-User-Id': userId,
               'X-User-Type': userType,
            },
         },
      )

      if (!response.ok) {
         return next(errorHandler(500, `Error fetching inventory items`))
      }

      const inventoryItems = await response.json()
      res.json(inventoryItems)
   },
)

export const fetchInventoryItemById = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL

   const user = req.user
   const userId = user?.id || 'guest'
   const userType = user?.userType || 'guest'

   const { inventoryItemId } = req.query

   const response = await fetch(
      `${javaUrl}/api/InventoryItem/getInventoryItem?inventoryItemId=${inventoryItemId}`,
      {
         headers: {
            'X-API-Key': process.env.API_KEY,
            'X-User-Id': userId,
            'X-User-Type': userType,
         },
      },
   )

   if (!response.ok) {
      return next(errorHandler(500, 'Could not fetch Item'))
   }

   const inventoryItem = await response.json()

   res.json(inventoryItem)
})

export const createInventoryItem = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL
   const user = req.user

   if (user.userType === 'user') {
      return next(errorHandler(403, 'Forbidden'))
   }

   const newInventoryItem = req.body
   const response = await fetch(
      `${javaUrl}/api/InventoryItem/createInventoryItem`,
      {
         method: 'POST',
         headers: {
            'X-API-Key': process.env.API_KEY,
            'X-User-Id': user.id,
            'X-User-Type': user.userType,
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(newInventoryItem),
      },
   )

   if (!response.ok) {
      return next(errorHandler(500, `Error creating inventory item`))
   }

   const createdInventoryItem = await response.json()

   res.json(createdInventoryItem)
})

export const updateInventoryItem = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL
   const user = req.user
   if (user.userType === 'user') {
      return next(errorHandler(403, 'Forbidden'))
   }

   const { inventoryItemId } = req.query

   const inventoryItemToUpdate = req.body
   const response = await fetch(
      `${javaUrl}/api/InventoryItem/updateInventoryItem?inventoryItemId=${inventoryItemId}`,
      {
         method: 'PUT',
         headers: {
            'X-API-Key': process.env.API_KEY,
            'X-User-Id': user.id,
            'X-User-Type': user.userType,
            'Content-Type': 'application/json',
         },
         body: JSON.stringify(inventoryItemToUpdate),
      },
   )

   if (!response.ok) {
      return next(errorHandler(500, `Error updating inventory item`))
   }

   const updatedInventoryItem = await response.json()
   res.json(updatedInventoryItem)
})

export const deleteInventoryItem = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL

   const user = req.user
   if (user.userType === 'user') {
      return next(errorHandler(403, 'Forbidden'))
   }

   const { inventoryItemId } = req.query

   const response = await fetch(
      `${javaUrl}/api/InventoryItem/deleteInventoryItem?inventoryItemId=${inventoryItemId}`,
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

   res.json({ message: 'Item deleted' })
})
