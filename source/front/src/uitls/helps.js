// Dummy data

export const fakeFeatuesList = {
	result_code: '0000',
	result: {
	  baseFeatureGroupDaoList: [
		{
		  baseFeatureGroupId: 1,
		  depthPath: 'user_demographic',
		  baseFeatureGroupName: 'user_demographic',
		  description: '테스트용1',
		  totalCount: 100,
		  baseFeature: [
			{
			  baseFeatureId: 1,
			  baseFeatureName: 'sex',
			  attributeClass: 'L',
			  type: 'String',
			},
			{
			  baseFeatureId: 2,
			  baseFeatureName: 'age_range',
			  attributeClass: 'L',
			  type: 'String',
			},
			{
			  baseFeatureId: 3,
			  baseFeatureName: 'job',
			  attributeClass: 'L',
			  type: 'String',
			},
		  ],
		  children: [],
		},
		{
		  baseFeatureGroupId: 2,
		  depthPath: 'user_relationship',
		  baseFeatureGroupName: 'user_relationship',
		  description: '테스트용2',
		  totalCount: 100,
		  baseFeature: [
			{
			  baseFeatureId: 0,
			  baseFeatureName: null,
			  attributeClass: null,
			  type: null,
			},
		  ],
		  children: [
			{
			  baseFeatureGroupId: 3,
			  depthPath: 'user_relationship/friends',
			  baseFeatureGroupName: 'friends',
			  description: '테스트용3',
			  totalCount: 100,
			  baseFeature: [
				{
				  baseFeatureId: 4,
				  baseFeatureName: 'follower',
				  attributeClass: 'C',
				  type: 'Integer',
				},
				{
				  baseFeatureId: 5,
				  baseFeatureName: 'followee',
				  attributeClass: 'C',
				  type: 'Integer',
				},
				{
				  baseFeatureId: 6,
				  baseFeatureName: 'bothways',
				  attributeClass: 'C',
				  type: 'Integer',
				},
			  ],
			  children: [],
			},
			{
			  baseFeatureGroupId: 4,
			  depthPath: 'user_relationship/unFriends',
			  baseFeatureGroupName: 'unFriends',
			  description: '테스트용4',
			  totalCount: 100,
			  baseFeature: [
				{
				  baseFeatureId: 7,
				  baseFeatureName: 'blocker',
				  attributeClass: 'C',
				  type: 'Integer',
				},
				{
				  baseFeatureId: 8,
				  baseFeatureName: 'blocked',
				  attributeClass: 'C',
				  type: 'Integer',
				},
			  ],
			  children: [],
			},
		  ],
		},
	  ],
	},
  };
  
  export const fakeFeatures = [
	{
	  baseFeatureId: 1,
	  result_code: '0000',
	  type: 'label',
	  featureName: 'sex',
	  result: {
		labelDaoList: [
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 1,
			labelId: 1,
			labelName: 'f',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 1,
			labelId: 2,
			labelName: 'm',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 1,
			labelId: 3,
			labelName: 'unkown',
			description: null,
			labelCondition: null,
		  },
		],
	  },
	},
	{
	  baseFeatureId: 2,
	  result_code: '0000',
	  type: 'label',
	  featureName: 'age_range',
	  result: {
		labelDaoList: [
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 1,
			labelName: '~ 14',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 2,
			labelName: '15-19',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 3,
			labelName: '20-24',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 4,
			labelName: '25-29',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 5,
			labelName: '30-34',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 6,
			labelName: '35-39',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 7,
			labelName: '40-44',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 8,
			labelName: '45-49',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 9,
			labelName: '50-54',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 10,
			labelName: '55-59',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 11,
			labelName: '60-64',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 12,
			labelName: '65-69',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 13,
			labelName: '70 ~',
			description: null,
			labelCondition: null,
		  },
		],
	  },
	},
	{
	  baseFeatureId: 3,
	  result_code: '0000',
	  type: 'label',
	  featureName: 'job',
	  result: {
		labelDaoList: [
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 1,
			labelName: '중학생',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 2,
			labelName: '고교생,고등 전문 학교생',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 3,
			labelName: '대학생,전문,기타 학생',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 4,
			labelName: '회사원(정사원',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 5,
			labelName: '회사원(파견 사원,계약직',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 6,
			labelName: '파트,아르바이트(프리터',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 7,
			labelName: '공무원 단체 직원',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 8,
			labelName: '경영자,임원',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 9,
			labelName: '자영업,자유업,기타 직업',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 10,
			labelName: '전업 주부 주부,가사 도우미',
			description: null,
			labelCondition: null,
		  },
		  {
			baseFeatureGroupId: 1,
			baseFeatureId: 2,
			labelId: 11,
			labelName: '무직,휴직,기타 직업',
			description: null,
			labelCondition: null,
		  },
		],
	  },
	},
	{
	  baseFeatureId: 4,
	  result_code: '0000',
	  type: 'attribute',
	  featureName: 'follower',
	  result: {
		attributeDaoList: [
		  {
			baseFeatureGroupId: 3,
			baseFeatureId: 4,
			min: 10.0,
			max: 10000.0,
			average: 3000.0,
			continuousAttributeId: 1,
		  },
		],
	  },
	},
	{
	  baseFeatureId: 5,
	  result_code: '0000',
	  type: 'attribute',
	  featureName: 'followee',
	  result: {
		attributeDaoList: [
		  {
			baseFeatureGroupId: 3,
			baseFeatureId: 4,
			min: 9.0,
			max: 93020.0,
			average: 3000.0,
			continuousAttributeId: 1,
		  },
		],
	  },
	},
  ];
  
  export const fakeFactor = [
	{
	  feature_id: 0,
	  factor: [
		{
		  value: 'm',
		  title: '남성',
		},
		{
		  value: 'f',
		  title: '여성',
		},
		{
		  value: 'unknown',
		  title: '미정',
		},
	  ],
	  type: 'A',
	},
	{
	  feature_id: 1,
	  factor: [
		'~ 14 세',
		'15-19 세',
		'20-24 세',
		'25-29 세',
		'30-34 세',
		'35-39 세',
		'40-44 세',
		'45-49 세',
		'50-54 세',
		'55-59 세',
		'60-64 세',
		'65-69 세',
		'70~ 세',
	  ],
	  type: 'B',
	},
	{
	  feature_id: 2,
	  factor: [
		{
		  value: 0,
		  title: '중학생',
		},
		{
		  value: 1,
		  title: '고교생,고등 전문 학교생',
		},
		{
		  value: 2,
		  title: '대학생,전문,기타 학생',
		},
		{
		  value: 3,
		  title: '회사원(정사원)',
		},
		{
		  value: 4,
		  title: '회사원(파견 사원,계약직)',
		},
		{
		  value: 5,
		  title: '파트,아르바이트(프리터)',
		},
		{
		  value: 6,
		  title: '공무원 단체 직원',
		},
		{
		  value: 7,
		  title: '경영자,임원',
		},
		{
		  value: 8,
		  title: '자영업,자유업,기타 직업',
		},
		{
		  value: 9,
		  title: '전업 주부 주부,가사 도우미',
		},
		{
		  value: 10,
		  title: '무직,휴직,기타 직업',
		},
	  ],
	  type: 'A',
	},
	{
	  feature_id: 3,
	  min: 500,
	  max: 70000,
	  type: 'C',
	},
	{
	  feature_id: 4,
	  min: 400,
	  max: 40000,
	  type: 'C',
	},
	{
	  feature_id: 5,
	  min: 900,
	  max: 800000,
	  type: 'C',
	},
	{
	  feature_id: 6,
	  factor: [
		{
		  value: 'apple',
		  title: '애플',
		},
		{
		  value: 'samsung',
		  title: '삼성',
		},
	  ],
	  type: 'A',
	},
	{
	  feature_id: 7,
	  factor: [
		{
		  value: '10.1',
		  title: '10.1 Version',
		},
		{
		  value: '11.2',
		  title: '11.2 Version',
		},
		{
		  value: '13.4',
		  title: '10.1 Version',
		},
		{
		  value: '12.7',
		  title: '12.7 Version',
		},
	  ],
	  type: 'A',
	},
  ];
  
  export const fakeSelected = {
	selected_category: [
	  {
		category_title: 'User Demo',
		selected: [
		  {
			selected_id: 0,
			selected: 'sex',
			selected_title: '성별',
		  },
		  {
			selected_id: 1,
			selected: 'age',
			selected_title: '연령대',
		  },
		  {
			selected_id: 2,
			selected: 'occupation',
			selected_title: '직업',
		  },
		],
	  },
	  {
		category_title: 'User Relationship',
		selected: [
		  {
			selected_id: 3,
			selected: 'followees',
			selected_title: '라인 회원 팔로이',
		  },
		  {
			selected_id: 4,
			selected: 'followers',
			selected_title: '라인 회원 팔로워',
		  },
		  {
			selected_id: 5,
			selected: 'bothways',
			selected_title: '맞팔',
		  },
		],
	  },
	],
  };
  // Random Number
  const randomIntFromInterval = (min, max) => {
	// min and max included
	return Math.floor(Math.random() * (max - min + 1) + min);
  };
  const randomDate = (start, end) => {
	return new Date(
	  start.getTime() + Math.random() * (end.getTime() - start.getTime())
	)
	  .toISOString()
	  .substring(0, 10);
  };
  
  randomDate(new Date(2012, 0, 1), new Date());
  export const featureList = [
	{
	  code: 'A00101',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00102',
	  name: '타겟 피처 세트 이름',
	  hashtags: ['해시', '태그'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00103',
	  name: 'Feature Set Name Target ',
	  hashtags: ['일본', '안드로이드', '남자'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00104',
	  name: 'Set Name Target name',
	  hashtags: ['대학생', '안드로이드'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00105',
	  name: 'Target Feature Set Name',
	  hashtags: ['Feature', 'Store'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00123',
	  name: 'Wow Users Rules',
	  hashtags: ['라인', 'Line'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00106',
	  name: 'Stay a Step Ahead',
	  hashtags: ['헬스', 'Health'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00107',
	  name: 'Perfect Details',
	  hashtags: ['예시', '입니다'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00108',
	  name: 'Always Data-driven',
	  hashtags: ['서울', '40대'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00109',
	  name: 'Build Lean and Exceptional Teams',
	  hashtags: ['H', 'A', 'S', 'H', 'T', 'A', 'G', 'S'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00110',
	  name: 'Open Communication, Vetical Decision-making',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00111',
	  name: '1% Problem-finding, 99% Solution-making',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00112',
	  name: 'Enjoy The Challanges',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00113',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00114',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00115',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00116',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00117',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00118',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
	{
	  code: 'A00119',
	  name: 'Target Feature Set Name',
	  hashtags: ['피처', '스토어'],
	  lastModifiedAt: randomDate(new Date(2021, 0, 1), new Date()),
	  mid_count: randomIntFromInterval(0, 999999999),
	},
  ];
  