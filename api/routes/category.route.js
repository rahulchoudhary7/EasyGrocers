import express from 'express'
import { isAuthenticated } from '../middleware/auth.js'
import {
   createCategory,
   deleteCategory,
   getAllCategories,
   updateCategory,
} from '../controller/category.controller.js'

const categoryRouter = express.Router()

categoryRouter.get('/getAllCategories', getAllCategories)

categoryRouter.post('/createCategory', isAuthenticated, createCategory)

categoryRouter.put('/updateCategory', isAuthenticated, updateCategory)

categoryRouter.delete('/deleteCategory', isAuthenticated, deleteCategory)


export default categoryRouter
