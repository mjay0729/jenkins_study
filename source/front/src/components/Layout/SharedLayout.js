import React from 'react'

// Outlet
import {  Outlet } from 'react-router-dom'

// Navbar Component 
import NavbarTemp from '../Navbar/Navbar'

const SharedLayout = () => {
  return (
    <div>
      {/* Navbar _ Only Dev Mode */}
      <NavbarTemp />
      {/* Main */}
        <main>
            <Outlet />
        </main>
    </div>
  )
}

export default SharedLayout