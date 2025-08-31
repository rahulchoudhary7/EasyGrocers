import express from "express";
import { login, logout, register } from "../controller/user.controller.js";


const userAuthRouter = express.Router();


userAuthRouter.post('/register', register)
userAuthRouter.post('/login', login)
userAuthRouter.get('/logout', logout)

export default userAuthRouter;