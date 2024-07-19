import mongoose from 'mongoose'

const addressSchema = mongoose.Schema({
   userId: {
      type: String,
      required: true,
   },

   houseNumber: {
      type: String,
      required: true,
   },

   floor: {
      type: String,
   },

   area: {
      type: String,
      required: true,
   },

   landmark: {
      type: String,
      required: true,
   },

   name: {
      type: String,
      required: true,
   },

   phone: {
      type: String,
      required: true,
   },
})

const Address = mongoose.model('Address', addressSchema)


export default Address