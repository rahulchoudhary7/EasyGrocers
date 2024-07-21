import express from 'express'
import { login } from '../controller/user.controller.js'
import { logout } from '../controller/user.controller.js'
import { register } from '../controller/user.controller.js'
import { getUser } from '../controller/user.controller.js'
import { isAuthenticated } from '../middleware.js/auth.js'
const userRouter = express.Router()

userRouter.post("/register",register)
userRouter.post("/login", login);
userRouter.get("/logout", logout);
userRouter.get("/getuser",isAuthenticated, getUser);

export default userRouter;
