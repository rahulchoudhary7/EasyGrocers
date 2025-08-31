import express from "express";
import { loginSeller, logoutSeller, registerSeller } from "../controller/seller.controller.js";

const sellerAuthRouter = express.Router()


sellerAuthRouter.post('/register', registerSeller);
sellerAuthRouter.post('/login', loginSeller);
sellerAuthRouter.get('/logout', logoutSeller);

export default sellerAuthRouter;