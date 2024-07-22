import mongoose from 'mongoose'

const itemSchema = mongoose.Schema({
   itemName: {
      type: String,
      required: true,
   },

   category: {
      type: String,
      required: true,
   },

   subCategory: {
      type: String,
   },

   description: {
      type: String,
      required: true,
   },

   itemPrice: {
      type: String,
      required: true,
   },

   discount: {
      type: number,
      required: true,
   },

   image: {
      type: String,
      required: true,
   },

   isVeg: {
      type: Boolean,
      required: true,
   },
   weight: {
      type: String,
      required: true,
   },

   types: {
      type: Array,
   },
})
