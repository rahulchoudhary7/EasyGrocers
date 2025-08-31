import express from "express";
import { loginAdmin, logoutAdmin, registerAdmin } from "../controller/admin.controller.js";

const adminAuthRouter = express.Router()

adminAuthRouter.post('/register', registerAdmin);
adminAuthRouter.post('/login', loginAdmin);
adminAuthRouter.get('/logout', logoutAdmin);

export default adminAuthRouter;