import express from 'express';
import { getAllAdmins, updateAdminStatus, deleteAdmin } from '../controller/admin.controller.js';

const adminRouter = express.Router();

adminRouter.get('/all', getAllAdmins);
adminRouter.patch('/status/:id', updateAdminStatus);
adminRouter.delete('/:id', deleteAdmin);

export default adminRouter;