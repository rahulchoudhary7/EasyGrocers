import asyncHandler from 'express-async-handler'

export const fetchInventoryItems = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL

   const user = req.body

   const { sellerId } = req.query

   const response = await fetch(
      `${javaUrl}/api/InventoryItem/getInventoryItemsForSeller?sellerId=${sellerId}`,
      {
         headers: {
            'X-API-Key': process.env.API_KEY,
            'X-User-Id': user.id,
            'X-User-Type': user.userType,
         },
      },
   )

   if (!response.ok) {
      throw new Error(`Error fetching inventory items`)
   }

   const inventoryItems = await response.json()
   res.json(inventoryItems)
})

export const createdInventoryItem = asyncHandler(async (req, res, next) => {
   const javaUrl = process.env.JAVA_URL
   const user = req.body

   const newInventoryItem = req.body;
   const response = await fetch(`${javaUrl}/api/InventoryItem/createInventoryItem`, {
      method: 'POST',
      headers: {
         'X-API-Key': process.env.API_KEY,
         'X-User-Id': user.id,
         'X-User-Type': user.userType,
         'Content-Type': 'application/json',
      },
      body: JSON.stringify(newInventoryItem),
   })

   if (!response.ok) {
      throw new Error(`Error creating inventory item: ${response.statusText}`)
   }

   const createdInventoryItem = await response.json()
   console.log(createdInventoryItem)
   res.json(createdInventoryItem)
})
