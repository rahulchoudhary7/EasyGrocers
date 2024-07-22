import express from "express";
import asyncHandler from 'express-async-handler'
import Address from "../models/address.model.js";

export const createAddress = asyncHandler(async(req, res, next)=>{
    const { userId, houseNumber, floor, area, landmark, name, phone } = req.body;

    const newAddress = new Address({
      userId,
      houseNumber,
      floor,
      area,
      landmark,
      name,
      phone
    });

    await newAddress.save()

    res.status(201).json(newAddress)
})