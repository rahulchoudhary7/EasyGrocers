import express from 'express'
import { isAuthenticated } from '../middleware/auth.js'
import {
   createdInventoryItem,
   fetchInventoryItems,
} from '../controller/inventoryitem.controller.js'

const inventoryItemRouter = express.Router()

inventoryItemRouter.get(
   '/getInventoryItems',
   isAuthenticated,
   fetchInventoryItems,
)

inventoryItemRouter.post(
   '/createInventoryItem',
   isAuthenticated,
   createdInventoryItem,
)

export default inventoryItemRouter
