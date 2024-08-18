import express from 'express';
import { registerSeller, loginSeller, logoutSeller } from '../controller/seller.controller.js';

const sellerRouter = express.Router();

sellerRouter.post('/register', registerSeller);
sellerRouter.post('/login', loginSeller);
sellerRouter.get('/logout', logoutSeller);
sellerRouter.get('/byCategory/:category')


export default sellerRouter;
