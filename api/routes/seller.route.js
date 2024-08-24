import express from 'express';
import { registerSeller, loginSeller, logoutSeller, sellerByCategory } from '../controller/seller.controller.js';
import { isAuthenticated } from '../middleware/auth.js';

const sellerRouter = express.Router();

sellerRouter.post('/register', registerSeller);
sellerRouter.post('/login', loginSeller);
sellerRouter.get('/logout', logoutSeller);
sellerRouter.get('/byCategory', sellerByCategory)


export default sellerRouter;
