import express from 'express'
import { isAuthenticated } from '../middleware/auth.js'
import {
   createInventoryItem,
   deleteInventoryItem,
   fetchAllInventoryItemsForSeller,
   fetchInventoryItemById,
   updateInventoryItem,
} from '../controller/inventoryitem.controller.js'

const inventoryItemRouter = express.Router()

inventoryItemRouter.get(
   '/getInventoryItems',
   isAuthenticated,
   fetchAllInventoryItemsForSeller,
)

inventoryItemRouter.get(
   '/getInventoryItem',
   isAuthenticated,
   fetchInventoryItemById,
)

inventoryItemRouter.post(
   '/createInventoryItem',
   isAuthenticated,
   createInventoryItem,
)

inventoryItemRouter.put(
   '/updateInventoryItem',
   isAuthenticated,
   updateInventoryItem,
)

inventoryItemRouter.delete(
   '/deleteInventoryItem',
   isAuthenticated,
   deleteInventoryItem,
)

export default inventoryItemRouter
