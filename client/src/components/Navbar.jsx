import React, { useState } from 'react'
import { Search, ShoppingCart, User, LogIn, MapPin } from 'lucide-react'
import {
   Disclosure,
   Menu,
   MenuButton,
   MenuItem,
   MenuItems,
} from '@headlessui/react'

export default function Header() {
   const [isLoggedIn, setIsLoggedIn] = useState(false)

   const toggleLogin = () => {
      setIsLoggedIn(!isLoggedIn)
   }

   return (
      <Disclosure as='nav' className='bg-[#4CAF50] sticky top-0 right-0 left-0 z-50 shadow-md'>
         <div className='mx-auto w-full px-2 sm:px-6 lg:px-8'>
            {/* Mobile layout */}
            <div className='sm:hidden'>
               <div className='flex items-center justify-between py-3'>
                  <div className='flex items-center'>
                     {/* <img
                        alt='EasyGrocers'
                        src='/cart.png'
                        className='h-8 w-8 overflow-hidden rounded-full'
                     /> */}
                     <img
                        alt='EasyGrocers'
                        src='/logo-no-background.svg'
                        className='h-7 w-auto overflow-hidden ml-2 bg-[#4CAF50]'
                     />
                  </div>
                  <div className='flex items-center space-x-2'>
                     <button className='text-white p-1 rounded-full hover:bg-[#ff6d35]'>
                        <ShoppingCart size={24} />
                     </button>
                     {isLoggedIn ? (
                        <Menu as='div' className='relative'>
                           <MenuButton className='flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-[#4CAF50]'>
                              <span className='sr-only'>Open user menu</span>
                              <img
                                 alt='User'
                                 src='https://images.unsplash.com/photo-1445499348736-29b6cdfc03b9?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'
                                 className='h-8 w-8 rounded-full'
                              />
                           </MenuButton>
                           <MenuItems className='absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none'>
                              <MenuItem>
                                 {({ active }) => (
                                    <a
                                       href='#'
                                       className={`${
                                          active ? 'bg-gray-100' : ''
                                       } block px-4 py-2 text-sm text-gray-700`}
                                    >
                                       Your Profile
                                    </a>
                                 )}
                              </MenuItem>
                              <MenuItem>
                                 {({ active }) => (
                                    <a
                                       href='#'
                                       className={`${
                                          active ? 'bg-gray-100' : ''
                                       } block px-4 py-2 text-sm text-gray-700`}
                                    >
                                       Settings
                                    </a>
                                 )}
                              </MenuItem>
                              <MenuItem>
                                 {({ active }) => (
                                    <a
                                       href='#'
                                       onClick={toggleLogin}
                                       className={`${
                                          active ? 'bg-gray-100' : ''
                                       } block px-4 py-2 text-sm text-gray-700`}
                                    >
                                       Sign out
                                    </a>
                                 )}
                              </MenuItem>
                           </MenuItems>
                        </Menu>
                     ) : (
                        <button
                           onClick={toggleLogin}
                           className='text-white p-1 rounded-full hover:bg-[#ff6d35]'
                        >
                           <LogIn size={24} />
                        </button>
                     )}
                  </div>
               </div>
               <div className='pb-3'>
                  <div className='relative'>
                     <input
                        type='text'
                        placeholder='Search for groceries...'
                        className='w-full p-2 rounded-full pl-10 text-gray-800'
                     />
                     <Search
                        className='absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400'
                        size={20}
                     />
                  </div>
                  <div className='mt-2 flex items-center text-white text-sm'>
                     <MapPin size={16} className='mr-1' />
                     <span>Deliver to: </span>
                     <span className='font-semibold ml-1'>New York 10001</span>
                  </div>
               </div>
            </div>

            {/* Desktop layout */}
            <div className='hidden sm:flex sm:h-16 sm:items-center sm:justify-between'>
               <div className='flex items-center space-x-4'>
                  <div className='flex items-center'>
                     {/* <img
                        alt='EasyGrocers'
                        src='/cart.png'
                        className='h-10 w-10 overflow-hidden rounded-full'
                     /> */}
                     <img
                        alt='EasyGrocers'
                        src='/logo-no-background.svg'
                        className='h-8 w-auto overflow-hidden ml-2'
                     />
                  </div>
                  <div className='flex flex-col items-start text-white text-sm'>
                     <span className='font-bold text-xs'>Delivery in 10 mins</span>
                     <div className='flex items-center'>
                        <MapPin size={16} className='mr-1' />
                        <span className='font-semibold'>
                           New York 10001
                        </span>
                     </div>
                  </div>
               </div>

               <div className='flex-grow mx-4'>
                  <div className='relative'>
                     <input
                        type='text'
                        placeholder='Search for groceries...'
                        className='w-full p-2 rounded-full pl-10'
                     />
                     <Search
                        className='absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400'
                        size={20}
                     />
                  </div>
               </div>

               <div className='flex items-center space-x-2'>
                  {!isLoggedIn && (
                     <button
                        onClick={toggleLogin}
                        className='bg-white text-[#4CAF50] px-3 py-1 rounded-full text-sm font-medium hover:bg-gray-100'
                     >
                        Login
                     </button>
                  )}

                  <button className='bg-white text-[#4CAF50] px-3 py-1 rounded-full text-sm font-medium hover:bg-gray-100 flex items-center'>
                     <ShoppingCart size={16} className='mr-1' />
                     <span>My Cart</span>
                  </button>

                  {isLoggedIn && (
                     <Menu as='div' className='relative'>
                        <MenuButton className='flex rounded-full bg-gray-800 text-sm focus:outline-none focus:ring-2 focus:ring-white focus:ring-offset-2 focus:ring-offset-[#4CAF50]'>
                           <span className='sr-only'>Open user menu</span>
                           <img
                              alt='User'
                              src='https://images.unsplash.com/photo-1445499348736-29b6cdfc03b9?q=80&w=1740&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D'
                              className='h-8 w-8 rounded-full'
                           />
                        </MenuButton>
                        <MenuItems className='absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none'>
                           <MenuItem>
                              {({ active }) => (
                                 <a
                                    href='#'
                                    className={`${
                                       active ? 'bg-gray-100' : ''
                                    } block px-4 py-2 text-sm text-gray-700`}
                                 >
                                    Your Profile
                                 </a>
                              )}
                           </MenuItem>
                           <MenuItem>
                              {({ active }) => (
                                 <a
                                    href='#'
                                    className={`${
                                       active ? 'bg-gray-100' : ''
                                    } block px-4 py-2 text-sm text-gray-700`}
                                 >
                                    Settings
                                 </a>
                              )}
                           </MenuItem>
                           <MenuItem>
                              {({ active }) => (
                                 <a
                                    href='#'
                                    onClick={toggleLogin}
                                    className={`${
                                       active ? 'bg-gray-100' : ''
                                    } block px-4 py-2 text-sm text-gray-700`}
                                 >
                                    Sign out
                                 </a>
                              )}
                           </MenuItem>
                        </MenuItems>
                     </Menu>
                  )}
               </div>
            </div>
         </div>
      </Disclosure>
   )
}
