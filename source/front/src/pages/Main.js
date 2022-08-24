import { useEffect, useState } from 'react';

// Styled
import styled from 'styled-components';

// React-Bootstrap
import Container from 'react-bootstrap/Container';
import Badge from 'react-bootstrap/Badge';
import Form from 'react-bootstrap/Form';

// Components
import MultiRangeSlider from '../components/RangeSlider/MultiRangeSlider';
import EstimationModal from '../components/Modal/EstimationModal';

// Languages Service
import { useTranslation } from 'react-i18next';
import { BaseFeatureAPI } from '../api/baseFeature';

// {📍 TODO : Refactor Fn getCategoryList  }
// {📍 TODO : Progress (Step Bar) refactor }
// {📍 TODO : handle Category Column2(when Click Column1 category) }

const Main = () => {
  // Languges Services
  const {t}=useTranslation();


  // state
  const [categoryList, setCategoryList] = useState([]); // Column1 category List
  const [categorySearch, setCategorySearch] = useState(''); // search term
  const [categoryFilterList, setCategoryFilterList] = useState([]); // seatch filter List

  const [selectFeatures, setSelcetFeatures] = useState([]); // select Features
  const [holdingFeatures, setHoldFeatures] = useState([]);
  const [modalData,setModalData]=useState({
    isOpen:false,
    title:"",
    data:""
  });
  const [registerInfo,setRegisterInfo]=useState({
    name:"",
    hashtags:"",
    description:""
  })

  // Multi input
  const [rangeValue, setRagneValue] = useState({ min: 25, max: 29000 });

  // Callback Fn
  const handleInput = (e) => {
    setRagneValue(() => {
      return { min: e.minValue, max: e.maxValue };
    });
  };

  const handleSearch = (e) => {
    const { value } = e.target;
    setCategorySearch(value);
    if (value === '') {
      setCategoryFilterList([]);
      setCategoryList(categoryList);
      return;
    }

    const filterList = categoryList.result.base_feature_group_list.map(
      (group) => {
        // User Demo
        if (group.base_feature_group_name === 'demographic') {
          return group.children.map(item=>item.base_feature).flat();
        }

        // No User Demo
        if (group.baseFeatureGroupName !== 'user_demographic') {
          return group.children.map((item) => item.base_feature).flat();
        }
        return group;
      }
    );

    const newFilterList = filterList.flat().filter((item) => {
      return item.base_feature_name.includes(value);
    });
    setCategoryFilterList(newFilterList);
  };

  const handleChartClick=(feature)=>{
    console.log({feature});
    setModalData(prev=>{
      return{...prev,isOpen:true,title:feature.featureName}
    });
  }
  // Handle Category
  const handleCategory = (e) => {
    const { features } = e.target.dataset;
    const { checked } = e.target;

    let prac = [...selectFeatures];
    if (checked) {
      prac.push(features);
    } else {
      prac = prac.filter((num) => num !== features);
    }
    setSelcetFeatures(prac);

    // if (checked) {
    //   // fetch Data (add to list)
    //   setTimeout(() => {
    //     // set time out for dev mode
    //     const newList = fakeFeatures.find(
    //       (feat) => feat.baseFeatureId === parseInt(features)
    //     );

    //     setHoldFeatures((prev) => {
    //       return [...prev, newList];
    //     });
    //   }, 100);
    // } else {
    //   // filter list
    //   const filterHoldList = holdingFeatures.filter((item) => {
    //     if (item.baseFeatureId) {
    //       return item.baseFeatureId !== parseInt(features);
    //     }
    //     return item;
    //   });
    //   console.log({ filterHoldList });
    //   setHoldFeatures(filterHoldList);
    // }
  };

  const handleRegisterForm=(e)=>{
    const {name,value}=e.target;
    setRegisterInfo(prev=>{
      return {
        ...prev,
        [name]:value
      }
    })
  }

  // progress step fn (Progress bar)
  const isStepOneChecked = selectFeatures.length > 0;
  const isStepFourChecked = Object.keys(registerInfo)
    .map(item=>registerInfo[item])
    .reduce((prev,curr)=>{
      if(curr.length > 0 ){
        return prev + 1
      }
      return prev;
  },0);
  const isCategoryNull = categoryList.length < 1;

  const getCategoryList=async()=>{
    try {
      const res = await fetch(BaseFeatureAPI);
      const result = await res.json();
      setCategoryList(result);
      
    } catch (error) {
      console.log(error,"Fetch Failed")
    }
  }

  // Use Effect

  useEffect(() => {
    getCategoryList();
  }, []);

  return (
    <Wrapper> {/* Styled components */}

      {/* Modal */}
      <EstimationModal modalData={modalData} setShow={setModalData} />

      <div className='d-flex flex-column align-items-center justify-content-center' >
        <h4 >
          {t('Target Feature set')}
        </h4>

        {/* Progress bar */}
        <div className='d-flex gap-3'>
          <Badge bg={isStepOneChecked?"success":"secondary"} style={{transition:"0.1s all linear",transform:isStepOneChecked?"scale(1.2)":""}} >1</Badge>
          <Badge bg="secondary">2</Badge>
          <Badge bg="secondary">3</Badge>
          <Badge bg={isStepFourChecked===0?"secondary" : isStepFourChecked===3?"success":"warning" } style={{transition:"0.1s all linear",transform:isStepFourChecked > 0 ?"scale(1.2)":""}} >4</Badge>
        </div>
      </div>

      <div className="wrapper">
        {/* 1️⃣ Column1 */}
        <Container className="container_r" style={{width:"370px"}} >
          <h4>{t("Features Category")}</h4>

          {/* search form */}
          <Form className='d-flex' >
            <Form.Control type="email" placeholder={t("search")} onChange={handleSearch} />
          </Form>
          

          {/* category section */}

          {/* 1. category List 로딩 인경우 */}
          {isCategoryNull && <h5>Loading...</h5>}

          {/* 2. categorySearchterm 🅾️ 있을경우. */}
          {categorySearch !== '' && categoryFilterList.length === 0 ? (
            <h6>No exact matches found</h6>
          ) : (
            categoryFilterList.map((item) => {
              return (
                <div key={item.base_feature_id} className="mt-2">
                  <Container className="d-flex mb-2">
                    <div className="d-flex me-1 align-items-center border">
                      <Form>
                        <Form.Check 
                          type={'checkbox'}
                          data-features={item.base_feature_id}
                          id={item.base_feature_id}
                          label={item.base_feature_name}
                          onChange={handleCategory}
                        />
                      </Form>
                    </div>
                  </Container>
                </div>
              );
            })
          )}

          {/* 3. categorySearchterm ❌ 없을경우. */}
          {categorySearch === '' &&
            !isCategoryNull &&
            categoryList.result.base_feature_group_list.map((group) => {
              const baseTitle =group.base_feature_group_name
              return (
                <div key={group.baseFeatureGroupId}>
                  <h4 className="text-start">{group.baseFeatureGroupName}</h4>

                  {/* User Demo 처리. */}
                  {group.base_feature_group_name === "demographic" && (
                    <Container className='text-start text-muted'>
                      <h4 >{baseTitle}</h4>
                      <div className='d-flex'>
                        {group.children.map((feature) => {
                          return (
                            <div
                              className="border d-flex me-1 rounded align-items-center text-dark"
                              key={feature.base_feature_group_id}
                            >
                              <Form>
                                 <Form.Check 
                                  type={'checkbox'}
                                  data-features={feature.base_feature_group_id}
                                  id={feature.base_feature_group_id}
                                  label={feature.base_feature_group_name}
                                  onChange={handleCategory}
                                 />
                              </Form>
                            </div>
                          );
                        })}
                      </div>
                    </Container>
                    )}

                  {/* User Demo 이외것 처리 */}
                  {group.base_feature_group_name !== 'demographic' && (
                      <Container className="text-start text-muted">
                        <h4>{baseTitle}</h4>
                        {group.children.map((childrenGroup,index) => {
                          return (
                            <div key={childrenGroup.base_feature_group_id}>
                              <h6>{`${index+1}. ${childrenGroup.base_feature_group_name}`}</h6>
                              {/* 2뎁스 조건들 Render */}
                              <div className="d-flex flex-wrap gap-1">
                                {childrenGroup.base_feature.map((feature) => {
                                  return (
                                    <div
                                      className="border d-flex rounded align-items-center text-dark"
                                      key={feature.base_feature_id}
                                    >
                                      <Form>
                                        <Form.Check 
                                          type={'checkbox'}
                                          data-features={feature.base_feature_id}
                                          id={feature.base_feature_id}
                                          label={feature.base_feature_name}
                                          onChange={handleCategory}
                                        />
                                      </Form>
                                    </div>
                                  );
                                })}
                              </div>
                            </div>
                          );
                        })}
                      </Container>
                    )}
                  <hr />
                </div>
              );
            })}
        </Container>

        {/* 2️⃣ Column2 */}
        <Container className="container_r">
          {/* column 에서 선택된 category list */}
          {holdingFeatures.length === 0 && (
            <div>
              <h1>No Selected Feature.</h1>
              <p>Please Select Feature from Left Section</p>
            </div>
          )}
          {holdingFeatures.length > 0 && holdingFeatures.map((feature) => {
            return (
              <SelectWrapper key={feature.baseFeatureId}  > {/* Styled components */}
                <div className='mb-0 d-flex flex-column justify-content-center bg-secondary text-white feature-title' >
                  <h6 style={{fontSize:"16px"}}>
                    {feature.featureName}
                  </h6>
                  {feature.type && (
                    <div className='chart__icon' onClick={()=>handleChartClick(feature)}>
                      📊
                    </div>)
                  }
                </div>
                <div className='column2_section' >
                  {/* Label Type */}
                  {feature.type === 'label' &&
                    feature.result.labelDaoList.map((label) => {
                      return (
                        <div
                          key={label.labelId}
                          className="d-flex align-items-center"
                          style={{ border: '1px solid #222' }}
                        >
                          <input type="checkbox" />
                          <h6 className="ms-2 mb-0">{label.labelName}</h6>
                        </div>
                      );
                    })}
                  {/* Attribute Type */}
                  {feature.type === 'attribute' &&
                    feature.result.attributeDaoList.map((attribute) => {
                      return (
                        <div
                          key={attribute.baseFeatureId}
                          className="d-flex align-items-center w-100"
                        >
                          <MultiRangeSlider
                            min={attribute.min}
                            max={attribute.max}
                            label={true}
                            minValue={rangeValue.min}
                            maxValue={rangeValue.max}
                            onInput={(e) => {
                              handleInput(e);
                            }}
                          />
                        </div>
                      );
                  })}
                </div>
              </SelectWrapper>
            );
          })}
        </Container>

        {/* 3️⃣ Column3 */}
        <Container className="container_r">
          <h6>{t("Target Feature Set Register")}</h6>
          <hr />

          {/* Register Form  */}
          <div className="form">
            <div className="row-form d-flex justify-content-between mb-1">
              <label className="d-flex" htmlFor="name">
                Name :{' '}
              </label>
              <input
                style={{ width: '70%' }}
                type="text"
                name="name"
                id="name"
                onChange={handleRegisterForm}
                value={registerInfo.name}
                placeholder="Please Write Target Feature Name"
              />
            </div>
            <div className="row-form d-flex justify-content-between mb-1">
              <label className="d-flex" htmlFor="hashtags">
                Hashtags :{' '}
              </label>
              <input
                style={{ width: '70%' }}
                type="text"
                name="hashtags"
                id="hashtags"
                onChange={handleRegisterForm}
                value={registerInfo.hashtags}
                placeholder="Please Write Hashtags"
              />
            </div>
            <div className="row-form d-flex justify-content-between mb-1">
              <label htmlFor="description">Description : </label>
              <textarea
                name="description"
                value={registerInfo.description}
                id="description"
                onChange={handleRegisterForm}
                placeholder="Please Write description"
              />
            </div>
          </div>

          {/* Register Button */}
          <div className="form_btn">
            <h6>MID Count</h6>
            <h6>expected 1,000 MID</h6>

            <div className="btns d-flex flex-column w-25 m-auto gap-1">
              <button className="bg-warning">A 버튼</button>
              <button className="bg-success">B 버튼</button>
              <button className="bg-primary">C 버튼</button>
            </div>
          </div>
        </Container>
      </div>
    </Wrapper>
  );
};

export default Main;

const Wrapper = styled.div`
  .wrapper {
    max-width: 1700px;
    margin: 2rem auto;
    text-align: center;
    display: grid;
    grid-gap: 1rem;
    grid-template-columns: 1fr 3fr 1fr;
  }

  /* container */
  .container_r {
    border: 1px solid red;
  }

  /* Accoridon Header */
  .accordion-button {
    padding: 5px;
    background-color: #f2f2f2;
    color: #222;
    font-weight: bold;
  }
`;
const SelectWrapper = styled.div`
  display:flex;
  .feature-title{
    display:grid;
    place-items:center;
    max-width:100px;
    min-width:100px;
    border:1px solid red;
    font-size:13px;
  }
  .column2_section{
    border:1px solid red;
    padding:5px;
    display:flex;
    gap:5px;
    flex-wrap:wrap;
    width:100%;
  }


  .chart__icon{
    cursor: pointer;
    transition:0.1s all linear;
    &:hover{
      transform:scale(1.2);
    }
  }
`;