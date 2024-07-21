import express from 'express'
import { createAddress } from '../controller/address.controller.js'

const router = express.Router()

router.post('/create', createAddress)

export default router
