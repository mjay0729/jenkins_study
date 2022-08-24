// React-Bootstrap
import Modal from 'react-bootstrap/Modal';

// Rechart 
import {BarChart,Bar, Tooltip, XAxis, YAxis, PieChart, Pie, Cell} from "recharts"


// Dummy Data
const data = [
  { name: "남성", count:828828 },
  { name: "여성", count:852324 },
  { name: "미정", count:122334 },
];
const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const EstimationModal = ({modalData,setShow}) => {

  const handleHideModal=()=>{
      setShow(prev=>{
          return {
              ...prev,
              isOpen:false
          }
      })
  }

  // Chart Callback Fn
  const RADIAN = Math.PI / 180;

  const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
    const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
    const x = cx + radius * Math.cos(-midAngle * RADIAN);
    const y = cy + radius * Math.sin(-midAngle * RADIAN);
    console.log({cx, cy, midAngle, innerRadius, outerRadius, percent, index })
    return (
      <text x={x} y={y} fill="white" textAnchor={x > cx ? 'start' : 'end'} dominantBaseline="central">
        {`${(percent * 100).toFixed(0)}%`}
      </text>
    );
  };

  return (
    <Modal
        show={modalData.isOpen}
        size="xl"
        onHide={handleHideModal}
        // dialogClassName="modal-100vw"
        aria-labelledby="example-custom-modal-styling-title"
    >
    <Modal.Header closeButton>
      <Modal.Title id="example-custom-modal-styling-title">
        {modalData.title} 통계
      </Modal.Title>
    </Modal.Header>
    <Modal.Body>
      <p>
        {modalData.title} 의 통계 모달창.
      </p>
        <br />

        {/* Chart Goes Here */}
        <div className='d-flex justify-content-between'>
          <BarChart width={300} height={400} data={data} >
            <Bar dataKey="count" fill="#222" barSize={20} />
            <XAxis dataKey="name" />
            <YAxis dataKey="count" width={90} />
            <Tooltip cursor={false} />
          </BarChart>

          <PieChart width={300} height={300}>
            <Pie 
              data={data} 
              dataKey="count" 
              label={renderCustomizedLabel}
              labelLine={false} 
              fill="#8884d8" 
            >
              {data.map((entry, index) => (
                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
              ))}
            </Pie>
            <Tooltip cursor={false} />
          </PieChart>
        </div>

    </Modal.Body>
    </Modal>
  )
}

export default EstimationModal;