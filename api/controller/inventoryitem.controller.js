import asyncHandler from 'express-async-handler'
import { errorHandler } from '../utils/errorHandler.js'

export const fetchAllInventoryItemsForSeller = asyncHandler(
   async (req, res, next) => {
      const javaUrl = process.env.JAVA_URL

      const user = req.user

      
      const { sellerId } = req.query
      
      console.log('====================================')
      console.log(user.id, user.userType, sellerId)
      console.log('====================================')
      const response = await fetch(
         `${javaUrl}/api/InventoryItem/getInventoryItemsForSeller?sellerId=${sellerId}`,
         {
            headers: {
               'X-API-Key': process.env.API_KEY,
               'X-User-Id': user.id,
               'X-User-Type': user.userType,
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

   const { inventoryItemId } = req.query

   const response = await fetch(
      `${javaUrl}/api/InventoryItem/getInventoryItem?iventoryItemId=${inventoryItemId}`,
      {
         headers: {
            'X-API-Key': process.env.API_KEY,
            'X-User-Id': user.id,
            'X-User-Type': user.userType,
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

   console.log(user.id, user.userType)
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

   // if (!response.ok) {
   //    return next(errorHandler(500, `Error updating inventory item`))
   // }

   const updatedInventoryItem = await response.json()
   res.json(updatedInventoryItem)
})

export const deleteInventoryItem = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL

   const user = req.user
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
