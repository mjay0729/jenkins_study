import React from 'react'

// Router
import { Link, useLocation} from 'react-router-dom'

// Styled
import styled from 'styled-components';

// React-Icons
import {TbError404} from "react-icons/tb"

// {📍 TODO : More Style }
const Error = () => {
  const {pathname} = useLocation();
  return (
    <Wrapper className='full-page'>
      <div>
        <TbError404 style={{width:500,height:500}} />
        <h3 className='mb-5' >Ohh! Page <span className='text-danger' >["{pathname}"]</span> Not Found</h3>
        <Link className='bg-dark rounded text-danger p-2' style={{fontSize:"2rem"}} to='/'>back home</Link>
      </div>
    </Wrapper>
  )
}

export default Error

const Wrapper = styled.main`
  text-align: center;
  height:100%;
  display: flex;
  align-items: center;
  justify-content: center;
  h3 {
    margin-bottom: 0.5rem;
  }
  p {
    margin-top: 0;
    margin-bottom: 0.5rem;
  }
  a {
    background-color:red;
    list-style:none;
    text-decoration:none;
    text-transform: capitalize;
  }
`;

