import mongoose from 'mongoose'
export const connectdb = async () => {
   try {
      await mongoose.connect(process.env.MONGODB_CONNECTION_STRING)
      console.log('Database connected successfully')
   } catch (error) {
      console.log(error.message)
   }
}
