import React, { useEffect, useMemo, useState } from 'react'

// Style
import styled from "styled-components";

// React-Bootstrap
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/esm/Button';
import Pagination from 'react-bootstrap/esm/Pagination';

// Dummy Data
import { featureList } from '../uitls/helps';

// Ag-Grid
import {AgGridReact} from "ag-grid-react"


import 'ag-grid-community/dist/styles/ag-grid.css';
import 'ag-grid-community/dist/styles/ag-theme-alpine.css';


// {📍 TODO : More Style }
// {📍 TODO : Make Form Component independent }
// {📍 TODO : Pagination }


// HashTags Components 
const HashTagsComp=(hash)=>{
  return <div>{hash.value.map(item=><span key={Math.random()} style={{color:'green'}} >#{item} </span>)}</div>
}

const Features = () => {
  const [rowData,setRowData] = useState([]);
  const columnDefs = [
    {field:"code",cellRenderer:(p)=><b>{p.value}</b>},
    {field:"name",filter:"agTextColumnFilter"},
    {field:"hashtags",filter:"agTextColumnFilter",cellRenderer:HashTagsComp},
    {field:"lastModifiedAt",filter:"agDateColumnFilter"},
    {field:"mid_count",headerName:"MID",filter:"agNumberColumnFilter",cellRenderer:p=><>{p.value.toLocaleString()} 개</>},
  ];
  const defaultColDef = useMemo(()=>({
    sortable:true,
    flex:1,
    floatingFilter:true
  }),[])

  const handleCellClick = (e)=>{
    console.log(e,"cell Clicked")
  }
  const handleDobuleClick=(e)=>{
    console.log(e,"cell Dobul Clicked")
  }
  useEffect(()=>{
    setRowData(featureList);
  },[])
  return (
    <Wrapper>

      {/* Form Section */}
      <div className="form" style={{width:"90%",margin:"2rem auto"}}>
          <Form >
            <h3 className='p-2 mb-4 rounded' style={{width:"fit-content"}} >타겟 피처 세트 조회</h3> 
            <Container className='bg-none' >
              {/* 이름 */}
              <Form.Group className="mb-3 d-flex flex-column align-center" controlId="exampleForm.ControlInput1">
                <Form.Label >이름</Form.Label>
                <Form.Control className='ms-auto' type="text" placeholder="타겟 피처 세트 이름을 작성해주세요." />
              </Form.Group>

              {/* 해시태그 */}
              <Form.Group className="mb-3 d-flex flex-column" controlId="exampleForm.ControlInput1">
                <Form.Label>해시 태그</Form.Label>
                <Form.Control className='ms-auto' type="text" placeholder="#해시태그" />
              </Form.Group>

              {/* 버튼 */}
              <Container className='text-end' >
                <Button variant="success" type="submit" className='px-5'>
                  조 회
                </Button>
              </Container>
            </Container>
          </Form>
          <hr />
        </div>

      {/* AG GRID Component*/}
      <Container>
        <div className="ag-theme-alpine" style={{height:"70vh",margin:"0 2rem"}} >
          <AgGridReact 
            onCellDoubleClicked={handleDobuleClick}
            onCellClicked={handleCellClick}
            rowData={rowData}
            columnDefs={columnDefs}
            defaultColDef={defaultColDef}
            animateRows={true}
            pagination={true}
            paginationPageSize={15}
            />
        </div>

        {/* Pagination Section */}
        <Container className='d-flex justify-content-center mt-5' >
          <Pagination>
            <Pagination.First />
            <Pagination.Prev />
            <Pagination.Item active >{1}</Pagination.Item>

            <Pagination.Item>{2}</Pagination.Item>
            <Pagination.Item>{3}</Pagination.Item>
            <Pagination.Item>{4}</Pagination.Item>
            <Pagination.Next />
            <Pagination.Last />
          </Pagination>
        </Container>
      </Container>
    </Wrapper>
  )
}

export default Features;

const Wrapper = styled.div`
  max-width:1720px;
  display:grid;
  grid-template-columns:1fr 4fr;
  margin: 3rem auto 0 auto;

  a{
    list-style:none;
    text-decoration:none;
    color:inherit;
  }
`;
