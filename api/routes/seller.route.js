import express from 'express';
import {  sellerByCategory } from '../controller/seller.controller.js';


const sellerRouter = express.Router();

sellerRouter.get('/byCategory', sellerByCategory)


export default sellerRouter;
